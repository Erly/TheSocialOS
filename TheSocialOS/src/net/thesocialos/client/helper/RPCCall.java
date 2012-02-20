package net.thesocialos.client.helper;

import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.event.RPCInEvent;
import net.thesocialos.client.event.RPCOutEvent;
import net.thesocialos.client.service.UserService;
import net.thesocialos.client.service.UserServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestTimeoutException;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.rpc.SerializationException;

public abstract class RPCCall<T> implements AsyncCallback<T> {

	protected abstract void callService(AsyncCallback<T> cb);
	
	private final UserServiceAsync userService = GWT.create(UserService.class);
	
	public RPCCall() {}

	/**
	 * Makes a request to check that the user sessionID and userID are correct and if they are correct it makes the call to onCall to method passing the number of retries.
	 * @param retriesLeft Number of attempts left to try before throwing an error. 
	 */
	private void call(final int retriesLeft) {
		onRPCOut(); // RPC Working
		boolean isLoginOrRegister = false;
		if (History.getToken().equals("login") || History.getToken().equals("register")) {
			isLoginOrRegister = true;
			onCall(retriesLeft);
		}
		
		if(!isLoginOrRegister)
			userService.checkIds(TheSocialOS.get().getJSessionID(), TheSocialOS.get().getSessionID(), TheSocialOS.get().getUserID(), new AsyncCallback<Boolean>() {
				
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					GWT.log(caught.getMessage(), caught);
				}

				@Override
				public void onSuccess(Boolean result) {
					if (result)
						onCall(retriesLeft);
					else {
						onRPCIn();
						History.newItem("login");
					}
					/*if( !result )
						throw new UserNotLoggedInException();*/
				}
			});
		
	}
	
	/**
	 * It makes the main request to the server and controls some generic exceptions. In case of failure it calls itself again with retriesLeft-1 until retriesLeft is 0.
	 * @param retriesLeft Number of attempts left to try before throwing an error.
	 */
	private void onCall(final int retriesLeft) {
		callService(new AsyncCallback<T>() {
			
			@Override
			public void onFailure(Throwable caught) {
				onRPCIn(); // RPC finished working
				GWT.log(caught.getMessage(), caught);
				try {
					throw caught;
				} catch (IncompatibleRemoteServiceException irsException) {	// The app is out of date
					Window.alert(TheSocialOS.getConstants().error_AppOutOfDate());
				} catch (SerializationException serializationException) {	// 
					Window.alert(TheSocialOS.getConstants().error_Serialization());
				//} catch (NotLoggedInException nliException) {
					// 		User not loged in redirect to login page.
				} catch (RequestTimeoutException timeoutException) {
					Window.alert(TheSocialOS.getConstants().error_Timeout());
				} catch (InvocationException invocationException) {
					if (retriesLeft <= 0) {
						RPCCall.this.onFailure(invocationException); // propagate exception
					} else {
						onCall(retriesLeft - 1); // retry call
					}
				} catch (Throwable e) {
					RPCCall.this.onFailure(e);
				}
			}

			@Override
			public void onSuccess(T result) {
				onRPCIn(); // RPC finished working
				RPCCall.this.onSuccess(result);
			}});
	}
	
	/**
	 * Fires an event indicating that the RPC request has finished.
	 */
	private void onRPCIn() {
		TheSocialOS.get().getEventBus().fireEvent(new RPCInEvent());
	}
	
	/**
	 * Fires an event indicating that the RPC request has started. A loading indicator will appear on the screen indicating that the system is working.
	 */
	private void onRPCOut() {
		TheSocialOS.get().getEventBus().fireEvent(new RPCOutEvent());
	}
	
	/**
	 * The main method to make the request.
	 * @param retryCount Number of attempts to try a request before returning an error. 
	 */
	public void retry(int retryCount) { // Public method to call the RPCCall
		call(retryCount);
	}
	
	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess(T result) {
		// TODO Auto-generated method stub
		
	}

}
