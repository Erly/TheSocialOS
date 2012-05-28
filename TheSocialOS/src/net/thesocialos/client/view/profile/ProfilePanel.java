package net.thesocialos.client.view.profile;

import gwtupload.client.SingleUploader;
import net.thesocialos.client.CacheLayer;
import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.presenter.ProfilePanelPresenter.Display;
import net.thesocialos.shared.model.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ProfilePanel extends Composite implements Display {
	
	interface ProfilePanelUiBinder extends UiBinder<Widget, ProfilePanel> {
	}
	
	private String googleURL = "https://accounts.google.com/o/oauth2/auth?"
			+ "redirect_uri=http%3A%2F%2Fwww.thesocialos.net%2Foauth2callback&"
			+ "response_type=code&client_id=398121744591.apps.googleusercontent.com&"
			+ "approval_prompt=force&scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fcalendar+"
			+ "https%3A%2F%2Fwww.google.com%2Fm8%2Ffeeds%2F+https%3A%2F%2Fdocs.google.com%2Ffeeds%2F+"
			+ "https%3A%2F%2Fmail.google.com%2Fmail%2Ffeed%2Fatom+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fplus.me+"
			+ "https%3A%2F%2Fpicasaweb.google.com%2Fdata%2F+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Ftasks+"
			+ "https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+"
			+ "https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile+https%3A%2F%2Fgdata.youtube.com&"
			+ "access_type=offline";
	
	private String facebookURL = "https://graph.facebook.com/oauth/authorize?client_id=124427357682835&"
			+ "display=page&redirect_uri=http://www.thesocialos.net/oauth2callbackFB&scope=user_status,"
			+ "publish_stream,offline_access,email,user_about_me,friends_about_me,user_birthday,friends_birthday,"
			+ "user_events,friends_events,user_groups,friends_groups,friends_hometown,user_likes,friends_likes,"
			+ "user_notes,friends_notes,user_photos,friends_photos,user_questions,friends_questions,user_status,"
			+ "friends_status,user_videos,friends_videos,read_insights,read_mailbox,read_requests,read_stream,"
			+ "xmpp_login,create_event,manage_notifications,publish_checkins,publish_stream,publish_actions,user_actions.music,"
			+ "friends_actions.music,user_actions.video,friends_actions.video";
	
	private String twitterURL = "http://www.thesocialos.net/oauthlogin?serviceType=twitter"; /*
																							 * "https://api.twitter.com/oauth/request_token?"
																							 * +
																							 * "oauth_callback=http://www.thesocialos.net/oauthcallback&x_auth_access_type=write"
																							 * ;
																							 */
	
	private String flickrURL = "http://www.thesocialos.net/oauthlogin?serviceType=flickr";
	private static ProfilePanelUiBinder uiBinder = GWT.create(ProfilePanelUiBinder.class);
	@UiField ProfileAttr name;
	@UiField ProfileAttr title;
	@UiField ProfileAttr email;
	@UiField ProfileAttr mobile;
	@UiField ProfileAttr address;
	@UiField ProfileAttr google;
	@UiField ProfileAttr facebook;
	@UiField ProfileAttr twitter;
	@UiField ProfileAttr flickr;
	@UiField ProfileAttrArea bio;
	@UiField Button btnEdit;
	@UiField ProfileAttr surname;
	@UiField Image imageAvatar;
	@UiField FormPanel uploadForm;
	@UiField VerticalPanel vertical;
	
	public ProfilePanel() {
		initWidget(uiBinder.createAndBindUi(this));
		
		User user = CacheLayer.UserCalls.getUser();
		// btnExamine.setName("Image");
		
		name.attrName.setText(TheSocialOS.getConstants().name());
		name.attrValue.setText(user.getName());
		surname.attrName.setText((TheSocialOS.getConstants().lastName()));
		surname.attrValue.setText(user.getLastName());
		title.attrName.setText(TheSocialOS.getConstants().title());
		title.attrValue.setText(user.getRole());
		// email.attrName.setText(TheSocialOS.getConstants()); Email is always email
		email.attrValue.setText(user.getEmail());
		mobile.attrName.setText(TheSocialOS.getConstants().mobile());
		mobile.attrValue.setText(user.getMobilePhone());
		address.attrName.setText(TheSocialOS.getConstants().address());
		address.attrValue.setText(user.getAddress());
		bio.attrName.setText(TheSocialOS.getConstants().Bio());
		bio.attrValue.setText(user.getBio());
		if (user.getUrlAvatar() != null) imageAvatar.setUrl(user.getUrlAvatar());
		else
			imageAvatar.setUrl("./images/anonymous_avatar.png");
		
		SingleUploader defaultUploader = new SingleUploader();
		
		defaultUploader.setAutoSubmit(false);
		defaultUploader.setServletPath("/upload");
		defaultUploader.getForm().addSubmitCompleteHandler(new SubmitCompleteHandler() {
			
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				CacheLayer.UserCalls.updateAvatar();
				
			}
		});
		
		vertical.add(defaultUploader);
		
	}
	
	@Override
	public ProfileAttr getAddress() {
		return address;
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
	public ProfileAttr getName() {
		return name;
	}
	
	@Override
	public ProfileAttr getUserTitle() {
		return title;
	}
	
	@Override
	public ProfileAttrArea getBio() {
		// TODO Auto-generated method stub
		return bio;
	}
	
	@Override
	public Button getButton() {
		// TODO Auto-generated method stub
		return btnEdit;
	}
	
	@Override
	public void setEditable(Boolean editable) {
		name.setEditable(editable);
		address.setEditable(editable);
		mobile.setEditable(editable);
		name.setEditable(editable);
		bio.setEditable(editable);
		surname.setEditable(editable);
	}
	
	@Override
	public boolean getEditable() {
		
		return name.getEditable();
	}
	
	@Override
	public ProfileAttr getSurname() {
		// TODO Auto-generated method stub
		return surname;
	}
	
	@Override
	public Image getAvatar() {
		// TODO Auto-generated method stub
		return imageAvatar;
	}
	
	@Override
	public FileUpload getExamineButton() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ProfileAttr getFacebook() {
		// TODO Auto-generated method stub
		return facebook;
	}
	
	@Override
	public ProfileAttr getTwitter() {
		// TODO Auto-generated method stub
		return twitter;
	}
	
	@Override
	public ProfileAttr getFlickR() {
		// TODO Auto-generated method stub
		return flickr;
	}
	
	@Override
	public ProfileAttr getGoogle() {
		// TODO Auto-generated method stub
		return google;
	}
	
	@Override
	public String getGoogleURL() {
		// TODO Auto-generated method stub
		return googleURL;
	}
	
	@Override
	public String getFlickRURL() {
		// TODO Auto-generated method stub
		return flickrURL;
	}
	
	@Override
	public String getTwitterURL() {
		// TODO Auto-generated method stub
		return twitterURL;
	}
	
	@Override
	public String getFacebookURL() {
		// TODO Auto-generated method stub
		return facebookURL;
	}
	
}
