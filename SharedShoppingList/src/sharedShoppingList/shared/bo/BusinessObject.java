package sharedShoppingList.shared.bo;

import java.sql.Timestamp;

import com.google.gwt.user.client.rpc.IsSerializable;

/*
 * Die Klasse BusinessObject definiert alle gemeinsamen Attribute in Form einer Superklasse.
 * Klassen wie Article, User, etc. können von dieser Klasse erben. Somit unnötiger Programmieraufwand verhindert.
 * 
 * @author Nico Weiler
 */
public abstract class BusinessObject implements IsSerializable {
	
	private int id;
	private String name;
	private Timestamp createDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	
	 public boolean equals(Object obj) {
	    	if (obj != null && obj instanceof BusinessObject) {
				BusinessObject bo = (BusinessObject) obj;
				
				try {
					
					if (bo.getId() == this.id) {
						return true;
					}
				} catch (IllegalArgumentException e) {
					
					return false;
					}
				}
			
			return false;
	    }
	
	
	

}
