package sharedShoppingList.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import sharedShoppingList.shared.bo.User;

/**
 * Interface, welches den Login realisiert.
 * 
 * 
 * @version 1.0
 * @see com.google.gwt.user.client.rpc.RemoteService
 * @see LoginServiceAsync
 */

@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService {

	/**
	 * @param requestUri
	 * @return
	 */
	public User login(String requestUri);

}
