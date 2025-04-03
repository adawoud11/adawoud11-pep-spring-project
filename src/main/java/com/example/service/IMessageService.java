package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.entity.Message;

public interface IMessageService {
    List<Message> getAllMessages();

    Optional<Message> getMessageById(Integer messageId);

    List<Message> getMessagesByAccountId(Integer accountId);

    Message createMessage(Message message);

    Integer updateMessage(Integer messageId, String newMessageText);

    Integer deleteMessage(Integer messageId);
}