package br.edu.ifce.pigeon.jobs;

import br.edu.ifce.pigeon.models.MailBox;
import br.edu.ifce.pigeon.models.User;
import br.edu.ifce.pigeon.ui.MainWindow;
import br.edu.ifce.pigeon.views.IPigeonController;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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

    public void addUserThread(int writeTime) throws FileNotFoundException {
        User user = new User(writeTime);
        UserThread thread = new UserThread(this.mailBox, user);
        this.userThreads.put(user.getId(), thread);
        //========== teste ==============
        putUserImage();
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

    private void putUserImage() throws FileNotFoundException {
        InputStream file = new FileInputStream(String.format("C:\\Users\\Mateus\\Documents\\Mateus\\PomboCorreio1\\src\\main\\resources\\img\\user\\user_tipo0%d.png", 4));
        Image user = new Image(file);
        javafx.scene.image.ImageView view = new javafx.scene.image.ImageView(user);
        view.setFitHeight(100);
        view.setFitWidth(100);
        MainWindow.gridPaneUsers.add(view, 0 , 0);


    }
}
