import java.util.Random;
import java.awt.Graphics;

public class Star {
    private double x;
    private double y;
    private Random r;
    private int width;
    private int height;
    private double xSpeed;
    private double ySpeed;


    public Star(int width, int height) {
        r = new Random();
        this.width = width;
        this.height = height;

        x = r.nextInt(width) - (width/2);
        y = r.nextInt(height) - (height/2);

        xSpeed = x/400;
        ySpeed = y/400;

    }

    public void update() {
        xSpeed *= 1.1;
        ySpeed *= 1.1;
        x += xSpeed;
        y += ySpeed;

        if(x < -(width/2) || x > (width/2) || y < -(height/2) || y > (height/2)) {
            x = r.nextInt(width) - (width/2);
            y = r.nextInt(height) - (height/2);
            xSpeed = x/400;
            ySpeed = y/400;
        }
    }

    public void draw(Graphics g) {
        g.fillRect(((int)this.x) + (width/2), ((int)this.y) + (height/2), 2, 2);
        g.drawLine(((int)this.x) + (width/2) - (int)xSpeed * 4,
        ((int)this.y) + (height/2) - (int)ySpeed * 4,
        ((int)this.x) + (width/2),
        ((int)this.y) + (height/2));
    }

    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
