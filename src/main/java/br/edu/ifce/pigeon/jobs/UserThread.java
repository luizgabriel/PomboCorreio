package br.edu.ifce.pigeon.jobs;

import java.util.concurrent.Semaphore;

import br.edu.ifce.pigeon.models.MailBox;
import br.edu.ifce.pigeon.models.User;
import br.edu.ifce.pigeon.models.Mail;

public class UserThread extends Thread {
    private final User user;
    private final MailBox mailBox;
    private boolean alive;

    private static Semaphore mutex = new Semaphore(1);

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
                mutex.acquire();
                // num_mail++
                this.mailBox.put(new Mail(this.user));
                // if (num_mail % N == 0){
                //    mutex.release();
                ///}
                mutex.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

