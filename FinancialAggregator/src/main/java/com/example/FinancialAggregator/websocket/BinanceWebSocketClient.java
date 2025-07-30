package com.example.FinancialAggregator.websocket;

import com.example.FinancialAggregator.config.KafkaProducerConfig;

import javax.websocket.*;
import java.net.URI;

@ClientEndpoint
public class BinanceWebSocketClient {

    private KafkaProducerConfig kafkaProducer;

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected to Binance WebSocket");
    }

//    @OnMessage
//    public void onMessage(String message) {
//        System.out.println("Received: " + message);
//        ProducerRecord<String, String> record = new ProducerRecord<>("crypto-ticker", message);
//        kafkaProducer.send(record);
//
//    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("Connection closed: " + reason);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    public static void main(String[] args) {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        String uri = "wss://stream.binance.com:9443/ws/btcusdt@trade";

        try {
            container.connectToServer(BinanceWebSocketClient.class, URI.create(uri));
            // Keep app running to receive data
            Thread.sleep(Long.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

