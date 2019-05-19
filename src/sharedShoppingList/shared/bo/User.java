package sharedShoppingList.shared.bo;

import java.sql.Timestamp;

/**
 * Diese Klasse erweitert die allgemeine BusinessObject-Klasse um
 * Nutzerspezifische Funktionen und Parameter.
 * 
 * @author Tobias Ilg
 */

public class User extends BusinessObject {

	private int id;
	private int groupid;
	private String username;
	private String name;
	private String gmail;

	public User() {
		super();
	}

	public int getGroupId() {
		return groupid;
	}

	public void setGroupId(int groupid) {
		this.groupid = groupid;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	public String getGmail() {
		return gmail;
	}

	public void setGmail(String gmail) {
		this.gmail = gmail;
	}

}
