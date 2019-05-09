package sharedShoppingList.shared.bo;

import java.sql.Timestamp;

/**
 * Diese Klasse erweitert die allgemeine BusinessObject-Klasse um Nutzerspezifische Funktionen und Parameter.
 * @author Tobias Ilg
 */

public class User extends BusinessObject{
	
	private int id;
	private String name;
	
	public User() {
		super();	
	}



	public User getUser() {
		return user;
	}
    
	public void deleteUser() {
		return null;
	}


	public String getName() {
		return name;
	}

	public void setName(String userName) {
		this.name = userName;
	}

    public int getUserId() {
		return id;
	}

	public void setUserId(int userId) {
		this.id = userId;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}
	
	

}
