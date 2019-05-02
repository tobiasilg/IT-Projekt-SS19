package sharedShoppingList.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import sharedShoppingList.shared.bo.Article;
import sharedShoppingList.shared.bo.ShoppingList;

/**
 * Mapper Klasse für </code>ShoppingList</code> Objekte.
 * Diese umfasst  Methoden um ListMapper Objekte zu erstellen, zu suchen, zu  ändern und zu löschen.
 * Das Mapping funktioniert dabei bidirektional. Es können Objekte in DB-Strukturen und DB-Stukturen in Objekte umgewandelt werden.
 * 
 * @author Nico Weiler
 */

public class ListMapper {
	
	//Speicherung der Instanz dieser Mapper Klasse
	
	private static ListMapper listMapper = null;
	
	/**
	 * Geschützter Konstrukter verhindert weitere Instanzierungen von ListMapper.
	 * Somit kann nur eine Instanz der Klasse ListMapper angelegt werden.
	 */
	protected ListMapper() {
	}

	
	/**
	 * Sicherstellung der Singleton-Eigenschaft der Mapperklasse.
	 * 
	 * @return Gibt den List Mapper zurück.
	 */
	public static ListMapper listMapper() {
		if (listMapper == null) {
			listMapper = new ListMapper();
		}

		return listMapper;
	}
	/*
	 * Methode zur Findung einer Einkaufsliste anhand der ID
	 * 
	 */
	
	public ShoppingList findById(int id) {
	Connection con = DBConnection.connection();
		
		String sql="select * from shoppingList where id=" + id;
		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {

				ShoppingList shoppinglist = new ShoppingList();
				shoppinglist.setId(rs.getInt("id"));
				shoppinglist.setName(rs.getString("name"));
				shoppinglist.setCreateDate(rs.getTimestamp("createDate"));
				
			
				return shoppinglist;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	/*
	 * Methode zur Auflistung aller vorhandenen Einkaufslisten
	 */
	
	public Vector<ShoppingList> findAll(){
		Connection con = DBConnection.connection();
		String sql = "select * from shoppingList order by name";
		
		Vector<ShoppingList> result= new Vector<ShoppingList>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				ShoppingList shoppinglist = new ShoppingList();
				shoppinglist.setId(rs.getInt("id"));
				shoppinglist.setName(rs.getString("name"));
				shoppinglist.setCreateDate(rs.getTimestamp("createDate"));
				
				result.addElement(shoppinglist);
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		return result;
		
	}
	/*
	 * Methode zur Auflistung aller Einkaufslisten eines bestimmten Benutzers
	 */
	
	public Vector<ShoppingList> findAllByCurrentUser(User user){
		Connection con = DBConnection.connection();
		String sql = "select * from shoppingList where id=" + user.getId;
		
		Vector<ShoppingList> result= new Vector<ShoppingList>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				ShoppingList shoppinglist = new ShoppingList();
				shoppinglist.setId(rs.getInt("id"));
				shoppinglist.setName(rs.getString("name"));
				shoppinglist.setCreateDate(rs.getTimestamp("createDate"));
				
				result.addElement(shoppinglist);
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		return result;
	}
	
	/*
	 * Methode zum Löschen einer Einkaufsliste aus der Datenbank
	 */
	
	public void delete (int id) {
	Connection con = DBConnection.connection();
		
		String sql= "delete from shoppingList where id=" + id +")";
		
	    try {
	    	
	    	Statement stmt = con.createStatement();
	    	stmt.executeUpdate(sql);	 
	      
	    }
	    catch (SQLException e2) {
	      e2.printStackTrace();
	    }
		
	}
	
	/*
	 * Methode um eine neue Einkaufsliste der Datenbank hinzuzufügen
	 */
	
	public void insert (ShoppingList shoppinglist) {
		Connection con = DBConnection.connection();
		
		String sql= "insert into shoppingList values ("+shoppinglist.getId() + "," + shoppinglist.getName()+ "," + shoppinglist.getCreateDate()+ ")";
		
	    try {
	    	
	    	Statement stmt = con.createStatement();
	    	stmt.executeUpdate(sql);	 
	      
	    }
	    catch (SQLException e2) {
	      e2.printStackTrace();
	    }
	}

}
