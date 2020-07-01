package com.zlgspace.timerprincekin;

import java.util.Date;

/**
 * Created by zl on 2020/6/30.
 * 定时任务，给定特定日期时间，执行任务
 */
public class TimerTask extends TimeTask {

    private Date date;

    public TimerTask(){

    }

    public TimerTask(Date date,Runnable task){
        setDate(date);
        setTask(task);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        computeDelay();
    }

    @Override
    public void setExeCount(int exeCount) {
        super.setExeCount(1);//定时任务只会执行一次，执行多次没有意义
    }

    void computeDelay(){
        long time = date.getTime();
        long curTime = System.currentTimeMillis();
        setDelay(time - curTime);
    }

}
