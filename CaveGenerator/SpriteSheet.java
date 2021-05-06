import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;

public class SpriteSheet {
    BufferedImage image;
    public SpriteSheet(String filePath) {
        image = null;
        try {
            image = ImageIO.read(new File(filePath));
        } catch(IOException e) {
            e.printStackTrace();
        }

        if(image == null) {
            System.out.println("nullpointer on sprite sheet");
        }
    }
}