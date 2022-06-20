package suppliersAPI;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/apiToughJet")
public class ToughJetSupplierController {
    @PostMapping("/search")
    public @ResponseBody
    String searchFlights() {
        return "[{\"carrier\":\"Tough Jet\",\n" +
                "\"price\":7.600432,\n" +
                "\"tax\": 5.623423,\n" +
                "\"discount\": 7,\n" +
                "\"departureAirportName\": \"ABC\",\n" +
                "\"arrivalAirportName\": \"Wed\",\n" +
                "\"outboundDateTime\": \"2011-12-03T10:15:30Z\",\n" +
                "\"inboundDateTime\": \"2011-12-04T10:15:30Z\"}]";
    }

}





