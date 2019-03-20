package br.edu.ifce.pigeon.presenters;

import br.edu.ifce.pigeon.jobs.PigeonThread;
import br.edu.ifce.pigeon.views.IMainWindow;
import br.edu.ifce.pigeon.views.IPigeonController;

public class MainPresenter extends BasePresenter<IMainWindow> implements IPigeonController {
    private int currentPigeonFrame;
    private static final int MAX_PIGEON_FRAMES = 9;

    private PigeonThread pigeonThread;

    public MainPresenter(IMainWindow view) {
        super(view);
    }

    @Override
    public void onLoadView(){
        getView().loadPigeonFrames(MAX_PIGEON_FRAMES);
    }

    @Override
    public void refreshPigeonFrame(AnimState state) {
        currentPigeonFrame = (currentPigeonFrame + 1) % MAX_PIGEON_FRAMES;
        IMainWindow.PigeonFacingDirection direction;

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
        getView().setHirePigeonDisable(true);
        getView().setFirePigeonDisable(false);
        getView().openCreatePigeonModal();

        pigeonThread = new PigeonThread(20, 5, 5, 10);
        pigeonThread.setPigeon(this);
        pigeonThread.start();
    }

    public void onClickFirePigeonBtn() {
        getView().setHirePigeonDisable(false);
        getView().setFirePigeonDisable(true);
        getView().firePigeon();

        if (pigeonThread != null)
            pigeonThread.fire();
    }

    public void onClickAddUserBtn() {
        getView().openCreateUserModal();
    }

}
