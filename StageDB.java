import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

class StageDB {

    private static Stage mainStage = null;
    private static Stage gameOverStage = null;
    private static MediaPlayer mainSound = null;
    private static MediaPlayer gameOverSound = null;
    private static MediaPlayer itemGetSound = null;
    private static Class mainClass;
    private static final String soundDirName = "sound/";
    private static final String mainSoundFileName = "Nature_Effects.mp3"; // BGM by OtoLogic
    private static final String gameoverSoundFileName = "gameover2_8bit.mp3"; // BGM gameover
    private static final String itemGetSoundFileName = "itemget.mp3"; // BGM itemget

    public static void setMainClass(Class mainClass) {
        StageDB.mainClass = mainClass;
    }

    public static MediaPlayer getMainSound() {
        if (mainSound == null) {
            try {
                Media m = new Media(new File(soundDirName + mainSoundFileName).toURI().toString());
                MediaPlayer mp = new MediaPlayer(m);
                mp.setCycleCount(MediaPlayer.INDEFINITE); // loop play
                mp.setRate(1.0); // 1.0 = normal speed
                mp.setVolume(0.5); // volume from 0.0 to 1.0
                mainSound = mp;
            } catch (Exception io) {
                System.err.print(io.getMessage());
            }
        }
        return mainSound;
    }

    public static MediaPlayer getGameOverSound() {
        if (gameOverSound == null) {
            try {
                // please write down the code for playing gameover sound
                Media m =
                        new Media(
                                new File(soundDirName + gameoverSoundFileName).toURI().toString());
                MediaPlayer mp = new MediaPlayer(m);
                // mp.setCycleCount(MediaPlayer.INDEFINITE); // loop play
                mp.setRate(1.0); // 1.0 = normal speed
                mp.setVolume(0.5); // volume from 0.0 to 1.0
                gameOverSound = mp;
            } catch (Exception io) {
                System.err.print(io.getMessage());
            }
        }
        return gameOverSound;
    }

    public static MediaPlayer getItemGetSound() {
        if (itemGetSound == null) {
            try {
                Media m =
                        new Media(new File(soundDirName + itemGetSoundFileName).toURI().toString());
                MediaPlayer mp = new MediaPlayer(m);
                // mp.setCycleCount(MediaPlayer.INDEFINITE); // loop play
                mp.setRate(1.0); // 1.0 = normal speed
                mp.setVolume(0.5); // volume from 0.0 to 1.0
                itemGetSound = mp;
            } catch (Exception io) {
                System.err.print(io.getMessage());
            }
        }
        return itemGetSound;
    }

    public static Stage getMainStage() {
        if (mainStage == null) {
            try {
                FXMLLoader loader = new FXMLLoader(mainClass.getResource("MapGame.fxml"));
                VBox root = loader.load();
                Scene scene = new Scene(root);
                mainStage = new Stage();
                mainStage.setScene(scene);
            } catch (IOException ioe) {
                System.err.println(ioe);
            }
        }
        return mainStage;
    }

    public static Stage getGameOverStage() {
        if (gameOverStage == null) {
            try {
                System.out.println("StageDB:getGameOverStage()");
                FXMLLoader loader = new FXMLLoader(mainClass.getResource("MapGameOver.fxml"));
                VBox root = loader.load();
                Scene scene = new Scene(root);
                gameOverStage = new Stage();
                gameOverStage.setScene(scene);
            } catch (IOException ioe) {
                System.err.println(ioe);
            }
        }
        return gameOverStage;
    }
}
