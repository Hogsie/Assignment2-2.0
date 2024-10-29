/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package textbasedrpggame;

/**
 *
 * @author Hayato
 */

public class Enemy extends Character {

    private String type; // Additional attribute for enemy type

    // Constructor
    public Enemy(String name, int health, int strength, int defense) {
        super(name, health, strength, defense);
      
    }

  
    
    public String getName(){
        return this.name;
    }

    

}
