package pigeon;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class PigeonThread extends Thread{
    public int max_capacity;
    private int load_time;
    private int unload_time;
    private int flight_time;

    public static Semaphore semaphore_pigeon;
    public static Semaphore mutex;
    public static Semaphore mailBox;

    public static ArrayList pigeon_left_to_right = new ArrayList<>();
    public static ArrayList pigeon_right_to_left = new ArrayList<>();

    public PigeonThread(Semaphore semaphore_pigeon, Semaphore mutex, Semaphore mailBox,
                        int max_capacity, int load_time, int unload_time, int flight_time) {

        this.max_capacity = max_capacity;
        this.load_time = load_time;
        this.unload_time = unload_time;
        this.flight_time = flight_time;
        this.semaphore_pigeon = semaphore_pigeon;
        for(int i=1; i<=9; i++){
            BufferedImage imagem = null;
            try {
                imagem = ImageIO.read(new FileInputStream(String.format("pigeon\\pigeon_frame0%d", i)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            pigeon_left_to_right.add(real_image(imagem));
            BufferedImage mirrored_image = toFlip(imagem);
            pigeon_right_to_left.add(real_image(mirrored_image));

        }
    }
    public static WritableImage real_image(BufferedImage image) {
        WritableImage real_image = null;
        if (image != null) {
            real_image = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pixel = real_image.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pixel.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }
        return real_image;
    }

    private static BufferedImage toFlip(BufferedImage imagem){
        BufferedImage mirrored = new BufferedImage(imagem.getWidth(), imagem.getHeight(), imagem.getType());
        for(int y=0; y < imagem.getHeight(); y++){
            for(int x=0; x < imagem.getWidth(); x++){
                int pivot = imagem.getWidth()-x-1;
                int pixel = imagem.getRGB(x, y);
                mirrored.setRGB(pivot, y, pixel);
            }
        }
        return mirrored;
    }

    @Override
    public void run(){
        while(true){
            try{
                semaphore_pigeon.acquire();
                mutex.acquire();

                for(int i=1; i <= max_capacity; ++i){
                    mailBox.release();
                }

                mutex.release();
                fly_left_to_right();
                unload_box();
                fly_right_to_left();
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private void fly_right_to_left() {
    }

    private void unload_box() {
    }

    private void fly_left_to_right() {
    }


}
