package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class NonPreempriveScheduling {
    private int time;
    private int numberOfProcesses;
    private ArrayList<Process> processes;
    private ArrayList<Process> readyQueue;
    private ArrayList<Process> doneProcesses;
    private float averageWaitingTime;
    private float averageTurnAroundTime;
    public NonPreempriveScheduling(ArrayList<Process> processes,int numberOfProcesses){
        this.processes = processes;
        this.numberOfProcesses = numberOfProcesses;
        this.doneProcesses = new ArrayList<Process>();
        this.readyQueue = new ArrayList<Process>();
        this.time = processes.get(0).getArrivalTime();
        this.averageWaitingTime = 0;
        this.averageTurnAroundTime = 0;
    }
    public float getAverageTurnAroundTime() {
        return averageTurnAroundTime;
    }

    public ArrayList<Process> getDoneProcesses() {
        return doneProcesses;
    }

    public float getAverageWaitingTime() {
        return averageWaitingTime;
    }
    public void updateTurnAroundTime(){

        for(Process p : readyQueue){
            p.incrementTurnAroundTime();
        }
    }

    public void updateWaitingTime(){

        if(readyQueue.size()>1){
            for (int i=1;i<readyQueue.size();i++){
                readyQueue.get(i).incrementWaitingTime();
            }
        }
    }
    public void updateReadyQueue(){
        for (int i=0;i<processes.size();i++) {
            Process p =processes.get(i);
            if (p.getArrivalTime() == time) {
                readyQueue.add(p);
                processes.remove(i);
            }
        }
        readyQueue.sort(new SortByArrival());
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
    public void work() {
        updateReadyQueue();

        while (!readyQueue.isEmpty()) {
            if (!readyQueue.get(0).decrementBurstTime()){
                doneProcesses.add(readyQueue.get(0));
                readyQueue.remove(0);
            }
            time++;
            updateTurnAroundTime();
            updateWaitingTime();
            updateReadyQueue();
        }
        calcAverageWaitingTime();
        calcAverageTurnAroundTime();
    }
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
            list.add(new Process(pName,pArrive,pBurst,pPriority));
        }
        list.sort(new SortByArrival());
        NonPreempriveScheduling p =new NonPreempriveScheduling(list,n);
        p.work();
        System.out.println(p.getDoneProcesses());
        System.out.println("Average turnaround time : " + p.getAverageTurnAroundTime());
        System.out.println("Average waiting time : " + p.getAverageWaitingTime());
    }

}
