import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MapData {
    public static final int TYPE_SPACE = 0;
    public static final int TYPE_WALL = 1;
    public static final int TYPE_GOAL = 2;
    public static final int TYPE_TIME = 3;
    public static final int TYPE_OMAMORI = 4;
    private static final String mapImageDir = "png/";
    private static final String mapImageFiles[] = {
        "SPACE.png", "WALL.png", "GOAL.png", "TIME.png", "OMAMORI.png"
    };
    public static int amountOmamori = 1;
    public static int amountWatch = 1;
    public static int amountGoal = 1;

    private Image[] mapImages;
    private ImageView[][] mapImageViews;
    private int[][] maps;
    private int width; // width of the map
    private int height; // height of the map
    private int goalX;
    private int goalY;

    MapData(int x, int y) {
        amountOmamori = 1;
        amountWatch = 1;
        amountGoal = 1;
        mapImages = new Image[5];
        mapImageViews = new ImageView[y][x];
        for (int i = 0; i < 5; i++) {
            mapImages[i] = new Image(mapImageDir + mapImageFiles[i]);
        }

        width = x;
        height = y;
        maps = new int[y][x];

        fillMap(MapData.TYPE_WALL);
        digMap(1, 3);
        decideGoal(13, 19);
        setImageViews();
    }

    // fill two-dimentional arrays with a given number (maps[y][x])
    private void fillMap(int type) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                maps[y][x] = type;
            }
        }
    }

    // dig walls for making roads
    private void digMap(int x, int y) {
        setMap(x, y, MapData.TYPE_SPACE);

        int[][] dl = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};
        int[] tmp;

        for (int i = 0; i < dl.length; i++) {
            int r = (int) (Math.random() * dl.length);
            tmp = dl[i];
            dl[i] = dl[r];
            dl[r] = tmp;
        }

        for (int i = 0; i < dl.length; i++) {
            int dx = dl[i][0];
            int dy = dl[i][1];

            if (getMap(x + dx * 2, y + dy * 2) == MapData.TYPE_WALL) {
                setMap(x + dx, y + dy, MapData.TYPE_SPACE);
                digMap(x + dx * 2, y + dy * 2);
            }
        }

        for (x = 1; x < 14; x++) {
            for (y = 1; y < 20; y++) {
                if (maps[x][y - 1] + maps[x - 1][y] + maps[x + 1][y] + maps[x][y + 1] == 3
                        && maps[x][y] == 0
                        && (x != 1 && y != 1)
                        && (x != 13 && y != 19)) {
                    double d123 = (double) (Math.random());
                    if (amountWatch < 1) {
                        d123 = 0.9;
                    }
                    if (amountOmamori < 1) {
                        d123 = 0.1;
                    }
                    if (d123 < 0.5) {
                        if (amountWatch > 0) {
                            double dwatch = (double) (Math.random());
                            if (dwatch < 0.5) {
                                setMap(y, x, MapData.TYPE_TIME);
                                amountWatch--;
                            }
                        }
                    } else {
                        if (amountOmamori > 0) {
                            double domamori = (double) (Math.random());
                            if (domamori < 0.5) {
                                setMap(y, x, MapData.TYPE_OMAMORI);
                                amountOmamori--;
                            }
                        }
                    }
                }
            }
            if (x == 13 && y == 19) {
                if (amountWatch > 0 || amountOmamori > 0) {
                    x = 1;
                    y = 1;
                }
            }
        }
    }

    // ゴールの位置を決定するメソッドです
    private void decideGoal(int x, int y) {
        for (x = 13; x > 0; x--) {
            for (y = 19; y > 0; y--) {
                if (maps[x][y - 1] + maps[x - 1][y] + maps[x + 1][y] + maps[x][y + 1] == 3
                        && maps[x][y] == 0) {
                    if (amountGoal == 1) {
                        setMap(y, x, MapData.TYPE_GOAL); // x,yにゴールマスを描画
                        amountGoal--;
                        goalX = y;
                        goalY = x;
                    }
                }
            }
        }
    }

    public int getm(int x, int y) {
        return maps[y][x];
    }

    public void deleteitem(int x, int y) {
        setMap(x, y, MapData.TYPE_SPACE);
        setImageViews();
    }

    public int getGoalX() {
        return goalX;
    }

    public int getGoalY() {
        return goalY;
    }

    public int getMap(int x, int y) {
        if (x < 0 || width <= x || y < 0 || height <= y) {
            return -1;
        }
        return maps[y][x];
    }

    public void setMap(int x, int y, int type) {
        if (x < 1 || width <= x - 1 || y < 1 || height <= y - 1) {
            return;
        }
        maps[y][x] = type;
    }

    public ImageView getImageView(int x, int y) {
        return mapImageViews[y][x];
    }

    public void setImageViews() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                mapImageViews[y][x] = new ImageView(mapImages[maps[y][x]]);
            }
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
