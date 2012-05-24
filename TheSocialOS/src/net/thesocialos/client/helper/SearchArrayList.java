package net.thesocialos.client.helper;

import java.util.ArrayList;

import net.thesocialos.shared.model.User;

public class SearchArrayList extends ArrayList<User> {
	
	public ArrayList<User> getSearchUsers(String userString) {
		String[] userParts = userString.split(" ", 2);
		
		if (userParts.length > 0 && !userParts[0].contains(" ") && !userParts[0].isEmpty()) {
			@SuppressWarnings("unchecked")
			ArrayList<User> searchList = (ArrayList<User>) clone();
			for (int i = 0; i < size(); i++) {
				User user = get(i);
				int matchCount = 0;
				for (String userPart : userParts)
					if (user.getName().contains(userPart)) matchCount++;
					else if (user.getLastName().contains(userPart)) matchCount++;
				if (userParts.length == 2)
					if (user.getName().contains(userParts[0]) && user.getLastName().contains(userParts[1])
							|| (user.getName().contains(userParts[1]) && user.getLastName().contains(userParts[0]))) {
						
					} else
						matchCount = 0;
				
				if (matchCount == 0) searchList.remove(user);
			}
			return searchList;
		}
		return this;
		
	}
	
	public boolean isAlreadyContact(User user) {
		return false;
	}
	
}
