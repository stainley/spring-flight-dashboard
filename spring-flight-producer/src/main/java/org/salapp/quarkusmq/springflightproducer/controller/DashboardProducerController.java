package org.salapp.quarkusmq.springflightproducer.controller;

import org.salapp.quarkusmq.springflightproducer.service.DashboardProducerService;
import org.salapp.springkafka.springflightshared.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/producer")
public class DashboardProducerController {

    private final DashboardProducerService dashboardProducerService;

    @Autowired
    public DashboardProducerController( DashboardProducerService dashboardProducerService) {
        this.dashboardProducerService = dashboardProducerService;
    }

    @PostMapping
    public Mono<Flight> sendMessage(@RequestBody Flight message) {
        Flight sentFlight = dashboardProducerService.sendMessage(message, "SDQ");

        return Mono.just(sentFlight);
    }
}
