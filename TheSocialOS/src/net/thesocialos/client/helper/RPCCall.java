package net.thesocialos.client.helper;

import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.event.RPCInEvent;
import net.thesocialos.client.event.RPCOutEvent;
import net.thesocialos.client.i18n.SocialOSConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestTimeoutException;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.rpc.SerializationException;

public abstract class RPCCall<T> implements AsyncCallback<T> {

	protected abstract void callService(AsyncCallback<T> cb);
	
	public RPCCall() {}

	private void call(final int retriesLeft) {
		onRPCOut(); // RPC Working
		
		callService(new AsyncCallback<T>() {
			
			@Override
			public void onFailure(Throwable caught) {
				onRPCIn(); // RPC finished working
				GWT.log(caught.getMessage(), caught);
				try {
					throw caught;
				} catch (InvocationException invocationException) {
					if (retriesLeft <= 0) {
						RPCCall.this.onFailure(invocationException); // propagate exception
					} else {
						call(retriesLeft - 1); // retry call
					}
				} catch (IncompatibleRemoteServiceException irsException) {
					Window.alert(TheSocialOS.getConstants().error_AppOutOfDate());
				} catch (SerializationException serializationException) {
					Window.alert(TheSocialOS.getConstants().error_Serialization());
				//} catch (NotLoggedInException nliException) {
				// 		User not loged in redirect to login page.
				} catch (RequestTimeoutException timeoutException) {
					Window.alert(TheSocialOS.getConstants().error_Timeout());
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
	
	private void onRPCIn() {
		TheSocialOS.get().getEventBus().fireEvent(new RPCInEvent());
	}
	
	private void onRPCOut() {
		TheSocialOS.get().getEventBus().fireEvent(new RPCOutEvent());
	}
	
	public void retry(int retryCount) {
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
