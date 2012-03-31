package net.thesocialos.client.helper;

import java.util.HashSet;

import net.thesocialos.client.api.Picasa;
import net.thesocialos.client.api.Picasa.Album;
import net.thesocialos.client.view.window.FolderWindow;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class PicturesHelper {
	
	/**
	 * Makes multiple AsyncCallbacks (one inside another) for loading all the albums 
	 * and then it opens a window with all the albums. 
	 */
	protected static void loadPictureAlbums() {
		//final HashSet<Album> albums = new HashSet<Picasa.Album>();
		loadPicasaAlbums(new HashSet<Picasa.Album>());
	}

	/**
	 * Loads the Picasa albums (if there is any) and calls loadFacebookAlbums
	 * @param albums The Hashset in which the albums are going to be saved
	 */
	private static void loadPicasaAlbums(final HashSet<Album> albums) {
		final Picasa picasa = new Picasa();
		try {
			picasa.getAlbumsRequest(new AsyncCallback<JavaScriptObject>() {
				
				@Override
				public void onSuccess(JavaScriptObject result) {
					if (null != result) {
						JSONObject object = new JSONObject(result);
						albums.addAll(picasa.getAlbums(object));
					}
					loadFacebookAlbums(albums);
				}
				
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}
			});
		} catch (RequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Loads the Facebook albums (if there is any) and calls loadFlickrAlbums
	 * @param albums The Hashset in which the albums are going to be saved
	 */
	private static void loadFacebookAlbums(final HashSet<Album> albums) {
		// TODO Auto-generated method stub
		loadFlickrAlbums(albums);
	}

	/**
	 * Loads the Flickr albums (if there is any) and opens a FolderWindow 
	 * with all the albums from Picasa, Facebook and Flickr
	 * @param albums The Hashset in which the albums are going to be saved
	 */
	private static void loadFlickrAlbums(final HashSet<Album> albums) {
		// TODO Auto-generated method stub
		FolderWindow window = new FolderWindow("Picture albums", albums);
		window.show();
	}
}