package org.example.sweater.service;

import org.example.sweater.entity.Message;
import org.example.sweater.entity.User;
import org.example.sweater.entity.dto.MessageDto;
import org.example.sweater.repository.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class MessageService {
    private final MessageRepo messageRepo;

    @Autowired
    EntityManager entityManager;

    public MessageService(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    public Page<MessageDto> messageList(Pageable pageable, String tag, User currentUser) {
        Page<MessageDto> page;
        if (tag != null && !tag.isBlank()) {
            page = messageRepo.findByTag(tag, pageable, currentUser);
        } else {
            page = messageRepo.findAll(pageable, currentUser);
        }
        return page;
    }

    public Page<MessageDto> messageListForUser(Pageable pageable, User author, User currentUser) {
        return messageRepo.findByUser(pageable, author, currentUser);
    }
}
