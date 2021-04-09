import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Starfield extends JPanel implements ActionListener{

    private static final long serialVersionUID = 1L;
    private int xSize;
    private int ySize;
    private int starCount;
    private Star[] stars;

    public Starfield(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
        starCount = 200;
        stars = new Star[starCount];
        for(int i = 0; i < starCount; i++) {
            stars[i] = new Star(xSize,ySize);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i = 0; i < starCount; i++) {
            stars[i].update();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, xSize, ySize);
        g.setColor(Color.white);
        for(int i = 0; i < starCount; i++) {
            stars[i].draw(g);
        }
        super.repaint();
    }
}
