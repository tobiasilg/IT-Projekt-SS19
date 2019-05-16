package sharedShoppingList.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;


import sharedShoppingList.server.db.ArticleMapper;
import sharedShoppingList.server.db.GroupMapper;
import sharedShoppingList.server.db.ListEntryMapper;
import sharedShoppingList.server.db.ListMapper;
import sharedShoppingList.server.db.StoreMapper;
import sharedShoppingList.server.db.UserMapper;
import sharedShoppingList.shared.Einkaufslistenverwaltung;
import sharedShoppingList.shared.bo.Store;

/**
 * Die Klasse <code>EinkaufslistenverwaltungImpl</code> implementiert das Interface
 * Einkaufslistenverwaltung. In der Klasse ist neben der ReportClientImpl sämtliche
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
		 * Um mit der Datenbank kommunizieren zu können benötigt die Klasse
		 * EinkaufslistenverwaltungImpl einen vollständigen Satz von Mappern.
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
	 * ABSCHNITT, Beginn: Methoden für Article Objekte
	 * 
	 * *************************
	 **/
	
	
	
	/**
	 * *************************
	 * ABSCHNITT, Beginn: Methoden für Store Objekte
	 * 
	 * *************************
	 **/
	public Store createStore (String name) throws IllegalArgumentException {
		Store store = new Store();
		/*
		 * Setzen einer vorl�ufigen Storenr.
		 * Der insert-Aufruf liefert dann ein Objekt, dessen Nummer mit der Datenbank konsistent ist.
		 */
		store.setId(1);
		store.setName(name);
		
		return this.storeMapper.insert(store);
	}
	
	public void deleteStore (Store store) throws IllegalArgumentException{
		
		/*
		 * L�schweitergabe zu kl�ren (falls store in ListEntry vorhanden, was passiert?)
		 */
		
		this.storeMapper.delete(store);

	}
	
	

}
