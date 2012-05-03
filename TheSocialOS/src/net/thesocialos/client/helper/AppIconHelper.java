package net.thesocialos.client.helper;

import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.view.Icon;

import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;

public class AppIconHelper {
	
	public static void populateDesktopWithIcons(AbsolutePanel desktop) {
		Icon pictures = new Icon(Icon.FOLDER_ICON, TheSocialOS.getConstants().pictures());
		pictures.setTitle(TheSocialOS.getConstants().pictures());
		Icon videos = new Icon(Icon.FOLDER_ICON, TheSocialOS.getConstants().videos());
		videos.setTitle(TheSocialOS.getConstants().videos());
		Icon music = new Icon(Icon.FOLDER_ICON, TheSocialOS.getConstants().music());
		music.setTitle(TheSocialOS.getConstants().music());
		Icon other = new Icon(Icon.FOLDER_ICON, TheSocialOS.getConstants().other());
		other.setTitle(TheSocialOS.getConstants().other());
		
		Icon[] folders = { pictures, videos, music, other };
		for (int i = 0; i < folders.length; i++)
			desktop.add(folders[i], 10, 10 + i * 90);
		
		pictures.addDoubleClickHandler(new DoubleClickHandler() {
			
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				MediaHelper.loadPictureAlbums();
			}
		});
		
		videos.addDoubleClickHandler(new DoubleClickHandler() {
			
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				MediaHelper.loadVideoFolders();
			}
		});
		other.addDoubleClickHandler(new DoubleClickHandler() {
			
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				MediaHelper.loadOtherFolders();
			}
		});
	}
}
