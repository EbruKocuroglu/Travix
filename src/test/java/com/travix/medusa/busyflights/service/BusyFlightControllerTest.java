package com.travix.medusa.busyflights.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc

public class BusyFlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSearchFlightsWithInvalidParameters() throws Exception {
        mockMvc.perform(post("/api/searchFlights")
                .content("{\"origin\":\"STN\",\"destination\":\"GWK\",\"departureDate\":\"2022-05-14\",\"returnDate\":\"2022-05-20\",\"numberOfPassengers\":5}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
