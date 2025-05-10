package items;

import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;
import cvut.cz.items.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    private Inventory inventory;

    @BeforeEach
    void setUp() {
        InventoryInformation inventoryInformation = new InventoryInformation(0, 0, 12, 3, 0, 0);
        InventoryCellGeneralInformation cellGeneralInformation = new InventoryCellGeneralInformation(0, 0, 0, 0,10, 10,0,0);
        inventory = new Inventory(inventoryInformation, cellGeneralInformation,
                new GameSpriteSourceInformation(null, 0, 0, 0, 0),
                new GameSpriteRenderInformation(0, 0, 0, 0, 0 ,0));
        inventory.deleteitems();
    }

    @Test
    void addItemToInventoryAddsItemWhenSpaceAvailable() {
        Item item = new Item(new GameSpriteSourceInformation(null, 0, 0, 0, 0),
                new ItemInformation("pistol", null, false, false, false, 1),
                1);
        boolean result = inventory.addItemToInventory(item);
        assertTrue(result);
        assertEquals(1, inventory.getItems().size());
    }
    @Test
    void addItemToInventoryDoesNotAddItemWhenNoSpaceAvailable() {
        for (int i = 0; i < inventory.getInventoryInformation().numberOfCells(); i++) {
            inventory.addItemToInventory(new Item(new GameSpriteSourceInformation(null, 0, 0, 0, 0),
                                                  new ItemInformation("Item" + i, null, false, false, false, 0),
                                                    1));

        }
        Item extraItem = new Item(new GameSpriteSourceInformation(null, 0, 0, 0, 0),
                new ItemInformation("ExtraItem", null, false, false, false, 1),
                1);

        boolean result = inventory.addItemToInventory(extraItem);
        assertFalse(result);
        assertEquals(inventory.getInventoryInformation().numberOfCells(), inventory.getItems().size());
    }

    @Test
    void addItemToInventoryIncreasesItemAmountWhenSameItemExists() {
        Item item = new Item(new GameSpriteSourceInformation(null, 0, 0, 20, 20),
                new ItemInformation("Pistol", null, false, false, false,1),
                1);

        inventory.addItemToInventory(item);
        inventory.addItemToInventory(new Item(new GameSpriteSourceInformation(null, 0, 0, 20, 20),
                new ItemInformation("Pistol", null, false , false ,false, 2),
                1));
        InventoryCell cell = inventory.getInventoryCellByName("Pistol");
        assertNotNull(cell);
        assertEquals(2, cell.getItemAmount());
    }

    @Test
    void getInventoryCellByNameReturnsCorrectCell() {
        Item item = new Item(new GameSpriteSourceInformation(null, 0, 0, 20, 20),
                            new ItemInformation("Pistol", null, false, false, false, 0),
                            1);
        inventory.addItemToInventory(item);
        InventoryCell cell = inventory.getInventoryCellByName("Pistol");
        assertNotNull(cell);
        assertEquals("Pistol", cell.getItem().getItemInformation().name());
    }

    @Test
    void getInventoryCellByNameReturnsNullWhenItemNotFound() {
        InventoryCell cell = inventory.getInventoryCellByName("NonExistentItem");
        assertNull(cell);
    }

    @Test
    void adjustItemScalesAndCentersItemCorrectly() {
        Item item = new Item(new GameSpriteSourceInformation(null, 15, 15, 50, 50),
                            new ItemInformation("Pistol", null, false, false, false, 1),
                            1);
        inventory.adjustItem(item, 100, 100);
        assertEquals(10, item.getGameSpriteRenderInformation().getTargetWidth());
        assertEquals(10, item.getGameSpriteRenderInformation().getTargetHeight());
        assertEquals(100, item.getGameSpriteRenderInformation().getScreenCoordinateX());
        assertEquals(100, item.getGameSpriteRenderInformation().getScreenCoordinateY());
    }

    @Test
    void setItemsAddsAllItemsToInventory() {
        List<Item> items = List.of(
                new Item(new GameSpriteSourceInformation(null, 0, 0, 20, 20),
                        new ItemInformation("Pistol", null, false, false, false, 1),
                        1),
                new Item(new GameSpriteSourceInformation(null,0, 0, 20, 20),
                        new ItemInformation("Shotgun", null, false, false, false, 0),
                        1)
        );
        inventory.setItems(items);
        for (Item item: inventory.getItems()) {
            System.out.println(item.getItemInformation().name());
        }
        assertEquals(2, inventory.getItems().size());
    }
}