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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.net.URI;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.jeasy.random.FieldPredicates.named;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PublicAPITest extends TestHelper {
    private static final URI PUBLIC_PATH = URI.create("/api/v1/public/announcements");
    private static final URI PRIVATE_PATH_ANNOUNCEMENTS = URI.create("/api/v1/announcement");
    private static final String DESCRIPTION = "Livro de java para iniciantes.";
    private static final String TITLE = "JAVA PARA INICIANTES";

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void init() throws Exception {
        UUID userId = createUser(UUID.fromString("c6cfbb5f-6715-48b6-b180-f7e2f3129f45"));
        UUID categoryId = createAnnouncementCategory(UUID.randomUUID());
        HttpHeaders httpHeadersAuth = getAuthorization(userId);
        AnnouncementRequest announcementRequest = new EasyRandom(
                new EasyRandomParameters()
                        .randomize(named("title"), () -> TITLE)
                        .randomize(named("categoryId"), ()-> categoryId)
                        .randomize(named("description"), () -> DESCRIPTION)
                        .randomize(named("userId"), () -> userId)
                        .randomize(named("saleValue"), () -> BigDecimal.TEN)
        ).nextObject(AnnouncementRequest.class);

        mockMvc.perform(post(PRIVATE_PATH_ANNOUNCEMENTS)
                        .headers(httpHeadersAuth)
                        .content(asJsonString(announcementRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isCreated()).andReturn();
    }

    @Test
    @DisplayName("Should receive a list of ads when searching by title")
    void getAnnouncement() throws Exception {
        mockMvc.perform(get(PUBLIC_PATH.getPath())
                        .param("title", "JAVA")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));
    }

    @Test
    @DisplayName("Should receive an empty list when you can't find the title")
    void getAnnouncementEmpty() throws Exception {
        mockMvc.perform(get(PUBLIC_PATH.getPath())
                        .param("title", "vazio")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)));
    }
}
