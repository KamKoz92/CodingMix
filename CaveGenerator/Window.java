import javax.swing.JFrame;

public class Window{
    
    public Window(Map map, int width, int height, String name) {
        JFrame frame = new JFrame(name);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(map);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}