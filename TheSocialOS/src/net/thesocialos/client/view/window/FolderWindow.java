package net.thesocialos.client.view.window;

import java.util.HashSet;
import java.util.Iterator;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;

import net.thesocialos.client.api.Media;
import net.thesocialos.client.view.Thumbnail;

public class FolderWindow {

	FlexTable table = new FlexTable();
	String title = "";
	private int i = 0;
	private int j = 0;
	
	public FolderWindow() {
		// TODO Auto-generated constructor stub
	}
	
	public FolderWindow(String title, HashSet<? extends Media> mediaSet) {
		this.title = title;
		Iterator<? extends Media> iterator = mediaSet.iterator();
		i = 0; j = 0;
		while (iterator.hasNext()) {
			Media media = iterator.next();
			Thumbnail thumb = new Thumbnail(media.getThumbnailURL(), media.getName());
			table.setWidget(j, i, thumb);
			//table.getFlexCellFormatter().setWidth(j, i, "150px");
			table.getFlexCellFormatter().setVerticalAlignment(j, i, HasVerticalAlignment.ALIGN_TOP);
			i++;
			if (i % 4 == 0) {
				j++;
				i = 0;
			}
		}
	}
	
	public FolderWindow(String title) {
		this.title = title;
	}

	public void show() {
		DialogBoxExt window = new DialogBoxExt(false, false, new MyCaption());
		window.setText(title);
		ScrollPanel panel = new ScrollPanel();
		panel.add(table);
		window.add(panel);
		panel.setSize("800px", "480px");
		window.show();
		window.setPopupPosition(10, 30);
	}
	
	public void addMedia(HashSet<? extends Media> mediaSet) {
		Iterator<? extends Media> iterator = mediaSet.iterator();
		while (iterator.hasNext()) {
			Media media = iterator.next();
			Thumbnail thumb = new Thumbnail(media.getThumbnailURL(), media.getName());
			table.setWidget(j, i, thumb);
			table.getFlexCellFormatter().setVerticalAlignment(j, i, HasVerticalAlignment.ALIGN_TOP);
			i++;
			if (i % 4 == 0) {
				j++;
				i = 0;
			}
		}
	}
}
