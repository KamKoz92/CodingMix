import java.awt.Color;
import java.awt.Graphics;

public class Grid {
    
    private int width;
    private int height;
    // public int[][] searchPheromone;
    public int[][] foodPheroemone;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        // searchPheromone = new int[width][height];
        foodPheroemone = new int[width][height];

        Color temp = new Color(2,2,2,2);
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                // searchPheromone[i][j] = 0;
                foodPheroemone[i][j] = 0;
            }
        }

    }
    public void evaporation() {
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                foodPheroemone[i][j] -= 5;
                if(foodPheroemone[i][j] < 0) foodPheroemone[i][j] = 0;
            }
        }
    }
    
    public void drawTrails(Graphics g) {
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                if(foodPheroemone[i][j] > 0) {
                    g.setColor(new Color(50,110,210,foodPheroemone[i][j]));
                    g.fillRect(i*2, j*2, 2, 2);
                }
            }
        }
    }
    public void leaveTrail(int x, int y) {
        // foodPheroemone[x][y] += 50;
        // if(foodPheroemone[x][y] > 254) foodPheroemone[x][y] = 254;
        foodPheroemone[x][y] = 254;
    }
}
