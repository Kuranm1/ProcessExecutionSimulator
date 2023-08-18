import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static boolean getPriority(String p) throws Exception {

        String priority= p.toLowerCase();

        if (priority.equals("high"))
            return true;
        else if (priority.equals("low"))
            return false;
        else
            throw new Exception("wrong task priority format");
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("configurations.txt"));

            int processorNumber = Integer.parseInt(reader.readLine()); // get the first line which contains the FIXED Processors number
            for (int i = 0; i < processorNumber; i++) {
                new Processor();
            }

            String line;
            while ((line = reader.readLine()) != null ){ // loop through each line and get the TASK information
                String[] parts = line.split(",");
                int creationTime = Integer.parseInt(parts[0]);
                int requestedTime = Integer.parseInt(parts[1]);
                boolean priority = getPriority(parts[2]);
                new Task(creationTime,requestedTime,priority);
            }

            reader.close();
            System.out.println("\n++ Welcome to Process Execution Simulator\n" +
                    "++ Mohammad AlQuraan\n" +
                    "++ The simulation begins :");
            Scheduler.loop(); // start scheduling process
        }

        catch (Exception e) {

            System.out.println("File is not found , or wrong configuration format");
        }


    }
}
