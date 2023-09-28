package com.minas.market.integration.webapi.contract;

import com.minas.market.integration.webapi.helper.TestHelper;
import com.minas.market.webapi.dto.request.MessageRequest;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.jeasy.random.FieldPredicates.named;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MessageAPITest extends TestHelper {

    private static final URI PATH = URI.create("/api/v1/message");
    @Autowired
    private MockMvc mockMvc;
    private UUID userId;
    private UUID announcementId;
    private MessageRequest messageRequest;
    private MessageRequest messageRequest2;
    private HttpHeaders httpHeadersAuth;

    @BeforeEach
    private void init() {
        userId = createUser(UUID.fromString("c6cfbb5f-6715-48b6-b180-f7e2f3129f45"));
        httpHeadersAuth = getAuthorization(userId);
        announcementId = createAnnouncement(userId);
        messageRequest = new EasyRandom(
                new EasyRandomParameters()
                        .randomize(named("userId"), () -> userId)
                        .randomize(named("announcementId"), () -> announcementId)
        ).nextObject(MessageRequest.class);

        messageRequest2 = new EasyRandom(
                new EasyRandomParameters()
                        .randomize(named("userId"), () -> userId)
                        .randomize(named("announcementId"), () -> announcementId)
        ).nextObject(MessageRequest.class);
    }

    @Test
    @DisplayName("Integration test for all methods in Message API")
    void messageAPI_CRUD() throws Exception {
        List<String> messages = postMessage();
        putMessage(messages.get(0));
        getMessage(messages.get(0));
        deleteMessage(messages.get(0));
        getAllMessagesByUserOrAnnouncements();
        readMessage(messages.get(1));
    }

    private List<String> postMessage() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(PATH)
                        .headers(httpHeadersAuth)
                        .content(asJsonString(messageRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult mvcResult2 = mockMvc.perform(post(PATH)
                        .headers(httpHeadersAuth)
                        .content(asJsonString(messageRequest2))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isCreated())
                .andReturn();

        return List.of(
                mvcResult.getResponse().getContentAsString().replaceAll("\"", ""),
                mvcResult2.getResponse().getContentAsString().replaceAll("\"", "")
        );
    }

    private void putMessage(String messageId) throws Exception {
        messageRequest.setMessage("updateMessage");

        mockMvc.perform(put(PATH.getPath() + "/" + messageId)
                        .headers(httpHeadersAuth)
                        .content(asJsonString(messageRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(messageId)).andReturn();
    }

    private void getMessage(String messageId) throws Exception {
        mockMvc.perform(get(PATH.getPath() + "/" + messageId)
                        .headers(httpHeadersAuth)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(messageRequest.getMessage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(messageId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(userId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.announcementId").value(announcementId.toString()));

    }

    private void deleteMessage(String messageId) throws Exception {
        mockMvc.perform(delete(PATH.getPath() + "/" + messageId)
                        .headers(httpHeadersAuth)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk());

        mockMvc.perform(get(PATH.getPath() + "/" + messageId)
                        .headers(httpHeadersAuth)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.deleted").value(true)
                )
        ;
    }

    private void getAllMessagesByUserOrAnnouncements() throws Exception {
        mockMvc.perform(get(PATH)
                        .headers(httpHeadersAuth)
                        .param("userId", userId.toString())
                        .param("announcementId", announcementId.toString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));
    }

    private void readMessage(String messageId) throws Exception {
        mockMvc.perform(post(PATH + "/read/" + messageId)
                        .headers(httpHeadersAuth)
                        .content(asJsonString(messageRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(get(PATH.getPath() + "/" + messageId)
                        .headers(httpHeadersAuth)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.read").value(true)
                );
    }
}
