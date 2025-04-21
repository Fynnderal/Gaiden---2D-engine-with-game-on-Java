package cvut.cz.characters;

public class CharacterInformation implements Cloneable{
    protected States currentState;
    protected int speed;
    protected int maxHealth;
    protected int currentHealth;
    protected int attackPower;


    public CharacterInformation(int attackPower, States currentState, int currentHealth, int maxHealth, int speed) {
        this.currentState = currentState;
        this.speed = speed;
        this.maxHealth = maxHealth;
        this.currentHealth = currentHealth;
        this.attackPower = attackPower;
    }

    public CharacterInformation clone() {
        try {
            return (CharacterInformation) super.clone();
        }catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public States getCurrentState() {return currentState;}
    public int getSpeed() { return speed; }
    public int getMaxHealth() { return maxHealth; }
    public int getCurrentHealth() { return currentHealth; }
    public int getAttackPower() { return attackPower;}

    public void setSpeed(int speed) { this.speed = speed;}
    public void setMaxHealth(int maxHealth) { this.maxHealth = maxHealth;}
    public void setCurrentHealth(int currentHealth) {
        if (currentHealth > maxHealth) currentHealth = maxHealth;
        this.currentHealth = currentHealth;
    }
    public void setAttackPower(int attackPower) { this.attackPower = attackPower;}
    public void setCurrentState(States currentState) {this.currentState = currentState;}
}
