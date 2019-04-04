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

    UserThread(IUsersListener view, MailBox mailBox, User user) {
        this.view = view;
        this.user = user;
        this.mailBox = mailBox;
        this.alive = true;
    }

    void fire() {
        this.alive = false;
    }

    @Override
    public void run() {
        while (this.alive) {
            write();
            if (mailBox.isFull())
                view.onRefreshStatus(user.getId(), User.Status.SLEEPING, 1);
            mailBox.put(this);
        }
    }

    private void write() {
        long elapsed = 0;
        long writeTime = getUser().getWriteTime() * 1000;

        while (this.alive && (elapsed < writeTime)) {
            view.onRefreshStatus(user.getId(), User.Status.WRITING, elapsed / ((float) writeTime));
            elapsed += 50;

            ThreadUtils.cpuBoundWait(50);
        }
    }

    public boolean isRunning() {
        return this.alive;
    }
    public User getUser() {
        return this.user;
    }
}

