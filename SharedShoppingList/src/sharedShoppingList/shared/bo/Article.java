package sharedShoppingList.shared.bo;

import java.sql.Timestamp;

/**
 * Die Klasse <code>Article</code> ist die Realisierung einer Artikelklasse. 
 * Allerdings trägt die Klasse nur unit (Einheit) als weiteres Attribut. 
 * Alle anderen Methoden und Attribute befinden sich in der Superklasse <code>BusinessObject</code>-
 * Um objektorientiert umgehen und um bestimmte Article-Typen deklarieren zu können,
 * ist diese Klasse dennoch wichtig. 
 * @author Nico Weiler
 */

public class Article extends BusinessObject{
	
	private String unit;
	private boolean favourite;
	
	public Article() {
		super();	
	}



	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public boolean isFavourite() {
		return favourite;
	}

	public void setFavourite(boolean favourite) {
		this.favourite = favourite;
	}
	
	
	

}
