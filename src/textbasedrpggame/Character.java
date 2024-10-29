/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package textbasedrpggame;

/**
 *
 * @author Hayato
 */

   

import java.util.Random;

public class Character {
    // allow subclass access
    protected String name;   
    protected int health;    
    protected int strength;  
    protected int defense;   
    protected int level;     
    private Random random;   

    // Constructor 
    public Character(String name, int health, int strength, int defense) {
        this.name = name;
        this.health = health;
        this.strength = strength;
        this.defense = defense;
        this.level = 1; // Default level
        this.random = new Random(); 
    }
    
    // gets and set
    public int getStrength() {
        return strength;
    }

    
    public void setStrength(int strength) {
        this.strength = strength;
    }

   
    public int getDefense() {
        return defense;
    }

    
    public void setDefense(int defense) {
        this.defense = defense;
    }

    
    public int getHealth() {
        return health;
    }

    
    public void setHealth(int health) {
        this.health = health;
    }

    // Attack method w randomized between 0.8 to 1.2
    public int attack(Character target) {
        // 0.8 is defult, adds dmg between 0.8 to 1.2 int
        double randomFactor = 0.8 + ((0.4) * random.nextDouble());
        // dmg cant be negative, minus defence multiplied by random factor
        int damage = (int) Math.max(0, (this.strength - target.defense) * randomFactor);
        target.health -= damage;
        System.out.println(this.name + " attacks " + target.name + " for " + damage + " damage.");
        return damage;
    }

    // checks alive
    public boolean isAlive() {
        return this.health > 0;
    }

    // level up function
    public void levelUp() {
        this.level++;
        this.strength += 1;
        this.defense += 1;
        this.health += 10;
        System.out.println("Congradulations! You leveled up!");
        
    }

    // status return
    @Override
    public String toString() {
        return this.name + ": Level " + this.level + ", Health: " + this.health +
               ", Strength: " + this.strength + ", Defense: " + this.defense;
    }
}
