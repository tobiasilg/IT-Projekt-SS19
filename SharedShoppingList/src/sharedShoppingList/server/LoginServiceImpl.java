package sharedShoppingList.server;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import sharedShoppingList.server.db.UserMapper;
import sharedShoppingList.shared.LoginService;
import sharedShoppingList.shared.bo.User;


     /** Hier wird die login Methode implementiert.
     * Diese prüft ob ein <code>User<code> dem System bekannt ist.
     * Ist dies der Fall, werden die <code>User<code> Attribute für das Objekt gesetzt.
     * Ansonsten wird ein neuer Datensatz in die Datenbank geschrieben und der <code>User<code> eingeloggt.
     * Ist der <code>User<code> nicht in mit seinem Google Account eingeloggt, 
     * wird ein LoginLink für das GoogleUserServiceAPI erstellt.
     * 
     * @author Nico Weiler, Patrick Treiber
     * @param requestUri die Domain der Startseite
     * @return neuer oder eingeloggter User
     */

public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {
	
			
		
		private static final long serialVersionUID = 1L;

		@Override
		public User login(String requestUri) {
			
			UserService userService= UserServiceFactory.getUserService();
			com.google.appengine.api.users.User googleUser = userService.getCurrentUser(); 
			User user = new User();
			
			/**
	         * Wenn der User<code> mit seinem Google Account eingeloggt ist, wird überprüft, 
	         * ob dieser unserem Kekbuy System bekannt ist.
	         * 
	         */
			if (googleUser != null) {

				 User existU = UserMapper.userMapper().findByGmail(googleUser.getEmail());
				
				/*
				 * Falls der User dem System bekannt ist, wird dieser eingeloggt.
				 */
				if(existU!=null) {
					
					existU.setLoggedIn(true);
					existU.setLogoutUrl(userService.createLogoutURL(requestUri));
		
					return existU; 
				}
				
				 /**
				  * Falls der <code>User<code> sich zum ersten Mal am System anmeldet, 
				  * wird ein neuer Datensatz in die Datenbank geschrieben.
				  */
			
				user.setLoggedIn(true);
				user.setLogoutUrl(userService.createLogoutURL(requestUri));
				user.setGmail(googleUser.getEmail());
				UserMapper.userMapper().insert(user);
				
				}	

			user.setLoginUrl(userService.createLoginURL(requestUri));
			
			return user;
		}
		
	

}

