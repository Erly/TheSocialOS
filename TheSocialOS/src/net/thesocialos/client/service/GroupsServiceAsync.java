package net.thesocialos.client.service;

import java.util.Map;

import net.thesocialos.shared.exceptions.GroupNotFoundException;
import net.thesocialos.shared.model.Group;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.server.rpc.XsrfProtect;
import com.googlecode.objectify.Key;

@RemoteServiceRelativePath("ContactsRPC")
@XsrfProtect
public interface GroupsServiceAsync extends ServiceAsync {
	
	void getUserGroups(AsyncCallback<Map<Key<Group>, Group>> callback);
}
