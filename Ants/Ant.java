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
    private int leftSpot, centerSpot, rightSpot;


    public Ant(int x, int y, Grid grid, AntSim antSim) {
        leftSpot = 0;
        centerSpot = 0;
        rightSpot = 0;
        this.antSim = antSim;
        this.x = x;
        this.y = y;
        this.grid = grid;
        this.isCarringFood = false;
        r = new Random();
        this.direction = new vector(randomFloat(-1,1),randomFloat(-1,1));
        this.direction.normalize();
        speed = 2.0f;
        wanderStrength = 0.1f;
        grid.leaveTrail((int)x,(int)y);
    }

    void update() {
        updateDirection();
        checkForObstacles();
        lookForTrails();
        move();
        grid.leaveTrail((int)x,(int)y);
    }

    
    private void lookForTrails() {
        if(lookForFoodPheromone()) {
            if(centerSpot < Math.max(leftSpot,rightSpot)) {
                if(leftSpot > rightSpot){
                    turnLeft();
                } else if(rightSpot > leftSpot) {
                    turnRight();
                }
            } 
        }
    }

    private void checkForObstacles() {
        float tempX = this.x + direction.x * speed;
        float tempY = this.y + direction.y * speed;
        while(tempX < 0 || tempX > antSim.getScaledWidth()-1 || tempY < 0 || tempY > antSim.getScaledHeight()-1){
            direction.x = randomFloat(-1,1);
            direction.y = randomFloat(-1,1);
            direction.normalize();
            tempX = this.x + direction.x * speed;
            tempY = this.y + direction.y * speed;
        }
    }

    private void updateDirection() {
        direction.x += randomFloat(-1,1) * wanderStrength;
        direction.y += randomFloat(-1,1) * wanderStrength;
        direction.normalize();
    }

    private void turnLeft() {
        double tempX = direction.x;
        double tempY = direction.y;

        // -1.5708 rad = -90 degrees
        direction.x =(float) (tempX * Math.cos(-1.5708) - tempY * Math.sin(-1.5708));
        direction.y =(float) (tempX * Math.sin(-1.5708) + tempY * Math.cos(-1.5708));
    }
    private void turnRight() {
        double tempX = direction.x;
        double tempY = direction.y;

        // -1.5708 rad = -90 degrees
        direction.x =(float) (tempX * Math.cos(1.5708) - tempY * Math.sin(1.5708));
        direction.y =(float) (tempX * Math.sin(1.5708) + tempY * Math.cos(1.5708));
    }

    private boolean lookForFoodPheromone() {
        int tempX = Math.round(direction.x);
        int tempY = Math.round(direction.y);


        if(tempX == -1 && tempY == -1) {
            leftSpot = getPheromone((int)this.x-1,(int)this.y);
            centerSpot = getPheromone((int)this.x-1,(int)this.y-1);
            rightSpot = getPheromone((int)this.x,(int)this.y-1);

        } else if(tempX == 0 && tempY == -1) {
            leftSpot = getPheromone((int)this.x-1,(int)this.y-1);
            centerSpot = getPheromone((int)this.x,(int)this.y-1);
            rightSpot = getPheromone((int)this.x+1,(int)this.y-1);

        } else if(tempX == 1 && tempY == -1) {
            leftSpot = getPheromone((int)this.x,(int)this.y-1);
            centerSpot = getPheromone((int)this.x+1,(int)this.y-1);
            rightSpot = getPheromone((int)this.x+1,(int)this.y);

        } else if(tempX == 1 && tempY == 0) {
            leftSpot = getPheromone((int)this.x+1,(int)this.y-1);
            centerSpot = getPheromone((int)this.x+1,(int)this.y);
            rightSpot = getPheromone((int)this.x+1,(int)this.y+1);

        } else if(tempX == 1 && tempY == 1) {
            leftSpot = getPheromone((int)this.x+1,(int)this.y);
            centerSpot = getPheromone((int)this.x+1,(int)this.y+1);
            rightSpot = getPheromone((int)this.x,(int)this.y+1);

        } else if(tempX == 0 && tempY == 1) {
            leftSpot = grid.foodPheroemone[(int)this.x+1][(int)this.y+1];
            centerSpot = grid.foodPheroemone[(int)this.x][(int)this.y+1];
            rightSpot = grid.foodPheroemone[(int)this.x-1][(int)this.y+1];

        } else if(tempX == -1 && tempY == 1) {
            leftSpot = grid.foodPheroemone[(int)this.x][(int)this.y+1];
            centerSpot = grid.foodPheroemone[(int)this.x-1][(int)this.y+1];
            rightSpot = grid.foodPheroemone[(int)this.x-1][(int)this.y];

        } else if(tempX == -1 && tempY == 0) {
            leftSpot = grid.foodPheroemone[(int)this.x-1][(int)this.y+1];
            centerSpot = grid.foodPheroemone[(int)this.x-1][(int)this.y];
            rightSpot = grid.foodPheroemone[(int)this.x-1][(int)this.y-1];
        } else {
            System.out.println("LookForPheromone problem");
        }

        if(leftSpot > 0 || centerSpot > 0 || rightSpot > 0) {
            return true;
        }
        return false;

    }

    private int getPheromone(int x2, int y2) {
        if(x2 < 0 || x2 > antSim.getScaledWidth()-1 || y2 < 0 || y2 > antSim.getScaledHeight()-1 ){
            return 0;
        } else {
            return grid.foodPheroemone[x2][y2];
        }
    }

    private void move() {
        this.x += direction.x * speed;
        this.y += direction.y * speed;
    
    }

    void draw(Graphics g, int scale) {
        g.fillRect((int)this.x * scale, (int)this.y * scale, scale, scale);
    }


    private float randomFloat(float min, float max) {
        return min + r.nextFloat() * (max - min);
    }
 
}

class vector{
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
        float length = (float)Math.sqrt( this.x*this.x + this.y*this.y );
        if (length != 0) {
          this.x = this.x/length;
          this.y = this.y/length;
        }
     }   

}