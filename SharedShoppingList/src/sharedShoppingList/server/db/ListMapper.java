package sharedShoppingList.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import sharedShoppingList.shared.bo.Article;
import sharedShoppingList.shared.bo.Group;
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
		
		String sql="select * from shoppinglist where id=" + id;
		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {

				ShoppingList shoppinglist = new ShoppingList();
				shoppinglist.setId(rs.getInt("id"));
				shoppinglist.setName(rs.getString("name"));
				shoppinglist.setCreateDate(rs.getTimestamp("createDate"));
				shoppinglist.setModDate(rs.getTimestamp("modDate"));
				shoppinglist.setGroupId(rs.getInt("groupid"));
				
			
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
		String sql = "select * from shoppinglist order by name";
		
		Vector<ShoppingList> result= new Vector<ShoppingList>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				ShoppingList shoppinglist = new ShoppingList();
				shoppinglist.setId(rs.getInt("id"));
				shoppinglist.setName(rs.getString("name"));
				shoppinglist.setCreateDate(rs.getTimestamp("createDate"));
				shoppinglist.setModDate(rs.getTimestamp("modDate"));
				shoppinglist.setGroupId(rs.getInt("groupid"));
				
				result.addElement(shoppinglist);
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		return result;
		
	}
	/*
	 * Methode zur Auflistung aller Einkaufslisten einer bestimmten Gruppe
	 */
	
	public Vector<ShoppingList> findAllByGroup(Group group){
		Connection con = DBConnection.connection();
		String sql = "select * from shoppinglist where groupid=" + group.getId();
		
		Vector<ShoppingList> result= new Vector<ShoppingList>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				ShoppingList shoppinglist = new ShoppingList();
				shoppinglist.setId(rs.getInt("id"));
				shoppinglist.setName(rs.getString("name"));
				shoppinglist.setCreateDate(rs.getTimestamp("createDate"));
				shoppinglist.setModDate(rs.getTimestamp("modDate"));
				shoppinglist.setGroupId(rs.getInt("groupid"));
				
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
	
	public void delete (ShoppingList shoppingList ) {
	Connection con = DBConnection.connection();
		
		String sql= "delete from shoppinglist where id=" + shoppingList.getId() +")";
		
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
		
		String sql= "insert into shoppinglist (id, name, createDate, modDate, groupid) values ("+shoppinglist.getId() + "," + shoppinglist.getName()+ "," + shoppinglist.getCreateDate()+ ","+ shoppinglist.getModDate()+","+shoppinglist.getGroupId()+ ")";
		
	    try {
	    	
	    	Statement stmt = con.createStatement();
	    	stmt.executeUpdate(sql);	 
	      
	    }
	    catch (SQLException e2) {
	      e2.printStackTrace();
	    }
	}
	
	/*
	 * Methode um eine Einkaufsliste zu editieren
	 */
	
	public ShoppingList update(ShoppingList shoppinglist) {
		Connection con = DBConnection.connection();
		String sql= "UPDATE shoppinglist SET name= '"+ shoppinglist.getName()+"' WHERE id= "+ shoppinglist.getId();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);

			

		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		return shoppinglist;
	}

}
