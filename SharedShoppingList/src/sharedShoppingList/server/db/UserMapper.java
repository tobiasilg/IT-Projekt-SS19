package sharedShoppingList.server.db;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Vector;

import sharedShoppingList.shared.bo.Group;
import sharedShoppingList.shared.bo.User;

/**
* Dieser Mapper ist für alle Datenbankvorgänge - also der Informationsabfrage aus der DB, sowie der Datenablage in der DB - des BOs User verantwortlich.
* Er ermöglicht die Durchführung aller "CRUD-Vorgänge". Dazu bietet er verschiedene Methoden.
* @author Tobias Ilg
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
	
	public User insert (User user) {
		Connection con = DBConnection.connection();
		
		String sql= "INSERT INTO user (name, username, gmail) VALUES ('" + user.getName() + "','" + user.getUsername() + "','" + user.getGmail()+ "')";
		
	    try {
	    	
	    	/*
	    	 * Einstellung dass autoincremented ID's zureuckgeliefert werden
	    	 */
	    	
	    	PreparedStatement stmt = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
	    	int affectedRows = stmt.executeUpdate();

	        if (affectedRows == 0) {
	            throw new SQLException("Creating user failed, no rows affected.");
	        }
	        
	        
	        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                user.setId(generatedKeys.getInt(1)); //index 1 = id column
	            }
	            else {
	                throw new SQLException("Creating user failed, no ID obtained.");
	            }
	        }	 
	      
	    }
	    catch (SQLException e) {
	      e.printStackTrace();
	    }
	    
	    return user;
	}
	
	
/* READ (find) - Dieser Block sorgt für Ausgabe bestehender Datensätze.*/
	
	/* find all */
	public Vector<User> findAll(){
		Connection con = DBConnection.connection();
		String sql = "SELECT * FROM user ORDER BY name";
		
		Vector<User> users= new Vector<User>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {

				User user = new User();

				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
                user.setUsername(rs.getString("username"));
                user.setGmail(rs.getString("gmail"));
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
		String sql="SELECT * FROM user WHERE id=" + id;
			
		try {

				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);

				if (rs.next()) {
					
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
                user.setUsername(rs.getString("username"));
                user.setGmail(rs.getString("gmail"));
				user.setCreateDate(rs.getTimestamp("createDate"));
				user.setModDate(rs.getTimestamp("modDate"));
					
				}

			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			return user;
		}
	
	/* find by gmail 
	 * 
	 *  */
	public User findByGmail(String gmail) {
		
		Connection con = DBConnection.connection();
		String sql="SELECT * FROM user WHERE gmail='" + gmail+"'";
			
		try {

				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				

				if (rs.next()) {
				User user = new User();

				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
                user.setUsername(rs.getString("username"));
                user.setGmail(rs.getString("gmail"));
				user.setCreateDate(rs.getTimestamp("createDate"));
				user.setModDate(rs.getTimestamp("modDate"));
				
				return user;
				}

			} catch (SQLException e) {
				e.printStackTrace();
				
			}
		return null;
		}
	
	/* find by name */
	public User findByName(String name) {
		
		Connection con = DBConnection.connection();
		User user = new User();
		
		// DISTINCT sorg hier dafür, dass nur ein Nutzer-Objekt zurückgegeben wird
		
		String sql="SELECT DISTINCT * FROM user WHERE name=" + name;
			
		try {

				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);

				if (rs.next()) {
					
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
                user.setUsername(rs.getString("username"));
                user.setGmail(rs.getString("gmail"));
				user.setCreateDate(rs.getTimestamp("createDate"));
				user.setModDate(rs.getTimestamp("modDate"));
					
				}

			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			return user;
		}
	
	/**
	 * Methode gibt alle User einer bestimmten Gruppe zuurück
	 * @param id
	 * @return
	 * @author Nico Weiler
	 */
	
	public Vector <User> findByGroup(Group group) {
	
		Connection con = DBConnection.connection();
		String sql = "SELECT user.id, user.name, user.username, "
				+"user.gmail, user.createDate, user.modDate "
				+"FROM membership INNER JOIN user "
				+"ON membership.userid = user.id "
				+"WHERE membership.groupid = " + group.getId();
	
		
		Vector<User> users= new Vector<User>();
		
		try {
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {

				User user = new User();

				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
                user.setUsername(rs.getString("username"));
                user.setGmail(rs.getString("gmail"));
				user.setCreateDate(rs.getTimestamp("createDate"));
				user.setModDate(rs.getTimestamp("modDate"));
				users.addElement(user);
				
				
			}

			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			return users;
		}
	
	
	
	
	
/*UPDATE*/
	
	public User update(User user) {
		Connection con = DBConnection.connection();
		String sql= "UPDATE user SET name= '"+ user.getName()+"', username='"+user.getUsername()+"' WHERE id= "+ user.getId();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	

/*DELETE*/
	
	public void delete (User user) {
	Connection con = DBConnection.connection();
		
		String sql= "DELETE FROM user WHERE id=" + user.getId();
		
	    try {
	    	
	    	Statement stmt = con.createStatement();
	    	stmt.executeUpdate(sql);	 
	      
	    }
	    catch (SQLException e) {
	      e.printStackTrace();
	    }
		
	}
	
	/**
	 * Löschen der Zuordnung zu einer bestimmten Gruppe
	 */
	
	public void deleteMembership (User user) {
		Connection con = DBConnection.connection();
			
			String sql= "DELETE FROM membership WHERE id=" + user.getId();
			
		    try {
		    	
		    	Statement stmt = con.createStatement();
		    	stmt.executeUpdate(sql);	 
		      
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }
			
		}
	
} 