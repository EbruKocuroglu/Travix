package com.travix.medusa.busyflights;

import com.travix.medusa.busyflights.service.CrazyAirService;
import com.travix.medusa.busyflights.service.SupplierInterface;
import com.travix.medusa.busyflights.service.ToughJetService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SupplierConfig {
    @Bean
    public SupplierConfig getSupplierConfigBean() {
        return new SupplierConfig();
    }

    @Bean
    public List<SupplierInterface> nameList() {
        return Arrays.asList(new CrazyAirService(), new ToughJetService());
    }
}
