package cvut.cz.characters;

import com.fasterxml.jackson.databind.ObjectMapper;
import cvut.cz.GameSpriteRenderInformation;
import cvut.cz.GameSpriteSourceInformation;
import cvut.cz.items.Item;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;
import java.net.URL;


public abstract class PlayableCharacter extends GameCharacter{
    private static final Logger logger = Logger.getLogger(PlayableCharacter.class.getName());
    private final static ObjectMapper mapper = new ObjectMapper();
//    private final static SimpleFilterProvider filters = new SimpleFilterProvider().addFilter("itemFilter", SimpleBeanPropertyFilter.filterOutAllExcept("Name", "Broken", "Amount"));

    protected Item[] items;
    private final URL pathToItems;

    public PlayableCharacter(URL pathToItems, CharacterInformation characterInformation, GameSpriteSourceInformation gameSpriteSourceInformation, GameSpriteRenderInformation gameSpriteRenderInformation) {
        super(characterInformation, gameSpriteSourceInformation, gameSpriteRenderInformation);
        this.pathToItems = pathToItems;
        if (pathToItems != null) {
            readAvailableItems(this.pathToItems);
        }
    }

    public void Move(Directions direction) {
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

    public void readAvailableItems(URL fileName) {
        try (FileReader fileReader = new FileReader(fileName.getPath())) {
            items = mapper.readValue(fileReader, Item[].class);
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
    }

    public Item[] getItems() {return items;}

    public void setItems(Item[] items) { this.items = items; }
}
