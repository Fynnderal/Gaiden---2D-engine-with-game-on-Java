package cvut.cz.characters;


/**
 * Saves basic information about a character.
 */

public class CharacterInformation implements Cloneable{
    private States currentState;
    private int speed;
    private final int maxHealth;
    private int currentHealth;
    private final int attackPower;

    public CharacterInformation(int attackPower, States currentState, int maxHealth, int speed) {
        this.currentState = currentState;
        this.speed = speed;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.attackPower = attackPower;
    }

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

    public States getCurrentState() {return currentState;}
    public int getSpeed() { return speed; }
    public int getMaxHealth() { return maxHealth; }
    public int getCurrentHealth() { return currentHealth; }
    public int getAttackPower() { return attackPower;}

    public void setSpeed(int speed) { this.speed = speed;}
    public void setCurrentState(States currentState) {this.currentState = currentState;}
}
