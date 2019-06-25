package sharedShoppingList.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import sharedShoppingList.shared.bo.User;

public interface LoginServiceAsync {

	/**
	 * @param requestUri
	 * @param asyncCallback
	 */
	void login(String requestUri, AsyncCallback<User> asyncCallback);

}
