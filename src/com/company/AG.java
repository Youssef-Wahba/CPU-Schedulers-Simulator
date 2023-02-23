package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class AG {
    private ArrayList<Process> processes;
    private ArrayList<Process> readyQueue;
    private ArrayList<Process> doneQueue;
    private int numberOfProcesses;
    private int time;
    private float averageWaitingTime;
    private float averageTurnAroundTime;
    private Process runningProcess=new Process();
    public AG(ArrayList<Process> processes,int numberOfProcesses){
        this.numberOfProcesses=numberOfProcesses;
        processes.sort(new SortByArrival());
        this.time=processes.get(0).getArrivalTime();
        this.processes=processes;
        readyQueue=new ArrayList<Process>();
        doneQueue=new ArrayList<Process>();
        this.averageWaitingTime = 0;
        this.averageTurnAroundTime = 0;
    }

// getters
    public ArrayList<Process> getProcesses() {
        return processes;
    }

    public ArrayList<Process> getReadyQueue() {
        return readyQueue;
    }

    public ArrayList<Process> getDoneQueue() {
        return doneQueue;
    }

    public int getNumberOfProcesses() {
        return numberOfProcesses;
    }

    public int getTime() {
        return time;
    }
//    setters
    public void setProcesses(ArrayList<Process> processes) {
        this.processes = processes;
    }

    public void setReadyQueue(ArrayList<Process> readyQueue) {
        this.readyQueue = readyQueue;
    }

    public void setDoneQueue(ArrayList<Process> doneQueue) {
        this.doneQueue = doneQueue;
    }

    public void setNumberOfProcesses(int numberOfProcesses) {
        this.numberOfProcesses = numberOfProcesses;
    }

    public void setTime(int time) {
        this.time = time;
    }
//    rest of functions
void calcWaitingTime(){
    for (int i=0;i<doneQueue.size();i++){
        doneQueue.get(i).setWaitingTime((doneQueue.get(i).getEndTime()-doneQueue.get(i).getArrivalTime())-doneQueue.get(i).getDevburstTime());
    }
}

    void calcTurnAround(){
        for (int i=0;i<doneQueue.size();i++){
            doneQueue.get(i).setTurnAroundTime(doneQueue.get(i).getEndTime()-doneQueue.get(i).getArrivalTime());
        }
    }

public void updateReadyQueue(){
    for (int i=0;i<processes.size();i++) {
        Process p =processes.get(i);
        if (p.getArrivalTime() <= time) {
            readyQueue.add(p);
            processes.remove(i);
            i--;
        }
    }
}
    public boolean prioritySchedule(ArrayList<Process> readyQueue,int remainingQuantum){
        if(readyQueue.size()<=0)
            return false;
        System.out.println("\t-- Priority Schedule started on "+ runningProcess.getName() + " --");
        System.out.println("Process " + runningProcess.getName()+ " quantum time before : " + runningProcess.getQuantumTime());
        readyQueue.sort(new SortByPriority());

        if(runningProcess.getPriority() <= readyQueue.get(0).getPriority()){
            System.out.println("Process " + runningProcess.getName()+ " quantum time after : " + runningProcess.getQuantumTime());
            return false;
        }
        else {
            runningProcess.setQuantumTime(runningProcess.getQuantumTime()+(int)Math.ceil((float)remainingQuantum/2));
            System.out.println("Process " +runningProcess.getName()+ " quantum time after : " + runningProcess.getQuantumTime());
            readyQueue.add(runningProcess);
            runningProcess=readyQueue.get(0);
            readyQueue.remove(0);
            return true;
        }
    }

    public boolean SJFScheduling(ArrayList<Process> readyQueue,int remainingQuantum){
        if(readyQueue.size()<=0)
            return false;
        System.out.println("\t-- SJF Schedule started on "+ runningProcess.getName() + " --");
        System.out.println("Process " + runningProcess.getName()+ " quantum time before : " + runningProcess.getQuantumTime());
        readyQueue.sort(new SortByBurst());
        if(runningProcess.getBurstTime() <= readyQueue.get(0).getBurstTime()){
            System.out.println("Process " + runningProcess.getName()+ " quantum time after : " + runningProcess.getQuantumTime());
            return false;
        }
        else {
            runningProcess.setQuantumTime(runningProcess.getQuantumTime()+remainingQuantum);
            System.out.println("Process " + runningProcess.getName()+ " quantum time after : " + runningProcess.getQuantumTime());
            readyQueue.add(runningProcess);
            runningProcess=readyQueue.get(0);
            readyQueue.remove(0);
            return true;
        }
    }

    public void calcAverageWaitingTime(){
        float total = 0;
        for (Process p: doneQueue){
            total += p.getWaitingTime();
        }
        averageWaitingTime = total/numberOfProcesses;
    }

    public void calcAverageTurnAroundTime(){
        float total = 0;
        for (Process p: doneQueue){
            total += p.getTurnAroundTime();
        }
        averageTurnAroundTime = total/numberOfProcesses;
    }


    public void work(){
        updateReadyQueue();
        runningProcess=readyQueue.get(0);
        readyQueue.remove(0);
        int remainingQuantum;
        while(runningProcess!=null){
//            ceil 25% of FCFS
            System.out.println("\t-- FCFS Scheduling started on "+ runningProcess.getName()+" --");
            int q= (int) Math.ceil((float) runningProcess.getQuantumTime()/4);
            remainingQuantum = runningProcess.getQuantumTime()-q;
            runningProcess.setBurstTime(runningProcess.getBurstTime()-q);
            if(runningProcess.getBurstTime()<=0){
                time+=q-runningProcess.getBurstTime()*-1;
                updateReadyQueue();
                runningProcess.setQuantumTime(0);
                runningProcess.setEndTime(time);
                doneQueue.add(runningProcess);
                System.out.println("Processs " + runningProcess.getName() + "finished :)" );
                System.out.println(doneQueue);
                if(readyQueue.size()<=0)
                    break;
                runningProcess=readyQueue.get(0);
                readyQueue.remove(0);
                continue;
            }
            time+=q;
            updateReadyQueue();
            int q2;
//            ceil 25% of non-preemptive priority
            if(prioritySchedule(readyQueue,remainingQuantum)){
                continue;
            }
            else{
                q2= (int) Math.ceil((float) runningProcess.getQuantumTime()/4);
                remainingQuantum = runningProcess.getQuantumTime()-q2-q;
                runningProcess.setBurstTime(runningProcess.getBurstTime()-q2);
                if(runningProcess.getBurstTime()<=0){
                    time+=q2-runningProcess.getBurstTime()*-1;
                    updateReadyQueue();
                    runningProcess.setQuantumTime(0);
                    runningProcess.setEndTime(time);
                    doneQueue.add(runningProcess);
                    System.out.println("Processs " + runningProcess.getName() + "finished :)" );
                    System.out.println(doneQueue);
                    if(readyQueue.size()<=0)
                        break;
                    runningProcess=readyQueue.get(0);
                    readyQueue.remove(0);
                    continue;
                }
            }
            time+=q2;
            updateReadyQueue();
            int q3;
//            ceil 50% of non-preemptive priority
            if(SJFScheduling(readyQueue,remainingQuantum)){
                continue;
            }
            else{
                q3= (int) Math.ceil((float) runningProcess.getQuantumTime()/2);
                remainingQuantum = runningProcess.getQuantumTime()-q3-q-q2;
                runningProcess.setBurstTime(runningProcess.getBurstTime()-q3);
                if(runningProcess.getBurstTime()<=0){
                    time+=q3-runningProcess.getBurstTime()*-1;
                    updateReadyQueue();
                    runningProcess.setQuantumTime(0);
                    runningProcess.setEndTime(time);
                    doneQueue.add(runningProcess);
                    System.out.println("Processs " + runningProcess.getName() + "finished :)" );
                    System.out.println(doneQueue);
                    if(readyQueue.size()<=0)
                        break;
                    runningProcess=readyQueue.get(0);
                    readyQueue.remove(0);
                    continue;
                }else if(runningProcess.getBurstTime()>0 && remainingQuantum<=0){
                    time+=q3-runningProcess.getBurstTime()*-1;
                    updateReadyQueue();
                    runningProcess.setQuantumTime(runningProcess.getQuantumTime()+2);
                    readyQueue.add(runningProcess);
                }
                else{
                    time+=q3;
                    updateReadyQueue();
                }

            }
        }
        calcTurnAround();
        calcWaitingTime();
        calcAverageWaitingTime();
        calcAverageTurnAroundTime();
        System.out.println("All Processes done");
        System.out.println("Average tunraround and waiting time calculated :)");
        System.out.println("done queue : "+doneQueue);
        System.out.println("Average turnaround time : "+ averageTurnAroundTime);
        System.out.println("Average waiting time : "+ averageWaitingTime);
    }


//4 p1 0 17 4 7 p2 2 6 7 9 p3 5 11 3 4 p4 15 4 6 6
//  main
    public static void main(String[] args) {
            ArrayList<Process> list=new ArrayList<Process>();
            System.out.print("Enter number of processes : ");
            int n=new Scanner(System.in).nextInt();
            for(int i = 0; i < n; i++){
                System.out.println("Process " + (i+1) + " : ");
                Scanner in = new Scanner(System.in);
                System.out.print("Name : ");
                String pName = in.nextLine();
                System.out.print("Arrival time : ");
                int pArrive = in.nextInt();
                System.out.print("Burst time : ");
                int pBurst = in.nextInt();
                System.out.print("Priority : ");
                int pPriority = in.nextInt();
                System.out.print("Quantum time : ");
                int pQuantum = in.nextInt();
                list.add(new Process(pName,pArrive,pBurst,pPriority,pQuantum));
            }
            AG a=new AG(list,n);
            a.work();
    }
}
