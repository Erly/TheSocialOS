package net.thesocialos.client.chat.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class ChatBlockView extends Composite {
	
	private class ChangeColor implements RepeatingCommand {
		Boolean isColored = false;
		
		@Override
		public boolean execute() {
			// TODO Auto-generated method stub
			if (isColored) {
				background.setStyleName("chatBlock_background_red", false);
				background.setStyleName("chatBlock_background_white", true);
				isColored = false;
			} else {
				background.setStyleName("chatBlock_background_red", true);
				background.setStyleName("chatBlock_background_white", false);
				isColored = true;
			}
			return true;
		}
		
	}
	interface ChatBlockViewUiBinder extends UiBinder<Widget, ChatBlockView> {
	}
	private static ChatBlockViewUiBinder uiBinder = GWT.create(ChatBlockViewUiBinder.class);
	
	@UiField AbsolutePanel background;
	
	@UiField Image lblImage;
	
	String name = "example@example.com";
	
	PopupPanel popupPanel = new PopupPanel(true);
	
	public ChatBlockView() {
		initWidget(uiBinder.createAndBindUi(this));
		CreatePopUP();
		Scheduler.get().scheduleFixedPeriod(new ChangeColor(), 3000);
		
	}
	
	public ChatBlockView(String name) {
		this();
		this.name = name;
	}
	
	private void CreatePopUP() {
		
		popupPanel.setStyleName("chatBlock_Popup");
		Label lblname = new Label(name);
		
		popupPanel.add(lblname);
	}
	
	public AbsolutePanel getBackground() {
		return background;
	}
	
	public Image getimgAvatar() {
		return lblImage;
	}
	
	@UiHandler("lblImage")
	void onLblImageMouseMove(MouseMoveEvent event) {
		popupPanel.setPopupPosition(event.getClientX(), event.getClientY());
	}
	
	@UiHandler("lblImage")
	void onLblImageMouseOut(MouseOutEvent event) {
		popupPanel.hide();
	}
	
	@UiHandler("lblImage")
	void onLblImageMouseOver(MouseOverEvent event) {
		
		popupPanel.setPopupPosition(event.getClientX(), event.getClientY());
		popupPanel.show();
	}
	
}
