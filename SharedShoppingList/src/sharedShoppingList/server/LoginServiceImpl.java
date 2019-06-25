package sharedShoppingList.server;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import sharedShoppingList.client.LoginService;
import sharedShoppingList.client.SharedShoppingListEditorEntry.CurrentUser;
import sharedShoppingList.shared.LoginInfo;
import sharedShoppingList.shared.bo.User;


/*Info: Der Loginservice ist für die Anmeldeverwaltung des Nutzers zuständig.
 * 	@TODO:
 * 	import com.google.appengine.api.users.User;
 *	import com.google.appengine.api.users.UserService;
 *	import com.google.appengine.api.users.UserServiceFactory;
 */

/* 
 * Servlet, das den Login über das Google User Service API verwaltet 
 * und RemoteServiceServlet als Superklasse besitzt
*/

public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {
	
			
		
		private static final long serialVersionUID = 1L;
		
		public LoginInfo login(String requestUri) {
		//UserService userService = UserServiceFactory.getUserService();
		User user = CurrentUser.getUser();
		LoginInfo loginInfo = new LoginInfo();
		
		if (user != null) {
		  loginInfo.setLoggedIn(true);
		  loginInfo.setGmail(user.getGmail());
		  loginInfo.setUsername(user.getUsername());
//		  loginInfo.setLogoutUrl(CurrentUser.createLogoutURL(requestUri));
		} else {
		  loginInfo.setLoggedIn(false);
//		  loginInfo.setLoginUrl(CurrentUser.createLoginURL(requestUri));
		}
		return loginInfo;
		}

}

