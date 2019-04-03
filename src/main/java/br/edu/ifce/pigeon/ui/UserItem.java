package br.edu.ifce.pigeon.ui;

import br.edu.ifce.pigeon.presenters.UserItemPresenter;
import br.edu.ifce.pigeon.views.IUserItem;
import com.jfoenix.controls.JFXButton;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class UserItem implements IUserItem {
    private final Node root = Component.load("user_item.fxml");
    private final UserItemPresenter presenter;

    private final Label statusLabel;
    private final ImageView imageView;

    public UserItem(int userId) throws IOException {
        presenter = new UserItemPresenter(this, userId);

        statusLabel = (Label) root.lookup("#statuslabel");
        imageView = (ImageView) root.lookup("#imageView");
        JFXButton cancelBtn = (JFXButton) root.lookup("#cancelBtn");

        cancelBtn.setOnMouseClicked(e -> presenter.onClickCancel());
        presenter.onLoadView();
    }

    public Node getRoot() {
        return root;
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
}
