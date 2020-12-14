package kyh.datorkommunikation.LabbTvå;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Logger implements MqttCallback {

    static String topic = "Filten";
    static int qos = 2;
    static String broker = "tcp://broker.hivemq.com:1883";
    static String clientId = "Logger";
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
                    LocalDateTime dateTime = LocalDateTime.now();
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                    writeFile(dateTime.format(format), s, mqttMessage.toString());
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) { }
            });
            sampleClient.subscribe("Filten/TempSensor");
            sampleClient.subscribe("Filten/Controller");

        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("except " + me);
            me.printStackTrace();

        }

    }
    public static void main(String[] args) {
        subscribeToMessage();
    }

    public static void writeFile(String date,String source, String value) throws IOException {
        System.out.println(date+" " + source +" "+ value);
        Writer output = new BufferedWriter(new FileWriter("logg.txt",true));
        try{
            output.write(">>> "+date+ ", " + source +", " + value + " <<<\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            output.close();
        }
    }
}



