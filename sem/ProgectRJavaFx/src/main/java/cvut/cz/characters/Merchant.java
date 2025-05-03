package cvut.cz.characters;


import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;
import cvut.cz.items.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Merchant extends NPC{
    private final Map<String, Integer> itemsPrices;

    private final String[] possibleSellingStartingPhrases = {"Got somethin' that might interest ya'!", "What're ya buyin'?", "Got some rare things on sale, stranger!", "Got a selection of good things on sale, stranger!",
            "Let's do some business then, eh!",  "I've got some new items in stock, heh heh. Come, take a look!", "I reserved some items, just for you! My other customer doesn't know about these, heh heh heh.", };
    private final String[] possibleSellingPhrases = {"A wise choice, mate!", "Ah, an awesome choice, stranger!", "I see you have an eye for things.", "Thank you!", "Pleasure doing business with you."	};
    private final String[] possibleNotEnoughMoneyPhrases = {"Not enough cash, stranger!", "This ain't a charity!", "That won't cover it, I'm afraid.", "Without the necessary funds, I'm afraid we don't have a deal!", };
    private final String[] possiblePhrasesAfterBuying = { "Is that all, stranger?", "Something else, mate?", "Take a look, maybe you'll find something else"};

    private boolean playerBoughtSomething;

    public Merchant( Map<String, Integer> itemsPrices,
                    ActionAreaInformation actionAreaInformation,
                    GameSpriteSourceInformation gameSpriteSourceInformation,
                     GameSpriteRenderInformation gameSpriteRenderInformation,
                    PlayableCharacter mainCharacter) {

        super(new CharacterInformation(0, States.IDLE, 100 ,0), gameSpriteSourceInformation, gameSpriteRenderInformation, actionAreaInformation, mainCharacter);
        this.itemsPrices = itemsPrices;
        this.currentPlayersAnswer = 0;
        this.playerBoughtSomething = false;
    }

    
    public String[] interact(){
        if (interactingWithPlayer) {
            if(!waitingForAnswer) {
                currentPlayersAnswer = 0;
                waitingForAnswer = true;
                return showItems();
            }

            if (currentPlayersAnswer == itemsPrices.size()){
                interactingWithPlayer = false;
                waitingForAnswer = false;
                playerBoughtSomething = false;
                return null;
            }


            waitingForAnswer = false;
            if (sellItem(itemsPrices.keySet().toArray(new String[0])[currentPlayersAnswer]))
                return new String[] {possibleSellingPhrases[new Random().nextInt(possibleSellingPhrases.length)]};
            else
                return new String[] {possibleNotEnoughMoneyPhrases[new Random().nextInt(possibleNotEnoughMoneyPhrases.length)]};
        }
        else{
            interactingWithPlayer = true;
            currentPlayersAnswer = 0;
            waitingForAnswer = true;
            return showItems();
        }
    }

    public String[] showItems() {
        List<String> result = new ArrayList<>();
        if (!playerBoughtSomething)
            result.add(possibleSellingStartingPhrases[new Random().nextInt(possibleSellingStartingPhrases.length)] + "\n");
        else
            result.add(possiblePhrasesAfterBuying[new Random().nextInt(possiblePhrasesAfterBuying.length)] + "\n");

        for (String item : itemsPrices.keySet()){
            int price = itemsPrices.get(item);
            String line = "Buy " + item + ":    " + itemsPrices.get(item);
            if (price > 1)
                line += " teabags";
            else
                line += " teabag";

            result.add(line);
        }

        if (!playerBoughtSomething)
            result.add("Maybe next time...");
        else
            result.add("That's all.");

        return result.toArray(new String[0]);
    }

    private boolean sellItem(String item){
        List<InventoryCell> characterInventoryCells = mainCharacter.getInventory().getCells();
        if (characterInventoryCells == null)
            return false;

        for (InventoryCell cell: characterInventoryCells){
            if (cell.getItem() == null)
                continue;

            if (cell.getItem().getItemInformation().name().equals("teabag") && cell.getItemAmount() >= itemsPrices.get(item)){
                Item soldItem = mainCharacter.getInventory().getPossibleItems().get(item.toLowerCase().replaceAll(" ", "")).clone();
                soldItem.setAmount(1);
                mainCharacter.getInventory().addItemToInventory(soldItem);
                cell.setItemAmount(cell.getItemAmount() - itemsPrices.get(item));
                playerBoughtSomething = true;
                return true;
            }
        }
        return false;
    }

    public void changePlayersAnswer(Directions direction){
        if (direction == Directions.UP)
            currentPlayersAnswer--;
        else
            currentPlayersAnswer++;

        currentPlayersAnswer  %= ( itemsPrices.size() + 1);
        if (currentPlayersAnswer < 0)
            currentPlayersAnswer += itemsPrices.size() + 1;
    }

    @Override
    public int getCurrentPlayersAnswer() {
        return (currentPlayersAnswer + 1);
    }

    @Override
    public void takeDamage(int damage) { }

    @Override
    protected void attack() {}
}
