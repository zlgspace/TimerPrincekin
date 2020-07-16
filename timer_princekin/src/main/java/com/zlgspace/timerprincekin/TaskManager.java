package com.zlgspace.timerprincekin;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by zl on 2020/6/30.
 */
class TaskManager {

    private final static String TAG = "TaskManager";

    private static TaskManager instance;

    private ArrayList<TimeTask> tastList = new ArrayList<TimeTask>();

    private Object lock = new Object();

    private TaskManager(){

    }

    public static synchronized TaskManager getInstance(){
        if(instance == null){
            instance = new TaskManager();
        }
        return instance;
    }

    public boolean isEmpty(){
        synchronized (lock) {
            return tastList.isEmpty();
        }
    }

    public void checkWait() throws InterruptedException {
        synchronized (lock){
            if(tastList.isEmpty()) {
                Log.d(TAG,"waiting for tasks to join...");
                lock.wait();
            }
        }
    }

    public void addTask(TimeTask task){
        synchronized (lock){
            if(tastList.contains(task))
                tastList.remove(task);
            tastList.add(task);
            if(tastList.size()==1) {
                Log.d(TAG,"task to join, ready to wake up into the timing state.");
                lock.notifyAll();
            }
        }
    }

    public void removeTask(TimeTask task){
        synchronized (lock){
            if(!tastList.contains(task))
                return;
            tastList.remove(task);
        }
    }

    public void clear(){
        synchronized (lock){
            tastList.clear();
        }
    }

    public void executeTask(TimerEngine engine){
        synchronized (lock){
            long curTime = System.currentTimeMillis();
            Iterator<TimeTask> iterator = tastList.iterator();
            while (iterator.hasNext()){
                TimeTask item = iterator.next();
                long cd =item.getExeTime();
                if((cd - curTime)<=0){
                    item.preExecute();
                    engine.executeTask(item);
                    if(!item.isContinue()) {
                        Log.d(TAG,"Task:"+item+" completed,removed from the task list");
                        iterator.remove();
                    }
                }
            }
        }
    }

    public long computeTaskMinDelay(){
        synchronized (lock) {
            long small = Long.MAX_VALUE;
            long curTime = System.currentTimeMillis();
            for (int i = 0; i < tastList.size(); i++) {
                TimeTask item = tastList.get(i);
                long exeTime = item.getExeTime();
                if (small > (exeTime - curTime)) {
                    small = (exeTime - curTime);
                }
            }
            if (small < 0)
                small = 0;
            return small;
        }
    }




}
