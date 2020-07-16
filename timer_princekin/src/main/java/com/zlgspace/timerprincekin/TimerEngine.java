package com.zlgspace.timerprincekin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class TimerEngine extends Thread {

    private static TimerEngine mTimerEngine;

    private static boolean isRunning = false;

    private ExecutorService executorService;

    public TimerEngine(){
        executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public void run() {
//        try {
            while (isRunning){
                try {
                TaskManager.getInstance().checkWait();
                long delay = TaskManager.getInstance().computeTaskMinDelay();
                mSleep(delay);
                TaskManager.getInstance().executeTask(mTimerEngine);
                } catch (Exception e) {
                }
            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            if(isRunning){
//                mTimerEngine = null;
//                startEngine();
//            }
//        }
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


    public static void startEngine(){
        if(mTimerEngine!=null&&mTimerEngine.isAlive())
            return;
        isRunning = true;
        mTimerEngine = new TimerEngine();
        mTimerEngine.setPriority(Thread.MAX_PRIORITY);
        mTimerEngine.start();
    }

    public static void stopEngine(){
        isRunning = false;
        if(mTimerEngine!=null&&mTimerEngine.isAlive()){
            mTimerEngine.interrupt();
        }
    }

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
