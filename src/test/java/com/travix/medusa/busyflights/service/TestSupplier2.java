package com.travix.medusa.busyflights.service;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestSupplier2 implements SupplierInterface {
    @Override
    public List<BusyFlightsResponse> searchFlights(BusyFlightsRequest busyFlightsRequest) {
        List<BusyFlightsResponse> busyFlightsResponseList = new ArrayList<>();
        BusyFlightsResponse busyFlightsResponse = new BusyFlightsResponse();
        busyFlightsResponse.setFare(7.95);
        busyFlightsResponse.setSupplier("ToughJet");
        busyFlightsResponse.setDestinationAirportCode("GTW");
        busyFlightsResponse.setDepartureAirportCode("STN");
        busyFlightsResponse.setAirline("Tough Jet");
        busyFlightsResponse.setArrivalDate(LocalDateTime.parse("2007-12-03T10:15:30"));
        busyFlightsResponse.setDepartureDate(LocalDateTime.parse("2007-12-03T10:19:30"));
        busyFlightsResponseList.add(busyFlightsResponse);
        return busyFlightsResponseList;
    }
}
