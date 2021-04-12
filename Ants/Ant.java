import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Ant {
    private float x;
    private float y;
    private vector direction;
    private Random r;
    private float speed;
    private float wanderStrength;
    private Grid grid;
    private AntSim antSim;
    private boolean isCarringFood;
    private sensor leftSpot, centerSpot, rightSpot;

    public Ant(int x, int y, Grid grid, AntSim antSim, boolean food) {

        this.antSim = antSim;
        this.x = x;
        this.y = y;
        this.grid = grid;
        this.isCarringFood = food;
        r = new Random();
        this.direction = new vector(randomFloat(-1, 1), randomFloat(-1, 1));
        this.direction.normalize();

        leftSpot = new sensor(-Math.PI / 4, direction, this.x, this.y, grid);
        centerSpot = new sensor(0.0, direction, this.x, this.y, grid);
        rightSpot = new sensor(Math.PI / 4, direction, this.x, this.y, grid);

        speed = 2.0f;
        wanderStrength = 0.1f;
        grid.leaveTrail((int) x, (int) y, 0);
    }

    void update() {
        updateDirection();
        checkForObstacles();
        lookForTrails();
        move();
        if (isCarringFood) {
            grid.leaveTrail((int) x, (int) y, 1);
        } else {
            grid.leaveTrail((int) x, (int) y, 0);
        }

    }

    private void lookForTrails() {
        if (lookForFoodPheromone()) {
            if (centerSpot.value < Math.max(leftSpot.value, rightSpot.value)) {
                if (leftSpot.value > rightSpot.value) {
                    turnLeft();
                } else if (rightSpot.value > leftSpot.value) {
                    turnRight();
                }
            }
        }
    }

    private void checkForObstacles() {
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

    private void updateDirection() {
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

    private boolean lookForFoodPheromone() {

        leftSpot.update(direction, this.x, this.y);
        centerSpot.update(direction, this.x, this.y);
        rightSpot.update(direction, this.x, this.y);

        if (leftSpot.value > 0 || centerSpot.value > 0 || rightSpot.value > 0) {
            return true;
        }
        return false;

    }

    private void move() {
        this.x += direction.x * speed;
        this.y += direction.y * speed;

    }

    void draw(Graphics g, int scale) {
        if(isCarringFood) {
            g.setColor(Color.yellow);
        } else {
            g.setColor(Color.white);
        }
        g.fillRect((int) this.x * scale, (int) this.y * scale, scale, scale);
    }

    private float randomFloat(float min, float max) {
        return min + r.nextFloat() * (max - min);
    }

}

// vector class
class vector {
    public float x;
    public float y;

    public vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public vector() {
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

// sensor class
class sensor {
    int spotX;
    int spotY;
    vector sensDirection;
    double turnInRads;
    int value;
    double tempDirX;
    double tempDirY;
    Grid grid;

    public sensor(double rads, vector antDirection, float x, float y, Grid grid) {
        value = 0;
        this.grid = grid;
        turnInRads = rads;
        tempDirX = antDirection.x;
        double tempDirY = antDirection.y;
        sensDirection = new vector((float) (tempDirX * Math.cos(rads) - tempDirY * Math.sin(rads)) * 2,
                (float) (tempDirX * Math.sin(rads) + tempDirY * Math.cos(rads)) * 2);

        this.spotX = (int) (x + sensDirection.x);
        this.spotY = (int) (y + sensDirection.y);
    }

    public void update(vector antDirection, float x, float y) {
        tempDirX = antDirection.x * 3;
        tempDirY = antDirection.y * 3;
        this.sensDirection.x = (float) (tempDirX * Math.cos(turnInRads) - tempDirY * Math.sin(turnInRads));
        this.sensDirection.y = (float) (tempDirX * Math.sin(turnInRads) + tempDirY * Math.cos(turnInRads));

        this.spotX = (int) (x + sensDirection.x);
        this.spotY = (int) (y + sensDirection.y);
        value = 0;

        if (!(spotX < 0 || spotX > grid.width - 1 || spotY < 0 || spotY > grid.height - 1)) {
            value += grid.foodPheromone[spotX][spotY];
        }
        if (!(spotX + 1 < 0 || spotX + 1 > grid.width - 1 || spotY < 0 || spotY > grid.height - 1)) {
            value += grid.foodPheromone[spotX + 1][spotY];
        }
        if (!(spotX - 1 < 0 || spotX - 1 > grid.width - 1 || spotY < 0 || spotY > grid.height - 1)) {
            value += grid.foodPheromone[spotX - 1][spotY];
        }
        if (!(spotX < 0 || spotX > grid.width - 1 || spotY + 1 < 0 || spotY + 1 > grid.height - 1)) {
            value += grid.foodPheromone[spotX][spotY + 1];
        }
        if (!(spotX < 0 || spotX > grid.width - 1 || spotY - 1 < 0 || spotY - 1 > grid.height - 1)) {
            value += grid.foodPheromone[spotX][spotY - 1];
        }
    }

}