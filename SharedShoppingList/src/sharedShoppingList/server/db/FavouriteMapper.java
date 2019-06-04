package sharedShoppingList.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import sharedShoppingList.shared.bo.Favourite;
import sharedShoppingList.shared.bo.Group;
import sharedShoppingList.shared.bo.ListEntry;

/**
 * Mapper Klasse fÃ¼r </code>Favourite</code> Objekte.
 * Diese umfasst  Methoden um FavouroteMapper Objekte zu erstellen, zu suchen, und zu lÃ¶schen.
 * 
 * @author Leon Seiz
 */

public class FavouriteMapper {
	
	/*
	 * Speicherung der instanz dieser Mapper Klasse
	 */
	
	private static FavouriteMapper favouriteMapper=null;
	
	/*
	 * Der geschütze Konstruktor verhindert weitere Instanziierungen von FavouriteMapper.
	 * Daher kann nur eine Instanz der Klasse FavouriteMapper angelegt werden
	 */
	
	public FavouriteMapper() {
		
	}
	
	/*
	 * Sicherstellung der Singleton-Eigenschaft der Mapperklasse.
	 * @return gibt dann den Favourite Mapper zurück
	 */
	
	public static FavouriteMapper favouriteMapper() {
		if (favouriteMapper == null) {
			favouriteMapper = new FavouriteMapper();
		}
		return favouriteMapper;
	}
	
	/*
	 * Methode zur 'Markerung' eines Favouriten
	 * 
	 */
	
	public Favourite createFavourite(Favourite favourite) {
		Connection con = DBConnection.connection();
		
		String sql="INSERT INTO favourite (groupsId, listEntryId) VALUES ("+ favourite.getGroupsId()+"," +favourite.getListEntryId()+")";
	
		
		try {
			Statement st = con.createStatement();
			st.executeUpdate(sql);
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return favourite;
	}
	public void deleteFavourite (Favourite favourite) {
		Connection con = DBConnection.connection();
		
		String sql ="DELETE FROM favourite WHERE id ="+ favourite.getId();
		
		try {
			
	    	/*
	    	 * Deactivate autoCommit for save insert in DATABASE
	    	 */
			
			con.setAutoCommit(false);
			
			Statement st = con.createStatement();
			st.executeUpdate(sql);
			
			con.commit();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public Vector <Favourite> findAllFavourites () {
		
		Connection con = DBConnection.connection();
		String sql = "SELECT * FROM favourite ORDER BY NAME";
		
		Vector <Favourite> result = new Vector <Favourite>();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			while (rs.next()) {
				Favourite favourite = new Favourite();
				favourite.setId(rs.getInt("id"));
				favourite.setGroupsId(rs.getInt("groupsId"));
				favourite.setListEntryId(rs.getInt("listEntryId"));
				
				result.addElement(favourite);
			}
			
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return result;
		
	}

}
