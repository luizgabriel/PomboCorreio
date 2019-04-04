package br.edu.ifce.pigeon.ui;

import br.edu.ifce.pigeon.models.User;
import br.edu.ifce.pigeon.navigation.Navigation;
import br.edu.ifce.pigeon.presenters.MainPresenter;
import br.edu.ifce.pigeon.views.IMainWindow;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.transform.Rotate;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class MainWindow implements IMainWindow {
    private final Parent root = Component.load("main_screen.fxml");
    private final MainPresenter presenter = new MainPresenter(this);

    //Pigeon Frames
    private final List<Image> pigeon = new ArrayList<>();

    private final Map<Integer, UserItem> usersItems = new HashMap<>();

    //Components
    private final ImageView imageView;
    private final JFXDrawer navigationDrawer;
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
        usersBox = new HBox();

        imageView.setLayoutY(100);
        usersScroll.setContent(usersBox);
        navigationDrawer.setSidePane(menu);
        hamburgerBtn.setOnMouseClicked(e -> presenter.onClickMenuBtn());
        hirePigeonBtn.setOnMouseClicked(e -> presenter.onClickHirePigeonBtn());
        firePigeonBtn.setOnMouseClicked(e -> presenter.onClickFirePigeonBtn());
        addUserBtn.setOnMouseClicked(e -> presenter.onClickAddUserBtn());

        imageView.setTranslateZ(imageView.getBoundsInLocal().getWidth() / 2.0);
        imageView.setRotationAxis(Rotate.Y_AXIS);

        presenter.onLoadView();
    }

    @Override
    public void toggleMenu() {
        if (navigationDrawer.isClosed()) {
            navigationDrawer.setVisible(true);
            navigationDrawer.open();
            navigationDrawer.setOverLayVisible(true);
        } else {
            navigationDrawer.setVisible(false);
            navigationDrawer.close();
            navigationDrawer.setOverLayVisible(false);
        }
    }

    @Override
    public void loadPigeonFrames(int framesCount) {
        for (int i = 1; i <= framesCount; i++) {
            try {
                BufferedImage image = Component.loadImageBuffer(String.format("pigeon/koopatroopa_frame%02d.png", i));
                pigeon.add(Component.loadImage(image));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private PigeonFacingDirection lastDirection = PigeonFacingDirection.RIGHT;

    @Override
    public void setPigeonFrame(int frame, PigeonFacingDirection direction) {
        imageView.setImage(pigeon.get(frame));
        if (lastDirection != direction) {
            imageView.setRotate(180);
            lastDirection = direction;
        }
    }

    @Override
    public void firePigeon() {
        this.imageView.setImage(null);
    }

    @Override
    public void setPigeonPosition(float time) {
        this.imageView.setLayoutX(80 + time * 600);

        // hmax = 250, hmin = 350, a = - 100*(hmax - hmin) =
        this.imageView.setLayoutY(350 - 200*time + 200*Math.pow(time, 2));
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

            usersItems.put(userId, item);
            usersBox.getChildren().add(item.getRoot());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUser(int userId) {
        UserItem item = usersItems.remove(userId);
        usersBox.getChildren().remove(item.getRoot());
    }

    @Override
    public void updateUserStatus(int userId, User.Status status, float progress) {
        Platform.runLater(() -> {
            UserItem item = usersItems.get(userId);
            if (item != null) {
                item.onStatusRefreshed(status, progress);
            }
        });
    }

    @Override
    public void setMailCount(int current, int max) {
        Platform.runLater(() -> mailCountLabel.setText(String.format("%d/%d", current, max)));
    }

    @Override
    public void askMailBoxCapacity() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Caixa de Correio");
        dialog.setHeaderText("Informe a capacidade máxima da caixa de correio:");
        dialog.setContentText("Capacidade:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent())
            presenter.onSetMailBoxCapacity(result.get());
        else
            Navigation.close();
    }

    public Parent getRoot() {
        return this.root;
    }
}
