package sharedShoppingList.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import sharedShoppingList.shared.bo.ListEntry;
import sharedShoppingList.shared.bo.ShoppingList;
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
		 * Geschützter Konstrukter verhindert weitere Instanzierungen von ArticleMapper.
		 * Somit kann nur eine Instanz der Klasse ArticleMapper angelegt werden.
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
			
			String sql="select * from listEntry where id=" + id;
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
		 * Methode zur Auflistung aller Listeneinträge
		 * @return: Vector mit allen Einträgen wird zurückgegeben
		 */
		
		public Vector<ListEntry>findAllListEntries(){
			Connection con = DBConnection.connection();
			String sql = "select * from article order by name";
			
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
			String sql = "select * from listEntry where userid=" + user.getId();
			
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
		 * Methode zum Löschen eines Artikels aus der Datenbank
		 */
		
		public void delete (int id) {
		Connection con = DBConnection.connection();
			
			String sql= "delete from listEntry where id=" + id +")";
			
		    try {
		    	
		    	Statement stmt = con.createStatement();
		    	stmt.executeUpdate(sql);	 
		      
		    }
		    catch (SQLException e2) {
		      e2.printStackTrace();
		    }
			
		}
		
		/*
		 * Methode um einen neuen Artikel der Datenbank hinzuzufügen
		 */
		
		public void insert (ListEntry listEntry) {
			Connection con = DBConnection.connection();
			
			String sql= "insert into article (id, name, createDate, modDate, amount, checked, userid, storeid, articleid, shoppinglistid) values ("+listEntry.getId() + "," + listEntry.getName()+ ","+ listEntry.getCreateDate()+ "," + listEntry.getModDate()+","+listEntry.getAmount()+","+listEntry.isChecked()+","+ listEntry.getUserId()+","+listEntry.getStoreId()+","+ listEntry.getArticleId()+","+listEntry.getShoppinglistId()+ ")";
			
		    try {
		    	
		    	Statement stmt = con.createStatement();
		    	stmt.executeUpdate(sql);	 
		      
		    }
		    catch (SQLException e2) {
		      e2.printStackTrace();
		    }
		}
}

