package net.thesocialos.client.view.deck;

import net.thesocialos.client.CacheLayer;
import net.thesocialos.client.api.TwitterAPI;
import net.thesocialos.shared.model.Columns;
import net.thesocialos.shared.model.Columns.TYPE;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class AddColumn extends Composite {
	
	private static AddColumnUiBinder uiBinder = GWT.create(AddColumnUiBinder.class);
	@UiField ListBox typeList;
	@UiField TextBox searchBox;
	@UiField RadioButton homeRadio;
	@UiField RadioButton mentionsRadio;
	@UiField Button showButton;
	@UiField DeckColumn column;
	@UiField Button addColumn;
	
	int type = 0;
	
	interface AddColumnUiBinder extends UiBinder<Widget, AddColumn> {
	}
	
	public AddColumn() {
		initWidget(uiBinder.createAndBindUi(this));
		searchBox.setVisible(false);
		homeRadio.setVisible(false);
		mentionsRadio.setVisible(false);
		showButton.setVisible(false);
		typeList.addItem(" - Select a column type - ");
		typeList.addItem("Timeline");
		typeList.addItem("Search");
		// typeList.addItem("List");
		typeList.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				if (typeList.getSelectedIndex() == 1) {
					searchBox.setVisible(false);
					homeRadio.setVisible(true);
					mentionsRadio.setVisible(true);
				} else if (typeList.getSelectedIndex() == 2) {
					searchBox.setVisible(true);
					homeRadio.setVisible(false);
					mentionsRadio.setVisible(false);
				}
				showButton.setVisible(true);
				type = typeList.getSelectedIndex();
			}
		});
		showButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				column.clearPosts();
				column.clearTweets();
				if (type == 1) {
					if (homeRadio.getValue()) column.setColumns(new Columns(TYPE.TIMELINE, Columns.HOME));
					else if (mentionsRadio.getValue()) column.setColumns(new Columns(TYPE.TIMELINE, Columns.MENTIONS));
					else
						return;
					new TwitterAPI().loadColumns(column);
				}
				if (type == 2) {
					if (searchBox.getValue().equals("")) return;
					column.setColumns(new Columns(TYPE.SEARCH, searchBox.getValue()));
					new TwitterAPI().loadColumns(column);
				}
			}
		});
		addColumn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				CacheLayer.UserCalls.addColumn(column.getColumns());
			}
		});
		column.getParent().getElement().getStyle().setWidth(100, Unit.PCT);
	}
}