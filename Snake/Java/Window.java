import javax.swing.JFrame;

public class Window {
        
    

    public Window(Snake snake, int xSize, int ySize, String name) {

        JFrame frame = new JFrame(name);
        frame.setSize(xSize,ySize);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(snake);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
