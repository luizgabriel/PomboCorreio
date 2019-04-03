package br.edu.ifce.pigeon.ui;

import br.edu.ifce.pigeon.presenters.CreateUserPresenter;
import br.edu.ifce.pigeon.views.ICreateView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CreateUserWindow implements ICreateView {
    private Parent root = Component.load("create_user.fxml");
    private final CreateUserPresenter presenter = new CreateUserPresenter(this);
    private final JFXSnackbar bar;

    public CreateUserWindow() throws IOException {
        JFXTextField writeTimeTxtField = (JFXTextField) root.lookup("#writeTimeTxtField");
        bar = new JFXSnackbar((Pane) root.lookup("#rootPane"));

        JFXButton saveBtn = (JFXButton) root.lookup("#saveBtn");

        saveBtn.setOnMouseClicked(e -> {
<<<<<<< HEAD
            try {
                presenter.onClickSaveBtn();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        });
=======
            presenter.onTypeWriteTime(writeTimeTxtField.getText());
            presenter.onClickSaveBtn();
        });

        presenter.onLoadView();
>>>>>>> ffbf314c1439d71a805c1562192e07001fb850ad
    }

    @Override
    public void showError(String text) {
        //TODO: encapsulate inside a snack bar view component
        Label label = new Label(text);
        label.setTextFill(Paint.valueOf("#dd0000"));
        bar.enqueue(new JFXSnackbar.SnackbarEvent(label));
    }

    @Override
    public void close() {
        Stage stage = (Stage) getRoot().getScene().getWindow();
        stage.close();
    }

    public Parent getRoot() {
        return root;
    }
}
