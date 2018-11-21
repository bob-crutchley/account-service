package com.qa.jms.reciever;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.qa.jms.domain.Offer;
import com.qa.jms.domain.Ticket;

@Component
public class AccountReceiver {

  private int ticketCount = 1;
  private int offerCount = 1;

  @JmsListener(destination = "TicketQueue", containerFactory = "myFactory")
  public void receiveMessage(Ticket ticket) {
    System.out.println("<" + ticketCount + "> Received <" + ticket	 + ">");
    ticketCount++;
  }
  
  @JmsListener(destination = "OfferQueue", containerFactory = "myFactory")
  public void receiveMessage(Offer offer) {
    System.out.println("<" + offerCount + "> Received <" + offer	 + ">");
    offerCount++;
  }
}
