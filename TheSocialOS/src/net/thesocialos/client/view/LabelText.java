package net.thesocialos.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;

public class LabelText extends Composite {

	private static LabelTextUiBinder uiBinder = GWT
			.create(LabelTextUiBinder.class);
	@UiField Label lblLabel;
	@UiField Label lblText;
	final private PopupPanel popPanel = new PopupPanel(true);
	Label popupLabel = new Label();
	

	interface LabelTextUiBinder extends UiBinder<Widget, LabelText> {
	}

	public LabelText(String labelText,String text) {
		initWidget(uiBinder.createAndBindUi(this));
		popPanel.setStyleName("popup");
		popPanel.add(popupLabel);
		popupLabel.setWordWrap(true);
		setLabelText(labelText);
		setText(text);
		
	}
	public LabelText(){
		initWidget(uiBinder.createAndBindUi(this));
		popPanel.setStyleName("popup");
		popPanel.add(popupLabel);
		popupLabel.setWordWrap(true);
	}

	



	@UiHandler("lblText")
	void onLblTextMouseOver(MouseOverEvent event) {
			
			
		 int x = event.getClientX();
		 int y = event.getClientY();
		 popupLabel.setText(lblText.getText());
		
		 popPanel.setPopupPosition(x, y);
		 popPanel.show();
	}
	@UiHandler("lblText")
	void onLblTextMouseOut(MouseOutEvent event) {
		popPanel.hide();
	}
	
	public void setLabelText(String labelText){
		lblLabel.setText(labelText);
	}
	public void setText(String text){
		lblText.setText(text);
	}
}
