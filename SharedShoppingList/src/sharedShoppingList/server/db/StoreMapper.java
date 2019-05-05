package sharedShoppingList.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Vector;

import sharedShoppingList.shared.bo.Article;


/**
 * Mapper Klasse für </code>Article</code> Objekte.
 * Diese umfasst  Methoden um ArticleMapper Objekte zu erstellen, zu suchen, zu  ändern und zu löschen.
 * Das Mapping funktioniert dabei bidirektional.. Es können Objekte in DB-Strukturen und DB-Stukturen in Objekte umgewandelt werden.
 * 
 * @author Nico Weiler
 */

public class StoreMapper {
	
	//Speicherung der Instanz dieser Mapper Klasse
	
	private static StoreMapper articleMapper = null;
	
	/**
	 * Geschützter Konstrukter verhindert weitere Instanzierungen von ArticleMapper.
	 * Somit kann nur eine Instanz der Klasse ArticleMapper angelegt werden.
	 */
	protected StoreMapper() {
	}

	
	/**
	 * Sicherstellung der Singleton-Eigenschaft der Mapperklasse.
	 * 
	 * @return Gibt den Article Mapper zurück.
	 */
	public static StoreMapper articleMapper() {
		if (articleMapper == null) {
			articleMapper = new StoreMapper();
		}

		return articleMapper;
	}
	
	/*
	 * Methode zur Findung eines Artikels anhand der ID
	 * Artikel BO ist noch nicht angelegt
	 */
	
	public Article findByID(int id) {
		Connection con = DBConnection.connection();
		
		String sql="select * from article where id=" + id
				;
		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {

				Article article = new Article();
				article.setId(rs.getInt("id"));
				article.setName(rs.getString("name"));
				article.setCreateDate(rs.getTimestamp("Creationdate"));
				article.setUnit(rs.getString("unit"));
			
				return article;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
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
	
	/*
	 * Methode zum Löschen eines Artikels aus der Datenbank
	 */
	
	public void delete (Article article) {
		
	}
	
	/*
	 * Methode um einen neuen Artikel der Datenbank hinzuzufügen
	 */
	
	public void insert (Article article) {
		
	}
	
	/*
	 * Methode um einen bestehen Artikel in der Datenbank abzuändern
	 */
	public void update (Article article) {
		
	}



}
