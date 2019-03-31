package br.edu.ifce.pigeon.ui;

import br.edu.ifce.pigeon.presenters.CreatePigeonPresenter;
import br.edu.ifce.pigeon.views.ICreatePigeonView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;

public class CreatePigeonWindow implements ICreatePigeonView {
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
        capacityTxtField.setOnKeyReleased(e -> presenter.onTypeCapacity(capacityTxtField.getText()));
        loadTimeTxtField.setOnKeyReleased(e -> presenter.onTypeLoadTime(loadTimeTxtField.getText()));
        unloadTimeTxtField.setOnKeyReleased(e -> presenter.onTypeUnloadTime(unloadTimeTxtField.getText()));
        travelTimeTxtField.setOnKeyReleased(e -> presenter.onTypeTravelTime(travelTimeTxtField.getText()));

        saveBtn.setOnMouseClicked(e -> presenter.onClickSaveBtn());
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
