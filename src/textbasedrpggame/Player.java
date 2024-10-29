/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package textbasedrpggame;

/**
 *
 * @author Hayato
 */


import java.util.List;

public class Player extends Character {
    private Inventory inventory;
    private int xp;         
    private int gold;    
    private Armour equippedArmour;
    private Weapon equippedWeapon;
    

    // Constructor to initialize the player
    public Player(String name, int health, int strength, int defense) {
        super(name, health, strength, defense);
        this.inventory = new Inventory();
        this.xp = 0;
        this.gold = 10; // Starting amount of currency
    }

     // Gets and sets
    public int getCurrency() {
        return this.gold;
    }
    
    public String getName(){
        return this.name;
        
    }
    
    public Inventory getInventory() {
        return inventory;
    }
    
    
   public void setName(String name) {
        this.name = name; // Set the player's name
    }

   
    public void setCurrency(int gold) {
        this.gold = gold;
    }
       
    
    public void printStats(){
        System.out.println("Player Stats:");
        System.out.println("Name: " + name);
        System.out.println("Health: " + health);
        System.out.println("Strength: " + strength);
        System.out.println("Defense: " + defense);
        System.out.println("Currency: " + gold);
}
   
    
     
    /// add gold
    public void earnCurrency(int amount) {
        this.gold += amount;
        System.out.println("You earned " + amount + " gold. Total gold: " + this.gold);
    }

    // spend gold
    public boolean removeCurrency(int amount) {
        if (this.gold >= amount) {
            this.gold -= amount;
            System.out.println("You spent " + amount + " gold. Remaining gold: " + this.gold);
            return true;
        } else {
            System.out.println("Not enough gold!");
            return false;
        }
    }
    
    // Method to show inventory and equipped items
    public String showInventory() {
        StringBuilder sb = new StringBuilder();
        sb.append("Your inventory contains:\n");

        List<Item> items = inventory.getItems();  // Ensure getItems() returns the list of items
        if (items.isEmpty()) {
            sb.append("No unequipped items.\n");
        } else {
            for (int i = 0; i < items.size(); i++) {
                sb.append(i + 1).append(". ").append(items.get(i)).append("\n");
            }
        }

        // Show equipped items
        sb.append("\nEquipped items:\n");
        if (equippedWeapon != null) {
            sb.append("Weapon: ").append(equippedWeapon).append("\n");
        } else {
            sb.append("Weapon: None\n");
        }
        
        if (equippedArmour != null) {
            sb.append("Armour: ").append(equippedArmour).append("\n");
        } else {
            sb.append("Armour: None\n");
        }
        
        return sb.toString();
    }
    
    public interface Event {
    void trigger(Player player);
}


    // Add an Item to the player's inventory
public void addItem(Item item) {
    inventory.addItem(item);
}


    // Method to gain experience
    public void gainExperience(int amount) {
        this.xp += amount;
        if (this.xp >= 100) { // amount before level up
            levelUp();
            this.xp = 0; // resets xp 
        }
    }
    
  public void equipArmour(Armour armour) {
    if (inventory.hasItem(armour)) {
        // Unequip the current armor, if any
        if (this.equippedArmour != null) {
            //reduce defence from equip
            this.defense -= this.equippedArmour.getDefense(); 
            System.out.println("Unequipped " + this.equippedArmour.getName() + ".");
        }
        
        // Equip the new armor
        this.equippedArmour = armour;
        this.defense += armour.getDefense(); 

        System.out.println("Equipped " + armour.getName() + ".");
    } else {
        System.out.println("Something went wrong");
    }
}
  
  public void useItem(Item item) {
    if (inventory.hasItem(item)) {
        System.out.println("Using item: " + item.getName());

        // Check the type of item and use it accordingly
        if (item instanceof HealthPotion) {
            ((HealthPotion) item).use(this);
        } else {
            System.out.println("Cannot use this item.");
        }

        // Remove item from inventory after use
        inventory.removeItem(item);
    } else {
        System.out.println("Item not found in inventory.");
    }
}
  
    public void useHealthPotion(HealthPotion potion) {
        System.out.println("Check health: " + getHealth());
        if (inventory.hasItem(potion)) {
            this.health += potion.getHealAmount();
            // max health is 100
            if (this.health > 100) { 
                this.health = 100;
            }
            inventory.removeItem(potion);
            System.out.println("Used " + potion.getName() + ". Your health is now " + this.health + ".");
        } else {
            System.out.println("whoops");
        }
    }
    
     
     
    public void equipWeapon(Weapon weapon) {
    if (inventory.hasItem(weapon)) {
        // Unequip the current weapon, if any
        if (this.equippedWeapon != null) {
            // reduce strength by current equip value
            this.strength -= this.equippedWeapon.getDamage();
            System.out.println("Unequipped " + this.equippedWeapon.getName() + ".");
        }
        
        // Equip the new weapon
        this.equippedWeapon = weapon;
        this.strength += weapon.getDamage(); 
        System.out.println("Equipped " + weapon.getName() + ".");
    } else {
        System.out.println("whoops.");
    }
}
    
    
  

    
    // return stats
    @Override
    public String toString() {
        return super.toString() + ", Experience: " + this.xp + ", Gold: " + this.gold + ", Inventory: " + this.inventory;
    }
}

