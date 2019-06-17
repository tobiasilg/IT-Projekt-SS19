package sharedShoppingList.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Vector;

import sharedShoppingList.shared.bo.Article;
import sharedShoppingList.shared.bo.ListEntry;
import sharedShoppingList.shared.bo.ShoppingList;
import sharedShoppingList.shared.bo.Store;
import sharedShoppingList.shared.bo.User;

/**
 * Mapper Klasse für </code>ListEntry</code> Objekte.
 * Diese umfasst  Methoden um ListEntry Objekte zu erstellen, zu suchen, zu  ändern und zu löschen.
 * Das Mapping funktioniert dabei bidirektional. Es können Objekte in DB-Strukturen und DB-Stukturen in Objekte umgewandelt werden.
 * 
 * @author Nico Weiler
 */

public class ListEntryMapper {

	
		
		//Speicherung der Instanz dieser Mapper Klasse
		
		private static ListEntryMapper listEntryMapper = null;
		
		/**
		 * Geschützter Konstrukter verhindert weitere Instanzierungen von ListEntryMapper.
		 * Somit kann nur eine Instanz der Klasse ListEntryMapper angelegt werden.
		 */
		public ListEntryMapper() {
		}

		
		/**
		 * Sicherstellung der Singleton-Eigenschaft der Mapperklasse.
		 * 
		 * @return Gibt den ListEntry Mapper zurück.
		 */
		public static ListEntryMapper listEntryMapper() {
			if (listEntryMapper == null) {
				listEntryMapper = new ListEntryMapper();
			}

			return listEntryMapper;
		}
		
		/*
		 * Methode zur Findung eines Listeneintrags anhand der ID
		 * 
		 */
		
		public ListEntry findByID(int id) {
			Connection con = DBConnection.connection();
			
			String sql="select * from listentry where id=" + id;
			try {

				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				if (rs.next()) {

					ListEntry listEntry = new ListEntry();
					listEntry.setId(rs.getInt("id"));
					listEntry.setName(rs.getString("name"));
					listEntry.setCreateDate(rs.getTimestamp("createDate"));
					listEntry.setAmount(rs.getDouble("amount"));
					listEntry.setChecked(rs.getBoolean("checked"));
					listEntry.setArticleId(rs.getInt("articleid"));
					listEntry.setUserId(rs.getInt("userid"));
					listEntry.setStoreId(rs.getInt("storeid"));
					listEntry.setShoppinglistId(rs.getInt("shoppinglistid"));
					return listEntry;
				}

			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			return null;
		}
		
		/*
		 * Methode zur Findung eines Listeneintrags anhand eines zugewiesenen Artikels
		 * 
		 */
		
		public Vector<ListEntry> findByArticle(Article article) {
			Connection con = DBConnection.connection();
			
			String sql="select * from listentry where articleid=" + article.getId();
			
			Vector<ListEntry> result= new Vector<ListEntry>();
			try {
				

				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {

					ListEntry listEntry = new ListEntry();
					listEntry.setId(rs.getInt("id"));
					listEntry.setName(rs.getString("name"));
					listEntry.setCreateDate(rs.getTimestamp("createDate"));
					listEntry.setAmount(rs.getDouble("amount"));
					listEntry.setChecked(rs.getBoolean("checked"));
					listEntry.setArticleId(rs.getInt("articleid"));
					listEntry.setUserId(rs.getInt("userid"));
					listEntry.setStoreId(rs.getInt("storeid"));
					listEntry.setShoppinglistId(rs.getInt("shoppinglistid"));
					
					result.addElement(listEntry);
				}

			} catch (SQLException e) {
				e.printStackTrace();
				
			}
			return result;
		}
		
		/**
		 * 
		 * Methode zur Findung eines Listeneintrags innerhalb der jeweiligen Shoppingliste
		 * Wird benötigt, um z.B. eine Löschweitergabe zu implementieren 
		 * Wenn Shoppingliste gelöscht wird sollen auch alle zugehörigen ListEntries gelöscht werden
		 * @author Nico Weiler
		 * @param sl
		 * @return result vektor der ListEntries
		 */
		
		public Vector<ListEntry> findAllByShoppingList(ShoppingList sl) {
			Connection con = DBConnection.connection();
			
			String sql="select * from listentry where shoppinglistid=" + sl.getId();
			
			Vector<ListEntry> result= new Vector<ListEntry>();
			try {
				

				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {

					ListEntry listEntry = new ListEntry();
					listEntry.setId(rs.getInt("id"));
					listEntry.setName(rs.getString("name"));
					listEntry.setCreateDate(rs.getTimestamp("createDate"));
					listEntry.setAmount(rs.getDouble("amount"));
					listEntry.setChecked(rs.getBoolean("checked"));
					listEntry.setArticleId(rs.getInt("articleid"));
					listEntry.setUserId(rs.getInt("userid"));
					listEntry.setStoreId(rs.getInt("storeid"));
					listEntry.setShoppinglistId(rs.getInt("shoppinglistid"));
					
					result.addElement(listEntry);
				}

			} catch (SQLException e) {
				e.printStackTrace();
				
			}
			return result;
		}
		
		/*
		 * Methode zur Findung eines Listeneintrags anhand eines zugewiesenen Händlers
		 * 
		 */
		
		public Vector<ListEntry> findByStore(Store store) {
			Connection con = DBConnection.connection();
			
			String sql="select * from listentry where storeid=" + store.getId();
			
			Vector<ListEntry> result= new Vector<ListEntry>();
			try {
				

				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {

					ListEntry listEntry = new ListEntry();
					listEntry.setId(rs.getInt("id"));
					listEntry.setName(rs.getString("name"));
					listEntry.setCreateDate(rs.getTimestamp("createDate"));
					listEntry.setAmount(rs.getDouble("amount"));
					listEntry.setChecked(rs.getBoolean("checked"));
					listEntry.setArticleId(rs.getInt("articleid"));
					listEntry.setUserId(rs.getInt("userid"));
					listEntry.setStoreId(rs.getInt("storeid"));
					listEntry.setShoppinglistId(rs.getInt("shoppinglistid"));
					
					result.addElement(listEntry);
				}

			} catch (SQLException e) {
				e.printStackTrace();
				
			}
			return result;
		}
		
		
		/*
		 * Methode zur Auflistung aller Listeneinträge
		 * @return: Vector mit allen Einträgen wird zurückgegeben
		 */
		
		public Vector<ListEntry>findAllListEntries(){
			Connection con = DBConnection.connection();
			String sql = "select * from listentry order by name";
			
			Vector<ListEntry> result= new Vector<ListEntry>();
			try {
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				while (rs.next()) {
					ListEntry listEntry = new ListEntry();
					listEntry.setId(rs.getInt("id"));
					listEntry.setName(rs.getString("name"));
					listEntry.setCreateDate(rs.getTimestamp("createDate"));
					listEntry.setCreateDate(rs.getTimestamp("modDate"));
					listEntry.setAmount(rs.getDouble("amount"));
					listEntry.setChecked(rs.getBoolean("checked"));
					listEntry.setArticleId(rs.getInt("articleid"));
					listEntry.setUserId(rs.getInt("userid"));
					listEntry.setStoreId(rs.getInt("storeid"));
					listEntry.setShoppinglistId(rs.getInt("shoppinglistid"));
					
					result.addElement(listEntry);
				}
			}catch(SQLException ex){
				ex.printStackTrace();
			}
			return result;
			
			
		}
		/*
		 * Methode zur Auflistung aller Einträge einer Einkaufsliste
		 */
		
		public Vector<ListEntry> findAllByCurrentUser(User user){
			Connection con = DBConnection.connection();
			String sql = "select * from listentry where userid=" + user.getId();
			
			Vector<ListEntry> result= new Vector<ListEntry>();
			try {
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				while (rs.next()) {
					ListEntry listEntry = new ListEntry();
					listEntry.setId(rs.getInt("id"));
					listEntry.setName(rs.getString("name"));
					listEntry.setCreateDate(rs.getTimestamp("createDate"));
					listEntry.setCreateDate(rs.getTimestamp("modDate"));
					listEntry.setAmount(rs.getDouble("amount"));
					listEntry.setChecked(rs.getBoolean("checked"));
					listEntry.setArticleId(rs.getInt("articleid"));
					listEntry.setUserId(rs.getInt("userid"));
					listEntry.setStoreId(rs.getInt("storeid"));
					listEntry.setShoppinglistId(rs.getInt("shoppinglistid"));
					
					result.addElement(listEntry);
				}
			}catch(SQLException ex){
				ex.printStackTrace();
			}
			return result;
		}
		
		/*
		 * Methode zum Löschen eines Listeneintrags aus der Datenbank
		 */
		
		public void delete (ListEntry listEntry) {
		Connection con = DBConnection.connection();
			
			String sql= "delete from listentry where id=" + listEntry.getId() +")";
			
		    try {
		    	
		    	Statement stmt = con.createStatement();
		    	stmt.executeUpdate(sql);	 
		      
		    }
		    catch (SQLException e2) {
		      e2.printStackTrace();
		    }
			
		}
		
		/*
		 * Methode um einen neuen Listeneintrag der Datenbank hinzuzufügen
		 */
		
		public ListEntry insert (ListEntry listEntry) {
			Connection con = DBConnection.connection();
			
			String sql= "insert into listentry (name, createDate, modDate, amount, checked, userid, storeid, articleid, shoppinglistid) values ('"+ listEntry.getName()+ "',"+ listEntry.getCreateDate()+ "," + listEntry.getModDate()+","+listEntry.getAmount()+","+listEntry.isChecked()+","+ listEntry.getUserId()+","+listEntry.getStoreId()+","+ listEntry.getArticleId()+","+listEntry.getShoppinglistId()+ ")";
			
		    try {
		    	
		    	Statement stmt = con.createStatement();
		    	stmt.executeUpdate(sql);	 
		      
		    }
		    catch (SQLException e2) {
		      e2.printStackTrace();
		    }
		    return listEntry;
		}
		
		/*
		 * Methode um einen Listeneintrag zu editieren
		 */
		
		public ListEntry update(ListEntry listentry) {
			Connection con = DBConnection.connection();
		
			
			String sql= "UPDATE listentry SET articleid= "+ listentry.getArticleId()+", amount='"+listentry.getAmount()+"', storeid="+listentry.getStoreId()+", userid="+listentry.getUserId()+", checked="+listentry.isChecked()+" WHERE id= "+ listentry.getId();

			try {
				Statement stmt = con.createStatement();
				stmt.executeUpdate(sql);

				

			} catch (SQLException e) {
				e.printStackTrace();
			}

			
			return listentry;
		}


		public List<ListEntry> findByStoreAndDate(Store store, Timestamp beginningDate) {
			Connection con = DBConnection.connection();
			/**
			 * TODO: buydate anlegen in db
			 * check .getDate Methode
			 */
			String sql = "SELECT * FROM listentry";
			if(store != null && beginningDate != null) {
				sql += " WHERE storeid = " + store.getId() + " AND buydate >= " + beginningDate.getDate();
			}
			if(store == null && beginningDate != null) {
				sql += " WHERE buydate >= " + beginningDate.getDate();
			} 
			if(store != null && beginningDate == null) {
				sql += " WHERE storeid = " + store.getId();
			}
			Vector<ListEntry> result= new Vector<ListEntry>();
			try {
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				while (rs.next()) {
					ListEntry listEntry = createEntry(rs);
					
					result.addElement(listEntry);
				}
			}catch(SQLException ex){
				ex.printStackTrace();
			}
			return result;
		}
		
		/**
		 * Erstellt aus einem ResultSet Eintrag einen ListEntry
		 * Methode ausgelagert
		 * @param rs
		 * @return
		 * @throws SQLException
		 */


		private ListEntry createEntry(ResultSet rs) throws SQLException {
			ListEntry listEntry = new ListEntry();
			listEntry.setId(rs.getInt("id"));
			listEntry.setName(rs.getString("name"));
			listEntry.setCreateDate(rs.getTimestamp("createDate"));
			listEntry.setCreateDate(rs.getTimestamp("modDate"));
			listEntry.setAmount(rs.getDouble("amount"));
			listEntry.setChecked(rs.getBoolean("checked"));
			listEntry.setArticleId(rs.getInt("articleid"));
			listEntry.setUserId(rs.getInt("userid"));
			listEntry.setStoreId(rs.getInt("storeid"));
			listEntry.setShoppinglistId(rs.getInt("shoppinglistid"));
			return listEntry;
		}
}

