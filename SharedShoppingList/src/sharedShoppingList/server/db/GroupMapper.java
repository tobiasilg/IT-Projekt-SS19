
package sharedShoppingList.server.db;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Vector;

import sharedShoppingList.shared.bo.Group;
import sharedShoppingList.shared.bo.User;

/**
* Dieser Mapper ist für alle Datenbankvorgänge - also der Informationsabfrage aus der DB, sowie der Datenablage in der DB - verantwortlich.
* Er ermöglicht die Durchführung aller "CRUD-Vorgänge". Dazu bietet er verschiedene Methoden (z.B. findByID vs findAll) an.
* Author dieser Klasse:
* @author Tobias Ilg
*/



public class GroupMapper {
	
	private static GroupMapper groupMapper = null;
	
    /*Der Konstruktur duch "protected" dafür, dass nur eine Instanz existieren kann*/
	public GroupMapper() {}
	
	public static GroupMapper groupMapper() {
		if (groupMapper == null) {
			groupMapper = new GroupMapper();
		}
		return groupMapper;
	}
	
	/* Alle weiteren Methoden sind in 4 Blöcke eingeteilt (Create, Read, Updated, Delete)*/
	
	
/* CREATE (insert) - Dieser Block verfügt nur über eine Methode, die für alle Neueinträge verantwortlich ist*/
	
	public Group insert (Group group) {
		Connection con = DBConnection.connection();

    
		String sql= "INSERT INTO einkaufsgruppe (name) VALUES ('"+ group.getName()+ "')";  

		
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
	                group.setId(generatedKeys.getInt(1)); //index 1 = id column
	            }
	            else {
	                throw new SQLException("Creating user failed, no ID obtained.");
	            }
	        }
	      
	    }
	    catch (SQLException e) {
	      e.printStackTrace();
	    }
	    
	    return group;
	}
	
	
/* READ (find) - Dieser Block sorgt für Ausgabe bestehender Datensätze. 
Dazu stehen zwei Methoden zur Verfügung. Zur Ausgabe aller Groupen eignet sich findAll.
Um eine spezifische Gruppe zu erhalten, bietet sich die Methode findById an.*/
	
	/* find all */
	public Vector<Group> findAll(){
		Connection con = DBConnection.connection();
		String sql = "select * from einkaufsgruppe";
		
		Vector<Group> groups= new Vector<Group>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {

				Group group = new Group();

				group.setId(rs.getInt("id"));
				group.setName(rs.getString("name"));
				group.setCreateDate(rs.getTimestamp("createDate"));
				group.setModDate(rs.getTimestamp("modDate"));
				
				groups.addElement(group);
			}

		}catch(SQLException e){
			e.printStackTrace();
		}
		return groups;
	}
	
	/* find by id */
	public Group findById(int id) {
		Connection con = DBConnection.connection();
		Group group = new Group();
		String sql="select * from einkaufsgruppe where id=" + id;
			
		try {

				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);

				if (rs.next()) {
					
					group.setId(rs.getInt("id"));
					group.setName(rs.getString("name"));
					group.setCreateDate(rs.getTimestamp("createDate"));
					group.setModDate(rs.getTimestamp("modDate"));
					
				}

			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			return group;
		}
	
	/**
	 * Gruppe pro User 
	 * @author Nico Weiler
	 * @param user
	 * @return group
	 */
	
	public Group findByUser(User user) {
		Connection con = DBConnection.connection();
		
		String sql="select * from einkaufsgruppe where id ="+ user.getGroupId();
		Group group = new Group();
		try {

				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);

				if (rs.next()) {
					
					group.setId(rs.getInt("id"));
					group.setName(rs.getString("name"));
					group.setCreateDate(rs.getTimestamp("createDate"));
					group.setModDate(rs.getTimestamp("modDate"));
					
				}

			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			return group;
		}
	
	
	
/*UPDATE*/
	
	public Group update(Group group) {
		Connection con = DBConnection.connection();
		String sql= "UPDATE einkaufsgruppe SET name= '"+ group.getName()+"' WHERE id= "+ group.getId();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return group;
	}
	

/*DELETE*/

	
	public void delete (Group group) {
	Connection con = DBConnection.connection();
		
		String sql="delete from einkaufsgruppe where id = " + group.getId();
		
	    try {
	    	
	    	Statement stmt = con.createStatement();
	    	stmt.executeUpdate(sql);	 
	      
	    }
	    catch (SQLException e) {
	      e.printStackTrace();
	    }
		
	}
	
	
} 

