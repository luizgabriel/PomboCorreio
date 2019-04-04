package br.edu.ifce.pigeon.views;

public interface IPigeonListener {
    enum AnimState {
        LOADING,
        TRAVEL_LEFT_TO_RIGHT,
        TRAVEL_RIGHT_TO_LEFT,
        UNLOADING
    }

    void onUpdate(float position, AnimState state);

    void onFired();

    void onCreated();
}
