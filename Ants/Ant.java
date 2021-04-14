import java.awt.Color;
import java.awt.Graphics;
// import java.util.ArrayList;
// import java.util.List;
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
        leftSpot = new Sensor(-Math.PI / 4, direction, this.x, this.y, grid, 3);
        centerSpot = new Sensor(0.0, direction, this.x, this.y, grid, 3);
        rightSpot = new Sensor(Math.PI / 4, direction, this.x, this.y, grid, 3);

        speed = 2.0f;
        wanderStrength = 0.1f;
        this.detector = new Detector(Math.PI / 3, direction, this.x, this.y, 6, grid, Detector.FOOD);
        lookingForObjects = true;

        // proximitySensors = new Sensor[3];
        // proximitySensors[0] = new Sensor(-Math.PI / 6, direction, this.x, this.y, grid, 3.5f);
        // proximitySensors[1] = new Sensor(0.0, direction, this.x, this.y, grid, 2.5f);
        // proximitySensors[2] = new Sensor(Math.PI / 6, direction, this.x, this.y, grid, 3.5f);

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
        if(isCarringFood) {
            return grid.checkSquareForObject((int)this.x, (int) this.y, Detector.NEST);
        } else {
            return grid.checkSquareForObject((int)this.x, (int) this.y, Detector.FOOD);
        }
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
                System.out.println("Found nest");
                direction = Vector.calculateVector((int) this.x, (int) this.y, detector.foundPoint.x,
                        detector.foundPoint.y);
                direction.normalize();
                lookingForObjects = false;
            }
        } else {
            if (detector.detectObject(Detector.FOOD)) {
                System.out.println("Found food");
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
                if (centerSpot.searchPheromoneVal < Math.max(leftSpot.searchPheromoneVal,
                        rightSpot.searchPheromoneVal)) {
                    if (leftSpot.searchPheromoneVal > rightSpot.searchPheromoneVal) {
                        turnLeft();
                    } else if (rightSpot.searchPheromoneVal > leftSpot.searchPheromoneVal) {
                        turnRight();
                    }
                }
            } else {
                if (centerSpot.foodPheromoneVal < Math.max(leftSpot.foodPheromoneVal, rightSpot.foodPheromoneVal)) {
                    if (leftSpot.foodPheromoneVal > rightSpot.foodPheromoneVal) {
                        turnLeft();
                    } else if (rightSpot.foodPheromoneVal > leftSpot.foodPheromoneVal) {
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
        double tempX = direction.x;
        double tempY = direction.y;

        // -0.785398 rad = -45 degrees
        // -0.436332 rad = -25 degrees
        direction.x = (float) (tempX * Math.cos(-0.436332) - tempY * Math.sin(-0.436332));
        direction.y = (float) (tempX * Math.sin(-0.436332) + tempY * Math.cos(-0.436332));
    }

    private void turnRight() {
        double tempX = direction.x;
        double tempY = direction.y;

        // 0.785398 rad = 45 degrees
        // 0.436332 rad = 25 degrees
        direction.x = (float) (tempX * Math.cos(0.436332) - tempY * Math.sin(0.436332));
        direction.y = (float) (tempX * Math.sin(0.436332) + tempY * Math.cos(0.436332));
    }

    private void turnBack() {
        double tempX = direction.x;
        double tempY = direction.y;

        direction.x = (float) (tempX * Math.cos(Math.PI) - tempY * Math.sin(Math.PI));
        direction.y = (float) (tempX * Math.sin(Math.PI) + tempY * Math.cos(Math.PI));
    }

    private boolean lookForPheromones(boolean pheromoneType) {
        leftSpot.update(direction, this.x, this.y);
        centerSpot.update(direction, this.x, this.y);
        rightSpot.update(direction, this.x, this.y);

        if (pheromoneType) {
            if (leftSpot.searchPheromoneVal > 0 || centerSpot.searchPheromoneVal > 0
                    || rightSpot.searchPheromoneVal > 0) {
                return true;
            }
        } else {
            if (leftSpot.foodPheromoneVal > 0 || centerSpot.foodPheromoneVal > 0 || rightSpot.foodPheromoneVal > 0) {
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
        // g.fillRect(leftSpot.spotX * scale, leftSpot.spotY * scale , scale, scale);
        // g.fillRect(rightSpot.spotX * scale, rightSpot.spotY * scale , scale, scale);
        // g.fillRect(centerSpot.spotX * scale, centerSpot.spotY * scale , scale,
        // scale);
        // g.fillRect(detector.leftSpot.x * scale, detector.leftSpot.y * scale, scale,
        // scale);
        // g.fillRect(detector.rightSpot.x * scale, detector.rightSpot.y * scale, scale,
        // scale);
        // for (Point p : detector.detectorPoints) {
        // g.fillRect(p.x * scale, p.y * scale, scale, scale);
        // }
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
    int foodPheromoneVal;
    int searchPheromoneVal;
    double tempDirX;
    double tempDirY;
    Grid grid;
    float length;

    public Sensor(double rads, Vector antDirection, float x, float y, Grid grid, float length) {
        foodPheromoneVal = 0;
        searchPheromoneVal = 0;
        this.grid = grid;
        turnInRads = rads;
        this.length = length;
        tempDirX = antDirection.x * length;
        tempDirY = antDirection.y * length;
        sensDirection = new Vector((float) (tempDirX * Math.cos(rads) - tempDirY * Math.sin(rads)),
                (float) (tempDirX * Math.sin(rads) + tempDirY * Math.cos(rads)));

        this.spotX = (int) (x + sensDirection.x);
        this.spotY = (int) (y + sensDirection.y);
    }

    public void update(Vector antDirection, float x, float y) {
        tempDirX = antDirection.x * length;
        tempDirY = antDirection.y * length;

        this.sensDirection.x = (float) (tempDirX * Math.cos(turnInRads) - tempDirY * Math.sin(turnInRads));
        this.sensDirection.y = (float) (tempDirX * Math.sin(turnInRads) + tempDirY * Math.cos(turnInRads));

        this.spotX = (int) (x + sensDirection.x);
        this.spotY = (int) (y + sensDirection.y);

        foodPheromoneVal = 0;
        searchPheromoneVal = 0;

        if (0 <= spotX && spotX < grid.width && 0 <= spotY && spotY < grid.height) {
            foodPheromoneVal += grid.foodPheromone[spotX][spotY];
            searchPheromoneVal += grid.searchPheromone[spotX][spotY];
        }

        if (0 <= (spotX + 1) && (spotX + 1) < grid.width && 0 <= spotY && spotY < grid.height) {
            foodPheromoneVal += grid.foodPheromone[spotX + 1][spotY];
            searchPheromoneVal += grid.searchPheromone[spotX + 1][spotY];
        }

        if (0 <= (spotX - 1) && (spotX - 1) < grid.width && 0 <= spotY && spotY < grid.height) {
            foodPheromoneVal += grid.foodPheromone[spotX - 1][spotY];
            searchPheromoneVal += grid.searchPheromone[spotX - 1][spotY];
        }

        if (0 <= spotX && spotX < grid.width && 0 <= (spotY + 1) && (spotY + 1) < grid.height) {
            foodPheromoneVal += grid.foodPheromone[spotX][spotY + 1];
            searchPheromoneVal += grid.searchPheromone[spotX][spotY + 1];
        }

        if (0 <= spotX && spotX < grid.width && 0 <= (spotY - 1) && (spotY - 1) < grid.height) {
            foodPheromoneVal += grid.foodPheromone[spotX][spotY - 1];
            searchPheromoneVal += grid.searchPheromone[spotX][spotY - 1];
        }
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
        // detectorPoints = new ArrayList<Point>();

        leftVector = new Vector((float) (tempDirX * Math.cos(-angle / 2) - tempDirY * Math.sin(-angle / 2)),
                (float) (tempDirX * Math.sin(-angle / 2) + tempDirY * Math.cos(-angle / 2)));

        rightVector = new Vector((float) (tempDirX * Math.cos(angle / 2) - tempDirY * Math.sin(angle / 2)),
                (float) (tempDirX * Math.sin(angle / 2) + tempDirY * Math.cos(angle / 2)));

        this.leftSpot = new Point((int) (x + leftVector.x), (int) (y + leftVector.y));
        this.rightSpot = new Point((int) (x + rightVector.x), (int) (y + rightVector.y));

        // setLinePoints(leftSpot.x, leftSpot.y, rightSpot.x, rightSpot.y, grid, type);
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
