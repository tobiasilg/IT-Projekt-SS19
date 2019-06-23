package sharedShoppingList.shared.bo;

import java.sql.Timestamp;

/**
 * Diese Klasse erweitert die allgemeine BusinessObject-Klasse um Nutzerspezifische Funktionen und Parameter.
 * @author Tobias Ilg, Leon Seiz
 */

public class User extends BusinessObject{
	
	private int groupid;
	private String username;
	private String gmail;
	
	private boolean isLoggedIn=false;
	private String loginUrl;
	private String logoutUrl;
	
	public User() {
		super();	
	}
	
	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}	
	
	public String getGmail() {
		return gmail;
	}
	public void setGmail(String gmail) {
		this.gmail = gmail;
	}
	
	/**
	 * Login Information
	 * 
	 */
	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

}
