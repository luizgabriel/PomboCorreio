import javafx.application.Application;
import javafx.stage.Stage;
import views.MainWindow;

public class PomboCorreioApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        MainWindow main = new MainWindow();
        primaryStage.setScene(main.getScene());
        primaryStage.show();
    }

}
