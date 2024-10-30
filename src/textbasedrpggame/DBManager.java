/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package textbasedrpggame;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Hayato
 */
public final class DBManager {
    
    // no user/password
    private static final String USERNAME = "db"; //your DB username
    private static final String PASSWORD = "db"; //your DB password
    private static final String URL = "jdbc:derby://localhost:1527/PlayerDB";
    private Player player; 
        Connection conn;
        
         public DBManager(Player player) {
        this.player = player; // Store the player instance
        establishConnection();
    }

    
     public Connection getConnection() {
        return this.conn;
    }
     
     public void createPlayersTable(Statement statement) {
    try {
        System.out.println("begin");


        statement.execute("CREATE TABLE PLAYERS ("
            + "ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
            + "NAME VARCHAR(255), "
            + "HEALTH INT, "
            + "STRENGTH INT, "
            + "DEFENSE INT, "
            + "CURRENCY INT"
            + ")"); 
        System.out.println("done");
    } catch (SQLException ex) {
        if (ex.getSQLState().equals("X0Y32")) { // Check for table already exists SQL state
            System.out.println("Players table already exists.");
        } else {
            ex.printStackTrace(); // Handle other SQL exceptions
        }
    }
}

     public void createItemsTable(Statement statement) {
    try {
        System.out.println("Starting creation of Items table...");
        
        statement.execute("CREATE TABLE ITEMS ("
            + "ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
            + "PLAYER_ID INT, "
            + "TYPE VARCHAR(50), " // e.g., 'Weapon', 'Armour', 'HealthPotion'
            + "NAMEITEMS VARCHAR(255), "
            + "DESCRIPTION VARCHAR(255), "
            + "IMAGE_PATH VARCHAR(255), "
            + "DAMAGE INT, " // Only for weapons
            + "DEFENSE INT, " // Only for armor
            + "HEAL_AMOUNT INT, " // Only for health potions
            + "FOREIGN KEY (PLAYER_ID) REFERENCES PLAYERS(ID)"
            + ")");

        System.out.println("Items table created successfully.");
    } catch (SQLException ex) {
        if ("X0Y32".equals(ex.getSQLState())) { // Table already exists
            System.out.println("Items table already exists.");
        } else {
            ex.printStackTrace(); // Other SQL exceptions
        }
    }
}



    
    //Establish connection
   public Connection establishConnection() {
        if (this.conn == null) {
            try {
                conn = DriverManager.getConnection(URL, USERNAME, PASSWORD); // Add your DB username and password
                Statement statement = conn.createStatement(); // Create Statement here
                createPlayersTable(statement); // Call to create table
                createItemsTable(statement);
                return conn; // Return the established connection
            } catch (SQLException ex) {
                ex.printStackTrace();
                return null; // Return null if connection fails
            }
        }
        return conn; // Return existing connection if already established
    }

    
    public void closeConnections() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public ResultSet queryDB(String sql) {

        Connection connection = this.conn;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return resultSet;
    }

    public void updateDB(String sql) {

        Connection connection = this.conn;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public List<String> getPlayerNames() {
        List<String> playerNames = new ArrayList<>();
        String query = "SELECT NAME FROM PLAYERS";

        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                playerNames.add(resultSet.getString("NAME"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Handle SQL exceptions
        }

        return playerNames;
    }


    public void savePlayer(Player player) {
       
      try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
              
             Statement statement = connection.createStatement()) {
       
          
 // Using a statement to insert the player
        String insertSQL = String.format("INSERT INTO players (name, health, strength, defense, xp, gold) VALUES ('%s', %d, %d, %d, %d, %d)",
                player.getName(), player.getHealth(), player.getStrength(), player.getDefense(), player.getXp(), player.getCurrency());

            // Execute the insert statement
            statement.executeUpdate(insertSQL);

        } catch (SQLException e) {
            // Handle SQL exceptions here
            System.err.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // Handle other exceptions
            System.err.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
      public void saveGame(Connection connection, Player player) {
    String insertPlayerSQL = "INSERT INTO Players (name, health, strength, defense, currency) VALUES ('" +
            player.getName() + "', " +
            player.getHealth() + ", " +
            player.getStrength() + ", " +
            player.getDefense() + ", " +
            player.getCurrency() + ")";
    
    try {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(insertPlayerSQL, Statement.RETURN_GENERATED_KEYS);
        
        // Get the generated player ID
        ResultSet generatedKeys = stmt.getGeneratedKeys();
        int playerId = 0;
        if (generatedKeys.next()) {
            playerId = generatedKeys.getInt(1);
        }

        // Save items data
        for (Item item : player.getInventory().getItems()) {
            String itemType = "";
            String itemSQL = "";

            if (item instanceof Weapon) {
                itemType = "Weapon";
                int damage = ((Weapon) item).getDamage();
                itemSQL = "INSERT INTO Items (playerId, type, nameitem, description, damage) VALUES (" +
                        playerId + ", '" +
                        itemType + "', '" +
                        item.getName() + "', '" +
                        item.getDescription() + "', " +
                        damage + ")";
            } else if (item instanceof Armour) {
                itemType = "Armour";
                int defense = ((Armour) item).getDefense();
                itemSQL = "INSERT INTO Items (playerId, type, nameitem, description, defense) VALUES (" +
                        playerId + ", '" +
                        itemType + "', '" +
                        item.getName() + "', '" +
                        item.getDescription() + "', " +
                        defense + ")";
            } else if (item instanceof HealthPotion) {
                itemType = "HealthPotion";
                int healAmount = ((HealthPotion) item).getHealAmount();
                itemSQL = "INSERT INTO Items (playerId, type, nameitem, description, healAmount) VALUES (" +
                        playerId + ", '" +
                        itemType + "', '" +
                        item.getName() + "', '" +
                        item.getDescription() + "', " +
                        healAmount + ")";
            }

            if (!itemSQL.isEmpty()) {
                stmt.executeUpdate(itemSQL);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

      public Player loadPlayer(int playerId) {
    Player player = null;

    try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
         Statement statement = connection.createStatement()) {
        
        // Load player data from the Players table
        String playerQuery = "SELECT * FROM PLAYERS WHERE ID = " + playerId;
        ResultSet playerResult = statement.executeQuery(playerQuery);

        if (playerResult.next()) {
            String name = playerResult.getString("NAME");
            int health = playerResult.getInt("HEALTH");
            int strength = playerResult.getInt("STRENGTH");
            int defense = playerResult.getInt("DEFENSE");
            int currency = playerResult.getInt("CURRENCY");

            // Initialize player object
            player = new Player(name, health, strength, defense);
            player.setCurrency(currency);
        }

        // Load items for this player from the Items table
        if (player != null) {
            String itemsQuery = "SELECT * FROM ITEMS WHERE PLAYER_ID = " + playerId;
            ResultSet itemsResult = statement.executeQuery(itemsQuery);

            while (itemsResult.next()) {
                String itemType = itemsResult.getString("TYPE");
                String itemName = itemsResult.getString("NAME");
                String itemDescription = itemsResult.getString("DESCRIPTION");
                String imagePath = itemsResult.getString("IMAGE_PATH");

                Item item = null;

                // Determine item type and create corresponding item
                switch (itemType) {
                    case "Weapon":
                        int damage = itemsResult.getInt("DAMAGE");
                        item = new Weapon(itemName, itemDescription, imagePath, damage);
                        break;
                    case "Armour":
                        int defenseValue = itemsResult.getInt("DEFENSE");
                        item = new Armour(itemName, itemDescription, imagePath, defenseValue);
                        break;
                    case "HealthPotion":
                        int healAmount = itemsResult.getInt("HEAL_AMOUNT");
                        item = new HealthPotion(itemName, itemDescription, imagePath, healAmount);
                        break;
                }

                // Add item to player's inventory if it was successfully created
                if (item != null) {
                    player.getInventory().addItem(item);
                }
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return player; // Return the loaded player with items
}

}
       


