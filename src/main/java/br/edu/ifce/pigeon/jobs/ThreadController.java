package br.edu.ifce.pigeon.jobs;

import br.edu.ifce.pigeon.models.MailBox;
import br.edu.ifce.pigeon.models.User;
import br.edu.ifce.pigeon.views.IPigeonController;
import br.edu.ifce.pigeon.views.IUsersController;

import java.util.HashMap;
import java.util.Map;

public class ThreadController {

    private MailBox mailBox;
    private PigeonThread pigeonThread;
    private Map<Integer, UserThread> userThreads;

    private IPigeonController pigeon;
    private IUsersController users;

    private ThreadController() {
        this.userThreads = new HashMap<>();
    }

    public void setPigeon(IPigeonController pigeon) {
        this.pigeon = pigeon;

        if (pigeonThread == null) {
            this.pigeon.enableCreation();
        } else {
            this.pigeon.disableCreation();
        }
    }

    public MailBox getMailBox() {
        return mailBox;
    }

    public void initMailBox(int maxCapacity) {
        this.mailBox = new MailBox(maxCapacity);
    }

    public void initPigeonThread(int maxCapacity, int loadTime, int unloadTime, int flightTime) {
        if (pigeonThread == null) {
            pigeonThread = new PigeonThread(this.pigeon, this.mailBox);
            pigeonThread.init(maxCapacity, loadTime, unloadTime, flightTime);
            mailBox.setPigeonThread(pigeonThread);

            pigeon.disableCreation();
        }
    }

    public void firePigeon() {
        if (this.pigeonThread != null) {
            this.pigeonThread.fire();
            this.pigeon.enableCreation();

            this.pigeonThread = null;
        }
    }

    public void addUserThread(int writeTime) {
        User user = new User(writeTime);
        UserThread thread = new UserThread(this.mailBox, user);
        thread.start();
        this.userThreads.put(user.getId(), thread);
    }

    public void fireUser(int userId) {
        UserThread thread = this.userThreads.remove(userId);
        thread.fire();
    }

    private static ThreadController instance;
    public static ThreadController getInstance() {
        if (instance == null) {
            instance = new ThreadController();
        }

        return instance;
    }

    public User getUser(int userId) {
        return this.userThreads.get(userId).getUser();
    }
}
