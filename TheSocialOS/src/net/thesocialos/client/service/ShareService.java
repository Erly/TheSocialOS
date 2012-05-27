package net.thesocialos.client.service;

import java.util.List;

import net.thesocialos.shared.model.SharedHistory;
import net.thesocialos.shared.model.SharedHistory.SHARETYPE;
import net.thesocialos.shared.model.User;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.server.rpc.XsrfProtect;
import com.googlecode.objectify.Key;

@RemoteServiceRelativePath("shareService")
@XsrfProtect
public interface ShareService extends RemoteService {
	
	Boolean addShare(Key<User> contact, String url, String title, SHARETYPE shareType);
	
	List<SharedHistory> getShare();
	
}
