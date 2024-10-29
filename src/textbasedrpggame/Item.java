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

    // Constructor
    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    
    public void apply(Player player) {
     
        System.out.println("Using item: " + this.name);
    }

   
    public String getName() {
        return name;
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
}
