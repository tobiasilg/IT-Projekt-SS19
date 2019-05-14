package sharedShoppingList.server;

import org.eclipse.jetty.security.LoginService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import sharedShoppingList.shared.LoginInfo;

public class LoginServiceImpl extends RemoteServiceServlet implements
LoginService {

public LoginInfo login(String requestUri) {
UserService userService = UserServiceFactory.getUserService();
User user = userService.getCurrentUser();
LoginInfo loginInfo = new LoginInfo();

if (user != null) {
  loginInfo.setLoggedIn(true);
  loginInfo.setEmailAddress(user.getEmail());
  loginInfo.setNickname(user.getNickname());
  loginInfo.setLogoutUrl(userService.createLogoutURL(requestUri));
} else {
  loginInfo.setLoggedIn(false);
  loginInfo.setLoginUrl(userService.createLoginURL(requestUri));
}
return loginInfo;
}

}

