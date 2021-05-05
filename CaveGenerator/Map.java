import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

public class Map extends JPanel implements ActionListener {
    private int[][] caveMap;
    private int width, height;
    private Random r;
    private int fillPercent;
    private int scale;

    public Map(int width, int height, long seed, int fillPercent, int scale) {
        caveMap = new int[width / scale][height / scale];
        this.width = width / scale;
        this.height = height / scale;
        this.scale = scale;
        r = new Random(seed);
        this.fillPercent = fillPercent;
        fillMap();
        for (int i = 0; i < 5; i++) {

            smoothMap();
        }
    }

    private void fillMap() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x == 0 || x == width - 1 || y == 0 || y == height - 1) {
                    caveMap[x][y] = 1;
                } else {
                    caveMap[x][y] = (r.nextInt(100) <= fillPercent) ? 1 : 0;
                }
            }
        }
    }

    private void smoothMap() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int wallTiles = getSurroundingWallCount(x, y);
                if (wallTiles > 4) {
                    caveMap[x][y] = 1;
                } else if (wallTiles < 4) {
                    caveMap[x][y] = 0;
                }
            }
        }
    }

    private int getSurroundingWallCount(int gridX, int gridY) {
        int wallCount = 0;
        for (int x = gridX - 1; x <= gridX + 1; x++) {
            for (int y = gridY - 1; y <= gridY + 1; y++) {
                if (x >= 0 && x < width && y >= 0 && y < height) {
                    if (x != gridX || y != gridY) {
                        wallCount += caveMap[x][y];
                    }
                } else {
                    wallCount++;
                }
            }
        }
        return wallCount;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                g.setColor((caveMap[x][y] < 1) ? Color.white : Color.black);
                g.fillRect(x * scale, y * scale, scale, scale);
            }
        }
        super.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // smoothMap();
    }
}
