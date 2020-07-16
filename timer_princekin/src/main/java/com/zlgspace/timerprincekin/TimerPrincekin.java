package com.zlgspace.timerprincekin;

public class TimerPrincekin {

    public static void start(){
        TimerEngine.startEngine();
    }

    public static void stop(){
        TimerEngine.stopEngine();
        TaskManager.getInstance().clear();
    }

    public static void addTask(ITask task){
        if(task instanceof TimerTask){
            ((TimerTask) task).computeDelay();
        }else if(task instanceof TimeTask){
            ((TimeTask) task).updateExeTime(((TimeTask) task).getDelay());
        }else{
            Log.e("TimerPrincekin","Illegal task. The task must be either a TimerTask or a TimeTask.");
            return;
        }
        TaskManager.getInstance().addTask((TimeTask)task);
        TimerEngine.update();
    }

    public static void removeTask(ITask task){
        TaskManager.getInstance().removeTask((TimeTask)task);
        TimerEngine.update();
    }

    public static void removeAllTask(){
        TaskManager.getInstance().clear();
        TimerEngine.update();
    }

}
