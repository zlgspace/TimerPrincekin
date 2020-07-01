package com.zlgspace.timerprincekin.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.zlgspace.timerprincekin.R;
import com.zlgspace.timerprincekin.TimeTask;
import com.zlgspace.timerprincekin.TimerPrincekin;
import com.zlgspace.timerprincekin.TimerTask;

import java.util.Date;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    private TimeTask loopTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //必须先start，一般建议放到application中调用，不添加任务也不会有影响，会自动进入阻塞状态，直到有任务添加
        TimerPrincekin.start();


         //计时任务，间隔多久后执行
        findViewById(R.id.timeBtn).setOnClickListener(view -> TimerPrincekin.addTask(new TimeTask(10*1000,timeTask)));

        //定时任务，某一时间后执行
        findViewById(R.id.timerBtn).setOnClickListener(view -> {
            long time = System.currentTimeMillis()+15*1000;
            TimerPrincekin.addTask(new TimerTask(new Date(time),timerTask));
        });

        findViewById(R.id.timeLoopBtn).setOnClickListener(view -> {
            loopTask = new TimeTask(1*1000,timeLoopTask);
            loopTask.setExeCount(0);
            TimerPrincekin.addTask(loopTask);
        });

        findViewById(R.id.rmvTimeLoopBtn).setOnClickListener(view -> TimerPrincekin.removeTask(loopTask));

        findViewById(R.id.rmvAllBtn).setOnClickListener(view -> TimerPrincekin.removeAllTask());

    }

    Runnable timeTask = () -> Log.d("MainActivity","timeTask  exe");

    Runnable timerTask = () -> Log.d("MainActivity","timerTask  exe");

    Runnable timeLoopTask = () -> Log.d("MainActivity","************timeLoopTask  exe**************");
}
