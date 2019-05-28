package sharedShoppingList.shared.bo;


/*
 * Die Klasse <code>Favourite</code> ist die Realisierung einer Artikelklasse. 
 * Die Klasse erbt als einzige Klasse nicht von der Klasse Business Object, da Sie die Attribute nicht benötigt
 */


public class Favourite {
	
	
	/*
	 * int id wird als PK in der Entität Favourite genutzt
	 */
	
	private int id;
	
	/*
	 * Fremdschluesselbeziehungen
	 */
	
	private int groupsId;
	private int listEntryId;
	
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id=id;	
	}
	
	public int getGroupsId() {
		return groupsId;
	}
	
	public void setGroupsId(int groupsId) {
		this.groupsId=groupsId;
	}
	
	public int getListEntryId() {
		return listEntryId;
	}
	
	public void setListEntryId(int listEntryId) {
		this.listEntryId=listEntryId;
	}
	
	
}
