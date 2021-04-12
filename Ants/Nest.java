import java.awt.Color;
import java.awt.Graphics;



public class Nest {
    public  int x;
    public  int y;
    public int size;
    private int scale;
    public Nest(int x, int y, int size, int scale) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.scale = scale;
    }

    public void draw(Graphics g) {
        g.setColor(Color.blue);
        g.fillRect(this.x*scale, y*scale, size*scale, size*scale);
    }
}
