package sharedShoppingList.shared.bo;

import java.sql.Timestamp;

/**
 * Diese Klasse erweitert die allgemeine BusinessObject-Klasse um Nutzerspezifische Funktionen und Parameter.
 * @author Tobias Ilg
 */

public class Store extends BusinessObject{
	
	private int id;
	private String name;
	
	public Store() {
		super();	
	}



	public Store getStore() {
		return store;
	}
    
	public void deleteStore() {
		return null;
	}


	public String getName() {
		return name;
	}

	public void setName(String storeName) {
		this.name = storeName;
	}

    public int getStoreId() {
		return id;
	}

	public void setStoreId(int storeId) {
		this.id = storeId;
	}
	

}
