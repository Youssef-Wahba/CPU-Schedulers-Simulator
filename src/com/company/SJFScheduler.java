package com.company;

import java.util.Scanner;

public class SJFScheduler {
  public static void main(String[] args) {
    Scanner cin =new Scanner(System.in);
    int NProcesses,Arrival_time,BTime,Context;
    String Name;
    System.out.print("ENTER NUMBER OF PROCESSES");
    NProcesses=cin.nextInt();
    Process_[] arrayOfProcesses = new Process_[NProcesses];
    for(int i =0;i<NProcesses;i++)
    {
      System.out.print("ENTER ProcessName OF P"+(i+1) );
      Name=cin.next();
      System.out.print("ENTER ArrivalTime OF P"+(i+1) );
      Arrival_time=cin.nextInt();
      System.out.print("ENTER BurstTime OF P"+(i+1) );
      BTime=cin.nextInt();
      arrayOfProcesses[i]=new Process_( Name, Arrival_time,BTime,0);
    }
    System.out.print("ENTER CONTEXT SWITCH");
    Context=cin.nextInt();
    SJFScheduling(arrayOfProcesses,Context,NProcesses);
    System.out.println("THE TURNAROUND AVERAGE TIME IS  "+CalcAvgTurnaround(arrayOfProcesses,NProcesses));
    System.out.println("THE WAITING AVERAGE TIME IS  "+CalcAvgWaiting(arrayOfProcesses,NProcesses));
  }
  public static void swap(Process_[] arr, int FirstIndex, int SecondIndex)
  {
    Process_ temp=new Process_();
    temp=arr[FirstIndex];
    arr[FirstIndex]=arr[SecondIndex];
    arr[SecondIndex]=temp;
  }
  public static float CalcAvgTurnaround(Process_[] arr, int sizee)
  {
    float sum=0;
    for(int i=0;i<sizee;++i){sum+=arr[i].getTurnaroundTime();}
    return sum/sizee;
  }
  public static float CalcAvgWaiting(Process_[] arr, int sizee)
  {
    float sum=0;
    for(int i=0;i<sizee;++i){sum+=arr[i].getWaitingTime();}
    return sum/sizee;
  }
  public static void SortByArrival (Process_[] p, int size, int StartIndex)
  {
    Process_ temp;
    for(int i=StartIndex;i<size;i++) {
      for (int k =StartIndex; k < size; k++) {
        if (p[i].ArrivalTime< p[k].ArrivalTime) {
          temp = p[i];
          p[i] = p[k];
          p[k] = temp;
        }
      }
    }
  }
  public static void SJFScheduling(Process_[] arr, int ContextSwitch, int NOfProcesses)
  {
    SortByArrival(arr,NOfProcesses,0);
    Process_ p = new Process_();
    p = arr[0];
    for (int i=1;i<NOfProcesses;i++)
    {
      if (p.BurstTime>arr[i].BurstTime&&arr[i].ArrivalTime<= p.ArrivalTime)
      {
        swap(arr,0,i);
        p=arr[0];
      }
    }
    int CurrentTime = 0,CurrentExecution=0,swtich=0;
    while (p.RemainingTime > 0)
    {
      p.Remainingcnt();
      CurrentTime++;
      for (int i =CurrentExecution+1; i < NOfProcesses; i++) {arr[i].Waitingcnt();}
      for (int i = CurrentExecution+1; i < NOfProcesses; i++)
      {
        if (p.RemainingTime==0)
        {
          arr[CurrentExecution].ContextSwitch(ContextSwitch);
          arr[CurrentExecution]=p;
          p=arr[CurrentExecution+1];
          CurrentExecution++;
          swtich++;
        }
        else if (p.RemainingTime > arr[i].RemainingTime && arr[i].ArrivalTime <= CurrentTime)
        {
          arr[CurrentExecution]=p;
          swap(arr,CurrentExecution,i);
          p=arr[CurrentExecution];
          swtich++;
        }
      }
      if (swtich>0)
      {
        CurrentTime+=ContextSwitch;
        for (int i=CurrentExecution;i<NOfProcesses;++i)
        {
          arr[i].ContextSwitch(ContextSwitch);
        }
        SortByArrival(arr,NOfProcesses,CurrentExecution+1);
        swtich=0;
      }
      if (CurrentExecution==NOfProcesses-1&&p.RemainingTime==0)p.ContextSwitch(ContextSwitch);
    }
    for (int i=0;i<NOfProcesses;i++){arr[i].TurnaroundTimeCalc();}
    System.out.println("PROCESS NAME"+"   "+"WAITING TIME"+"   "+"TURNAROUND TIME");
    for(int i =0;i<NOfProcesses;i++)
    {
      System.out.println(arr[i].ProcessName+"   "+arr[i].getWaitingTime()+"   "+arr[i].getTurnaroundTime());
    }
  }
}