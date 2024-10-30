/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package textbasedrpggame;

/**
 *
 * @author Hayato
 */




public abstract class Item {
    public String name;
    public String description;
    public String IconPath;

    // Constructor
    public Item(String name, String description, String IconPath) {
        this.name = name;
        this.IconPath = IconPath;
        this.description = description;
    }

    
    public void apply(Player player) {
     
        System.out.println("Using item: " + this.name);
    }

   
    public String getName() {
        return name;
    }
    
    public String getItemType(Item item) {
    if (item instanceof Weapon) {
        return "Weapon";
    } else if (item instanceof Armour) {
        return "Armour";
    } else if (item instanceof HealthPotion) {
        return "HealthPotion";
    }
    return "Unknown"; // Default case
}
    
     public String getIconPath() {
        return IconPath; // Return the image path
    }
    
    // Abstract method to be implemented by subclasses
    public abstract void skill(Player player);

   
    public String getDescription() {
        return description;
    }
    
   
    @Override
    public String toString() {
        return this.name + ": " + this.description;
    }

    public abstract int getStat(); // Abstract method to get stats for items
 
}
