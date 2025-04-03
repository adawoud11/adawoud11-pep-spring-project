package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.ValidationRules.MessageValidationRules;
import com.example.entity.Account;
import com.example.entity.Message;

import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService implements IMessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Message createMessage(Message message) {

        ValidateMessage(message);
        Optional<Account> existingAccount = accountRepository.findById(message.getPostedBy());
        if (existingAccount == null || existingAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageValidationRules.USER_NOT_FOUND_ERROR);
        }

        message.setPostedBy(existingAccount.get().getAccountId());
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageById(Integer messageId) {
        return messageRepository.findById(messageId);
    }

    public Integer deleteMessage(Integer messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return 1;
        }
        return 0;
    }

    public Integer updateMessage(Integer messageId, String newMessageText) {
        // Validate new message text
        if (newMessageText == null || newMessageText.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageValidationRules.MESSAGE_TEXT_BLANK_ERROR);
        }

        if (newMessageText.length() > 255) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageValidationRules.MESSAGE_TEXT_LENGTH_ERROR);
        }

        return messageRepository.findById(messageId)
                .map(message -> {
                    message.setMessageText(newMessageText);
                    messageRepository.save(message);
                    return 1;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        MessageValidationRules.MESSAGE_NOT_FOUND_ERROR));
    }

    public List<Message> getMessagesByAccountId(Integer accountId) {
        Optional<Account> existingAccount = accountRepository.findById(accountId);
        if (existingAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.OK, MessageValidationRules.USER_NOT_FOUND_ERROR);
        }
        return messageRepository.findByPostedBy(existingAccount.get().getAccountId());
    }

    private void ValidateMessage(Message message) {
        // Validate message text
        if (message.getMessageText() == null || message.getMessageText().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageValidationRules.MESSAGE_TEXT_BLANK_ERROR);
        }

        if (message.getMessageText().length() > 255) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageValidationRules.MESSAGE_TEXT_LENGTH_ERROR);
        }

        // Validate postedBy refers to a real user
        if (message.getPostedBy() == null || message.getPostedBy() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageValidationRules.USER_NOT_FOUND_ERROR);
        }
    }

}