import java.util.Collections;
import java.util.Stack;

public abstract class Scheduler extends Simulator {


    private static Stack<Task> sortedTasks = new Stack<Task>(); // to store the sorted tasks by creation time

     static public void sortTasks(){  // sort all task based on creation time then put them in a Stack.
         Collections.sort(listOfTasks);
         sortedTasks.addAll(listOfTasks);
    }

    public static void addToQueue(Task t) { // add WAITING task to Queue depend on its Priority
        if (t.isHigh())
            highQueue.add(t);
        else
            lowQueue.add(t);
    }

    public static boolean isThereIdleProcessors(){ // check if there is idle processor
         return !idleProcessors.empty();
    }

    public static boolean isThereHighQueue(){ // check if there is task in high queue
         return !highQueue.isEmpty();
    }

    public static boolean isThereLowQueue(){ // check if there is task in low queue
        return !lowQueue.isEmpty();
    }

    public static boolean isThereAssignedLowTask() { // check if there is low task assigned to INTERRUPT
         return !lowExecuting.isEmpty();
    } // used in INTERRUPT

    public static void addToCompletedTasks(Stack<Task> execTask){ // loop through EXECUTING tasks and check if there is COMPLETED tasks to add
        Stack<Task> temp = new Stack<Task>();
        while(!execTask.empty()){
            Task t = execTask.pop();
            if (t.getState()!=TaskState.COMPLETED){
                temp.add(t); // Hold all the executing tasks in temp
            }
            else{
                completedTask.add(t); // Add the completed task in the stack
                printTaskStatus(t,"is completed");
            }

        }
        execTask.addAll(temp); // return the executing tasks from temp
        temp.clear();
    }

    public static void assignToProcessor() { // assign tasks in the queues to idle processor and set the state to EXECUTING

         while (isThereHighQueue()) { // check the high priority WAITING tasks
             if (isThereIdleProcessors()) { // check if there is idle processor
                 Processor p = idleProcessors.pop();
                 p.setState(true);
                 Task t = highQueue.poll();
                 t.setState(TaskState.EXECUTING);
                 t.setProcessor(p);
                 highExecuting.add(t);
                 printTaskStatus(t,"Starts executing in Processor(ID:"+p.getId()+")");
             }
             else if (isThereAssignedLowTask()){ // INTERRUPT in the case of no idle processors
                 Task t = lowExecuting.pop();
                 printTaskStatus(t,"Interrupted and returned to the Queue");
                 lowQueue.add(t);
                 Processor p = t.getProcessor();
                 p.setState(false);
                 idleProcessors.add(p);
             }
             else
                 break;
         }

         while (isThereLowQueue()){ // check the low priority WAITING tasks
             if (isThereIdleProcessors()){
                 Processor p = idleProcessors.pop();
                 p.setState(true);
                 Task t = lowQueue.poll();
                 t.setState(TaskState.EXECUTING);
                 t.setProcessor(p);
                 lowExecuting.add(t);
                 printTaskStatus(t,"Starts executing in Processor(ID:"+p.getId()+")");
             }
             else
                 break;
         }
    }

     public static void loop(){ // Start the scheduling process

         sortTasks(); // sort tasks based on creation time

         int cycle;

         while(true){

             cycle = clock.getCounter(); // get cycle from the clock

             printCycle(); // print the cycle number on the screen

             while (!sortedTasks.empty()&&sortedTasks.peek().getCreationTime() == cycle){ // check if there is a task created at the cycle time
                 printTaskStatus(sortedTasks.peek(),"Created and added to the Queue");
                 addToQueue(sortedTasks.pop()); // add the task to Queue
             }

             addToCompletedTasks(lowExecuting); // Add task to completed if it's finished executing
             addToCompletedTasks(highExecuting);

             assignToProcessor(); // assign queued tasks to idle processors

             for (Task t: highExecuting) {
                 t.incrementCounter(); // clock cycle increment for each exec task
             }

             for (Task t: lowExecuting) {
                 t.incrementCounter();
             }

             clock.incrementCounter();

             if (completedTask.size()==listOfTasks.size() && lowExecuting.isEmpty() && highExecuting.isEmpty()) {
                 printSummary(); // print the summary when the simulation is finished
                 break;
             }
         }
    }



}
