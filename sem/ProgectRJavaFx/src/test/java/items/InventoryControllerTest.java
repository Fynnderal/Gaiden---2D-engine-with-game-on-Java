package items;

import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;
import cvut.cz.characters.Directions;
import cvut.cz.characters.PlayableCharacter;
import cvut.cz.items.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryControllerTest {
    private Inventory inventory;
    private InventoryController inventoryController;

    @BeforeEach
    void setUp() {
        InventoryInformation inventoryInformation = new InventoryInformation(0, 0, 12, 3, 0, 0);
        InventoryCellGeneralInformation cellGeneralInformation = new InventoryCellGeneralInformation(10, 20, 0, 0,10, 10,0,0);
        inventory = new Inventory(inventoryInformation, cellGeneralInformation,
                new GameSpriteSourceInformation(null, 0, 0, 0, 0),
                new GameSpriteRenderInformation(0, 0, 0, 0, 0 ,0));
        inventory.deleteitems();

        Pointer pointer = new Pointer(new GameSpriteSourceInformation(null, 0, 0,0,0));
        inventory.setPointer(pointer);
        inventoryController = new InventoryController(inventory);
    }

    @Test
    void movePointerRightUpdatesPointerCoordinates() {
        int currentRow = 0;
        for (int i = 0; i < inventory.getInventoryInformation().numberOfCells() - 1; i++) {
            if ((i + 1) % 3 == 0)
                currentRow += 20;
            inventoryController.movePointer(Directions.RIGHT);
            assertEquals((10 + i * 10) % 30, inventory.getPointer().getGameSpriteRenderInformation().getScreenCoordinateX());
            assertEquals(currentRow, inventory.getPointer().getGameSpriteRenderInformation().getScreenCoordinateY());
        }

        inventoryController.movePointer(Directions.RIGHT);
        assertEquals(0, inventory.getPointer().getGameSpriteRenderInformation().getScreenCoordinateX());
        assertEquals(0, inventory.getPointer().getGameSpriteRenderInformation().getScreenCoordinateY());
    }

    @Test
    void movePointerLeftUpdatesPointerCoordinates() {
        inventoryController.movePointer(Directions.LEFT);
        assertEquals(20, inventory.getPointer().getGameSpriteRenderInformation().getScreenCoordinateX());
        assertEquals(60, inventory.getPointer().getGameSpriteRenderInformation().getScreenCoordinateY());

        int currentRow = 60;
        for (int i = inventory.getInventoryInformation().numberOfCells() - 1; i > 0; i--) {
            if (i % 3 == 0)
                currentRow -= 20;
            inventoryController.movePointer(Directions.LEFT);
            assertEquals(((i - 1) * 10) % 30, inventory.getPointer().getGameSpriteRenderInformation().getScreenCoordinateX());
            assertEquals(currentRow, inventory.getPointer().getGameSpriteRenderInformation().getScreenCoordinateY());
        }
    }

    @Test
    void movePointerDownUpdatesPointerCoordinates() {
        for (int i = 0; i < 3; i++) {
            inventoryController.movePointer(Directions.DOWN);
            assertEquals(0, inventory.getPointer().getGameSpriteRenderInformation().getScreenCoordinateX());
            assertEquals((i + 1) * 20, inventory.getPointer().getGameSpriteRenderInformation().getScreenCoordinateY());
        }

        inventoryController.movePointer(Directions.DOWN);
        assertEquals(0, inventory.getPointer().getGameSpriteRenderInformation().getScreenCoordinateX());
        assertEquals(0, inventory.getPointer().getGameSpriteRenderInformation().getScreenCoordinateY());
    }

    @Test
    void movePointerUpUpdatesPointerCoordinates() {
        inventoryController.movePointer(Directions.UP);
        assertEquals(0, inventory.getPointer().getGameSpriteRenderInformation().getScreenCoordinateX());
        assertEquals(60, inventory.getPointer().getGameSpriteRenderInformation().getScreenCoordinateY());

        for (int i = 2; i >= 0; i--) {
            inventoryController.movePointer(Directions.UP);
            assertEquals(0, inventory.getPointer().getGameSpriteRenderInformation().getScreenCoordinateX());
            assertEquals(i * 20, inventory.getPointer().getGameSpriteRenderInformation().getScreenCoordinateY());
        }
    }

    @Test
    void combineCellsCreatesNewItemWhenCombinationIsValid() {
        Map<String, String> canBeCombined1 = new HashMap<>();

        canBeCombined1.put("Item2", "CombinedItem");

        Item item1 = new Item(new GameSpriteSourceInformation(null, 0,0,0,0), new ItemInformation("Item1", canBeCombined1, false, false, false, 0), 1);
        Item item2 = new Item(new GameSpriteSourceInformation(null, 0,0,0,0), new ItemInformation("Item2", null, false, false, false, 0), 1);
        inventory.addItemToInventory(item1);
        inventory.addItemToInventory(item2);


        Map<String, Item> possibleItems = new HashMap<>();
        possibleItems.put("CombinedItem", new Item(new GameSpriteSourceInformation(null, 0,0,0,0), new ItemInformation("CombinedItem", null, false, false, false, 0), 1));

        inventory.setPossibleItems(possibleItems);

        inventoryController.combineCells(inventory.getCells().getFirst(), inventory.getCells().get(1));

        assertEquals(1, inventory.getItems().size());
        assertEquals("CombinedItem", inventory.getItems().get(0).getItemInformation().name());
    }

    @Test
    void combineCellsReduceNumberOfCombinedItems() {
        Map<String, String> canBeCombined1 = new HashMap<>();

        canBeCombined1.put("Item2", "CombinedItem");

        Item item1 = new Item(new GameSpriteSourceInformation(null, 0,0,0,0), new ItemInformation("Item1", canBeCombined1, false, false, false, 0), 4);
        Item item2 = new Item(new GameSpriteSourceInformation(null, 0,0,0,0), new ItemInformation("Item2", null,false, false, false, 0), 2);
        inventory.addItemToInventory(item1);
        inventory.addItemToInventory(item2);


        Map<String, Item> possibleItems = new HashMap<>();
        possibleItems.put("CombinedItem", new Item(new GameSpriteSourceInformation(null, 0,0,0,0), new ItemInformation("CombinedItem", null, false, false, false, 0), 1));

        inventory.setPossibleItems(possibleItems);

        inventoryController.combineCells(inventory.getCells().getFirst(), inventory.getCells().get(1));

        assertEquals(3, inventory.getCells().getFirst().getItemAmount());
        assertEquals(1, inventory.getCells().get(1).getItemAmount());
    }

    @Test
    void discardCellReducesItemAmountIfItemCanBeDiscarded() {
        Item item1 = new Item(new GameSpriteSourceInformation(null, 0, 0, 0, 0), new ItemInformation("Item1", null, false, false, true, 0), 4);
        inventory.addItemToInventory(item1);

        inventoryController.discardCell(inventory.getCells().getFirst());

        assertEquals(3, inventory.getCells().getFirst().getItemAmount());
    }
    @Test
    void discardCellDoesNothingIfItemCantBeDiscarded() {
        Item item1 = new Item(new GameSpriteSourceInformation(null, 0, 0, 0, 0), new ItemInformation("Item1", null, false, false, false, 0), 4);
        inventory.addItemToInventory(item1);

        inventoryController.discardCell(inventory.getCells().getFirst());

        assertEquals(4, inventory.getCells().getFirst().getItemAmount());
    }

    @Test
    void equipCellSetsItemAsEquippedIfItemCanBeEquipped() {
        Item item1 = new Item(new GameSpriteSourceInformation(null, 0, 0, 0, 0), new ItemInformation("Item1", null, true, false, false, 0), 4);
        inventory.addItemToInventory(item1);

        inventoryController.equipCell(inventory.getCells().getFirst());

        assertEquals(item1.getItemInformation().name(), inventory.getEquippedCell().getItem().getItemInformation().name());
    }

    @Test
    void equipCellDoesNothingIfItemCantBeEquipped() {
        Item item1 = new Item(new GameSpriteSourceInformation(null, 0, 0, 0, 0), new ItemInformation("Item1", null, false, false, false, 0), 4);
        inventory.addItemToInventory(item1);

        inventoryController.equipCell(inventory.getCells().getFirst());

        assertEquals(null, inventory.getEquippedCell());
    }

    @Test
    void useCellDoesNothingIfItemCantBeUsed() {
        Item item1 = new Item(new GameSpriteSourceInformation(null, 0, 0, 0, 0), new ItemInformation("Item1", null, false, false, false, 0), 4);
        inventory.addItemToInventory(item1);

        inventoryController.useCell(new PlayableCharacter(null, null, null) {
            @Override
            public void shoot(Item weapon) {

            }

            @Override
            public void useItem(Item item) {

            }

            @Override
            public void aim(Item weapon) {

            }

            @Override
            protected void attack() {

            }
        });

        assertEquals(4, inventory.getCells().getFirst().getItemAmount());
    }

    @Test
    void useCellReduceNumberOfItem() {
        Item item1 = new Item(new GameSpriteSourceInformation(null, 0, 0, 0, 0), new ItemInformation("Item1", null, false, true, false, 0), 4);
        inventory.addItemToInventory(item1);

        inventoryController.useCell(new PlayableCharacter(null, null, null) {
            @Override
            public void shoot(Item weapon) {

            }

            @Override
            public void useItem(Item item) {
                item.setAmount(item.getAmount() - 1);
            }

            @Override
            public void aim(Item weapon) {

            }

            @Override
            protected void attack() {

            }
        });

        assertEquals(3, inventory.getCells().getFirst().getItemAmount());
    }
}
