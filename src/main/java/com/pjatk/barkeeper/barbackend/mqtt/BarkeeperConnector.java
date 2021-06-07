package com.pjatk.barkeeper.barbackend.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
public class BarkeeperConnector {
    private Map<String, String> barkeeperMessage;

    public Map<String, String> getBarkeeperMessage() {
        return barkeeperMessage;
    }

    public void setBarkeeperMessage(Map<String, String> barkeeperMessage) {
        this.barkeeperMessage = barkeeperMessage;
    }

    public MqttClient makeConnection() throws MqttException, IOException {

        String username = "sjnxrivd";
        String password = "FGbW4apCsMXN";
        String serverUri = "tcp://farmer.cloudmqtt.com:11538";
        String clientId = "SpringServer";

        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        MqttClient mqttClient = new MqttClient(serverUri, clientId);
        mqttClient.setCallback(callback());
        mqttClient.connect(options);

        mqttClient.subscribe("sd-112485-6665"); //TODO hardcoded valueeeeeeeee

        return mqttClient;
    }
    private MqttCallback callback(){
        return new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                //
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                String payload = new String(message.getPayload());
                System.out.println(payload);
                Map<String, String> mapToMessage = new HashMap<>();
                mapToMessage.put(topic, payload);

                setBarkeeperMessage(mapToMessage);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                //
            }
        };
    }

    //1:30;3:25;4:20
    public void sendMessageToBarkeeper (MqttClient client, String message) throws MqttException {
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());

        client.publish("barkeeper", mqttMessage);
    }
}
