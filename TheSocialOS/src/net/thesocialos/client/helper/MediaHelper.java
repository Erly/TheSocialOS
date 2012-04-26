package net.thesocialos.client.helper;

import java.util.HashSet;

import com.google.gwt.user.client.ui.WindowPanelLayout;
import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.api.FacebookAPI;
import net.thesocialos.client.api.FlickrAPI;
import net.thesocialos.client.api.Media;
import net.thesocialos.client.api.PicasaAPI;
import net.thesocialos.client.api.YoutubeAPI;
import net.thesocialos.client.app.AppConstants;
import net.thesocialos.client.desktop.DesktopEventOnOpen;
import net.thesocialos.client.desktop.window.FolderWindow;
import net.thesocialos.client.desktop.window.Footer;
import net.thesocialos.client.desktop.window.MyCaption;

public class MediaHelper {
	
	/**
	 * Loads the photo albums in a new FolderWindow
	 */
	protected static void loadPictureAlbums() {
		// final HashSet<Album> albums = new HashSet<Picasa.Album>();
		FolderWindow folder = new FolderWindow("Picture albums", FolderWindow.PICTURES, new WindowPanelLayout(false,
				false, new MyCaption(), new Footer()), AppConstants.IMAGEFOLDERS);
		loadPicasaAlbums(folder);
		loadFacebookAlbums(folder);
		loadFlickrAlbums(folder);
		TheSocialOS.getEventBus().fireEvent(new DesktopEventOnOpen(folder));
		// folder.show();
	}
	
	protected static void loadVideoFolders() {
		// Temporally load only the youtube playlists
		FolderWindow folder = new FolderWindow("Youtube playlists", FolderWindow.VIDEOS, new WindowPanelLayout(false,
				false, new MyCaption(), new Footer()), AppConstants.VIDEOFOLDERS);
		HashSet<Media> mediaSet = new HashSet<Media>();
		YoutubeAPI yt = new YoutubeAPI();
		mediaSet.add(yt.createFolder(YoutubeAPI.Folder.TYPE.UPLOADS));
		mediaSet.add(yt.createFolder(YoutubeAPI.Folder.TYPE.PLAYLIST));
		mediaSet.add(yt.createFolder(YoutubeAPI.Folder.TYPE.FAVORITES));
		
		folder.addMedia(mediaSet);
		TheSocialOS.getEventBus().fireEvent(new DesktopEventOnOpen(folder));
		// folder.show();
	}
	
	/**
	 * Loads the Picasa albums (if there is any) in the passed FolderWindow
	 * 
	 * @param folder
	 *            The FolderWindow in which the albums are gonna be loaded
	 */
	private static void loadPicasaAlbums(final FolderWindow folder) {
		new PicasaAPI().loadAlbumsInFolder(folder);
	}
	
	/**
	 * Loads the Facebook albums (if there is any) in the passed FolderWindow
	 * 
	 * @param folder
	 *            The FolderWindow in which the albums are gonna be loaded
	 */
	private static void loadFacebookAlbums(final FolderWindow folder) {
		new FacebookAPI().loadAlbumsInFolder(folder);
	}
	
	/**
	 * Loads the Flickr albums (if there is any) in the passed FolderWindow
	 * 
	 * @param folder
	 *            The FolderWindow in which the albums are gonna be loaded
	 */
	private static void loadFlickrAlbums(final FolderWindow folder) {
		new FlickrAPI().loadAlbumsInFolder(folder);
	}
}