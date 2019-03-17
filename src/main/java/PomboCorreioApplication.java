import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.MenuBar;
import javafx.scene.image.PixelWriter;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.util.Duration;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class PomboCorreioApplication extends Application {

    public static Semaphore semaphore_pigeon = new Semaphore(0);
    public static Semaphore semaphore_mutex = new Semaphore(1);
    public static Semaphore semaphore_mailBox;


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(PomboCorreioApplication.class.getResource("fxml/main_screen.fxml"));
        Scene scene = new Scene(loader.load());

        //Usa o menubar nativo no OSX
        MenuBar menu = (MenuBar) scene.lookup("#main-menu-bar");
        try{
            Image imagem = new Image(new FileInputStream("pigeon\\pigeon.gif"));
            ImageView imagem_view = (ImageView) scene.lookup("#image-view-pigeon");
            imagem_view.setImage(imagem);
            menu.setUseSystemMenuBar(true);

            Duration duration = Duration.millis(10000);
            TranslateTransition pigeon_moviment = new TranslateTransition(duration, imagem_view);
            pigeon_moviment.setByX(400);
            //Move in Y axis by +100
            pigeon_moviment.setByY(0);
            //Go back to previous position after 2.5 seconds
            pigeon_moviment.setAutoReverse(true);
            //Repeat animation twice
            pigeon_moviment.setCycleCount(6);
            pigeon_moviment.play();

            primaryStage.setScene(scene);
            primaryStage.show();

        }catch (FileNotFoundException e) {
            System.out.println("Erro");
        }
    }

}
