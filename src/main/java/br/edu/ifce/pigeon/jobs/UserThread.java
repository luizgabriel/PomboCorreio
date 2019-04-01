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
                write();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void write() throws InterruptedException {
        int elapsed = 0;
        int writeTime = user.getWriteTime() * 1000;
        while (this.alive && (elapsed < writeTime)) {
            //view.onChangeState(anim);
            //view.onChangePosition(elapsed / ((float) writeTime));
            elapsed += 80;

            try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public User getUser() {
        return this.user;
    }
}

