import javax.swing.JFrame;

public class Window{
    
    public Window(Dungeon dungeon, int width, int height, String name) {
        JFrame frame = new JFrame(name);
        frame.setSize(width+15, height+25); //screen offset 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(dungeon);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
