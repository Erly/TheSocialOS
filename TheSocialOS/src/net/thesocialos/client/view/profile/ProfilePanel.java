package net.thesocialos.client.view.profile;

import java.util.Iterator;
import java.util.Map;
import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.presenter.ProfilePanelPresenter.Display;
import net.thesocialos.shared.model.Account;
import net.thesocialos.shared.model.Facebook;
import net.thesocialos.shared.model.Google;
import net.thesocialos.shared.model.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.googlecode.objectify.Key;

public class ProfilePanel extends Composite implements Display {

	private static ProfilePanelUiBinder uiBinder = GWT
			.create(ProfilePanelUiBinder.class);
	@UiField ProfileAttr name;
	@UiField ProfileAttr title;
	@UiField ProfileAttr email;
	@UiField ProfileAttr mobile;
	@UiField ProfileAttr address;
	@UiField ProfileAttr google;
	@UiField ProfileAttr facebook;
	Google googleAccount;
	Facebook facebookAccount;

	interface ProfilePanelUiBinder extends UiBinder<Widget, ProfilePanel> {
	}

	public ProfilePanel() {
		initWidget(uiBinder.createAndBindUi(this));
		User user = TheSocialOS.get().getCurrentUser();
		Map<Key<Account>, Account> accounts = TheSocialOS.get().getCurrentUserAccounts();
		Iterator<Account> it = accounts.values().iterator();
		Window.alert("Hay " + accounts.size() + " cuentas de usuario");
		while (it.hasNext()) {
			Account account = it.next();
			if (account instanceof Google) {
				googleAccount = (Google) account;
			} else if (account instanceof Facebook) {
				facebookAccount = (Facebook) account;
			}
			Window.alert(account.getClass().getName());
		}
		name.attrName.setText(TheSocialOS.getConstants().name());
		name.attrValue.setText(user.getName() + " " + user.getLastName());
		title.attrName.setText(TheSocialOS.getConstants().title());
		title.attrValue.setText(user.getRole());
		//email.attrName.setText(TheSocialOS.getConstants()); Email is always email
		email.attrValue.setText(user.getEmail());
		mobile.attrName.setText(TheSocialOS.getConstants().mobile());
		mobile.attrValue.setText(user.getMobilePhone());
		address.attrName.setText(TheSocialOS.getConstants().address());
		address.attrValue.setText(user.getAddress());
		
		google.attrName.setText(TheSocialOS.getConstants().googleAccount());
		if (null != googleAccount) {
			google.attrValue.setText(googleAccount.getUsername()); // TODO change the email for the Google Account email
		} else {
			google.attrValue.addStyleName("hand");
			google.setAttrLink("https://accounts.google.com/o/oauth2/auth?" + 
					"redirect_uri=http%3A%2F%2Fwww.thesocialos.net%2Foauth2callback&" + 
					"response_type=code&client_id=398121744591.apps.googleusercontent.com&" + 
					"approval_prompt=force&scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fcalendar+" +
					"https%3A%2F%2Fwww.google.com%2Fm8%2Ffeeds%2F+https%3A%2F%2Fdocs.google.com%2Ffeeds%2F+" +
					"https%3A%2F%2Fmail.google.com%2Fmail%2Ffeed%2Fatom+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fplus.me+" +
					"https%3A%2F%2Fpicasaweb.google.com%2Fdata%2F+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Ftasks+" +
					"https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+" +
					"https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile+https%3A%2F%2Fgdata.youtube.com&" +
					"access_type=offline", 
					"Google Account login");
		}
		
		facebook.attrName.setText(TheSocialOS.getConstants().facebookAccount());
		if (null != facebookAccount) {
			facebook.attrValue.setText(facebookAccount.getUsername()); // TODO change the email for the Facebook Account name
		} else {
			facebook.attrValue.addStyleName("hand");
			facebook.setAttrLink("https://graph.facebook.com/oauth/authorize?client_id=124427357682835&" +
					"display=page&redirect_uri=http://www.thesocialos.net/oauth2callbackFB&scope=user_status," +
					"publish_stream,offline_access,email", 
					"Facebook Account Login");
		}
	}

	@Override
	public ProfileAttr getName() {
		return name;
	}
	
	@Override
	public ProfileAttr getUserTitle() {
		return title;
	}

	@Override
	public ProfileAttr getEmail() {
		return email;
	}

	@Override
	public ProfileAttr getMobile() {
		return mobile;
	}

	@Override
	public ProfileAttr getAddress() {
		return address;
	}
}
