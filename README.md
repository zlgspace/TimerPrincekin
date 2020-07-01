# TimerPrincekin
Android 或者JAVA 用的定时、计时任务执行管理器，直接添加对应任务既可以在指定时间或者间隔时间后执行，无需关注其他，可一次管理多个任务


//必须先start，一般建议放到application中调用，不添加任务也不会有影响，会自动进入阻塞状态，直到有任务添加
TimerPrincekin.start();


//添加定时任务
long time = System.currentTimeMillis()+15*1000;
TimerPrincekin.addTask(new TimerTask(new Date(time),timerTask));


//添加计时任务
TimerPrincekin.addTask(new TimeTask(10*1000,timeTask))


任务默认只执行一次，结束后会被自动从任务列表中移除，需要重新添加

不过可以通过，Task 的setExeCount 设置执行次数，设置参数小于等于0时表示该任务，无限循环执行

移除正在进行的任务
TimerPrincekin.removeTask(task)

移除所有等待任务
TimerPrincekin.removeAllTask()
