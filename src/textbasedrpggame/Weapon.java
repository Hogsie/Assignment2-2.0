/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package textbasedrpggame;




public class Weapon extends Item {
    private int damage;

    // Constructor 
    public Weapon(String name, String description, int damage) {
        super(name, description);
        this.damage = damage;
    }

    // gets and sets
    public int getDamage() {
        return damage;
    }
    
    public String getDesciption(){
        return description;
    }

   
    public void setDamage(int damage) {
        this.damage = damage;
    }

    //add bonus attack
    @Override
    public void apply(Player player) {
        // Increase player's attack strength
        player.setStrength(player.getStrength() + damage);
        System.out.println("Equipped " + getName() + ". Increased attack strength by " + damage + ".");
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
