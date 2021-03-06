package net.thesocialos.client.service;

import java.util.ArrayList;
import java.util.Map;

import net.thesocialos.shared.LoginResult;
import net.thesocialos.shared.ChannelApiEvents.ChApiChatUserChngState.STATETYPE;
import net.thesocialos.shared.ChannelApiEvents.ChApiContactNew;
import net.thesocialos.shared.exceptions.UserExistsException;
import net.thesocialos.shared.exceptions.UserUpdateException;
import net.thesocialos.shared.exceptions.UsersNotFoundException;
import net.thesocialos.shared.model.Account;
import net.thesocialos.shared.model.Columns;
import net.thesocialos.shared.model.User;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.server.rpc.XsrfProtect;
import com.googlecode.objectify.Key;

@RemoteServiceRelativePath("userService")
@XsrfProtect
public interface UserService extends RemoteService {
	
	String getChannel();
	
	Map<Key<Account>, Account> getCloudAccounts();
	
	Map<Key<Columns>, Columns> getDeckColumns();
	
	void deleteCloudAccount(Account account);
	
	User getLoggedUser(String sid);
	
	LoginResult login(String email, String password, boolean keeploged);
	
	void logout();
	
	void register(User user) throws UserExistsException;
	
	User updateUser(User user) throws UserUpdateException;
	
	String getAvatar();
	
	void setDeckColumns(ArrayList<Columns> columns);
	
	void addDeckColumn(Columns column);
	
	void checkChannel(ChApiContactNew newContact);
	
	void setState(STATETYPE statetype, String customMsg);
	
	void sendResetPass(String email) throws UsersNotFoundException;
	
	Boolean changePassWord(String oldPassWord, String newPassword);
}
