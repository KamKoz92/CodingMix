import javax.swing.Timer;

public class Main {
    public static Timer timer;
    public static void main(String[] args) {
        int width = 800;
        int height = 600;
        Map map = new Map(width, height, 1, 45);
        new Window(map, width, height, "Cave");
        timer = new Timer(1000, map);
        timer.start();
    }
}
