package br.edu.ifce.pigeon.navigation;

import br.edu.ifce.pigeon.ui.Component;
import br.edu.ifce.pigeon.ui.CreatePigeonWindow;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFXNavigation implements INavigation {
    private final Stage primary;

    public JavaFXNavigation(Stage primary) {
        this.primary = primary;
    }

    @Override
    public void openCreatePigeon() {
        try {
            Stage stage = new Stage();
            CreatePigeonWindow window = new CreatePigeonWindow();
            stage.setScene(new Scene(window.getRoot()));
            stage.setTitle("Contratar pombo");
            stage.setResizable(false);
            stage.setIconified(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openCreateUser() {
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(Component.load("create_user.fxml")));
            stage.setTitle("Adicionar pombo");
            stage.setResizable(false);
            stage.setIconified(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
