package sharedShoppingList.client;

import com.google.gwt.user.client.rpc.AsyncCallback;



/**
 * The <code>LoginServiceAsync</code Interface will be implemented
 * on the client client-side??
 */

	public interface LoginServiceAsync {
		  public void login(String requestUri, AsyncCallback<LoginInfo> async);
		}
	

