package kyh.datorkommunikation.LabbTvå;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public abstract class Controller implements MqttCallback {
    static String topic = "Filten/TempSensor";
    static int qos = 2;
    static String broker = "tcp://broker.hivemq.com:1883";
    static String clientId = "Controller";
    static MemoryPersistence persistence = new MemoryPersistence();

    static MqttClient sampleClient;

    public static void subscribeToMessage(){


        try {
            sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            sampleClient.connect(connOpts);
            sampleClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable throwable) {
                }

                @Override
                public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                    newPublish(mqttMessage.toString());
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) { }
            });
            sampleClient.subscribe(topic);

        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("except " + me);
            me.printStackTrace();
        }
    }

    public static void newPublish(String message) throws MqttException {

        String content = "";
        if(Integer.parseInt(message) < 22){
            content = "+";
        } else {
            content = "-";
        }
        MqttMessage newMessage = new MqttMessage(content.getBytes());
        newMessage.setQos(qos);
        sampleClient.publish("Filten/Controller", newMessage);
        System.out.println(message+content);
    }


    public static void main(String[] args) {
        subscribeToMessage();
    }
}


