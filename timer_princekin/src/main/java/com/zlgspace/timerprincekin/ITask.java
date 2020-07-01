package com.zlgspace.timerprincekin;

public interface ITask {

    int TYPE_EXE_ON_MAIN_THREAD = 1;

    int TYPE_EXE_ON_ASYNC_THREAD = 2;

    void execute();
}
