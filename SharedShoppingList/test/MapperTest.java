
import java.util.Date;

import sharedShoppingList.server.db.ArticleMapper;
import sharedShoppingList.shared.bo.Article;

public class MapperTest {

	public static void main(String[] args) {
		
		ArticleMapper am=new ArticleMapper();
		
		
		//System.out.println(am.findByID(1));
		
		Article apfel = new Article();
		
		apfel.setName("Apfel");
		apfel.setId(2);
		apfel.setCreateDate(new Timestamp());
		
		
		
		
		
		
		
		

	}

}
