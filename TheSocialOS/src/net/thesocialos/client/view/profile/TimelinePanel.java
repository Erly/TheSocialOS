package net.thesocialos.client.view.profile;

import net.thesocialos.client.presenter.TimelinePresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TimelinePanel extends Composite implements Display {
	
	interface TimelinePanelUiBinder extends UiBinder<Widget, TimelinePanel> {
	}
	
	private static TimelinePanelUiBinder uiBinder = GWT.create(TimelinePanelUiBinder.class);
	
	@UiField VerticalPanel mainPanel;
	
	public TimelinePanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public HasWidgets getMainPanel() {
		return mainPanel;
	}
	
}
