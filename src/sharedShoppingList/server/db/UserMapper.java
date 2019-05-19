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

public class UserMapper {

	/**
	 * all @author Tobias Ilg
	 */

	private static UserMapper usermapper = null;

	/**
	 * Falls noch kein UserMapper existiert wird ein neuen UserMapper erstellt und
	 * zurückgegebens
	 * 
	 * @return erstmalig erstellter UserMapper
	 */

	public static UserMapper userMapper() {
		if (usermapper == null) {
			usermapper = new UserMapper();
		}
		return usermapper;
	}

	/**
	 * Findet Users durch eine U_ID und speichert die dazugehörigen Werte (U_ID,
	 * name, createDate, modDate) in einem User Objekt ab und gibt dieses wieder
	 * 
	 * @param uid übergebener Integer der U_ID
	 * @return Ein vollständiges User Objekt
	 */

	public User findByID(User user) {
		Connection con = DBConnection.connection();
		User u = new User();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT U_ID, name, createDate, modDate FROM T_User WHERE U_ID ="
					+ user.getId() + " ORDER BY modDate");

			if (rs.next()) {

				u.setId(rs.getInt("U_ID"));
				u.setName(rs.getString("name"));
				u.setCreateDate(rs.getTimestamp("createDate"));
				u.setModDate(rs.getTimestamp("modDate"));

				return u;
			}
		}

		catch (SQLException e) {
			e.printStackTrace();
			return u;
		}

		return u;
	}

	/**
	 * Gibt alle User Objekte zurück welche mit U_ID, name, createDate und modDate
	 * befüllt sind von einem spezifischen User Hierfür holen wir U_ID, name,
	 * createDate und modDate aus der T_User Tabelle, die dem User mit der id
	 * zugeteilt sind, und speichern diese in einem User Objekt ab und fügen diese
	 * dem Vector hinzu Diesen Vector befüllt mit Nutzern und geben ihn zurück
	 *
	 * @return Ein Vector voller User Objekte welche befüllt sind
	 */
	public Vector<User> findAllByUID(User u) {
		Connection con = DBConnection.connection();
		Vector<User> result = new Vector<User>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT U_ID, name, createDate, modDate FROM T_User WHERE creator ="
					+ u.getId() + " ORDER BY modDate");

			while (rs.next()) {
				User u1 = new User();
				u1.setId(rs.getInt("U_ID"));
				u1.setName(rs.getString("name"));
				u1.setCreateDate(rs.getTimestamp("createDate"));
				u1.setModDate(rs.getTimestamp("modDate"));
				result.addElement(u);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return result;
	}

	/**
	 * Gibt alle User Objekte zurück welche mit U_ID, name, createDate und modDate
	 * befüllt sind Hierfür holen wir U_ID, creator, cintent, createDate und modDate
	 * aus der T_User Tabelle und speichern diese in einem User Objekt ab und fügen
	 * diese dem Vector hinzu Diesen Vector befüllt mit Users geben wir zurück
	 *
	 * @return Ein Vector voller User Objekte welche befüllt sind
	 */

	public Vector<User> findAll() {
		Connection con = DBConnection.connection();
		Vector<User> result = new Vector<User>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT U_ID, name, createDate, modDate FROM T_User ORDER BY modDate");

			while (rs.next()) {
				User u = new User();
				u.setId(rs.getInt("U_ID"));
				u.setName(rs.getString("name"));
				u.setCreateDate(rs.getTimestamp("createDate"));
				u.setModDate(rs.getTimestamp("modDate"));
				result.addElement(u);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return result;
	}

	/**
	 * Sucht nach der höchsten U_ID um diese um eins zu erhöhen und als neue U_ID zu
	 * nutzen Befüllt T_User mit U_ID, name, createDate und modDate Ein User Objekt
	 * wird zurückgegeben
	 *
	 * @param user übergebenes User Objekt mit allen Attributen
	 * @return Ein vollständiges User Objekt
	 */
	public User insert(User user) {
		Connection con = DBConnection.connection();
		Timestamp ts = new Timestamp(System.currentTimeMillis());

		String u = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ts);

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT MAX(U_ID) AS maxsid FROM T_User");
			if (rs.next()) {

				user.setId(rs.getInt("maxsid") + 1);
				Statement stmt2 = con.createStatement();
				stmt2.executeUpdate("INSERT INTO T_User (U_ID, name, createDate, modDate)" + " VALUES (" + user.getId()
						+ ", " + user.getName() + ", '" + u + "', '" + u + "')");

				return user;

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return user;
		}
		return user;
	}

	/**
	 * Befüllt T_User mit U_ID, name, createDate und modDate, falls sich was
	 * geändert hat Ein User Objekt wird zurückgegeben
	 * 
	 * @return Ein vollständiges User Objekt
	 */

	public User update(User user) {
		Connection con = DBConnection.connection();
		Timestamp ts = new Timestamp(System.currentTimeMillis());

		String u = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ts);

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("UPDATE T_User SET U_ID =" + user.getId() + ", creator =" + user.getName()
// +"', createDate="
// + user.getCreateDate()
					+ "', modDate='" + u + "' WHERE U_ID=" + user.getId());
		} catch (SQLException e2) {
			e2.printStackTrace();
			return user;
		}
		return user;
	}

	/**
	 * Entfernt alles aus T_User wo die U_ID der ID des übergebenen Objekts
	 * entspricht
	 *
	 * @param user übergebenes User Objekt mit Attribut U_ID
	 */
	public void delete(User user) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM T_User WHERE U_ID =" + user.getId());
		}

		catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
}