package br.edu.ifce.pigeon.ui;

import br.edu.ifce.pigeon.models.User;
import br.edu.ifce.pigeon.presenters.MainPresenter;
import br.edu.ifce.pigeon.views.IMainWindow;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Pair;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class MainWindow implements IMainWindow {
    private final Parent root = Component.load("main_screen.fxml");
    private final MainPresenter presenter = new MainPresenter(this);

    //Pigeon Frames
    private final List<Image> left = new ArrayList<>();
    private final List<Image> right = new ArrayList<>();

    private final Map<Integer, Pair<Integer, UserItem>> usersItems = new HashMap<>();

    //Components
    private final ImageView imageView;
    private final JFXDrawer navigationDrawer;
    private final HamburgerSlideCloseTransition transition;
    private final JFXButton hirePigeonBtn;
    private final JFXButton firePigeonBtn;
    private final HBox usersBox;
    private final Label mailCountLabel;

    public MainWindow() throws IOException {
        Parent menu = Component.load("navigation_menu.fxml");

        JFXHamburger hamburgerBtn = (JFXHamburger) root.lookup("#menu-btn");
        JFXButton addUserBtn = (JFXButton) menu.lookup("#add-user-btn");
        ScrollPane usersScroll = (ScrollPane) root.lookup("#usersScroll");

        imageView = (ImageView) root.lookup("#image-view-pigeon");
        navigationDrawer = (JFXDrawer) root.lookup("#navigation-drawer");
        hirePigeonBtn = (JFXButton) menu.lookup("#hire-pigeon-btn");
        firePigeonBtn = (JFXButton) menu.lookup("#fire-pigeon-btn");
        mailCountLabel = (Label) root.lookup("#mailCountLabel");
        transition = new HamburgerSlideCloseTransition(hamburgerBtn);
        usersBox = new HBox();

        transition.setRate(-1);
        imageView.setLayoutY(100);
        usersScroll.setContent(usersBox);
        navigationDrawer.setSidePane(menu);
        hamburgerBtn.setOnMouseClicked(e -> presenter.onClickMenuBtn());
        hirePigeonBtn.setOnMouseClicked(e -> presenter.onClickHirePigeonBtn());
        firePigeonBtn.setOnMouseClicked(e -> presenter.onClickFirePigeonBtn());
        addUserBtn.setOnMouseClicked(e -> presenter.onClickAddUserBtn());

        presenter.onLoadView();
    }

    @Override
    public void toggleMenu() {
        Platform.runLater(() -> {
            transition.setRate(transition.getRate() * -1);
            transition.play();

            if (navigationDrawer.isClosed()) {
                navigationDrawer.open();
            } else {
                navigationDrawer.close();
            }
        });
    }

    @Override
    public void loadPigeonFrames(int framesCount) {
        for (int i = 1; i <= framesCount; i++) {
            try {
                BufferedImage image = Component.loadImageBuffer(String.format("pigeon/pigeon_frame0%d.png", i));
                right.add(Component.loadImage(image));
                left.add(Component.loadImage(flipImage(image)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setPigeonFrame(int frame, PigeonFacingDirection direction) {
        Image pigeonFrame;
        switch (direction) {
            default:
            case LEFT:
                pigeonFrame = left.get(frame);
                break;
            case RIGHT:
                pigeonFrame = right.get(frame);
                break;
        }

        this.imageView.setImage(pigeonFrame);
    }

    private static BufferedImage flipImage(BufferedImage image) {
        BufferedImage mirrored = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pivot = image.getWidth() - x - 1;
                int pixel = image.getRGB(x, y);
                mirrored.setRGB(pivot, y, pixel);
            }
        }
        return mirrored;
    }

    @Override
    public void firePigeon() {
        this.imageView.setImage(null);
    }

    @Override
    public void setPigeonPosition(float position) {
        this.imageView.setLayoutX(75 + position * 550);
    }

    @Override
    public void setHirePigeonDisable(boolean disabled) {
        this.hirePigeonBtn.setDisable(disabled);
    }

    @Override
    public void setFirePigeonDisable(boolean disabled) {
        this.firePigeonBtn.setDisable(disabled);
    }

    @Override
    public void addUser(int userId) {
        try {
            UserItem item = new UserItem(userId);
            int index = usersItems.size();

            usersItems.put(userId, new Pair<>(index, item));
            usersBox.getChildren().add(index, item.getRoot());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUser(int userId) {
        Pair<Integer, UserItem> item = usersItems.remove(userId);
        usersBox.getChildren().remove((int) item.getKey());
    }

    @Override
    public void updateUserStatus(int userId, User.Status status, float progress) {
        Platform.runLater(() -> {
            Pair<Integer, UserItem> item = usersItems.get(userId);
            if (item != null) {
                item.getValue().onStatusRefreshed(status, progress);
            }
        });
    }

    @Override
    public void setMailCount(int current, int max) {
        Platform.runLater(() -> {
            mailCountLabel.setText(String.format("%d/%d", current, max));
        });
    }

    @Override
    public void askMailBoxCapacity() {
        TextInputDialog dialog = new TextInputDialog();

        dialog.setTitle("Caixa de Correio");
        dialog.setHeaderText("Informe a capacidade máxima da caixa de correio:");
        dialog.setContentText("Capacidade:");
        dialog.showAndWait().ifPresent(presenter::onSetMailBoxCapacity);
    }

    public Parent getRoot() {
        return this.root;
    }
}
