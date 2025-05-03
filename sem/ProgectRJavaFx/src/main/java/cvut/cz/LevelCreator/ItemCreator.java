package cvut.cz.LevelCreator;

import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;
import cvut.cz.MainApplication;
import cvut.cz.RenderManager;
import cvut.cz.items.Item;
import cvut.cz.items.ItemInformation;
import javafx.scene.image.Image;

import java.net.URL;

public class ItemCreator extends LevelCreator {

    public ItemCreator(RenderManager renderManager, MainApplication mainApp) {
        super(renderManager, mainApp);
    }

    public Item createPistol(int worldX, int worldY, double scaleFactor) {
        URL pathToPistol = MainApplication.class.getResource("/cvut/cz/pistol.png");

        GameSpriteSourceInformation pistolSourceInformation = new GameSpriteSourceInformation(pathToPistol, 0, 0, 30, 45);
        GameSpriteRenderInformation pistolRenderInformation = new GameSpriteRenderInformation(0, 0, (int) Math.ceil(30 * scaleFactor), (int) Math.ceil(45 * scaleFactor), worldX, worldY);

        ItemInformation pistolInformation = new ItemInformation("pistol", null, true, false, false, 14);

        renderManager.getImagesToDraw().put(String.valueOf(pathToPistol), new Image(String.valueOf(pathToPistol)));

        return  new Item(pistolSourceInformation, pistolRenderInformation, pistolInformation, 1);
    }

    public Item createTeaBag(int worldX, int worldY, double scaleFactor) {
        URL pathToTeabag = MainApplication.class.getResource("/cvut/cz/Teabag.png");

        GameSpriteSourceInformation teaBagSourceInformation = new GameSpriteSourceInformation(pathToTeabag, 0, 0, 176, 226);
        GameSpriteRenderInformation teaBagRenderInformation = new GameSpriteRenderInformation(0, 0, (int)Math.ceil(176 * scaleFactor), (int)Math.ceil(226 * scaleFactor), worldX, worldY);

        ItemInformation teaBagItemWorldInformation = new ItemInformation("teabag", null, false, false, true, 0);

        renderManager.getImagesToDraw().put(String.valueOf(pathToTeabag), new Image(String.valueOf(pathToTeabag)));

        return new Item(teaBagSourceInformation, teaBagRenderInformation, teaBagItemWorldInformation, 1);
    }

    public Item createShotGunShell(int worldX, int worldY, double scaleFactor) {
        URL pathToShotGunShell = MainApplication.class.getResource("/cvut/cz/ShotGunShell.png");

        GameSpriteSourceInformation shotGunShellSourceInformation = new GameSpriteSourceInformation(pathToShotGunShell, 0, 0, 168, 347);
        GameSpriteRenderInformation shotGunShellRenderInformation = new GameSpriteRenderInformation(0, 0, (int) Math.ceil(168 * scaleFactor), (int) Math.ceil(347 * scaleFactor), worldX, worldY);

        ItemInformation shotGunShellInformation = new ItemInformation("shotgunshell", null, false, false, true, 0);

        renderManager.getImagesToDraw().put(String.valueOf(pathToShotGunShell), new Image(String.valueOf(pathToShotGunShell)));

        return  new Item(shotGunShellSourceInformation, shotGunShellRenderInformation, shotGunShellInformation, 1);
    }

    public Item createShotGun(int worldX, int worldY, double scaleFactor) {
        URL pathToShotgun = MainApplication.class.getResource("/cvut/cz/Shotgun.png");

        GameSpriteSourceInformation shotgunSourceInformation = new GameSpriteSourceInformation(pathToShotgun, 0, 0, 33, 41);
        GameSpriteRenderInformation shotgunRenderInformation = new GameSpriteRenderInformation(0, 0, (int) Math.ceil(33 * scaleFactor), (int) Math.ceil(41 * scaleFactor), worldX, worldY);

        ItemInformation shotgunItemInformation = new ItemInformation("shotgun", null, true, false, false, 7);
        renderManager.getImagesToDraw().put(String.valueOf(pathToShotgun), new Image(String.valueOf(pathToShotgun)));

        return new Item(shotgunSourceInformation, shotgunRenderInformation, shotgunItemInformation, 1);
    }

    public Item creatPistolBullet(int worldX, int worldY, double scaleFactor) {
        URL pathToPistolBullet = MainApplication.class.getResource("/cvut/cz/PistolBullet.png");

        GameSpriteSourceInformation pistolBulletSourceInformation = new GameSpriteSourceInformation(pathToPistolBullet, 0, 0, 64, 166);
        GameSpriteRenderInformation pistolBulletRenderInformation = new GameSpriteRenderInformation(0, 0, (int)Math.ceil(64 * scaleFactor), (int)Math.ceil(166 * scaleFactor), worldX, worldY);
        ItemInformation pistolBulletInformation = new ItemInformation("pistolbullet", null, false, false, true, 0);

        renderManager.getImagesToDraw().put(String.valueOf(pathToPistolBullet), new Image(String.valueOf(pathToPistolBullet)));

        return  new Item(pistolBulletSourceInformation, pistolBulletRenderInformation, pistolBulletInformation, 1);
    }

    public Item createMedKit(int worldX, int worldY, double scaleFactor) {
        URL pathToMedKit = MainApplication.class.getResource("/cvut/cz/medkit.png");
        GameSpriteSourceInformation medKitSourceInformation = new GameSpriteSourceInformation(pathToMedKit, 0, 0, 36, 31);
        GameSpriteRenderInformation medKitRenderInformation = new GameSpriteRenderInformation(0,0, (int) Math.ceil(scaleFactor * 36), (int) Math.ceil(31 * scaleFactor), worldX, worldY);
        ItemInformation medKitInformation = new ItemInformation("medkit", null, false, true, true, -20);
        renderManager.getImagesToDraw().put(String.valueOf(pathToMedKit), new Image(String.valueOf(pathToMedKit)));

        return new Item(medKitSourceInformation, medKitRenderInformation, medKitInformation, 1);
    }

    public Item createChocolateBar(int worldX, int worldY, double scaleFactor) {
        URL pathToChocolateBar = MainApplication.class.getResource("/cvut/cz/chocolateBar.png");

        GameSpriteSourceInformation chocolateBarSourceInformation = new GameSpriteSourceInformation(pathToChocolateBar, 0, 0, 437, 222);
        GameSpriteRenderInformation chocolateBarRenderInformation = new GameSpriteRenderInformation(0, 0, (int) Math.ceil(33 * scaleFactor), (int) Math.ceil(41 * scaleFactor), worldX, worldY);

        ItemInformation chocolateBarInformation = new ItemInformation("chocolatebar", null, false, true, true, -10);
        renderManager.getImagesToDraw().put(String.valueOf(pathToChocolateBar), new Image(String.valueOf(pathToChocolateBar)));

        return  new Item(chocolateBarSourceInformation, chocolateBarRenderInformation, chocolateBarInformation, 1);
    }
}
