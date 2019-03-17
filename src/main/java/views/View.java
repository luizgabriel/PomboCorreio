package views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class View {

    private final Scene scene;

    public View(String viewName) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(View.class.getResource(String.format("fxml/%s", viewName)));

        this.scene = new Scene(loader.load());
    }

    public Scene getScene() {
        return this.scene;
    }

    public Node lookup(String query) {
        return this.scene.lookup(query);
    }

    public static BufferedImage loadImageBuffer(String path) throws IOException {
        return ImageIO.read(View.class.getResourceAsStream(String.format("img/%s", path)));
    }

    public static WritableImage loadImage(BufferedImage image) {
        WritableImage realImage = null;

        if (image != null) {
            realImage = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pixel = realImage.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pixel.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }

        return realImage;
    }

    public static WritableImage loadImage(String path) throws IOException {
        BufferedImage image = loadImageBuffer(path);
        return loadImage(image);
    }

}
