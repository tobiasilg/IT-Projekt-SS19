package sharedShoppingList.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import sharedShoppingList.shared.bo.Article;
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
		String sql = "SELECT f.*, a.id AS articleId, l.amount AS Menge, a.name AS Artikelname, a.unit AS Einheit FROM favourite AS f"
				+ " LEFT JOIN listentry AS l ON f.listentryid = l.id"
				+ " LEFT JOIN article AS a ON a.id = l.articleid";
		
		Vector <Favourite> result = new Vector <Favourite>();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			while (rs.next()) {
				Favourite favourite = new Favourite();
				favourite.setId(rs.getInt("id"));
				favourite.setGroupsId(rs.getInt("groupId"));
				favourite.setListEntryId(rs.getInt("listEntryId"));
				
		
				
				Article article = new Article();
				article.setName(rs.getString("Artikelname"));
				article.setId(rs.getInt("articleid"));
				
				ListEntry listEntry = new ListEntry();
				
				listEntry.setAmount(rs.getInt("Menge"));
				listEntry.setId(rs.getInt("listEntryId"));
								
				
				listEntry.setArticle(article);
				favourite.setListEntry(listEntry);
				
				
				result.addElement(favourite);
			}
			
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return result;
		
	}
	
	
	public Vector <Favourite> findFavouritesByGroupId (int groupId) {
		
		Connection con = DBConnection.connection();
		String sql = "SELECT f.*, a.id AS articleId, l.amount AS Menge, a.name AS Artikelname, a.unit AS Einheit FROM favourite AS f"
				+ " LEFT JOIN listentry AS l ON f.listentryid = l.id"
				+ " LEFT JOIN article AS a ON a.id = l.articleid WHERE f.groupid =" + groupId;
		
		Vector <Favourite> result = new Vector <Favourite> ();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			while (rs.next()) {
				Favourite favourite = new Favourite();
				favourite.setId(rs.getInt("id"));
				favourite.setGroupsId(rs.getInt("groupsId"));
				favourite.setListEntryId(rs.getInt("listEntryId"));
				
				Article article = new Article();
				article.setId(rs.getInt("id"));
				article.setName(rs.getString("Artikelname"));
				article.setUnit(rs.getString("unit"));
				
				ListEntry listentry = new ListEntry ();
				listentry.setAmount(rs.getDouble("Menge"));
				listentry.setUserId(rs.getInt("userId"));
				
				/*
				 * Hier eventuell im SQL Statement alias setzen für "name"?
				 */
				listentry.setName(rs.getString("name"));
				listentry.setStoreId(rs.getInt("storeid"));
				
				listentry.setArticle(article);
				favourite.setListEntry(listentry);
				
				result.addElement(favourite);
				}
				
			} catch (SQLException ex) {
				ex.printStackTrace();
				
			}
			return result;
		
	}

}
