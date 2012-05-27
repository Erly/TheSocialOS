package net.thesocialos.client.view;

import net.thesocialos.client.presenter.SharedUnit.Display;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class SharedView extends Composite implements Display {
	
	private static ChatSharedViewUiBinder uiBinder = GWT.create(ChatSharedViewUiBinder.class);
	@UiField(provided = true) CellList<Object> cellListImages = new CellList<Object>(new AbstractCell<Object>() {
		@Override
		public void render(Context context, Object value, SafeHtmlBuilder sb) {
			// TODO
		}
	});
	@UiField(provided = true) CellList<Object> cellListVideos = new CellList<Object>(new AbstractCell<Object>() {
		@Override
		public void render(Context context, Object value, SafeHtmlBuilder sb) {
			// TODO
		}
	});
	
	interface ChatSharedViewUiBinder extends UiBinder<Widget, SharedView> {
	}
	
	public SharedView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
}
