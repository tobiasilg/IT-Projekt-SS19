package sharedShoppingList.shared.bo;

import java.sql.Timestamp;

/**
 * Diese Klasse erweitert die allgemeine BusinessObject-Klasse um nutzerspezifische Funktionen und Parameter.
 * @author Tobias Ilg, Leon Seiz
 */

public class Group extends BusinessObject{
	
	private static final long serialVersionUID = 1L;
	
	private int groupid;
	private String groupname;
	
	public Group() {
		super();	
	}
	
	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}	
	
}
