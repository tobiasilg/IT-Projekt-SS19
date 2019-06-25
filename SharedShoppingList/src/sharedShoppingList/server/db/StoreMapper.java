
package sharedShoppingList.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import sharedShoppingList.shared.bo.Store;

/**
 * Dieser Mapper ist für alle Datenbankvorgänge - also der Informationsabfrage
 * aus der DB, sowie der Datenablage in der DB - des BOs "Store" verantwortlich.
 * Er ermöglicht die Durchführung aller "CRUD-Vorgänge". Dazu bietet er
 * verschiedene Methoden an. Author dieser Klasse:
 * 
 * @author Tobias Ilg
 */

public class StoreMapper {

	private static StoreMapper storeMapper = null;

	public StoreMapper() {
	}

	public static StoreMapper storeMapper() {
		if (storeMapper == null) {
			storeMapper = new StoreMapper();
		}
		return storeMapper;
	}

	/*
	 * Alle weiteren Methoden sind in 4 Blöcke eingeteilt (Create, Read, Updated,
	 * Delete)
	 */

	/* CREATE (insert) */

	public Store insert(Store store) {
		Connection con = DBConnection.connection();

		String sql = "INSERT INTO store (name) VALUES ('" + store.getName() + "')";

		try {
			/*
	    	 * Einstellung dass autoincremented ID's zureuckgeliefert werden
	    	 */
			
			PreparedStatement stmt = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
	    	int affectedRows = stmt.executeUpdate();

	        if (affectedRows == 0) {
	            throw new SQLException("Creating store failed, no rows affected.");
	        }
	        
	        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                store.setId(generatedKeys.getInt(1)); //index 1 = id column
	            }
	            else {
	                throw new SQLException("Creating store failed, no ID obtained.");
	            }
	        }

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return store;
	}

	/* READ (find) */

	/* find all */
	public Vector<Store> findAll() {
		Connection con = DBConnection.connection();
		String sql = "SELECT * FROM store ORDER BY name";

		Vector<Store> stores = new Vector<Store>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {

				Store store = new Store();

				store.setId(rs.getInt("id"));
				store.setName(rs.getString("name"));
				store.setCreateDate(rs.getTimestamp("createDate"));
				store.setModDate(rs.getTimestamp("modDate"));

				stores.addElement(store);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stores;
	}

	/* find by id */
	public Store findById(int id) {
		Connection con = DBConnection.connection();
		Store store = new Store();
		String sql = "SELECT * FROM store WHERE id=" + id;

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {

				store.setId(rs.getInt("id"));
				store.setName(rs.getString("name"));
				store.setCreateDate(rs.getTimestamp("createDate"));
				store.setModDate(rs.getTimestamp("modDate"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return store;
	}

	/* UPDATE */

	public Store update(Store store) {
		Connection con = DBConnection.connection();

		// String sql = "UPDATE store " + "SET name=\" ' " + store.getName() + " ' \", "
		// + "WHERE id=" + store.getId();

		String sql = "UPDATE store SET name= '" + store.getName() + "' WHERE id= " + store.getId();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return store;
	}

	/* DELETE */

	public void delete(Store store) {
		Connection con = DBConnection.connection();

		String sql = "DELETE FROM store WHERE id=" + store.getId();

		try {

			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	
	/* Neue Methode für Pawel */
	@SuppressWarnings("null")
	public ArrayList<Store> getAllStores() {
		
		Connection con = DBConnection.connection();
		String sql = "SELECT * FROM store ORDER BY name";

		ArrayList<Store> stores = new ArrayList<Store>();
		
		
		try {
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {

				//Wie gehabt
				Store store = new Store();

				store.setId(rs.getInt("id"));
				store.setName(rs.getString("name"));
				store.setCreateDate(rs.getTimestamp("createDate"));
				store.setModDate(rs.getTimestamp("modDate"));

				stores.add(store);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stores;
	}

}
