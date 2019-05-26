
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
		UserMapper um=new UserMapper();
		
		Group wg= new Group();
		wg.setName("WG");
		
		//gm.insert(wg);
		
		User nico=new User();
		User leon = new User();
		
		leon.setName("Leon");
		leon.setUserName("LeonKek");
		leon.setGmail("leon.seiz@kekmail.de");
		leon.setGroupId(1);
		
		nico.setName("Nico");
		nico.setUserName("nico154");
		nico.setGmail("nicow154@googlemail.com");
		nico.setGroupId(1);
		
		//um.insert(leon);
		
		
	
		ListEntry le1 = new ListEntry();
		
		le1.setArticleId(1);
		le1.setAmount(5);
		le1.setName("1. Eintrag");
		//le1.setShoppinglistId(1);
		le1.setStoreId(1);
		le1.setUserId(1);
		
		
		
		//lem.insert(le1);
		
		
		
		
		//System.out.println(am.findByID(1));
		
		Article apfel = new Article();
		Article banane = new Article();
		Article spüli = new Article();
		Article klop = new Article();
		Article mehl = new Article();
		
		mehl.setName("Mehl");
		mehl.setUnit("gramm");
		
		am.delete(mehl);
		
		
		klop.setName("Klopapier");
		klop.setUnit("Packung");
		
		spüli.setName("Spülmittel");
		spüli.setUnit("St");
		
		apfel.setName("Apfel");
		apfel.setUnit("St");
		
		banane.setName("Banane");
		banane.setUnit("St");
		
		//am.update(klop);
		
		
		

		
		
		
		
		
		
		

	}

}
