package cvut.cz.characters;


/**
 * Keeps basic information about a character: current state of the character, speed, maxHealth, currentHealth and attack power.
 */

public class CharacterInformation implements Cloneable{
    //Action that the character is performing at the moment
    private States currentState;

    private int speed;
    private final int maxHealth;

    //Amount of health at the moment
    private int currentHealth;

    //damage that the character can deal
    private final int attackPower;

    /**
     * Initializes the CharacterInformation to specified values.
     * @param attackPower - amount of damage that character can inflict
     * @param currentState - current state of character
     * @param maxHealth - Maximum health of the character. Current amount of health is initially set to maxHealth
     * @param speed - speed of the character
     */
    public CharacterInformation(int attackPower, States currentState, int maxHealth, int speed) {
        this.currentState = currentState;
        this.speed = speed;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.attackPower = attackPower;
    }

    /**
     * Creates and returns a copy of this object.
     *
     * @return A clone of this instance, or null if cloning is not supported.
     */
    @Override
    public CharacterInformation clone() {
        try {
            return (CharacterInformation) super.clone();
        }catch (CloneNotSupportedException e) {
            return null;
        }
    }

    /**
     * Sets current health of character
     * @param currentHealth - value to set, if it's greater than maxHealth, currentHealth will equal maxHealth
     */
    public void setCurrentHealth(int currentHealth) {
        if (currentHealth > maxHealth) currentHealth = maxHealth;
        this.currentHealth = currentHealth;
    }
    /**
     * Gets the current state of the character.
     *
     * @return The current state of the character.
     */
    public States getCurrentState() {return currentState;}
    /**
     * Gets the speed of the character.
     *
     * @return The speed of the character.
     */
    public int getSpeed() { return speed; }
    /**
     * Gets the maximum health of the character.
     *
     * @return The maximum health of the character.
     */
    public int getMaxHealth() { return maxHealth; }
    /**
     * Gets the current health of the character.
     *
     * @return The current health of the character.
     */
    public int getCurrentHealth() { return currentHealth; }
    /**
     * Gets the attack power of the character.
     *
     * @return The attack power of the character.
     */
    public int getAttackPower() { return attackPower; }

    /**
     * Sets the speed of the character.
     *
     * @param speed The speed to set.
     */
    public void setSpeed(int speed) { this.speed = speed; }

    /**
     * Sets the current state of the character.
     *
     * @param currentState The state to set.
     */
    public void setCurrentState(States currentState) { this.currentState = currentState; }
}
