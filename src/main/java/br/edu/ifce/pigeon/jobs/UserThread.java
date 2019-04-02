package br.edu.ifce.pigeon.jobs;

import java.util.concurrent.Semaphore;

import br.edu.ifce.pigeon.models.MailBox;
import br.edu.ifce.pigeon.models.User;
import br.edu.ifce.pigeon.models.Mail;

public class UserThread extends Thread {
    private final User user;
    private final MailBox mailBox;
    private boolean alive;

    //private static Semaphore mutex = new Semaphore(1);

    public UserThread(MailBox mailBox, User user) {
        this.user = user;
        this.mailBox = mailBox;
        this.alive = true;
    }

    public void fire() {
        this.alive = false;
    }

    @Override
    public void run() {
        while (this.alive) {
            try {
                //mailBox.mutex.acquire();
                this.mailBox.put(new Mail(this.user));
                //mailBox.mutex.release();
                Thread.sleep(this.user.getWriteTime() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

