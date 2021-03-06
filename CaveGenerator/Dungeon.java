
// import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.*;
import javax.swing.JPanel;

public class Dungeon extends JPanel implements ActionListener {
    private int[][] dungeonTiles;
    private int width;
    private int height;
    private int scale;
    private Set<Point> randomWalk;
    private boolean randomStartPosition;
    private DungeonTileset dungeonTileset;

    public Dungeon(int width, int height, int scale, boolean randomStartPosition) {
        this.width = width / scale;
        this.height = height / scale;
        this.scale = scale;
        this.randomStartPosition = randomStartPosition;
        dungeonTiles = new int[this.width][this.height];
        randomWalk = new HashSet<Point>();

        fillTiles();
        randomWalkGeneration(new Point(this.width / 2, this.height / 2), 50, 100, new Point(0, 0),
                new Point(this.width, this.height));

        dungeonTileset = new DungeonTileset("Dungeon_Tileset.png");
    }

    private void randomWalkGeneration(Point startingPoint, int iterations, int walkLength, Point firstBoundPoint,
            Point secondBoundPoint) {
        if (randomStartPosition) {
            if (randomWalk.isEmpty()) {
                startingPoint = new Point(width / 2, height / 2);
            } else {
                startingPoint = (Point) randomWalk.toArray()[Point.r.nextInt(randomWalk.size())];
            }
        }

        for (int i = 0; i < iterations; i++) {
            randomWalk.addAll(simpleRandomWalk(startingPoint, walkLength, firstBoundPoint, secondBoundPoint));
        }

        System.out.println("Path Created");
        for (Point p : randomWalk) {
            dungeonTiles[p.x][p.y] = 0;
        }
        System.out.println("Path Engraved");
    }

    private void fillTiles() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                dungeonTiles[x][y] = 1;
            }
        }
    }

    private HashSet<Point> simpleRandomWalk(Point startPosition, int walkLength, Point firstBoundPoint,
            Point secondBoundPoint) {
        HashSet<Point> path = new HashSet<Point>();
        path.add(startPosition);
        Point previousPosition = startPosition;
        for (int i = 0; i < walkLength; i++) {
            Point newPosition = Point.add(previousPosition, Point.getRandomCardinalDirection());

            if (isPointInBounds(newPosition, firstBoundPoint, secondBoundPoint)) {
                path.add(newPosition);
                previousPosition = newPosition;
            }
        }
        return path;
    }

    private boolean isPointInBounds(Point point, Point firstBoundPoint, Point secondBoundPoint) {
        if (firstBoundPoint.x <= point.x && point.x < secondBoundPoint.x && firstBoundPoint.y <= point.y
                && point.y < secondBoundPoint.y) {
            return true;
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawDungeon(g);
        super.repaint();
    }

    private void drawDungeon(Graphics g) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (dungeonTiles[x][y] == 0) {
                    g.drawImage(dungeonTileset.getTile("basicFloor"), x * scale, y * scale, null);
                } else {
                    g.drawImage(dungeonTileset.getTile(getWallType(x, y)), x * scale, y * scale, null);
                }

            }
        }
    }

    private String getWallType(int x, int y) {

        if (!tileSolid(x, y + 1)) {
            return "topWall";
        }
        if (!tileSolid(x, y - 1)) {
            return "bottomWall";
        }
        if (!tileSolid(x - 1, y)) {
            return "rightWall";
        }
        if (!tileSolid(x + 1, y)) {
            return "leftWall";
        }
        

        return "basicWall";
    }

    private boolean tileSolid(int x, int y) {
        if (0 <= x && x < width && 0 <= y && y < height) {
            if (dungeonTiles[x][y] == 0) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }

    }

    private static class Point {
        private int x;
        private int y;
        private static Random r = new Random();

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;

            }
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof Point)) {
                return false;
            }
            Point p = (Point) obj;
            return x == p.x && y == p.y;
        }

        @Override
        public int hashCode() {
            int hashCode = 17;
            hashCode = 31 * hashCode + x;
            hashCode = 31 * hashCode + y;
            return hashCode;
        }

        public static Point add(Point a, Point b) {
            return new Point(a.x + b.x, a.y + b.y);

        }

        public static List<Point> cardinalDirectionList = new ArrayList<Point>(
                Arrays.asList(new Point(0, 1), new Point(1, 0), new Point(0, -1), new Point(-1, 0)));

        public static Point getRandomCardinalDirection() {
            return cardinalDirectionList.get(r.nextInt(cardinalDirectionList.size()));
        }
    }
}
