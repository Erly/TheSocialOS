package net.thesocialos.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Unindexed;

@SuppressWarnings("serial")
@Cached
public class User implements Serializable {


	@Id private String email; //Email of the user
	
	@Unindexed private  String password; //Password of the user
	
	@Unindexed private String avatar; //Avatar of the user
	
	@Unindexed private String background; //Background of the user
	
	private String firstName; //firstname
	
	@Unindexed private String role; //The role of the user
	
	private String address; // address of user

	@Unindexed String lastName; //lastnameString address;
	
	@Unindexed String mobilePhone;
	
	@Unindexed String job;
	
	private Date lastTimeActive;
	
	Key<Group> groups[];
	
	Key<Conversation> conversations[];
	
	Key<OutConversation> offlineConversations[];
	
	List<Key<Session>> sessions = new ArrayList<Key<Session>>();
	
	List<Key<User>> contacts = new ArrayList<Key<User>>();
	/*
	 * Las peticiones de amistad de los contactos
	 */
	private List<Key<User>> petitionsContacts = new ArrayList<Key<User>>();
	
	List<Key<? extends Account>> accounts = new ArrayList<Key<? extends Account>>();

	public User(String email, String password, String picture, String background,String firstName,String lastName,String role){
		this.email = email;
		this.password = password;
		this.avatar = picture;
		this.background = background;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		
	}
	
	public User(String email,String picture,String background,String firstName,String lastName,String role){
		this.email = email;
		this.avatar = picture;
		this.background = background;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		
	}
	
	public User(){
		
	}
	
	public Key<Group>[] getGroups() {
		
		return groups;
	}

	public void setGroups(Key<Group>[] groups) {
		this.groups = groups;
	}

	public Key<Conversation>[] getConversations() {
		return conversations;
	}
	public List<Key<User>> getContacts(){
		return contacts;
	}
	/**
	 * Añade un contacto al usuario
	 * @param contact El contacto a añadir
	 * @return true si se a podido añadir. False si ya estaba añadido
	 */
	public boolean addContact(Key<User> contact){
		if (contacts.contains(contact))
			return false;
		contacts.add(contact);
		return true;
		
	}
	public List<Key<User>> getpetitionsContacts(){
		return petitionsContacts;
	}
	/**
	 * Añade una invitación de contacto al usuario
	 * @param contact El contacto a añadir
	 * @return true si se a podido añadir. False si ya estaba añadido
	 */
	public boolean addPetitionContacts(Key<User> contact){
		if (petitionsContacts.contains(contact) || contacts.contains(contact)){
			return false;
		}
		petitionsContacts.add(contact);
		return true;
	}
	/**
	 * Añade un contacto desde la lista de invitaciones
	 * @param contact El contacto a añadir
	 * @return true si se a podido añadir. False si ya estaba añadido
	 */
	public boolean addPetitionContactTOContact(Key<User> contact){
		if (petitionsContacts.contains(contact) && (contacts.contains(contact) == false)){
			contacts.add(contact);
			petitionsContacts.remove(contact);
			return true;
		}
		return false;
	}


	public void setConversations(Key<Conversation>[] conversations) {
		this.conversations = conversations;
	}

	public Key<OutConversation>[] getOfflineConversations() {
		return offlineConversations;
	}

	public void setOfflineConversations(Key<OutConversation>[] offlineConversations) {
		this.offlineConversations = offlineConversations;
	}

	public List<Key<Session>> getSessions() {
		return sessions;
	}

	public void addSessions(Key<Session> session) {
		this.sessions.add(session);
	}

	public List<Key<? extends Account>> getAccounts() {
		return accounts;
	}

	public void setAccounts(ArrayList<Key<? extends Account>> accounts) {
		this.accounts = accounts;
	}

	public void addAccount(Key<? extends Account> account) {
		accounts.add(account);
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getAvatar() {
		return avatar;
	}

	public String getBackground() {
		return background;
	}
	public String getName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	public String getMobilePhone() {
		return mobilePhone;
	}

	public String getRole() {
		return role;
	}
	
	public static User toDTO(User user) {
		return new User(user.email, user.avatar, user.background, user.firstName, user.lastName, user.role);
	}
	

	public String getAddress() {
		return address;
	}

	public Date getLastTimeActive() {
		return lastTimeActive;
	}

	public void setLastTimeActive(Date lastTimeActive) {
		this.lastTimeActive = lastTimeActive;
	}
	
	public void overwriteAccountsList(List<Key<? extends Account>> newAccountsKeys) {
		this.accounts.clear();
		this.accounts.addAll(newAccountsKeys);
	}
}
