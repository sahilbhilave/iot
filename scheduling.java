import java.io.*;
import java.util.*;

class process implements Comparable<process> {
    String name;
    int arrivalTime;
    int burstTime;
    int turnAroundTime;
    int completionTime;
    int waitTime;

    process(String name, int arrivalTime, int burstTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }

    @Override
    public int compareTo(process other) {
        return Integer.compare(this.arrivalTime, other.arrivalTime);
    }

}

class scheduling {
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

            processes.add(new process("P" + i, at, bt));
        }

        int choice = -1;

        System.out.println("1.FCFS");
        System.out.println("2.RR");
        System.out.println("Enter your choice : ");
        choice = Integer.parseInt(br.readLine());

        switch (choice) {
            case 1:
                fcfs();
                break;
            case 2:
                RR();
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

    public void fcfs() {
        double sumTurnAround = 0;
        double sumWaitTime = 0;
        process p = processes.get(0);
        int completionTime = p.arrivalTime;

        Collections.sort(processes);
        for (int i = 0; i < processes.size(); i++) {

            p = processes.get(i);
            if (p.arrivalTime > completionTime) {
                completionTime = p.arrivalTime;
            }
            completionTime = completionTime + p.burstTime;
            p.completionTime = completionTime;
            p.turnAroundTime = p.completionTime - p.arrivalTime;
            p.waitTime = p.turnAroundTime - p.burstTime;

            sumTurnAround = sumTurnAround + p.turnAroundTime;
            sumWaitTime = sumWaitTime + p.waitTime;
        }

        avgTurnAroundTime = sumTurnAround / processes.size();
        avgWaitingTime = sumWaitTime / processes.size();

        display();
    }

    public void RR() throws NumberFormatException, IOException {
        double sumTurnAround = 0;
        double sumWaitTime = 0;
        List<Integer> arr = new ArrayList<Integer>();
        for (int i = 0; i < processes.size(); i++) {
            process p = processes.get(i);
            arr.add(p.burstTime);
        }

        int timeSlice;
        int processno = 1;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter Time Slice : ");
        timeSlice = Integer.parseInt(br.readLine());

        int completionTime = 0;
        completionTime = processes.get(0).arrivalTime;

        Queue<process> q = new LinkedList<process>();
        q.add(processes.get(0));

        while (!q.isEmpty() || processno < processes.size()) {
            process p = q.poll();
            System.out.println(p.name);

            for (int i = 0; i < timeSlice; i++) {
                if (p.burstTime > 0) {
                    p.burstTime = p.burstTime - 1;
                    completionTime++;
                } else {
                    break;
                }

                for (int j = processno; j < processes.size(); j++) {
                    process pro = processes.get(j);
                    if (pro.arrivalTime <= completionTime) {
                        q.add(pro);
                        processno++;
                    }
                }
            }
            if (p.burstTime == 0) {
                p.completionTime = completionTime;
            }

            if (p.burstTime > 0) {
                q.add(p);
            }
        }

        for (int i = 0; i < arr.size(); i++) {
            process p = processes.get(i);
            p.burstTime = arr.get(i);
            p.turnAroundTime = p.completionTime - p.arrivalTime;
            p.waitTime = p.turnAroundTime - p.burstTime;

            sumTurnAround = sumTurnAround + p.turnAroundTime;
            sumWaitTime = sumWaitTime + p.waitTime;
        }

        avgTurnAroundTime = sumTurnAround / processes.size();
        avgWaitingTime = sumWaitTime / processes.size();
        display();

    }

    public static void main(String[] args) throws NumberFormatException, IOException {
        scheduling sc = new scheduling();
        sc.getInput();
        // sc.fcfs();
        // sc.display();

    }
}