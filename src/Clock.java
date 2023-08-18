public class Clock {
    private int counter;
    private static Clock instance = new Clock();
    private Clock() {
        counter = 0 ;
    }

    public static Clock getInstance() {
        return instance;
    }

    public int getCounter() {
        return counter;
    }

    public void incrementCounter() {
        this.counter++;
    }
}
