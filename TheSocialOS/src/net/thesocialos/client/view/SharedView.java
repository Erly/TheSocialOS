package net.thesocialos.client.view;

import java.util.Date;

import net.thesocialos.client.presenter.SharedUnit.Display;
import net.thesocialos.shared.model.SharedHistory;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class SharedView extends Composite implements Display {
	
	private static ChatSharedViewUiBinder uiBinder = GWT.create(ChatSharedViewUiBinder.class);
	@UiField(provided = true) CellList<SharedHistory> cellListImages = new CellList<SharedHistory>(
			new AbstractCell<SharedHistory>() {
				@Override
				public void render(Context context, SharedHistory value, SafeHtmlBuilder sb) {
					if (value == null) return;
					String cssTableURL = null;
					switch (value.getMessageType()) {
					case IMAGE:
						cssTableURL = "sharedTableImage";
						break;
					case VIDEO:
						cssTableURL = "sharedTableVideo";
						break;
					
					default:
						break;
					}
					DateTimeFormat fmt = DateTimeFormat.getFormat("HH:mm:ss | dd/MM/yyyy");
					Date date = new Date(value.getDate());
					
					sb.appendHtmlConstant(" <table class=" + cssTableURL + "> " + "<tr>" + "<td class='sharedTable'>");
					sb.appendEscaped(value.getTittle());
					sb.appendHtmlConstant("</td>" + "</tr>" + "<tr>" + "<td class='sharedTable1'>");
					sb.appendEscaped(value.getSendUser().getName());
					sb.appendHtmlConstant("</td>" + "<tr>" + "<td class='sharedTable1'>");
					sb.appendEscaped(fmt.format(date));
					sb.appendHtmlConstant("</td>" + "</tr>" + "</table>");
					
				}
			});
	@UiField SimplePanel widgetPanel;
	
	interface ChatSharedViewUiBinder extends UiBinder<Widget, SharedView> {
	}
	
	public SharedView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public CellList<SharedHistory> getComponentsList() {
		// TODO Auto-generated method stub
		return cellListImages;
	}
	
	@Override
	public SimplePanel getSimplePanel() {
		// TODO Auto-generated method stub
		return widgetPanel;
	}
	
}
