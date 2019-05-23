package sharedShoppingList.server.db;

import java.sql.Connection;
import java.sql.DriverManager;

/*
 * Klasse um die Datenbank Verbindung herzustellen.
 * Diese Klasse wird nur einmal instanziiert
 * 
 * @author Nico Weiler und Leon Seiz
 */

public class DBConnection {
	
	/*
	 * Die folgende Variable speichert die einzige Instanz dieser Klasse.
	 * Sie ist nur einmal für sämtliche Instanzen vorhanden.
	 */
    private static Connection con = null;  

    /**
     * Über die folgende zwei URLs werden die Datenbanken angesprochen.
     * Eine lokale zum Testen
     * Eine in der Cloud (Google Cloud Plattform)
     */
    private static String googleUrl = "jdbc:google:mysql://it-projekt-hdm-238911:us-central1:myinstance/shoppinglist?user=root&password=Kekbuy2019";
    private static String localUrl = "jdbc:mysql://127.0.0.1:3306/itproject?user=root&password=Kekbuy2019";
    
    /**
     * Singleton Eigenschaft wird sichergestellt indem nur eine Instanz
     * von DBConnection vorhanden ist.
     * 
     * DBConnection wird immer durch Aufruf dieser statischen Methode instanziiert
     * 
     * 
     * @return <code>DBConncetion</code>-Objekt.
     * @see con
     */
    public static Connection connection() {
        // Wenn es bisher keine Conncetion zur DB gab, ...
        if (con == null) {
            
            try {
               /* if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
                    // Load the class that provides the new
                    // "jdbc:google:mysql://" prefix.
                    Class.forName("com.mysql.jdbc.GoogleDriver");
                    url = googleUrl;
                } else {
                    // Local MySQL instance to use during development.
                    Class.forName("com.mysql.jdbc.Driver");
                    url = localUrl;
                }
                /*
                 * Dann erst kann uns der DriverManager eine Verbindung mit den
                 * oben in der Variable url angegebenen Verbindungsinformationen
                 * aufbauen.
                 * 
                 * Diese Verbindung wird dann in der statischen Variable con
                 * abgespeichert und fortan verwendet.
                 */
               // con = DriverManager.getConnection(localUrl);
            	con = DriverManager.getConnection("jdbc:mysql://localhost:3306/itproject?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "Kekbuy2019");
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
