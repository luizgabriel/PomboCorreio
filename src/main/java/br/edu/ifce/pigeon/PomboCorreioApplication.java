package br.edu.ifce.pigeon;

import br.edu.ifce.pigeon.navigation.JavaFXNavigation;
import br.edu.ifce.pigeon.navigation.Navigation;
import br.edu.ifce.pigeon.ui.MainWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PomboCorreioApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        MainWindow main = new MainWindow();
        primaryStage.setScene(new Scene(main.getRoot()));
        primaryStage.setResizable(false);
        primaryStage.setTitle("IFCE .:: POMBO-CORREIO");
        primaryStage.show();

        Navigation.getInstance().setNavigationImpl(new JavaFXNavigation(primaryStage));
    }

}
