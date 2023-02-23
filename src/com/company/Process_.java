package com.company;
public class Process_
{
    public String ProcessName;
    public int ArrivalTime;
    public int BurstTime;
    public int ProcessPriority;
    public int WaitingTime=0;
    private int TurnaroundTime;
    public int RemainingTime;
    public Process_()
    {
        this.ProcessName="Default";
        this.ArrivalTime=0;
        this.BurstTime=0;
        this.ProcessPriority=0;
        RemainingTime=0;
        WaitingTime=0;
    }
    public Process_(String ProcessName, int ArrivalTime, int BurstTime, int ProcessPriority)
    {
        this.ProcessName=ProcessName;
        this.ArrivalTime=ArrivalTime;
        this.BurstTime=BurstTime;
        this.ProcessPriority=ProcessPriority;
        RemainingTime=BurstTime;
        WaitingTime=0;
    }
    public int getArrivalTime() {return ArrivalTime;}
    public int getBurstTime(){return BurstTime;}
    public void Waitingcnt(){++WaitingTime;}
    public void Remainingcnt(){--RemainingTime;}
    public void ContextSwitch(int x){WaitingTime+=x;}
    public void TurnaroundTimeCalc() {  TurnaroundTime= (WaitingTime-ArrivalTime)+BurstTime;}
    public int getTurnaroundTime(){return TurnaroundTime;}
    public int getWaitingTime() {return WaitingTime-ArrivalTime;}
}
