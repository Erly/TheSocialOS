package net.thesocialos.server;

import java.util.Map;

import javax.servlet.http.HttpSession;

import net.thesocialos.client.service.GroupsService;
import net.thesocialos.shared.exceptions.GroupNotFoundException;
import net.thesocialos.shared.model.Group;
import net.thesocialos.shared.model.User;

import com.google.gwt.user.server.rpc.XsrfProtectedServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;

@SuppressWarnings("serial")
public class GroupsImpl extends XsrfProtectedServiceServlet implements GroupsService{

	@Override
	public Map<Key<Group>, Group> getUserGroups() throws GroupNotFoundException {
		HttpSession httpSession = perThreadRequest.get().getSession();
		Objectify ofy = UserHelper.getBBDD(httpSession);
		User user = UserHelper.getUserfromSession(httpSession);
		Map<Key<Group>, Group> groups;
		try {
			groups = ofy.get(Group.class,user.getGroups());
		} catch (IllegalArgumentException e) {
			throw new GroupNotFoundException("No groups Found");
		}
		return groups;
		
	}

}
