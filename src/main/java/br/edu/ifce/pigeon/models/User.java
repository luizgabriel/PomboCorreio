package br.edu.ifce.pigeon.models;

import java.util.UUID;

public class User {
    private int id;
    private int image;
    private int writeTime;

    private static int currentImage = 0;
    private static final int MAX_IMAGES = 13;

    public User(int writeTime) {
        this.writeTime = writeTime;
        this.id = UUID.randomUUID().hashCode();
        this.image = (currentImage++) % MAX_IMAGES;
    }

    public int getId() {
        return id;
    }

    public int getImage() {
        return image;
    }

    public int getWriteTime() {
        return writeTime;
    }

    public enum Status {
        SLEEPING,
        WRITING
    }
}
