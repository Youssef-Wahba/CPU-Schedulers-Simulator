package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class RoundRobinScheduler {
    private int time;
    private int numberOfProcesses;
    private ArrayList<Process> processes;
    private ArrayList<Process> readyQueue;
    private ArrayList<Process> doneProcesses;
    private float averageWaitingTime;
    private float averageTurnAroundTime;
    private int quantum;
    private int contextSwitch;
    private int q=0;

    //  parameterized constructor
    public RoundRobinScheduler(ArrayList<Process> processes,int numberOfProcesses, int quantum, int contextSwitch){
        processes.sort(new SortByArrival());
        this.processes = processes;
        this.numberOfProcesses = numberOfProcesses;
        this.doneProcesses = new ArrayList<Process>();
        this.readyQueue = new ArrayList<Process>();
        this.time = processes.get(0).getArrivalTime();
        this.averageWaitingTime = 0;
        this.averageTurnAroundTime = 0;
        this.quantum = quantum;
        this.contextSwitch = contextSwitch;
    }
//    getters

    public float getAverageTurnAroundTime() {
        return averageTurnAroundTime;
    }

    public ArrayList<Process> getReadyQueue() {
        return readyQueue;
    }

    public ArrayList<Process> getDoneProcesses() {
        return doneProcesses;
    }

    public float getAverageWaitingTime() {
        return averageWaitingTime;
    }

    public int getNumberOfProcesses() {
        return numberOfProcesses;
    }

    public int getTime() {
        return time;
    }

    public ArrayList<Process> getProcesses() {
        return processes;
    }

    public int getQuantum() {
        return quantum;
    }

    public int getContextSwitch() {
        return contextSwitch;
    }
    //    setters
    public void setContextSwitch(int contextSwitch) {
        this.contextSwitch = contextSwitch;
    }

    public void setNumberOfProcesses(int numberOfProcesses) {
        this.numberOfProcesses = numberOfProcesses;
    }

    public void setProcesses(ArrayList<Process> processes) {
        this.processes = processes;
    }

    public void setAverageTurnAroundTime(float averageTurnAroundTime) {
        this.averageTurnAroundTime = averageTurnAroundTime;
    }

    public void setAverageWaitingTime(float averageWaitingTime) {
        this.averageWaitingTime = averageWaitingTime;
    }

    public void setDoneProcesses(ArrayList<Process> doneProcesses) {
        this.doneProcesses = doneProcesses;
    }

    public void setReadyQueue(ArrayList<Process> readyQueue) {
        this.readyQueue = readyQueue;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }
    public void updateTurnAroundTime(int q){
        for(Process p : readyQueue){
            p.incrementTurnAroundTime(q + contextSwitch);
        }
    }

    public void updateWaitingTime(int q){
        if(readyQueue.size()>1){
            for (int i=1;i<readyQueue.size();i++){
                readyQueue.get(i).incrementWaitingTime(q);
            }
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

    public void calcAverageWaitingTime(){
        float total = 0;
        for (Process p: doneProcesses){
            total += p.getWaitingTime();
        }
        averageWaitingTime = total/numberOfProcesses;
    }

    public void calcAverageTurnAroundTime(){
        float total = 0;
        for (Process p: doneProcesses){
            total += p.getTurnAroundTime();
        }
        averageTurnAroundTime = total/numberOfProcesses;
    }
    void calcWaitingTime(){
        for (int i=0;i<doneProcesses.size();i++){
            doneProcesses.get(i).setWaitingTime((doneProcesses.get(i).getEndTime()-doneProcesses.get(i).getArrivalTime())-doneProcesses.get(i).getDevburstTime());
        }
    }
    void calcTurnAround(){
        for (int i=0;i<doneProcesses.size();i++){
            doneProcesses.get(i).setTurnAroundTime(doneProcesses.get(i).getEndTime()-doneProcesses.get(i).getArrivalTime());
        }
    }
    public void work() {
        updateReadyQueue();
        while (!readyQueue.isEmpty()) {
            int calc=readyQueue.get(0).decrementBurstTime(quantum);
            if (calc==0){
                time+=quantum+contextSwitch;
                updateReadyQueue();
                readyQueue.get(0).setEndTime(time);
                doneProcesses.add(readyQueue.get(0));
                readyQueue.remove(0);
            }else if(calc<0){
                calc*=-1;
                int t = quantum-calc;
                time+=t+contextSwitch;
                updateReadyQueue();
                readyQueue.get(0).setEndTime(time);
                doneProcesses.add(readyQueue.get(0));
                readyQueue.remove(0);
            }else{
                time+=quantum+contextSwitch;
                updateReadyQueue();
                readyQueue.add(readyQueue.remove(0));
            }
        }
        calcWaitingTime();
        calcTurnAround();
        calcAverageWaitingTime();
        calcAverageTurnAroundTime();
    }
    //3 1 5 p1 8 0 p2 2 0 p3 7 0 p4 3 0 p5 5 0
    // 3 1  p1 0 4 p2pp 1 8 p3 3 2 p4 10 6 p5 12 5
    public static void main(String[] args) {
        ArrayList<Process> list=new ArrayList<Process>();
        System.out.print("Enter quantum time : ");
        int q = new Scanner(System.in).nextInt();
        System.out.print("Enter context switch : ");
        int s = new Scanner(System.in).nextInt();
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
            list.add(new Process(pName,pArrive,pBurst,0));
        }
        RoundRobinScheduler rR =new RoundRobinScheduler(list,n,q,s);
        rR.work();
        System.out.println(rR.getDoneProcesses());
        System.out.println("Average turnaround time : " + rR.getAverageTurnAroundTime());
        System.out.println("Average waiting time : " + rR.getAverageWaitingTime());
    }

}
