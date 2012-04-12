package net.thesocialos.client.helper;

import net.thesocialos.client.api.FacebookAPI;
import net.thesocialos.client.api.FlickrAPI;
import net.thesocialos.client.api.Media;
import net.thesocialos.client.api.PicasaAPI;
import net.thesocialos.client.api.PicasaAPI.Album;
import net.thesocialos.client.view.window.FolderWindow;

import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.Image;

public class DblClickHandlerHelper {
	final Media media;
	
	public DblClickHandlerHelper(Media media) {
		this.media = media;
	}
	
	public DoubleClickHandler getDoubleClickHandler() {
		if (media instanceof PicasaAPI.Album) {
			return picasaAlbum;
		} else if (media instanceof PicasaAPI.Picture) {
			return picasaPicture;
		} else if (media instanceof FacebookAPI.Album) {
			return facebookAlbum;
		} else if (media instanceof FacebookAPI.Picture) {
			return facebookPicture;
		} else if (media instanceof FlickrAPI.Album) {
			return flickrAlbum;
		} else if (media instanceof FlickrAPI.Picture) {
			return flickrPicture;
		}/* else if (media instanceof PicasaAPI) {			  YoutubeAPI.Album
			return youtubeAlbum;
		} else if (media instanceof PicasaAPI) {			  YoutubeAPI.Video
			return youtubeVideo;
		}*/
		return null;
	}
	
	private DoubleClickHandler picasaAlbum = new DoubleClickHandler() {
		
		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			FolderWindow folder = new FolderWindow(media.getName());
			new PicasaAPI().loadPicturesInFolder((PicasaAPI.Album) media, folder);
			folder.show();
		}
	};
	
	private DoubleClickHandler picasaPicture = new DoubleClickHandler() {
		
		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			DecoratedPopupPanel popup = new DecoratedPopupPanel(true);
			popup.add(new Image(((PicasaAPI.Picture)media).getUrl()));
			popup.setAnimationEnabled(true);
			popup.setGlassEnabled(true);
			popup.center();
		}
	};
	
	private DoubleClickHandler facebookAlbum = new DoubleClickHandler() {
		
		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			FolderWindow folder = new FolderWindow(media.getName());
			new FacebookAPI().loadPicturesInFolder((FacebookAPI.Album) media, folder);
			folder.show();
		}
	};
	
	private DoubleClickHandler facebookPicture = new DoubleClickHandler() {
		
		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			DecoratedPopupPanel popup = new DecoratedPopupPanel(true);
			popup.add(new Image(((FacebookAPI.Picture)media).getUrl()));
			popup.setAnimationEnabled(true);
			popup.setGlassEnabled(true);
			popup.center();
		}
	};
	
	private DoubleClickHandler flickrAlbum = new DoubleClickHandler() {
		
		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private DoubleClickHandler flickrPicture = new DoubleClickHandler() {
		
		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private DoubleClickHandler youtubeAlbum = new DoubleClickHandler() {
		
		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private DoubleClickHandler youtubeVideo = new DoubleClickHandler() {
		
		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			// TODO Auto-generated method stub
			
		}
	};
}
