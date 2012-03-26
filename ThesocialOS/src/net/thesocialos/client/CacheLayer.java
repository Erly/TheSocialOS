package net.thesocialos.client;


import java.util.LinkedHashMap;
import java.util.Map;

import net.thesocialos.client.helper.RPCXSRF;
import net.thesocialos.client.service.ContacsService;
import net.thesocialos.client.service.ContacsServiceAsync;
import net.thesocialos.client.service.UserServiceXSRF;
import net.thesocialos.client.service.UserServiceXSRFAsync;
import net.thesocialos.shared.model.Group;
import net.thesocialos.shared.model.Session;
import net.thesocialos.shared.model.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.objectify.Key;


public class CacheLayer {

	static User user;
	static Map<Key<User>, User> contacts = new LinkedHashMap<Key<User>, User>();
	static LinkedHashMap<String, Session> sessions;
	static Map<Key<Group>, Group> group = new LinkedHashMap<Key<Group>, Group>();
	
	private final static UserServiceXSRFAsync userService = GWT.create(UserServiceXSRF.class);
	private final static ContacsServiceAsync contactService = GWT.create(ContacsService.class);
	
	public static User getUser(Boolean onlyCached){
		if (user!= null){
			return user;
		}else{
			if (onlyCached){
				return null;
			}
			return null;
		}
		
	}
	public static boolean deleteUser(){
		user = null;
		return true;
	}
	public static void setUser(User user){
		CacheLayer.user = user;
	}
	
	
	
	public class UserCalls{
		
			
			
			
		
	}
	
	public class SessionCalls{
		
	}
	/**
	 * Se encarga de hacer las llamadas asincronas de los contactos
	 * @author vssnake
	 *
	 */
	public static class ContactCalls{
		public static User getContact(String email){
			return null;
		}
		public static void getContacts(boolean refeshContacts,AsyncCallback<Map<Key<User>,User>> callback){
			if (contacts.isEmpty() || refeshContacts){
				ContactCalls.getContacts(callback);
			}else{
				callback.onSuccess(contacts);
			}
		}
		public static Boolean addContact(){
			return null;
		}
		public static Boolean putAllContacts(){
			
			return null;
		}
		
		static private void getContacts(final AsyncCallback<Map<Key<User>,User>> callback){
			
			new RPCXSRF<Map<Key<User>,User>> (contactService) {

				@Override
				protected void XSRFcallService(AsyncCallback<Map<Key<User>,User>> cb) {
					contactService.getFriendsList(cb);
					
				}
				public void onSuccess(Map<Key<User>,User> contacts){
					
					CacheLayer.contacts = contacts;
					callback.onSuccess(contacts);
					
		
				}
				public void onFailure(Throwable caught){
					callback.onFailure(caught);
				}
				
			}.retry(3);
		
		}
		
	
	
	}
	public class GroupCalls{
		
	}
}
