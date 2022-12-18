package com.example.SK_Project2.UserService.listener;

import com.example.SK_Project2.UserService.dto.IncrementRentCarDto;
import com.example.SK_Project2.UserService.messageHelper.MessageHelper;
import com.example.SK_Project2.UserService.service.ClientService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class IncrementRentCarListener {

    private MessageHelper messageHelper;
    private ClientService clientService;

    public IncrementRentCarListener(MessageHelper messageHelper, ClientService clientService) {
        this.messageHelper = messageHelper;
        this.clientService = clientService;
    }

    @JmsListener(destination = "${destination.incrementRentCar}", concurrency = "5-10")
    public void incrementRentCar(Message message) throws JMSException{

        IncrementRentCarDto incrementRentCarDto = messageHelper.getMessage(message,IncrementRentCarDto.class);
        System.out.println(incrementRentCarDto);
        clientService.incrementRentCar(incrementRentCarDto.getId(), incrementRentCarDto.getDays());
    }
}
