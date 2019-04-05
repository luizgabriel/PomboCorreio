package br.edu.ifce.pigeon.jobs;

import br.edu.ifce.pigeon.jobs.PigeonThread;
import br.edu.ifce.pigeon.jobs.UserThread;
import br.edu.ifce.pigeon.models.Mail;
import br.edu.ifce.pigeon.presenters.IMailBoxListener;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.function.Consumer;

public class MailBox implements Iterable<Mail> {
    private int maxCapacity;
    private final IMailBoxListener listener;
    private BlockingQueue<Mail> queue;
    private PigeonThread pigeonThread;
    private Semaphore mainBox;

    public MailBox(int capacity, IMailBoxListener listener) {
        maxCapacity = capacity;
        this.listener = listener;
        mainBox = new Semaphore(maxCapacity);
        queue = new LinkedBlockingQueue<>();

        this.listener.onChange(0, maxCapacity);
    }

    public void setPigeonThread(PigeonThread pigeonThread) {
        this.pigeonThread = pigeonThread;
    }

    public void put(UserThread thread) {
        try {

            if (thread.isRunning()) {
                mainBox.acquire();
                queue.put(new Mail(thread.getUser()));
            }

            if (pigeonThread != null && (queue.size() % pigeonThread.getMaxCapacity() == 0)) {
                pigeonThread.wakeUp();
            }

            listener.onChange(getCount(), getMaxCapacity());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void take() {
        try {
            queue.take();
            listener.onChange(getCount(), getMaxCapacity());
            mainBox.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public boolean isFull() {
        return getCount() >= getMaxCapacity();
    }
}
