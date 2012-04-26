package net.thesocialos.server;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.allen_sauer.gwt.log.client.Log;

import net.thesocialos.shared.model.User;

import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.RpcTokenException;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.user.server.rpc.UnexpectedException;

public class ServiceSecurityLayer extends RemoteServiceServlet {
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() {
		// checkSession();
		
	}
	
	/**
	 * The implementation of the service.
	 */
	private final Object delegate;
	
	/**
	 * The default constructor used by service implementations that extend this class. The servlet will delegate AJAX
	 * requests to the appropriate method in the subclass.
	 */
	public ServiceSecurityLayer() {
		this.delegate = this;
	}
	
	/**
	 * The wrapping constructor used by service implementations that are separate from this class. The servlet will
	 * delegate AJAX requests to the appropriate method in the given object.
	 */
	public ServiceSecurityLayer(Object delegate) {
		this.delegate = delegate;
	}
	
	/**
	 * 
	 * 
	 * 
	 * Process a call originating from the given request. Uses the
	 * {@link RPC#invokeAndEncodeResponse(Object, java.lang.reflect.Method, Object[])} method to do the actual work.
	 * <p>
	 * Subclasses may optionally override this method to handle the payload in any way they desire (by routing the
	 * request to a framework component, for instance). The {@link HttpServletRequest} and {@link HttpServletResponse}
	 * can be accessed via the {@link #getThreadLocalRequest()} and {@link #getThreadLocalResponse()} methods.
	 * </p>
	 * This is public so that it can be unit tested easily without HTTP.
	 * 
	 * The additional code verify if you don't alter the Id session or UserCode
	 * 
	 * @param payload
	 *            the UTF-8 request payload
	 * @return a string which encodes either the method's return, a checked exception thrown by the method, or an
	 *         {@link IncompatibleRemoteServiceException}
	 * @throws SerializationException
	 *             if we cannot serialize the response
	 * @throws UnexpectedException
	 *             if the invocation throws a checked exception that is not declared in the service method's signature
	 * @throws RuntimeException
	 *             if the service method throws an unchecked exception (the exception will be the one thrown by the
	 *             service)
	 */
	@Override
	public String processCall(String payload) throws SerializationException {
		// First, check for possible XSRF situation
		checkPermutationStrongName();
		
		try {
			RPCRequest rpcRequest = RPC.decodeRequest(payload, delegate.getClass(), this);
			onAfterRequestDeserialized(rpcRequest);
			if (checkSession(rpcRequest) == null) { // Check the intrusion
				return RPC.encodeResponseForFailure(null, new IncompatibleRemoteServiceException(
						"La excepción de los huevos", new Throwable("Warning full UP")));
			}
			return RPC.invokeAndEncodeResponse(delegate, rpcRequest.getMethod(), rpcRequest.getParameters(),
					rpcRequest.getSerializationPolicy(), rpcRequest.getFlags());
		} catch (IncompatibleRemoteServiceException ex) {
			log("An IncompatibleRemoteServiceException was thrown while processing this call.", ex);
			return RPC.encodeResponseForFailure(null, ex);
		} catch (RpcTokenException tokenException) {
			log("An RpcTokenException was thrown while processing this call.", tokenException);
			return RPC.encodeResponseForFailure(null, tokenException);
		}
	}
	
	/**
	 * Verify if you have sessions id in server sessions
	 * 
	 * @return
	 */
	protected User checkSession() {
		
		String sid = (String) getThreadLocalRequest().getSession().getAttribute("SID");
		String uid = (String) getThreadLocalRequest().getSession().getAttribute("UID");
		
		// return checkSession(sid,uid);
		return null;
		
	}
	
	/**
	 * 
	 * @param sid
	 *            ID of session
	 * @param uid
	 *            Code of user
	 * @return The user
	 */
	protected User checkSession(String sid, String uid) {
		/*
		 * if (sid == null || uid == null){ return null; } PersistenceManager pm = PMF.get().getPersistenceManager();
		 * try{ User user = pm.getObjectById(User.class, uid); // Session session = new SearchJDO().searchSession(user,
		 * sid); //Log.debug(session.getUser().toString()); // if (session != null){ Log.debug("SecurityLayer-->usid: "
		 * + session.getSessionID()); Log.debug("SecurityLayer-->sid:  " + sid); if (user == null ||
		 * !session.getSessionID().equals(sid)) {//Verifing session id Log.debug("SecurityLayer-->session altered");
		 * return null; }else{ return user; } } } finally { pm.close(); } Log.debug("Session null"); return null;
		 */
		return null;
	}
	
	/**
	 * The layer
	 * 
	 * @param rpcRequest
	 * @return
	 */
	private Boolean checkSession(RPCRequest rpcRequest) {
		System.out.println(rpcRequest.getMethod().getDeclaringClass().getName());
		if (rpcRequest.getMethod().getName().equals("login") || rpcRequest.getMethod().getName().equals("logout")
				|| rpcRequest.getMethod().getName().equals("register") /*
																		 * || rpcRequest.getMethod().getName().equals(
																		 * "getLoggedUser")
																		 */) {
			return true;
		} else {
			if (checkSession() != null) {
				return true;
			} else {
				return false;
			}
			
		}
		
	}
	
}
