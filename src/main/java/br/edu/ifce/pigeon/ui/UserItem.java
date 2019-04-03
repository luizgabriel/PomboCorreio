package br.edu.ifce.pigeon.ui;

import br.edu.ifce.pigeon.models.User;
import br.edu.ifce.pigeon.presenters.UserItemPresenter;
import br.edu.ifce.pigeon.views.IUserItem;
import com.jfoenix.controls.JFXButton;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class UserItem implements IUserItem {
    private final Node root = Component.load("user_item.fxml");
    private final UserItemPresenter presenter;

    private final Label statusLabel;
    private final ImageView imageView;
    private final ProgressBar progressBar;

    public UserItem(int userId) throws IOException {
        presenter = new UserItemPresenter(this, userId);

        statusLabel = (Label) root.lookup("#statusLabel");
        imageView = (ImageView) root.lookup("#imageView");
        progressBar = (ProgressBar) root.lookup("#progressBar");
        JFXButton deleteBtn = (JFXButton) root.lookup("#deleteBtn");

        deleteBtn.setOnMouseClicked(e -> presenter.onClickCancel());
        presenter.onLoadView();
    }

    @Override
    public void setImage(int image) {
        try {
            Image img = Component.loadImage(String.format("user/user_tipo%02d.png", image));
            imageView.setImage(img);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setStatus(String s) {
        statusLabel.setText(s);
    }

    @Override
    public void setProgress(float progress) {
        progressBar.setProgress(progress);
    }

    public void onStatusRefreshed(User.Status status, float progress) {
        presenter.onRefresh(status, progress);
    }

    public Node getRoot() {
        return root;
    }
}
