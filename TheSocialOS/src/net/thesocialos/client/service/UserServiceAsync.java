package net.thesocialos.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import net.thesocialos.shared.LoginResult;
import net.thesocialos.shared.UserDTO;
import net.thesocialos.shared.UserSummaryDTO;

public interface UserServiceAsync extends ServiceAsync{

	
	void createServerSession(AsyncCallback<Void> callback);
	
	void getLoggedUser(String[] ids, AsyncCallback<UserDTO> callback);
}
