import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.JPanel;

public class Dungeon extends JPanel implements ActionListener {
    private int[][] dungeonTiles;
    private int width;
    private int height;
    private int scale;
    private Set<Point> randomWalk;
    private boolean randomStartPosition;

    public Dungeon(int width, int height, int scale, boolean randomStartPosition) {
        this.width = width / scale;
        this.height = height / scale;
        this.scale = scale;
        this.randomStartPosition = randomStartPosition;
        dungeonTiles = new int[this.width][this.height];
        randomWalk = new HashSet<Point>();
        fillTiles();
        

    }

    private void fillTiles() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                dungeonTiles[x][y] = 1;
            }
        }
    }

    private HashSet<Point> simpleRandomWalk(Point startPosition, int walkLength) {
        HashSet<Point> path = new HashSet<Point>();
        path.add(startPosition);
        Point previousPosition = startPosition;
        for (int i = 0; i < walkLength; i++) {
            Point newPosition = Point.add(previousPosition, Point.getRandomCardinalDirection());
            path.add(newPosition);
            previousPosition = newPosition;
        }

        return path;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Point startingPoint;
        if(randomStartPosition) {
            if(randomWalk.isEmpty()) {
                startingPoint = new Point(width/2, height/2);
            } else {
                startingPoint =(Point)randomWalk.toArray()[Point.r.nextInt(randomWalk.size())];
            }
        } else {
            startingPoint = new Point(width/2, height/2);
        }

        randomWalk.addAll(simpleRandomWalk(startingPoint, 25));
        System.out.println("Path Created");
        for (Point p : randomWalk) {
        dungeonTiles[p.x][p.y] = 0;
        }
        System.out.println("Path Engraved");

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
                g.setColor((dungeonTiles[x][y] < 1) ? Color.white : Color.black);
                g.fillRect(x * scale, y * scale, scale, scale);
            }
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
