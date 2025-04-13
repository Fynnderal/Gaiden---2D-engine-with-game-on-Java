package cvut.cz.characters;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    private URL pathToItems;



    public void setItems(Item[] items) { this.items = items; }

    public Item[] getItems() {return items;}

    public PlayableCharacter(URL pathToInventory, URL pathToImage, int sourceCoordinateX, int sourceCoordinateY, int sourceWidth, int sourceHeight, int screenCoordinateX, int screenCoordinateY, int targetWidth, int targetHeight, int worldCoordinateX, int worldCoordinateY ) {
        this(0, States.IDLE, 0, 0, 0,pathToInventory, pathToImage, sourceCoordinateX, sourceCoordinateY, sourceWidth, sourceHeight, screenCoordinateX, screenCoordinateY, targetWidth, targetHeight, worldCoordinateX, worldCoordinateY);
    }

    public PlayableCharacter(int attackPower, States currentState, int currentHealth, int maxHealth, double speed, URL pathToItems, URL pathToImage, int sourceCoordinateX, int sourceCoordinateY, int sourceWidth, int sourceHeight, int screenCoordinateX, int screenCoordinateY, int targetWidth, int targetHeight, int worldCoordinateX, int worldCoordinateY) {
        super(attackPower, currentState, currentHealth, maxHealth, speed, pathToImage, sourceCoordinateX, sourceCoordinateY, sourceWidth, sourceHeight, screenCoordinateX, screenCoordinateY, targetWidth, targetHeight, worldCoordinateX, worldCoordinateY);
        this.pathToItems = pathToItems;
        if (pathToItems != null) {
            readAvailableItems(this.pathToItems);
        }
    }

    public void Move(Directions direction) {
        currentState = States.WALKING;
        switch (direction) {
            case UP:
                this.worldCoordinateY -= speed;
                break;
            case DOWN:
                this.worldCoordinateY += speed;
                break;
            case RIGHT:
                this.worldCoordinateX += speed;
                break;
            case LEFT:
                this.worldCoordinateX -= speed;
                break;
        }
        currentState = States.IDLE;
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
}
