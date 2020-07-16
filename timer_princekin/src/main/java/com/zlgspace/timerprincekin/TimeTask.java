package com.zlgspace.timerprincekin;

import java.lang.ref.SoftReference;

/**
 * Created by zl on 2020/6/30.
 *
 * 计时任务，间隔多少毫秒之后，执行任务
 */
public class TimeTask extends Task{

    private long delay; //执行等待时间

    private SoftReference<Runnable> task;

    private int exeCount = 1;//<=0 loop execution,

    public TimeTask(){

    }

    public TimeTask(long delay, Runnable task){
        setDelay(delay);
        setTask(task);
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        if(delay<0)
            delay = 0;
        this.delay = delay;
        updateExeTime(delay);
    }

    public Runnable getTask() {
        if(task==null)
            return null;
        return task.get();
    }

    public void setTask(Runnable task) {
        this.task = new SoftReference<>(task);
    }

    public int getExeCount() {
        return exeCount;
    }

    public void setExeCount(int exeCount) {
        this.exeCount = exeCount;
    }

    public void execute(){
        if(task!=null&&task.get()!=null) {
            task.get().run();
        }
    }

    void preExecute(){
        curExeCount++;
        if((exeCount>0)&&curExeCount>=exeCount)
            return;
        updateExeTime(delay);
    }

    boolean isContinue(){
        if(task==null||task.get()==null) {
            return false;
        }

        if((exeCount>0)&&curExeCount>=exeCount)
            return false;

        return true;
    }
}
