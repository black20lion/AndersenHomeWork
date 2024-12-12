package com.sahokia.kos.springhomeworkone;

import com.sahokia.kos.springhomeworkone.config.ApplicationContextConfig;
import com.sahokia.kos.springhomeworkone.model.TicketType;
import com.sahokia.kos.springhomeworkone.service.TicketService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringHomeWorkOneApplication {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationContextConfig.class);
        TicketService ticketService = context.getBean(TicketService.class);
        System.out.println(ticketService.getCustomerById(12));
        ticketService.saveCustomer("John");
        ticketService.saveTicket(16, TicketType.MONTH);
        ticketService.updateTicketType(12, TicketType.YEAR);
    }
}
