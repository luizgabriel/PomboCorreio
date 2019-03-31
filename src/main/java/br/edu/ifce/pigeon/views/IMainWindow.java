package br.edu.ifce.pigeon.views;

import br.edu.ifce.pigeon.jobs.ThreadController;

public interface IMainWindow {

    enum PigeonFacingDirection {
        LEFT,
        RIGHT
    }

    void loadPigeonFrames(int framesCount);
    void setPigeonFrame(int frame, PigeonFacingDirection direction);
    void setPigeonPosition(float position);
    void toggleMenu();
    void firePigeon();
    void setHirePigeonDisable(boolean disabled);
    void setFirePigeonDisable(boolean disabled);

}
