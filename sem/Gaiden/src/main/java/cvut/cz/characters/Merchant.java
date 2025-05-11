package cvut.cz.characters;


import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;
import cvut.cz.items.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represents a merchant NPC in the game. The merchant interacts with the player,
 * sells items, and provides dialogue phrases during interactions.
 */
public class Merchant extends NPC{
    //Contains pairs where key is an item that merchant can sell and value is price of the item.
    private final Map<String, Integer> itemsPrices;

    //Array of phrases that merchant can say to the player when dialogue starts
    private final String[] possibleSellingStartingPhrases = {"Got somethin' that might interest ya'!", "What're ya buyin'?", "Got some rare things on sale, stranger!", "Got a selection of good things on sale, stranger!",
            "Let's do some business then, eh!",  "I've got some new items in stock, heh heh. Come, take a look!", "I reserved some items, just for you! My other customer doesn't know about these, heh heh heh.", };

    //Array of phrases that merchant can say to the player if he bought something
    private final String[] possibleSellingPhrases = {"A wise choice, mate!", "Ah, an awesome choice, stranger!", "I see you have an eye for things.", "Thank you!", "Pleasure doing business with you."	};

    //Array of phrases that merchant can say to the player when he has not enough money to buy something
    private final String[] possibleNotEnoughMoneyPhrases = {"Not enough cash, stranger!", "This ain't a charity!", "That won't cover it, I'm afraid.", "Without the necessary funds, I'm afraid we don't have a deal!", };

    //Array of phrases that merchant can say to the player if he bought something but didn't leave
    private final String[] possiblePhrasesAfterBuying = { "Is that all, stranger?", "Something else, mate?", "Take a look, maybe you'll find something else"};

    //True if player bought something
    private boolean playerBoughtSomething;


    /**
     * Initializes  a merchant.
     *
     * @param itemsPrices A map of items and their prices that the merchant can sell.
     * @param actionAreaInformation Information about the action area of the merchant.
     * @param gameSpriteSourceInformation Information about the source of the merchant's sprite.
     * @param gameSpriteRenderInformation Information about rendering the merchant's sprite.
     * @param mainCharacter The main character.
     */
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

    /**
     * Manages interaction with the player. Selects a suitable phrase and handles item selling.
     *
     * @return An array of strings representing the phrase the merchant is saying at the moment.
     *         These should be displayed on the screen.
     */
    public String[] interact(){
        if (interactingWithPlayer) {
            //if the merchant is not waiting for answers, it means that at the moment the player doesn't see selling menu
            if(!waitingForAnswer) {
                currentPlayersAnswer = 0;
                waitingForAnswer = true;
                return showItems();
            }

            //last option the player can choose is always quitting.
            if (currentPlayersAnswer == itemsPrices.size()){
                interactingWithPlayer = false;
                waitingForAnswer = false;
                playerBoughtSomething = false;
                return null;
            }

            //Selling item to the player
            waitingForAnswer = false;
            if (sellItem(itemsPrices.keySet().toArray(new String[0])[currentPlayersAnswer]))
                return new String[] {possibleSellingPhrases[new Random().nextInt(possibleSellingPhrases.length)]};
            else
                return new String[] {possibleNotEnoughMoneyPhrases[new Random().nextInt(possibleNotEnoughMoneyPhrases.length)]};
        }
        // if the merchant was not interacting just start from the beginning of conversation
        else{
            interactingWithPlayer = true;
            currentPlayersAnswer = 0;
            waitingForAnswer = true;
            return showItems();
        }
    }

    /**
     * Displays the items available for sale to the player.
     *
     * @return An array of strings representing the merchant's dialogue and the list of items with their prices.
     */
    public String[] showItems() {
        List<String> result = new ArrayList<>();
        if (!playerBoughtSomething)
            result.add(possibleSellingStartingPhrases[new Random().nextInt(possibleSellingStartingPhrases.length)] + "\n");
        else
            result.add(possiblePhrasesAfterBuying[new Random().nextInt(possiblePhrasesAfterBuying.length)] + "\n");

        // Adds rows with items names and their prices
        for (String item : itemsPrices.keySet()){
            int price = itemsPrices.get(item);
            String line = "Buy " + item + ":    " + itemsPrices.get(item);
            if (price > 1)
                line += " teabags";
            else
                line += " teabag";

            result.add(line);
        }

        // Option to quit the interaction.
        if (!playerBoughtSomething)
            result.add("Maybe next time...");
        else
            result.add("That's all.");

        return result.toArray(new String[0]);
    }

    /**
     * Handles the process of selling an item to the player.
     *
     * @param item The name of the item to be sold.
     * @return True if the player successfully bought the item, false otherwise.
     */
    private boolean sellItem(String item){
        List<InventoryCell> characterInventoryCells = mainCharacter.getInventory().getCells();

        if (characterInventoryCells == null)
            return false;

        for (InventoryCell cell: characterInventoryCells){
            if (cell.getItem() == null)
                continue;

            //Checks if player has teabags in inventory and if their amount is enough to buy the item
            if (cell.getItem().getItemInformation().name().equals("teabag") && cell.getItemAmount() >= itemsPrices.get(item)){
                //Get the item object by name from possible items, that inventory can contain
                Item soldItem = mainCharacter.getInventory().getPossibleItems().get(item.toLowerCase().replaceAll(" ", "")).clone();

                //Merchant sell only one item in a time
                soldItem.setAmount(1);

                mainCharacter.getInventory().addItemToInventory(soldItem);

                //taking teabags from the player
                cell.setItemAmount(cell.getItemAmount() - itemsPrices.get(item));
                playerBoughtSomething = true;
                return true;
            }
        }

        return false;
    }

    /**
     * Updates the player's selected answer during interaction.
     *
     * @param direction The direction to change the selection. If it is LEFT or UP, selects the previous phrase;
     *                  otherwise, selects the next phrase.
     */
    public void changePlayersAnswer(Directions direction){
        if (direction == Directions.UP || direction == Directions.LEFT)
            currentPlayersAnswer--;
        else
            currentPlayersAnswer++;

        currentPlayersAnswer  %= ( itemsPrices.size() + 1);

        //remainder must not be negative
        if (currentPlayersAnswer < 0)
            currentPlayersAnswer += itemsPrices.size() + 1;
    }

    /**
     * Gets the player's current selected answer.
     *
     * @return The index of the player's current answer, incremented by 1.
     */
    @Override
    public int getCurrentPlayersAnswer() {
        return (currentPlayersAnswer + 1);
    }

    /**
     * Prevents the merchant from taking damage.
     *
     * @param damage The amount of damage.
     */
    @Override
    public void takeDamage(int damage) { }
}
