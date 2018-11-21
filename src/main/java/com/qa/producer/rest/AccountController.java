package com.qa.producer.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.qa.producer.domain.Offer;
import com.qa.producer.domain.Ticket;

@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${url.ticket}")
	private String baseURL1;
	@Value("${url.offer}")
	private String baseURL2;

	@GetMapping("/processOffer")
	public void send() {
		Ticket ticket = restTemplate.getForObject(baseURL1 + "/random", Ticket.class);
		jmsTemplate.convertAndSend("TicketQueue", ticket);
		Offer offer = restTemplate.getForObject(baseURL2 + "/offer/" + ticket.getTicketNumber(), Offer.class);
		System.out.println("This is the prize info " + offer.toString());
		jmsTemplate.convertAndSend("OfferQueue", offer);
	}
}
