package com.travix.medusa.busyflights.controller;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.service.BusyFlightsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;


@Controller
@RequestMapping("/api")
public class BusyFlightsController {

    private final BusyFlightsService busyFlightsService;

    public BusyFlightsController(BusyFlightsService busyFlightsService) {
        this.busyFlightsService = busyFlightsService;
    }

    /**
     * Searches flights for all suppliers
     * @param busyFlightsRequest search criteria
     * @return Search results which correspond to given criteria
     */
    @PostMapping("/searchFlights")
    public @ResponseBody
    String searchFlights(@Valid @RequestBody BusyFlightsRequest busyFlightsRequest) {
        return busyFlightsService.searchFlights(busyFlightsRequest);
    }


}
