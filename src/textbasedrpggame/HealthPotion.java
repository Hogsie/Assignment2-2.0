/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package textbasedrpggame;

/**
 *
 * @author Hayato
 */
public class HealthPotion extends Item {
    private int healingAmount;

    // Constructor 
    public HealthPotion(String name, String description, String IconPath, int healingAmount) {
        super(name, description, IconPath);
        this.healingAmount = healingAmount;
    }

    public int getHealAmount() {
        return healingAmount;
    }
    
    public String getDesciption(){
        return description;
    }


   public void use(Player player) {
       
    int newHealth = player.getHealth() + healingAmount;
    //max health 100
    if (newHealth > 2000) {
        newHealth = 100;
    }
    player.setHealth(newHealth);
    System.out.println(player.getName() + " used a " + this.getName() + " and healed for " + healingAmount + " health points.");
}
   @Override
   // optional make specific potion item
    public void skill(Player player) {
        player.setDefense(player.getDefense() + healingAmount);
        System.out.println("Equipped " + getName() + ". Increased health by " + healingAmount + ".");
    }

    // Method to display potion details
    @Override
    public String toString() {
        return super.toString() + " (Heals " + healingAmount + " HP)";
    }
    
    @Override
    public int getStat() {
        return healingAmount; // Return defense for armour
    }
}
