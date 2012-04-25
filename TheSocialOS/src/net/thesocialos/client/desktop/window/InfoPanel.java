package net.thesocialos.client.desktop.window;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class InfoPanel extends Composite implements HasText {

	private static InfoPanelUiBinder uiBinder = GWT
			.create(InfoPanelUiBinder.class);

	interface InfoPanelUiBinder extends UiBinder<Widget, InfoPanel> {
	}

	@UiField Label label;
	
	public InfoPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public InfoPanel(String text) {
		initWidget(uiBinder.createAndBindUi(this));
		label.setText(text);
	}

	@Override
	public String getText() {
		return label.getText();
	}

	@Override
	public void setText(String text) {
		label.setText(text);
	}
}
