package net.thesocialos.client.chat.view;

import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.chat.events.ChatHideConversation;
import net.thesocialos.shared.model.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.objectify.Key;

public class ChatBlockView extends Composite {
	
	/*
	 * private class ChangeColor implements RepeatingCommand { Boolean isColored = false;
	 * @Override public boolean execute() { if (isColored) { background.setStyleName("chatBlock_background_red", false);
	 * background.setStyleName("chatBlock_background_white", true); isColored = false; } else {
	 * background.setStyleName("chatBlock_background_red", true); background.setStyleName("chatBlock_background_white",
	 * false); isColored = true; } return true; } }
	 */
	interface ChatBlockViewUiBinder extends UiBinder<Widget, ChatBlockView> {
	}
	
	private static ChatBlockViewUiBinder uiBinder = GWT.create(ChatBlockViewUiBinder.class);
	
	@UiField Image lblImage;
	@UiField AbsolutePanel background;
	@UiField HTMLPanel htmlPanel;
	
	Key<User> userKey;
	private boolean activated = false;
	
	PopupPanel popupPanel = new PopupPanel(true);
	
	private ChatBlockView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		// Scheduler.get().scheduleFixedPeriod(new ChangeColor(), 3000);
		// background.setStyleName("chatBlock_activated", true);
		handlers();
		
	}
	
	public ChatBlockView(Key<User> userKey, String urlAvatar) {
		this();
		this.userKey = userKey;
		CreatePopUP();
		if (urlAvatar == null) lblImage.setUrl("./images/anonymous_avatar.png");
		else
			lblImage.setUrl(urlAvatar);
	}
	
	private void CreatePopUP() {
		
		popupPanel.setStyleName("chatBlock_Popup", true);
		Label lblname = new Label(userKey.getName());
		
		popupPanel.add(lblname);
	}
	
	private void handlers() {
		getBackground().addDomHandler(new MouseOverHandler() {
			
			@Override
			public void onMouseOver(MouseOverEvent event) {
				// TODO Auto-generated method stub
				popupPanel.setPopupPosition(event.getClientX(), event.getClientY() - 20);
				popupPanel.show();
			}
		}, MouseOverEvent.getType());
		getBackground().addDomHandler(new MouseOutHandler() {
			
			@Override
			public void onMouseOut(MouseOutEvent event) {
				// TODO Auto-generated method stub
				popupPanel.hide();
			}
		}, MouseOutEvent.getType());
		getBackground().addDomHandler(new MouseMoveHandler() {
			
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				// TODO Auto-generated method stub
				popupPanel.setPopupPosition(event.getClientX(), event.getClientY() - 20);
			}
		}, MouseMoveEvent.getType());
		getBackground().addDomHandler(new MouseUpHandler() {
			
			@Override
			public void onMouseUp(MouseUpEvent event) {
				// messagesPending(false);
				TheSocialOS.getEventBus().fireEvent(new ChatHideConversation(userKey));
				
			}
		}, MouseUpEvent.getType());
		
	}
	
	public AbsolutePanel getBackground() {
		return background;
	}
	
	public Image getimgAvatar() {
		return lblImage;
	}
	
	/**
	 * Makes the ChatBlock animate or not
	 * 
	 * @param pending
	 *            true to animate
	 */
	public void messagesPending(Boolean pending) {
		if (activated) htmlPanel.setStyleName("chatBlock_activated", pending);
		
	}
	
	/**
	 * @return the activated
	 */
	public boolean isActivated() {
		return activated;
	}
	
	/**
	 * @param activated
	 *            the activated to set
	 */
	public void setActivated(boolean activated) {
		if (!activated) htmlPanel.setStyleName("chatBlock_activated", activated);
		this.activated = activated;
	}
	
}
