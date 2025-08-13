package com.main.notes.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Table(name = "message_details")
public class MessageDto implements IDto {
    @Column(name = "message")
    private String message;

    @Column(name="create_time")
    private String timeOfCreation;

    @Column(name = "creator")
    private String creator;

    @Column(name = "receiverUser")
    private String receiverUser;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer messageId;

    @Column(name = "public")
    private boolean accessible;

    @Column(name = "fileName")
    private String fileName;

    @Transient
    private boolean replaceImage;

    @Transient
    private String fileNameWithoutId;

    @Transient
    private String filePath;

    @Transient
    private String messageColor;

    @Transient
    private MultipartFile file;

    public void setMessage(String message)
    {
        this.message = message;
    }

    public void setMessageId(Integer messageId)
    {
        this.messageId = messageId;
    }

    public void setTime(String time)
    {
        timeOfCreation = time;
    }

    public void setCreator(String creator)
    {
        this.creator = creator;
    }

    public void setReceiverUser(String receiverUser)
    {
        this.receiverUser = receiverUser;
    }

    public void setAccessible(boolean accessible)
    {
        this.accessible = accessible;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public void setReplaceImage(boolean replaceImage)
    {
        this.replaceImage = replaceImage;
    }

    public void setFileNameWithoutId(String fileNameWithoutId)
    {
        this.fileNameWithoutId = fileNameWithoutId;
    }

    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public void setMessageColor(String messageColor)
    {
        this.messageColor = messageColor;
    }

    public void setFile(MultipartFile file)
    {
        this.file = file;
    }

    public String getMessage()
    {
           return this.message;
    }

    public Integer getMessageId()
    {
        return this.messageId;
    }

    public String getTimeOfCreation()
    {
        return this.timeOfCreation;
    }

    public String getCreator()
    {
        return this.creator;
    }

    public String getReceiverUser()
    {
        return this.receiverUser;
    }

    public boolean getAccessible()
    {
        return this.accessible;
    }

    public String getFileName()
    {
        return this.fileName;
    }

    public boolean getReplaceImage()
    {
        return this.replaceImage;
    }

    public String getFileNameWithoutId()
    {
        return fileNameWithoutId;
    }

    public String getFilePath()
    {
        return this.filePath;
    }

    public String getMessageColor()
    {
       return this.messageColor;
    }

    public MultipartFile getFile()
    {
        return this.file;
    }
}
