package com.travix.medusa.busyflights.service;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BusyFlightServiceTest {
    @Test
    public void testSearchFlights() {
        BusyFlightsService service = new BusyFlightsService();
        List<SupplierInterface> supplierInterfaceList = new ArrayList<>();
        supplierInterfaceList.add(new TestSupplier());
        supplierInterfaceList.add(new TestSupplier2());
        service.suppliers = supplierInterfaceList;
        String flights = service.searchFlights(new BusyFlightsRequest());
        assertEquals("[\n" +
                "  {\n" +
                "    \"airline\": \"Crazy Air\",\n" +
                "    \"supplier\": \"CrazyAir\",\n" +
                "    \"fare\": 3.65,\n" +
                "    \"departureAirportCode\": \"STN\",\n" +
                "    \"destinationAirportCode\": \"GTW\",\n" +
                "    \"departureDate\": \"2007-12-03T10:19:30\",\n" +
                "    \"arrivalDate\": \"2007-12-03T10:15:30\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"airline\": \"Tough Jet\",\n" +
                "    \"supplier\": \"ToughJet\",\n" +
                "    \"fare\": 7.95,\n" +
                "    \"departureAirportCode\": \"STN\",\n" +
                "    \"destinationAirportCode\": \"GTW\",\n" +
                "    \"departureDate\": \"2007-12-03T10:19:30\",\n" +
                "    \"arrivalDate\": \"2007-12-03T10:15:30\"\n" +
                "  }\n" +
                "]", flights);

    }

}
