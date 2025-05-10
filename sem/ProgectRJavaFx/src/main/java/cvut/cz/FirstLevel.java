package cvut.cz;

import cvut.cz.LevelCreator.CharacterCreator;
import cvut.cz.LevelCreator.ItemCreator;
import cvut.cz.LevelCreator.LevelCreator;
import cvut.cz.Map.*;
import cvut.cz.Model.MainPlayerModel;
import cvut.cz.Model.MapModel;
import cvut.cz.characters.ActionAreaInformation;
import cvut.cz.characters.CrossHair;
import cvut.cz.characters.NPC;

import cvut.cz.items.Item;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the first level of the game.
 * Handles the creation of the level, including GUI, items, map spots, and characters.
 */
public class FirstLevel{
    protected final MainApplication mainApp;

    protected final ItemCreator itemCreator;
    protected final CharacterCreator characterCreator;
    protected final LevelCreator levelCreator;

    /**
     * Constructor for the FirstLevel class.
     *
     * @param mainApplication The main application instance.
     * @param renderManager   The render manager used for rendering game elements.
     */
    public FirstLevel(MainApplication mainApplication, RenderManager renderManager){
        this.mainApp = mainApplication;
        itemCreator= new ItemCreator(renderManager, mainApp);
        characterCreator = new CharacterCreator(renderManager, mainApp);
        levelCreator = new LevelCreator(renderManager, mainApp);
    }

    /**
     * Creates the GUI for the level
     */
    protected void createGUI(){
        Map<String, CrossHair> weaponsAndGunSights = new HashMap<>();
        weaponsAndGunSights.put("pistol", levelCreator.createPistolCrossHair(4));
        weaponsAndGunSights.put("shotgun", levelCreator.createShotGunCrossHair(4));

        MainPlayerModel.getMainPlayerModel().getMainPlayer().setWeaponsAndCrossHairs(weaponsAndGunSights);

        configureInventory();

        mainApp.getGuiCreator().createHealthPanel();
        mainApp.getMainContainer().getChildren().add(mainApp.getGuiCreator().getHealthPanel());
    }

    /**
     * Configures the inventory by adding possible items that can be used in the game.
     */
    protected void configureInventory(){
        HashMap<String, Item> possibleItems = new HashMap<>();
        possibleItems.put("shotgun", itemCreator.createShotGun(0, 0, 5, 1));
        possibleItems.put("teabag", itemCreator.createTeaBag(0,0, 5, 1));
        possibleItems.put("pistol", itemCreator.createPistol(0, 0, 5, 1));
        possibleItems.put("pistolbullet", itemCreator.creatPistolBullet(0, 0, 5, 1));
        possibleItems.put("medkit", itemCreator.createMedKit(0, 0, 5, 1));
        possibleItems.put("chocolatebar", itemCreator.createChocolateBar(0, 0, 5, 1));
        possibleItems.put("shotgunshell", itemCreator.createShotGunShell(0, 0, 5, 1));
        possibleItems.put("water", itemCreator.createGlassOfWater(0, 0, 5, 1));
        possibleItems.put("key", itemCreator.createKey(0, 0, 5, 1));
        possibleItems.put("crowbar", itemCreator.createCrowbar(0, 0, 5, 1));
        possibleItems.put("screwdriver", itemCreator.createScrewdriver(0, 0, 5, false, 1));
        possibleItems.put("ducttape", itemCreator.createDuctTape(0, 0, 5, 1));
        possibleItems.put("tea", itemCreator.createTea(0, 0, 5, 1));
        possibleItems.put("brokenscrewdriver", itemCreator.createScrewdriver(0, 0, 5, true,1));

        MainPlayerModel.getMainPlayerModel().getInventory().setPossibleItems(possibleItems);

    }

    /**
     * Creates items and places them on the map at specific locations.
     */
    protected void createItemsOnMap(){
        List<Item> itemsOnMap = new ArrayList<>();
        itemsOnMap.add(itemCreator.createGlassOfWater(1394, 494, 0.4, 1));
        itemsOnMap.add(itemCreator.createTea(1382, 154, 0.3, 1));
        itemsOnMap.add(itemCreator.createChocolateBar(789, 1046, 0.1, 2));
        itemsOnMap.add(itemCreator.createPistol(1365, 2173, 0.9, 1));

        itemsOnMap.add(itemCreator.createPistolBullet(1305, 1801, 0.2, 10));
        itemsOnMap.add(itemCreator.createPistolBullet(1313, 2237, 0.2, 10));
        itemsOnMap.add(itemCreator.createTeaBag(54, 2104, 0.15, 10));

        itemsOnMap.add(itemCreator.createKey(1876, 2841, 4, 1));

        itemsOnMap.add(itemCreator.createTea(4189, 1144, 0.3, 2));

        itemsOnMap.add(itemCreator.createShotGunShell(4195, 1056, 0.08, 3));

        itemsOnMap.add(itemCreator.createCrowbar(3814, 1594, 0.8, 1));

        itemsOnMap.add(itemCreator.createGlassOfWater(4256, 2206, 0.4, 2));


        itemsOnMap.add(itemCreator.createMedKit(4900, 3540, 0.9, 2));

        itemsOnMap.add(itemCreator.createShotGunShell(4903, 3096, 0.08, 4));


        itemsOnMap.add(itemCreator.createKey(5491, 2768, 4, 1));

        itemsOnMap.add(itemCreator.createKey(7893, 3567, 4, 1));

        itemsOnMap.add(itemCreator.createScrewdriver(8745, 3819, 0.2, true, 1));

        itemsOnMap.add(itemCreator.createDuctTape(1326, 2691, 1.7, 1));

        itemsOnMap.add(itemCreator.createShotGun(8814, 5413, 1.1, 1));

        itemsOnMap.add(itemCreator.createGlassOfWater(9151, 6740, 0.4, 1));
        itemsOnMap.add(itemCreator.createTea(9512, 6469, 0.3, 1));
        itemsOnMap.add(itemCreator.createChocolateBar(9626, 5950, 0.1, 1));

        itemsOnMap.add(itemCreator.createTeaBag(8930, 6894, 0.15, 7));


        itemsOnMap.add(itemCreator.createPistolBullet(8894, 7928, 0.2, 10));

        itemsOnMap.add(itemCreator.createTea(9506, 8202 , 0.3, 2));

        itemsOnMap.add(itemCreator.createChocolateBar(8423, 7802, 0.1, 2));
        itemsOnMap.add(itemCreator.createShotGunShell(8521, 7399, 0.08, 5));

        itemsOnMap.add(itemCreator.createKey(12381, 7080, 4, 1));

        itemsOnMap.add(itemCreator.createMedKit(12477, 7403, 0.9, 1));

        itemsOnMap.add(itemCreator.createChocolateBar(13828, 8984, 0.1, 2));
        itemsOnMap.add(itemCreator.createShotGunShell(13812, 9623, 0.08, 5));



        itemsOnMap.add(itemCreator.createPistolBullet(11520, 8282, 0.2, 8));
        itemsOnMap.add(itemCreator.createKey(9634, 6725, 4, 1));


        itemsOnMap.add(itemCreator.createKey(14994, 7552, 4, 1));
        itemsOnMap.add(itemCreator.createMedKit(14570, 7756, 0.9, 1));

        MapModel.getMapModel().setItemsInWorld(itemsOnMap);

    }


    /**
     * Creates map spots and places them on the map.
     */
    protected void createMapSpots(){
        Ventilation ventilation = levelCreator.createVentilation(8576, 4321, 1);
        Ventilation ventilation2 = levelCreator.createVentilation(3841, 996, 1);


        ventilation2.setSpotToTeleport(ventilation);
        ventilation.setSpotToTeleport(ventilation2);

        Ventilation ventilation3 = levelCreator.createVentilation(8816, 5821, 1);
        Ventilation ventilation4 = levelCreator.createVentilation(8006, 2781, 1);


        ventilation4.setSpotToTeleport(ventilation3);
        ventilation3.setSpotToTeleport(ventilation4);

        List<MapSpot> mapSpots = new ArrayList<>();

        URL pathToKeyDoor = MainApplication.class.getResource("/cvut/cz/keyDoor.png");
        URL pathToRustDoor = MainApplication.class.getResource("/cvut/cz/rustDoor.png");

        Door door1 = levelCreator.createDoor(4032, 2408, 7, "key", pathToKeyDoor);
        Door door2 = levelCreator.createDoor(5089, 2702, 7, "crowbar", pathToRustDoor);
        Door door3 = levelCreator.createDoor(8197, 2701, 7, "key", pathToKeyDoor);
        Door door4 = levelCreator.createDoor(9492, 7218, 7, "key", pathToKeyDoor);
        Door door5 = levelCreator.createDoor(10038, 8442, 7, "crowbar", pathToRustDoor);
        Door door6 = levelCreator.createDoor(12355, 8148, 7, "key", pathToKeyDoor);
        Door door7 = levelCreator.createDoor(14861, 8148, 7, "key", pathToKeyDoor);
        Door door8 = levelCreator.createDoor(16520, 8442, 7, "key", pathToKeyDoor);
        Door door9 = levelCreator.createDoor(16982, 9989, 7, "crowbar", pathToRustDoor);


        mapSpots.add(levelCreator.createVendingMachine(598, 786, 7, itemCreator.createChocolateBar(610, 800, 0.1, 1)));
        mapSpots.add(levelCreator.createVendingMachine(722, 786, 7, itemCreator.createChocolateBar(800, 800, 0.1, 1)));
        mapSpots.add(levelCreator.createVendingMachine(8441, 2715, 7, itemCreator.createTeaBag(8450, 2725, 5, 1)));
        mapSpots.add(levelCreator.createVendingMachine(8581, 2715, 7, itemCreator.createChocolateBar(8590, 2725, 0.1, 1)));
        mapSpots.add(levelCreator.createVendingMachine(8706, 2715, 7, itemCreator.createTeaBag(8715, 2725, 5, 1)));
        mapSpots.add(levelCreator.createVendingMachine(9301, 5761, 7, itemCreator.createChocolateBar(9310, 5790, 0.1, 1)));
        mapSpots.add(levelCreator.createVendingMachine(9562, 5759, 7, itemCreator.createChocolateBar(9570, 5770, 0.1, 1)));

        mapSpots.add(ventilation2);
        mapSpots.add(ventilation);
        mapSpots.add(ventilation3);
        mapSpots.add(ventilation4);

        mapSpots.add(door1);
        mapSpots.add(door2);
        mapSpots.add(door3);
        mapSpots.add(door4);
        mapSpots.add(door5);
        mapSpots.add(door6);
        mapSpots.add(door7);
        mapSpots.add(door8);
        mapSpots.add(door9);


        MapModel.getMapModel().setMapSpots(mapSpots);

        MapModel.getMapModel().setPassageBetweenLevels(new PassageBetweenLevels(16656, 11492, 700,500));
    }

    /**
     * Creates characters and places them on the map
     */
    protected void createCharactersOnMap(){
        ActionAreaInformation merchantActionArea = new ActionAreaInformation(6116, 1631, 919, 780, 500);

        ActionAreaInformation zombie1ActionArea = new ActionAreaInformation(1524, 2430, 7395, 270, 8000);
        ActionAreaInformation zombie2ActionArea = new ActionAreaInformation(3772, 950, 555, 1435, 8000);
        ActionAreaInformation zombie3ActionArea = new ActionAreaInformation(4699, 2734, 1093, 1495, 8000);
        ActionAreaInformation zombie4ActionArea = new ActionAreaInformation(7809, 2734, 1093, 1495, 8000);
        ActionAreaInformation zombie5ActionArea = new ActionAreaInformation(14469, 7414, 845, 700, 8000);
        ActionAreaInformation zombie6ActionArea = new ActionAreaInformation(8331, 4268, 575, 1446, 8000);

        ActionAreaInformation watchman1ActionArea = new ActionAreaInformation(12098, 6694, 551, 1420, 250);
        ActionAreaInformation watchman2ActionArea = new ActionAreaInformation(13022, 8485, 1090, 1485, 250);
        ActionAreaInformation watchman3ActionArea = new ActionAreaInformation(16129, 8463, 1096, 1500, 250);

        HashMap<String, Integer> itemPrices = new HashMap<>();
        itemPrices.put("Pistol bullet", 1);
        itemPrices.put("Medkit", 10);
        itemPrices.put("Chocolate bar", 7);

        List<NPC> charactersOnMap = new ArrayList<>();
        charactersOnMap.add(characterCreator.createMerchant(merchantActionArea, 6525, 1640, 5, itemPrices));

        charactersOnMap.add(characterCreator.createZombie(zombie1ActionArea,8342, 2529, 5));
        charactersOnMap.add(characterCreator.createZombie(zombie1ActionArea,7097, 2499, 5));
        charactersOnMap.add(characterCreator.createZombie(zombie2ActionArea, 4248, 1039, 5));
        charactersOnMap.add(characterCreator.createZombie(zombie3ActionArea, 5591, 3957, 5));
        charactersOnMap.add(characterCreator.createZombie(zombie3ActionArea, 4900, 3241, 5));
        charactersOnMap.add(characterCreator.createZombie(zombie3ActionArea, 5618, 2893, 5));
        charactersOnMap.add(characterCreator.createZombie(zombie4ActionArea, 8218, 2916, 5));
        charactersOnMap.add(characterCreator.createZombie(zombie4ActionArea, 8714, 3507, 5));
        charactersOnMap.add(characterCreator.createZombie(zombie5ActionArea, 15165, 7918, 5));
        charactersOnMap.add(characterCreator.createZombie(zombie6ActionArea, 8411, 4376, 5));
        charactersOnMap.add(characterCreator.createZombie(zombie6ActionArea, 8826, 4691, 5));
        charactersOnMap.add(characterCreator.createZombie(zombie6ActionArea, 8391, 4756, 5));

        charactersOnMap.add(characterCreator.createWatchMan(watchman1ActionArea, 12505, 6935, 5));
        charactersOnMap.add(characterCreator.createWatchMan(watchman2ActionArea, 13958, 9551, 5));
        charactersOnMap.add(characterCreator.createWatchMan(watchman2ActionArea, 13079, 8907, 5));
        charactersOnMap.add(characterCreator.createWatchMan(watchman3ActionArea, 16241, 9811, 5));
        charactersOnMap.add(characterCreator.createWatchMan(watchman3ActionArea, 17117, 8925, 5));
        charactersOnMap.add(characterCreator.createWatchMan(watchman3ActionArea, 16877, 9650, 5));

        MapModel.getMapModel().setCharactersInWorld(charactersOnMap);
    }

    /**
     * Creates the entire level, including the map, items, GUI, characters, and map spots.
     */
    public void createLevel(){
        levelCreator.createInventory();

        levelCreator.createMap(MapModel.getMapModel().getCurrentLevel());

        MainPlayerModel.getMainPlayerModel().setMainPlayer(characterCreator.createOfficeWorker(0,0, 5));
        MainPlayerModel.getMainPlayerModel().getMainPlayer().setInventory(MainPlayerModel.getMainPlayerModel().getInventory());
        createItemsOnMap();
        createGUI();

        List<Item> items = new ArrayList<>();
        MainPlayerModel.getMainPlayerModel().getInventory().setItems(items);

        createMapSpots();
        createCharactersOnMap();


        MapModel.getMapModel().getDrawableObjects().remove(MainPlayerModel.getMainPlayerModel().getMainPlayer());
        MapModel.getMapModel().getDrawableObjects().add(MainPlayerModel.getMainPlayerModel().getMainPlayer());
    }

}
