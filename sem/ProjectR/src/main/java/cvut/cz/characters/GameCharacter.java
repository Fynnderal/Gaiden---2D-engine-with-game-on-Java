package cvut.cz.characters;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import cvut.cz.GameSprite;
import cvut.cz.items.Item;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public abstract class GameCharacter extends GameSprite {
    private final static ObjectMapper mapper = new ObjectMapper();
    private final static SimpleFilterProvider filters = new SimpleFilterProvider().addFilter("itemFilter", SimpleBeanPropertyFilter.filterOutAllExcept("Name", "Broken", "Amount"));
    protected Item[] items;

    public Item[] getItems() {
        return items;
    }

    public void readAvailableItems(String fileName) {
        try (FileReader fileReader = new FileReader(fileName)) {
            items = mapper.readValue(fileReader, Item[].class);
        }
        catch (IOException e) {
            System.err.println("[ERROR] Problem with reading json file. Problem: " + e.getMessage());
        }

    }
    public void writeAvailableItems(String fileName) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            mapper.setFilterProvider(filters);
            mapper.writeValue(fileWriter, items);
        }
        catch (IOException e) {
            System.err.println("[ERROR] Problem with writing into json file. Problem: " + e.getMessage());
        }
    }


}
