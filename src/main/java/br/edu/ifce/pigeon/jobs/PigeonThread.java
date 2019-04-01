package br.edu.ifce.pigeon.jobs;

import br.edu.ifce.pigeon.models.MailBox;
import br.edu.ifce.pigeon.views.IPigeonListener;

import java.util.concurrent.Semaphore;

import static br.edu.ifce.pigeon.views.IPigeonListener.AnimState.*;

public class PigeonThread extends Thread {
    private final IPigeonListener view;
    private final MailBox mailBox;
    private int max_capacity;
    private int load_time;
    private int unload_time;
    private int flight_time;
    private boolean alive;
    public Semaphore semaphore_pigeon;

    public PigeonThread(IPigeonListener view, MailBox mailBox) {
        this.view = view;
        this.mailBox = mailBox;
        this.alive = true;
    }

    public void init(int max_capacity, int load_time, int unload_time, int flight_time) {
        this.max_capacity = max_capacity;
        this.load_time = load_time * 1000;
        this.unload_time = unload_time * 1000;
        this.flight_time = flight_time * 1000;
        this.view.onChangeState(IPigeonListener.AnimState.LOADING);
        this.semaphore_pigeon = new Semaphore(0);

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
                    System.out.println("Dormindo");
                    semaphore_pigeon.acquire();

                }
                //mailBox.mutex.acquire();
                loadBox();
                //mailBox.mutex.release();
                fly(TRAVEL_LEFT_TO_RIGHT);
                unload_box();
                fly(TRAVEL_RIGHT_TO_LEFT);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadBox() {
        long elapsed = 0;

        while (this.alive && (elapsed < load_time)) {
            elapsed += 80;

            view.onChangeState(LOADING);
            view.onChangePosition(1);
            try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
            }
        }

        for (int i = 0; i < this.max_capacity; i++) {
            this.mailBox.take();
        }
    }

    private void unload_box() {
        int elapsed = 0;

        while (this.alive && (elapsed < unload_time)) {
            this.view.onChangeState(UNLOADING);
            this.view.onChangePosition(1);
            elapsed += 80;

            try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void fly(IPigeonListener.AnimState anim) {
        int elapsed = 0;

        while (this.alive && (elapsed < flight_time)) {
            view.onChangeState(anim);
            view.onChangePosition(elapsed / ((float) flight_time));
            elapsed += 80;

            try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getMaxCapacity() {
        return this.max_capacity;
    }

    public void wakeUp() {
        this.semaphore_pigeon.release();
    }
}
