import java.awt.image.BufferedImage;
import java.util.HashMap;

public class DungeonTileset {
    private HashMap<String,BufferedImage> tileTypes;
    private SpriteSheet spriteSheet;

    public DungeonTileset(String filePath) {
        spriteSheet = new SpriteSheet(filePath);
    
        tileTypes = new HashMap<String,BufferedImage>();
        setTiles();
    }

    private void setTiles() {
        tileTypes.put("floor1", spriteSheet.getSpriteSheet().getSubimage(9 * 16, 7 * 16, 16, 16));
        tileTypes.put("floor2", spriteSheet.getSpriteSheet().getSubimage(6 * 16, 0, 16, 16));
        tileTypes.put("floor3", spriteSheet.getSpriteSheet().getSubimage(7 * 16, 0, 16, 16));
        tileTypes.put("floor4", spriteSheet.getSpriteSheet().getSubimage(8 * 16, 0, 16, 16));
        tileTypes.put("floor5", spriteSheet.getSpriteSheet().getSubimage(9 * 16, 0, 16, 16));
    }

    public BufferedImage getTile(String keyString) {
        return tileTypes.get(keyString);
    }
}
