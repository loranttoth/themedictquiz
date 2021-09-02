package com.loranttoth.themedictquiz;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class TimerThread extends Thread {

    private NumberGameView gameView;
    private int counter;

    // flag to hold game state
    private boolean running;
    public void setRunning(boolean running) {
        this.running = running;
    }

    public TimerThread(NumberGameView gameView, int counter) {
        super();
        this.counter = counter;
        this.gameView = gameView;
    }

    @Override
    public void run() {

        while (running) {
            if (gameView == null){
                running = false;
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
            counter--;
            if (gameView != null)
                gameView.counterRefresh(counter);
            if (counter == 0){
                running = false;
                break;
            }

        }
        if (gameView != null)
            gameView.counterRefresh(counter);
    }

}