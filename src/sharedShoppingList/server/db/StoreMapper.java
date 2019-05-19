
package sharedShoppingList.server.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.util.Vector;

import sharedShoppingList.shared.bo.Store;
import sharedShoppingList.shared.bo.User;

/**
 * Author dieser Klasse:
 * 
 * @author Tobias Ilg
 */

public class StoreMapper {

	/**
	 * Konstruktor für den StoreMapper (Singleton) Static weil Singleton -> einzige
	 * Instanz dieser Klasse ;)
	 *
	 */

	private static StoreMapper storemapper = null;

	/**
	 * Falls noch kein StoreMapper existiert wird ein neuen StoreMapper erstellt und
	 * zurückgegeben
	 *
	 * @return erstmalig erstellter StoreMapper
	 *
	 */

	public static StoreMapper storeMapper() {
		if (storemapper == null) {
			storemapper = new StoreMapper();
		}
		return storemapper;
	}

	/**
	 * Findet Stores durch eine S_ID und speichert die dazugehörigen Werte (S_ID,
	 * name, createDate, modDate) in einem Store Objekt ab und gibt dieses wieder
	 *
	 * @param sid übergebener Integer der S_ID
	 * @return Ein vollständiges Store Objekt
	 *
	 * @author Tobias Ilg
	 */

	public Store findByID(Store store) {
		Connection con = DBConnection.connection();
		Store s = new Store();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT S_ID, name, createDate, modDate FROM T_Store WHERE S_ID ="
					+ store.getId() + " ORDER BY modDate");

			if (rs.next()) {

				s.setId(rs.getInt("S_ID"));
//s.setOwnerId(rs.getInt("name"));
				s.setCreateDate(rs.getTimestamp("createDate"));
				s.setModDate(rs.getTimestamp("modDate"));

				return s;
			}
		}

		catch (SQLException e) {
			e.printStackTrace();
			return s;
		}

		return s;
	}

	/**
	 * Gibt alle Store Objekte zurück welche mit S_ID, name, createDate und modDate
	 * befüllt sind von einem spezifischen User Hierfür holen wir S_ID, name,
	 * createDate und modDate aus der T_Store Tabelle, die dem User mit der id
	 * zugeteilt sind, und speichern diese in einem Store Objekt ab und fügen diese
	 * dem Vector hinzu Diesen Vector befüllt mit Stores geben wir zurück
	 *
	 * @return Ein Vector voller Store Objekte welche befüllt sind
	 *
	 */
	public Vector<Store> findAllByUID(User u) {
		Connection con = DBConnection.connection();
		Vector<Store> result = new Vector<Store>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT S_ID, name, createDate, modDate FROM T_Store WHERE creator ="
					+ u.getId() + " ORDER BY modDate");

			while (rs.next()) {
				Store s = new Store();
				s.setId(rs.getInt("S_ID"));
//s.setOwnerId(rs.getInt("name"));
				s.setCreateDate(rs.getTimestamp("createDate"));
				s.setModDate(rs.getTimestamp("modDate"));
				result.addElement(s);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return result;
	}

	/**
	 * Gibt alle Store Objekte zurück welche mit S_ID, name, createDate und modDate
	 * befüllt sind Hierfür holen wir S_ID, creator, cintent, createDate und
	 * modDate aus der T_Store Tabelle und speichern diese in einem Store Objekt ab
	 * und fügen diese dem Vector hinzu Diesen Vector befüllt mit Stores geben wir
	 * zurück
	 *
	 * @return Ein Vector voller Store Objekte welche befüllt sind
	 *
	 */
	public Vector<Store> findAll() {
		Connection con = DBConnection.connection();
		Vector<Store> result = new Vector<Store>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT S_ID, name, createDate, modDate FROM T_Store ORDER BY modDate");

			while (rs.next()) {
				Store s = new Store();
				s.setId(rs.getInt("S_ID"));
//s.setOwnerId(rs.getInt("name"));
				s.setCreateDate(rs.getTimestamp("createDate"));
				s.setModDate(rs.getTimestamp("modDate"));
				result.addElement(s);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return result;
	}

	/**
	 * Sucht nach der höchsten S_ID um diese um eins zu erhöhen und als neue S_ID
	 * zu nutzen Befüllt T_Store mit S_ID, name, createDate und modDate Ein Store
	 * Objekt wird zurückgegeben
	 *
	 * @param store übergebenes Store Objekt mit allen Attributen
	 * @return Ein vollständiges Store Objekt
	 *
	 */
	public Store insert(Store store) {
		Connection con = DBConnection.connection();
		Timestamp ts = new Timestamp(System.currentTimeMillis());

		String s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ts);

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT MAX(S_ID) AS maxsid FROM T_Store");
			if (rs.next()) {

				store.setId(rs.getInt("maxsid") + 1);
				Statement stmt2 = con.createStatement();
				stmt2.executeUpdate("INSERT INTO T_Store (S_ID, name, createDate, modDate)" + " VALUES ("
						+ store.getId() + ", " + store.getName() + ", '" + s + "', '" + s + "')");

				return store;

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return store;
		}
		return store;
	}

	/**
	 * Befüllt T_Store mit S_ID, name, createDate und modDate, falls sich was
	 * geändert hat Ein Store Objekt wird zurückgegeben
	 *
	 * @param store übergebenes Store Objekt mit Attributen S_ID und type
	 * @return Ein vollständiges Store Objekt
	 *
	 */
	public Store update(Store store) {
		Connection con = DBConnection.connection();
		Timestamp ts = new Timestamp(System.currentTimeMillis());

		String s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ts);

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("UPDATE T_Store SET S_ID =" + store.getId() + ", creator =" + store.getName()
// +"', createDate="
// + store.getCreateDate()
					+ "', modDate='" + s + "' WHERE S_ID=" + store.getId());
		} catch (SQLException e2) {
			e2.printStackTrace();
			return store;
		}
		return store;
	}

	/**
	 * Entfernt alles aus T_Store wo die S_ID der ID des übergebenen Objekts
	 * entspricht
	 *
	 * @param store übergebenes Store Objekt mit Attribut S_ID
	 *
	 */
	public void delete(Store store) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM T_Store WHERE S_ID =" + store.getId());
		}

		catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
}
