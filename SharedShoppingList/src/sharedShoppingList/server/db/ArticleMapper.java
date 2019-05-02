package sharedShoppingList.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;


import sharedShoppingList.shared.bo.Article;


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
	 * 
	 */
	
	public Article findByID(int id) {
		Connection con = DBConnection.connection();
		
		String sql="select * from article where id=" + id;
		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {

				Article article = new Article();
				article.setId(rs.getInt("id"));
				article.setName(rs.getString("name"));
				article.setCreateDate(rs.getTimestamp("createDate"));
				article.setUnit(rs.getString("unit"));
				article.setFavourite(rs.getBoolean("favourite"));
			
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
	
	public Vector<Article>findAllArticles(){
		Connection con = DBConnection.connection();
		String sql = "select * from article order by name";
		
		Vector<Article> result= new Vector<Article>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				Article article = new Article();
				article.setId(rs.getInt("id"));
				article.setName(rs.getString("name"));
				article.setCreateDate(rs.getTimestamp("createDate"));
				article.setUnit(rs.getString("unit"));
				article.setFavourite(rs.getBoolean("favourite"));
				
				result.addElement(article);
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		return result;
		
		
	}
	/*
	 * Methode zur Auflistung aller Artikel einer Einkaufsliste
	 */
	
	public ArrayList<Article> findAllByCurrentUser(User user){
		
	}
	
	/*
	 * Methode zum Löschen eines Artikels aus der Datenbank
	 */
	
	public void delete (int id) {
	Connection con = DBConnection.connection();
		
		String sql= "delete from article where id=" + id +")";
		
	    try {
	    	
	    	Statement stmt = con.createStatement();
	    	stmt.executeUpdate(sql);	 
	      
	    }
	    catch (SQLException e2) {
	      e2.printStackTrace();
	    }
		
	}
	
	/*
	 * Methode um einen neuen Artikel der Datenbank hinzuzufügen
	 */
	
	public void insert (Article article) {
		Connection con = DBConnection.connection();
		
		String sql= "insert into article values ("+article.getId() + "," + article.getName()+ "," +article.getUnit() + "," + article.getCreateDate()+ "," + article.isFavourite()+ ")";
		
	    try {
	    	
	    	Statement stmt = con.createStatement();
	    	stmt.executeUpdate(sql);	 
	      
	    }
	    catch (SQLException e2) {
	      e2.printStackTrace();
	    }
	}
	




}
