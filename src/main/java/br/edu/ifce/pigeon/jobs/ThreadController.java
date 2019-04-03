package br.edu.ifce.pigeon.jobs;

import br.edu.ifce.pigeon.models.MailBox;
import br.edu.ifce.pigeon.models.User;
<<<<<<< HEAD
import br.edu.ifce.pigeon.ui.MainWindow;
import br.edu.ifce.pigeon.views.IPigeonController;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
=======
import br.edu.ifce.pigeon.presenters.IMailBoxListener;
import br.edu.ifce.pigeon.views.IPigeonListener;
import br.edu.ifce.pigeon.views.IUsersListener;
>>>>>>> ffbf314c1439d71a805c1562192e07001fb850ad

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
            for (UserThread threads : userThreads.values()) {
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

    public void addUserThread(int writeTime) throws FileNotFoundException {
        User user = new User(writeTime);
<<<<<<< HEAD
        UserThread thread = new UserThread(this.mailBox, user);
        this.userThreads.put(user.getId(), thread);
        //========== teste ==============
        putUserImage();
=======
        UserThread thread = new UserThread(usersListener, mailBox, user);

        userThreads.put(user.getId(), thread);
        usersListener.onAdded(user.getId());

        thread.start();
>>>>>>> ffbf314c1439d71a805c1562192e07001fb850ad
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

<<<<<<< HEAD
    private void putUserImage() throws FileNotFoundException {
        InputStream file = new FileInputStream(String.format("C:\\Users\\Mateus\\Documents\\Mateus\\PomboCorreio1\\src\\main\\resources\\img\\user\\user_tipo0%d.png", 4));
        Image user = new Image(file);
        javafx.scene.image.ImageView view = new javafx.scene.image.ImageView(user);
        view.setFitHeight(100);
        view.setFitWidth(100);
        MainWindow.gridPaneUsers.add(view, 0 , 0);


=======
    public User getUser(int userId) {
        return this.userThreads.get(userId).getUser();
>>>>>>> ffbf314c1439d71a805c1562192e07001fb850ad
    }
}
