package com.travix.medusa.busyflights.service;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;

import java.util.List;

/**
 * Suppliers need to implement this interface to provide search results
 */
public interface SupplierInterface {
    List<BusyFlightsResponse> searchFlights(BusyFlightsRequest busyFlightsRequest);
}
