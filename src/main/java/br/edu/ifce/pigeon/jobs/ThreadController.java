package br.edu.ifce.pigeon.jobs;

import br.edu.ifce.pigeon.models.MailBox;
import br.edu.ifce.pigeon.models.User;
import br.edu.ifce.pigeon.presenters.IMailBoxListener;
import br.edu.ifce.pigeon.views.IPigeonListener;
import br.edu.ifce.pigeon.views.IUsersListener;

import java.util.HashMap;
import java.util.Map;

public class ThreadController {

    private MailBox mailBox;
    private PigeonThread pigeonThread;
    private Map<Integer, UserThread> userThreads;

    private IPigeonListener pigeonListener;
    private IUsersListener usersListener;

    private ThreadController() {
        this.userThreads = new HashMap<>();
    }

    public void setPigeonListener(IPigeonListener pigeonListener) {
        this.pigeonListener = pigeonListener;

        if (pigeonThread == null) {
            this.pigeonListener.onCreated();
        } else {
            this.pigeonListener.onFired();
        }
    }

    public void setUsersListener(IUsersListener usersListener) {
        this.usersListener = usersListener;

        if (userThreads.size() > 0) {
            for (UserThread threads: userThreads.values()) {
                this.usersListener.onAdded(threads.getUser().getId());
            }
        }
    }

    public MailBox getMailBox() {
        return mailBox;
    }

    public void initMailBox(int maxCapacity, IMailBoxListener listener) {
        this.mailBox = new MailBox(maxCapacity, listener);
    }

    public void initPigeonThread(int maxCapacity, int loadTime, int unloadTime, int flightTime) {
        if (pigeonThread == null) {
            pigeonThread = new PigeonThread(this.pigeonListener, this.mailBox);
            pigeonThread.init(maxCapacity, loadTime, unloadTime, flightTime);
            mailBox.setPigeonThread(pigeonThread);

            pigeonListener.onFired();
        }
    }

    public void firePigeon() {
        if (this.pigeonThread != null) {
            this.pigeonThread.fire();
            this.pigeonListener.onCreated();

            this.pigeonThread = null;
        }
    }

    public void addUserThread(int writeTime) {
        User user = new User(writeTime);
        UserThread thread = new UserThread(usersListener, mailBox, user);

        userThreads.put(user.getId(), thread);
        usersListener.onAdded(user.getId());

        thread.start();
    }

    public void fireUser(int userId) {
        UserThread thread = this.userThreads.remove(userId);
        thread.fire();

        this.usersListener.onRemoved(thread.getUser().getId());
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
