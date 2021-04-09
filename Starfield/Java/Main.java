import javax.swing.Timer;


public class Main {
    private static Timer timer;
    private static Starfield StarField;
    public static void main(String[] args) {
        int xSize = 1300;
        int ySize = 1000;
        StarField = new Starfield(xSize, ySize);
        new Window(StarField, xSize, ySize, "Starfield");
        timer = new Timer(10, StarField); 
        timer.start();
    }
}
