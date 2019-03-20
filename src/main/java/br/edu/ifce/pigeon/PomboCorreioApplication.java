package br.edu.ifce.pigeon;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import br.edu.ifce.pigeon.ui.MainWindow;

public class PomboCorreioApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        MainWindow main = new MainWindow();
        primaryStage.setScene(new Scene(main.getRoot()));
        primaryStage.setResizable(false);
        primaryStage.setTitle("IFCE .:: POMBO-CORREIO");
        primaryStage.show();
    }

}
