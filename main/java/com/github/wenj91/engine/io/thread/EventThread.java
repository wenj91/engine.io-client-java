package com.github.wenj91.engine.io.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * Created by wenj91 on 2016-12-23.
 */
public class EventThread extends Thread{

    private EventThread(Runnable runnable) {
        super(runnable);
    }

    public static void execTask(Runnable task){
        task.run();
    }
}
