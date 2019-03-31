package br.edu.ifce.pigeon.views;

public interface IPigeonController {
    enum AnimState {
        LOADING,
        TRAVEL_LEFT_TO_RIGHT,
        TRAVEL_RIGHT_TO_LEFT,
        UNLOADING
    }

    void refreshPigeonFrame(AnimState state);
    void setPigeonPosition(float position);
    void disablePigeonCreation();
    void enablePigeonCreation();
}
