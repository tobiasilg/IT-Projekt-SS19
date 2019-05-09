package sharedShoppingList.shared.bo;

import java.sql.Timestamp;

/**
 * Diese Klasse erweitert die allgemeine BusinessObject-Klasse um Nutzerspezifische Funktionen und Parameter.
 * @author Tobias Ilg
 */

public class Group extends BusinessObject{
	
	private int id;
	private String name;
	
	public Group() {
		super();	
	}



	public Group getGroup() {
		return group;
	}
    
	public void deleteGroup() {
		return null;
	}


	public String getName() {
		return name;
	}

	public void setName(String groupName) {
		this.name = groupName;
	}

    public int getGroupId() {
		return id;
	}

	public void setGroupId(int groupId) {
		this.id = groupId;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}
	
	
	

}
