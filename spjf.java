import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class process {
    String name = "";
    int arrivalTime = 0;
    int burstTime = 0;
    int completionTime = 0;
    int turnAroundTime = 0;
    int waitTime = 0;
    int priority = 0;

    process(String name, int arrivalTime, int burstTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }

    process(String name, int arrivalTime, int burstTime, int priority) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }
}

class spjf {

    public List<process> processes = new ArrayList<process>();
    double avgTurnAroundTime = 0;
    double avgWaitingTime = 0;

    public void getInput() throws NumberFormatException, IOException {
        System.out.println("Enter number of processes");
        int n = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());

        for (int i = 0; i < n; i++) {
            System.out.println("Enter arrival time : ");
            int at = Integer.parseInt(br.readLine());

            System.out.println("Enter burst time : ");
            int bt = Integer.parseInt(br.readLine());

            processes.add(new process("P" + (i+1), at, bt));
        }

        int choice = -1;

        System.out.println("1.SJF with preemption");
        System.out.println("2.Priority");
        System.out.println("Enter your choice : ");
        choice = Integer.parseInt(br.readLine());

        switch (choice) {
            case 1:
                srtf();
                break;
            case 2:
                priority();
                break;
        }

    }

    public void display() {
        System.out.println("Process Details:");
        System.out.println("Name Arrival-Time Burst-Time Completion-Time TurnaroundTime Waiting Time");
        for (int i = 0; i < processes.size(); i++) {
            process p = processes.get(i);
            System.out.printf("%-4s %-13d %-10d %-15d %-15d %-13d%n", p.name, p.arrivalTime, p.burstTime,
                    p.completionTime, p.turnAroundTime, p.waitTime);
        }
        System.out.println("Average Turnaround Time : " + avgTurnAroundTime);
        System.out.println("Average Waiting Time : " + avgWaitingTime);
    }

    public void displayPriority() {
        System.out.println("Process Details:");
        System.out.println("Name Arrival-Time Burst-Time Priority Completion-Time TurnaroundTime Waiting Time");
        for (int i = 0; i < processes.size(); i++) {
            process p = processes.get(i);
            System.out.printf("%-4s %-13d %-10d %-8d %-15d %-15d %-13d%n", p.name, p.arrivalTime, p.burstTime,p.priority, p.completionTime, p.turnAroundTime, p.waitTime);
        }
        System.out.println("Average Turnaround Time : " + avgTurnAroundTime);
        System.out.println("Average Waiting Time : " + avgWaitingTime);
    }

    public void srtf()
    {
        ArrayList<Integer> arr = new ArrayList<Integer>();
        for(int i=0;i<processes.size();i++)
        {
            arr.add(processes.get(i).burstTime);
        }

        

        ArrayList<process> queue = new ArrayList<>();
        process p;
        int counter = 0;
        
        counter = processes.get(0).arrivalTime;

        //Get the shortest arrival time process
        int k=0;
        for(int i=0;i<processes.size();i++)
        {
            if(processes.get(i).arrivalTime < counter){
                counter = processes.get(i).arrivalTime;
                k = i;
            }
        }

        queue.add(processes.get(k));
        System.out.println(counter+" "+k);
        int burst_time;
        while(!queue.isEmpty())
        {
            k=0;
            for(int i=0;i<processes.size();i++)
            {
                p = processes.get(i);
                if(p.arrivalTime == counter && !queue.contains(p))
                {
                    
                    queue.add(p);
                }
            }

            burst_time = queue.get(0).burstTime;
            for(int i=0;i<queue.size();i++)
            {
                if(queue.get(i).burstTime < burst_time)
                {
                    burst_time = queue.get(i).burstTime;
                    k = i;
                }
                
            }
            System.out.println(queue.get(k).name +" "+queue.get(k).burstTime);
            queue.get(k).burstTime--;

            if(queue.get(k).burstTime == 0)
            {
                for(int i=0;i<processes.size();i++)
                {
                    if(processes.get(i).name.equals(queue.get(k).name))
                    {
                        processes.get(i).completionTime = (counter+1);
                    }
                }
                queue.remove(queue.get(k));
            }

            

            counter++;
        }

        for(int i=0;i<processes.size();i++)
            {
                processes.get(i).burstTime = arr.get(i);
            }
        display();
    }

    public void priority() {
        process p;
        process q;
        process n;
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in)))
            {
        for(int i=0;i<processes.size();i++)
        {
            p = processes.get(i);
            System.out.println("Enter priority for "+p.name+" : ");
            p.priority = Integer.parseInt(br.readLine());
        }
        }catch(Exception e)
            {}
        displayPriority();
    }

    public static void main(String[] args) throws NumberFormatException, IOException {
        spjf s = new spjf();
        s.getInput();
    }

}