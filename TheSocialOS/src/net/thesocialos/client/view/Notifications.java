package net.thesocialos.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TabLayoutPanel;

public class Notifications extends Composite {

	private static NotificationsUiBinder uiBinder = GWT
			.create(NotificationsUiBinder.class);
	

	interface NotificationsUiBinder extends UiBinder<Widget, Notifications> {
	}

	public Notifications() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
