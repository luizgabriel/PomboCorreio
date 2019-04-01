package br.edu.ifce.pigeon.presenters;

import br.edu.ifce.pigeon.jobs.ThreadController;
import br.edu.ifce.pigeon.views.ICreateView;

public class CreatePigeonPresenter extends CreatePresenter {
    private final ThreadController controller = ThreadController.getInstance();

    private int capacity;
    private int loadTime;
    private int unloadTime;
    private int travelTime;

    public CreatePigeonPresenter(ICreateView view) {
        super(view);
    }

    @Override
    public void onSave() {
        this.controller.initPigeonThread(capacity, loadTime, unloadTime, travelTime);
    }

    @Override
    protected boolean validate() {
        if (capacity <= 0) {
            getView().showError("capacidade inválida!");
            return false;
        }

        if (loadTime <= 0) {
            getView().showError("Tempo de carga inválido!");
            return false;
        }

        if (unloadTime <= 0) {
            getView().showError("Tempo de descarregamento inválido!");
            return false;
        }

        if (travelTime <= 0) {
            getView().showError("Tempo de viagem inválido!");
            return false;
        }

        return true;
    }

    public void onTypeCapacity(String text) {
        capacity = parseInput(text);
    }

    public void onTypeLoadTime(String text) {
        loadTime = parseInput(text);
    }

    public void onTypeUnloadTime(String text) {
        unloadTime = parseInput(text);
    }

    public void onTypeTravelTime(String text) {
        travelTime = parseInput(text);
    }

}
