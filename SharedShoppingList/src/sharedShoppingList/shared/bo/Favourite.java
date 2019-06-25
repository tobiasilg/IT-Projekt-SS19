package sharedShoppingList.shared.bo;

import com.google.gwt.user.client.rpc.IsSerializable;

/*
 * @author: Leon Seiz
 * Die Klasse <code>Favourite</code> ist die Realisierung einer Artikelklasse. 
 * Die Klasse erbt als einzige Klasse nicht von der Klasse Business Object, da Sie die Attribute nicht ben�tigt
 */


public class Favourite implements IsSerializable {
	
	private static final long serialVersionUID = 1L;
	
	
	/*
	 * int id wird als PK in der Entit�t Favourite genutzt
	 */
	
	private int id;
	
	/*
	 * Fremdschluesselbeziehungen
	 */
	
	private int groupsId;
	private int listEntryId;
	private ListEntry listEntry;
	
	public Favourite() {
		
	}
	



	public ListEntry getListEntry() {
		return listEntry;
	}

	public void setListEntry(ListEntry listEntry) {
		this.listEntry = listEntry;
	}

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
