package sharedShoppingList.shared.bo;

import java.sql.Timestamp;

/**
 * Die Klasse <code>ListEntry</code> ist die Realisierung einer Klasse, welche den Eintrag einer Einkaufsliste darstellt. 
 * Alle Methoden und Attribute befinden sich in der Superklasse <code>BusinessObject</code>- außer die Attribute amount und checked
 * Um objektorientiert umgehen und um bestimmte Listeneinträge-Typen deklarieren zu können,
 * ist diese Klasse dennoch wichtig. 
 * @author Nico Weiler
 */

public class ListEntry extends BusinessObject {
	
	
	
	public ListEntry() {
		super();
	}
	
	/*
	 * Das boolsche Attribut checked soll das Abhaken eines Eintrags in einer Einkaufsliste darstellen.
	 */
	private boolean checked;
	
	/*
	 * amount stellt die einzukaufende Menge eines Artikels dar
	 */
	private double amount;

	/*
	 * Fremdschlüssel-Beziehungen
	 */
	
	/*
	 * Datum, an welchem die Artikerl( Listeneinträge) abgehakt und somit gekauft wurden
	 */
	
	private Timestamp buyDate;
	
	private int articleId;
	private int storeId;
	private int userId;
	private int shoppinglistId;
	
	private Article article;
	private ShoppingList shoppinglist;
	private User user;
	private Store store;
	
	
	
	
	
	
	public Timestamp getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(Timestamp buyDate) {
		this.buyDate = buyDate;
	}

	public ShoppingList getShoppinglist() {
		return shoppinglist;
	}

	public void setShoppinglist(ShoppingList shoppinglist) {
		this.shoppinglist = shoppinglist;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public int getShoppinglistId() {
		return shoppinglistId;
	}

	public void setShoppinglistId(int shoppinglistId) {
		this.shoppinglistId = shoppinglistId;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public double getAmount() {
		return amount;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	
	

}
