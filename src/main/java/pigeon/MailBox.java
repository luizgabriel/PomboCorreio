package pigeon;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class MailBox {
    private BlockingQueue<Mail> queue;
    private int maxCapacity;

    private static MailBox instance;
    private static Semaphore mutex;

    private MailBox(int initialCapacity) {
        maxCapacity = initialCapacity;
        mutex = new Semaphore(initialCapacity);
        queue = new LinkedBlockingQueue<>(maxCapacity);
    }

    public void put(Mail m) {
        try {
            mutex.acquire();
            queue.put(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Mail take() {
        mutex.release();
        try {
            return queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getCapacity() {
        return this.maxCapacity;
    }

    public int getRemaningCapacity() {
        return this.queue.remainingCapacity();
    }

    public int size() {
        return this.queue.size();
    }

    public static MailBox getInstance() {
        if (instance == null) {
            instance = new MailBox(10);
        }

        return instance;
    }

}
