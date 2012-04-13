package net.thesocialos.client.helper;

import net.thesocialos.client.api.FacebookAPI;
import net.thesocialos.client.api.FlickrAPI;
import net.thesocialos.client.api.PicasaAPI;
import net.thesocialos.client.view.window.FolderWindow;

public class PicturesHelper {
	
	/**
	 * Makes multiple AsyncCallbacks (one inside another) for loading all the albums 
	 * and then it opens a window with all the albums. 
	 */
	protected static void loadPictureAlbums() {
		//final HashSet<Album> albums = new HashSet<Picasa.Album>();
		FolderWindow folder = new FolderWindow("Picture albums", FolderWindow.PICTURES);
		loadPicasaAlbums(folder);
		loadFacebookAlbums(folder);
		loadFlickrAlbums(folder);
		folder.show();
	}

	/**
	 * Loads the Picasa albums (if there is any) and calls loadFacebookAlbums
	 * @param albums The Hashset in which the albums are going to be saved
	 */
	private static void loadPicasaAlbums(final FolderWindow folder) {
		new PicasaAPI().loadAlbumsInFolder(folder);
	}

	/**
	 * Loads the Facebook albums (if there is any) and calls loadFlickrAlbums
	 * @param albums The Hashset in which the albums are going to be saved
	 */
	private static void loadFacebookAlbums(final FolderWindow folder) {
		new FacebookAPI().loadAlbumsInFolder(folder);
	}

	/**
	 * Loads the Flickr albums (if there is any) and opens a FolderWindow 
	 * with all the albums from Picasa, Facebook and Flickr
	 * @param albums The Hashset in which the albums are going to be saved
	 */
	private static void loadFlickrAlbums(final FolderWindow folder) {
		new FlickrAPI().loadAlbumsInFolder(folder);
	}
}