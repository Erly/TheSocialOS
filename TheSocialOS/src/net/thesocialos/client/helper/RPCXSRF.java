package net.thesocialos.client.helper;

import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.event.RPCInEvent;
import net.thesocialos.client.event.RPCOutEvent;
import net.thesocialos.client.service.ServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestTimeoutException;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.HasRpcToken;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.rpc.RpcTokenException;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.rpc.XsrfToken;
import com.google.gwt.user.client.rpc.XsrfTokenService;
import com.google.gwt.user.client.rpc.XsrfTokenServiceAsync;

public abstract class RPCXSRF<T> implements AsyncCallback<T> {
	
	private final XsrfTokenServiceAsync xsrf = (XsrfTokenServiceAsync) GWT.create(XsrfTokenService.class);
	
	private final ServiceAsync service;
	
	public RPCXSRF(ServiceAsync service) {
		this.service = service;
	}
	
	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Fires an event indicating that the RPC request has finished.
	 */
	private void onRPCIn() {
		TheSocialOS.getEventBus().fireEvent(new RPCInEvent());
	}
	
	/**
	 * Fires an event indicating that the RPC request has started. A loading indicator will appear on the screen
	 * indicating that the system is working.
	 */
	private void onRPCOut() {
		TheSocialOS.getEventBus().fireEvent(new RPCOutEvent());
	}
	
	@Override
	public void onSuccess(T result) {
		// TODO Auto-generated method stub
		
	}
	
	public void retry(int retry) {
		XSRFService(retry);
	}
	
	protected abstract void XSRFcallService(AsyncCallback<T> cb);
	
	private void XSRFService(int retry) {
		onRPCOut(); // RPC Working
		((ServiceDefTarget) xsrf).setServiceEntryPoint(GWT.getModuleBaseURL() + "xsrf");
		xsrf.getNewXsrfToken(new AsyncCallback<XsrfToken>() {
			
			@Override
			public void onFailure(Throwable caught) {
				onRPCIn(); // RPC finished working
				try {
					throw caught;
				} catch (RpcTokenException e) {
					e.printStackTrace();
					// Can be thrown for several reasons:
					// - duplicate session cookie, which may be a sign of a cookie
					// overwrite attack
					// - XSRF token cannot be generated because session cookie isn't
					// present
				} catch (Throwable e) {
					e.printStackTrace();
					// unexpected
				}
			}
			
			@Override
			public void onSuccess(XsrfToken token) {
				// onRPCIn(); // RPC finished working
				((HasRpcToken) service).setRpcToken(token);
				XSRFcallService(new AsyncCallback<T>() {
					
					@Override
					public void onFailure(Throwable caught) {
						onRPCIn(); // RPC finished working
						GWT.log(caught.getMessage(), caught);
						try {
							throw caught;
						} catch (IncompatibleRemoteServiceException irsException) { // The app is out of date
							Window.alert(TheSocialOS.getConstants().error_AppOutOfDate());
						} catch (SerializationException serializationException) { //
							Window.alert(TheSocialOS.getConstants().error_Serialization());
							// } catch (NotLoggedInException nliException) {
							// User not loged in redirect to login page.
						} catch (RequestTimeoutException timeoutException) {
							Window.alert(TheSocialOS.getConstants().error_Timeout());
						} catch (InvocationException invocationException) {
							RPCXSRF.this.onFailure(invocationException); // propagate exception
						} catch (Throwable e) {
							RPCXSRF.this.onFailure(e);
						}
					}
					
					@Override
					public void onSuccess(T result) {
						onRPCIn(); // RPC finished working
						RPCXSRF.this.onSuccess(result);
					}
				});
				
			}
		});
	}
	
}
