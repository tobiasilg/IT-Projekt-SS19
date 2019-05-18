package sharedShoppingList.server;

import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;


import sharedShoppingList.server.db.ArticleMapper;
import sharedShoppingList.server.db.GroupMapper;
import sharedShoppingList.server.db.ListEntryMapper;
import sharedShoppingList.server.db.ListMapper;
import sharedShoppingList.server.db.StoreMapper;
import sharedShoppingList.server.db.UserMapper;
import sharedShoppingList.shared.Einkaufslistenverwaltung;

import sharedShoppingList.shared.bo.Article;

import sharedShoppingList.shared.bo.Store;
import sharedShoppingList.shared.bo.User;


/**
 * Die Klasse <code>EinkaufslistenverwaltungImpl</code> implementiert das Interface
 * Einkaufslistenverwaltung. In der Klasse ist neben der ReportClientImpl sÃ¤mtliche
 * Applikationslogik vorhanden.
 * 
 * @author Nico Weiler, Leon Seiz, Tobias Ilg
 * @version 1.0
 */

public class EinkaufslistenverwaltungImpl extends RemoteServiceServlet implements Einkaufslistenverwaltung{
	
private static final long serialVersionUID = 1L;

	
	/**
	 * Referenz auf den ArticleMapper, welcher Article Objekte mit der
	 * Datenbank abgleicht.
	 */
	private ArticleMapper articleMapper = null;

	
	/**
	 * Referenz auf den GroupMapper, welcher Group Objekte mit der Datenbank
	 * abgleicht.
	 */
	private GroupMapper groupMapper = null;

	
	/**
	 * Referenz auf den ListEntryMapper, welcher ListEntry Objekte mit der
	 * Datenbank abgleicht.
	 */
	private ListEntryMapper listEntryMapper = null;

	
	/**
	 * Referenz auf den ListMapper, welcher List Objekte mit der
	 * Datenbank abgleicht.
	 */
	private ListMapper listMapper = null;

	
	/**
	 * Referenz auf den StoreMapper, welcher Store Objekte mit der
	 * Datenbank abgleicht.
	 */
	private StoreMapper storeMapper = null;

	
	/**
	 * Referenz auf den UserMapper, welcher User Objekte mit der Datenbank
	 * abgleicht.
	 */
	private UserMapper userMapper = null;
	
	/**
	 * Ein <code>RemoteServiceServlet</code> wird unter GWT mittels
	 * <code>GWT.create(Klassenname.class)</code> Client-seitig erzeugt. Hierzu ist
	 * ein solcher No-Argument-Konstruktor anzulegen.
	 * 
	 * Es bietet sich also an, eine separate Instanzenmethode zu erstellen, die
	 * Client-seitig direkt nach <code>GWT.create(Klassenname.class)</code>
	 * aufgerufen wird, um eine Initialisierung der Instanz vorzunehmen.
	 * 
	 * @see #init()
	 * @throws IllegalArgumentException
	 */
	
	public EinkaufslistenverwaltungImpl() throws IllegalArgumentException{
	}
	
	/**
	 * init() Initialisiert alle Mapper in der Klasse.
	 */
	public void init() throws IllegalArgumentException {

		/**
		 * Um mit der Datenbank kommunizieren zu kÃ¶nnen benÃ¶tigt die Klasse
		 * EinkaufslistenverwaltungImpl einen vollstÃ¤ndigen Satz von Mappern.
		 */

		this.articleMapper=ArticleMapper.articleMapper();
		this.groupMapper=GroupMapper.groupMapper();
		this.listEntryMapper=ListEntryMapper.listEntryMapper();
		this.listMapper=ListMapper.listMapper();
		this.storeMapper=StoreMapper.storeMapper();
		this.userMapper = UserMapper.userMapper();
		
	}
	
	/**
	 * *************************
	 * ABSCHNITT, Beginn: Methoden fÃ¼r Article Objekte
	 * 
	 * *************************
	 **/
	
	/**
	 * Anlegen eines neuen Artikels, der dann in der DB gespeichert wird
	 * @param String name String unit
	 * 
	 */
	

	public Article createArticle(String name, String unit) throws IllegalArgumentException {
		Article article =new Article();
		article.setName(name);
		article.setUnit(unit);
		
		article.setId(1);
		
		return this.articleMapper.insert(article);
	}
	
	
	/**
	 * Speichern/Update des Artikels in der DB
	 * @param Article Objekt
	 */
	
	public void save (Article article) throws IllegalArgumentException {
		this.articleMapper.update(article);
	}
	
	
	/**
	 * Löschen eines Articles und damit entfernen des Article Tupel aus der DB
	 * @param Article Objekt
	 */
	
	public void delete (Article article)throws IllegalArgumentException{
		
	}
	
	/**
	 * Auslesen aller angelegten Artikel
	 * @return Alle Artikel
	 * @throws IllegalArgumentException
	 */
	
	public Vector<Article> getAllArticles()throws IllegalArgumentException{
		return this.articleMapper.findAllArticles();
	}
	
	/**
	 * 
	 * @param user
	 * @return Alle Artikel, welche einem bestimmten Nutzer zugewiesen sind.
	 * @throws IllegalArgumentException
	 */
	
	public Vector<Article> getAllArticlesOf(User user)throws IllegalArgumentException{
		return this.articleMapper.findAllByCurrentUser(user);
	}
	
	

	
	
	/**
	 * *************************
	 * ABSCHNITT, Beginn: Methoden fÃ¼r Store Objekte
	 * @author Leon
	 * *************************
	 **/
	public Store createStore (String name) throws IllegalArgumentException {
		Store store = new Store();
		/*
		 * Setzen einer vorläufigen Storenr.
		 * Der insert-Aufruf liefert dann ein Objekt, dessen Nummer mit der Datenbank konsistent ist.
		 */
		store.setId(1);
		store.setName(name);
		
		return this.storeMapper.insert(store);
	}
	
	public void deleteStore (Store store) throws IllegalArgumentException{
		
		/*
		 * Löschweitergabe zu klären (falls store in ListEntry vorhanden, was passiert?)
		 */
		
		this.storeMapper.delete(store);

	}

	public Vector <Store> getAllStores (Store store) throws IllegalArgumentException {
		return this.storeMapper.findAll();
		
	}
	
	public Store getStoreByID (Store store) throws IllegalArgumentException{
		return this.storeMapper.findByID(store);
		
	}
	public void save (Store store) throws IllegalArgumentException {
		this.storeMapper.update(store);
	}
	/*
	 * Kl�rung!
	 */
	
	
	//public Store getStoreByName (String name) throws IllegalArgumentException{
	//	return this.storeMapper.findByID();
	//}
	}
	
	/*
	 * ABSCHNITT, Beginn: Methoden f�r ListEntry Objekte
	 * @author Leon
	 */
