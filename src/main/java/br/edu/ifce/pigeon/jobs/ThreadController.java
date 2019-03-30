package br.edu.ifce.pigeon.jobs;

import br.edu.ifce.pigeon.models.MailBox;
import br.edu.ifce.pigeon.models.MailBox;
import br.edu.ifce.pigeon.models.User;
import br.edu.ifce.pigeon.views.IPigeonController;

import java.util.HashMap;
import java.util.Map;

public class ThreadController {

    private final IPigeonController pigeonController;
    private MailBox mailBox;
    private PigeonThread pigeonThread;
    private Map<Integer, UserThread> userThreads;

    public ThreadController(IPigeonController pigeonController) {
        this.pigeonController = pigeonController;
        this.userThreads = new HashMap<>();
    }

    public MailBox getMailBox() {
        return mailBox;
    }

    public void initMailBox(int maxCapacity) {
        this.mailBox = new MailBox(maxCapacity);
    }

    public void initPigeonThread(int maxCapacity, int loadTime, int unloadTime, int flightTime) {
        this.firePigeon();

        this.pigeonThread = new PigeonThread(this.pigeonController, this.mailBox);
        this.pigeonThread.init(maxCapacity, loadTime, unloadTime, flightTime);
        this.mailBox.setPigeonThread(this.pigeonThread);
    }

    public void firePigeon() {
        if (this.pigeonThread != null) {
            this.pigeonThread.fire();
        }
    }

    public User initUserThread(int writeTime) {
        User user = new User(writeTime);
        UserThread thread = new UserThread(this.mailBox, user);
        this.userThreads.put(user.getId(), thread);

        return user;
    }

    public void fireUser(User user) {
        UserThread thread = this.userThreads.remove(user.getId());
        thread.fire();
    }
}
