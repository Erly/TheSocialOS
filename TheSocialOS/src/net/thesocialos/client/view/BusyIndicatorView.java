package net.thesocialos.client.view;

import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.presenter.BusyIndicatorPresenter.Display;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class BusyIndicatorView extends PopupPanel implements Display {
	
	private Label message = new Label(TheSocialOS.getConstants().loading());
	
	public BusyIndicatorView() {
		setAnimationEnabled(false);
		add(message);
	}
	
	public BusyIndicatorView(String msg) {
		this();
		message.setText(msg);
	}
	
	@Override
	public Widget asWidget() {
		return this;
	}

}
 