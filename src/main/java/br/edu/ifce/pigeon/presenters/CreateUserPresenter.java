package br.edu.ifce.pigeon.presenters;

import br.edu.ifce.pigeon.jobs.ThreadController;
import br.edu.ifce.pigeon.ui.Component;
import br.edu.ifce.pigeon.ui.MainWindow;
import br.edu.ifce.pigeon.views.ICreateView;
import javafx.scene.control.Button;

import java.io.IOException;

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
        try {
            MainWindow.users.add(Component.load("image_users.fxml"));
            MainWindow.boxUsers.getChildren().add(new Button("adsfdasgadsgfafg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        public void onTypeWriteTime(String text) {
        writeTime = parseInput(text);
    }
}
