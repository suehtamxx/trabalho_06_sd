package org.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.util.Random;

public class Main {
    private static final String EXCHANGE_NAME = "topic_imagens";
    private static final String QUEUE_NAME = "fila_signs";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("rabbitmq_broker");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "sign");

        System.out.println(" [*] Consumidor de SINAIS aguardando mensagens...");
        Random random = new Random();

        String[] tiposSinais = {"Pare", "Velocidade Máxima 80km/h", "Proibido Virar à Esquerda", "Semáforo à frente"};

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Recebido: '" + message + "'");

            try {
                System.out.println(" [.] Processando imagem com IA 2 (Smile)...");

                // Simula o processamento da biblioteca Smile
                //double cpuLoad = smile.math.MathEx.factorial(15);

                String sinalClassificado = tiposSinais[random.nextInt(tiposSinais.length)];

                System.out.println("     => IA Identificou Sinal: " + sinalClassificado);

                // Atraso de 1 segundo para a fila encher
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                System.out.println(" [v] Processamento concluído.\n");
            }
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}