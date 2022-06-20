package com.travix.medusa.busyflights.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class BusyFlightsService {

    @Autowired
    public List<SupplierInterface> suppliers;
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Double.class, (JsonSerializer<Double>) (src, typeOfSrc, context) -> {
                DecimalFormat df = new DecimalFormat("#.00");
                df.setRoundingMode(RoundingMode.CEILING);
                return new JsonPrimitive(Double.parseDouble(df.format(src)));
            })
            .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) -> new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
            .create();

    /**
     * Searches flights for all suppliers
     * @param busyFlightsRequest search criteria
     * @return search results as a String in Json format
     */
    public String searchFlights(BusyFlightsRequest busyFlightsRequest) {

        List<BusyFlightsResponse> results = new ArrayList<>();
        suppliers.forEach(supplier -> results.addAll(supplier.searchFlights(busyFlightsRequest)));

        results.sort(Comparator.comparingDouble(BusyFlightsResponse::getFare));

        return gson.toJson(results);
    }

}
