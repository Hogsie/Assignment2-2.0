/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package textbasedrpggame;

/**
 *
 * @author Hayato
 */


public class Combat {

    private Player player;
    private Enemy enemy;

    // Constructor 
    public Combat(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
    }

    // method
    public void startBattle() {
        System.out.println("Battle Start! " + player.getName() + " vs. " + enemy.getName());

        // Combat loop
        while (player.isAlive() && enemy.isAlive()) {
            // Player's turn to attack
            player.attack(enemy);
            if (!enemy.isAlive()) {
                System.out.println(enemy.getName() + " has been defeated!");
                break;
            }

            // Enemy's turn to attack
            enemy.attack(player);
            if (!player.isAlive()) {
                System.out.println(player.getName() + " has been defeated! Game Over.");
                break;
            }
        }
        
        // Determine the outcome of the battle
        if (player.isAlive()) {
            System.out.println(player.getName() + " wins the battle!");
        } else {
            System.out.println(player.getName() + " loses the battle.");
        }
    }
    
    }
