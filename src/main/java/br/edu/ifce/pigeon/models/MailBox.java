package br.edu.ifce.pigeon.models;

import br.edu.ifce.pigeon.jobs.PigeonThread;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.function.Consumer;

public class MailBox implements Iterable<Mail> {
    private BlockingQueue<Mail> queue;
    private PigeonThread pigeonThread;
    public static Semaphore mainBox;
    public static Semaphore mutex;

    public MailBox(int capacity) {
        mutex = new Semaphore(1);
        mainBox = new Semaphore(capacity);
        queue = new LinkedBlockingQueue<>();
    }

    public void setPigeonThread(PigeonThread pigeonThread) {
        this.pigeonThread = pigeonThread;
    }

    public void put(Mail m) {
        try {
            if (this.pigeonThread != null && (this.queue.size() % this.pigeonThread.getMaxCapacity() == 0)) {
                this.pigeonThread.wakeUp();
            }

            mainBox.acquire();
            queue.put(m);
            System.out.println(queue.size() + " - put -" + m.getUser().getId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Mail take() {
        mainBox.release();
        try {
            queue.take();
            System.out.println(getCount() + " - take");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Iterator<Mail> iterator() {
        return this.queue.iterator();
    }

    @Override
    public void forEach(Consumer<? super Mail> action) {
        this.queue.forEach(action);
    }

    @Override
    public Spliterator<Mail> spliterator() {
        return this.queue.spliterator();
    }

    public int getCount() {
        return this.queue.size();
    }
}
