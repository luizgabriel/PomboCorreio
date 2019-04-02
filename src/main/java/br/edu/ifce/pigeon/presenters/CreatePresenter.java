package br.edu.ifce.pigeon.presenters;

import br.edu.ifce.pigeon.views.ICreateView;

import java.io.IOException;

public abstract class CreatePresenter extends BasePresenter<ICreateView> {

    public CreatePresenter(ICreateView view) {
        super(view);
    }

    @Override
    void onLoadView() {

    }

    public void onClickSaveBtn() throws IOException {
        if (!validate()) return;

        onSave();
        getView().close();
    }

    protected abstract boolean validate();
    public abstract void onSave() throws IOException;

    protected int parseInput(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

}
