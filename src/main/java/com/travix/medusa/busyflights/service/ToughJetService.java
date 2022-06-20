package com.travix.medusa.busyflights.service;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
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
public class ToughJetService implements SupplierInterface {
    private static final String TOUGH_JET_REST_URL = "/apiToughJet/search";
    private static final String TOUGH_JET_REST_BASE_URL = "http://localhost:8080";
    private final WebClient client;

    public ToughJetService() {
        client = WebClient.create(TOUGH_JET_REST_BASE_URL);
    }

    public ToughJetService(String baseUrl) {
        client = WebClient.create(baseUrl);
    }

    /**
     * Searches flights for Tough Jet supplier
     * @param busyFlightsRequest search criteria
     * @return BusyFlightsResponse list which corresponds to given criterias
     */
    @Override
    public List<BusyFlightsResponse> searchFlights(BusyFlightsRequest busyFlightsRequest) {

        ToughJetRequest toughJetRequest = getToughJetRequestInstance(busyFlightsRequest);
        List<ToughJetResponse> toughJetResponses = getToughJetAirLineFlights(toughJetRequest);

        return convertResponseList(toughJetResponses);
    }

    private List<BusyFlightsResponse> convertResponseList(List<ToughJetResponse> toughJetResponses) {
        List<BusyFlightsResponse> busyFlightsResponseList = new ArrayList<BusyFlightsResponse>();
        for (ToughJetResponse toughJetResponse : toughJetResponses) {
            busyFlightsResponseList.add(convertToughJetResponseToBusyFlightsResponse(toughJetResponse));
        }
        return busyFlightsResponseList;
    }

    private BusyFlightsResponse convertToughJetResponseToBusyFlightsResponse(ToughJetResponse toughJetResponse) {
        BusyFlightsResponse busyFlightsResponse = new BusyFlightsResponse();
        busyFlightsResponse.setAirline(toughJetResponse.getCarrier());
        busyFlightsResponse.setArrivalDate(toughJetResponse.getInboundDateTime());
        busyFlightsResponse.setDepartureDate(toughJetResponse.getOutboundDateTime());
        busyFlightsResponse.setDepartureAirportCode(toughJetResponse.getDepartureAirportName());
        busyFlightsResponse.setDestinationAirportCode(toughJetResponse.getArrivalAirportName());
        busyFlightsResponse.setSupplier("Tough Jet");
        busyFlightsResponse.setFare(getPrice(toughJetResponse));
        return busyFlightsResponse;

    }

    private double getPrice(ToughJetResponse toughJetResponse) {
        return toughJetResponse.getBasePrice() - (toughJetResponse.getBasePrice() * toughJetResponse.getDiscount() / 100) + toughJetResponse.getTax();
    }

    private List<ToughJetResponse> getToughJetAirLineFlights(ToughJetRequest toughJetRequest) {
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = client.method(HttpMethod.POST);


        WebClient.RequestBodySpec bodySpec = uriSpec.uri(TOUGH_JET_REST_URL);

        WebClient.RequestHeadersSpec<?> headersSpec = bodySpec.body(
                Mono.just(toughJetRequest), ToughJetRequest.class);

        WebClient.ResponseSpec responseSpec = headersSpec.header(
                HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .acceptCharset(StandardCharsets.UTF_8)
                .ifNoneMatch("*")
                .ifModifiedSince(ZonedDateTime.now())
                .retrieve();
        ToughJetResponse[] toughJetResponses = responseSpec.bodyToMono(ToughJetResponse[].class).block();
        if (toughJetResponses != null) {
            return Arrays.asList(toughJetResponses);
        }
        return new ArrayList<>();
    }

    private ToughJetRequest getToughJetRequestInstance(BusyFlightsRequest busyFlightsRequest) {
        ToughJetRequest toughJetRequest = new ToughJetRequest();
        toughJetRequest.setFrom(busyFlightsRequest.getOrigin());
        toughJetRequest.setTo(busyFlightsRequest.getDestination());
        toughJetRequest.setInboundDate(busyFlightsRequest.getDepartureDate());
        toughJetRequest.setOutboundDate(busyFlightsRequest.getReturnDate());
        toughJetRequest.setNumberOfAdults(busyFlightsRequest.getNumberOfPassengers());

        return toughJetRequest;
    }
}
