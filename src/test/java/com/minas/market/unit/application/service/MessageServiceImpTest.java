package com.minas.market.unit.application.service;

import com.minas.market.application.service.AnnouncementServiceImp;
import com.minas.market.application.service.MessageServiceImp;
import com.minas.market.application.service.UserAuthenticatedServiceImp;
import com.minas.market.application.service.security.UserServiceImp;
import com.minas.market.infrastructure.mapper.MessageMapper;
import com.minas.market.infrastructure.persistence.entity.MessageEntity;
import com.minas.market.infrastructure.persistence.entity.security.User;
import com.minas.market.infrastructure.persistence.repository.MessageRepository;
import com.minas.market.webapi.dto.request.MessageRequest;
import com.minas.market.webapi.exception.NotFoundException;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import static org.jeasy.random.FieldPredicates.named;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;


@SpringBootTest
class MessageServiceImpTest {

    @Autowired
    private MessageServiceImp messageServiceImp;
    @Autowired
    private MessageMapper messageMapper;
    @MockBean
    private MessageRepository messageRepository;
    @MockBean
    private AnnouncementServiceImp announcementServiceImp;
    @MockBean
    private UserServiceImp userServiceImp;
    @MockBean
    private UserAuthenticatedServiceImp userAuthenticatedServiceImp;
    private MessageRequest messageRequest;

    @BeforeEach
    private void init() {
        messageRequest = new EasyRandom().nextObject(MessageRequest.class);

        User userMock = new EasyRandom(
                new EasyRandomParameters().randomize(named("address"), List::of)
                        .randomize(named("announcement"), List::of)
                        .randomize(named("messages"), List::of)
        ).nextObject(User.class);
        Mockito.when(userAuthenticatedServiceImp.me()).thenReturn(userMock);
    }

    @Test
    @DisplayName("Should create message when data is correct")
    void create_onSuccess() {
        validateUserAndAnnouncements_success();
        MessageEntity expected = new MessageEntity();
        expected.setId(UUID.randomUUID());
        Mockito.when(userServiceImp.findUserById(any(UUID.class))).thenReturn(Optional.of(new User()));
        Mockito.when(messageRepository.save(any(MessageEntity.class))).thenReturn(expected);

        MessageEntity entitySaved = messageServiceImp.create(messageRequest);

        assertEquals(expected, entitySaved);
    }

    private void validateUserAndAnnouncements_success() {
        Mockito.when(userServiceImp.findUserById(UUID.randomUUID())).thenReturn(Optional.of(new User()));
        Mockito.when(announcementServiceImp.findById(messageRequest.getAnnouncementId())).thenReturn(null);
    }

    @Test
    @DisplayName("Should updated message when data is correct")
    void update_onSuccess() {
        validateUserAndAnnouncements_success();
        UUID messageId = UUID.randomUUID();
        MessageEntity expected = new EasyRandom().nextObject(MessageEntity.class);
        Mockito.when(messageRepository.findById(messageId)).thenReturn(Optional.of(expected));
        Mockito.when(messageRepository.save(any(MessageEntity.class))).thenReturn(expected);
        Mockito.when(userServiceImp.findUserById(any(UUID.class))).thenReturn(Optional.of(new User()));

        MessageEntity updated = messageServiceImp.update(messageId, messageRequest);

        assertEquals(expected, updated);
    }

    @Test
    @DisplayName("Should return error when user is not found")
    void update_notFoundUser() {


        UUID messageId = UUID.randomUUID();
        Mockito.when(userServiceImp.findUserById(any(UUID.class))).thenReturn(Optional.empty());

        NotFoundException exception = Assertions.assertThrows(NotFoundException.class,
                () -> messageServiceImp.update(messageId, messageRequest));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    @DisplayName("Should find message when exists")
    void findById_onSuccess() {
        MessageEntity expected = new EasyRandom().nextObject(MessageEntity.class);
        Mockito.when(messageRepository.findById(expected.getId())).thenReturn(Optional.of(expected));

        MessageEntity messageEntity = messageServiceImp.findById(expected.getId());

        assertEquals(expected, messageEntity);
    }

    @Test
    @DisplayName("Should throw error when message is not found")
    void findById_onError() {
        UUID messageId = UUID.randomUUID();
        Mockito.when(messageRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        NotFoundException exception = Assertions.assertThrows(NotFoundException.class,
                () -> messageServiceImp.findById(messageId));

        assertEquals("Message not found", exception.getMessage());
    }

    @Test
    @DisplayName("Should deleted message when id is valid")
    void delete() {
        UUID messageIdMock = UUID.randomUUID();
        assertDoesNotThrow(() -> messageServiceImp.delete(messageIdMock));
    }

    @Test
    @DisplayName("Should deleted message when id is valid")
    void readMessage() {
        UUID messageIdMock = UUID.randomUUID();
        assertDoesNotThrow(() -> messageServiceImp.readMessage(messageIdMock));
    }

    @Test
    @DisplayName("Should return messages filtered by userId")
    void getAllMessagesByUserOrAnnouncements_findAllByUserId() {
        List<MessageEntity> expectMessages = new EasyRandom()
                .objects(MessageEntity.class, 10)
                .toList();
        Mockito.when(messageRepository.findAllByUserId(any(UUID.class))).thenReturn(expectMessages);

        List<MessageEntity> messages = messageServiceImp.getAllMessagesByUserOrAnnouncements(UUID.randomUUID(), null);

        assertEquals(expectMessages, messages);
    }

    @Test
    @DisplayName("Should return messages filtered by announcementId")
    void getAllMessagesByUserOrAnnouncements_findAllByAnnouncementId() {
        List<MessageEntity> expectMessages = new EasyRandom()
                .objects(MessageEntity.class, 10)
                .toList();
        Mockito.when(messageRepository.findAllByAnnouncementId(any(UUID.class))).thenReturn(expectMessages);

        List<MessageEntity> messages = messageServiceImp.getAllMessagesByUserOrAnnouncements(null, UUID.randomUUID());

        assertEquals(expectMessages, messages);
    }

    @Test
    @DisplayName("Should return messages filtered by userId and announcementId")
    void getAllMessagesByUserOrAnnouncements_findAllByUserIdAndAnnouncementId() {
        List<MessageEntity> expectMessages = new EasyRandom()
                .objects(MessageEntity.class, 10)
                .toList();
        Mockito.when(messageRepository.findAllByUserIdAndAnnouncementId(any(UUID.class), any(UUID.class))).thenReturn(expectMessages);

        List<MessageEntity> messages = messageServiceImp.getAllMessagesByUserOrAnnouncements(UUID.randomUUID(), UUID.randomUUID());

        assertEquals(expectMessages, messages);
    }

    @Test
    @DisplayName("Should return an empty list when not informing the filter")
    void getAllMessagesByUserOrAnnouncements_default() {
        List<MessageEntity> messages = messageServiceImp.getAllMessagesByUserOrAnnouncements(null, null);
        assertEquals(List.of(), messages);
    }

}
