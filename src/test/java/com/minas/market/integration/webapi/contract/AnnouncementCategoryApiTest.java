package com.minas.market.integration.webapi.contract;

import com.minas.market.integration.webapi.helper.TestHelper;
import org.jeasy.random.EasyRandom;
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
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AnnouncementCategoryApiTest extends TestHelper {
    private static final URI PATH = URI.create("/api/v1/announcement-category");
    @Autowired
    private MockMvc mockMvc;
    private HttpHeaders httpHeadersAuth;
    private String announcementCategoryRequest;

    @BeforeEach
    public void init() {
        UUID userId = createUser(UUID.fromString("c6cfbb5f-6715-48b6-b180-f7e2f3129f45"));
        httpHeadersAuth = getAuthorization(userId);
        announcementCategoryRequest = new EasyRandom().nextObject(String.class);
    }

    @Test
    @DisplayName("Integration test for all methods in Announcement Category API")
    void announcementAPI_CRUD() throws Exception {
        String categoryId = postAnnouncementCategory();
        getAnnouncementCategory(categoryId);
        getAllAnnouncementCategories();
        deleteAnnouncementCategory(categoryId);
    }

    private String postAnnouncementCategory() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(PATH)
                        .headers(httpHeadersAuth)
                        .content(asJsonString(announcementCategoryRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isCreated()).andReturn();
        return mvcResult.getResponse().getContentAsString().replaceAll("\"", "");
    }

    private void getAnnouncementCategory(String categoryId) throws Exception {
        mockMvc.perform(get(PATH.getPath() + "/" + categoryId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(httpHeadersAuth)
                )
                .andExpect(status().isOk())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.id").value(categoryId)
                );
    }

    private void getAllAnnouncementCategories() throws Exception {
        mockMvc.perform(get(PATH)
                        .headers(httpHeadersAuth)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));
    }

    private void deleteAnnouncementCategory(String categoryId) throws Exception {
        mockMvc.perform(delete(PATH.getPath() + "/" + categoryId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(httpHeadersAuth)
                )
                .andExpect(status().isOk());

        mockMvc.perform(get(PATH.getPath() + "/" + categoryId)
                        .headers(httpHeadersAuth)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.active").value(false)
                );
    }

}
