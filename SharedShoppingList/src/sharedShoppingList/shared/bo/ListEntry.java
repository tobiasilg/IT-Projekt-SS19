package sharedShoppingList.shared.bo;

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

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	

}
