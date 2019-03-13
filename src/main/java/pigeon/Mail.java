package pigeon;

import java.util.UUID;

public class Mail {
    private User user;
    private int id;

    public Mail(User user) {
        this.user = user;
        this.id = UUID.randomUUID().hashCode();
    }

    public User getUser() {
        return this.user;
    }

    public int getId() {
        return this.id;
    }
}
