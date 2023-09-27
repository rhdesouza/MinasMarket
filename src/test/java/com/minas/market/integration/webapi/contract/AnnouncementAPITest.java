package com.minas.market.integration.webapi.contract;

import com.minas.market.integration.webapi.helper.TestHelper;
import com.minas.market.webapi.dto.request.AnnouncementRequest;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.jeasy.random.FieldPredicates.named;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class AnnouncementAPITest extends TestHelper {
    private static final URI PATH = URI.create("/api/v1/announcement");
    @Autowired
    private MockMvc mockMvc;
    private AnnouncementRequest announcementRequest;
    private UUID userId;
    private HttpHeaders httpHeadersAuth;

    @BeforeEach
    public void init() {
        userId = createUser(UUID.fromString("c6cfbb5f-6715-48b6-b180-f7e2f3129f45"));
        httpHeadersAuth = getAuthorization(userId);
        announcementRequest = new EasyRandom(
                new EasyRandomParameters()
                        .randomize(named("userId"), () -> userId)
                        .randomize(named("saleValue"), () -> BigDecimal.TEN)
        ).nextObject(AnnouncementRequest.class);
    }

    @Test
    @DisplayName("Integration test for all methods in Announcement API")
    void announcementAPI_CRUD() throws Exception {
        String announcementId = postAnnouncement();
        putAnnouncement(announcementId);
        getAnnouncement(announcementId);
        getAllAnnouncementByUserId();
        deleteAnnouncement(announcementId);
    }


    private String postAnnouncement() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(PATH)
                        .headers(httpHeadersAuth)
                        .content(asJsonString(announcementRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isCreated()).andReturn();
        return mvcResult.getResponse().getContentAsString().replaceAll("\"", "");
    }

    private void putAnnouncement(String announcementId) throws Exception {
        mockMvc.perform(put(PATH.getPath() + "/" + announcementId)
                        .headers(httpHeadersAuth)
                        .content(asJsonString(announcementRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(announcementId));
    }

    private void getAnnouncement(String announcementId) throws Exception {
        mockMvc.perform(get(PATH.getPath() + "/" + announcementId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(httpHeadersAuth)
                )
                .andExpect(status().isOk())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.id").value(announcementId)
                )
        ;
    }

    private void getAllAnnouncementByUserId() throws Exception {
        mockMvc.perform(get(PATH)
                        .headers(httpHeadersAuth)
                        .param("userId", userId.toString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));
    }

    private void deleteAnnouncement(String announcementId) throws Exception {
        mockMvc.perform(delete(PATH.getPath() + "/" + announcementId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(httpHeadersAuth)
                )
                .andExpect(status().isOk());

        mockMvc.perform(get(PATH.getPath() + "/" + announcementId)
                        .headers(httpHeadersAuth)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.active").value(false)
                )
        ;
    }
}
