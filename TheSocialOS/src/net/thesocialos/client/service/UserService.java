package net.thesocialos.client.service;

import java.util.ArrayList;


import net.thesocialos.shared.LoginResult;
import net.thesocialos.shared.UserDTO;
import net.thesocialos.shared.UserSummaryDTO;
import net.thesocialos.shared.exceptions.UserExistsException;


import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.server.rpc.XsrfProtect;

@RemoteServiceRelativePath("userService")

public interface UserService extends RemoteService {
	
	
	void createServerSession();
	
	UserDTO getLoggedUser(String[] ids);

}
