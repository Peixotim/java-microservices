package com.ms.user.producers;

import com.ms.user.producers.dtos.EmailDto;
import com.ms.user.user.models.UserModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {

    final RabbitTemplate rabbitTemplate;

    public UserProducer(RabbitTemplate template){
        this.rabbitTemplate = template;
    }

    @Value(value = "${broker.queue.email.name}")
    private String routingKey;


    public void publishMessageEmail(UserModel userModel){
        var emailDTO = new EmailDto(
                userModel.getId(),
                userModel.getEmail(),
                "Cadastro realizado com sucesso!",
                userModel.getName() + ", seja bem vindo(a)! \nAgradecemos o seu cadastro, aproveite agora todos os recursos da nossa plataforma!"
        );

        rabbitTemplate.convertAndSend("",routingKey,emailDTO);
    }
}
