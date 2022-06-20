package suppliersAPI;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/apiCrazyAir")
public class CrazyAirSupplierController {
    @PostMapping("/search")
    public @ResponseBody
    String searchFlights() {
        return "[{\"airline\":\"Crazy Airline\",\n" +
                "\"price\":3,\n" +
                "\"cabinclass\": \"Economy\",\n" +
                "\"departureAirportCode\": \"ABC\",\n" +
                "\"destinationAirportCode\": \"ABC\",\n" +
                "\"departureDate\": \"2011-12-03T10:15:30\",\n" +
                "\"arrivalDate\": \"2011-12-03T10:15:30\"}]";
    }

}