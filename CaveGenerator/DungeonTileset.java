import java.awt.image.BufferedImage;
import java.util.HashMap;

public class DungeonTileset {
    private HashMap<String, BufferedImage> tileTypes;
    private SpriteSheet spriteSheet;

    public DungeonTileset(String filePath) {
        spriteSheet = new SpriteSheet(filePath);

        tileTypes = new HashMap<String, BufferedImage>();
        setTiles();
    }

    private void setTiles() {
        tileTypes.put("basicFloor", spriteSheet.getSpriteSheet().getSubimage(9 * 16, 7 * 16, 16, 16));
        tileTypes.put("basicWall", spriteSheet.getSpriteSheet().getSubimage(8 * 16, 7 * 16, 16, 16));
        
        tileTypes.put("floor2", spriteSheet.getSpriteSheet().getSubimage(6 * 16, 0, 16, 16));
        tileTypes.put("floor3", spriteSheet.getSpriteSheet().getSubimage(7 * 16, 0, 16, 16));
        tileTypes.put("floor4", spriteSheet.getSpriteSheet().getSubimage(8 * 16, 0, 16, 16));
        tileTypes.put("floor5", spriteSheet.getSpriteSheet().getSubimage(9 * 16, 0, 16, 16));

        tileTypes.put("leftWall", spriteSheet.getSpriteSheet().getSubimage(0, 16, 16, 16));
        tileTypes.put("rightWall", spriteSheet.getSpriteSheet().getSubimage(5 * 16, 16, 16, 16));
        tileTypes.put("topWall", spriteSheet.getSpriteSheet().getSubimage(16, 0, 16, 16));
        tileTypes.put("bottomWall", spriteSheet.getSpriteSheet().getSubimage(16, 4 * 16, 16, 16));

    }

    public BufferedImage getTile(String keyString) {
        return tileTypes.get(keyString);
    }
}
