import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.fxml.FXML;

public class MapGameController implements Initializable {
    public MapData mapData;
    public MoveChara chara;
    public GridPane mapGrid;
    public ImageView[] mapImageViews;

    private Timeline timer;
    private Label label;

    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapData = new MapData(21, 15);
        chara = new MoveChara(1, 1, mapData);
        mapImageViews = new ImageView[mapData.getHeight() * mapData.getWidth()];
        for (int y = 0; y < mapData.getHeight(); y ++) {
            for (int x = 0; x < mapData.getWidth(); x ++) {
                int index = y * mapData.getWidth() + x;
                mapImageViews[index] = mapData.getImageView(x, y);
            }
        }
        drawMap(chara, mapData);
        timeLimit();
    }

    // Draw the map
    public void drawMap(MoveChara c, MapData m) {
        int cx = c.getPosX();
        int cy = c.getPosY();
        mapGrid.getChildren().clear();
        mapImageViews = new ImageView[mapData.getHeight() * mapData.getWidth()];
        for (int y = 0; y < mapData.getHeight(); y ++) {
            for (int x = 0; x < mapData.getWidth(); x ++) {
                int index = y * mapData.getWidth() + x;
                mapImageViews[index] = mapData.getImageView(x, y);
            }
        }
        for (int y = 0; y < mapData.getHeight(); y ++) {
            for (int x = 0; x < mapData.getWidth(); x ++) {
                int index = y * mapData.getWidth() + x;
                if (x == cx && y == cy) {
                    mapGrid.add(c.getCharaImageView(), x, y);
                }else {
                    mapGrid.add(mapImageViews[index], x, y);
                }
            }
        }
    }

    public void drawMap(int n, int x, int y){
        mapGrid.add(mapData.getImageView(x, y),x,y);
    }

    //制限時間を設定及び表示するメソッドです
    public void timeLimit() {
        if (timer != null) {
            timer.stop();
            label.setVisible(false);
        }

        label = new Label("30");

        timer = new Timeline(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                label.setText(String.valueOf(Integer.parseInt(label.getText()) - 1));

                if (Integer.parseInt(label.getText()) == 0) {
                    try {
                        System.out.println("ゲームオーバー");
                        StageDB.getMainStage().hide();
                        StageDB.getMainSound().stop();
                        StageDB.getGameOverSound().play();
                        StageDB.getGameOverStage().show();
                        mapData = new MapData(21, 15);
                        chara = new MoveChara(1, 1, mapData);
                        mapImageViews = new ImageView[mapData.getHeight() * mapData.getWidth()];
                        for (int y = 0; y < mapData.getHeight(); y ++) {
                            for (int x = 0; x < mapData.getWidth(); x ++) {
                                int index = y * mapData.getWidth() + x;
                                mapImageViews[index] = mapData.getImageView(x, y);
                            }
                        }
                        drawMap(chara, mapData);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }

                if (chara.timeCheck) {
                    label.setText(String.valueOf(Integer.parseInt(label.getText()) + 15));
                    chara.timeCheck = false;
                }
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        label.setFont(new Font("Arial",40));
        anchorPane.setTopAnchor(label, 1.0);
        anchorPane.setRightAnchor(label, 10.0);
        anchorPane.getChildren().add(label);
        timer.play();
    }

    //ゴール時にマップを生成orGameClear画面にするメソッド
    public void goalOrNot(int check) {
        if (check == 0) {
            try {
                System.out.println("GemeClear画面に移行");
                StageDB.getMainStage().hide();
                StageDB.getMainSound().stop();
                StageDB.getGameClearSound().play();
                StageDB.getGameClearStage().show();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } else if (check == 1) {
            System.out.println("マップを生成");
            mapData = new MapData(21, 15);
            chara = new MoveChara(1, 1, mapData);
            mapImageViews = new ImageView[mapData.getHeight() * mapData.getWidth()];
            for (int y = 0; y < mapData.getHeight(); y ++) {
                for (int x = 0; x < mapData.getWidth(); x ++) {
                    int index = y * mapData.getWidth() + x;
                    mapImageViews[index] = mapData.getImageView(x, y);
                }
            }
            drawMap(chara, mapData);
        }
    }

    // Get users' key actions
    public void keyAction(KeyEvent event) {
        KeyCode key = event.getCode();
        System.out.println("keycode:" + key);
        if (key == KeyCode.H) {
            leftButtonAction();
        } else if (key == KeyCode.J) {
            downButtonAction();
        } else if (key == KeyCode.K) {
            upButtonAction();
        } else if (key == KeyCode.L) {
            rightButtonAction();
        }
    }

    // Operations for going the cat up
    public void upButtonAction() {
        printAction("UP");
        chara.setCharaDirection(MoveChara.TYPE_UP);
        chara.move(0, -1);
        drawMap(chara, mapData);
        goalOrNot(chara.goalCheck(chara.getPosX(), chara.getPosY()));
    }

    // Operations for going the cat down
    public void downButtonAction() {
        printAction("DOWN");
        chara.setCharaDirection(MoveChara.TYPE_DOWN);
        chara.move(0, 1);
        drawMap(chara, mapData);
        goalOrNot(chara.goalCheck(chara.getPosX(), chara.getPosY()));
    }

    // Operations for going the cat right
    public void leftButtonAction() {
        printAction("LEFT");
        chara.setCharaDirection(MoveChara.TYPE_LEFT);
        chara.move(-1, 0);
        drawMap(chara, mapData);
        goalOrNot(chara.goalCheck(chara.getPosX(), chara.getPosY()));
    }

    // Operations for going the cat right
    public void rightButtonAction() {
        printAction("RIGHT");
        chara.setCharaDirection(MoveChara.TYPE_RIGHT);
        chara.move(1, 0);
        drawMap(chara, mapData);
        goalOrNot(chara.goalCheck(chara.getPosX(), chara.getPosY()));
    }

    @FXML
    public void func1ButtonAction(ActionEvent event) {
        try {
            System.out.println("func1");
            StageDB.getMainStage().hide();
            StageDB.getMainSound().stop();
            StageDB.getGameOverSound().play();
            StageDB.getGameOverStage().show();
            mapData = new MapData(21, 15);
            chara = new MoveChara(1, 1, mapData);
            mapImageViews = new ImageView[mapData.getHeight() * mapData.getWidth()];
            for (int y = 0; y < mapData.getHeight(); y ++) {
                for (int x = 0; x < mapData.getWidth(); x ++) {
                    int index = y * mapData.getWidth() + x;
                    mapImageViews[index] = mapData.getImageView(x, y);
                }
            }
            drawMap(chara, mapData);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    public void func2ButtonAction(ActionEvent event) {
        System.out.println("func2");
        mapData = new MapData(21, 15);
        chara = new MoveChara(1, 1, mapData);
        mapImageViews = new ImageView[mapData.getHeight() * mapData.getWidth()];
        for (int y = 0; y < mapData.getHeight(); y ++) {
            for (int x = 0; x < mapData.getWidth(); x ++) {
                int index = y * mapData.getWidth() + x;
                mapImageViews[index] = mapData.getImageView(x, y);
            }
        }
        drawMap(chara, mapData);
        timeLimit();
    }

    @FXML
    public void func3ButtonAction(ActionEvent event) {
        System.out.println("func3: Nothing to do");
    }

    @FXML
    public void func4ButtonAction(ActionEvent event) {
        System.out.println("func4: Nothing to do");
    }

    // Print actions of user inputs
    public void printAction(String actionString) {
        System.out.println("Action: " + actionString);
    }

}
