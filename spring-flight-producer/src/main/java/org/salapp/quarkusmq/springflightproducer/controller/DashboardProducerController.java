package org.salapp.quarkusmq.springflightproducer.controller;

import org.salapp.quarkusmq.springflightproducer.service.DashboardProducerService;
import org.salapp.springkafka.springflightshared.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/producer")
public class DashboardProducerController {

    private final DashboardProducerService dashboardProducerService;

    @Autowired
    public DashboardProducerController(DashboardProducerService dashboardProducerService) {
        this.dashboardProducerService = dashboardProducerService;
    }

    @PostMapping
    public Flux<String> sendMessage(@RequestBody Flight message) {
        String messageSent = dashboardProducerService.sendMessage(message, "SDQ");

        return Flux.just(messageSent);
    }
}
