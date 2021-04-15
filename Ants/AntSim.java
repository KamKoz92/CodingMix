import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JPanel;

public class AntSim extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    private int numOfAnts;
    private List<Ant> ants;
    private Random r;
    private Grid grid;
    private int scaledWidth;
    private int scaledHeight;
    private int scale;
    private Nest nest;

    public AntSim(int width, int height, int numOfAnts) {
        scale = 2;
        scaledWidth = width / scale;
        scaledHeight = height / scale;
        this.numOfAnts = numOfAnts;
        grid = new Grid(scaledWidth, scaledHeight, scale);
        ants = new ArrayList<Ant>();
        r = new Random();
        nest = new Nest(scaledWidth / 2 - 10, scaledHeight / 2 - 10, 10, scale);
        grid.addNest(nest);
        for (int i = 0; i < this.numOfAnts; i++) {
            ants.add(new Ant(newRandomInt(nest.x, nest.x + nest.size), newRandomInt(nest.y, nest.y + nest.size), grid,
                    this));
        }

        grid.addFood(50, 50, 25);
        grid.addFood(500, 50, 25);
        grid.addFood(50, 250, 25);
        grid.addFood(500, 250, 25);
    }

    private int newRandomInt(int min, int max) {
        return r.nextInt(max - min) + min;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Ant a : ants) {
            a.update();
        }
        grid.evaporation();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, scaledWidth * scale, scaledHeight * scale);
        grid.drawGridElements(g);
        nest.draw(g);
        for (Ant a : ants) {
            a.draw(g, scale);
        }
        super.repaint();
    }

    // private void drawC(Point center, float radius, Graphics g) {
    //     int top = (int)Math.floor(center.y - radius), 
    //         bottom = (int)Math.ceil(center.y + radius), 
    //         left = (int)Math.floor(center.x - radius),
    //         right = (int)Math.ceil(center.x + radius);

    //     for (int y = top; y <= bottom; y++) {
    //         for (int x = left; x <= right; x++) {
    //             if (inside_circle(center, new Point(x, y), radius)) {
    //                 g.fillRect(x*scale, y*scale, scale, scale);
    //             }
    //         }
    //     }
    // }

    // private boolean inside_circle(Point center, Point tile, float radius) {
    //     float dx = center.x - tile.x, dy = center.y - tile.y;
    //     float distance_squared = dx * dx + dy * dy;
    //     return distance_squared <= radius * radius;
    // }

    public int getScaledWidth() {
        return scaledWidth;
    }

    public int getScaledHeight() {
        return scaledHeight;
    }

    public int getScale() {
        return scale;
    }

}
