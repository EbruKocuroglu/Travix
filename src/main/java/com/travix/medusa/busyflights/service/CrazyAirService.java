package com.travix.medusa.busyflights.service;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CrazyAirService implements SupplierInterface {
    private static final String CRAZY_AIR_REST_BASE_URL = "http://localhost:8080";
    private static final String CRAZY_AIR_REST_URL = "/apiCrazyAir/search";
    private final WebClient client;

    public CrazyAirService() {
        client = WebClient.create(CRAZY_AIR_REST_BASE_URL);
    }

    public CrazyAirService(String baseUrl) {
        client = WebClient.create(baseUrl);
    }

    /**
     * Searches flights for Crazy Air supplier
     * @param busyFlightsRequest search criteria
     * @return BusyFlightsResponse list which corresponds to given criterias
     */
    @Override
    public List<BusyFlightsResponse> searchFlights(BusyFlightsRequest busyFlightsRequest) {
        CrazyAirRequest crazyAirRequest = getCrazyAirRequestInstance(busyFlightsRequest);
        List<CrazyAirResponse> crazyAirResponse = getCrazyAirLineFlights(crazyAirRequest);

        return convertResponseList(crazyAirResponse);
    }

    private List<BusyFlightsResponse> convertResponseList(List<CrazyAirResponse> crazyAirResponseList) {
        List<BusyFlightsResponse> busyFlightsResponseList = new ArrayList<BusyFlightsResponse>();
        for (CrazyAirResponse crazyAirResponse : crazyAirResponseList) {
            busyFlightsResponseList.add(convertCrazyAirResponseToBusyFlightsResponse(crazyAirResponse));
        }
        return busyFlightsResponseList;
    }

    private BusyFlightsResponse convertCrazyAirResponseToBusyFlightsResponse(CrazyAirResponse crazyAirResponse) {
        BusyFlightsResponse busyFlightsResponse = new BusyFlightsResponse();
        busyFlightsResponse.setAirline(crazyAirResponse.getAirline());
        busyFlightsResponse.setArrivalDate(crazyAirResponse.getArrivalDate());
        busyFlightsResponse.setDepartureDate(crazyAirResponse.getDepartureDate());
        busyFlightsResponse.setDepartureAirportCode(crazyAirResponse.getDestinationAirportCode());
        busyFlightsResponse.setDestinationAirportCode(crazyAirResponse.getDestinationAirportCode());
        busyFlightsResponse.setSupplier("Crazy Air");
        busyFlightsResponse.setFare(crazyAirResponse.getPrice());
        return busyFlightsResponse;
    }

    private List<CrazyAirResponse> getCrazyAirLineFlights(CrazyAirRequest crazyAirRequest) {
        WebClient.RequestHeadersSpec<?> headersSpec = client.method(HttpMethod.POST)
                .uri(CRAZY_AIR_REST_URL)
                .body(
                        Mono.just(crazyAirRequest), CrazyAirRequest.class);

        WebClient.ResponseSpec responseSpec = headersSpec.header(
                HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .acceptCharset(StandardCharsets.UTF_8)
                .ifNoneMatch("*")
                .ifModifiedSince(ZonedDateTime.now())
                .retrieve();
        CrazyAirResponse[] crazyAirResponse = responseSpec.bodyToMono(CrazyAirResponse[].class).block();
        if (crazyAirResponse != null) {
            return Arrays.asList(crazyAirResponse);
        }
        return new ArrayList<>();
    }


    private CrazyAirRequest getCrazyAirRequestInstance(BusyFlightsRequest busyFlightsRequest) {
        CrazyAirRequest crazyAirRequest = new CrazyAirRequest();
        crazyAirRequest.setOrigin(busyFlightsRequest.getOrigin());
        crazyAirRequest.setDestination(busyFlightsRequest.getDestination());
        crazyAirRequest.setDepartureDate(busyFlightsRequest.getDepartureDate());
        crazyAirRequest.setReturnDate(busyFlightsRequest.getReturnDate());
        crazyAirRequest.setPassengerCount(busyFlightsRequest.getNumberOfPassengers());
        return crazyAirRequest;
    }
}
