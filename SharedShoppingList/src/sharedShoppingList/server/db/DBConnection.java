package sharedShoppingList.server.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	
	
	private static Connection con=null;
	
	private static String googleUrl = "jdbc:google:mysql://it-projekt-hdm-238911:us-central1:myinstance/shoppinglist?user=root&password=Kekbuy2019";

	
	public static Connection connection() {
        // Wenn es bisher keine Conncetion zur DB gab, ...
        if (con == null) {
        	
        	
            String url = null;
            try {
           
                    // Load the class that provides the new
                    // "jdbc:google:mysql://" prefix.
                    Class.forName("com.mysql.jdbc.GoogleDriver");
                    url = googleUrl;
                
                
                /*
                 * Dann erst kann uns der DriverManager eine Verbindung mit den
                 * oben in der Variable url angegebenen Verbindungsinformationen
                 * aufbauen.
                 * 
                 * Diese Verbindung wird dann in der statischen Variable con
                 * abgespeichert und fortan verwendet.
                 */
                con = DriverManager.getConnection(url);
            } catch (Exception e) {
                con = null;
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }

        // Zurückgeben der Verbindung
        return con;
    }
}
