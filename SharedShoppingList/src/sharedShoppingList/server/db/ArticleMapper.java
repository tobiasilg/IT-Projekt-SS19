package sharedShoppingList.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import sharedShoppingList.shared.bo.Article;
import sharedShoppingList.shared.bo.User;


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
	public ArticleMapper() {
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
		
		String sql="SELECT * FROM article WHERE id=" + id;
		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {

				Article article = new Article();
				article.setId(rs.getInt("id"));
				article.setName(rs.getString("name"));
				article.setCreateDate(rs.getTimestamp("createDate"));
				article.setModDate(rs.getTimestamp("modDate"));
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
		String sql = "SELECT * FROM article ORDER BY name";
		
		Vector<Article> result= new Vector<Article>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				Article article = new Article();
				article.setId(rs.getInt("id"));
				article.setName(rs.getString("name"));
				article.setCreateDate(rs.getTimestamp("createDate"));
				article.setModDate(rs.getTimestamp("modDate"));
				article.setUnit(rs.getString("unit"));
				
				
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
	
	public Vector<Article> findAllByCurrentUser(User user){
		Connection con = DBConnection.connection();
		String sql = "SELECT * FROM article WHERE id=" + user.getId();
		
		Vector<Article> result= new Vector<Article>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				Article article = new Article();
				article.setId(rs.getInt("id"));
				article.setName(rs.getString("name"));
				article.setCreateDate(rs.getTimestamp("createDate"));
				article.setModDate(rs.getTimestamp("modDate"));
				article.setUnit(rs.getString("unit"));
				
				
				result.addElement(article);
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		return result;
	}
	
	/*
	 * Methode zum Löschen eines Artikels aus der Datenbank
	 */
	
	public void delete (Article article) {
	Connection con = DBConnection.connection();
		
		String sql= "DELETE FROM article WHERE id=" + article.getId();
		
	    try {
	    	/*
	    	 * Setzt den AutoCommit auf false, um das sichere Schreiben in die Datenbank zu gewährleisten.
	    	 */
			con.setAutoCommit(false);
	    	
	    	Statement stmt = con.createStatement();
	    	stmt.executeUpdate(sql);	
	    	
	    	con.commit();
	      
	    }
	    catch (SQLException e2) {
	      e2.printStackTrace();
	    }
		
	}
	
	/*
	 * Methode um einen neuen Artikel der Datenbank hinzuzufügen
	 */
	
	public Article insert (Article article) {
		Connection con = DBConnection.connection();
		
		String sql= "INSERT INTO article (name, unit) VALUES ('"+ article.getName()+ "','"+article.getUnit() + "')";
		
		try {
	    	/*
	    	 * Einstellung dass automatisch generierte  ID's aus der DB
	    	 * zureuckgeliefert werden.
	    	 * Somit kann ohne einen Refresh der Artikel sofort angezeigt werden
	    	 */
	    	
	    	PreparedStatement stmt = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS); //ID wird aus der DB geholt
	    	int affectedRows = stmt.executeUpdate(); //Wurde etwas in die DB geschrieben?

	        if (affectedRows == 0) { //Kein neuer Eintrag in DB
	            throw new SQLException("Creating article failed, no rows affected.");
	        }
	        
	        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
	        	
	            if (generatedKeys.next()) {
	                article.setId(generatedKeys.getInt(1)); //index 1 = id column
	            }
	            else {
	                throw new SQLException("Creating article failed, no ID obtained.");
	            }
	            
	        }catch(SQLException e) {
	        	e.printStackTrace();
	        }
	      
	      
	    }
	    catch (SQLException e2) {
	      e2.printStackTrace();
	    }
	    return article;
	}
	
	/*
	 * Methode um einen Artikel zu editieren
	 */
	
	public Article update(Article article) {
		Connection con = DBConnection.connection();

		
		String sql= "UPDATE article SET name= '"+ article.getName()+"', unit='"+article.getUnit()+"' WHERE id= "+ article.getId();


		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return article;
	}

	




}
