
import java.util.Date;

import sharedShoppingList.server.db.ArticleMapper;
import sharedShoppingList.server.db.GroupMapper;
import sharedShoppingList.server.db.ListEntryMapper;
import sharedShoppingList.server.db.StoreMapper;
import sharedShoppingList.server.db.UserMapper;
import sharedShoppingList.shared.bo.Article;
import sharedShoppingList.shared.bo.Group;
import sharedShoppingList.shared.bo.ListEntry;
import sharedShoppingList.shared.bo.Store;
import sharedShoppingList.shared.bo.User;

public class MapperTest {

	public static void main(String[] args) {
		
		ArticleMapper am=new ArticleMapper();
		ListEntryMapper lem=new ListEntryMapper();
		StoreMapper sm=new StoreMapper();
		//UserMapper um= new UserMapper();
		GroupMapper gm= new GroupMapper();
		
		Group wg= new Group();
		wg.setName("WG");
		
		gm.insert(wg);
		
		User nico=new User();
		
		nico.setName("Nico");
		nico.setUserName("nico154");
		/*
		 * ListEntry le1 = new ListEntry();
		
		le1.setArticleId(1);
		le1.setAmount(5);
		le1.setName("1. Eintrag");
		
		lem.insert(le1);
		 */
		
		
		
		//System.out.println(am.findByID(1));
		
		Article apfel = new Article();
		Article banane = new Article();
		Article spüli = new Article();
		Article klop = new Article();
		
		klop.setName("Klopapier");
		klop.setUnit("Pack");
		
		spüli.setName("Spülmittel");
		spüli.setUnit("St");
		
		apfel.setName("Apfel");
		apfel.setUnit("St");
		
		banane.setName("Banane");
		banane.setUnit("St");
		

		
		
		
		
		
		
		

	}

}
