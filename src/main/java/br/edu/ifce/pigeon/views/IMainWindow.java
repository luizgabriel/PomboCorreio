package br.edu.ifce.pigeon.views;

import br.edu.ifce.pigeon.jobs.ThreadController;
import br.edu.ifce.pigeon.models.User;

public interface IMainWindow {

    enum PigeonFacingDirection {
        LEFT,
        RIGHT
    }

    void toggleMenu();

    void loadPigeonFrames(int framesCount);
    void setPigeonFrame(int frame, PigeonFacingDirection direction);
    void setPigeonPosition(float position);
    void setHirePigeonDisable(boolean disabled);
    void setFirePigeonDisable(boolean disabled);
    void firePigeon();

    void addUser(int userId);
    void removeUser(int userId);
    void updateUserStatus(int userId, User.Status status, float percentage);

}
