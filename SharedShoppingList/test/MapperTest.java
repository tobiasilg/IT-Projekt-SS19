
import java.util.Date;

import sharedShoppingList.server.db.ArticleMapper;
import sharedShoppingList.shared.bo.Article;

public class MapperTest {

	public static void main(String[] args) {
		
		ArticleMapper am=new ArticleMapper();
		
		
		//System.out.println(am.findByID(1));
		
		Article apfel = new Article();
		Article banane = new Article();
		
		apfel.setName("Apfel");
		apfel.setUnit("St");
		
		banane.setName("Banane");
		banane.setUnit("St");
		
		
		am.insert(banane);

		
		
		
		
		
		
		

	}

}
