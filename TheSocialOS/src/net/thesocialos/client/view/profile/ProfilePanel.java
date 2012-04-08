package net.thesocialos.client.view.profile;

import net.thesocialos.client.CacheLayer;
import java.util.Iterator;
import java.util.Map;
import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.presenter.ProfilePanelPresenter.Display;
import net.thesocialos.shared.model.Account;
import net.thesocialos.shared.model.Facebook;
import net.thesocialos.shared.model.FlickR;
import net.thesocialos.shared.model.Google;
import net.thesocialos.shared.model.Twitter;
import net.thesocialos.shared.model.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.googlecode.objectify.Key;

public class ProfilePanel extends Composite implements Display {

	private String googleURL = "https://accounts.google.com/o/oauth2/auth?" + 
			"redirect_uri=http%3A%2F%2Fwww.thesocialos.net%2Foauth2callback&" + 
			"response_type=code&client_id=398121744591.apps.googleusercontent.com&" + 
			"approval_prompt=force&scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fcalendar+" +
			"https%3A%2F%2Fwww.google.com%2Fm8%2Ffeeds%2F+https%3A%2F%2Fdocs.google.com%2Ffeeds%2F+" +
			"https%3A%2F%2Fmail.google.com%2Fmail%2Ffeed%2Fatom+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fplus.me+" +
			"https%3A%2F%2Fpicasaweb.google.com%2Fdata%2F+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Ftasks+" +
			"https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+" +
			"https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile+https%3A%2F%2Fgdata.youtube.com&" +
			"access_type=offline";
	
	private String facebookURL = "https://graph.facebook.com/oauth/authorize?client_id=124427357682835&" +
			"display=page&redirect_uri=http://www.thesocialos.net/oauth2callbackFB&scope=user_status," +
			"publish_stream,offline_access,email,user_about_me,friends_about_me,user_activities,friends_activities," +
			"user_birthday,friends_birthday,user_checkins,friends_checkins,user_education_history," +
			"friends_education_history,user_events,friends_events,user_groups,friends_groups,user_hometown," +
			"friends_hometown,user_interests,friends_interests,user_likes,friends_likes,user_location," +
			"friends_location,user_notes,friends_notes,user_photos,friends_photos,user_questions," +
			"friends_questions,user_relationships,friends_relationships,user_relationship_details," +
			"friends_relationship_details,user_religion_politics,friends_religion_politics,user_status," +
			"friends_status,user_videos,friends_videos,user_website,friends_website,user_work_history," +
			"friends_work_history,read_friendlists,read_insights,read_mailbox,read_requests,read_stream," +
			"xmpp_login,ads_management,create_event,manage_friendlists,manage_notifications,user_online_presence," +
			"friends_online_presence,publish_checkins,publish_stream,rsvp_event,publish_actions,user_actions.music," +
			"friends_actions.music,user_actions.news,friends_actions.news,user_actions.video,friends_actions.video," +
			"user_actions:APP_NAMESPACE,friends_actions:APP_NAMESPACE,user_games_activity,friends_games_activity," +
			"manage_pages";
	
	private String twitterURL = "http://www.thesocialos.net/oauthlogin?serviceType=twitter"; /*"https://api.twitter.com/oauth/request_token?" +
			"oauth_callback=http://www.thesocialos.net/oauthcallback&x_auth_access_type=write";*/
	
	private String flickrURL = "http://www.thesocialos.net/oauthlogin?serviceType=flickr";
	
	private static ProfilePanelUiBinder uiBinder = GWT
			.create(ProfilePanelUiBinder.class);
	@UiField ProfileAttr name;
	@UiField ProfileAttr title;
	@UiField ProfileAttr email;
	@UiField ProfileAttr mobile;
	@UiField ProfileAttr address;
	@UiField ProfileAttr google;
	@UiField ProfileAttr facebook;
	@UiField ProfileAttr twitter;
	@UiField ProfileAttr flickr;
	Map<Key<Account>, Account> accounts;
	Google googleAccount;
	Facebook facebookAccount;
	Twitter twitterAccount;
	FlickR flickrAccount;

	interface ProfilePanelUiBinder extends UiBinder<Widget, ProfilePanel> {
	}

	public ProfilePanel() {
		initWidget(uiBinder.createAndBindUi(this));
		populateAccountsMap();
		User user = CacheLayer.UserCalls.getUser();
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
			google.setAttrLink(googleURL, "Google Account login");
		}
		
		facebook.attrName.setText(TheSocialOS.getConstants().facebookAccount());
		if (null != facebookAccount) {
			facebook.attrValue.setText(facebookAccount.getUsername()); // TODO change the email for the Facebook Account name
		} else {
			facebook.attrValue.addStyleName("hand");
			facebook.setAttrLink(facebookURL, "Facebook Account Login");
		}
		
		twitter.attrName.setText(TheSocialOS.getConstants().twitterAccount());
		if (null != twitterAccount) {
			twitter.attrValue.setText(twitterAccount.getUsername());
		} else {
			twitter.attrValue.addStyleName("hand");
			twitter.setAttrLink(twitterURL, "Twitter Account Login");
		}
		
		flickr.attrName.setText(TheSocialOS.getConstants().flickrAccount());
		if (null != flickrAccount) {
			flickr.attrValue.setText(flickrAccount.getUsername());
		} else {
			flickr.attrValue.addStyleName("hand");
			flickr.setAttrLink(flickrURL, "Flickr Account Login");
		}
	}

	private void populateAccountsMap() {
		accounts = CacheLayer.UserCalls.getAccounts();
		Iterator<Account> it = accounts.values().iterator();
		while (it.hasNext()) {
			Account account = it.next();
			if (account instanceof Google) {
				googleAccount = (Google) account;
			} else if (account instanceof Facebook) {
				facebookAccount = (Facebook) account;
			} else if (account instanceof Twitter) {
				twitterAccount = (Twitter) account;
			} else if (account instanceof FlickR) {
				flickrAccount = (FlickR) account;
			}
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
