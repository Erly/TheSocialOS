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

public class Petitions extends Composite {

	private static PetitionsUiBinder uiBinder = GWT
			.create(PetitionsUiBinder.class);
	@UiField(provided=true) CellList<Object> cellList = new CellList<Object>(new AbstractCell<Object>(){
		@Override
		public void render(Context context, Object value, SafeHtmlBuilder sb) {
			// TODO
		}
	});

	interface PetitionsUiBinder extends UiBinder<Widget, Petitions> {
	}

	public Petitions() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
