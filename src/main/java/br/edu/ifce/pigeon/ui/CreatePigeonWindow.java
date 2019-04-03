package br.edu.ifce.pigeon.ui;

import br.edu.ifce.pigeon.presenters.CreatePigeonPresenter;
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

public class CreatePigeonWindow implements ICreateView {
    private Parent root = Component.load("create_pigeon.fxml");
    private final CreatePigeonPresenter presenter = new CreatePigeonPresenter(this);
    private final JFXSnackbar bar;

    public CreatePigeonWindow() throws IOException {
        JFXTextField capacityTxtField = (JFXTextField) root.lookup("#capacityTxtField");
        JFXTextField loadTimeTxtField = (JFXTextField) root.lookup("#loadTimeTxtField");
        JFXTextField unloadTimeTxtField = (JFXTextField) root.lookup("#unloadTimeTxtField");
        JFXTextField travelTimeTxtField = (JFXTextField) root.lookup("#travelTimeTxtField");
        bar = new JFXSnackbar((Pane) root.lookup("#rootPane"));

        JFXButton saveBtn = (JFXButton) root.lookup("#saveBtn");

        saveBtn.setOnMouseClicked(e -> {

            presenter.onTypeCapacity(capacityTxtField.getText());
            presenter.onTypeLoadTime(loadTimeTxtField.getText());
            presenter.onTypeUnloadTime(unloadTimeTxtField.getText());
            presenter.onTypeTravelTime(travelTimeTxtField.getText());
            try {
                presenter.onClickSaveBtn();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        });

        presenter.onLoadView();
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
