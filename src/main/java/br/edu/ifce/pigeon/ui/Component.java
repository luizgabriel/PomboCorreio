package br.edu.ifce.pigeon.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public final class Component {

    private Component() {
    } //Prevent instantiation

    public static Parent load(String name) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Component.getResource(String.format("fxml/%s", name)));
        return loader.load();
    }

    public static URL getResource(String path) {
        return Component.class.getClassLoader().getResource(path);
    }

    public static BufferedImage loadImageBuffer(String name) throws IOException {
        return ImageIO.read(getResource(String.format("img/%s", name)));
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
