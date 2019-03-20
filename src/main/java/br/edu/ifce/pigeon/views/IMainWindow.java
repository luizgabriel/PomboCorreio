package br.edu.ifce.pigeon.views;

public interface IMainWindow {

    enum PigeonFacingDirection {
        LEFT,
        RIGHT
    }

    void loadPigeonFrames(int framesCount);
    void setPigeonFrame(int frame, PigeonFacingDirection direction);
    void toggleMenu();
    void openCreatePigeonModal();
    void openCreateUserModal();
    void firePigeon();
    void setHirePigeonDisable(boolean disabled);
    void setFirePigeonDisable(boolean disabled);

}
