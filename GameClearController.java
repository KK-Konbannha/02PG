import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class GameClearController {

    @FXML
    void onGameClearAction(ActionEvent event) {
        try {
            StageDB.getGameClearStage().hide();
            StageDB.getGameClearSound().stop();
            System.exit(0);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
