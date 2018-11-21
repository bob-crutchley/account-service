package com.qa.jms.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.qa.jms.domain.Offer;
import com.qa.jms.domain.Ticket;

@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/processOffer")
	public void send() {
		Ticket ticket = restTemplate.getForObject("http://localhost:8083/random", Ticket.class);
		jmsTemplate.convertAndSend("TicketQueue", ticket);
		Offer offer = restTemplate.getForObject("http://localhost:8082/offer/" + ticket.getTicketNumber(), Offer.class);
		System.out.println("This is the prize info " + offer.toString());
		jmsTemplate.convertAndSend("OfferQueue", offer);
	}
}
