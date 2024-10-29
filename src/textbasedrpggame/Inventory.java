/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package textbasedrpggame;

/**
 *
 * @author Hayato
 */



import java.util.ArrayList;
import java.util.List;

public class Inventory {
// list items for inventory
    private List<Item> items;  
    private Weapon weaponSlot;  
    private Armour armourSlot;  
    

    // Constructor to initialize the inventory
    public Inventory() {
        this.items = new ArrayList<>();
        // nothing equiped
        this.weaponSlot = null;  
        this.armourSlot = null;   
    }

    
    // Return the list of items in the inventory
    public List<Item> getItems() {
        return new ArrayList<>(items); 
    }
    
     public void addItem(Item item) {
        items.add(item);
        System.out.println(item.getName() + " has been added to your inventory.");
    }
    // Method to remove an item from the inventory
    public void removeItem(Item item) {
        System.out.println("Check");
        if (items.remove(item)) {
            System.out.println(item.getName() + " has been removed from your inventory.");
        } else {
            System.out.println(item.getName() + " is not in your inventory.");
        }
    }
    
    


// Equip a weapon
public void equipWeapon(Weapon weapon, Player player) {
    // Unequip the 
    if (weaponSlot != null) {
        unequipWeapon(player);  
    }
    weaponSlot = weapon;
    weapon.apply(player);  
    items.remove(weapon);  // 
    System.out.println(weapon.getName() + " has been equipped.");
}

// Unequip the current weapon
public void unequipWeapon(Player player) {
    if (weaponSlot != null) {
        player.setStrength(player.getStrength() - weaponSlot.getDamage());  
        items.add(weaponSlot);  
        System.out.println(weaponSlot.getName() + " has been unequipped.");
        weaponSlot = null;
    }
}
    // Equip armour
    public void equipArmour(Armour armour, Player player) {
        if (armourSlot != null) {
            unequipArmour(player);  // Unequip the current armour first
        }
        armourSlot = armour;
        armour.apply(player);  
        // Remove the armour from the general inventory
        items.remove(armour);  
        System.out.println(armour.getName() + " has been equipped.");
    }

    // Unequip the current armour
    public void unequipArmour(Player player) {
        if (armourSlot != null) {
            player.setDefense(player.getDefense() - armourSlot.getDefense());  // Revert the player's defense
            items.add(armourSlot);  // Add the armour back to the general inventory
            System.out.println(armourSlot.getName() + " has been unequipped.");
            armourSlot = null;
        }
    }

// Method to check if the inventory contains a specific item
    public boolean hasItem(Item item) {
        return items.contains(item);
    }

    
}
