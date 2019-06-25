package sharedShoppingList.shared;

import java.io.Serializable;

public class LoginInfo implements Serializable {
	

/**
 * Ein Objekt dieser Klasse wird erzeugt und mit den entsprechenden Attributen versehen, 
 * sobald der Login erfolgreich war.
 *
 */

	
	private boolean loggedIn = false;
	private String loginUrl;
	private String logoutUrl;
	
	private String gmail;
	private int groupid;
	String username;
	
	public LoginInfo() {
		
	}
	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
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
	
}