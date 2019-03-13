package pigeon;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MailBox {
    private BlockingQueue<Mail> queue;
    private int maxCapacity;

    private static MailBox instance;

    private MailBox(int initialCapacity) {
        maxCapacity = initialCapacity;
        queue = new LinkedBlockingQueue<>(maxCapacity);
    }

    public void put(Mail m) throws InterruptedException {
        queue.put(m);
    }

    public Mail take() throws InterruptedException {
        return queue.take();
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
