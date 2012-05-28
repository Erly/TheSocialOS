package net.thesocialos.client.view.deck;

import net.thesocialos.client.presenter.SocialDeckPresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class SocialDeck extends Composite implements Display {
	
	interface SocialDeckUiBinder extends UiBinder<Widget, SocialDeck> {
	}
	
	private static SocialDeckUiBinder uiBinder = GWT.create(SocialDeckUiBinder.class);
	@UiField TextArea textField;
	@UiField Button postButton;
	@UiField TabPanel postsColumnsPanel;
	/*
	 * @UiField HorizontalPanel plusTab;
	 * @UiField HorizontalPanel facebookTab;
	 * @UiField HorizontalPanel twitterTab;
	 * @UiField HorizontalPanel allTab;
	 */
	@UiField HorizontalPanel timelineTab;
	
	public SocialDeck() {
		initWidget(uiBinder.createAndBindUi(this));
		postsColumnsPanel.selectTab(0);
	}
	
	@Override
	public HasWidgets getAllPostColumnsPanel() {
		return timelineTab;
	}
	
	@Override
	public HasClickHandlers getPostButton() {
		return postButton;
	}
	
	@Override
	public TextArea getTextField() {
		return textField;
	}
}
