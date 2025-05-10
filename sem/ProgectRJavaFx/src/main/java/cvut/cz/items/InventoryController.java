package cvut.cz.items;

import com.fasterxml.jackson.databind.ObjectMapper;
import cvut.cz.characters.Directions;
import cvut.cz.characters.PlayableCharacter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Controls inventory. It manages moving of the pointer, combining, discarding, equipping, and using items.
 */
public class InventoryController {
    private final Inventory inventory;

    //Cell at which the pointer is currently pointing
    private int currentCell;

    private final static ObjectMapper mapper = new ObjectMapper();
    //path where information about player's items is saved

    Logger logger = Logger.getLogger(InventoryController.class.getName());

    /**
     * Constructs an InventoryController for the specified inventory.
     *
     * @param inventory The inventory to be controlled.
     */
    public InventoryController(Inventory inventory) {
        this.inventory = inventory;
        this.currentCell = 0;
    }

    /**
     * Moves the pointer of the inventory in the specified direction.
     *
     * @param direction The direction of the movement.
     */
    public void movePointer(Directions direction) {
        if (inventory.getPointer() == null)
            return;

        if (inventory.getCells().isEmpty())
            logger.severe("There is no inventory cells");

        //changing currently selected cell
        switch(direction){
            case RIGHT:
                currentCell = ++currentCell % inventory.getCells().size();
                break;

            case LEFT:
                currentCell = --currentCell % inventory.getCells().size();
                //number of current cell can't be negative
                if (currentCell < 0)
                    currentCell = inventory.getCells().size() +currentCell;
                break;

            case DOWN:
                currentCell = (currentCell + inventory.getInventoryInformation().numberOfCellsInRow()) % inventory.getCells().size();
                break;
            case UP:
                currentCell = (currentCell - inventory.getInventoryInformation().numberOfCellsInRow()) % inventory.getCells().size();
                //number of current cell can't be negative
                if (currentCell < 0)
                    currentCell = inventory.getCells().size() +currentCell;
                break;

        }
        //sets coordinates of the pointer to the coordinates of the selected cell.
        inventory.getPointer().getGameSpriteRenderInformation().setScreenCoordinateX(inventory.getCells().get(currentCell).getCoordinateX());
        inventory.getPointer().getGameSpriteRenderInformation().setScreenCoordinateY(inventory.getCells().get(currentCell).getCoordinateY());
    }
    /**
     * Combines two inventory cells into a new item if they can be combined.
     *
     * @param firstCell  The first inventory cell to combine.
     * @param secondCell The second inventory cell to combine.
     */
    public void combineCells(InventoryCell firstCell, InventoryCell secondCell){
        if (inventory.getPossibleItems() == null)
            return;

        if (firstCell == null || secondCell == null)
            return;

        if (firstCell.getItem() == null || secondCell.getItem() == null)
            return;

        Map<String, String> canBeCombinedWithInto1 = firstCell.getItem().getItemInformation().canBeCombinedWithInto();

        if (canBeCombinedWithInto1 == null)
            return;


         //checks if the first item can be combined with the second one
        if (canBeCombinedWithInto1.containsKey(secondCell.getItem().getItemInformation().name())){
            String resultItemName = canBeCombinedWithInto1.get(secondCell.getItem().getItemInformation().name());

            //checks if there is enough space in the inventory.
            if (inventory.addItemToInventory(inventory.getPossibleItems().get(resultItemName).clone())) {
                firstCell.setItemAmount(firstCell.getItem().getAmount() - 1);
                secondCell.setItemAmount(secondCell.getItem().getAmount() - 1);
            }
        }
    }

    /**
     * Discards one item from the specified inventory cell.
     *
     * @param inventoryCell The inventory cell to discard the item from.
     */
    public void discardCell(InventoryCell inventoryCell){
        if (inventoryCell == null)
            return;

        if (inventoryCell.getItem() == null)
            return;

        if (!inventoryCell.getItem().getItemInformation().canBeDiscarded())
            return;

        inventoryCell.setItemAmount(inventoryCell.getItem().getAmount() - 1);
    }

    /**
     * Equips or unequips the item in the specified inventory cell.
     *
     * @param inventoryCell The inventory cell containing the item to equip or unequip.
     */
    public void equipCell(InventoryCell inventoryCell){
        if (inventoryCell == null)
            return;

        if (inventoryCell.getItem() == null)
            return;

        if (!inventoryCell.getItem().getItemInformation().canBeEquipped())
            return;

        if (inventory.getEquippedCell() == inventoryCell) {
            inventory.setEquippedCell(null);
            inventoryCell.setIsItemEquipped(false);
        }else{
            if (inventory.getEquippedCell() != null)
                inventory.getEquippedCell().setIsItemEquipped(false);
            inventoryCell.setIsItemEquipped(true);
            inventory.setEquippedCell(inventoryCell);
        }
    }

    /**
     * Uses the item in the currently selected inventory cell on the specified character.
     *
     * @param character The character to use the item on.
     */
    public void useCell(PlayableCharacter character) {
        InventoryCell inventoryCell = inventory.getSelectedInventoryCell();

        if (inventoryCell == null)
            return;

        if (inventoryCell.getItem() == null)
            return;

        if (!inventoryCell.getItem().getItemInformation().canBeUsed())
            return;

        character.useItem(inventoryCell.getItem());

        inventoryCell.setItemAmount(inventoryCell.getItem().getAmount());
    }


    /**
     * Reads available items from a JSON file and sets them in the inventory.
     */
    public void readAvailableItems(String pathToItems) {
        try (FileReader fileReader = new FileReader(pathToItems)) {
            Object[] temp = mapper.readValue(fileReader, Item[].class);
            List<Item> items = new ArrayList<>();
            for (Object object: temp){
                if (object instanceof Item){
                    items.add((Item) object);
                }
            }
            inventory.setItems(items);

        } catch (IOException | ClassCastException e) {
            System.err.println("[ERROR] Problem with reading json file. Problem: " + e.getMessage());
        }

    }

    /**
     * Writes the available items in the inventory to a JSON file.
     */
    public void writeAvailableItems(String pathToItems) {
        try (FileWriter fileWriter = new FileWriter(pathToItems)) {
            mapper.writeValue(fileWriter, inventory.getItems().toArray());
        } catch (IOException e) {
            System.err.println("[ERROR] Problem with writing into json file. Problem: " + e.getMessage());
        }
    }

    /**
     * Gets the index of the currently selected cell.
     *
     * @return The index of the currently selected cell.
     */
    public int getCurrentCell() { return currentCell; }
}
