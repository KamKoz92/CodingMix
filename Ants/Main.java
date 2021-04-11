import javax.swing.Timer;

public class Main {

    private static Timer timer;
    private static AntSim simulation;


    public static void main(String[] args) {
        int width = 800;
        int height = 800;


        simulation = new AntSim(width, height, 2);
        new Window(simulation, width, height, "Ant Simulation");
        timer = new Timer(50, simulation);
        timer.start();
    }
}