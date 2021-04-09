import javax.swing.Timer;
//Snake game
//Not implemented:
//Game over when snake colide with itself
//Window boundaries
//Point system
//Food can spawn on snake
//

public class Main {

    private static Timer timer;
    private static Snake snake;
    public static void main(String[] args) {
        int xSize = 800;
        int ySize = 600;        

        snake = new Snake(xSize,ySize);
        new Window(snake, xSize, ySize, "Snake");
        snake.requestFocusInWindow();
        snake.addKeyListener(snake);
        timer = new Timer(100, snake);
        timer.start();
    }
}