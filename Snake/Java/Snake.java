import javax.swing.JPanel;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Color;

public class Snake extends JPanel implements ActionListener, KeyListener {

    private static final long serialVersionUID = 1L;
    private int width;
    private int height;
    private int xspeed;
    private int yspeed;
    private int scale;
    private List<Box> body;
    private Random r;
    private Food food;
    private int tempx;
    private int tempy;
    public Snake(int width, int height) {
    
        this.width = width;
        this.height = height;
        this.scale = 10;
        this.xspeed = 1;
        this.yspeed = 0;
        r = new Random();
        food = new Food();
        food.newLocation(r.nextInt(width/scale),r.nextInt(height/scale),scale);
        body = new ArrayList<Box>();
        body.add(new Box(10, 10, scale));
        this.tempx = 0;
        this.tempy = 0;
    }

    private void update() {
        tempx = body.get(body.size()-1).getX();
        tempy = body.get(body.size()-1).getY();

        for (int i = body.size() - 1; i > 0; i--) {
            body.get(i).setX(body.get(i - 1).getX());
            body.get(i).setY(body.get(i - 1).getY());
        }
        body.get(0).setX(body.get(0).getX() + xspeed * scale);
        body.get(0).setY(body.get(0).getY() + yspeed * scale);

        if(body.get(0).getX() == food.x && body.get(0).getY() == food.y) {
            body.add(new Box(tempx,tempy,scale));
            food.newLocation(r.nextInt(width/scale),r.nextInt(height/scale),scale);
        }
    }

    private void changeDirection(int i, int j) {
        this.xspeed = i;
        this.yspeed = j;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.update();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.white);
        for (Box b : body) {
            b.draw(g);
        }
        g.fillRect(food.x, food.y, scale, scale);
        super.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int id = e.getKeyCode();
        if (id == KeyEvent.VK_UP) {
            changeDirection(0, -1);
        } else if (id == KeyEvent.VK_DOWN) {
            changeDirection(0, 1);
        } else if (id == KeyEvent.VK_LEFT) {
            changeDirection(-1, 0);
        } else if (id == KeyEvent.VK_RIGHT) {
            changeDirection(1, 0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

class Food {
    public int x;
    public int y;

    public void newLocation(int x, int y, int scale) {
        this.x = x * scale;
        this.y = y * scale;
        System.out.println(x + " " + y);
    }
}