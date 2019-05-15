package sharedShoppingList.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import sharedShoppingList.client.LoginService;
import sharedShoppingList.shared.LoginInfo;

/*Info (muss noch hinzugefügt werden)
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
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		LoginInfo loginInfo = new LoginInfo();
		
		if (user != null) {
		  loginInfo.setLoggedIn(true);
		  loginInfo.setEmailAddress(user.getGmail());
		  loginInfo.setNickname(user.getUserName());
		  loginInfo.setLogoutUrl(userService.createLogoutURL(requestUri));
		} else {
		  loginInfo.setLoggedIn(false);
		  loginInfo.setLoginUrl(userService.createLoginURL(requestUri));
		}
		return loginInfo;
		}

}
