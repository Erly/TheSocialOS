package net.thesocialos.client;


import java.util.LinkedHashMap;
import java.util.Map;

import net.thesocialos.client.helper.RPCXSRF;
import net.thesocialos.client.service.ContacsService;
import net.thesocialos.client.service.ContacsServiceAsync;
import net.thesocialos.client.service.UserService;
import net.thesocialos.client.service.UserServiceAsync;
import net.thesocialos.shared.model.Account;
import net.thesocialos.shared.model.Group;
import net.thesocialos.shared.model.Session;
import net.thesocialos.shared.model.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.objectify.Key;


public class CacheLayer {


	static User user;
	static Map<Key<User>, User> contacts = new LinkedHashMap<Key<User>, User>();
	static Map<Key<User>, User> users = new LinkedHashMap<Key<User>, User>();
	static LinkedHashMap<String, Session> sessions;
	static Map<Key<Group>, Group> group = new LinkedHashMap<Key<Group>, Group>();

	
	private final static UserServiceAsync userService = GWT.create(UserService.class);
	private final static ContacsServiceAsync contactService = GWT.create(ContacsService.class);	
	
	public static class UserCalls{
		
		static User user = null;
		static Map<Key<Account>, Account> accounts = null;
		
		public static User getUser(){
			return user;
		}
		
		public static boolean deleteUser(){
			user = null;
			return true;
		}
		
		public static void setUser(User user){
			CacheLayer.UserCalls.user = user;
		}
		
		public static void getAccounts(Boolean cached, final AsyncCallback<Map<Key<Account>, Account>> callback) {
			if (cached && null != accounts) {
				callback.onSuccess(accounts);
			}
			
			new RPCXSRF<Map<Key<Account>, Account>> (userService) {

				@Override
				protected void XSRFcallService(AsyncCallback<Map<Key<Account>, Account>> cb) {
					userService.getCloudAccounts(cb);
				}
				
				public void onSuccess(Map<Key<Account>, Account> accounts){
					CacheLayer.UserCalls.accounts = accounts;
					callback.onSuccess(accounts);
				}
				
				public void onFailure(Throwable caught){
					callback.onFailure(caught);
				}
				
			}.retry(3);
		}
		
		public static void setAccounts(Map<Key<Account>, Account> accounts) {
			CacheLayer.UserCalls.accounts = accounts;
		}
	}
	
	public static class SessionCalls{
		static LinkedHashMap<String, Session> sessions;
	}
	
	/**
	 * Se encarga de hacer las llamadas asincronas de los contactos
	 * @author vssnake
	 */
	public static class ContactCalls{
		
		static Map<Key<User>, User> contacts = new LinkedHashMap<Key<User>, User>();
		
		public static User getContact(String email){
			return null;
		}
		
		public static void getContacts(boolean cached, AsyncCallback<Map<Key<User>,User>> callback){
			if (contacts.isEmpty() || !cached){
				ContactCalls.getContacts(callback);
			}else{
				callback.onSuccess(contacts);
			}
		}

		public static void getUsers(boolean refreshUsers,AsyncCallback<Map<Key<User>,User>> callback){
			if (users.isEmpty() || refreshUsers){
				
			}
		}

		public static Boolean addContact(){
			return null;
		}
		
		public static Boolean putAllContacts(){
			
			return null;
		}


		
		static private void getContacts(final AsyncCallback<Map<Key<User>, User>> callback){
			new RPCXSRF<Map<Key<User>, User>> (contactService) {


				@Override
				protected void XSRFcallService(AsyncCallback<Map<Key<User>, User>> cb) {
					contactService.getFriendsList(cb);
				}
				
				public void onSuccess(Map<Key<User>, User> contacts){
					CacheLayer.ContactCalls.contacts = contacts;
					callback.onSuccess(contacts);
				}
				
				public void onFailure(Throwable caught){
					callback.onFailure(caught);
				}
				
			}.retry(3);
		}
		private static void getUsers(final AsyncCallback<Map<Key<User>,User>> callback){
			
		}
	}
	

	
	

	public static class GroupCalls{

		
		static Map<Key<Group>, Group> group = new LinkedHashMap<Key<Group>, Group>();
	}
}
