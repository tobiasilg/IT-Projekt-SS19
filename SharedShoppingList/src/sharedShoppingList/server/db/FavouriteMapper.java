package sharedShoppingList.server.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
	
	public Favourite createFavourite(Group group, ListEntry listEntry) {
		Connection con = DBConnection.connection();
		
		String sql="INSERT INTO favourite (groupsId, listEntryId) VALUES ("+ group.getId()+"," +listEntry.getId()+")";
	
		
		try {
			Statement st = con.createStatement();
			st.executeUpdate(sql);
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		/*
		 * Noch Klärungsbedarf
		 */
		
		return listEntry;
	}

}
