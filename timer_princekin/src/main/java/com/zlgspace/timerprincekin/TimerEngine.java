package com.zlgspace.timerprincekin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class TimerEngine extends Thread {

    private static TimerEngine mTimerEngine;

    private static boolean isRunning = false;

    private ExecutorService executorService;//这里暂时先用单线程池

    public TimerEngine(){
        executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public void run() {
        try {
            while (isRunning){
                TaskManager.getInstance().checkWait();//没有任务时，引擎直接处于等待状态
                long delay = TaskManager.getInstance().computeTaskMinDelay();//获取当前任务中最小的等待时间
                mSleep(delay);//设置休眠
                TaskManager.getInstance().executeTask(mTimerEngine);//执行当前时刻可以执行的任务
            }
        } catch (Exception e) {
            e.printStackTrace();
            if(isRunning){//异常停止后，重新启动
                mTimerEngine = null;
                startEngine();
            }
        }
    }

    public void executeTask(final ITask task){
        if(task==null)
            return;
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                task.execute();
            }
        });
    }


    //启动引擎
    public static void startEngine(){
        if(mTimerEngine!=null&&mTimerEngine.isAlive())
            return;
        isRunning = true;
        mTimerEngine = new TimerEngine();
        mTimerEngine.setPriority(Thread.MAX_PRIORITY);//优先级设置为最高
        mTimerEngine.start();
    }

    //停止引擎
    public static void stopEngine(){
        isRunning = false;
        if(mTimerEngine!=null&&mTimerEngine.isAlive()){
            mTimerEngine.interrupt();
        }
    }

    /**
     * 打断当前休眠，重新计算休眠时间
     */
    public static void update(){
        if(mTimerEngine!=null&&mTimerEngine.isAlive()){
            mTimerEngine.interrupt();
        }
    }

    public void mSleep(long time){
        try {
            sleep(time);
        } catch (InterruptedException e) {
//            e.printStackTrace();
        }
    }
}
