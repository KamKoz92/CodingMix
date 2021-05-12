import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class SpriteSheet {
    private BufferedImage image;
    

    public SpriteSheet(String filePath) {
        image = null;
        try {
            image = ImageIO.read(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (image == null) {
            System.out.println("nullpointer on sprite sheet");
        } else {
            System.out.println("Image loaded ");
        }

        // int tilesWidth = image.getWidth() / 16;
        // int tilesHeight = image.getHeight() / 16;
    }

    public BufferedImage getSpriteSheet() {
        return image;
    }
}