package textbasedrpggame;

public class Weapon extends Item {
    private int damage;

    // Constructor 
    public Weapon(String name, String description, String iconPath, int damage) {
        super(name, description, iconPath); // Make sure Item class has an iconPath
        this.damage = damage;
    }

    // Getters and setters
    public int getDamage() {
        return damage;
    }
    
    public String getDescription() { // Corrected method name
        return description; // Make sure 'description' is declared in Item
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    // Apply the weapon effect
    @Override
    public void apply(Player player) {
        player.setStrength(player.getStrength() + damage);
        System.out.println("Equipped " + getName() + ". Increased attack strength by " + damage + ".");
    }
    
    @Override
    public int getStat() {
        return damage; // Return defense for armour
    }
    
    @Override
    public void skill(Player player) {
        player.setDefense(player.getDefense() + damage);
        System.out.println("Equipped " + getName() + ". Increased attack by " + damage + ".");
    }

    @Override
    public String toString() {
        return super.toString() + ", Damage: " + damage;
    }
}
