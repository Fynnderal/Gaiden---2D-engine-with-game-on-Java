package cvut.cz;

import cvut.cz.LevelCreator.CharacterCreator;
import cvut.cz.LevelCreator.ItemCreator;
import cvut.cz.LevelCreator.LevelCreator;
import cvut.cz.Map.MapSpot;
import cvut.cz.Map.Ventilation;
import cvut.cz.Model.MainPlayerModel;
import cvut.cz.Model.MapModel;
import cvut.cz.characters.ActionAreaInformation;
import cvut.cz.characters.GunSight;
import cvut.cz.characters.NPC;

import cvut.cz.items.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirstLevel{
    private final MainApplication mainApp;

    private final ItemCreator itemCreator;
    private final CharacterCreator characterCreator;
    private final LevelCreator levelCreator;

    public FirstLevel(MainApplication mainApplication, RenderManager renderManager){
        this.mainApp = mainApplication;
        itemCreator= new ItemCreator(renderManager, mainApp);
        characterCreator = new CharacterCreator(renderManager, mainApp);
        levelCreator = new LevelCreator(renderManager, mainApp);
    }


    private void createGUI(){
        Map<String, GunSight> weaponsAndGunSights = new HashMap<>();
        weaponsAndGunSights.put("pistol", levelCreator.createPistolGunSight(4));
        weaponsAndGunSights.put("shotgun", levelCreator.createShotGunGunSight(4));

        MainPlayerModel.getMainPlayerModel().getMainPlayer().setWeaponsAndGunSights(weaponsAndGunSights);

        configureInventory();

        mainApp.getGuiCreator().createHealthPanel();
        mainApp.getMainContainer().getChildren().add(mainApp.getGuiCreator().getHealthPanel());
    }

    private void configureInventory(){
        HashMap<String, Item> possibleItems = new HashMap<>();
        possibleItems.put("shotgun", itemCreator.createShotGun(0, 0, 5));
        possibleItems.put("teabag", itemCreator.createTeaBag(0,0, 5));
        possibleItems.put("pistol", itemCreator.createPistol(0, 0, 5));
        possibleItems.put("pistolbullet", itemCreator.creatPistolBullet(0, 0, 5));
        possibleItems.put("medkit", itemCreator.createMedKit(0, 0, 5));
        possibleItems.put("chocolatebar", itemCreator.createChocolateBar(0, 0, 5));
        MainPlayerModel.getMainPlayerModel().getInventory().setPossibleItems(possibleItems);

    }

    private void createItemsOnMap(){
        List<Item> itemsOnMap = new ArrayList<>();
        itemsOnMap.add(itemCreator.createPistol(100, 100, 0.9));
        itemsOnMap.add(itemCreator.createTeaBag(30, 30, 0.15));
        itemsOnMap.add(itemCreator.createShotGunShell(-10, -10, 0.08));
        MapModel.getMapModel().setItemsInWorld(itemsOnMap);
    }

    private void createMapSpots(){
        Ventilation ventilation = levelCreator.createVentilation(10, 10, 1);
        Ventilation ventilation2 = levelCreator.createVentilation(150, 150, 1);
        ventilation2.setSpotToTeleport(ventilation);
        ventilation.setSpotToTeleport(ventilation2);
        List<MapSpot> mapSpots = new ArrayList<>();
        mapSpots.add(ventilation2);
        mapSpots.add(ventilation);
        MapModel.getMapModel().setMapSpots(mapSpots);
    }

    private void createCharactersOnMap(){
        ActionAreaInformation merchantActionArea = new ActionAreaInformation(900, 900, 1500, 1500, 1500);
        ActionAreaInformation zombieActionArea = new ActionAreaInformation(-200, -200, 500, 500, 500);
        ActionAreaInformation watchmanActionArea = new ActionAreaInformation(400, 400, 500, 500, 200);

        List<NPC> charactersOnMap = new ArrayList<>();
        charactersOnMap.add(characterCreator.createMerchant(merchantActionArea, 900, 900, 5));
        charactersOnMap.add(characterCreator.createZombie(zombieActionArea,-200, -200, 5));
        charactersOnMap.add(characterCreator.createWatchMan(watchmanActionArea, 400, 400, 5));
        MapModel.getMapModel().setCharactersInWorld(charactersOnMap);
    }


    public void createLevel(){
        levelCreator.createInventory();

        levelCreator.createMap(1);

        MainPlayerModel.getMainPlayerModel().setMainPlayer(characterCreator.createOfficeWorker(0,0, 5));
        MainPlayerModel.getMainPlayerModel().getInventory().setCharacter(MainPlayerModel.getMainPlayerModel().getMainPlayer());
        MainPlayerModel.getMainPlayerModel().getMainPlayer().setInventory(MainPlayerModel.getMainPlayerModel().getInventory());


        createItemsOnMap();
        createGUI();




        List<Item> items = new ArrayList<>();
        items.add(itemCreator.createShotGun(0, 0, 5));
        items.add(itemCreator.createTeaBag(0,0, 5));
        MainPlayerModel.getMainPlayerModel().getInventory().setItems(items);

        createCharactersOnMap();
        createMapSpots();


        MapModel.getMapModel().getDrawableObjects().remove(MainPlayerModel.getMainPlayerModel().getMainPlayer());
        MapModel.getMapModel().getDrawableObjects().add(MainPlayerModel.getMainPlayerModel().getMainPlayer());

        //test
    }

}
