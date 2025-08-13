package com.main.notes.service;

import com.main.notes.dto.GetAllMessagesDto;
import com.main.notes.dto.MessageFiltersDto;
import com.main.notes.dto.IDto;
import com.main.notes.event.IMessageEvent;
import com.main.notes.dto.MessageDto;
import com.main.notes.exception.MessageNotFoundException;

public interface IMessageService {
    IMessageEvent createMessage(MessageDto message);
    IMessageEvent updateMessage(MessageDto message);
    IDto getMessage(String messageId) throws MessageNotFoundException;
    IMessageEvent deleteMessage(String messageId, String username);
    IMessageEvent deleteMessageByUsername(String username);
    GetAllMessagesDto getAllMessages(MessageFiltersDto filters);
}
