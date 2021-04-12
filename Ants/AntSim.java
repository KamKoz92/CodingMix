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

    public AntSim(int width, int height, int numOfAnts) {
        scale = 2;
        scaledWidth = width / scale;
        scaledHeight = height / scale;
        this.numOfAnts = numOfAnts;
        grid = new Grid(scaledWidth, scaledHeight);
        ants = new ArrayList<Ant>();
        r = new Random();
        for (int i = 0; i < this.numOfAnts; i++) {
            
            ants.add(new Ant(newRandomInt(0, scaledWidth), newRandomInt(0, scaledHeight), grid, this, r.nextBoolean()));
        }
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
        grid.drawTrails(g);
        for (Ant a : ants) {
            a.draw(g, scale);
        }
        super.repaint();
    }

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
