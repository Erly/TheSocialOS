package net.thesocialos.client.chat.view;

import net.thesocialos.shared.model.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
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
	
	Key<User> userKey;
	
	PopupPanel popupPanel = new PopupPanel(true);
	
	public ChatBlockView() {
		initWidget(uiBinder.createAndBindUi(this));
		CreatePopUP();
		// Scheduler.get().scheduleFixedPeriod(new ChangeColor(), 3000);
		// background.setStyleName("chatBlock_activated", true);
		handlers();
		
	}
	
	public ChatBlockView(Key<User> userKey) {
		this();
		this.userKey = userKey;
	}
	
	private void CreatePopUP() {
		
		popupPanel.setStyleName("chatBlock_Popup");
		Label lblname = new Label("vssnake");
		
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
		
	}
	
	public AbsolutePanel getBackground() {
		return background;
	}
	
	public Image getimgAvatar() {
		return lblImage;
	}
	
}
