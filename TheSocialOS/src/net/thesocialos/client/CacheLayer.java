package net.thesocialos.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.thesocialos.client.api.Media;
import net.thesocialos.client.event.AccountUpdateEvent;
import net.thesocialos.client.event.AvatarUpdateEvent;
import net.thesocialos.client.event.ContactsChangeEvent;
import net.thesocialos.client.event.ContactsPetitionChangeEvent;
import net.thesocialos.client.helper.RPCXSRF;
import net.thesocialos.client.service.ContacsService;
import net.thesocialos.client.service.ContacsServiceAsync;
import net.thesocialos.client.service.UserService;
import net.thesocialos.client.service.UserServiceAsync;
import net.thesocialos.shared.ChannelApiEvents.ChApiChatUserChngState.STATETYPE;
import net.thesocialos.shared.model.Account;
import net.thesocialos.shared.model.Columns;
import net.thesocialos.shared.model.Group;
import net.thesocialos.shared.model.Session;
import net.thesocialos.shared.model.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
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
		 * CHECK if this user has in your contacts
		 * 
		 * @param emailTocontact
		 * @return true if is | false if not
		 */
		public static boolean isContact(Key<User> userKey) {
			return contacts.containsKey(userKey);
		}
		
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
		
		public static Key<User> getContactKey(String email) {
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
					TheSocialOS.getEventBus().fireEvent(new ContactsPetitionChangeEvent());
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
		
		public static void getContact(final Key<User> contact, final AsyncCallback<User> callback) {
			if (!CacheLayer.contacts.containsKey(contact)) getContacts(new AsyncCallback<Map<Key<User>, User>>() {
				
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onSuccess(Map<Key<User>, User> result) {
					callback.onSuccess(result.get(contact));
					
				}
			});
			else
				callback.onSuccess(CacheLayer.contacts.get(contact));
			
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
					TheSocialOS.getEventBus().fireEvent(new ContactsChangeEvent());
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
		
		public static void getContactsWithoutKey(boolean cached, final AsyncCallback<Map<String, User>> callback) {
			if (contacts.isEmpty() || !cached) ContactCalls.getContacts(new AsyncCallback<Map<Key<User>, User>>() {
				
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onSuccess(Map<Key<User>, User> result) {
					Set<Entry<Key<User>, User>> set = result.entrySet();
					Iterator<Entry<Key<User>, User>> i = set.iterator();
					Map<String, User> resultWhitoutKey = new HashMap<String, User>();
					
					while (i.hasNext()) {
						Entry<Key<User>, User> me = i.next();
						
						User user = me.getValue();
						user.setOwnKey(me.getKey());
						resultWhitoutKey.put(user.getEmail(), user);
					}
					callback.onSuccess(resultWhitoutKey);
					
				}
			});
			else {
				Set<Entry<Key<User>, User>> set = contacts.entrySet();
				Iterator<Entry<Key<User>, User>> i = set.iterator();
				Map<String, User> resultWhitoutKey = new HashMap<String, User>();
				
				while (i.hasNext()) {
					Entry<Key<User>, User> me = i.next();
					
					User user = me.getValue();
					user.setOwnKey(me.getKey());
					resultWhitoutKey.put(user.getEmail(), user);
				}
				callback.onSuccess(resultWhitoutKey);
			}
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
					if (callback != null) // TheSocialOS.getEventBus().fireEvent(new ContactsChangeEvent());
						callback.onSuccess(true);
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
		
		public static void setChatState(final STATETYPE stateType, final String customMsg,
				final AsyncCallback<Void> callback) {
			new RPCXSRF<Void>(userService) {
				
				@Override
				protected void XSRFcallService(AsyncCallback<Void> cb) {
					userService.setState(stateType, customMsg, cb);
					
				}
				
				@Override
				public void onSuccess(Void nothing) {
					getUser().chatState = stateType;
					// Falta custom State
					callback.onSuccess(nothing);
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
		
		public static Map<Key<Account>, Account> getAccounts() {
			return accounts;
		}
		
		public static void getAccounts(boolean cached, final AsyncCallback<Map<Key<Account>, Account>> callback) {
			if (accounts != null && cached) callback.onSuccess(accounts);
			else
				new RPCXSRF<Map<Key<Account>, Account>>(userService) {
					
					@Override
					public void onFailure(Throwable caught) {
						GWT.log(caught.getMessage());
						Window.alert(caught.getMessage());
					}
					
					@Override
					public void onSuccess(Map<Key<Account>, Account> accounts) {
						CacheLayer.UserCalls.setAccounts(accounts);
						callback.onSuccess(accounts);
						
					}
					
					@Override
					protected void XSRFcallService(AsyncCallback<Map<Key<Account>, Account>> cb) {
						userService.getCloudAccounts(cb);
					}
					
				}.retry(3);
			
		}
		
		public static void setColumns(Map<Key<Columns>, Columns> columns) {
			CacheLayer.UserCalls.columns = columns;
		}
		
		public static void setUser(User user) {
			CacheLayer.user = user;
			updateAvatar();
		}
		
		public static void updateUser(String name, String surname, String address, String mobile, String bio,
				final AsyncCallback<Boolean> callback) {
			final User userUpdated = User.toDTO(CacheLayer.user.getEmail(), CacheLayer.user.getUrlAvatar(),
					CacheLayer.user.getBackground(), name, surname, CacheLayer.user.getRole(), mobile, address, bio,
					CacheLayer.user.getTokenChannel());
			
			new RPCXSRF<User>(userService) {
				
				@Override
				protected void XSRFcallService(AsyncCallback<User> cb) {
					userService.updateUser(userUpdated, cb);
					
				}
				
				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
					callback.onSuccess(false);
				}
				
				@Override
				public void onSuccess(User user) {
					CacheLayer.user = user;
					TheSocialOS.getEventBus().fireEvent(new AccountUpdateEvent());
					callback.onSuccess(true);
				}
			}.retry(3);
		}
		
		public static void updateAvatar() {
			new RPCXSRF<String>(userService) {
				
				@Override
				protected void XSRFcallService(AsyncCallback<String> cb) {
					userService.getAvatar(cb);
					
				}
				
				@Override
				public void onFailure(Throwable caught) {
					
				}
				
				@Override
				public void onSuccess(String avatar) {
					CacheLayer.user.setAvatar(avatar);
					TheSocialOS.getEventBus().fireEvent(new AvatarUpdateEvent());
					
				}
			}.retry(3);
		}
		
		public static String getAvatar() {
			return CacheLayer.user.getUrlAvatar();
		}
	}
	
	public static class FolderCalls {
		public static List<Set<? extends Media>> files = new ArrayList<Set<? extends Media>>();
	}
	
}
