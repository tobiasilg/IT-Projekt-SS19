package sharedShoppingList.shared.bo;

/**
 * Die Klasse <code>ShoppingList</code> ist die Realisierung einer Klasse, welche die Einkaufsliste darstellt. 
 * Alle Methoden und Attribute befinden sich in der Superklasse <code>BusinessObject</code>- 
 * Um objektorientiert umgehen und um bestimmte Listeneinträge-Typen deklarieren zu können,
 * ist diese Klasse dennoch wichtig. 
 * 
 * @author Nico Weiler
 */

public class ShoppingList extends BusinessObject {
	
	private static final long serialVersionUID = 1L;
	
	private int groupId;
	private int newOne;
	
	
	
	public ShoppingList() {
		super();
	}
	
	

	public int getNewOne() {
		return newOne;
	}



	public void setNewOne(int newOne) {
		this.newOne = newOne;
	}



	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	

}
