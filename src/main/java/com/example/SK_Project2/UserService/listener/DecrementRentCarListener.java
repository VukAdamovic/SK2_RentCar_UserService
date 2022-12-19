package com.example.SK_Project2.UserService.listener;

import com.example.SK_Project2.UserService.dto.DecrementRentCarDto;
import com.example.SK_Project2.UserService.dto.IncrementRentCarDto;
import com.example.SK_Project2.UserService.messageHelper.MessageHelper;
import com.example.SK_Project2.UserService.service.ClientService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class DecrementRentCarListener {

    private MessageHelper messageHelper;
    private ClientService clientService;

    public DecrementRentCarListener(MessageHelper messageHelper, ClientService clientService) {
        this.messageHelper = messageHelper;
        this.clientService = clientService;
    }

    @JmsListener(destination = "${destination.decrementRentCar}", concurrency = "5-10")
    public void decrementRentCar(Message message) throws JMSException {
        DecrementRentCarDto decrementRentCarDto = messageHelper.getMessage(message,DecrementRentCarDto.class);
        System.out.println(decrementRentCarDto);
        clientService.decrementRentCar(decrementRentCarDto.getId(), decrementRentCarDto.getDays());
    }

}
