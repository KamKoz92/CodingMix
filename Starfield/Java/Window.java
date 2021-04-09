import javax.swing.JFrame;

public class Window {
    
    public Window(Starfield StarField, int xSize, int ySize, String name) {
        JFrame frame = new JFrame(name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(xSize, ySize);
        frame.setContentPane(StarField);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
