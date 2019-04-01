package br.edu.ifce.pigeon.presenters;

import br.edu.ifce.pigeon.jobs.ThreadController;
import br.edu.ifce.pigeon.views.ICreateView;

public class CreateUserPresenter extends CreatePresenter {

    private final ThreadController controller = ThreadController.getInstance();

    private int writeTime;

    public CreateUserPresenter(ICreateView view) {
        super(view);
    }

    @Override
    protected boolean validate() {
        if (writeTime <= 0) {
            getView().showError("Tempo de escrita inválido!");
            return false;
        }

        return true;
    }

    @Override
    public void onSave() {
        controller.addUserThread(writeTime);
    }

    public void onTypeWriteTime(String text) {
        writeTime = parseInput(text);
    }
}
