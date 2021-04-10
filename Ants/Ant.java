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
    public Ant(int x, int y, Grid grid) {
        this.x = x;
        this.y = y;
        this.grid = grid;
        r = new Random();
        this.direction = new vector(randomFloat(-1,1),randomFloat(-1,1));
        this.direction.normalize();
        speed = 1.5f;
        wanderStrength = 0.1f;
        grid.leaveTrail((int)x,(int)y);
    }


    void update() {
        updateDirection();
        checkForObstacles();
        move();
        grid.leaveTrail((int)x,(int)y);
    }

    
    private void checkForObstacles() {
        float tempX = this.x + direction.x * speed;
        float tempY = this.y + direction.y * speed;
        while(tempX < 0 || tempX > 400 || tempY < 0 || tempY > 400) {
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
        capDirection();
    }

    

    private void move() {
        this.x += direction.x * speed;
        this.y += direction.y * speed;
    
    }

    void draw(Graphics g, int scale) {
        g.fillRect((int)this.x * scale, (int)this.y * scale, scale, scale);
    }

    private void capDirection() {
        if(direction.x < -1) {
            direction.x = -1;
        }
        if(direction.x > 1) {
            direction.x  = 1;
        }
        if(direction.y < -1) {
            direction.y = -1;
        }
        if(direction.y > 1) {
            direction.y = 1;
        }
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