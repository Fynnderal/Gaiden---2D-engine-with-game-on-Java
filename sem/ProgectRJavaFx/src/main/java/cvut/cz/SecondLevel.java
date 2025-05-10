package cvut.cz;

import cvut.cz.Model.MapModel;
import cvut.cz.characters.ActionAreaInformation;
import cvut.cz.characters.NPC;
import cvut.cz.items.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents the second level of the game.
 * Extends the functionality of the FirstLevel class by overriding methods to create level-specific items and characters.
 */
public class SecondLevel extends FirstLevel{

    /**
     * Constructor for the SecondLevel class.
     *
     * @param mainApplication The main application instance.
     * @param renderManager   The render manager used for rendering game elements.
     */
    public SecondLevel(MainApplication mainApplication, RenderManager renderManager) {
        super(mainApplication, renderManager);
    }

    /**
     * Creates characters and places them on the map with their respective action areas.
     * Overrides the method from the FirstLevel class to define characters specific to the second level.
     */
    @Override
    protected void createCharactersOnMap() {
        ActionAreaInformation merchantActionArea = new ActionAreaInformation(6116, 1631, 919, 780, 500);

        ActionAreaInformation zombie1ActionArea = new ActionAreaInformation(1524, 2430, 7395, 270, 8000);
        ActionAreaInformation zombie2ActionArea = new ActionAreaInformation(3772, 950, 555, 1435, 8000);
        ActionAreaInformation zombie3ActionArea = new ActionAreaInformation(4699, 2734, 1093, 1495, 8000);
        ActionAreaInformation zombie4ActionArea = new ActionAreaInformation(7809, 2734, 1093, 1495, 8000);
        ActionAreaInformation zombie5ActionArea = new ActionAreaInformation(14469, 7414, 845, 700, 8000);
        ActionAreaInformation zombie6ActionArea = new ActionAreaInformation(8331, 4268, 575, 1446, 8000);
        ActionAreaInformation zombie7ActionArea = new ActionAreaInformation(13022, 8485, 1090, 1485, 8000);

        ActionAreaInformation watchman1ActionArea = new ActionAreaInformation(12098, 6694, 551, 1420, 250);
        ActionAreaInformation watchman2ActionArea = new ActionAreaInformation(13022, 8485, 1090, 1485, 250);
        ActionAreaInformation watchman3ActionArea = new ActionAreaInformation(16129, 8463, 1096, 1500, 250);
        ActionAreaInformation watchman4ActionArea = new ActionAreaInformation(1524, 2430, 7395, 270, 250);
        ActionAreaInformation watchman5ActionArea = new ActionAreaInformation(4699, 2734, 1093, 1495, 250);

        HashMap<String, Integer> itemPrices = new HashMap<>();
        itemPrices.put("Pistol bullet", 1);
        itemPrices.put("Medkit", 10);
        itemPrices.put("Chocolate bar", 7);

        List<NPC> charactersOnMap = new ArrayList<>();
        charactersOnMap.add(characterCreator.createMerchant(merchantActionArea, 6525, 1640, 5, itemPrices));

        charactersOnMap.add(characterCreator.createZombie(zombie1ActionArea,8342, 2529, 5));
        charactersOnMap.add(characterCreator.createZombie(zombie1ActionArea,7097, 2499, 5));
        charactersOnMap.add(characterCreator.createZombie(zombie2ActionArea, 4248, 1039, 5));
        charactersOnMap.add(characterCreator.createZombie(zombie2ActionArea, 4091, 1166, 5));
        charactersOnMap.add(characterCreator.createZombie(zombie3ActionArea, 5591, 3957, 5));
        charactersOnMap.add(characterCreator.createZombie(zombie3ActionArea, 4900, 3241, 5));
        charactersOnMap.add(characterCreator.createZombie(zombie3ActionArea, 5618, 2893, 5));
        charactersOnMap.add(characterCreator.createZombie(zombie4ActionArea, 8218, 2916, 5));
        charactersOnMap.add(characterCreator.createZombie(zombie4ActionArea, 8714, 3507, 5));
        charactersOnMap.add(characterCreator.createZombie(zombie4ActionArea, 7936, 3771, 5));
        charactersOnMap.add(characterCreator.createZombie(zombie4ActionArea, 8771, 3876, 5));

        charactersOnMap.add(characterCreator.createZombie(zombie5ActionArea, 15165, 7918, 5));
        charactersOnMap.add(characterCreator.createZombie(zombie5ActionArea, 14601, 7716, 5));
        charactersOnMap.add(characterCreator.createZombie(zombie7ActionArea, 13236, 9381, 5));
        charactersOnMap.add(characterCreator.createZombie(zombie7ActionArea, 14046, 8891, 5));
        charactersOnMap.add(characterCreator.createZombie(zombie6ActionArea, 8411, 4376, 5));
        charactersOnMap.add(characterCreator.createZombie(zombie6ActionArea, 8826, 4691, 5));
        charactersOnMap.add(characterCreator.createZombie(zombie6ActionArea, 8391, 4756, 5));
        charactersOnMap.add(characterCreator.createZombie(zombie6ActionArea, 8601, 5286, 5));
        charactersOnMap.add(characterCreator.createZombie(zombie6ActionArea, 8811, 5506, 5));
        charactersOnMap.add(characterCreator.createZombie(zombie6ActionArea, 8751, 5126, 5));


        charactersOnMap.add(characterCreator.createWatchMan(watchman4ActionArea,3756, 2511, 5));
        charactersOnMap.add(characterCreator.createWatchMan(watchman1ActionArea, 12505, 6935, 5));
        charactersOnMap.add(characterCreator.createWatchMan(watchman1ActionArea, 12161, 7646, 5));
        charactersOnMap.add(characterCreator.createWatchMan(watchman2ActionArea, 13958, 9551, 5));
        charactersOnMap.add(characterCreator.createWatchMan(watchman5ActionArea,5621, 4026, 5));
        charactersOnMap.add(characterCreator.createWatchMan(watchman2ActionArea, 13079, 8907, 5));
        charactersOnMap.add(characterCreator.createWatchMan(watchman3ActionArea, 16241, 9811, 5));
        charactersOnMap.add(characterCreator.createWatchMan(watchman3ActionArea, 17117, 8925, 5));
        charactersOnMap.add(characterCreator.createWatchMan(watchman3ActionArea, 16877, 9650, 5));
        charactersOnMap.add(characterCreator.createWatchMan(watchman3ActionArea, 16341, 8786, 5));


        MapModel.getMapModel().setCharactersInWorld(charactersOnMap);
    }

    /**
     * Creates items and places them on the map at specific locations.
     * Overrides the method from the FirstLevel class to define items specific to the second level.
     */
    @Override
    protected void createItemsOnMap(){
        List<Item> itemsOnMap = new ArrayList<>();
        itemsOnMap.add(itemCreator.createTeaBag(1394, 494, 0.15, 3));
        itemsOnMap.add(itemCreator.createChocolateBar(1382, 154, 0.1, 1));
        itemsOnMap.add(itemCreator.createChocolateBar(789, 1046, 0.1, 2));

        itemsOnMap.add(itemCreator.createPistolBullet(1305, 1801, 0.2, 10));
        itemsOnMap.add(itemCreator.createPistolBullet(1313, 2237, 0.2, 10));
        itemsOnMap.add(itemCreator.createTeaBag(54, 2104, 0.15, 10));

        itemsOnMap.add(itemCreator.createKey(1876, 2841, 4, 1));

        itemsOnMap.add(itemCreator.createTeaBag(4189, 1144, 0.15, 2));

        itemsOnMap.add(itemCreator.createShotGunShell(4195, 1056, 0.08, 3));


        itemsOnMap.add(itemCreator.creatPistolBullet(4256, 2206, 0.4, 5));


        itemsOnMap.add(itemCreator.createMedKit(4900, 3540, 0.2, 2));

        itemsOnMap.add(itemCreator.createShotGunShell(4903, 3096, 0.08, 4));


        itemsOnMap.add(itemCreator.createKey(5491, 2768, 4, 1));

        itemsOnMap.add(itemCreator.createKey(7893, 3567, 4, 1));


        itemsOnMap.add(itemCreator.createShotGunShell(1326, 2691, 0.08,  3));

        itemsOnMap.add(itemCreator.createMedKit(8814, 5413, 0.9, 2));

        itemsOnMap.add(itemCreator.createPistolBullet(9151, 6740, 0.4, 4));
        itemsOnMap.add(itemCreator.createChocolateBar(9512, 6469, 0.1, 2));
        itemsOnMap.add(itemCreator.createChocolateBar(9626, 5950, 0.1, 1));

        itemsOnMap.add(itemCreator.createTeaBag(8930, 6894, 0.15, 7));


        itemsOnMap.add(itemCreator.createPistolBullet(8894, 7928, 0.2, 10));

        itemsOnMap.add(itemCreator.createTeaBag(9506, 8202 , 0.15, 4));

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
}
