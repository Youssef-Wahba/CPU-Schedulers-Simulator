package com.company;

public class Process {
    private String name;
    private int arrivalTime;
    private int burstTime;
    private int priority;
    private int waitingTime;
    private int turnAroundTime;
    private int endTime;
    private int quantumTime;
    private int devburstTime;
    private int devQuantum;
//  default constructor
    Process(){
        this.name = "";
        this.arrivalTime = 0;
        this.burstTime = 0;
        this.priority = 0;
        this.waitingTime = 0;
        this.turnAroundTime = 0;
        this.endTime = 0;
    }
    //    for RR and priority
    Process(String name,int arrivalTime,int burstTime,int priority){
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.waitingTime = 0;
        this.turnAroundTime = 0;
        this.endTime = 0;
        this.devburstTime=this.burstTime;
        this.devQuantum=this.quantumTime;
    }
//    for AG
    Process(String name,int arrivalTime,int burstTime,int priority,int quantum){
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.waitingTime = 0;
        this.turnAroundTime = 0;
        this.quantumTime=quantum;
        this.endTime = 0;
        this.devburstTime=this.burstTime;
        this.devQuantum=this.quantumTime;
    }
//    getters


    public int getDevQuantum() {
        return devQuantum;
    }

    public int getDevburstTime() {
        return devburstTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public int getQuantumTime() {
        return quantumTime;
    }

    public int getTurnAroundTime() {
        return turnAroundTime;
    }
    public int getWaitingTime() {
        return waitingTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getPriority() {
        return priority;
    }

    public String getName() {
        return name;
    }
//    setters

    public void setDevQuantum(int devQuantum) {
        this.devQuantum = devQuantum;
    }

    public void setDevburstTime(int devburstTime) {
        this.devburstTime = devburstTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public void setQuantumTime(int quantumTime) {
        this.quantumTime = quantumTime;
    }

    public void setTurnAroundTime(int turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
//  rest of functions
    public boolean decrementBurstTime(){
        if(burstTime>0){
            burstTime--;
            return true;
        }
        return false;
    }

    public int decrementBurstTime(int c){
        for(int i=0;i<c;i++){
            burstTime--;
        }
        return burstTime;
    }

    public void decrementPriority(){
        if (priority>0) priority--;
    }

    public void incrementWaitingTime(){
        waitingTime++;
    }

    public void incrementWaitingTime(int c){
        waitingTime+=c;
    }

    public void incrementTurnAroundTime(){
        turnAroundTime++;
    }

    public void incrementTurnAroundTime(int c){
        turnAroundTime+=c;
    }
//  to string function for printing

//    @Override
//    public String toString() {
//        return "\nProcess{" +
//                "name='" + name + '\'' +
//                ", arrivalTime=" + arrivalTime +
//                ", burstTime=" + devburstTime +
//                ", priority=" + priority +
//                ", waitingTime=" + waitingTime +
//                ", turnAroundTime=" + turnAroundTime +
//                "}\n";
//    }

    @Override
    public String toString() {
        return "\nProcess{" +
                "name='" + name + '\'' +
                ", arrivalTime=" + arrivalTime +
                ", burstTime=" + devburstTime +
                ", priority=" + priority +
                ", waitingTime=" + waitingTime +
                ", turnAroundTime=" + turnAroundTime +
                ", endTime=" + endTime +
                ", quantumTime=" + quantumTime +
                "}\n";
    }
}
