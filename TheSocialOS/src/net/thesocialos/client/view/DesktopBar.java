package net.thesocialos.client.view;

import net.thesocialos.client.CacheLayer;
import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.event.ContactEventHandler;
import net.thesocialos.client.event.ContactsPetitionChangeEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;

public class DesktopBar extends Composite {

	private static DesktopBarUiBinder uiBinder = GWT
			.create(DesktopBarUiBinder.class);

	interface DesktopBarUiBinder extends UiBinder<HorizontalPanel, DesktopBar> {
	}

	public DesktopBar() {
		initWidget(uiBinder.createAndBindUi(this));
		
		TheSocialOS.getEventBus().addHandler(ContactsPetitionChangeEvent.TYPE, new ContactEventHandler() {
			
			

			@Override
			public void onContactsPetitionChange(
					ContactsPetitionChangeEvent event) {
				lblPetitionsNumber.setText(Integer.toString(CacheLayer.ContactCalls.getCountPetitionsContanct()));
				
			}
		});
	}

	@UiField Label clock;
	@UiField Label username;
	@UiField FocusPanel userPanel;
	@UiField FocusPanel startButton;
	@UiField FocusPanel socialOSButton;
	@UiField FocusPanel focusContacts;
	@UiField Label lblContacts;
	@UiField Label lblSearchBox;
	@UiField FocusPanel searchButton;
	@UiField Label lblPetitions;
	@UiField Label lblPetitionsNumber;
	@UiField FocusPanel PetitionsButton;
	@UiField FocusPanel ProgramsButton;
	@UiField Image imgProgram;
	@UiField Label labelProgram;
	

	public FocusPanel getFocusContact(){
		return focusContacts;
	}
	public FocusPanel getSearchBox(){
		return searchButton;
	}
	
	public FocusPanel getPetitionsButton(){
		return  PetitionsButton;
	}
	public Label getPetitionsNumber(){
		return lblPetitionsNumber;
	}
	public FocusPanel getProgramsButton(){
		return ProgramsButton;
	}
	public Image getImgProgram(){
		return imgProgram;
	}
	public Label getLabelProgram(){
		return labelProgram;
	}
	/**
	 * Set in the widget a number of petitions is waiting
	 * @param number
	 */
	
}
