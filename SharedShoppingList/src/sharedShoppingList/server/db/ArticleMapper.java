package sharedShoppingList.server.db;

import java.sql.Connection;
import java.util.ArrayList;


/**
 * Mapper Klasse für </code>Article</code> Objekte.
 * Diese umfasst  Methoden um ArticleMapper Objekte zu erstellen, zu suchen, zu  ändern und zu löschen.
 * Das Mapping funktioniert dabei bidirektional. Es können Objekte in DB-Strukturen und DB-Stukturen in Objekte umgewandelt werden.
 * 
 * @author Nico Weiler
 */

public class ArticleMapper {
	
	//Speicherung der Instanz dieser Mapper Klasse
	
	private static ArticleMapper articleMapper = null;
	
	/**
	 * Geschützter Konstrukter verhindert weitere Instanzierungen von ArticleMapper.
	 * Somit kann nur eine Instanz der Klasse ArticleMapper angelegt werden.
	 */
	protected ArticleMapper() {
	}

	
	/**
	 * Sicherstellung der Singleton-Eigenschaft der Mapperklasse.
	 * 
	 * @return Gibt den Article Mapper zurück.
	 */
	public static ArticleMapper articleMapper() {
		if (articleMapper == null) {
			articleMapper = new ArticleMapper();
		}

		return articleMapper;
	}
	
	/*
	 * Methode zur Findung eines Artikels anhand der ID
	 * Artikel BO ist noch nicht angelegt
	 */
	
	public Article findByID(int id) {
		
	}
	
	/*
	 * Methode zur Auflistung aller Artikel
	 * @return: ArrayList mit allen Artikeln wird zurückgegeben
	 */
	
	public ArrayList<Article>findAllArticles(){
		
	}
	/*
	 * Methode zur Auflistung aller Artikel einer Einkaufsliste
	 */
	
	public ArrayList<Article> findAllByCurrentUser(User user){
		
	}
	
	public void delete (Article article) {
		
	}
	
	public void insert (Article article) {
		
	}
	public void update (Article article) {
		
	}



}
