import javax.swing.Timer;

public class Main {

    private static Timer timer;
    private static AntSim simulation;


    public static void main(String[] args) {
        int width = 1200;
        int height = 800;


        simulation = new AntSim(width, height, 200);
        new Window(simulation, width, height, "Ant Simulation");
        timer = new Timer(5, simulation);
        timer.start();
    }
}