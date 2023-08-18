import java.util.*;

public abstract class Simulator {
    protected static LinkedList<Task> listOfTasks = new LinkedList<Task>();
    protected static Queue<Task> highQueue = new ArrayDeque<Task>();
    protected static Queue<Task> lowQueue = new ArrayDeque<Task>();
    protected static Stack<Processor> idleProcessors = new Stack<Processor>();
    protected static Stack<Task> lowExecuting = new Stack<Task>();
    protected static Stack<Task> highExecuting = new Stack<Task>();
    protected static Stack<Task> completedTask = new Stack<Task>();
    protected static Clock clock = Clock.getInstance();

    static  public void printCycle(){
        System.out.println("------------------------------------------------  Cycle " +clock.getCounter()+" ------------------------------------------------");
        System.out.println("Number of idle processor: "+idleProcessors.size());
    }
    static public void printTaskStatus(Task t, String message){
//        printCycle();
        System.out.println("Task (ID:" +t.getId()+") "+message );

    }

    static public void printSummary(){
        System.out.println("\n------------------------------------------------ Summary ------------------------------------------------ ");
        for (Task t : completedTask) {
            System.out.println(t);
        }
    }

}
