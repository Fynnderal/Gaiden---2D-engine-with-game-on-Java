package cvut.cz.LevelCreator;

import cvut.cz.Animation.AnimationInformation;
import cvut.cz.Animation.CharacterAnimation;
import cvut.cz.Animation.EnemyAnimation;
import cvut.cz.Animation.PlayerAnimation;
import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;
import cvut.cz.MainApplication;
import cvut.cz.Model.MainPlayerModel;
import cvut.cz.RenderManager;
import cvut.cz.characters.*;
import javafx.scene.image.Image;

import java.net.URL;
import java.util.HashMap;

public class CharacterCreator extends LevelCreator {

    public CharacterCreator(RenderManager renderManager, MainApplication mainApplication) {
        super(renderManager, mainApplication);
    }

    public AnimationInformation[] createAnimation(int sourceY, int sourceWidth, int sourceHeight, int numberOfSourceSprites, int numberOfAnimationSprites, double scaleFactor) {
        AnimationInformation[] animation = new AnimationInformation[numberOfAnimationSprites];

        for (int i = 0; i < numberOfSourceSprites; i++) {
            for (int j = 0; j < (numberOfAnimationSprites / numberOfSourceSprites); j++) {
                animation[(numberOfAnimationSprites / numberOfSourceSprites) * i + j] = new AnimationInformation(16 * i * 2, sourceY, sourceWidth, sourceHeight, (int) Math.ceil(sourceWidth * scaleFactor), (int) Math.ceil(sourceHeight * scaleFactor));
            }
        }

        return animation;
    }

    private void setEnemyAnimation(Enemy enemy, double scaleFactor){
        EnemyAnimation enemyAnimation = new EnemyAnimation();
        enemy.setCharacterAnimation(enemyAnimation);

        enemyAnimation.idleUpAnimation = createAnimation(32, 16, 27, 5, 50, scaleFactor);
        enemyAnimation.idleDownAnimation = createAnimation(0, 16, 27, 5, 50, scaleFactor);
        enemyAnimation.idleRightAnimation = createAnimation(96, 15, 27, 5, 50, scaleFactor);
        enemyAnimation.idleLeftAnimation = createAnimation(64, 15, 27, 5, 50, scaleFactor);

        enemyAnimation.moveUpAnimation = createAnimation(160, 13, 27, 10, 40, scaleFactor);
        enemyAnimation.moveDownAnimation = createAnimation(128, 13, 27, 10, 40, scaleFactor);
        enemyAnimation.moveRightAnimation = createAnimation(192, 16, 27, 10, 40, scaleFactor);
        enemyAnimation.moveLeftAnimation = createAnimation(224, 16, 27, 10, 40, scaleFactor);

        enemyAnimation.attackUpAnimation = createAnimation(288, 14, 28, 8, 128, scaleFactor);
        enemyAnimation.attackDownAnimation = createAnimation(256, 14, 28, 8, 128, scaleFactor);
        enemyAnimation.attackLeftAnimation = createAnimation(352, 18, 28, 8, 128, scaleFactor);
        enemyAnimation.attackRightAnimation = createAnimation(320, 18, 28, 8, 128, scaleFactor);

        enemyAnimation.dyingUpAnimation = createAnimation(416, 16, 27, 7, 70, scaleFactor);
        enemyAnimation.dyingRightAnimation = createAnimation(448, 19, 27, 7, 70, scaleFactor);
        enemyAnimation.dyingLeftAnimation = createAnimation(480, 19, 27, 7, 70, scaleFactor);
        enemyAnimation.dyingDownAnimation = createAnimation(384, 16, 27, 7, 70, scaleFactor);

        enemyAnimation.takingDamageDownAnimation = createAnimation(512, 23, 26, 1, 16, scaleFactor);
        enemyAnimation.takingDamageLeftAnimation = createAnimation(608, 23, 26, 1, 16, scaleFactor);
        enemyAnimation.takingDamageRightAnimation = createAnimation(576, 23, 26, 1, 16, scaleFactor);
        enemyAnimation.takingDamageUpAnimation = createAnimation(544, 23, 26, 1, 16, scaleFactor);

    }

    private void setPlayerAnimation(PlayableCharacter mainCharacter, double scaleFactor){
        PlayerAnimation mainPlayerAnimation = new PlayerAnimation();
        mainPlayerAnimation.moveDownAnimation = createAnimation(0, 16, 32, 4, 32, scaleFactor);
        mainPlayerAnimation.moveUpAnimation = createAnimation(32, 16, 32, 4, 32, scaleFactor);
        mainPlayerAnimation.moveLeftAnimation = createAnimation(96, 16, 32, 4, 32, scaleFactor);
        mainPlayerAnimation.moveRightAnimation = createAnimation(64, 16, 32, 4, 32, scaleFactor);

        mainPlayerAnimation.idleUpAnimation = createAnimation(288, 16,32,2, 64, scaleFactor);
        mainPlayerAnimation.idleDownAnimation = createAnimation(256, 16,32,2, 64, scaleFactor);
        mainPlayerAnimation.idleRightAnimation = createAnimation(320, 16,32,2, 64, scaleFactor);
        mainPlayerAnimation.idleLeftAnimation = createAnimation(352, 16,32,2, 64, scaleFactor);

        mainPlayerAnimation.shootingShotGunUpAnimation = createAnimation(416, 16,32,4, 32, scaleFactor);
        mainPlayerAnimation.shootingShotGunDownAnimation = createAnimation(384, 16,32,4, 32, scaleFactor);
        mainPlayerAnimation.shootingShotGunRightAnimation = createAnimation(576, 29,32,4, 32, scaleFactor);
        mainPlayerAnimation.shootingShotGunLeftAnimation = createAnimation(608, 29,31,4, 32, scaleFactor);

        mainPlayerAnimation.shootingPistolRightAnimation = createAnimation(448, 20,32,4, 32, scaleFactor);
        mainPlayerAnimation.shootingPistolLeftAnimation = createAnimation(480, 20,32,4, 32, scaleFactor);
        mainPlayerAnimation.shootingPistolUpAnimation = createAnimation(416, 16,32,4, 32, scaleFactor);
        mainPlayerAnimation.shootingPistolDownAnimation = createAnimation(384, 16,32,4, 32, scaleFactor);

        mainPlayerAnimation.aimingPistolDownAnimation = createAnimation(384, 16, 32, 1, 1, scaleFactor);
        mainPlayerAnimation.aimingPistolUpAnimation = createAnimation(416, 16, 32, 1, 1, scaleFactor);
        mainPlayerAnimation.aimingPistolRightAnimation = createAnimation(448, 20, 32, 1, 1, scaleFactor);
        mainPlayerAnimation.aimingPistolLeftAnimation = createAnimation(480, 20, 32, 1, 1, scaleFactor);

        mainPlayerAnimation.aimingShotGunRightAnimation = createAnimation(576, 29, 31, 1, 1, scaleFactor);
        mainPlayerAnimation.aimingShotGunLeftAnimation = createAnimation(608, 29, 31, 1, 1, scaleFactor);
        mainPlayerAnimation.aimingShotGunDownAnimation = createAnimation(384, 16, 32, 1, 1, scaleFactor);
        mainPlayerAnimation.aimingShotGunUpAnimation = createAnimation(416, 16, 32, 1, 1, scaleFactor);

        mainPlayerAnimation.dyingUpAnimation = createAnimation(160, 21, 26, 4, 64, scaleFactor);
        mainPlayerAnimation.dyingRightAnimation = createAnimation(192, 23, 26, 4, 64, scaleFactor);
        mainPlayerAnimation.dyingLeftAnimation = createAnimation(224, 23, 26, 4, 64, scaleFactor);
        mainPlayerAnimation.dyingDownAnimation = createAnimation(128, 21, 26, 4, 64, scaleFactor);

        mainPlayerAnimation.takingDamageLeftAnimation = createAnimation(736, 23, 26, 1, 16, scaleFactor);
        mainPlayerAnimation.takingDamageRightAnimation = createAnimation(704, 23, 26, 1, 16, scaleFactor);
        mainPlayerAnimation.takingDamageUpAnimation = createAnimation(672, 23, 26, 1, 16, scaleFactor);
        mainPlayerAnimation.takingDamageDownAnimation = createAnimation(640, 23, 26, 1, 16, scaleFactor);

        mainCharacter.setCharacterAnimation(mainPlayerAnimation);
    }

    public Zombie createZombie(ActionAreaInformation actionAreaInformation, int worldX, int worldY, double scaleFactor) {
        URL pathToZombie = MainApplication.class.getResource("/cvut/cz/Zombie.png");

        CharacterInformation zombieInformation = new CharacterInformation(20, States.IDLE, 60, 3);
        GameSpriteSourceInformation zombieSourceInformation = new GameSpriteSourceInformation(pathToZombie, 0, 0, 0, 0);
        GameSpriteRenderInformation zombieRenderInformation = new GameSpriteRenderInformation(0, 0, 0, 0, worldX, worldY);

        Zombie zombie = new Zombie(zombieInformation, zombieSourceInformation, zombieRenderInformation, actionAreaInformation, MainPlayerModel.getMainPlayerModel().getMainPlayer());

        setEnemyAnimation(zombie, scaleFactor);

        return zombie;
    }

    public WatchMan createWatchMan(ActionAreaInformation actionAreaInformation, int worldX, int worldY, double scaleFactor) {
        URL pathToWatchman = MainApplication.class.getResource("/cvut/cz/watchman.png");

        CharacterInformation watchmanInformation = new CharacterInformation(65, States.IDLE, 85, 7);
        GameSpriteSourceInformation watchmanSourceInformation = new GameSpriteSourceInformation(pathToWatchman, 0, 0, 0, 0);
        GameSpriteRenderInformation watchmanRenderInformation = new GameSpriteRenderInformation(0, 0, 0, 0, worldX, worldY);

        WatchMan watchman = new WatchMan(watchmanInformation, watchmanSourceInformation, watchmanRenderInformation, actionAreaInformation, MainPlayerModel.getMainPlayerModel().getMainPlayer());

        setEnemyAnimation(watchman, scaleFactor);

        EnemyAnimation watchmanAnimation = (EnemyAnimation) watchman.getCharacterAnimation();
        watchmanAnimation.chasingDownAnimation = createAnimation(640, 13 ,27, 10, 40, scaleFactor);
        watchmanAnimation.chasingLeftAnimation = createAnimation(736, 16 ,27, 10, 40, scaleFactor);
        watchmanAnimation.chasingRightAnimation = createAnimation(704, 16 ,27, 10, 40, scaleFactor);
        watchmanAnimation.chasingUpAnimation = createAnimation(672, 13 ,27, 10, 40, scaleFactor);

        return watchman;
    }

    public OfficeWorker createOfficeWorker(int worldX, int worldY, double scaleFactor) {
        URL pathToOfficeWorkerImage = MainApplication.class.getResource("/cvut/cz/MainCharacter.png");

        CharacterInformation officeWorkerCharacterInformation = new CharacterInformation(0, States.IDLE, 100, 5);
        GameSpriteSourceInformation officeWorkerSourceInformation = new GameSpriteSourceInformation(pathToOfficeWorkerImage, 0, 0, 0, 0);
        GameSpriteRenderInformation officeWorkerRenderInformation = new GameSpriteRenderInformation(691, 306, 0, 0, worldX, worldY);

        OfficeWorker officeWorker = new OfficeWorker(officeWorkerCharacterInformation, officeWorkerSourceInformation, officeWorkerRenderInformation);

        renderManager.getImagesToDraw().put(String.valueOf(pathToOfficeWorkerImage), new Image(String.valueOf(pathToOfficeWorkerImage)));

        setPlayerAnimation(officeWorker, scaleFactor);

        return officeWorker;
    }

    public Merchant createMerchant(ActionAreaInformation actionAreaInformation, int worldX, int worldY, double scaleFactor) {
        URL pathToMerchant = MainApplication.class.getResource("/cvut/cz/Merchant.png");

        HashMap<String, Integer> itemPrices = new HashMap<>();
        itemPrices.put("Pistol bullet", 1);
        itemPrices.put("Medkit", 10);
        itemPrices.put("Chocolate bar", 7);

        GameSpriteSourceInformation merchantSourceInformation = new GameSpriteSourceInformation(pathToMerchant, 0, 0, 0, 0);
        GameSpriteRenderInformation merchantRenderInformation = new GameSpriteRenderInformation(0, 0, 0, 0, worldX, worldY);

        Merchant merchant = new Merchant(itemPrices, actionAreaInformation, merchantSourceInformation, merchantRenderInformation, MainPlayerModel.getMainPlayerModel().getMainPlayer());

        CharacterAnimation merchantAnimation = new CharacterAnimation();

        merchantAnimation.idleDownAnimation = createAnimation(0, 32, 48, 4, 48, scaleFactor);
        merchant.setCharacterAnimation(merchantAnimation);

        return merchant;
    }
}
