package net.thesocialos.client.view;

import net.thesocialos.client.presenter.SearchBoxPresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.Label;

public class SearchBoxView extends Composite implements Display{

	private static SearchBoxUiBinder uiBinder = GWT
			.create(SearchBoxUiBinder.class);
	@UiField(provided=true) CellList<Object> cellList = new CellList<Object>(new AbstractCell<Object>(){
		@Override
		public void render(Context context, Object value, SafeHtmlBuilder sb) {
			// TODO
		}
	});
	@UiField Label lblFriends;
	@UiField Label lblGroups;

	interface SearchBoxUiBinder extends UiBinder<Widget, SearchBoxView> {
	}

	public SearchBoxView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Label getLabelFriends() {
		// TODO Auto-generated method stub
		return lblFriends;
	}

	@Override
	public Label getLabelGroups() {
		// TODO Auto-generated method stub
		return lblGroups;
	}

	@Override
	public CellList<Object> getComponentsList() {
		// TODO Auto-generated method stub
		return cellList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setComponentsList(CellList<?> cellList) {
		// TODO Auto-generated method stub
		this.cellList = (CellList<Object>) cellList;
	}

}
