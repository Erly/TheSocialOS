package net.thesocialos.client.service;

import java.util.Map;

import net.thesocialos.shared.model.SharedHistory;
import net.thesocialos.shared.model.SharedHistory.SHARETYPE;
import net.thesocialos.shared.model.User;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.objectify.Key;

public interface ShareServiceAsync extends ServiceAsync {
	
	void addShare(Key<User> contact, String url, SHARETYPE type, AsyncCallback<Boolean> callback);
	
	void getShare(AsyncCallback<Map<Key<SharedHistory>, SharedHistory>> callback);
}
