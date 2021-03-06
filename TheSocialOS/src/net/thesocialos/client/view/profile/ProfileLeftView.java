package net.thesocialos.client.view.profile;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ProfileLeftView extends Composite {
	
	interface ProfileLeftViewUiBinder extends UiBinder<VerticalPanel, ProfileLeftView> {
	}
	
	private static ProfileLeftViewUiBinder uiBinder = GWT.create(ProfileLeftViewUiBinder.class);
	
	@UiField Image photo;
	@UiField Label name, friends;
	@UiField VerticalPanel friendsCol;
	
	public ProfileLeftView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
}
