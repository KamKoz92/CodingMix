import java.awt.Graphics;
import java.awt.image.*;
import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;

public class SpriteSheet {
    private BufferedImage image;
    private BufferedImage[][] tiles;

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

        int tilesWidth = image.getWidth() / 16;
        int tilesHeight = image.getHeight() / 16;
        tiles = new BufferedImage[tilesWidth][tilesHeight];

        for (int i = 0; i < tilesWidth; i++) {
            for (int j = 0; j < tilesHeight; j++) {
                tiles[i][j] = image.getSubimage(i * 16, j * 16, 16, 16);
            }
        }

    }

    public void draw(Graphics g) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                g.drawImage(tiles[i][j], 48 + (i * 16), 48 + (j * 16), null);
            }
        }
    }
}