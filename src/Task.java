public class Task  implements Comparable<Task> {
    static private int tasksNum = 0;
    private final int id;
    private final int creationTime;
    private final int requestedTime;
    private int completionTime;
    private final boolean priority ;
    private TaskState state;
    private int counter;
    private Processor p ;

    public Task(int creationTime , int requestedTime, boolean priority) {
        tasksNum++;
        this.id = tasksNum;
        this.creationTime = creationTime;
        this.requestedTime = requestedTime;
        this.priority = priority;
        this.state = TaskState.WAITING;
        Simulator.listOfTasks.add(this);
        counter = 0 ;
        p=null;

    }

    public int getId() {
        return id;
    }

    public int getCreationTime() {
        return creationTime;
    }

    public int getRequestedTime() {
        return requestedTime;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public boolean isHigh() {
        return priority;
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    public void incrementCounter(){
        counter++;
        if(counter==getRequestedTime()){
            setState(TaskState.COMPLETED);
            setCompletionTime(Simulator.clock.getCounter());
            p.setState(false);
            Simulator.idleProcessors.add(p);
            p=null;
        }

    }
    public void setProcessor(Processor p) {
        this.p = p ;
    }

    public Processor getProcessor (){
        return p;
    }


    @Override
    public int compareTo(Task o) {
        return o.getCreationTime() - this.getCreationTime();
    }

    @Override
    public String toString() {
        return "" +
                "Task(ID "+ id +")" +
                ", creationTime=" + creationTime +
                ", requestedTime=" + requestedTime +
                ", completionTime=" + completionTime +
                ", Priority=" + priority +
                ", state=" + state;
    }
}
