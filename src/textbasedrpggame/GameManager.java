/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package textbasedrpggame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;


import java.util.ArrayList;

import java.util.Random;
    public class GameManager {
    private Player player;
    private Inventory inventory;
    private Scanner scanner;
    private Random random;


    public GameManager() {
        this.inventory = new Inventory();
        this.scanner = new Scanner(System.in);
        this.random = new Random(); 
    }
    
   

    
    

   public void startNewGame() {
    System.out.print("Enter your character's name: ");
    String playerName = scanner.nextLine();
    player = new Player(playerName, 100, 10, 5);
   
    System.out.println("Welcome, " + player.getName() + "! You awake in an unknown location...");
   
    // Prompt the player to type "ready" to begin combat
    System.out.println("A level 1 zombie approaches. Type 'ready' to begin combat.");
    String input = scanner.nextLine().toLowerCase();

    if (input.equals("ready")) {
        encounterBeginnerEnemy();
    } else {
        System.out.println("You hesitated! The zombie attacks!");
        encounterBeginnerEnemy();
    }

    System.out.println("You search the body...");
    System.out.println("Type 'search' to find something valuable.");
    String key = scanner.nextLine().toLowerCase();

    if (key.equals("search")) {
        // Simulate finding the health potion
        HealthPotion healthPotion = new HealthPotion("Small Health Potion", "Restores 20 HP.", 20);
        player.getInventory().addItem(healthPotion);
        System.out.println("You found a Small Health Potion!");
    } else {
        // Concussion scenario
        System.out.println("You suffered a concussion and failed to search properly.");
        System.out.println("The search was unsuccessful, and you found nothing.");
    }

    waitForNext(); 
    runGame(); 
}

    
   public void runGame() {
    boolean gameRunning = true;
    while (gameRunning) {
        showOptions();
        String action = scanner.nextLine().toLowerCase();

        switch (action) {
            case "inv":
                handleInventory();
                break;
            case "stats":
                //System.out.println(player);
                player.printStats();
                break;
            case "explore":
                triggerRandomEvent();
                break;
            case "help":
                showHelp();
                break;
            case "save":
                try {
                    saveGame("player_data");
                } catch (IOException e) {
                    System.out.println("An error occurred while saving the game.");
                    e.printStackTrace();
                }
                break;
            case "quit":
                gameRunning = false;
                System.out.println("Returning to menu");
                break;
            case "cheat":
                HealthPotion largePotion = new HealthPotion("Large Health Potion", "Restores a large amount of health.", 1000);
                player.getInventory().addItem(largePotion);
                break;
            case "cheat2":
                player.getInventory().addItem(new Weapon("Rusty Sword", "A rusty old sword.", 5));
                player.getInventory().addItem(new Armour("Basic Armour", "Some basic protection.", 5));
                break;
                
            case "cheat3":
               

                     player.getInventory().addItem(new ShinySword());
                     
                     break;
            default:
                System.out.println("Invalid command.");
                break;
        }
    }
}


    // saving the player data
    public void saveGame(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(player.getName() + "\n");
            writer.write(player.getHealth() + "\n");
            writer.write(player.getStrength() + "\n");
            writer.write(player.getDefense() + "\n");
            writer.write(player.getCurrency() + "\n");
            writer.write(player.getInventory().getItems().size() + "\n");
          
             // saves items 4 lines of text, type, name, stats and description,
        for (Item item : player.getInventory().getItems()) {
            if (item instanceof Weapon) {
                writer.write("Weapon\n");
                writer.write(item.getName() + "\n");
                writer.write(((Weapon) item).getDamage() + "\n");
            } else if (item instanceof Armour) {
                writer.write("Armour\n");
                writer.write(item.getName() + "\n");
                writer.write(((Armour) item).getDefense() + "\n");
            } else if (item instanceof HealthPotion) {
                writer.write("HealthPotion\n");
                writer.write(item.getName() + "\n");
                writer.write(((HealthPotion) item).getHealAmount() + "\n");
            }
            
             writer.write(item.getDescription() + "\n");  
        }
        }
    }
    
    // loads player data
    public void loadGame(String filename) throws IOException {
    System.out.println("check2");
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
        String name = reader.readLine();
        int health = Integer.parseInt(reader.readLine());
        int strength = Integer.parseInt(reader.readLine());
        int defense = Integer.parseInt(reader.readLine());
        int gold = Integer.parseInt(reader.readLine());

        player = new Player(name, health, strength, defense);
        player.setCurrency(gold);

        int numItems = Integer.parseInt(reader.readLine());
        for (int i = 0; i < numItems; i++) {
            String itemType = reader.readLine();
            String itemName = reader.readLine();
            Item item = null;

            // reverses the save file, reads back the 4 lines of text saved per item, 
            // thats type, description, name ,stats and type.
            switch (itemType) {
                case "Weapon":
                    int attackValue = Integer.parseInt(reader.readLine());
                    String weaponDescription = reader.readLine(); 
                    item = new Weapon(itemName, weaponDescription, attackValue);
                    break;
                case "Armour":
                    int defenseValue = Integer.parseInt(reader.readLine());
                    String armourDescription = reader.readLine(); 
                    item = new Armour(itemName, armourDescription, defenseValue);
                    break;
                case "HealthPotion":
                    int healingAmount = Integer.parseInt(reader.readLine());
                    String potionDescription = reader.readLine(); 
                    item = new HealthPotion(itemName, potionDescription, healingAmount);
                    break;
            }

            if (item != null) {
                player.getInventory().addItem(item);
            }
        }
    }
    
    runGame();
}

    //options
 private void showOptions() {
    System.out.println("Choose an action:");
    System.out.println("(inv) Inventory");
    System.out.println("(stats) Stats");
    System.out.println("(explore) Explore");
    System.out.println("(help) Help");
    System.out.println("(save) Save Game");
    System.out.println("(quit) Quit");
}
 
 // help menu
 private void showHelp() {
    System.out.println("=== Help Menu ===");
    System.out.println("inv - View your inventory.");
    System.out.println("stats - View your character's stats.");
    System.out.println("explore - Explore the world and encounter random events.");
    System.out.println("help - Show this help menu.");
    System.out.println("Save game in current state");
    System.out.println("quit - Exit the game.");
}

 private void checkHealth() {
    if (player.getHealth() <= 0) {
        System.out.println("You have been defeated! Game Over.");
       System.exit(0); // Exit the game
    }
}
 private void waitForNext() {
    System.out.println("Type 'next' to continue...");
    String input = scanner.nextLine().toLowerCase();
    while (!input.equals("next")) {
        System.out.println("Invalid input. Type 'next' to continue...");
        input = scanner.nextLine().toLowerCase();
    }
}
    
   

     private void triggerRandomEvent() {
        double chance = random.nextDouble();
        if (chance < 0.3) {
            encounterEnemy();
        } else if (chance < 0.6) {
            searchHouse();
        } else if (chance < 0.8) {
            exploreSafeHouse();
        } else {
            encounterSurvivor();
        }
    }

     
     // fighting begginer enemy, lvl 1 zombie
    private void encounterBeginnerEnemy() {
        Enemy enemy = new Enemy("Level 1 Zombie", 50, 7, 2);
        Combat combat = new Combat(player, enemy);
        combat.startBattle();
        if (!player.isAlive()) {
            System.out.println("HOW?????");
           
        }
    }

    // chat gpt assisted/function, got confused with string array and object array inventory
    private void handleInventory() {
    System.out.println(player.showInventory());
    System.out.println("Enter the number of the item you want to use or equip, or type 'cancel' to exit:");

    String input = scanner.nextLine().toLowerCase();

    if (input.equals("cancel")) {
        return;
    }

    try {
        int choice = Integer.parseInt(input) - 1;
        List<Item> items = player.getInventory().getItems();

        if (choice >= 0 && choice < items.size()) {
            Item selectedItem = items.get(choice);

            if (selectedItem instanceof Weapon) {
                player.equipWeapon((Weapon) selectedItem);
            } else if (selectedItem instanceof Armour) {
                player.equipArmour((Armour) selectedItem);
            } else if (selectedItem instanceof HealthPotion) {
                player.useItem(selectedItem);
     
            } else {
                System.out.println("Item cannot be equipped or used.");
            }
        } else {
            System.out.println("Invalid item number.");
        }
    } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a number.");
    }
}

    /*
    // Small Health Potion
HealthPotion smallPotion = new HealthPotion("Small Health Potion", "Restores a small amount of health.", 20);

// Medium Health Potion
HealthPotion mediumPotion = new HealthPotion("Medium Health Potion", "Restores a moderate amount of health.", 50);

// Large Health Potion
HealthPotion largePotion = new HealthPotion("Large Health Potion", "Restores a large amount of health.", 100);

*/
// CHATGPT assisted/prompted Generated scenaroios
    private void encounterEnemy() {
    // Randomly select a zombie level between 1 and 10
    int zombieLevel = (int) (Math.random() * 10) + 1;
    Enemy enemy = new Enemy("Level " + zombieLevel + " Zombie", 50 * zombieLevel, 7 * zombieLevel, 2 * zombieLevel);
    System.out.println("You encounter a Level " + zombieLevel + " Zombie!");

    // Give the player the option to fight or run
    System.out.print("Do you want to (fight) or (run)? ");
    String choice = scanner.nextLine().toLowerCase();

    if (choice.equals("fight")) {
        Combat combat = new Combat(player, enemy);
        combat.startBattle();
        if (player.isAlive()) {
            // Reward the player based on the zombie's level
            System.out.println("You defeated the Level " + zombieLevel + " Zombie!");
            player.getInventory().addItem(new Weapon("Rusty Sword", "A rusty old sword.", 5));
            player.getInventory().addItem(new Armour("Basic Armour", "Some basic protection.", 5 * zombieLevel));
            player.earnCurrency(50 * zombieLevel);
            player.gainExperience(20*zombieLevel);
        } else {
              checkHealth();
        }
    } else if (choice.equals("run")) {
        System.out.println("You ran away safely.");
    } else {
        System.out.println("Invalid choice. The zombie attacks!");
        Combat combat = new Combat(player, enemy);
        combat.startBattle();
        if (!player.isAlive()) {
              checkHealth();
            
        }
    }
}
    
    private void searchHouse() {
    System.out.println("You enter a house to search for supplies...");

    double encounterChance = Math.random();

    if (encounterChance < 0.4) {  // 40% chance to encounter a zombie
        System.out.println("Oh no! You've encountered a zombie!");

        // Random zombie level between 1 and 10
        int zombieLevel = (int) (Math.random() * 10) + 1;
        Enemy zombie = new Enemy("Level " + zombieLevel + " Zombie", 30 + zombieLevel * 5, 5 + zombieLevel, 3 + zombieLevel);
        
        System.out.println("The zombie is level " + zombieLevel + ". Do you want to fight or run?");
        String decision = scanner.nextLine().toLowerCase();

        if (decision.equals("fight")) {
            Combat combat = new Combat(player, zombie);
            combat.startBattle();

            if (player.isAlive()) {
                System.out.println("You defeated the zombie!");

                // Reward player
                player.getInventory().addItem(new Weapon("Rusty Sword", "A rusty old sword.", 5));
                player.getInventory().addItem(new Armour("Worn Armour", "A piece of worn armor.", 3));
                int goldReward = 50 * zombieLevel;
                player.earnCurrency(goldReward);
                player.gainExperience(100);

                System.out.println("You found a Rusty Sword, Worn Armour, and " + goldReward + " gold!");
            } else {
                System.out.println("You were defeated by the zombie...");
                  checkHealth();
            }
        } else {
            System.out.println("You managed to escape from the zombie.");
        }
    } else if (encounterChance < 0.7) {  // 30% chance to loot items
        Item item = new HealthPotion("Health Potion", "A potion that restores 20 health.",20);
        player.getInventory().addItem(item);
        System.out.println("You found a " + item.getName() + "!");
    } else {  // 30% chance to find gold
        int goldFound = (int) (Math.random() * 100) + 50;
        player.earnCurrency(goldFound);
        System.out.println("You found " + goldFound + " gold!");
    }
    waitForNext();
}

    private void encounterSurvivor() {
    System.out.println("You find a survivor who offers to sell you some items.");
    System.out.println("The survivor has the following items for sale:");
    System.out.println("1. Small Health Potion - 10 gold");
    System.out.println("2. Rusty Sword - 20 gold");
    System.out.println("3. Worn Armour - 30 gold");
    System.out.println("Type the number of the item you want to buy, or type 'leave' to exit.");

    String choice = scanner.nextLine().toLowerCase();

    switch (choice) {
        case "1":
            purchaseItem("Small Health Potion", 10);
            break;
        case "2":
            purchaseItem("Rusty Sword", 20);
            break;
        case "3":
            purchaseItem("Worn Armour", 30);
            break;
        case "leave":
            System.out.println("You decided to leave the survivor.");
            break;
        default:
            System.out.println("Invalid choice. The survivor leaves.");
            break;
    }
    waitForNext();
}

private void purchaseItem(String itemName, int price) {
    if (player.removeCurrency(price)) {
        Item item;
        switch (itemName) {
            case "Small Health Potion":
                item = new HealthPotion("Small Health Potion", "Restores 20 HP.", 20);
                break;
            case "Rusty Sword":
                item = new Weapon("Rusty Sword", "A rusty old sword.", 5);
                break;
            case "Worn Armour":
                item = new Armour("Worn Armour", "Old, but effective armor.", 5);
                break;
            default:
                System.out.println("Item not found.");
                return;
        }
        player.getInventory().addItem(item);
        System.out.println("You bought a " + itemName + "!");
    } else {
        System.out.println("You don't have enough gold.");
    }
    
}


private void exploreSafeHouse() {
    System.out.println("You stumble upon an abandoned safe house. It looks like it hasn’t been used in a long time.");
    System.out.println("Do you want to search the safe house? Type 'search' to look inside, or 'leave' to move on.");
    
    String choice = scanner.nextLine().toLowerCase();
    
    if (choice.equals("search")) {
        Random random = new Random();
        double chance = random.nextDouble();
        
        if (chance < 0.3) { // 30% chance of triggering a trap
            triggerTrap();
        } else {
            findLoot();
        }
    } else {
        System.out.println("You decide to leave the safe house and continue your journey.");
    }
    waitForNext();
}

private void triggerTrap() {
    System.out.println("As you search the safe house, you accidentally trigger a hidden trap!");
    int damage = 15; // Amount of damage from the trap
    player.setHealth(player.getHealth() - damage);
    System.out.println("You take " + damage + " damage from the trap! Your health is now " + player.getHealth() + ".");
  checkHealth();
    // After taking damage, offer the player a chance to search again
    System.out.println("Despite the trap, you can still search for loot. Type 'search' to try again or 'leave' to exit.");
    String choice = scanner.nextLine().toLowerCase();
    
    if (choice.equals("search")) {
        findLoot();
    } else {
        System.out.println("You decide to leave the safe house, feeling a bit worse for wear.");
    }
}

private void findLoot() {
    // Define possible loot items
    List<Item> possibleLoot = new ArrayList<>();
    possibleLoot.add(new HealthPotion("Medium Health Potion", "Restores 50 HP.", 50));
    possibleLoot.add(new Weapon("Sharp Sword", "A well-made sword with a sharp edge.", 10));
    possibleLoot.add(new Armour("Shiny Armour", "Provides excellent protection.", 15));

    // Randomly select loot
    Random random = new Random();
    Item loot = possibleLoot.get(random.nextInt(possibleLoot.size()));

    player.getInventory().addItem(loot);
    System.out.println("You find a " + loot.getName() + " in the safe house!");

    // Optionally, you can provide details of the item found
    System.out.println("Item details: " + loot.toString());
}





    }