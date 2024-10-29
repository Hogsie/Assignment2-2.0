/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package textbasedrpggame;

/**
 *
 * @author Hayato
 */


public class Armour extends Item {
    private int defense;

    // Constructor for Armour
    public Armour(String name, String description, int defense) {
        super(name, description);  
        this.defense = defense;
    }

    // Getter for defense
    public int getDefense() {
        return defense;
    }

    // Setter for defense
    public void setDefense(int defense) {
        this.defense = defense;
    }
    
    public String getDesciption(){
        return description;
    }


    // overiddes apply in item
    @Override
    public void skill(Player player) {
        player.setDefense(player.getDefense() + defense);
        System.out.println("Equipped " + getName() + ". Increased defense by " + defense + ".");
    }

    // Override toString method
    @Override
    public String toString() {
        return super.toString() + ", Defense: " + defense;
    }
}
