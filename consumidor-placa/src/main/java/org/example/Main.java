package org.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.util.Random;

public class Main {
    private static final String EXCHANGE_NAME = "topic_imagens";
    private static final String QUEUE_NAME = "fila_placas";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("rabbitmq_broker"); // Já configurado para o Docker

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "plate");

        System.out.println(" [*] Consumidor de PLACAS aguardando mensagens...");
        Random random = new Random();
        String[] tiposVeiculo = {"Carro", "Moto", "Caminhão"};

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Recebido: '" + message + "'");

            try {
                System.out.println(" [.] Processando imagem com IA 1 (Smile)...");
                double cpuLoad = smile.math.MathEx.factorial(15);

                // Simula a classificação da IA
                String tipoClassificado = tiposVeiculo[random.nextInt(tiposVeiculo.length)];
                String letrasPlaca = "ABC";
                int numerosPlaca = 1000 + random.nextInt(9000); // Gera números de 1000 a 9999

                System.out.println("     => IA Identificou: Placa " + letrasPlaca + "-" + numerosPlaca + " | Categoria: " + tipoClassificado);

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