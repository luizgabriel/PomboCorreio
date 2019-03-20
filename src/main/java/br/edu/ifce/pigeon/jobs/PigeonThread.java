package br.edu.ifce.pigeon.jobs;

import br.edu.ifce.pigeon.views.IPigeonController;
import br.edu.ifce.pigeon.models.MailBox;

import java.util.concurrent.Semaphore;

import static br.edu.ifce.pigeon.views.IPigeonController.AnimState.TRAVEL_LEFT_TO_RIGHT;

public class PigeonThread extends Thread {
    private int max_capacity;
    private int load_time;
    private int unload_time;
    private int flight_time;
    private IPigeonController pigeon;
    private boolean alive;

    private static Semaphore semaphore_pigeon = new Semaphore(0);
    private static Semaphore mutex = new Semaphore(1);

    public PigeonThread(int max_capacity, int load_time, int unload_time, int flight_time) {
        this.alive = true;
        this.max_capacity = max_capacity;
        this.load_time = load_time;
        this.unload_time = unload_time;
        this.flight_time = flight_time;
    }

    public void setPigeon(IPigeonController pigeon) {
        this.pigeon = pigeon;
    }

    public void fire() {
        this.alive = false;
    }

    @Override
    public void run() {
        while (this.alive) {
            try {
                semaphore_pigeon.acquire();
                mutex.acquire();

                for (int i = 1; i <= max_capacity; ++i) {
                    MailBox.getInstance().take();
                }

                mutex.release();
                fly_left_to_right();
                unload_box();
                fly_right_to_left();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void fly_right_to_left() {
        this.pigeon.refreshPigeonFrame(TRAVEL_LEFT_TO_RIGHT);
    }

    private void unload_box() {
    }

    private void fly_left_to_right() {
    }


}
