package characters;

import cvut.cz.Animation.AnimationStates;
import cvut.cz.Animation.PlayerAnimation;
import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;
import cvut.cz.Model.MainPlayerModel;
import cvut.cz.characters.CharacterInformation;
import cvut.cz.characters.CrossHair;
import cvut.cz.characters.OfficeWorker;
import cvut.cz.characters.States;
import cvut.cz.items.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OfficeWorkerTest {

    OfficeWorker officeWorker;

    @BeforeEach
    public void setUp() {
        CharacterInformation characterInfo = new CharacterInformation(0, States.IDLE, 100, 5);
        GameSpriteRenderInformation renderInfo = new GameSpriteRenderInformation(0, 0, 0, 0, 0, 0);
        GameSpriteSourceInformation sourceInfo = new GameSpriteSourceInformation(null, 0, 0, 0, 0);

        officeWorker = new OfficeWorker(characterInfo, sourceInfo, renderInfo);

        PlayerAnimation playerAnimation = new PlayerAnimation();
        officeWorker.setCharacterAnimation(playerAnimation);

        InventoryInformation inventoryInformation = new InventoryInformation(0, 0, 12,  3, 0, 0);
        GameSpriteSourceInformation gameSpriteSourceInformation = new GameSpriteSourceInformation(null, 0, 0, 0, 0);
        GameSpriteRenderInformation gameSpriteRenderInformation = new GameSpriteRenderInformation(0, 0, 0, 0, 0, 0);
        InventoryCellGeneralInformation inventoryCellGeneralInformation = new InventoryCellGeneralInformation(0, 0, 0, 0, 0, 0, 0,0);
        officeWorker.setInventory(new Inventory(inventoryInformation, inventoryCellGeneralInformation, gameSpriteSourceInformation, gameSpriteRenderInformation));
    }

    @Test
    public void aim_chooseAimDirectionRight(){
        GameSpriteRenderInformation renderInformation = new GameSpriteRenderInformation(10, 0, 0, 0,0, 0);
        CrossHair crossHair = new CrossHair(null, renderInformation, 0,0);

        Map<String, CrossHair> weaponsAndCrossHairs = new HashMap<>();
        weaponsAndCrossHairs.put("pistol", crossHair);
        officeWorker.setWeaponsAndCrossHairs(weaponsAndCrossHairs);

        officeWorker.aim(new Item(null, null, new ItemInformation("pistol", null, false, false, false, 0),0));

        assertEquals(AnimationStates.AimingPistolRight, officeWorker.getCharacterAnimation().currentAnimationState);
    }

    @Test
    public void aim_chooseAimDirectionLeft(){
        GameSpriteRenderInformation renderInformation = new GameSpriteRenderInformation(-10, 0, 0, 0,0, 0);
        CrossHair crossHair = new CrossHair(null, renderInformation, 0,0);

        Map<String, CrossHair> weaponsAndCrossHairs = new HashMap<>();
        weaponsAndCrossHairs.put("pistol", crossHair);
        officeWorker.setWeaponsAndCrossHairs(weaponsAndCrossHairs);

        officeWorker.aim(new Item(null, null, new ItemInformation("pistol", null, false, false, false, 0),0));

        assertEquals(AnimationStates.AimingPistolLeft, officeWorker.getCharacterAnimation().currentAnimationState);
    }

    @Test
    public void aim_chooseAimDirectionUp(){
        GameSpriteRenderInformation renderInformation = new GameSpriteRenderInformation(0, -10, 0, 0,0, 0);
        CrossHair crossHair = new CrossHair(null, renderInformation, 0,0);

        Map<String, CrossHair> weaponsAndCrossHairs = new HashMap<>();
        weaponsAndCrossHairs.put("pistol", crossHair);
        officeWorker.setWeaponsAndCrossHairs(weaponsAndCrossHairs);

        officeWorker.aim(new Item(null, null, new ItemInformation("pistol", null, false, false, false, 0),0));

        assertEquals(AnimationStates.AimingPistolUp, officeWorker.getCharacterAnimation().currentAnimationState);
    }

    @Test
    public void aim_chooseAimDirectionDown(){
        GameSpriteRenderInformation renderInformation = new GameSpriteRenderInformation(0, 10, 0, 0,0, 0);
        CrossHair crossHair = new CrossHair(null, renderInformation, 0,0);

        Map<String, CrossHair> weaponsAndCrossHairs = new HashMap<>();
        weaponsAndCrossHairs.put("pistol", crossHair);
        officeWorker.setWeaponsAndCrossHairs(weaponsAndCrossHairs);

        officeWorker.aim(new Item(null, null, new ItemInformation("pistol", null, false, false, false, 0),0));

        assertEquals(AnimationStates.AimingPistolDown, officeWorker.getCharacterAnimation().currentAnimationState);
    }

    @Test
    public void shoot_chooseShootDirectionRight(){
        GameSpriteRenderInformation renderInformation = new GameSpriteRenderInformation(10, 0, 0, 0,0, 0);
        CrossHair crossHair = new CrossHair(null, renderInformation, 0,0);

        Map<String, CrossHair> weaponsAndCrossHairs = new HashMap<>();
        weaponsAndCrossHairs.put("pistol", crossHair);
        officeWorker.setWeaponsAndCrossHairs(weaponsAndCrossHairs);
        officeWorker.isAiming = true;

        officeWorker.shoot(new Item(null, null, new ItemInformation("pistol", null, false, false, false, 0),0));

        assertEquals(AnimationStates.ShootingPistolRight, officeWorker.getCharacterAnimation().currentAnimationState);
    }

    @Test
    public void shoot_chooseShootDirectionLeft(){
        GameSpriteRenderInformation renderInformation = new GameSpriteRenderInformation(-10, 0, 0, 0,0, 0);
        CrossHair crossHair = new CrossHair(null, renderInformation, 0,0);

        Map<String, CrossHair> weaponsAndCrossHairs = new HashMap<>();
        weaponsAndCrossHairs.put("pistol", crossHair);
        officeWorker.setWeaponsAndCrossHairs(weaponsAndCrossHairs);
        officeWorker.isAiming = true;

        officeWorker.shoot(new Item(null, null, new ItemInformation("pistol", null, false, false, false, 0),0));

        assertEquals(AnimationStates.ShootingPistolLeft, officeWorker.getCharacterAnimation().currentAnimationState);
    }

    @Test
    public void shoot_chooseShootDirectionUp(){
        GameSpriteRenderInformation renderInformation = new GameSpriteRenderInformation(0, -10, 0, 0,0, 0);
        CrossHair crossHair = new CrossHair(null, renderInformation, 0,0);

        Map<String, CrossHair> weaponsAndCrossHairs = new HashMap<>();
        weaponsAndCrossHairs.put("pistol", crossHair);
        officeWorker.setWeaponsAndCrossHairs(weaponsAndCrossHairs);
        officeWorker.isAiming = true;

        officeWorker.shoot(new Item(null, null, new ItemInformation("pistol", null, false, false, false, 0),0));

        assertEquals(AnimationStates.ShootingPistolUp, officeWorker.getCharacterAnimation().currentAnimationState);
    }

    @Test
    public void shoot_chooseShootDirectionDown(){
        GameSpriteRenderInformation renderInformation = new GameSpriteRenderInformation(0, 10, 0, 0,0, 0);
        CrossHair crossHair = new CrossHair(null, renderInformation, 0,0);

        Map<String, CrossHair> weaponsAndCrossHairs = new HashMap<>();
        weaponsAndCrossHairs.put("pistol", crossHair);
        officeWorker.setWeaponsAndCrossHairs(weaponsAndCrossHairs);

        officeWorker.isAiming = true;
        officeWorker.shoot(new Item(null, null, new ItemInformation("pistol", null, false, false, false, 0),0));

        assertEquals(AnimationStates.ShootingPistolDown, officeWorker.getCharacterAnimation().currentAnimationState);
    }

    @Test
    public void useItem_UseOfTea(){
        officeWorker.getCharacterInformation().setCurrentHealth(0);
        officeWorker.useItem(new Item(null, null, new ItemInformation("tea", null, false, false, false, -10),0));

        assertEquals(10, officeWorker.getCharacterInformation().getCurrentHealth());
    }

    @Test
    public void useItem_UseOfWater(){
        officeWorker.getCharacterInformation().setCurrentHealth(0);
        officeWorker.useItem(new Item(null, null, new ItemInformation("water", null, false, false, false, -10),0));

        assertEquals(10, officeWorker.getCharacterInformation().getCurrentHealth());
    }

    @Test
    public void useItem_UseOfMedKit(){
        officeWorker.getCharacterInformation().setCurrentHealth(0);
        officeWorker.useItem(new Item(null, null, new ItemInformation("medkit", null, false, false, false, -10),0));

        assertEquals(10, officeWorker.getCharacterInformation().getCurrentHealth());
    }

    @Test
    public void useItem_UseOfChocolateBar(){
        officeWorker.getCharacterInformation().setCurrentHealth(0);
        officeWorker.useItem(new Item(null, null, new ItemInformation("chocolatebar", null, false, false, false, -10),0));

        assertEquals(10, officeWorker.getCharacterInformation().getCurrentHealth());
    }
}
