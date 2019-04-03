package br.edu.ifce.pigeon.presenters;

import br.edu.ifce.pigeon.jobs.ThreadController;
import br.edu.ifce.pigeon.models.User;
import br.edu.ifce.pigeon.views.IRefreshStatus;
import br.edu.ifce.pigeon.views.IUserItem;

public class UserItemPresenter extends BasePresenter<IUserItem> implements IRefreshStatus {
    private final ThreadController controller = ThreadController.getInstance();
    private final int userId;

    public UserItemPresenter(IUserItem view, int userId) {
        super(view);
        this.userId = userId;
    }

    @Override
    public void onLoadView() {
        User user = controller.getUser(userId);
        getView().setImage(user.getImage());
    }

    @Override
    public void onRefresh(Status status) {
        switch (status) {
            case SLEEPING:
                getView().setStatus("Dormindo...");
                break;
            case WRITING:
                getView().setStatus("Escrevendo...");
                break;
        }
    }

    public void onClickCancel() {
        this.controller.fireUser(this.userId);
    }
}
