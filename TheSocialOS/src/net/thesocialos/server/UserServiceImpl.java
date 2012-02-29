package net.thesocialos.server;


import net.thesocialos.client.service.UserService;
import net.thesocialos.server.model.User;
import net.thesocialos.shared.UserDTO;




import com.google.gwt.user.server.rpc.RemoteServiceServlet;



@SuppressWarnings("serial")
public class UserServiceImpl extends RemoteServiceServlet  implements
		UserService {

	public UserServiceImpl() {
		
	}
	
	@Override
	public void createServerSession() {
		perThreadRequest.get().getSession();
	}

	@Override
	public UserDTO getLoggedUser(String[] ids) {
		perThreadRequest.get().getSession();
		User user = UserHelper.getLoggedUser(ids, getThreadLocalRequest());
		if (user!=null){
			
			return User.toDTO(user);
			
		}
		return null;
	}
	


	
}