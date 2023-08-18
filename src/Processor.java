public class Processor{
    static private int processorsNum =0 ;
    private int id;
    private boolean state ;

    public Processor() {
        this.state = false;
        processorsNum++;
        id = processorsNum;
        Simulator.idleProcessors.add(this);
    }

    public int getId() {
        return id;
    }

    public boolean isBusy() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
