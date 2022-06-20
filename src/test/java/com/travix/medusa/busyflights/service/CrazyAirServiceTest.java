package com.travix.medusa.busyflights.service;

import com.google.gson.Gson;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
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
public class CrazyAirServiceTest {
    public static CrazyAirService crazyAirService;
    public static MockWebServer mockBackEnd;

    @Before
    public void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
        String baseUrl = String.format("http://localhost:%s",
                mockBackEnd.getPort());
        crazyAirService = new CrazyAirService(baseUrl);

    }

    @After
    public void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @Test
    public void testSearcFlights() throws Exception {
        CrazyAirResponse response = new CrazyAirResponse();
        response.setAirline("THY");
        CrazyAirResponse[] mockResponse = new CrazyAirResponse[]{response};

        mockBackEnd.enqueue(new MockResponse()
                .setBody(new Gson().toJson(mockResponse))
                .addHeader("Content-Type", "application/json"));

        List<BusyFlightsResponse> busyFlightsResponseList = crazyAirService.searchFlights(new BusyFlightsRequest());
        assertEquals(1, busyFlightsResponseList.size());
        assertEquals("THY", busyFlightsResponseList.get(0).getAirline());


    }
}
