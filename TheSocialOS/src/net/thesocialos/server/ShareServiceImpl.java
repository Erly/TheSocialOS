package net.thesocialos.server;

import java.util.Calendar;
import java.util.Map;

import net.thesocialos.client.service.ShareService;
import net.thesocialos.shared.model.SharedHistory;
import net.thesocialos.shared.model.SharedHistory.SHARETYPE;
import net.thesocialos.shared.model.User;

import com.google.gwt.user.server.rpc.XsrfProtectedServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

public class ShareServiceImpl extends XsrfProtectedServiceServlet implements ShareService {
	
	@Override
	public Boolean addShare(Key<User> contact, String url, SHARETYPE shareType) {
		Objectify ofy = ObjectifyService.begin();
		if (UserHelper.isYourFriend(perThreadRequest.get().getSession(), ofy, contact)) {
			SharedHistory share = new SharedHistory(shareType, url, contact, Calendar.getInstance().getTimeInMillis());
			User user = UserHelper.getUserWithEmail(UserHelper.getUserHttpSession(perThreadRequest.get().getSession()),
					ofy);
			user.addHistoryKey(ofy.put(share));
			ofy.put(user);
			return true;
		}
		
		return false;
	}
	
	@Override
	public Map<Key<SharedHistory>, SharedHistory> getShare() {
		// TODO Auto-generated method stub
		Objectify ofy = ObjectifyService.begin();
		User user = UserHelper
				.getUserWithEmail(UserHelper.getUserHttpSession(perThreadRequest.get().getSession()), ofy);
		
		return ofy.get(user.getShared());
	}
	
}
