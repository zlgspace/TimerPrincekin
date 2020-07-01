package com.zlgspace.timerprincekin;

/**
 * 统一管理定时或者计时任务，目前只支持异步任务执行
 */
public class TimerPrincekin {

    public static void start(){
        TimerEngine.startEngine();
    }

    public static void stop(){
        TimerEngine.stopEngine();
        //清空当前任务列表
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
        TimerEngine.update();//有任务添加时打断当前休眠，重新计算最小等待时间进行休眠
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
