package br.edu.ifce.pigeon.jobs;

import br.edu.ifce.pigeon.models.MailBox;
import br.edu.ifce.pigeon.views.IPigeonListener;

import java.util.concurrent.Semaphore;

import static br.edu.ifce.pigeon.views.IPigeonListener.AnimState.*;

public class PigeonThread extends Thread {
    public static final int FRAME_TICK = 30;
    private final IPigeonListener view;
    private final MailBox mailBox;
    private int max_capacity;
    private int loadTime;
    private int unloadTime;
    private int flightTime;
    private boolean alive;
    private Semaphore semaphore;

    public PigeonThread(IPigeonListener view, MailBox mailBox) {
        this.view = view;
        this.mailBox = mailBox;
        this.alive = true;
    }

    public void init(int max_capacity, int loadTime, int unloadTime, int flightTime) {
        this.max_capacity = max_capacity;
        this.loadTime = loadTime * 1000;
        this.unloadTime = unloadTime * 1000;
        this.flightTime = flightTime * 1000;
        this.view.onChangeState(IPigeonListener.AnimState.LOADING);
        this.semaphore = new Semaphore(0);

        this.start();
    }

    public void fire() {
        this.alive = false;
    }

    @Override
    public void run() {
        while (this.alive) {
            try {
                if (mailBox.getCount() < getMaxCapacity()) {
                    view.onChangeState(LOADING);
                    view.onChangePosition(1);
                    semaphore.acquire();
                }

                loadBox();
                fly(TRAVEL_LEFT_TO_RIGHT);
                unloadBox();
                fly(TRAVEL_RIGHT_TO_LEFT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadBox() {

        long elapsed = 0;

        while (this.alive && (elapsed < loadTime)) {
            elapsed += 50;

            view.onChangeState(LOADING);
            view.onChangePosition(1);

            Count(50);
        }

        for (int i = 0; i < this.max_capacity; i++) {
            this.mailBox.take();
        }

        /*
        long last = System.currentTimeMillis();
        long elapsed = last;
        long mail_take = last;
        while (this.alive && (System.currentTimeMillis() - last < loadTime)){
            if (System.currentTimeMillis() - elapsed >= 80) {
                elapsed = System.currentTimeMillis();
                view.onChangeState(LOADING);
                view.onChangePosition(1);
            }
            if (System.currentTimeMillis() - mail_take >= (loadTime / max_capacity)*0.8) {
                mail_take = System.currentTimeMillis();
                this.mailBox.take();
            }
        }
        */
    }

    private void unloadBox() {
        int elapsed = 0;

        while (this.alive && (elapsed < unloadTime)) {
            this.view.onChangeState(UNLOADING);
            this.view.onChangePosition(1);
            elapsed += 50;
            Count(50);
        }
    }

    private void fly(IPigeonListener.AnimState anim) {
        int elapsed = 0;

        while (this.alive && (elapsed < flightTime)) {
            view.onChangeState(anim);
            view.onChangePosition(elapsed / ((float) flightTime));
            elapsed += 50;
            Count(50);
        }
    }

    public int getMaxCapacity() {
        return this.max_capacity;
    }

    public void wakeUp() {
        this.semaphore.release();
    }

    public static void Count (int time){
        boolean flag = true;

        long last = System.currentTimeMillis();
        while (flag){
            if( System.currentTimeMillis() - last >= time){
                flag = false;
            }
        }
    }
}
