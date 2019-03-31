package br.edu.ifce.pigeon.presenters;

import br.edu.ifce.pigeon.jobs.ThreadController;
import br.edu.ifce.pigeon.views.ICreatePigeonView;

public class CreatePigeonPresenter extends BasePresenter<ICreatePigeonView> {
    private final ThreadController controller = ThreadController.getInstance();

    private int capacity;
    private int loadTime;
    private int unloadTime;
    private int travelTime;

    public CreatePigeonPresenter(ICreatePigeonView view) {
        super(view);
    }

    public void onClickSaveBtn() {
        if (!validate()) return;

        this.controller.initPigeonThread(capacity, loadTime, unloadTime, travelTime);
        getView().close();
    }

    private boolean validate() {
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

    @Override
    void onLoadView() {

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

    private int parseInput(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }


}
