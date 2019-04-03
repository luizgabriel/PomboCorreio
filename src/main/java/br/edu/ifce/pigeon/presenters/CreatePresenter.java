package br.edu.ifce.pigeon.presenters;

import br.edu.ifce.pigeon.views.ICreateView;

public abstract class CreatePresenter extends BasePresenter<ICreateView> {

    public CreatePresenter(ICreateView view) {
        super(view);
    }

    @Override
    public void onLoadView() {

    }

    public void onClickSaveBtn() {
        if (!validate()) return;

        onSave();
        getView().close();
    }

    protected abstract boolean validate();

    public abstract void onSave();

    protected int parseInput(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

}
