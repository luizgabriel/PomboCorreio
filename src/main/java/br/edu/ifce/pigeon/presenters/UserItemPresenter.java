package br.edu.ifce.pigeon.presenters;

import br.edu.ifce.pigeon.jobs.ThreadController;
import br.edu.ifce.pigeon.models.User;
import br.edu.ifce.pigeon.views.IUserItem;

public class UserItemPresenter extends BasePresenter<IUserItem> {
    private final ThreadController controller = ThreadController.getInstance();
    private final int userId;
    private User user;

    public UserItemPresenter(IUserItem view, int userId) {
        super(view);
        this.userId = userId;
    }

    @Override
    public void onLoadView() {
        user = controller.getUser(userId);
        getView().setImage(user.getImage());
        onRefresh(User.Status.WRITING, 0);
    }

    public void onRefresh(User.Status status, float progress) {
        getView().setProgress(progress);

        switch (status) {
            case SLEEPING:
                getView().setStatus(String.format("[TE: %d s] Dormindo...", user.getWriteTime()));
                break;
            case WRITING:
                getView().setStatus(String.format("[TE: %d s] Escrevendo...", user.getWriteTime()));
                break;
        }
    }

    public void onClickCancel() {
        this.controller.fireUser(this.userId);
    }
}
