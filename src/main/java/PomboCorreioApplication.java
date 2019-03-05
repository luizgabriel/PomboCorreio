import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

public class PomboCorreioApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(PomboCorreioApplication.class.getResource("fxml/main_screen.fxml"));
        Scene scene = new Scene(loader.load());

        //Usa o menubar nativo no OSX
        MenuBar menu = (MenuBar) scene.lookup("#main-menu-bar");
        menu.setUseSystemMenuBar(true);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
