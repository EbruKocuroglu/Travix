package com.travix.medusa.busyflights.service;

import com.google.gson.Gson;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ToughJetServiceTest {
    public ToughJetService toughJetService;
    public MockWebServer mockBackEnd;

    @Before
    public void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
        String baseUrl = String.format("http://localhost:%s",
                mockBackEnd.getPort());
        toughJetService = new ToughJetService(baseUrl);

    }

    @After
    public void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @Test
    public void testSearcFlights() throws Exception {
        ToughJetResponse response = new ToughJetResponse();
        response.setCarrier("Easy Jet");
        ToughJetResponse[] mockResponse = new ToughJetResponse[]{response};

        mockBackEnd.enqueue(new MockResponse()
                .setBody(new Gson().toJson(mockResponse))
                .addHeader("Content-Type", "application/json"));

        List<BusyFlightsResponse> busyFlightsResponseList = toughJetService.searchFlights(new BusyFlightsRequest());
        assertEquals(1, busyFlightsResponseList.size());
        assertEquals("Easy Jet", busyFlightsResponseList.get(0).getAirline());


    }
}
