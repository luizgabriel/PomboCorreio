package br.edu.ifce.pigeon.ui;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Parent;

import java.io.IOException;

public class CreatePigeonWindow {
    private Parent root;
    private final JFXButton createBtn;

    public CreatePigeonWindow() throws IOException {
        root = Component.load("main_screen.fxml");

        createBtn = (JFXButton) root.lookup("#botao_salvar_pombo");

        createBtn.setOnMouseClicked(event -> {
            //TODO
        });
    }

    public Parent getRoot() {
        return root;
    }
}
