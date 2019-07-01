package sharedShoppingList.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;

import sharedShoppingList.shared.bo.Article;
import sharedShoppingList.shared.bo.Favourite;
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
		protected ListEntryMapper() {
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
			
			String sql="SELECT * FROM listentry WHERE id=" + id;
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
			
			String sql="SELECT * FROM listentry WHERE articleid=" + article.getId();
			
			Vector<ListEntry> result= new Vector<ListEntry>();
			try {
				

				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {

					ListEntry listEntry = new ListEntry();
					listEntry.setId(rs.getInt("id"));
					listEntry.setName(rs.getString("name"));
					listEntry.setCreateDate(rs.getTimestamp("createDate"));
					listEntry.setModDate(rs.getTimestamp("modDate"));
					listEntry.setBuyDate(rs.getTimestamp("buyDate"));
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
			
			String sql="SELECT article.name, article.unit, listentry.id, listentry.checked, listentry.amount, user.name as username, store.name as storename, favourite.id as favid FROM listentry " + 
					"left join article " + 
					"	on listentry.articleid = article.id " + 
					"left join user " + 
					"	on listentry.userid = user.id " + 
					"left join store " + 
					"	on listentry.storeid = store.id " + 
					"left join favourite " + 
					"	on listentry.id=favourite.listentryid " + 
					"WHERE listentry.shoppinglistid= " + sl.getId();
			
			Vector<ListEntry> result= new Vector<ListEntry>();
			try {
				

				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {

					ListEntry listEntry = new ListEntry();
					listEntry.setChecked(rs.getBoolean("checked"));
					listEntry.setAmount(rs.getDouble("amount"));
					listEntry.setId(rs.getInt("id"));
					
					Article article = new Article();
					article.setName(rs.getString("name"));
					article.setUnit(rs.getString("unit"));
					
					User user=new User();
					user.setName(rs.getString("username"));
					
					Store store= new Store();
					store.setName(rs.getString("storename"));
					
					Favourite fav=new Favourite();
					fav.setId(rs.getInt("favid"));
					
					listEntry.setArticle(article);
					listEntry.setStore(store);
					listEntry.setUser(user);
					
					//setFav fehlt noch
					
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
			
			String sql="SELECT * FROM listentry WHERE storeid=" + store.getId();
			
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
			String sql = "SELECT * FROM listentry ORDER BY name";
			
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
			String sql = "SELECT * FROM listentry WHERE userid=" + user.getId();
			
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
			
			String sql= "DELETE FROM listentry WHERE id=" + listEntry.getId();
			
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
			
			String sql= "INSERT INTO listentry (name, amount, userid, storeid, articleid, shoppinglistid) VALUES ('"+ listEntry.getName()+ "',"+listEntry.getAmount()+","+ listEntry.getUserId()+","+listEntry.getStoreId()+","+ listEntry.getArticleId()+","+listEntry.getShoppinglistId()+")";
			
			try {
		    	/*
		    	 * Einstellung dass automatisch generierte  ID's aus der DB
		    	 * zureuckgeliefert werden.
		    	 * Somit kann ohne einen Refresh der Listeneintrag sofort angezeigt werden
		    	 */
		    	
		    	PreparedStatement stmt = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS); //ID wird aus der DB geholt
		    	int affectedRows = stmt.executeUpdate(); //Wurde etwas in die DB geschrieben?

		        if (affectedRows == 0) { //Kein neuer Eintrag in DB
		            throw new SQLException("Creating ListEntry failed, no rows affected.");
		        }
		        
		        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
		        	
		            if (generatedKeys.next()) {
		                listEntry.setId(generatedKeys.getInt(1)); //index 1 = id column
		            }
		            else {
		                throw new SQLException("Creating ListEntry failed, no ID obtained.");
		            }
		        }
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
			
			
			String date = null;
			if (listentry.getBuyDate() != null) {
				date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(listentry.getBuyDate());
			}
			
	
			String sql= "UPDATE listentry SET "
					+ "articleid= "+ listentry.getArticleId()+","
					+ " amount='"+listentry.getAmount()+"',"
					+ " storeid="+listentry.getStoreId()+","
					+ " userid="+listentry.getUserId()+","
					+ " checked="+listentry.isChecked()+","
					+ " buyDate ='" + date + "',"
					+ " WHERE id= "+ listentry.getId();
			
			try {
				Statement stmt = con.createStatement();
				stmt.executeUpdate(sql);

				

			} catch (SQLException e) {
				e.printStackTrace();
			}

			
			return listentry;
		}


		public List<ListEntry> findByStoreAndDate(Store store, Timestamp beginningDate, Timestamp endDate, int groupId) {
			Connection con = DBConnection.connection();
		
			
			String sql= "";
			if(store != null && beginningDate != null &&endDate!=null) {
				System.out.println("Store und Date");
				String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(beginningDate);
				String enddate = new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(endDate);
				 sql = "SELECT le.*, e.groupId FROM listentry AS le "
						+ "LEFT JOIN einkaufsgruppe AS e ON le.shoppinglistid = e.id "
						+ "WHERE le.buyDate BETWEEN '" + date + "' AND '" + enddate
						+  "AND e.groupId = " + groupId
						+ " AND storeid = " + store.getId();
				 System.out.println("Nach sql store and date");
			}
			if(store == null && beginningDate != null && endDate!=null) {
				String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(beginningDate);
				String enddate = new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(endDate);
				sql = "SELECT le.*, e.groupId FROM listentry AS le"
						+ "LEFT JOIN einkaufsgruppe AS e ON le.shoppinglistid = e.id"
						+ "WHERE le.buyDate BETWEEN '" + date + "' AND '" + enddate
						+  "AND e.groupId =" + groupId;
			} 
			if(store != null && beginningDate == null && endDate==null) {
				 sql = "SELECT le.*, e.groupId FROM listentry AS le"
							+ "LEFT JOIN einkaufsgruppe AS e ON le.shoppinglistid = e.id"
							+ "WHERE e.groupId =" + groupId
							+ "AND storeid =" + store.getId();
		
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
		
		// Methode um Listeneinträge eines Zeitraums ausgeben zu lassen
		
		public List<ListEntry> findByDate(Timestamp beginningDate) {
			Connection con = DBConnection.connection();
			/**
			 * TODO: buydate anlegen in db
			 * check .getDate Methode
			 */
			String sql = "SELECT * FROM listentry";
			if(beginningDate != null) {
				sql += " WHERE buydate >= " + beginningDate.getTime();
			}
			if(beginningDate != null) {
				sql += " WHERE buydate >= " + beginningDate.getTime();
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
			listEntry.setBuyDate(rs.getTimestamp("buyDate"));
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

