package de.presti.ree6.webinterface.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * A "Connector" Class which connect with the used Database Server.
 * Used to manage the connection between Server and Client.
 */
@SuppressWarnings({"SqlNoDataSourceInspection", "SqlResolve"})
public class SQLConnector {

    // Various String that keep connection information to use for a connection.
    private final String databaseUser, databaseName, databasePassword, databaseServerIP;

    // The port of the Server.
    private final int databaseServerPort;

    // An Instance of the actual Java SQL Connection.
    private Connection connection;

    // An Instance of the SQL-Worker which works with the Data in the Database.
    private final SQLWorker sqlWorker;

    /**
     * Constructor with the needed data to open an SQL connection.
     * @param databaseUser the Database Username
     * @param databaseName the Database name
     * @param databasePassword the Database User password
     * @param databaseServerIP the Address of the Database Server.
     * @param databaseServerPort the Port of the Database Server.
     */
    public SQLConnector(String databaseUser, String databaseName, String databasePassword, String databaseServerIP, int databaseServerPort) {
        this.databaseUser = databaseUser;
        this.databaseName = databaseName;
        this.databasePassword = databasePassword;
        this.databaseServerIP = databaseServerIP;
        this.databaseServerPort = databaseServerPort;
        connectToSQLServer();
        createTables();

        sqlWorker = new SQLWorker(this);
    }

    /**
     * Try to open a connection to the SQL Server with the given data.
     */
    public void connectToSQLServer() {

        // Check if there is already an open Connection.
        if (IsConnected()) {
            try {
                // Close if there is and notify.
                connection.close();
                System.out.println("Service (MariaDB) has been stopped.");
            } catch (Exception ignore) {
                // Notify if there was an error.
                System.out.println("Service (MariaDB) couldn't be stopped.");
            }
        }

        try {
            // Create a new Connection by using the SQL DriverManager and the MariaDB Java Driver and notify if successful.
            connection = DriverManager.getConnection("jdbc:mariadb://" + databaseServerIP + ":" + databaseServerPort + "/" + databaseName + "?autoReconnect=true", databaseUser, databasePassword);
            System.out.println("Service (MariaDB) has been started. Connection was successful.");
        } catch (Exception ignore) {
            // Notify if there was an error.
            System.out.println("Service (MariaDB) couldn't be started. Connection was unsuccessful.");
        }
    }

    /**
     * Create Tables in the Database if they aren't already set.
     */
    public void createTables() {

        // Check if there is an open Connection if not, skip.
        if (!IsConnected()) return;

        // Creating the HashMap for the tables.
        final HashMap<String, String> valueMap = new HashMap<>();

        // Registering the tables and types.
        valueMap.put("Settings", "(GID VARCHAR(40), NAME VARCHAR(40), VALUE VARCHAR(50)");
        valueMap.put("CommandStats", "(COMMAND VARCHAR(40), USES VARCHAR(50))");
        valueMap.put("GuildStats", "(GID VARCHAR(40), COMMAND VARCHAR(40), USES VARCHAR(50))");
        valueMap.put("TwitchNotify", "(GID VARCHAR(40), NAME VARCHAR(40), CID VARCHAR(40), TOKEN VARCHAR(68))");
        valueMap.put("LogWebhooks", "(GID VARCHAR(40), CID VARCHAR(40), TOKEN VARCHAR(68))");
        valueMap.put("WelcomeWebhooks", "(GID VARCHAR(40), CID VARCHAR(40), TOKEN VARCHAR(68))");
        valueMap.put("NewsWebhooks", "(GID VARCHAR(40), CID VARCHAR(40), TOKEN VARCHAR(68))");
        valueMap.put("RainbowWebhooks", "(GID VARCHAR(40), CID VARCHAR(40), TOKEN VARCHAR(68))");
        valueMap.put("JoinMessage", "(GID VARCHAR(40), MSG VARCHAR(250))");
        valueMap.put("MuteRoles", "(GID VARCHAR(40), RID VARCHAR(40))");
        valueMap.put("ChatProtector", "(GID VARCHAR(40), WORD VARCHAR(40))");
        valueMap.put("AutoRoles", "(GID VARCHAR(40), RID VARCHAR(40))");
        valueMap.put("Invites", "(GID VARCHAR(40), UID VARCHAR(40), USES VARCHAR(40), CODE VARCHAR(40))");
        valueMap.put("Level", "(GID VARCHAR(40), UID VARCHAR(40), XP VARCHAR(500))");
        valueMap.put("VCLevel", "(GID VARCHAR(40), UID VARCHAR(40), XP VARCHAR(500))");
        valueMap.put("VCLevelAutoRoles", "(GID VARCHAR(40), RID VARCHAR(40), LVL VARCHAR(500))");
        valueMap.put("ChatLevelAutoRoles", "(GID VARCHAR(40), RID VARCHAR(40), LVL VARCHAR(500))");

        // Iterating through all table presets.
        for (String key : valueMap.keySet()) {

            // Create a Table based on the key.
            try (PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + key + " " + valueMap.get(key))) {
                ps.executeUpdate();
            } catch (SQLException ignore) {

                // Notify if there was an error.
                System.out.println("Couldn't create " + key + " Table.");
            }
        }


    }

    /**
     * Check if there is an open connection to the Database Server.
     * @return boolean If the connection is opened.
     */
    public boolean IsConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (Exception ignore) {}

        return false;
    }

    /**
     * Call to close the current Connection.
     */
    public void close() {
        // Check if there is already an open Connection.
        if (IsConnected()) {
            try {
                // Close if there is and notify.
                connection.close();
                System.out.println("Service (MariaDB) has been stopped.");
            } catch (Exception ignore) {
                // Notify if there was an error.
                System.out.println("Service (MariaDB) couldn't be stopped.");
            }
        }
    }

    /**
     * Retrieve an Instance of the SQL-Connection.
     * @return Connection Instance of te SQL-Connection.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Retrieve an Instance of the SQL-Worker to work with the Data.
     * @return {@link SQLWorker} the Instance saved in this SQL-Connector.
     */
    public SQLWorker getSqlWorker() {
        return sqlWorker;
    }
}
