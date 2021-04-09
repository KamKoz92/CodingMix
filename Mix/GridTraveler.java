

public class GridTraveler {

    public static void main(String[] args) {
        long  start = System.nanoTime();
        System.out.println(gridTraveler2(10, 10));
        long elapsedTime = System.nanoTime() - start;
        System.out.println(elapsedTime);

        start = System.nanoTime();
        System.out.println(gridTraveler(10, 10));
        elapsedTime = System.nanoTime() - start;
        System.out.println(elapsedTime);

        
    }

    public static int gridTraveler(int n, int m, int[][] grid){
        if(n == 0 || m == 0) {
            return 0;
        } else if(n == 1 && m == 1) {
            return 1;
        }
        if(grid[n-1][m-1] > -1) return grid[n-1][m-1];
        grid[n-1][m-1] = gridTraveler(n-1, m, grid) + gridTraveler(n, m-1, grid);
        return grid[n-1][m-1];
    }
    public static int gridTraveler(int n, int m) {
        int[][] grid = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                grid[i][j] = -1;
            }
        }
        grid[n-1][m-1] = gridTraveler(n - 1, m, grid) + gridTraveler(n, m - 1, grid);
        return grid[n-1][m-1];
    }

    public static int gridTraveler2(int n, int m) {
        if(n == 0 || m == 0) {
            return 0;
        } else if(n == 1 && m == 1) {
            return 1;
        }
        return gridTraveler2(n-1, m) + gridTraveler(n, m-1);
    }
}
