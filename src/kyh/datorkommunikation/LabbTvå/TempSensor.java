package kyh.datorkommunikation.LabbTvå;

public class TempSensor {

    public int getRandomNumber(int high, int low){
        return (int) (Math.random() * (high - low)) + low;
    }
}
