package com.minas.market.integration.webapi.contract;

import com.minas.market.integration.webapi.helper.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;
import java.util.HexFormat;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*@SpringBootTest
@AutoConfigureMockMvc*/
class AnnouncementImageAPITest extends TestHelper {
    private static final URI PATH = URI.create("/api/v1/announcement-image");
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private UUID announcementId;

    AnnouncementImageAPITest() {
    }
/*
    @BeforeEach
    public void init() {
        UUID userId = createUser();
        announcementId = createAnnouncement(userId);
    }

    @Test
    @DisplayName("Integration test for all methods in Announcement Image API")
    void announcementAPI_CRUD() throws Exception {
        String announcementImageId = postAnnouncementImage();
        getAnnouncementImage(announcementImageId);
        getAllByAnnouncementId();
        deleteAnnouncementImage(announcementImageId);
    }

    private String postAnnouncementImage() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "jpg", HexFormat.ofDelimiter(":")
                .parseHex("e0:4f:d0:20:ea:3a:69:10:a2:d8:08:00:2b:30:30:9d"));

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        ResultActions resultActions = mockMvc.perform(
                multipart(PATH)
                        .file(file)
                        .header("announcement-id", announcementId)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
        ).andExpect(status().isCreated());

        return resultActions.andReturn().getResponse().getContentAsString().replaceAll("\"", "");
    }

    private void getAnnouncementImage(String announcementImageId) throws Exception {
        mockMvc.perform(get(PATH.getPath() + "/" + announcementImageId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.id").value(announcementImageId)
                )
        ;
    }

    private void getAllByAnnouncementId() throws Exception {
        mockMvc.perform(get(PATH)
                        .param("announcementId", announcementId.toString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));
    }

    private void deleteAnnouncementImage(String announcementImageId) throws Exception {
        mockMvc.perform(delete(PATH.getPath() + "/" + announcementImageId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk());

        mockMvc.perform(get(PATH.getPath() + "/" + announcementImageId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isNotFound())
        ;

    }*/
}
