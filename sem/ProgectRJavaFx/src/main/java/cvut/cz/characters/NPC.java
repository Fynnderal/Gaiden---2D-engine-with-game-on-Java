package cvut.cz.characters;

import cvut.cz.GameSprite.GameSpriteRenderInformation;
import cvut.cz.GameSprite.GameSpriteSourceInformation;

public abstract class NPC extends GameCharacter{
    protected ActionAreaInformation actionAreaInformation;

    protected PlayableCharacter mainCharacter;

    protected boolean interactingWithPlayer;
    protected boolean isInteractable;
    protected boolean waitingForAnswer;

    protected int currentPlayersAnswer;

    public NPC(CharacterInformation characterInformation, GameSpriteSourceInformation gameSpriteSourceInformation,
                 GameSpriteRenderInformation gameSpriteRenderInformation, ActionAreaInformation actionAreaInformation, PlayableCharacter mainCharacter) {

        super(characterInformation, gameSpriteSourceInformation, gameSpriteRenderInformation);
        this.actionAreaInformation = actionAreaInformation;
        this.isInteractable = true;
        this.interactingWithPlayer = false;
        this.waitingForAnswer = false;
        this.mainCharacter = mainCharacter;
    }

    protected void spotPlayer() {
        if (isBlocked)
            return;

        int mainCharacterX = mainCharacter.getGameSpriteRenderInformation().getWorldCoordinateX();
        int mainCharacterY = mainCharacter.getGameSpriteRenderInformation().getWorldCoordinateY();

        if (isPlayerInActionArea()){
            int distance = calculateLengthOfVector(this.gameSpriteRenderInformation.getWorldCoordinateX(), this.gameSpriteRenderInformation.getWorldCoordinateY(),
                    mainCharacterX, mainCharacterY);

            if (distance <= actionAreaInformation.spottingRadius())
                this.characterInformation.setCurrentState(States.CHASING);
        }else{
            this.characterInformation.setCurrentState(States.IDLE);
        }
    }

    public boolean isPlayerInActionArea() {
        return (
            mainCharacter.getGameSpriteRenderInformation().getWorldCoordinateX() < actionAreaInformation.actionAreaX() + actionAreaInformation.actionAreaWidth() &&
            mainCharacter.getGameSpriteRenderInformation().getWorldCoordinateX() + mainCharacter.getGameSpriteRenderInformation().getTargetWidth() >= actionAreaInformation.actionAreaX() &&
            mainCharacter.getGameSpriteRenderInformation().getWorldCoordinateY() < actionAreaInformation.actionAreaY() + actionAreaInformation.actionAreaHeight() &&
            mainCharacter.getGameSpriteRenderInformation().getWorldCoordinateY() + mainCharacter.getGameSpriteRenderInformation().getTargetHeight() >= actionAreaInformation.actionAreaY()
        );
    }

    @Override
    protected void attack() {
        System.out.println("I am peaceful");
    }

    @Override
    protected void move(Directions direction, int speed) {
        System.out.println("I can't move");
    }

    public int getCurrentPlayersAnswer() { return currentPlayersAnswer; }
    public boolean isWaitingForAnswer() { return waitingForAnswer; }

    public abstract String[] interact();
    public abstract void changePlayersAnswer(Directions direction);
}
