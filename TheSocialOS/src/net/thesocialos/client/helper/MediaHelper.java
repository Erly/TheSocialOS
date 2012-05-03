package net.thesocialos.client.helper;

import java.util.HashSet;

import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.api.DriveAPI;
import net.thesocialos.client.api.FacebookAPI;
import net.thesocialos.client.api.FlickrAPI;
import net.thesocialos.client.api.Media;
import net.thesocialos.client.api.MediaParent;
import net.thesocialos.client.api.PicasaAPI;
import net.thesocialos.client.api.YoutubeAPI;
import net.thesocialos.client.app.AppConstants;
import net.thesocialos.client.desktop.DesktopEventOnOpen;
import net.thesocialos.client.desktop.window.FolderWindow;

public class MediaHelper {
	
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
	 * Loads the Drive folders (if there is any) in the passed FolderWindow
	 * 
	 * @param folder
	 *            The FolderWindow in which the albums are gonna be loaded
	 */
	private static void loadDriveFolders(final FolderWindow folder) {
		new DriveAPI().loadFoldersInFolder(folder);
	}
	
	/**
	 * Loads the photo albums in a new FolderWindow
	 */
	protected static void loadPictureAlbums() {
		// final HashSet<Album> albums = new HashSet<Picasa.Album>();
		FolderWindow folder = new FolderWindow("Picture albums", FolderWindow.PICTURES, AppConstants.IMAGEFOLDERS);
		reloadPictureAlbums(folder);
		folder.setParent(new MediaParent(MediaParent.PICTURES, "Picture albums"));
		TheSocialOS.getEventBus().fireEvent(new DesktopEventOnOpen(folder));
		// folder.show();
	}
	
	public static void reloadPictureAlbums(FolderWindow folder) {
		loadPicasaAlbums(folder);
		loadFacebookAlbums(folder);
		loadFlickrAlbums(folder);
	}
	
	protected static void loadVideoFolders() {
		// Temporally load only the youtube playlists
		FolderWindow folder = new FolderWindow("Youtube playlists", FolderWindow.VIDEOS, AppConstants.VIDEOFOLDERS);
		reloadVideoFolders(folder);
		folder.setParent(new MediaParent(MediaParent.VIDEOS, "Youtube playlists"));
		TheSocialOS.getEventBus().fireEvent(new DesktopEventOnOpen(folder));
		// folder.show();
	}
	
	public static void reloadVideoFolders(FolderWindow folder) {
		HashSet<Media> mediaSet = new HashSet<Media>();
		YoutubeAPI yt = new YoutubeAPI();
		mediaSet.add(yt.createFolder(YoutubeAPI.Folder.TYPE.UPLOADS));
		mediaSet.add(yt.createFolder(YoutubeAPI.Folder.TYPE.PLAYLIST));
		mediaSet.add(yt.createFolder(YoutubeAPI.Folder.TYPE.FAVORITES));
		
		folder.addMedia(mediaSet);
	}
	
	public static void loadOtherFolders() {
		FolderWindow folder = new FolderWindow("Other documents", FolderWindow.OTHER, AppConstants.OTHERFOLDERS);
		reloadOtherFolders(folder);
		folder.setParent(new MediaParent(MediaParent.OTHER, "Other documents"));
		TheSocialOS.getEventBus().fireEvent(new DesktopEventOnOpen(folder));
	}
	
	public static void reloadOtherFolders(FolderWindow folder) {
		loadDriveFolders(folder);
	}
}