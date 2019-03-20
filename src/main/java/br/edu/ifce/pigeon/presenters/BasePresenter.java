package br.edu.ifce.pigeon.presenters;

public abstract class BasePresenter<View> {
    private View view;

    BasePresenter(View view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

    abstract void onLoadView();
}
