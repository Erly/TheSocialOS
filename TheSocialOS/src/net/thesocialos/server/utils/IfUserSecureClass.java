package net.thesocialos.server.utils;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.condition.PojoIf;

public class IfUserSecureClass extends PojoIf<Object> {

	

	@Override
	public boolean matches(Object pojo) {
		// TODO Auto-generated method stub
		if (User.class.equals(pojo)){
			return true;
		}
		return false;
	}

}
