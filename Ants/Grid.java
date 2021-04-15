import java.awt.Color;
import java.awt.Graphics;

public class Grid {
    public int width;
    public int height;
    public float[][] searchPheromone;
    public float[][] foodPheromone;
    /** 0 - free square, 1 - nest, 2 -food */
    public int[][] squareType;
    private int scale;

    public Grid(int width, int height, int scale) {
        this.width = width;
        this.height = height;
        this.scale = scale;
        searchPheromone = new float[width][height];
        foodPheromone = new float[width][height];
        squareType = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                searchPheromone[i][j] = 0;
                foodPheromone[i][j] = 0;
                squareType[i][j] = 0;
            }
        }
    }

    public void evaporation() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                foodPheromone[i][j] -= 3f;
                foodPheromone[i][j] = clamp(0, 255, foodPheromone[i][j]);

                searchPheromone[i][j] -= 3f;
                searchPheromone[i][j] = clamp(0, 255, searchPheromone[i][j]);
            }
        }
    }

    public void drawGridElements(Graphics g) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (searchPheromone[i][j] > 0) {
                    // light blue
                    g.setColor(new Color(50, 110, 210, (int)clamp(0,254,searchPheromone[i][j])));
                    g.fillRect(i * scale, j * scale, scale, scale);
                }
                if (foodPheromone[i][j] > 0) {
                    // light red
                    g.setColor(new Color(220, 50, 10, (int)clamp(0,254,foodPheromone[i][j])));
                    g.fillRect(i * scale, j * scale, scale, scale);
                }
                if (squareType[i][j] == 2) {
                    g.setColor(Color.green);
                    g.fillRect(i * scale, j * scale, scale, scale);
                }
            }
        }
    }

    /** Set pheromone trail 0-search pheromone, 1-food phereomone */
    public void leaveTrail(int x, int y, int trailType, float power) {
        if (!(x < 0 || x > width - 1 || y < 0 || y > height - 1)) {
            if (trailType == 0) {
                searchPheromone[x][y] = 254.0f * (float)Math.exp(-0.005 * power);
                // foodPheromone[x][y] = clamp(0, 254, foodPheromone[x][y]);
            } else if (trailType == 1) {
                foodPheromone[x][y] = 254.0f * (float)Math.exp(-0.005 * power);
                // foodPheromone[x][y] = clamp(0, 254, foodPheromone[x][y]);
            }
        }
        
    }

    public void addNest(Nest nest) {
        for (int i = nest.x; i < nest.x + nest.size; i++) {
            for (int j = nest.y; j < nest.y + nest.size; j++) {
                squareType[i][j] = 1;
            }
        }
    }

    public void addFood(int x, int y, int size) {
        // check for collision problem with other types, someday..//
        for (int i = x; i < x + size; i++) {
            for (int j = y; j < y + size; j++) {
                squareType[i][j] = 2;
            }
        }
    }

    public boolean outOfBounds(int x, int y) {
        if (0 <= x && x < width && 0 <= y && y < height) {
            return false;
        }
        return true;
    }

    public boolean checkSquareForObject(int x, int y, int type) {
        if (!outOfBounds(x, y)) {
            if (squareType[x][y] == type) {
                return true;
            }
        }
        return false;
    }
    private float clamp(float min, float max, float val) {
        if(val < min)
            val = min;
        else if(val > max)
            val = max;
        return val;
    }
}
