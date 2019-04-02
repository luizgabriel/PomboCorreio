package br.edu.ifce.pigeon.jobs;

import br.edu.ifce.pigeon.models.MailBox;
import br.edu.ifce.pigeon.models.User;
import br.edu.ifce.pigeon.views.IPigeonController;

import java.util.HashMap;
import java.util.Map;

public class ThreadController {

    private MailBox mailBox;
    private PigeonThread pigeonThread;
    private Map<Integer, UserThread> userThreads;

    private IPigeonController pigeonController;

    private ThreadController() {
        this.userThreads = new HashMap<>();
    }

    public void setPigeonController(IPigeonController pigeonController) {
        this.pigeonController = pigeonController;

        if (pigeonThread == null) {
            this.pigeonController.enablePigeonCreation();
        } else {
            this.pigeonController.disablePigeonCreation();
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
            pigeonThread = new PigeonThread(this.pigeonController, this.mailBox);
            pigeonThread.init(maxCapacity, loadTime, unloadTime, flightTime);
            mailBox.setPigeonThread(pigeonThread);

            pigeonController.disablePigeonCreation();
        }
    }

    public void firePigeon() {
        if (this.pigeonThread != null) {
            this.pigeonThread.fire();
            this.pigeonController.enablePigeonCreation();

            this.pigeonThread = null;
        }
    }

    public void addUserThread(int writeTime) {
        User user = new User(writeTime);
        UserThread thread = new UserThread(this.mailBox, user);
        thread.start();
        this.userThreads.put(user.getId(), thread);
    }

    public void fireUser(User user) {
        UserThread thread = this.userThreads.remove(user.getId());
        thread.fire();
    }

    private static ThreadController instance;
    public static ThreadController getInstance() {
        if (instance == null) {
            instance = new ThreadController();
        }

        return instance;
    }
}
