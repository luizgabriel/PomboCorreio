package views;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import pigeon.PigeonThread;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class MainWindow extends View implements IPigeonView {
    private static final int MAX_PIGEON_FRAMES = 9;
    private final ImageView imageView;
    private ArrayList<Image> leftToRight;
    private ArrayList<Image> rightToLeft;

    public enum PigeonAnimState {
        LOADING,
        TRAVEL_LEFT_TO_RIGHT,
        TRAVEL_RIGHT_TO_LEFT,
        UNLOADING
    }

    public MainWindow() throws IOException {
        super("main_screen.fxml");
        this.leftToRight = new ArrayList<>(MAX_PIGEON_FRAMES);
        this.rightToLeft = new ArrayList<>(MAX_PIGEON_FRAMES);
        this.imageView = (ImageView) this.lookup("#image-view-pigeon");

        for (int i = 1; i <= 9; i++) {
            try {
                BufferedImage image = View.loadImageBuffer(String.format("pigeon/pigeon_frame0%d.png", i));
                leftToRight.add(View.loadImage(image));
                rightToLeft.add(View.loadImage(flipImage(image)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        PigeonThread t = new PigeonThread(20, 5, 5, 10);
        t.setPigeon(this);
        t.start();

        startAnimation();
    }

    private void startAnimation() {
        Duration duration = Duration.millis(10000);
        TranslateTransition pigeon_moviment = new TranslateTransition(duration, this.imageView);
        pigeon_moviment.setByX(400);
        //Move in Y axis by +100
        pigeon_moviment.setByY(0);
        //Go back to previous position after 2.5 seconds
        pigeon_moviment.setAutoReverse(true);
        //Repeat animation twice
        pigeon_moviment.setCycleCount(6);
        pigeon_moviment.play();
    }

    private static BufferedImage flipImage(BufferedImage image) {
        BufferedImage mirrored = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pivot = image.getWidth() - x - 1;
                int pixel = image.getRGB(x, y);
                mirrored.setRGB(pivot, y, pixel);
            }
        }
        return mirrored;
    }

    public void setPigeonFrame(int i, PigeonAnimState state) {
        Image frame = null;
        switch (state) {
            case LOADING:
                frame = rightToLeft.get(0);
                break;
            case TRAVEL_RIGHT_TO_LEFT:
                frame = rightToLeft.get(i % MAX_PIGEON_FRAMES);
                break;
            case TRAVEL_LEFT_TO_RIGHT:
            case UNLOADING:
                frame = leftToRight.get(i % MAX_PIGEON_FRAMES);
                break;
        }

        this.imageView.setImage(frame);
    }
}
