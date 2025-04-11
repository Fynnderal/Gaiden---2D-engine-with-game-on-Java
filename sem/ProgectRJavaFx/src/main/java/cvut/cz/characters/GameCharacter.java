package cvut.cz.characters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import cvut.cz.GameSprite;
import cvut.cz.Updatable;
import cvut.cz.items.Item;

import java.net.URL;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public abstract class GameCharacter extends GameSprite implements Updatable {
    private final static ObjectMapper mapper = new ObjectMapper();
//    private final static SimpleFilterProvider filters = new SimpleFilterProvider().addFilter("itemFilter", SimpleBeanPropertyFilter.filterOutAllExcept("Name", "Broken", "Amount"));

    protected Item[] items;
    protected States currentState;
    protected double speed;
    protected int maxHealth;
    protected int currentHealth;
    protected int attackPower;

    public Item[] getItems() {return items;}

    public States getCurrentState() {return currentState;}

    public double getSpeed() { return speed; }

    public int getMaxHealth() { return maxHealth; }

    public int getCurrentHealth() { return currentHealth; }

    public int getAttackPower() { return attackPower;}

    public void setSpeed(double speed) { this.speed = speed;}

    public void setMaxHealth(int maxHealth) { this.maxHealth = maxHealth;}

    public void setCurrentHealth(int currentHealth) { this.currentHealth = currentHealth;}

    public void setAttackPower(int attackPower) { this.attackPower = attackPower;}

    public void setCurrentState(States currentState) {this.currentState = currentState;}

    public GameCharacter(URL pathToImage, int sourceCoordinateX, int sourceCoordinateY, int sourceWidth, int sourceHeight, int screenCoordinateX, int screenCoordinateY, int targetWidth, int targetHeight, int worldCoordinateX, int worldCoordinateY) {
        this(0, States.IDLE, 0, 0,0, pathToImage, sourceCoordinateX, sourceCoordinateY, sourceWidth, sourceHeight, screenCoordinateX, screenCoordinateY, targetWidth, targetHeight, worldCoordinateX, worldCoordinateY);
    }

    public GameCharacter(int attackPower, States currentState, int currentHealth, int maxHealth, double speed, URL pathToImage, int sourceCoordinateX, int sourceCoordinateY, int sourceWidth, int sourceHeight, int screenCoordinateX, int screenCoordinateY, int targetWidth, int targetHeight, int worldCoordinateX, int worldCoordinateY){
        super(pathToImage, sourceCoordinateX, sourceCoordinateY, sourceWidth, sourceHeight, screenCoordinateX, screenCoordinateY, targetWidth, targetHeight, worldCoordinateX, worldCoordinateY);
        this.speed = speed;
        this.maxHealth = maxHealth;
        this.currentHealth = currentHealth;
        this.currentState = currentState;
        this.attackPower = attackPower;
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

    protected abstract void Attack();
    protected abstract void Move(Directions direction);
    protected abstract void Die();
    protected abstract void takeDamage(int damage);
    public void setWorldCoordinateX(int worldCoordinateX) { this.worldCoordinateX = worldCoordinateX; }
    public void setWorldCoordinateY(int worldCoordinateY) { this.worldCoordinateY = worldCoordinateY; }
}
