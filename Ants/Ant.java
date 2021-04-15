import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Ant {
    private float x;
    private float y;
    private Vector direction;
    private Random r;
    private float speed;
    private float wanderStrength;
    private Grid grid;
    private AntSim antSim;
    private boolean isCarringFood;
    private Sensor leftSpot, centerSpot, rightSpot;
    private Detector detector;
    private boolean lookingForObjects;

    public Ant(int x, int y, Grid grid, AntSim antSim) {
        this.antSim = antSim;
        this.x = x;
        this.y = y;
        this.grid = grid;
        this.isCarringFood = false;
        r = new Random();
        this.direction = new Vector(randomFloat(-1, 1), randomFloat(-1, 1));
        this.direction.normalize();
        leftSpot = new Sensor(-Math.PI / 7, direction, this.x, this.y, grid, 1.5f, antSim.getScale());
        centerSpot = new Sensor(0.0, direction, this.x, this.y, grid, 1.5f, antSim.getScale());
        rightSpot = new Sensor(Math.PI / 7, direction, this.x, this.y, grid, 1.5f, antSim.getScale());

        speed = 2.0f;
        wanderStrength = 0.3f;
        this.detector = new Detector(Math.PI / 2, direction, this.x, this.y, 8, grid, Detector.FOOD);
        lookingForObjects = true;
    }

    void update() {
        if (lookingForObjects) {
            detector.update(direction, this.x, this.y);
            lookForTrails();
            randomizeDirection();
            lookForObjects();
        } else {
            proximtyDetection();
        }

        bounceOfWalls();
        move();
        leavePheromones();
    }

    private void proximtyDetection() {
        if (detectProximity()) {
            turnBack();
            lookingForObjects = true;
            isCarringFood = !isCarringFood;
        }
    }

    private boolean detectProximity() {

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (isCarringFood) {
                    if (grid.checkSquareForObject((int) this.x + i, (int) this.y + j, Detector.NEST))
                        return true;
                } else {
                    if (grid.checkSquareForObject((int) this.x + i, (int) this.y + j, Detector.FOOD))
                        return true;
                }
            }
        }
        return false;
    }

    private void leavePheromones() {
        if (isCarringFood) {
            grid.leaveTrail((int) x, (int) y, 1);
        } else {
            grid.leaveTrail((int) x, (int) y, 0);
        }
    }

    private void lookForObjects() {
        if (isCarringFood) {
            if (detector.detectObject(Detector.NEST)) {
                direction = Vector.calculateVector((int) this.x, (int) this.y, detector.foundPoint.x,
                        detector.foundPoint.y);
                direction.normalize();
                lookingForObjects = false;
            }
        } else {
            if (detector.detectObject(Detector.FOOD)) {
                direction = Vector.calculateVector((int) this.x, (int) this.y, detector.foundPoint.x,
                        detector.foundPoint.y);
                direction.normalize();
                lookingForObjects = false;
            }
        }
    }

    private void lookForTrails() {
        if (lookForPheromones(isCarringFood)) {
            if (isCarringFood) {
                if (centerSpot.value < Math.max(leftSpot.value, rightSpot.value)) {
                    if (leftSpot.value > rightSpot.value) {
                        turnLeft();
                    } else if (rightSpot.value > leftSpot.value) {
                        turnRight();
                    }
                }
            } else {
                if (centerSpot.value < Math.max(leftSpot.value, rightSpot.value)) {
                    if (leftSpot.value > rightSpot.value) {
                        turnLeft();
                    } else if (rightSpot.value > leftSpot.value) {
                        turnRight();
                    }
                }
            }
        }
    }

    private void bounceOfWalls() {
        float tempX = this.x + direction.x * speed;
        float tempY = this.y + direction.y * speed;
        while (tempX < 0 || tempX > antSim.getScaledWidth() - 1 || tempY < 0 || tempY > antSim.getScaledHeight() - 1) {
            direction.x = randomFloat(-1, 1);
            direction.y = randomFloat(-1, 1);
            direction.normalize();
            tempX = this.x + direction.x * speed;
            tempY = this.y + direction.y * speed;
        }
    }

    private void randomizeDirection() {
        direction.x += randomFloat(-1, 1) * wanderStrength;
        direction.y += randomFloat(-1, 1) * wanderStrength;
        direction.normalize();
    }

    private void turnLeft() {
        // -0.785398 rad = -45 degrees
        // -0.436332 rad = -25 degrees
        direction.x = (float) (direction.x * Math.cos(-0.436332) - direction.y * Math.sin(-0.436332));
        direction.y = (float) (direction.x * Math.sin(-0.436332) + direction.y * Math.cos(-0.436332));
    }

    private void turnRight() {
        // 0.785398 rad = 45 degrees
        // 0.436332 rad = 25 degrees
        direction.x = (float) (direction.x * Math.cos(0.436332) - direction.y * Math.sin(0.436332));
        direction.y = (float) (direction.x * Math.sin(0.436332) + direction.y * Math.cos(0.436332));
    }

    private void turnBack() {
        direction.x = (float) (direction.x * Math.cos(Math.PI) - direction.y * Math.sin(Math.PI));
        direction.y = (float) (direction.x * Math.sin(Math.PI) + direction.y * Math.cos(Math.PI));
    }

    private boolean lookForPheromones(boolean pheromoneType) {
        leftSpot.update(direction, this.x, this.y, isCarringFood);
        centerSpot.update(direction, this.x, this.y, isCarringFood);
        rightSpot.update(direction, this.x, this.y, isCarringFood);

        if (pheromoneType) {
            if (leftSpot.value > 0 || centerSpot.value > 0 || rightSpot.value > 0) {
                return true;
            }
        } else {
            if (leftSpot.value > 0 || centerSpot.value > 0 || rightSpot.value > 0) {
                return true;
            }
        }
        return false;
    }

    private void move() {
        this.x += direction.x * speed;
        this.y += direction.y * speed;
    }

    void draw(Graphics g, int scale) {
        if (isCarringFood) {
            g.setColor(Color.yellow);
        } else {
            g.setColor(Color.white);
        }

        g.fillRect((int) this.x * scale, (int) this.y * scale, scale, scale);
        // g.setColor(Color.red);
        // // g.fillRect((int)(this.x * scale + direction.x), (int)(this.y * scale +
        // // direction.y * scale), scale, scale);
        // g.fillRect(leftSpot.spotX * scale, leftSpot.spotY * scale, scale, scale);
        // g.fillRect(rightSpot.spotX * scale, rightSpot.spotY * scale, scale, scale);
        // g.fillRect(centerSpot.spotX * scale, centerSpot.spotY * scale, scale, scale);

        // scale);
        // g.fillRect(detector.leftSpot.x * scale, detector.leftSpot.y * scale, scale,
        // scale);
        // g.fillRect(detector.rightSpot.x * scale, detector.rightSpot.y * scale, scale,
        // scale);

    }

    private float randomFloat(float min, float max) {
        return min + r.nextFloat() * (max - min);
    }

}

class Vector {
    public float x;
    public float y;

    public static Vector calculateVector(int startX, int startY, int endX, int endY) {
        return new Vector(endX - startX, endY - startY);
    }

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector() {
        this.x = 0;
        this.y = 0;
    }

    public void normalize() {
        float length = (float) Math.sqrt(this.x * this.x + this.y * this.y);
        if (length != 0) {
            this.x = this.x / length;
            this.y = this.y / length;
        }
    }
}

/** Senses pheromones */
class Sensor {
    int spotX;
    int spotY;
    Vector sensDirection;
    double turnInRads;
    int value;
    Grid grid;
    float length;
    int[][] pheromones;

    public Sensor(double rads, Vector antDirection, float x, float y, Grid grid, float length, int scale) {
        this.grid = grid;
        turnInRads = rads;
        this.length = length * scale;
        value = 0;

        sensDirection = new Vector((float) (antDirection.x * Math.cos(rads) - antDirection.x * Math.sin(rads)),
                (float) (antDirection.x * Math.sin(rads) + antDirection.x * Math.cos(rads)));

        this.spotX = (int) (x + sensDirection.x * this.length);
        this.spotY = (int) (y + sensDirection.y * this.length);
    }

    public void update(Vector antDirection, float x, float y, boolean isCarringFood) {

        this.sensDirection.x = (float) (antDirection.x * Math.cos(turnInRads) - antDirection.y * Math.sin(turnInRads));
        this.sensDirection.y = (float) (antDirection.x * Math.sin(turnInRads) + antDirection.y * Math.cos(turnInRads));

        this.spotX = (int) (x + sensDirection.x * this.length);
        this.spotY = (int) (y + sensDirection.y * this.length);

        pheromones = (isCarringFood) ? grid.searchPheromone : grid.foodPheromone;

        value = 0;
        getPheromonesInCircle();
    }

    private void getPheromonesInCircle() {
        int top = (int) Math.floor(spotY - 1);
        int bottom = (int) Math.ceil(spotY + 1);
        int left = (int) Math.floor(spotX - 1);
        int right = (int) Math.ceil(spotX + 1);

        for (int y = top; y <= bottom; y++) {
            for (int x = left; x <= right; x++) {
                if (insideCircle(x, y, 2)) {
                    if (!grid.outOfBounds(x, y)) {
                        value += pheromones[x][y];
                    }
                }
            }
        }
    }

    private boolean insideCircle(int tileX, int tileY, float radius) {
        float dx = spotX - tileX;
        float dy = spotY - tileY;
        float distance_squared = dx * dx + dy * dy;
        return distance_squared <= radius * radius;
    }
}

class Point {
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

/** Detects nest/food */
class Detector {
    public static int FOOD = 2;
    public static int NEST = 1;
    // public List<Point> detectorPoints;
    public Point leftSpot, rightSpot, foundPoint;
    public Vector leftVector, rightVector;
    public int type;
    private double angle;
    private double tempDirX;
    private double tempDirY;
    private int length;
    private Grid grid;

    public Detector(double angle, Vector antDirection, float x, float y, int lenght, Grid grid, int type) {
        this.angle = angle;
        this.length = lenght;
        this.grid = grid;
        this.type = type;
        this.foundPoint = new Point(-1, -1);
        tempDirX = antDirection.x * lenght;
        tempDirY = antDirection.y * length;

        leftVector = new Vector((float) (tempDirX * Math.cos(-angle / 2) - tempDirY * Math.sin(-angle / 2)),
                (float) (tempDirX * Math.sin(-angle / 2) + tempDirY * Math.cos(-angle / 2)));

        rightVector = new Vector((float) (tempDirX * Math.cos(angle / 2) - tempDirY * Math.sin(angle / 2)),
                (float) (tempDirX * Math.sin(angle / 2) + tempDirY * Math.cos(angle / 2)));

        this.leftSpot = new Point((int) (x + leftVector.x), (int) (y + leftVector.y));
        this.rightSpot = new Point((int) (x + rightVector.x), (int) (y + rightVector.y));

    }

    public void update(Vector antDirection, float x, float y) {
        tempDirX = antDirection.x * length;
        tempDirY = antDirection.y * length;

        leftVector.x = (float) (tempDirX * Math.cos(-angle / 2) - tempDirY * Math.sin(-angle / 2));
        leftVector.y = (float) (tempDirX * Math.sin(-angle / 2) + tempDirY * Math.cos(-angle / 2));

        rightVector.x = (float) (tempDirX * Math.cos(angle / 2) - tempDirY * Math.sin(angle / 2));
        rightVector.y = (float) (tempDirX * Math.sin(angle / 2) + tempDirY * Math.cos(angle / 2));

        leftSpot.x = (int) (x + leftVector.x);
        leftSpot.y = (int) (y + leftVector.y);

        rightSpot.x = (int) (x + rightVector.x);
        rightSpot.y = (int) (y + rightVector.y);

        // detectorPoints.clear();
        // setLinePoints(leftSpot.x, leftSpot.y, rightSpot.x, rightSpot.y, grid, type);
    }

    /** Detec 1 - nest, 2 - food */
    public boolean detectObject(int type) {
        int x0 = leftSpot.x;
        int y0 = leftSpot.y;
        int x1 = rightSpot.x;
        int y1 = rightSpot.y;

        int dx = Math.abs(x1 - x0);
        int sx = (x0 < x1) ? 1 : -1;
        int dy = -Math.abs(y1 - y0);
        int sy = (y0 < y1) ? 1 : -1;
        int err = dx + dy;
        int e2, x = x0, y = y0;
        while (true) {
            // detectorPoints.add(new Point(x, y));
            if (checkPoint(x, y, grid, type)) {
                foundPoint.set(x, y);
                return true;
            }

            if (x == x1 && y == y1)
                break;

            e2 = 2 * err;
            if (e2 >= dy) {
                err += dy;
                x += sx;
            }
            if (e2 <= dx) {
                err += dx;
                y += sy;
            }
        }
        foundPoint.set(-1, -1);
        return false;
    }

    private boolean checkPoint(int x, int y, Grid grid, int type) {
        if (grid.outOfBounds(x, y)) {
            return false;
        }

        if (type == 1) {
            if (grid.squareType[x][y] == 1) {
                return true;
            }
        } else if (type == 2) {
            if (grid.squareType[x][y] == 2) {
                return true;
            }
        } else {
            System.out.println("Unknown square type");
        }
        return false;
    }
}
