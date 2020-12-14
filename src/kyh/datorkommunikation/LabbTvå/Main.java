package kyh.datorkommunikation.LabbTvå;

import java.util.Timer;
import java.util.TimerTask;
/*
* Vi kör alla 3 program i samma projekt.

* Starta Main först
* Starta Controller sedan
* Starta Logger sist
* */
public class Main {

    public static void main(String[] args) {

        Timer myTimer = new Timer();
        TimerTask myTask = new TimerTask () {
            @Override
            public void run () {
                TempSensor newTemp = new TempSensor();
            }
        };

        myTimer.scheduleAtFixedRate(myTask , 0l, 60 * (1000)); // Runs every 1 minute
    }
}


