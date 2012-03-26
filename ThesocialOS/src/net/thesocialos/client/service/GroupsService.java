package net.thesocialos.client.service;

import java.util.Map;

import net.thesocialos.shared.exceptions.GroupNotFoundException;
import net.thesocialos.shared.model.Group;

import com.google.gwt.user.client.rpc.RemoteService;
import com.googlecode.objectify.Key;

public interface GroupsService extends RemoteService{
	
	Map<Key<Group>, Group> getUserGroups() throws GroupNotFoundException;
}
