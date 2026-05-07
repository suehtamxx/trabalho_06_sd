package org.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Random;

public class Main {
    private static final String EXCHANGE_NAME = "topic_imagens";
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("rabbitmq_broker");
        
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        Random random = new Random();
        int count = 1;

        System.out.println(" [*] Iniciando envio de mensagens...");

        // loop infinito para gerar mensagens
        while(true) {
            // altera entre dois tipos de mensagens
            boolean isPlate = random.nextBoolean();

            // define a routingkey baseada no tipo (plate ou sign)
            String routingKey = isPlate ? "plate" : "sign";
            String message = "Simulacao de imagem de " + routingKey + " #" + count;

            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Enviado '" + routingKey + "':'" + message + "'");

            count++;

            Thread.sleep(200);
        }
    }
}