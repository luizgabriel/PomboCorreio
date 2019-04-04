package br.edu.ifce.pigeon.jobs;

import br.edu.ifce.pigeon.models.Mail;
import br.edu.ifce.pigeon.models.MailBox;
import br.edu.ifce.pigeon.models.User;
import br.edu.ifce.pigeon.views.IUsersListener;

public class UserThread extends Thread {
    public static final int FRAME_TICK = 10;
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
            mailBox.lock();
            if (this.alive)
                mailBox.put(new Mail(this.user));
            mailBox.unlock();
        }
    }

    private void write() {
        long tick = 0;
        long elapsed = 0;
        long last = 0;
        long writeTime = getUser().getWriteTime() * 1000;

        while (this.alive && (elapsed < writeTime)) {
            last = System.currentTimeMillis();
            if (tick > FRAME_TICK) {
                view.onRefreshStatus(user.getId(), User.Status.WRITING, elapsed / ((float) writeTime));

                elapsed += FRAME_TICK;
                tick = 0;
            }
            tick += (System.currentTimeMillis() - last);
        }
    }

    public User getUser() {
        return this.user;
    }
}

