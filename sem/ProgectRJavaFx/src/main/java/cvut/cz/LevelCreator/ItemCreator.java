package cvut.cz.LevelCreator;

import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;
import cvut.cz.MainApplication;
import cvut.cz.RenderManager;
import cvut.cz.items.Item;
import cvut.cz.items.ItemInformation;
import javafx.scene.image.Image;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * A class responsible for creating various items in the game.
 * This includes defining their properties, appearance, and behavior.
 */
public class ItemCreator extends LevelCreator {

    /**
     * Constructor for the ItemCreator class.
     *
     * @param renderManager The render manager responsible for rendering game objects.
     * @param mainApp The main application instance.
     */
    public ItemCreator(RenderManager renderManager, MainApplication mainApp) {
        super(renderManager, mainApp);
    }


    /**
     * Creates a pistol item at the specified world coordinates.
     *
     * @param worldX The X-coordinate of the pistol in the world.
     * @param worldY The Y-coordinate of the pistol in the world.
     * @param scaleFactor The scale factor for resizing the pistol's sprite.
     * @return An Item object representing the pistol.
     */
    public Item createPistol(int worldX, int worldY, double scaleFactor, int amount) {
        URL pathToPistol = MainApplication.class.getResource("/cvut/cz/pistol.png");

        GameSpriteSourceInformation pistolSourceInformation = new GameSpriteSourceInformation(pathToPistol, 0, 0, 30, 45);
        GameSpriteRenderInformation pistolRenderInformation = new GameSpriteRenderInformation(0, 0, (int) Math.ceil(30 * scaleFactor), (int) Math.ceil(45 * scaleFactor), worldX, worldY);

        ItemInformation pistolInformation = new ItemInformation("pistol", null, true, false, false, 14);

        renderManager.getImagesToDraw().put(String.valueOf(pathToPistol), new Image(String.valueOf(pathToPistol)));

        return  new Item(pistolSourceInformation, pistolRenderInformation, pistolInformation, amount);
    }

    /**
     * Creates a teabag item at the specified world coordinates.
     *
     * @param worldX The X-coordinate of the teabag in the world.
     * @param worldY The Y-coordinate of the teabag in the world.
     * @param scaleFactor The scale factor for resizing the teabag's sprite.
     * @return An Item object representing the teabag.
     */
    public Item createTeaBag(int worldX, int worldY, double scaleFactor, int amount) {
        URL pathToTeabag = MainApplication.class.getResource("/cvut/cz/Teabag.png");

        Map<String, String> combinations = new HashMap<>();
        combinations.put("water", "tea");

        GameSpriteSourceInformation teaBagSourceInformation = new GameSpriteSourceInformation(pathToTeabag, 0, 0, 176, 226);
        GameSpriteRenderInformation teaBagRenderInformation = new GameSpriteRenderInformation(0, 0, (int)Math.ceil(176 * scaleFactor), (int)Math.ceil(226 * scaleFactor), worldX, worldY);

        ItemInformation teaBagItemWorldInformation = new ItemInformation("teabag", combinations, false, false, true, 0);

        renderManager.getImagesToDraw().put(String.valueOf(pathToTeabag), new Image(String.valueOf(pathToTeabag)));

        return new Item(teaBagSourceInformation, teaBagRenderInformation, teaBagItemWorldInformation, amount);
    }

    /**
     * Creates a screwdriver item at the specified world coordinates.
     *
     * @param worldX The X-coordinate of the screwdriver in the world.
     * @param worldY The Y-coordinate of the screwdriver in the world.
     * @param scaleFactor The scale factor for resizing the screwdriver's sprite.
     * @return An Item object representing the screwdriver.
     */
    public Item createScrewdriver(int worldX, int worldY, double scaleFactor, boolean broken, int amount) {
        URL pathToScrewdriver;
        ItemInformation screwdriverItemWorldInformation;
        if (broken){
            pathToScrewdriver = MainApplication.class.getResource("/cvut/cz/brokenscrewdriver.png");

            Map<String, String> combinations = new HashMap<>();
            combinations.put("ducttape", "screwdriver");

            screwdriverItemWorldInformation = new ItemInformation("brokenscrewdriver", combinations, false, false, false, 0);
        }else {
            pathToScrewdriver = MainApplication.class.getResource("/cvut/cz/screwdriver.png");
            screwdriverItemWorldInformation = new ItemInformation("screwdriver", null, false, true, false   , 0);
        }

        GameSpriteSourceInformation screwdriverSourceInformation = new GameSpriteSourceInformation(pathToScrewdriver, 0, 0, 247, 246);
        GameSpriteRenderInformation screwdriverRenderInformation = new GameSpriteRenderInformation(0, 0, (int)Math.ceil(247 * scaleFactor), (int)Math.ceil(246 * scaleFactor), worldX, worldY);


        renderManager.getImagesToDraw().put(String.valueOf(pathToScrewdriver), new Image(String.valueOf(pathToScrewdriver)));

        return new Item(screwdriverSourceInformation, screwdriverRenderInformation, screwdriverItemWorldInformation, amount);
    }

    /**
     * Creates a pistol bullet item at the specified world coordinates.
     *
     * @param worldX The X-coordinate of the pistol bullet in the world.
     * @param worldY The Y-coordinate of the pistol bullet in the world.
     * @param scaleFactor The scale factor for resizing the pistol bullet's sprite.
     * @return An Item object representing the pistol bullet.
     */
    public Item createPistolBullet(int worldX, int worldY, double scaleFactor, int amount) {
        URL pathToPistolBullet = MainApplication.class.getResource("/cvut/cz/PistolBullet.png");

        GameSpriteSourceInformation pistolBulletSourceInformation = new GameSpriteSourceInformation(pathToPistolBullet, 0, 0, 62, 166);
        GameSpriteRenderInformation pistolBulletRenderInformation = new GameSpriteRenderInformation(0, 0, (int) Math.ceil(62 * scaleFactor), (int) Math.ceil(166 * scaleFactor), worldX, worldY);

        ItemInformation pistolBulletInformation = new ItemInformation("pistolbullet", null, false, false, true, 0);

        renderManager.getImagesToDraw().put(String.valueOf(pathToPistolBullet), new Image(String.valueOf(pathToPistolBullet)));

        return  new Item(pistolBulletSourceInformation, pistolBulletRenderInformation, pistolBulletInformation, amount);
    }
    /**
     * Creates a shotgun shell item at the specified world coordinates.
     *
     * @param worldX The X-coordinate of the shotgun shell in the world.
     * @param worldY The Y-coordinate of the shotgun shell in the world.
     * @param scaleFactor The scale factor for resizing the shotgun shell's sprite.
     * @return An Item object representing the shotgun shell.
     */
    public Item createShotGunShell(int worldX, int worldY, double scaleFactor, int amount) {
        URL pathToShotGunShell = MainApplication.class.getResource("/cvut/cz/ShotGunShell.png");

        GameSpriteSourceInformation shotGunShellSourceInformation = new GameSpriteSourceInformation(pathToShotGunShell, 0, 0, 168, 347);
        GameSpriteRenderInformation shotGunShellRenderInformation = new GameSpriteRenderInformation(0, 0, (int) Math.ceil(168 * scaleFactor), (int) Math.ceil(347 * scaleFactor), worldX, worldY);

        ItemInformation shotGunShellInformation = new ItemInformation("shotgunshell", null, false, false, true, 0);

        renderManager.getImagesToDraw().put(String.valueOf(pathToShotGunShell), new Image(String.valueOf(pathToShotGunShell)));

        return  new Item(shotGunShellSourceInformation, shotGunShellRenderInformation, shotGunShellInformation, amount);
    }

    /**
     * Creates a shotgun item at the specified world coordinates.
     *
     * @param worldX The X-coordinate of the shotgun in the world.
     * @param worldY The Y-coordinate of the shotgun in the world.
     * @param scaleFactor The scale factor for resizing the shotgun's sprite.
     * @return An Item object representing the shotgun.
     */
    public Item createShotGun(int worldX, int worldY, double scaleFactor, int amount) {
        URL pathToShotgun = MainApplication.class.getResource("/cvut/cz/Shotgun.png");

        GameSpriteSourceInformation shotgunSourceInformation = new GameSpriteSourceInformation(pathToShotgun, 0, 0, 33, 41);
        GameSpriteRenderInformation shotgunRenderInformation = new GameSpriteRenderInformation(0, 0, (int) Math.ceil(33 * scaleFactor), (int) Math.ceil(41 * scaleFactor), worldX, worldY);

        ItemInformation shotgunItemInformation = new ItemInformation("shotgun", null, true, false, false, 7);
        renderManager.getImagesToDraw().put(String.valueOf(pathToShotgun), new Image(String.valueOf(pathToShotgun)));

        return new Item(shotgunSourceInformation, shotgunRenderInformation, shotgunItemInformation, amount);
    }

    /**
     * Creates a pistol bullet item at the specified world coordinates.
     *
     * @param worldX The X-coordinate of the pistol bullet in the world.
     * @param worldY The Y-coordinate of the pistol bullet in the world.
     * @param scaleFactor The scale factor for resizing the pistol bullet's sprite.
     * @return An Item object representing the pistol bullet.
     */
    public Item creatPistolBullet(int worldX, int worldY, double scaleFactor, int amount) {
        URL pathToPistolBullet = MainApplication.class.getResource("/cvut/cz/PistolBullet.png");

        GameSpriteSourceInformation pistolBulletSourceInformation = new GameSpriteSourceInformation(pathToPistolBullet, 0, 0, 64, 166);
        GameSpriteRenderInformation pistolBulletRenderInformation = new GameSpriteRenderInformation(0, 0, (int)Math.ceil(64 * scaleFactor), (int)Math.ceil(166 * scaleFactor), worldX, worldY);
        ItemInformation pistolBulletInformation = new ItemInformation("pistolbullet", null, false, false, true, 0);

        renderManager.getImagesToDraw().put(String.valueOf(pathToPistolBullet), new Image(String.valueOf(pathToPistolBullet)));

        return  new Item(pistolBulletSourceInformation, pistolBulletRenderInformation, pistolBulletInformation, amount);
    }

    /**
     * Creates a medkit item at the specified world coordinates.
     *
     * @param worldX The X-coordinate of the medkit in the world.
     * @param worldY The Y-coordinate of the medkit in the world.
     * @param scaleFactor The scale factor for resizing the medkit's sprite.
     * @return An Item object representing the medkit.
     */
    public Item createMedKit(int worldX, int worldY, double scaleFactor, int amount) {
        URL pathToMedKit = MainApplication.class.getResource("/cvut/cz/medkit.png");

        GameSpriteSourceInformation medKitSourceInformation = new GameSpriteSourceInformation(pathToMedKit, 0, 0, 36, 31);
        GameSpriteRenderInformation medKitRenderInformation = new GameSpriteRenderInformation(0,0, (int) Math.ceil(scaleFactor * 36), (int) Math.ceil(31 * scaleFactor), worldX, worldY);

        ItemInformation medKitInformation = new ItemInformation("medkit", null, false, true, true, -50);
        renderManager.getImagesToDraw().put(String.valueOf(pathToMedKit), new Image(String.valueOf(pathToMedKit)));

        return new Item(medKitSourceInformation, medKitRenderInformation, medKitInformation, amount);
    }

    /**
     * Creates a key item at the specified world coordinates.
     *
     * @param worldX The X-coordinate of the key in the world.
     * @param worldY The Y-coordinate of the key in the world.
     * @param scaleFactor The scale factor for resizing the key's sprite.
     * @return An Item object representing the key.
     */
    public Item createKey(int worldX, int worldY, double scaleFactor, int amount) {
        URL pathToKey = MainApplication.class.getResource("/cvut/cz/key.png");

        GameSpriteSourceInformation keySourceInformation = new GameSpriteSourceInformation(pathToKey, 0, 0, 12, 12);
        GameSpriteRenderInformation keyRenderInformation = new GameSpriteRenderInformation(0,0, (int) Math.ceil(scaleFactor * 12), (int) Math.ceil(12 * scaleFactor), worldX, worldY);
        ItemInformation keyInformation = new ItemInformation("key", null, false, true, false, 0);

        renderManager.getImagesToDraw().put(String.valueOf(pathToKey), new Image(String.valueOf(pathToKey)));

        return new Item(keySourceInformation, keyRenderInformation, keyInformation, amount);
    }

    /**
     * Creates a crowbar item at the specified world coordinates.
     *
     * @param worldX The X-coordinate of the crowbar in the world.
     * @param worldY The Y-coordinate of the crowbar in the world.
     * @param scaleFactor The scale factor for resizing the v's sprite.
     * @return An Item object representing the crowbar.
     */
    public Item createCrowbar(int worldX, int worldY, double scaleFactor, int amount) {
        URL pathToCrowbar = MainApplication.class.getResource("/cvut/cz/crowbar.png");

        GameSpriteSourceInformation crowbarSourceInformation = new GameSpriteSourceInformation(pathToCrowbar, 0, 0, 76, 64);
        GameSpriteRenderInformation crowbarRenderInformation = new GameSpriteRenderInformation(0,0, (int) Math.ceil(scaleFactor * 76), (int) Math.ceil(64 * scaleFactor), worldX, worldY);
        ItemInformation crowbarInformation = new ItemInformation("crowbar", null, false, true, false, 0);

        renderManager.getImagesToDraw().put(String.valueOf(pathToCrowbar), new Image(String.valueOf(pathToCrowbar)));

        return new Item(crowbarSourceInformation, crowbarRenderInformation, crowbarInformation, amount);
    }
    /**
     * Creates a chocolate bar item at the specified world coordinates.
     *
     * @param worldX The X-coordinate of the chocolate bar in the world.
     * @param worldY The Y-coordinate of the chocolate bar in the world.
     * @param scaleFactor The scale factor for resizing the chocolate bar's sprite.
     * @return An Item object representing the chocolate bar.
     */
    public Item createChocolateBar(int worldX, int worldY, double scaleFactor, int amount) {
        URL pathToChocolateBar = MainApplication.class.getResource("/cvut/cz/chocolateBar.png");

        GameSpriteSourceInformation chocolateBarSourceInformation = new GameSpriteSourceInformation(pathToChocolateBar, 0, 0, 437, 222);
        GameSpriteRenderInformation chocolateBarRenderInformation = new GameSpriteRenderInformation(0, 0, (int) Math.ceil(437 * scaleFactor), (int) Math.ceil(222 * scaleFactor), worldX, worldY);

        ItemInformation chocolateBarInformation = new ItemInformation("chocolatebar", null, false, true, true, -10);
        renderManager.getImagesToDraw().put(String.valueOf(pathToChocolateBar), new Image(String.valueOf(pathToChocolateBar)));

        return  new Item(chocolateBarSourceInformation, chocolateBarRenderInformation, chocolateBarInformation, amount);
    }

    /**
     * Creates a duct tape item at the specified world coordinates.
     *
     * @param worldX The X-coordinate of the duct tape in the world.
     * @param worldY The Y-coordinate of the duct tape in the world.
     * @param scaleFactor The scale factor for resizing the duct tape's sprite.
     * @return An Item object representing the duct tape.
     */
    public Item createDuctTape(int worldX, int worldY, double scaleFactor, int amount) {
        URL pathToDuctTape= MainApplication.class.getResource("/cvut/cz/ducttape.png");

        Map<String, String> combinations = new HashMap<>();
        combinations.put("brokenscrewdriver", "screwdriver");

        GameSpriteSourceInformation ductTapeSourceInformation = new GameSpriteSourceInformation(pathToDuctTape, 0, 0, 15, 13);
        GameSpriteRenderInformation ductTapeRenderInformation = new GameSpriteRenderInformation(0, 0, (int)Math.ceil(16 * scaleFactor), (int)Math.ceil(13 * scaleFactor), worldX, worldY);

        ItemInformation ductTapeItemWorldInformation = new ItemInformation("ducttape", combinations, false, false, false, 0);

        renderManager.getImagesToDraw().put(String.valueOf(pathToDuctTape), new Image(String.valueOf(pathToDuctTape)));

        return new Item(ductTapeSourceInformation, ductTapeRenderInformation, ductTapeItemWorldInformation, amount);
    }

    /**
     * Creates a glass of water item at the specified world coordinates.
     *
     * @param worldX The X-coordinate of the glass of water in the world.
     * @param worldY The Y-coordinate of the glass of water in the world.
     * @param scaleFactor The scale factor for resizing the glass of water's sprite.
     * @return An Item object representing the glass of water.
     */
    public Item createGlassOfWater(int worldX, int worldY, double scaleFactor, int amount) {
        URL pathToGlassOfWater= MainApplication.class.getResource("/cvut/cz/water.png");

        Map<String, String> combinations = new HashMap<>();
        combinations.put("teabag", "tea");

        GameSpriteSourceInformation glassOfWaterSourceInformation = new GameSpriteSourceInformation(pathToGlassOfWater, 0, 0, 48, 63);
        GameSpriteRenderInformation glassOfWaterRenderInformation = new GameSpriteRenderInformation(0, 0, (int)Math.ceil(48 * scaleFactor), (int)Math.ceil(63 * scaleFactor), worldX, worldY);

        ItemInformation glassOfWaterItemWorldInformation = new ItemInformation("water", combinations, false, true, true, -5);

        renderManager.getImagesToDraw().put(String.valueOf(pathToGlassOfWater), new Image(String.valueOf(pathToGlassOfWater)));

        return new Item(glassOfWaterSourceInformation, glassOfWaterRenderInformation, glassOfWaterItemWorldInformation, amount);
    }

    /**
     * Creates a tea item at the specified world coordinates.
     *
     * @param worldX The X-coordinate of tea in the world.
     * @param worldY The Y-coordinate of tea in the world.
     * @param scaleFactor The scale factor for resizing tea's sprite.
     * @return An Item object representing the tea.
     */
    public Item createTea(int worldX, int worldY, double scaleFactor, int amount) {
        URL pathToTea = MainApplication.class.getResource("/cvut/cz/cupoftea.png");

        GameSpriteSourceInformation teaSourceInformation = new GameSpriteSourceInformation(pathToTea, 0, 0, 113, 104);
        GameSpriteRenderInformation teaRenderInformation = new GameSpriteRenderInformation(0, 0, (int)Math.ceil(113 * scaleFactor), (int)Math.ceil(104 * scaleFactor), worldX, worldY);

        ItemInformation teaItemWorldInformation = new ItemInformation("tea", null, false, true, true, -30);

        renderManager.getImagesToDraw().put(String.valueOf(pathToTea), new Image(String.valueOf(pathToTea)));

        return new Item(teaSourceInformation, teaRenderInformation, teaItemWorldInformation, amount);
    }
}
