import java.awt.Color;
import java.awt.Graphics;

public class Grid {

    public int width;
    public int height;
    public int[][] searchPheromone;
    public int[][] foodPheromone;

    public Grid(int width, int height) {
        this.width = width;//
        this.height = height;//
        searchPheromone = new int[width][height];
        foodPheromone = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                searchPheromone[i][j] = 0;
                foodPheromone[i][j] = 0;
            }
        }

    }

    public void evaporation() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                foodPheromone[i][j] -= 5;
                if (foodPheromone[i][j] < 0)
                    foodPheromone[i][j] = 0;

                searchPheromone[i][j] -= 5;
                if (searchPheromone[i][j] < 0)
                    searchPheromone[i][j] = 0;
            }
        }
    }

    
    public void drawTrails(Graphics g) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (searchPheromone[i][j] > 0) {
                    // light blue
                    g.setColor(new Color(50, 110, 210, searchPheromone[i][j]));
                    g.fillRect(i * 2, j * 2, 2, 2);
                }
                if (foodPheromone[i][j] > 0) {
                    // light red
                    g.setColor(new Color(220, 50, 10, foodPheromone[i][j])); 
                    g.fillRect(i * 2, j * 2, 2, 2);
                }
            }
        }
    }

    /** Set pheromone trail 0-search pheromone, 1-food phereomone */
    public void leaveTrail(int x, int y, int trailType) {
        if(!(x < 0 || x > width -1 || y < 0 || y > height -1)) {
            if(trailType == 0) {
                searchPheromone[x][y] = 254;    
            } else if(trailType == 1) {
                foodPheromone[x][y] = 254;
            }
            
        }
    }
}
