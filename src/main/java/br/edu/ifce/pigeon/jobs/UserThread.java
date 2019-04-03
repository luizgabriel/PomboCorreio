package br.edu.ifce.pigeon.jobs;

import br.edu.ifce.pigeon.models.Mail;
import br.edu.ifce.pigeon.models.MailBox;
import br.edu.ifce.pigeon.models.User;
import br.edu.ifce.pigeon.views.IUsersListener;

public class UserThread extends Thread {
    private final IUsersListener view;
    private final User user;
    private final MailBox mailBox;
    private boolean alive;

    public UserThread(IUsersListener view, MailBox mailBox, User user) {
        this.view = view;
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
            write();

            if (mailBox.isFull())
                view.onRefreshStatus(user.getId(), User.Status.SLEEPING, 1);

            mailBox.put(new Mail(this.user));
        }
    }

    private void write() {
        int elapsed = 0;
        int writeTime = user.getWriteTime() * 1000;
        while (this.alive && (elapsed < writeTime)) {
            view.onRefreshStatus(user.getId(), User.Status.WRITING, elapsed / ((float) writeTime));
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

