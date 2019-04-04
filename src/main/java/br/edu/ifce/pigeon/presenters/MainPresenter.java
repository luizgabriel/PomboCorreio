package br.edu.ifce.pigeon.presenters;

import br.edu.ifce.pigeon.jobs.ThreadController;
import br.edu.ifce.pigeon.models.User;
import br.edu.ifce.pigeon.navigation.Navigation;
import br.edu.ifce.pigeon.views.IMainWindow;
import br.edu.ifce.pigeon.views.IPigeonListener;
import br.edu.ifce.pigeon.views.IUsersListener;

import static br.edu.ifce.pigeon.views.IMainWindow.PigeonFacingDirection.LEFT;
import static br.edu.ifce.pigeon.views.IMainWindow.PigeonFacingDirection.RIGHT;


public class MainPresenter extends BasePresenter<IMainWindow> implements IPigeonListener, IUsersListener, IMailBoxListener {
    private int currentPigeonFrame;
    private static final int MAX_PIGEON_FRAMES = 9;

    private final ThreadController controller;

    public MainPresenter(IMainWindow view) {
        super(view);
        this.controller = ThreadController.getInstance();
    }

    @Override
    public void onLoadView() {
        getView().loadPigeonFrames(MAX_PIGEON_FRAMES);

        this.controller.setPigeonListener(this);
        this.controller.setUsersListener(this);
        getView().askMailBoxCapacity();
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
    public void onUpdate(float position, AnimState state) {
        currentPigeonFrame = (currentPigeonFrame + 1) % MAX_PIGEON_FRAMES;

        switch (state) {
            default:
            case LOADING:
            case TRAVEL_RIGHT_TO_LEFT:
                getView().setPigeonFrame(currentPigeonFrame, LEFT);
                getView().setPigeonPosition(1 - position);
                break;
            case TRAVEL_LEFT_TO_RIGHT:
            case UNLOADING:
                getView().setPigeonFrame(currentPigeonFrame, RIGHT);
                getView().setPigeonPosition(position);
                break;
        }
    }

    @Override
    public void onCreated() {
        getView().setHirePigeonDisable(false);
        getView().setFirePigeonDisable(true);
        getView().firePigeon();
        getView().toggleMenu();
    }

    @Override
    public void onFired() {
        getView().setHirePigeonDisable(true);
        getView().setFirePigeonDisable(false);
        getView().toggleMenu();
    }
    //</editor-fold>

    //<editor-fold desc="Users Listener">
    @Override
    public void onAdded(int userId) {
        getView().addUser(userId);
    }

    @Override
    public void onRemoved(int userId) {
        getView().removeUser(userId);
    }

    @Override
    public void onRefreshStatus(int userId, User.Status status, float percentage) {
        getView().updateUserStatus(userId, status, percentage);
    }
    //</editor-fold>

    //<editor-fold desc-"MailBox Listener">
    @Override
    public void onChange(int current, int max) {
        getView().setMailCount(current, max);
    }

    public void onSetMailBoxCapacity(String capacityText) {
        try {
            int capacity = Integer.parseInt(capacityText);
            if (capacity > 0) {
                controller.initMailBox(capacity, this);
            } else {
                getView().askMailBoxCapacity();
            }
        } catch (NumberFormatException e) {
            getView().askMailBoxCapacity();
        }
    }
    //</editor-fold>
}
