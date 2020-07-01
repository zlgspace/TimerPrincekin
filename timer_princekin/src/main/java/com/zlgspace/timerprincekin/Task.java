package com.zlgspace.timerprincekin;

/**
 * Created by zl on 2020/7/1.
 */
public class Task implements ITask {

    //该属性暂时无效
    private int exeThreadType = TYPE_EXE_ON_ASYNC_THREAD;

    protected int curExeCount = 0;

    long exeTime;

    @Override
    public void execute() {

    }

    public int getExeThreadType() {
        return exeThreadType;
    }

    public void setExeThreadType(int type) {
        this.exeThreadType = type;
    }

    long getExeTime(){
        return exeTime;
    }

    void updateExeTime(long delay){
        exeTime = delay + System.currentTimeMillis();
    }
}
