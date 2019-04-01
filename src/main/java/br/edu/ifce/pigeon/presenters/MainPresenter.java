package br.edu.ifce.pigeon.presenters;

import br.edu.ifce.pigeon.jobs.ThreadController;
import br.edu.ifce.pigeon.models.Mail;
import br.edu.ifce.pigeon.models.User;
import br.edu.ifce.pigeon.navigation.Navigation;
import br.edu.ifce.pigeon.views.IMainWindow;
import br.edu.ifce.pigeon.views.IPigeonController;

public class MainPresenter extends BasePresenter<IMainWindow> implements IPigeonController {
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

        this.controller.setPigeonController(this);
        this.controller.initMailBox(10);

        User u = new User(10);
        this.controller.getMailBox().put(new Mail(u));
        this.controller.getMailBox().put(new Mail(u));
        this.controller.getMailBox().put(new Mail(u));
        this.controller.getMailBox().put(new Mail(u));
        this.controller.getMailBox().put(new Mail(u));
        this.controller.getMailBox().put(new Mail(u));
        this.controller.getMailBox().put(new Mail(u));
        this.controller.getMailBox().put(new Mail(u));
    }

    @Override
    public void refreshPigeonFrame(AnimState state) {
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

    public void onClickMenuBtn() {
        getView().toggleMenu();
    }

    public void onClickHirePigeonBtn() {
        Navigation.openCreatePigeon();
    }

    public void onClickFirePigeonBtn() {
        controller.firePigeon();
    }

    public void enablePigeonCreation() {
        getView().setHirePigeonDisable(false);
        getView().setFirePigeonDisable(true);
        getView().firePigeon();
    }

    @Override
    public void disablePigeonCreation() {
        getView().setHirePigeonDisable(true);
        getView().setFirePigeonDisable(false);
        getView().toggleMenu();
    }

    public void onClickAddUserBtn() {
        Navigation.openCreateUser();
    }

    @Override
    public void setPigeonPosition(float position) {
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
}
