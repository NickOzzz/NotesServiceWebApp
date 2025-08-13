package com.main.notes.service;

import com.main.notes.dto.*;
import com.main.notes.event.*;
import com.main.notes.exception.MessageNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MessageService implements IMessageService {
    private final EntityManager sqlExecutor;
    private final IUserService userService;
    private final IFileService fileService;

    @Autowired
    public MessageService(EntityManager sqlExecutor, IUserService userService, IFileService fileService)
    {
        this.sqlExecutor = sqlExecutor;
        this.userService = userService;
        this.fileService = fileService;
    }

    @Override
    @Transactional
    public IMessageEvent createMessage(MessageDto message)
    {
        if (!message.getAccessible())
        {
            message.setReceiverUser("");
        }

       try
       {
           if (message.getFile() == null
                   || message.getFile().getOriginalFilename() == null
                   || message.getFile().getOriginalFilename().isEmpty())
           {
               message.setFileName("None");
           }
           else
           {
               String fileNameWithId = this.fileService.CreateFile(
                       message.getFile(),
                       message.getCreator(),
                       message.getReceiverUser(),
                       message.getAccessible());
               message.setFileName(fileNameWithId);
           }
           sqlExecutor.persist(message);
           return new MessageCreatedEvent(message.getMessageId().toString());
       }
       catch(Exception ex)
       {
           System.out.println("Could not create message: " + ex.getMessage());
           return new MessageFailedEvent("NoId", Operations.Create);
       }
    }

    @Override
    @Transactional
    public IMessageEvent updateMessage(MessageDto message)
    {
        if (!message.getAccessible())
        {
            message.setReceiverUser("");
        }

        try
        {
            IDto retrievedMessage = getMessage(message.getMessageId().toString());

            if (retrievedMessage instanceof MessageDto
                    && ((MessageDto) retrievedMessage).getCreator().equals(message.getCreator()))
            {
                ReplaceFile(message, (MessageDto)retrievedMessage);
                sqlExecutor.merge(message);
                return new MessageUpdatedEvent(message.getMessageId().toString(), message.getMessage());
            }
            throw new MessageNotFoundException("Could not retrieve message by Id " + message.getMessageId());
        }
        catch(MessageNotFoundException ex)
        {
            System.out.println("Message by this id could not be found: " + message.getMessageId());
            return new MessageFailedEvent(message.getMessageId().toString(), Operations.Get);
        }
        catch(Exception ex)
        {
            System.out.println("Could not update message: " + ex.getMessage());
            return new MessageFailedEvent(message.getMessageId().toString(), Operations.Update);
        }
    }

    @Override
    @Transactional
    public IDto getMessage(String messageId)
            throws MessageNotFoundException
    {
        MessageDto message = sqlExecutor.find(MessageDto.class, Integer.parseInt(messageId));

        if (message == null)
        {
            throw new MessageNotFoundException("Could not retrieve message by Id " + messageId);
        }
        return message;
    }

    @Override
    @Transactional
    public IMessageEvent deleteMessage(String messageId, String username)
    {
        MessageDto message = sqlExecutor.find(MessageDto.class, Integer.parseInt(messageId));
        if (message == null || !message.getCreator().equals(username))
        {
            return new MessageFailedEvent(messageId, Operations.Delete);
        }

        try
        {
            this.fileService.DeleteFile(message.getFileName(), username);
        }
        catch (Exception ex)
        {
            System.out.println("Could not delete message: " + ex.getMessage());
            return new MessageFailedEvent(messageId, Operations.Delete);
        }

        sqlExecutor.remove(message);
        return new MessageDeletedEvent(messageId);
    }

    @Override
    @Transactional
    public IMessageEvent deleteMessageByUsername(String username)
    {
        try
        {
            this.fileService.DeleteDirectory(username);
            sqlExecutor
                    .createQuery("DELETE FROM MessageDto WHERE creator= :creator")
                    .setParameter("creator", username)
                    .executeUpdate();
            return new MessageDeletedEvent("NoId");
        }
        catch(Exception ex)
        {
            System.out.println("Could not delete message by username: " + ex.getMessage());
            return new MessageFailedEvent("NoId", Operations.Delete);
        }
    }

    @Override
    @Transactional
    public GetAllMessagesDto getAllMessages(MessageFiltersDto filters)
    {
        Query query = sqlExecutor.createQuery("FROM MessageDto");
        List<MessageDto> messages = query.getResultList();

        List<String> creators = GetCreators(messages, filters);
        List<MessageDto> filteredMessages = filterMessages(messages, filters);
        SetMessageColors(filteredMessages, filters);

        for (MessageDto message : filteredMessages)
        {
            String fileNameWithId = message.getFileName();
            if (fileNameWithId != null && !fileNameWithId.equals("None"))
            {
                String accessLevel = message.getFileName().split("-")[0];
                int sizeOfAccessLevel = accessLevel.length() + 1;
                final int uuidSize = 36;
                String fileNameWithoutId = fileNameWithId.substring(sizeOfAccessLevel + uuidSize);
                message.setFileNameWithoutId(fileNameWithoutId);

                String filePath = "files/" + message.getCreator() + "/" + fileNameWithId;
                message.setFilePath(filePath);
            }
            else
            {
                message.setFileNameWithoutId("None");
                message.setFilePath("");
            }
        }

        GetAllMessagesDto result = new GetAllMessagesDto();
        result.setMessages(filteredMessages);
        result.setCreators(creators);

        return result;
    }

    private List<String> GetCreators(List<MessageDto> messages, MessageFiltersDto filters)
    {
        List<String> creators = new ArrayList<String>();
        creators.add("");

        List<UserDto> users = this.userService.GetAllUsers();
        for (UserDto user : users)
        {
            String username = user.getUsername();
            if (!username.equals(filters.getUsername()))
            {
                creators.add(username);
            }
        }
        return creators;
    }

    private List<MessageDto> filterMessages(List<MessageDto> messages, MessageFiltersDto filters)
    {
        String username = filters.getUsername();
        String receiverUsername = filters.getReceiverUsername();

        Stream<MessageDto> messagesStream = messages.stream();

        if (username.equals(receiverUsername)) {
            return messagesStream.filter(message -> message.getCreator().equals(username)
                    || (message.getAccessible()
                    && message.getReceiverUser() != null
                    && message.getReceiverUser().equals(username)))
                    .collect(Collectors.toList());
        }

        if (receiverUsername == null || receiverUsername.isBlank() || receiverUsername.isEmpty())
        {
            return messagesStream.filter(message -> message.getCreator().equals(username)
                            || (message.getAccessible()
                            && (message.getReceiverUser() == null
                            || message.getReceiverUser().isEmpty()
                            || message.getReceiverUser().equals(username))))
                    .collect(Collectors.toList());
        }

        return messagesStream.filter(message -> (message.getCreator().equals(username)
                        && message.getReceiverUser() != null
                        && message.getReceiverUser().equals(receiverUsername))
                        || (message.getAccessible()
                        && message.getReceiverUser() != null
                        && message.getReceiverUser().equals(username)
                        && message.getCreator().equals(receiverUsername))
                        || (message.getAccessible()
                        && message.getCreator().equals(receiverUsername)
                        && (message.getReceiverUser() == null || message.getReceiverUser().isEmpty())))
                .collect(Collectors.toList());
    }

    private void SetMessageColors(List<MessageDto> messages, MessageFiltersDto filters)
    {
        for (MessageDto message : messages)
        {
            if (message.getCreator().equals(filters.getUsername()))
            {
                message.setMessageColor("#607960");
            }
            else if (message.getReceiverUser() != null && message.getReceiverUser().equals(filters.getUsername()))
            {
                message.setMessageColor("#609560");
            }
            else
            {
                message.setMessageColor("#606b60");
            }
        }
    }

    private void ReplaceFile(MessageDto message, MessageDto previousMessage) throws Exception
    {
        String creator = message.getCreator();
        String previousFileName = previousMessage.getFileName();
        if (message.getReplaceImage())
        {
            MultipartFile newFile = message.getFile();

            this.fileService.DeleteFile(previousFileName, creator);

            if (newFile != null
                    && newFile.getOriginalFilename() != null
                    && !newFile.getOriginalFilename().isEmpty())
            {
                String newFileNameWithId = this.fileService.CreateFile(
                        newFile,
                        creator,
                        message.getReceiverUser(),
                        message.getAccessible());
                message.setFileName(newFileNameWithId);
            }
            else
            {
                message.setFileName("None");
            }
        }
        else
        {
            previousFileName = this.fileService.RenameFile(
                    previousFileName,
                    message.getCreator(),
                    message.getReceiverUser(),
                    message.getAccessible());
            message.setFileName(previousFileName);
        }
    }
}
