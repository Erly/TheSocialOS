package net.thesocialos.client;

import java.util.LinkedHashMap;
import java.util.Map;

import net.thesocialos.client.helper.RPCXSRF;
import net.thesocialos.client.service.ContacsService;
import net.thesocialos.client.service.ContacsServiceAsync;
import net.thesocialos.client.service.UserService;
import net.thesocialos.client.service.UserServiceAsync;
import net.thesocialos.shared.model.Account;
import net.thesocialos.shared.model.Columns;
import net.thesocialos.shared.model.Group;
import net.thesocialos.shared.model.Session;
import net.thesocialos.shared.model.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.objectify.Key;

public class CacheLayer {
	
	static User user = null;
	private static Map<Key<User>, User> contacts = new LinkedHashMap<Key<User>, User>();
	
	// Usuarios de la aplicaci�n
	private static Map<String, User> users = new LinkedHashMap<String, User>();
	private static LinkedHashMap<String, Session> sessions;
	
	private static Map<Key<Group>, Group> group = new LinkedHashMap<Key<Group>, Group>();
	
	private static Map<String, User> petitionsContacts = new LinkedHashMap<String, User>();
	
	private final static UserServiceAsync userService = GWT.create(UserService.class);
	
	private final static ContacsServiceAsync contactService = GWT.create(ContacsService.class);
	
	/**
	 * Se encarga de hacer las llamadas asincronas de los contactos
	 * 
	 * @author vssnake
	 */
	public static class ContactCalls {
		/**
		 * Accept a contact
		 * 
		 * @param contact
		 * @param callback
		 *            true success | false error
		 */
		public static void acceptAContact(final User contact, final AsyncCallback<Boolean> callback) {
			new RPCXSRF<Boolean>(contactService) {
				
				@Override
				public void onFailure(Throwable caught) {
					callback.onFailure(caught);
				}
				
				@Override
				public void onSuccess(Boolean result) {
					callback.onSuccess(result);
				}
				
				@Override
				protected void XSRFcallService(AsyncCallback<Boolean> cb) {
					// TODO Auto-generated method stub
					contactService.acceptContact(contact.getEmail(), cb);
				}
			}.retry(3);
		}
		
		public static Boolean addContact() {
			return null;
		}
		
		/**
		 * A�ade un usuario una peticion del usuario logeado
		 * 
		 * @param callback
		 *            true si a tenido exito
		 * @param contactUser
		 *            El usuario a que enviar la petici�n
		 */
		public static void addPetitionContact(final User contactUser, final AsyncCallback<Boolean> callback) {
			new RPCXSRF<Boolean>(contactService) {
				
				@Override
				public void onFailure(Throwable caught) {
					callback.onFailure(caught);
				}
				
				@Override
				public void onSuccess(Boolean success) {
					callback.onSuccess(success);
				}
				
				@Override
				protected void XSRFcallService(AsyncCallback<Boolean> cb) {
					contactService.addPetitionContact(contactUser, cb);
				}
			}.retry(3);
		}
		
		/**
		 * Deny a contact
		 * 
		 * @param contact
		 * @param callback
		 *            true success | false error
		 */
		public static void denyAContact(final User contact, final AsyncCallback<Boolean> callback) {
			new RPCXSRF<Boolean>(contactService) {
				
				@Override
				public void onFailure(Throwable caught) {
					callback.onFailure(caught);
				}
				
				@Override
				public void onSuccess(Boolean result) {
					callback.onSuccess(result);
				}
				
				@Override
				protected void XSRFcallService(AsyncCallback<Boolean> cb) {
					// TODO Auto-generated method stub
					contactService.denyContact(contact.getEmail(), cb);
				}
			}.retry(3);
		}
		
		public static User getContact(String email) {
			return null;
		}
		
		/**
		 * Llamada asincrona para recoger los contactos del servidor
		 * 
		 * @param callback
		 */
		private static void getContactPetitions(final AsyncCallback<Map<String, User>> callback) {
			new RPCXSRF<Map<String, User>>(contactService) {
				
				@Override
				public void onFailure(Throwable caught) {
					callback.onFailure(caught);
				}
				
				@Override
				public void onSuccess(Map<String, User> petitionsContacts) {
					
					CacheLayer.petitionsContacts = petitionsContacts;
					callback.onSuccess(petitionsContacts);
				}
				
				@Override
				protected void XSRFcallService(AsyncCallback<Map<String, User>> cb) {
					contactService.getPetitionContact(cb);
					
				}
			}.retry(3);
		}
		
		public static void getContactPetitions(boolean cached, final AsyncCallback<Map<String, User>> callback) {
			if (petitionsContacts.isEmpty() || !cached) getContactPetitions(callback);
			else
				callback.onSuccess(petitionsContacts);
		}
		
		/**
		 * Llamada asincrona para recoger los contactos del servidor
		 * 
		 * @param callback
		 */
		static private void getContacts(final AsyncCallback<Map<Key<User>, User>> callback) {
			new RPCXSRF<Map<Key<User>, User>>(contactService) {
				
				@Override
				public void onFailure(Throwable caught) {
					callback.onFailure(caught);
				}
				
				@Override
				public void onSuccess(Map<Key<User>, User> contacts) {
					CacheLayer.contacts = contacts;
					callback.onSuccess(contacts);
				}
				
				@Override
				protected void XSRFcallService(AsyncCallback<Map<Key<User>, User>> cb) {
					contactService.getFriendsList(cb);
				}
				
			}.retry(3);
		}
		
		/**
		 * Obtiene los contactos
		 * 
		 * @param cached
		 *            if true si se quiere cojer los cacheados
		 * @param callback
		 */
		public static void getContacts(boolean cached, AsyncCallback<Map<Key<User>, User>> callback) {
			if (contacts.isEmpty() || !cached) ContactCalls.getContacts(callback);
			else
				callback.onSuccess(contacts);
		}
		
		/**
		 * Obtienes el numero de peticiones en espera
		 * 
		 * @return numero de peticiones en int
		 */
		public static int getCountPetitionsContanct() {
			return petitionsContacts.size();
		}
		
		/**
		 * Llamada asincrona para recoger los usuarios del servidor
		 * 
		 * @param callback
		 */
		private static void getUsers(final AsyncCallback<Map<String, User>> callback) {
			new RPCXSRF<Map<String, User>>(contactService) {
				
				@Override
				public void onFailure(Throwable caught) {
					callback.onFailure(caught);
				}
				
				@Override
				public void onSuccess(Map<String, User> users) {
					CacheLayer.users = users;
					callback.onSuccess(users);
				}
				
				@Override
				protected void XSRFcallService(AsyncCallback<Map<String, User>> cb) {
					contactService.getUsers(cb);
					
				}
			}.retry(3);
			
		}
		
		/**
		 * Obtiene los usuarios del servidor
		 * 
		 * @param cached
		 *            if true si se quiere cojer los cacheados
		 * @param callback
		 */
		public static void getUsers(boolean cached, AsyncCallback<Map<String, User>> callback) {
			if (users.isEmpty() || !cached) getUsers(callback);
			else
				callback.onSuccess(users);
		}
		
		public static Boolean putAllContacts() {
			
			return null;
		}
		
		public static void updateContacts(final AsyncCallback<Boolean> callback) {
			getContacts(new AsyncCallback<Map<Key<User>, User>>() {
				
				@Override
				public void onFailure(Throwable caught) {
					
					if (callback != null) callback.onSuccess(false);
				}
				
				@Override
				public void onSuccess(Map<Key<User>, User> result) {
					
					contacts = result;
					if (callback != null) callback.onSuccess(true);
				}
			});
		}
		
		public static void updatePetitionsContacts(final AsyncCallback<Boolean> callback) {
			getContactPetitions(new AsyncCallback<Map<String, User>>() {
				
				@Override
				public void onFailure(Throwable caught) {
					
					if (callback != null) callback.onSuccess(false);
				}
				
				@Override
				public void onSuccess(Map<String, User> result) {
					petitionsContacts = result;
					if (callback != null) callback.onSuccess(true);
					
				}
			});
		}
		
	}
	
	public static class GroupCalls {
		
	}
	
	public static class SessionCalls {
		
	}
	
	public static class UserCalls {
		
		private static Map<Key<Account>, Account> accounts = null;
		private static Map<Key<Columns>, Columns> columns = null;
		
		public static boolean deleteUser() {
			user = null;
			return true;
		}
		
		public static Map<Key<Account>, Account> getAccounts() {
			return accounts;
		}
		
		public static Map<Key<Columns>, Columns> getColumns() {
			return columns;
		}
		
		public static User getUser() {
			return user;
		}
		
		public static void refreshAccounts() {
			new RPCXSRF<Map<Key<Account>, Account>>(userService) {
				
				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}
				
				@Override
				public void onSuccess(Map<Key<Account>, Account> accounts) {
					CacheLayer.UserCalls.setAccounts(accounts);
				}
				
				@Override
				protected void XSRFcallService(AsyncCallback<Map<Key<Account>, Account>> cb) {
					userService.getCloudAccounts(cb);
				}
			}.retry(3);
		}
		
		public static void refreshColumns() {
			new RPCXSRF<Map<Key<Columns>, Columns>>(userService) {
				
				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}
				
				@Override
				public void onSuccess(Map<Key<Columns>, Columns> columns) {
					CacheLayer.UserCalls.setColumns(columns);
				}
				
				@Override
				protected void XSRFcallService(AsyncCallback<Map<Key<Columns>, Columns>> cb) {
					userService.getDeckColumns(cb);
				}
			}.retry(3);
		}
		
		public static void getNewChannelId(final AsyncCallback<Void> callback) {
			new RPCXSRF<String>(userService) {
				
				@Override
				protected void XSRFcallService(AsyncCallback<String> cb) {
					System.out.println("callback");
					userService.getChannel(cb);
					
				}
				
				@Override
				public void onSuccess(String channelIDToken) {
					getUser().setTokenChannel(channelIDToken);
					callback.onSuccess(null);
				}
				
				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}
			}.retry(3);
		}
		
		public static void setAccounts(Map<Key<Account>, Account> accounts) {
			CacheLayer.UserCalls.accounts = accounts;
		}
		
		public static void setColumns(Map<Key<Columns>, Columns> columns) {
			CacheLayer.UserCalls.columns = columns;
		}
		
		public static void setUser(User user) {
			CacheLayer.user = user;
		}
	}
	
}
