import javax.swing.Timer;

public class Main {
    public static Timer timer;
    public static void main(String[] args) {
        int width = 800;
        int height = 600;
        int scale = 16;
        // Map map = new Map(width, height, 1, 45, scale);
        Dungeon dungeon = new Dungeon(width, height, scale);
        new Window(dungeon, width, height, "Cave");
        timer = new Timer(2000, dungeon);
        timer.start();
    }
}
