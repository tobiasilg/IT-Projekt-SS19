package sharedShoppingList.server.db;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.util.Vector;

import sharedShoppingList.shared.bo.User;

/**
* Dieser Mapper ist für alle Datenbankvorgänge - also der Informationsabfrage aus der DB, sowie der Datenablage in der DB - des BOs User verantwortlich.
* Er ermöglicht die Durchführung aller "CRUD-Vorgänge". Dazu bietet er verschiedene Methoden.
* Author dieser Klasse: @author Tobias Ilg
*/



public class UserMapper {
	
	private static UserMapper userMapper = null;
	
    /*Der Konstruktur duch "protected" dafür, dass nur eine Instanz existieren kann*/
	protected UserMapper() {}
	
	public static UserMapper userMapper() {
		if (userMapper == null) {
			userMapper = new UserMapper();
		}
		return userMapper;
	}
	
	/* Alle weiteren Methoden sind in 4 Blöcke eingeteilt (Create, Read, Updated, Delete)*/
	
	
/* CREATE (insert) - Dieser Block verfügt nur über eine Methode, die für alle Neueinträge verantwortlich ist*/
	
	public void insert (User user) {
		Connection con = DBConnection.connection();
		
		String sql= "insert into user (id, name, username, gmail, groupid, createDate, modDate) values ("+user.getId() + "," + user.getName() + "," + user.getUserName() + "," + user.getGmail()+ "," + user.getGroupId()+ "," + user.getCreateDate()+ ","+ user.getModDate() +")";  
		
	    try {
	    	
	    	Statement stmt = con.createStatement();
	    	stmt.executeUpdate(sql);	 
	      
	    }
	    catch (SQLException e) {
	      e.printStackTrace();
	    }
	}
	
	
/* READ (find) - Dieser Block sorgt für Ausgabe bestehender Datensätze.*/
	
	/* find all */
	public Vector<User> findAll(){
		Connection con = DBConnection.connection();
		String sql = "select * from user order by name";
		
		Vector<User> users= new Vector<User>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {

				User user = new User();

				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
                user.setUserName(rs.getString("username"));
                user.setGmail(rs.getString("gmail"));
                user.setGroupId(rs.getInt("groupid"));
				user.setCreateDate(rs.getTimestamp("createDate"));
				user.setModDate(rs.getTimestamp("modDate"));
				
				users.addElement(user);
			}

		}catch(SQLException e){
			e.printStackTrace();
		}
		return users;
	}
	
	/* find by id */
	public User findById(int id) {
		Connection con = DBConnection.connection();
		User user = new User();
		String sql="select * from user where id=" + id;
			
		try {

				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);

				if (rs.next()) {
					
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
                user.setUserName(rs.getString("username"));
                user.setGmail(rs.getString("gmail"));
                user.setGroupId(rs.getInt("groupid"));
				user.setCreateDate(rs.getTimestamp("createDate"));
				user.setModDate(rs.getTimestamp("modDate"));
					
				}

			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			return user;
		}
	
	
/*UPDATE*/
	
	public User update(User user) {
		Connection con = DBConnection.connection();
		String sql="UPDATE user " + "SET name=\"" + user.getName() + "\", " + "SET username=\"" + user.getUserName() + "\", " + "SET gmail=\"" + user.getGmail() + "\", " + "SET groupid=\"" + user.getGroupId() + "\", " + "WHERE id=" + user.getId();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	

/*DELETE*/
	
	public void delete (int id) {
	Connection con = DBConnection.connection();
		
		String sql= "delete from user where id=" + id +")";
		
	    try {
	    	
	    	Statement stmt = con.createStatement();
	    	stmt.executeUpdate(sql);	 
	      
	    }
	    catch (SQLException e) {
	      e.printStackTrace();
	    }
		
	}
	
} 