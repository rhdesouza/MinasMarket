package com.minas.market.integration.webapi.contract;

import com.minas.market.integration.webapi.helper.TestHelper;
import com.minas.market.webapi.dto.request.AddressRequest;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.jeasy.random.FieldPredicates.named;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AddressAPITest extends TestHelper {

    private static final URI PATH = URI.create("/api/v1/address");
    @Autowired
    private MockMvc mockMvc;
    private UUID userId;
    private AddressRequest addressRequest;

    @BeforeEach
    public void init() {
        userId = createUser(UUID.fromString("c6cfbb5f-6715-48b6-b180-f7e2f3129f45"));
        addressRequest = new EasyRandom(
                new EasyRandomParameters()
                        .randomize(named("number"), () -> 12)
        ).nextObject(AddressRequest.class);
    }

    @Test
    @DisplayName("Integration test for all methods in Address API")
    void announcementAPI_CRUD() throws Exception {
        String addressId = postAddress();
        putAddress(addressId);
        getAnnouncement(addressId);
        getAllAddressByUserId();
        deleteAddress(addressId);
    }

    private String postAddress() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(PATH)
                        .param("userId", userId.toString())
                        .content(asJsonString(addressRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isCreated()).andReturn();
        return mvcResult.getResponse().getContentAsString().replaceAll("\"", "");
    }

    private void putAddress(String addressId) throws Exception {
        mockMvc.perform(put(PATH.getPath() + "/" + addressId)
                        .param("userId", userId.toString())
                        .content(asJsonString(addressRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(addressId));
    }

    private void getAnnouncement(String addressId) throws Exception {
        mockMvc.perform(get(PATH.getPath() + "/" + addressId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.id").value(addressId)
                )
        ;
    }

    private void getAllAddressByUserId() throws Exception {
        mockMvc.perform(get(PATH)
                        .param("userId", userId.toString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));
    }

    private void deleteAddress(String addressId) throws Exception {
        mockMvc.perform(delete(PATH.getPath() + "/" + addressId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk());

        mockMvc.perform(get(PATH.getPath() + "/" + addressId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.active").value(false)
                )
        ;
    }
}
