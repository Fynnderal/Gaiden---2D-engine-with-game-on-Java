package cvut.cz.characters;

import com.fasterxml.jackson.databind.ObjectMapper;
import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;
import cvut.cz.items.Item;
import cvut.cz.Map.Map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import java.net.URL;


public abstract class PlayableCharacter extends GameCharacter{
    private static final Logger logger = Logger.getLogger(PlayableCharacter.class.getName());
    private final static ObjectMapper mapper = new ObjectMapper();

    protected List<Item> items;
    private final URL pathToItems;
    protected final Map map;

    protected final long timeBetweenPistolShots = 400;
    protected final long timeBetweenShotgunShots = 700;
    protected long now;
    protected long lastPistolShot;
    protected long lastShotgunShot;

    protected HashMap<String, GunSight> weaponsAndGunSights;


    public PlayableCharacter(URL pathToItems, CharacterInformation characterInformation, GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation, Map map) {
        super(characterInformation, gameSpriteSourceInformation, gameSpriteRenderInformation);
        this.pathToItems = pathToItems;
        items = new ArrayList<>();
        this.map = map;
/*        if (pathToItems != null) {
            readAvailableItems(this.pathToItems);
        }*/
    }

    @Override
    public void update() {
        now = System.currentTimeMillis();
    }

    public void move(Directions direction) {
        characterInformation.setCurrentState(States.WALKING);
        switch (direction) {
            case UP:
                this.gameSpriteRenderInformation.setWorldCoordinateY(gameSpriteRenderInformation.getWorldCoordinateY() - characterInformation.getSpeed());
                break;
            case DOWN:
                this.gameSpriteRenderInformation.setWorldCoordinateY(gameSpriteRenderInformation.getWorldCoordinateY() + characterInformation.getSpeed());
                break;
            case RIGHT:
                this.gameSpriteRenderInformation.setWorldCoordinateX(gameSpriteRenderInformation.getWorldCoordinateX() + characterInformation.getSpeed());
                break;
            case LEFT:
                this.gameSpriteRenderInformation.setWorldCoordinateX(gameSpriteRenderInformation.getWorldCoordinateX() - characterInformation.getSpeed());
                break;
        }
        characterInformation.setCurrentState(States.IDLE);
    }

/*    public void readAvailableItems(URL fileName) {
        try (FileReader fileReader = new FileReader(fileName.getPath())) {
            items = (List<Item>) mapper.readValue(fileReader, List<Item>.class);
        }
        catch (IOException e) {
            System.err.println("[ERROR] Problem with reading json file. Problem: " + e.getMessage());
        }

    }

    public void writeAvailableItems(URL fileName) {
        try (FileWriter fileWriter = new FileWriter(fileName.getPath())) {
            mapper.writeValue(fileWriter, items);
        }
        catch (IOException e) {
            System.err.println("[ERROR] Problem with writing into json file. Problem: " + e.getMessage());
        }
    }*/

    @Override
    public void takeDamage(int damage) {
        characterInformation.setCurrentHealth(characterInformation.getCurrentHealth() - damage);
    }


    public List<Item> getItems() {return items;}
    public HashMap<String, GunSight> getWeaponsAndGunSights() { return weaponsAndGunSights; }

    public void setWeaponsAndGunSights(HashMap<String, GunSight> weaponsAndGunSights) { this.weaponsAndGunSights = weaponsAndGunSights;}
    public void setItems(List<Item> items) { this.items = items; }


    public abstract void shoot(Item weapon, List<GameCharacter> charactersOnMap);
    public abstract void useItem(Item item);
}
