package sharedShoppingList.server;

import java.sql.Timestamp;
import java.util.List;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import sharedShoppingList.server.db.ArticleMapper;
import sharedShoppingList.server.db.FavouriteMapper;
import sharedShoppingList.server.db.GroupMapper;
import sharedShoppingList.server.db.ListEntryMapper;
import sharedShoppingList.server.db.ListMapper;
import sharedShoppingList.server.db.StoreMapper;
import sharedShoppingList.server.db.UserMapper;
import sharedShoppingList.shared.Einkaufslistenverwaltung;

import sharedShoppingList.shared.bo.Article;
import sharedShoppingList.shared.bo.Favourite;
import sharedShoppingList.shared.bo.Group;
import sharedShoppingList.shared.bo.ListEntry;
import sharedShoppingList.shared.bo.ShoppingList;
import sharedShoppingList.shared.bo.Store;
import sharedShoppingList.shared.bo.User;

/**
 * Die Klasse <code>EinkaufslistenverwaltungImpl</code> implementiert das
 * Interface Einkaufslistenverwaltung. In der Klasse ist neben der
 * ReportClientImpl sÃ¤mtliche Applikationslogik vorhanden.
 * 
 * @author Nico Weiler, Leon Seiz, Tobias Ilg
 * @version 1.0
 */

public class EinkaufslistenverwaltungImpl extends RemoteServiceServlet implements Einkaufslistenverwaltung {

	private static final long serialVersionUID = 1L;

	/**
	 * Referenz auf den ArticleMapper, welcher Article Objekte mit der Datenbank
	 * abgleicht.
	 */
	private ArticleMapper articleMapper = null;
	
	/**
	 * Referenz auf den FavouriteMapper, welcher Favourite Objekte mit der Datenbank
	 * abgleicht.
	 */
	private FavouriteMapper favouriteMapper = null;

	/**
	 * Referenz auf den GroupMapper, welcher Group Objekte mit der Datenbank
	 * abgleicht.
	 */
	private GroupMapper groupMapper = null;

	/**
	 * Referenz auf den ListEntryMapper, welcher ListEntry Objekte mit der Datenbank
	 * abgleicht.
	 */
	private ListEntryMapper listEntryMapper = null;

	/**
	 * Referenz auf den ListMapper, welcher List Objekte mit der Datenbank
	 * abgleicht.
	 */
	private ListMapper listMapper = null;

	/**
	 * Referenz auf den StoreMapper, welcher Store Objekte mit der Datenbank
	 * abgleicht.
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

	public EinkaufslistenverwaltungImpl() throws IllegalArgumentException {
	}

	/**
	 * init() Initialisiert alle Mapper in der Klasse.
	 */
	public void init() throws IllegalArgumentException {

		/**
		 * Um mit der Datenbank kommunizieren zu kÃ¶nnen benÃ¶tigt die Klasse
		 * EinkaufslistenverwaltungImpl einen vollstÃ¤ndigen Satz von Mappern.
		 */

		this.articleMapper = ArticleMapper.articleMapper();
		this.groupMapper = GroupMapper.groupMapper();
		this.listEntryMapper = ListEntryMapper.listEntryMapper();
		this.listMapper = ListMapper.listMapper();
		this.storeMapper = StoreMapper.storeMapper();
		this.userMapper = UserMapper.userMapper();
		this.favouriteMapper = FavouriteMapper.favouriteMapper();	

	}

	/**
	 * ************************* 
	 * ABSCHNITT, Beginn: Methoden fuer Article Objekte
	 * @author Nico Weiler
	 * *************************
	 **/

	/**
	 * Anlegen eines neuen Artikels, der dann in der DB gespeichert wird
	 * 
	 * @param String name String unit
	 * 
	 */

	public Article createArticle(String name, String unit) throws IllegalArgumentException {
		Article article = new Article();
		article.setName(name);
		article.setUnit(unit);
		/*
		 * Setzen einer vorläufigen Storenr. Der insert-Aufruf liefert dann ein Objekt,
		 * dessen Nummer mit der Datenbank konsistent ist.
		 * autoincrement=1
		 */
		article.setId(1);

		return this.articleMapper.insert(article);
	}

	/**
	 * Speichern/Update des Artikels in der DB
	 * 
	 * @param Article Objekt
	 */

	public void save(Article article) throws IllegalArgumentException {
		this.articleMapper.update(article);
	}

	/**
	 * Löschen eines Articles und damit entfernen des Article Tupel aus der DB
	 * Die Löschweitergabe sorgt dafür, dass auch Listeneinträge, welche den jeweiligen Artikel
	 * enthalten, gelöscht werden. 
	 * @param Article Objekt
	 */

	public void delete(Article article) throws IllegalArgumentException {
		
		Vector<ListEntry> listEntries = this.getAllListEntriesByArticle(article);
		/*
		 * Prüfen ob Listeneinträge mit dem jeweiligen Artikel vorhanden sind.
		 */
		if(listEntries != null) {
			for(ListEntry le:listEntries) {

				this.listEntryMapper.delete(le);
				
			}
		}
		/*
		 * Eigentliches Löschen des Artikels
		 */
		
		this.articleMapper.delete(article);
		
	}

	/**
	 * Auslesen aller angelegten Artikel
	 * 
	 * @return Alle Artikel
	 * @throws IllegalArgumentException
	 */

	public Vector<Article> getAllArticles() throws IllegalArgumentException {
		return this.articleMapper.findAllArticles();
	}

	/**
	 * 
	 * @param user
	 * @return Alle Artikel, welche einem bestimmten Nutzer zugewiesen sind.
	 * @throws IllegalArgumentException
	 */

	public Vector<Article> getAllArticlesOf(User user) throws IllegalArgumentException {
		return this.articleMapper.findAllByCurrentUser(user);
		
	}
	
	/**
	 * ************************* 
	 * ABSCHNITT, Beginn: Methoden fuer User Objekte
	 * @author Nico Weiler
	 * *************************
	 **/
	
	/**
	 * Löschen des Accounts eines Users.
	 * User soll in der Lage sein, seinen eigenen Account zu löschen.
	 * @param user object
	 */
	
	public void delete (User user)throws IllegalArgumentException {
		
		Vector<ListEntry> listEntries = this.getAllListEntriesByUser(user);
		/*
		 * Prüfen ob Listeneinträge mit dem jeweiligen User vorhanden sind.
		 */
		if(listEntries != null) {
			for(ListEntry le:listEntries) {

				this.listEntryMapper.delete(le);
				
			}
		}
		/*
		 * Eigentliches Löschen des Users
		 */
		this.userMapper.delete(user);
	}
	
	/**
	 * Speichern/Update des Users in der DB
	 * 
	 * @param User Objekt
	 */

	public void save(User user) throws IllegalArgumentException {
		this.userMapper.update(user);
	}
	
	/**
	 * gibt alle User Objekte zurück
	 * 
	 * @param
	 * @return alle User Objekte in Form eines Vectors
	 * @throws IllegalArgumentException
	 */
	
	public Vector<User> getAllUsers()throws IllegalArgumentException{
		return this.userMapper.findAll();
	}
	
	/**
	 * gibt einen User mit der angegeben Id zurück
	 * @param id
	 * @return UserObjekt mit der entsprechenden Id
	 * @throws IllegalArgumentException
	 */
	public User getUserByID(int id) throws IllegalArgumentException{
		return this.userMapper.findById(id);
	}
	
	
	/**
	 * ************************* ABSCHNITT, Beginn: Methoden fÃ¼r Store Objekte
	 * 
	 * @author Leon *************************
	 **/
	public Store createStore(String name) throws IllegalArgumentException {
		Store store = new Store();
		/*
		 * Setzen einer vorläufigen Storenr. Der insert-Aufruf liefert dann ein Objekt,
		 * dessen Nummer mit der Datenbank konsistent ist.
		 */
		store.setId(1);
		store.setName(name);

		return this.storeMapper.insert(store);
	}
	
	
	/**
	 * Löschen eines Stores mit zugehöriger Löschweitergabe
	 * @author Nico Weiler
	 * @param store
	 * @throws IllegalArgumentException
	 */

	public void delete(Store store) throws IllegalArgumentException {
		
		Vector<ListEntry> listEntries = this.getAllListEntriesByStore(store);
		/*
		 * Prüfen ob Listeneinträge mit dem jeweiligen Händler vorhanden sind.
		 */
		
		if(listEntries != null) {
			for(ListEntry le:listEntries) {

				this.listEntryMapper.delete(le);
				
			}
		}
		/*
		 * Löschen des Händlers
		 */

		this.storeMapper.delete(store);

	}
	/*
	 * @author Leon Seiz
	 * store
	 */

	public Vector<Store> getAllStores() throws IllegalArgumentException {
		return this.storeMapper.findAll();

	}

	public Store getStoreByID( int id) throws IllegalArgumentException {
		return this.storeMapper.findById(id);

	}

	public void save(Store store) throws IllegalArgumentException {
		this.storeMapper.update(store);
	}
	/*
	 * Kl�rung!
	 */

	// public Store getStoreByName (String name) throws IllegalArgumentException{
	// return this.storeMapper.findByID();
	// }

	/*
	 * ABSCHNITT, Beginn: Methoden f�r ListEntry Objekte
	 * 
	 * @author Leon
	 */
	public ListEntry createListentry(String name) throws IllegalArgumentException {
		ListEntry listentry = new ListEntry();

		/*
		 * Setzen einer vorläufigen Storenr. Der insert-Aufruf liefert dann ein Objekt,
		 * dessen Nummer mit der Datenbank konsistent ist.
		 */

		listentry.setId(1);
		listentry.setName(name);
		/*
		 * 
		 */
		return this.listEntryMapper.insert(listentry);
	}

	public void save(ListEntry listentry) throws IllegalArgumentException {
		this.listEntryMapper.update(listentry);
	}
	
	public void delete(ListEntry listEntry) throws IllegalArgumentException{
		this.listEntryMapper.delete(listEntry);
	}
	
	public Vector<ListEntry> getAllListEntriesByArticle(Article article) throws IllegalArgumentException {
		return this.listEntryMapper.findByArticle(article);
		
	}
	
	public Vector<ListEntry> getAllListEntriesByStore(Store store) throws IllegalArgumentException {
		return this.listEntryMapper.findByStore(store);
		
	}
	
	/**
	 * Methode wird benötigt um nach dem Löschen eines Users, die zugehörigen
	 * Listeneinträge zu löschen
	 * @param user object
	 * @return all Listeneinträge des jeweiligen Users
	 * @author Nico Weiler
	 */
	
	public Vector<ListEntry>getAllListEntriesByUser(User user) throws IllegalArgumentException{
		return this.listEntryMapper.findAllByCurrentUser(user);
	}
	
	/**
	 * Methode wird für den Report benötigt
	 * @author Nico Weiler
	 * @param Store und ausgewähltes Datum
	 */
	
	@Override
	public List<ListEntry> getEntriesByStoreAndDate(Store store, Timestamp beginningDate) throws IllegalArgumentException {
		return listEntryMapper.findByStoreAndDate(store, beginningDate);
	}
	
	public Vector <User> getAllUser (User user) throws IllegalArgumentException {
		return this.userMapper.findAll();
	}
	
	/**
	 * Alle User einer Gruppe zur Anzeigen in der Gruppenverwaltung
	 * 
	 */
	
	public Vector<User> getUsersByGroup(Group group) throws IllegalArgumentException{
		return this.userMapper.findByGroup(group);
	}
	
	
	
	
	
/** BO: GRUPPE @author Tobi **/

	
	/** Create einer neuen Gruppe */

	public Group createGroup(String name) throws IllegalArgumentException {
		Group group = new Group();
		group.setName(name);

        /** ACHTUNG! NUR VORLÄUFIG!
        * Die ID muss später in aufsteigender Reihenfolge vergeben werden
        * @TODO ID-Verabe anpassen.
        */
		group.setId(1);

		this.groupMapper.insert(group);
		
		return group;
	}

		
	/** Read einer Gruppe */

    /** Ausgabe aller Gruppen
    *@TODO Klären, ob mehrere Gruppen parallel existieren können*/

    public Vector<Group> getAllGroups() throws IllegalArgumentException {
		return this.groupMapper.findAll();
	}

    
    /** Ausgabe einer Gruppe */
    public Group findGroupbyId(int id) throws IllegalArgumentException {
		return this.groupMapper.findById(id);
	}


	/** Update einer Gruppe */

	public void save(Group group) throws IllegalArgumentException {
		this.groupMapper.update(group);
	}

	/** Löschen einer Gruppe */

	public void delete(Group group) throws IllegalArgumentException {
		this.groupMapper.delete(group);
		
	}
	
	/**
	 * Gruppe pro User soll im Nav angezeigt werden
	 * 
	 */
	
	public Group getGroupByUser (User user) throws IllegalArgumentException{
		return this.groupMapper.findByUser(user);
	}
	
	
/** SHOPPINGLISTE @author Tobi **/

	
	/** Create einer neuen Shoppingliste */

	public ShoppingList createShoppingList(String name) throws IllegalArgumentException {
		ShoppingList shoppingList = new ShoppingList();
		shoppingList.setName(name);

        /** ACHTUNG! NUR VORLÄUFIG!
        * Die ID muss später in aufsteigender Reihenfolge vergeben werden
        * @TODO ID-Verabe anpassen.
        */
		shoppingList.setId(1);

		this.listMapper.insert(shoppingList);
		
		return shoppingList;
	}

		
	/** Read einer Shoppingliste */

        /** Ausgabe aller Listen **/

    public Vector<ShoppingList> getAll() throws IllegalArgumentException {
		return this.listMapper.findAll();
	}

        /** Ausgabe aller Listen einer Gruppe **/

    public Vector<ShoppingList> getAllByGroup(Group group) throws IllegalArgumentException {
		return this.listMapper.findAllByGroup(group);
	}

    
        /** Ausgabe einer Shoppingliste */
    public ShoppingList findShoppingListById(int id) throws IllegalArgumentException {
		return this.listMapper.findById(id);
	}


	/** Update einer Shoppingliste */

	public void save(ShoppingList shoppingList) throws IllegalArgumentException {
		this.listMapper.update(shoppingList);
	}

	/** Löschen einer Shoppingliste */

	public void delete(ShoppingList shoppingList) throws IllegalArgumentException {
		this.listMapper.delete(shoppingList);
		
	}
	
	//* Favourite @author Leon Seiz *//
	
	public Favourite createFavourite (ListEntry listentry, Group group) throws IllegalArgumentException{
		
		Favourite favourite = new Favourite ();
		favourite.setGroupsId(group.getId());
		favourite.setListEntryId(listentry.getId());
		
		
		return this.favouriteMapper.createFavourite(favourite);
	}
	
	public void deleteArticle (Favourite favourite) throws IllegalArgumentException{
		this.favouriteMapper.deleteFavourite(favourite);
	}
	
	public Vector <Favourite> getAllFavourites() throws IllegalArgumentException{
		return this.favouriteMapper.findAllFavourites();
	}
	
	


	
}
