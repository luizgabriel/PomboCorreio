package br.edu.ifce.pigeon.presenters;

import br.edu.ifce.pigeon.jobs.ThreadController;
import br.edu.ifce.pigeon.models.Mail;
import br.edu.ifce.pigeon.models.User;
import br.edu.ifce.pigeon.navigation.Navigation;
import br.edu.ifce.pigeon.views.IMainWindow;
import br.edu.ifce.pigeon.views.IPigeonListener;
import br.edu.ifce.pigeon.views.IUsersListener;

public class MainPresenter extends BasePresenter<IMainWindow> implements IPigeonListener, IUsersListener {
    private int currentPigeonFrame;
    private static final int MAX_PIGEON_FRAMES = 9;

    private final ThreadController controller;
    private IMainWindow.PigeonFacingDirection direction;

    public MainPresenter(IMainWindow view) {
        super(view);
        this.controller = ThreadController.getInstance();
    }

    @Override
    public void onLoadView() {
        getView().loadPigeonFrames(MAX_PIGEON_FRAMES);


        this.controller.setPigeonListener(this);
        this.controller.setUsersListener(this);
        this.controller.initMailBox(10);
        getView().toggleMenu();
    }

    public void onClickMenuBtn() {
        getView().toggleMenu();
    }

    public void onClickHirePigeonBtn() {
        Navigation.openCreatePigeon();
    }

    public void onClickFirePigeonBtn() {
        controller.firePigeon();
    }

    public void onClickAddUserBtn() {
        Navigation.openCreateUser();
    }

    //<editor-fold desc="Pigeon Listener">
    @Override
    public void onChangeState(AnimState state) {
        currentPigeonFrame = (currentPigeonFrame + 1) % MAX_PIGEON_FRAMES;

        switch (state) {
            default:
            case LOADING:
            case TRAVEL_RIGHT_TO_LEFT:
                direction = IMainWindow.PigeonFacingDirection.LEFT;
                break;
            case TRAVEL_LEFT_TO_RIGHT:
            case UNLOADING:
                direction = IMainWindow.PigeonFacingDirection.RIGHT;
                break;
        }

        getView().setPigeonFrame(currentPigeonFrame, direction);
    }

    @Override
    public void onCreated() {
        getView().setHirePigeonDisable(false);
        getView().setFirePigeonDisable(true);
        getView().firePigeon();
        //getView().toggleMenu();
    }

    @Override
    public void onFired() {
        getView().setHirePigeonDisable(true);
        getView().setFirePigeonDisable(false);
        //getView().toggleMenu();
    }

    @Override
    public void onChangePosition(float position) {
        switch (direction) {
            case LEFT:
                getView().setPigeonPosition(1 - position);
                break;
            default:
            case RIGHT:
                getView().setPigeonPosition(position);
                break;
        }
    }
    //</editor-fold>

    //<editor-fold desc="Users Listener">
    @Override
    public void onAdded(int userId) {
        getView().addUser(userId);
        getView().toggleMenu();
    }

    @Override
    public void onRemoved(int userId) {
        getView().removeUser(userId);
    }

    @Override
    public void onRefreshStatus(int userId, User.Status status, float percentage) {
        getView().updateUserStatus(userId, status, percentage);
    }
}
