package net.thesocialos.client.view;

import net.thesocialos.client.CacheLayer;
import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.event.ContactEventHandler;
import net.thesocialos.client.event.ContactsPetitionChangeEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;

public class DesktopBar extends Composite {
	
	interface DesktopBarUiBinder extends UiBinder<HTMLPanel, DesktopBar> {
	}
	
	private static DesktopBarUiBinder uiBinder = GWT.create(DesktopBarUiBinder.class);
	
	@UiField Label clock;
	
	@UiField Label username;
	@UiField FocusPanel userPanel;
	@UiField FocusPanel socialOSButton;
	@UiField FocusPanel focusContacts;
	@UiField Label lblContacts;
	@UiField Label lblSearchBox;
	@UiField FocusPanel searchButton;
	@UiField Label lblPetitions;
	@UiField Label lblPetitionsNumber;
	@UiField FocusPanel PetitionsButton;
	@UiField FocusPanel startButton;
	@UiField Aplication appManagerButton;
	
	public DesktopBar() {
		initWidget(uiBinder.createAndBindUi(this));
		TheSocialOS.getEventBus().addHandler(ContactsPetitionChangeEvent.TYPE, new ContactEventHandler() {
			
			@Override
			public void onContactsPetitionChange(ContactsPetitionChangeEvent event) {
				lblPetitionsNumber.setText(Integer.toString(CacheLayer.ContactCalls.getCountPetitionsContanct()));
			}
		});
	}
	
	public FocusPanel getFocusContact() {
		return focusContacts;
	}
	
	public FocusPanel getPetitionsButton() {
		return PetitionsButton;
	}
	
	public Label getPetitionsNumber() {
		return lblPetitionsNumber;
	}
	
	/*
	 * public FocusPanel getProgramsButton() { return ProgramsButton; } public Image getImgProgram() { return
	 * imgProgram; } public Label getLabelProgram() { return labelProgram; }
	 */
	public FocusPanel getSearchBox() {
		return searchButton;
	}
	
	public Aplication getAppManagerButton() {
		return appManagerButton;
	}
	
}
