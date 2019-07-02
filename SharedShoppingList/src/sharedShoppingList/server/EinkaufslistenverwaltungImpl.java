package sharedShoppingList.server;

import java.sql.Timestamp;
import java.util.Date;
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
	 * @author Nico Weiler, Tobias Ilg
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
	 * Löschen eines Artikels ohne jegliche Löschweitergabe
	 * 
	 * @param Article Objekt
	 */

	public Article delete(Article article) throws IllegalArgumentException {
		
		Vector<ListEntry> listEntries = this.getAllListEntriesByArticle(article);

		/*
		 * Falls ein Artikel in einem Listeneintrag auftaucht, soll dieser nicht gelöscht werden
		 * Gui wirft Meldung
		 */


		if(listEntries.size() > 0) {

			 
			return null;
			
			
		}else {
			/**
			 * Eigentliches Löschen des Artikels
			 */
			this.articleMapper.delete(article);
			return article;
			
		}
		
		
	
		
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
	 * Auslesen eines Artikels anhand der ID
	 * 
	 * @param id
	 * @return spezifischer Artikel
	 * @throws IllegalArgumentException
	 */
	
	public Article getArticleById(int id) throws IllegalArgumentException{

		return this.articleMapper.findByID(id);
	}
	
	/**
	 * Auslesen aller Artikel einer spez. Gruppe
	 * 
	 * @param group
	 * @return Vector<Article>
	 * @throws IllegalArgumentException
	 */
	

	/**
	 * 
	 * @param user
	 * @return Alle Artikel, welche einem bestimmten Nutzer zugewiesen sind.
	 * @throws IllegalArgumentException
	 */

	public Vector<Article> getAllArticlesOf(User user) throws IllegalArgumentException {
		
		if (user != null) {
			
			return this.articleMapper.findAllByCurrentUser(user);
		}else {
			
			return null;
		}
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
		 * Löschen der Zuordnung zu einer Gruppe
		 */
		this.userMapper.deleteMembership(user);
		
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
	 * Gibt einen User mit dem entsprechenden Name zurück
	 * @param name
	 * @return UserObjekt
	 * @throws IllegalArgumentException
	 */
	public User getUserByName(String name) throws IllegalArgumentException{
		return this.userMapper.findByName(name);
	}
	
	public User getUserByMail(String gmail) throws IllegalArgumentException {
		return this.userMapper.findByGmail(gmail);
	}
	
	public User createUser(String name, String gmail, String username) throws IllegalArgumentException {
		User user= new User();
		
		user.setName(name);
		user.setUsername(username);
		user.setGmail(gmail);
		
		return this.userMapper.insert(user);
	}
	
	
	/**
	 * ************************* ABSCHNITT, Beginn: Methoden fÃ¼r Store Objekte
	 * 
	 * @author Leon *************************
	 **/
	public Store createStore(String name) throws IllegalArgumentException {
		Store store = new Store();
		
		
		
		store.setName(name);

		return this.storeMapper.insert(store);
	}
	
	
	/**
	 * Löschen eines Stores mit zugehöriger Löschweitergabe
	 * @author Nico Weiler
	 * @param store
	 * @throws IllegalArgumentException
	 */

	public Store delete(Store store) throws IllegalArgumentException {
		
		Vector<ListEntry> listEntries = this.getAllListEntriesByStore(store);
		/*
		 * Falls ein Store in einem Listeneintrag auftaucht, soll dieser nicht gelöscht werden
		 * Gui wirft Meldung
		 */
		if(listEntries.size() > 0) {
			 
			return null;
				
			
		}else {
			/**
			 * Eigentliches Löschen des Stores
			 */
			this.storeMapper.delete(store);
			return store;
		}

	}
	/*
	 * @author Leon Seiz
	 * store
	 */

	/** Alle Stores ausgeben lassen
	 * 
	 * @return Vector<Store>
	 * @throws IllegalArgumentException
	 */
	
	public Vector<Store> getAllStores() throws IllegalArgumentException {
		return this.storeMapper.findAll();

	}

	/** Einen spez. Store anhand dessen ID ausgeben lassen
	 * 
	 * @param id
	 * @return Store
	 * @throws IllegalArgumentException
	 */
	public Store getStoreByID(int id) throws IllegalArgumentException {
		return this.storeMapper.findById(id);

	}
	
	/** Alle Stores einer Gruppe ausgeben lassen
	 * 
	 * @param group
	 * @return Vector<Store>
	 * @throws IllegalArgumentException
	 */
	
//	public Vector<Store> findStoreByGroup(Group group) throws IllegalArgumentException {
//		return this.storeMapper.findStoreByGroup(group);
//
//	}

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
	public ListEntry createListentry(String name, User user, Article article, double amount, Store store, ShoppingList sl) throws IllegalArgumentException {
		ListEntry listentry = new ListEntry();
		System.out.println("createListentry");

	
		listentry.setName(name);
		
		listentry.setUserId(user.getId());
		System.out.println("UserID:"+ user.getId());
		
		listentry.setArticleId(article.getId());
		System.out.println("ArtikelID:"+ article.getId());
		
		listentry.setAmount(amount);
		System.out.println("Menge:"+ amount);
		
		listentry.setStoreId(store.getId());
		System.out.println("StoreID:"+ store.getId());
		
		listentry.setShoppinglistId(sl.getId());
		System.out.println("ShoppingListID:"+ sl.getId());
		
		
		
		/*
		 * 
		 */
		
		return this.listEntryMapper.insert(listentry);
	}

	public void save(ListEntry listentry) throws IllegalArgumentException {
		/*
		 * Wenn checked angehakt wurde, soll ein neues Datum (das aktuelle) gesetzt werden
		 */
		if(listentry.isChecked()) {
			listentry.setBuyDate((Timestamp) new Date());
		}
		else {
			listentry.setBuyDate(null);
			
		}
		
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
	
	public Vector<ListEntry> getAllListEntries() throws IllegalArgumentException {
		return this.listEntryMapper.findAllListEntries();
		
	}
	 
	/**
	 * Methode wird benötigt um nach dem Löschen einer ShoppingListe, die zugehörigen
	 * Listeneinträge zu löschen
	 * @param sl 
	 * @return alle Listeneinträge der shoppingliste
	 * @author Nico Weiler
	 */
	
	public Vector<ListEntry>getAllListEntriesByShoppingList (ShoppingList sl) throws IllegalArgumentException{
		return this.listEntryMapper.findAllByShoppingList(sl);
	}
	
	/**
	 * Methode wird benötigt um nach dem Löschen eines Users, die zugehörigen
	 * Listeneinträge zu löschen
	 * @param user object
	 * @return alle Listeneinträge des jeweiligen Users
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
	
	
	public List<ListEntry> getEntriesByStoreAndDate(Store store, Timestamp beginningDate, Timestamp endDate, int groupId) throws IllegalArgumentException {
		return listEntryMapper.findByStoreAndDate(store, beginningDate, endDate, groupId);
	}
	
	public Vector <User> getAllUser (User user) throws IllegalArgumentException {
		return this.userMapper.findAll();
	}
	
	/**
	 * Alle User einer Gruppe zum Anzeigen in der Gruppenverwaltung
	 * 
	 */
	
	public Vector<User> getUsersByGroup(Group group) throws IllegalArgumentException{
		return this.userMapper.findByGroup(group);
	}
	
	
	
	
	
/** BO: GRUPPE @author Tobi **/

	
	/** Create einer neuen Gruppe */

	public Group createGroup(User user, String name) throws IllegalArgumentException {
		Group group = new Group();
		group.setName(name);

		group = this.groupMapper.insert(group);
		
		/*
		 * Nachdem die  ID vorhanden ist, wird der User der Gruppe hinzugefügt.
		 */
		this.groupMapper.insertMembership(user.getId(), group.getId());
		
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

	/** Löschen einer Gruppe mit zugehöriger Löschweitergabe
	 * Nachdem Listeneintrgäge und Shoppinglisten gelöscht wurden, 
	 * kann die Gruppe gelöscht werden.
	 * @param group
	 * @author Nico Weiler
	 * 
	 **/

	public void delete(Group group) throws IllegalArgumentException {
		
		Vector<ShoppingList> shoppingLists = this.getAllByGroup(group);
		
		/*
		 * Prüfen ob Shoppinglisten der jeweiligen Gruppe vorhanden sind.
		 */
		
		if(shoppingLists != null) {
			for(ShoppingList sl:shoppingLists) {
				
				
				Vector<ListEntry> listEntries = this.getAllListEntriesByShoppingList(sl);
				
				/*
				 * Prüfen ob die Shoppingliste auch Listeneinträge enthält
				 */
				
				if(listEntries != null) {
					
					for(ListEntry le: listEntries) {
						
						/*
						 * Zuerst werden die Listeneinträge gelöscht
						 */
						this.listEntryMapper.delete(le);
					}
					
				}
				/*
				 * Anschließend werden die Shoppinglisten gelöscht
				 */
				
				this.listMapper.delete(sl);
				
			}
		}
		/*
		 * Zum Schluss wird die gewünschte Gruppe gelöscht
		 */
		
		System.out.println(group.getName());
		
		this.groupMapper.deleteMembership(group.getId());
		this.groupMapper.delete(group);
		
	}
	
	/**
	 * Gruppe pro User soll im Nav angezeigt werden
	 * 
	 */
	
	public Vector<Group> getGroupsByUser (User user) throws IllegalArgumentException{
		return this.groupMapper.findByUser(user);
	}
	
	
/** SHOPPINGLISTE @author Tobi **/

	
	/** Create einer neuen Shoppingliste */

	public ShoppingList createShoppingList(String name, Group group) throws IllegalArgumentException {
		
		ShoppingList shoppingList = new ShoppingList();
		shoppingList.setName(name);
		shoppingList.setGroupId(group.getId());


		ShoppingList sl = this.listMapper.insert(shoppingList);
		
		Vector <Favourite> favs = this.favouriteMapper.findFavouritesByGroupId(group.getId());
		
		for (Favourite favourite : favs) {
			ListEntry le = new ListEntry();
			ListEntry favle = this.listEntryMapper.findByID(favourite.getListEntryId());
			le.setAmount(favle.getAmount());
			le.setUserId(favle.getUserId());
			le.setArticleId(favle.getArticleId());
			le.setShoppinglistId(sl.getId());
			le.setStoreId(favle.getStoreId());
			System.out.println(favle);
			this.listEntryMapper.insert(le);
			
			//le.setCreateDate(favourite.getlis);
		}
		
		
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

	/**
	 * Löschen einer Shoppingliste inkl. zugehöriger  Listeneinträge
	 * @author Nico Weiler
	 * @param shoppingList
	 * 
	 */

	public void delete(ShoppingList shoppingList) throws IllegalArgumentException {
		
		Vector<ListEntry> listEntries = this.getAllListEntriesByShoppingList(shoppingList);
		/*
		 * Prüfen ob Listeneinträge in  der jeweiligen Shoppinglist vorhanden sind.
		 */
		if(listEntries != null) {
			for(ListEntry le:listEntries) {

				this.listEntryMapper.delete(le);
				
			}
		}
		/*
		 * Eigentliches Löschen der Shoppinglist
		 */
		
		this.listMapper.delete(shoppingList);
		
	}
	
	//* Favourite @author Leon Seiz *//
	
	public Favourite createFavourite (ListEntry listentry, Group group) throws IllegalArgumentException{
		
		Favourite favourite = new Favourite ();
		favourite.setGroupsId(group.getId());
		favourite.setListEntryId(listentry.getId());
		
		
		return this.favouriteMapper.createFavourite(favourite);
	}
	
	public void delete (Favourite favourite) throws IllegalArgumentException{
		this.favouriteMapper.deleteFavourite(favourite);
	}
	
	public Vector <Favourite> getAllFavourites() throws IllegalArgumentException{
		return this.favouriteMapper.findAllFavourites();
	}

	
	public Group getGroupById(int id) throws IllegalArgumentException {
		return this.groupMapper.findById(id);
	}
	
	/**
	 * Beginn Abschnitt sonstige Methoden 
	 * @author Nico Weiler
	 */
	
	/**
	 * Kenntlichmachung der letzten Änderung in einer Gruppe
	 * @param group eines bestimmten users
	 * @param user 
	 * @return Boolean
	 * @throws IllegalArgumentException
	 */
	
	public Boolean changed(Group group, User user) throws IllegalArgumentException {

		Vector<Group> g = this.getGroupsByUser(user);

		if (g != null && group != null) {

			if (!g.equals(group)) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	/**
	 * Kenntlichmachung der letzten Änderung in einer Shoppinglist
	 * @param shoppingList
	 * @return Boolean
	 * @throws IllegalArgumentException
	 */
	
	public Boolean changed(ShoppingList shoppingList) throws IllegalArgumentException {

		ShoppingList sl = this.findShoppingListById(shoppingList.getId());

		if (sl != null) {

			if (!sl.equals(shoppingList)) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	/**
	 * Kenntlichmachung der letzten Änderung eines Listeneintrags
	 * @param group eines bestimmten users
	 * @param user 
	 * @return Boolean
	 * @throws IllegalArgumentException
	 */
	
	public Boolean changed(Vector<ListEntry> listEntry, ShoppingList shoppingList) throws IllegalArgumentException {

		Vector<ListEntry> le = this.getAllListEntriesByShoppingList(shoppingList);

		if (le != null) {

			if (!le.equals(listEntry)) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	/**
	 * Filtert Listeneinträge innerhalb von Shoppinglisten nach Händler
	 * @param store
	 * @return le
	 * @throws IllegalArgumentException
	 */
	
	public Vector<ListEntry> filterByStore(Store store)throws IllegalArgumentException{
		Vector<ListEntry> le= this.getAllListEntriesByStore(store);
		

		if(le != null) {
			return le;
		}
		
		return null;
	}
	
	/**
	 * Filtert Listeneinträge innerhalb von Shoppinglisten nach Einkäufer
	 * @param user
	 * @return le
	 * @throws IllegalArgumentException
	 */
	
	public Vector<ListEntry> filterByUser(User user, ShoppingList sl)throws IllegalArgumentException{
		Vector<ListEntry> le= this.getAllListEntriesByUser(user);
		

		if(le != null) {
			return le;
		}
		
		return null;
	}

	@Override
	public List<ListEntry> getEntriesByDate(Timestamp beginningDate) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Einen User einer Gruppe hinzufügen
	 * @param user 
	 * @param group 
	 * @throws IllegalArgumentException
	 */
	@Override
	public void addUser(User user, Group group) throws IllegalArgumentException {
		this.groupMapper.insertMembership(user.getId(), group.getId());
	}
	
	/**
	 * User aus der Gruppe entfernen
	 * @param user 
	 * @param group
	 * @throws IllegalArgumentException
	 */
	@Override
	public void removeUserMembership(User user, Group group) throws IllegalArgumentException {
		this.groupMapper.deleteMembership(user.getId(), group.getId());
}

	@Override
	public Vector<Article> getArticleByGroup(Group group) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Store> findStoreByGroup(Group group) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}



	
	


	
}
