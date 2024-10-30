/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package textbasedrpggame;
import java.io.IOException;


/**
 *
 * @author Hayato
 */


import java.util.Scanner;

public class MainMenu {
    private Scanner scanner;
    private GameManager gameManager;

    public MainMenu() {
        this.scanner = new Scanner(System.in);
        this.gameManager = new GameManager();
    }
// main menu
  /*  
    public void displayMenu() {
        while (true) {
            System.out.println("=== Main Menu ===");
            System.out.println("1. Start New Game");
            System.out.println("2. Load Game");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    gameManager.startNewGame();
                    break;
                case "2":
                    try {
        gameManager.loadGame("player_data"); // Pass filename to loadGame
    } catch (IOException e) {
        System.out.println("An error occurred while loading the game.");
        e.printStackTrace();
    }
                case "3":
                    System.out.println("Exiting game...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
*/
}
