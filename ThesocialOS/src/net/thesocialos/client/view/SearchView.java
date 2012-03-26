package net.thesocialos.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;

public class SearchView extends Composite {

	private static SearchViewUiBinder uiBinder = GWT
			.create(SearchViewUiBinder.class);
	@UiField(provided=true) CellList<Object> cellList = new CellList<Object>(new AbstractCell<Object>(){
		@Override
		public void render(Context context, Object value, SafeHtmlBuilder sb) {
			// TODO
		}
	});
	@UiField(provided=true) CellList<Object> cellList_1 = new CellList<Object>(new AbstractCell<Object>(){
		@Override
		public void render(Context context, Object value, SafeHtmlBuilder sb) {
			// TODO
		}
	});

	interface SearchViewUiBinder extends UiBinder<Widget, SearchView> {
	}

	public SearchView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
