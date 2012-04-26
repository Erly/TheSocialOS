package net.thesocialos.client.helper;

import net.thesocialos.client.api.FacebookAPI;
import net.thesocialos.client.api.FlickrAPI;
import net.thesocialos.client.api.Media;
import net.thesocialos.client.api.MediaPicture;
import net.thesocialos.client.api.PicasaAPI;
import net.thesocialos.client.api.YoutubeAPI;
import net.thesocialos.client.view.window.FolderWindow;

import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Image;

public class DblClickHandlerHelper {
	final Media media;
	
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
			openImage();
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
			openImage();
		}
	};
	
	private DoubleClickHandler flickrAlbum = new DoubleClickHandler() {
		
		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			FolderWindow folder = new FolderWindow(media.getName());
			new FlickrAPI().loadPicturesInFolder((FlickrAPI.Album) media, folder);
			folder.show();
		}
	};
	
	private DoubleClickHandler flickrPicture = new DoubleClickHandler() {
		
		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			openImage();
		}
	};
	
	private DoubleClickHandler youtubeAlbum = new DoubleClickHandler() {
		
		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			FolderWindow folder = new FolderWindow(media.getName());
			new YoutubeAPI().loadPlaylistVideosInFolder((YoutubeAPI.Album) media, folder);
			folder.show();
		}
	};
	
	private DoubleClickHandler youtubeVideo = new DoubleClickHandler() {
		
		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			openVideo();
		}
	};
	
	private DoubleClickHandler youtubeFolder = new DoubleClickHandler() {
		
		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			FolderWindow folder = new FolderWindow(media.getName());
			switch (((YoutubeAPI.Folder) media).getType()) {
			case UPLOADS:
				new YoutubeAPI().loadUploadsInFolder(folder);
				break;
			case PLAYLIST:
				new YoutubeAPI().loadPlaylistsInFolder(folder);
				break;
			case FAVORITES:
				new YoutubeAPI().loadFavoritesInFolder(folder);
				break;
			}
			folder.show();
		}
	};
	
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
		} else if (media instanceof YoutubeAPI.Album) {
			return youtubeAlbum;
		} else if (media instanceof YoutubeAPI.Video) {
			return youtubeVideo;
		} else if (media instanceof YoutubeAPI.Folder) { return youtubeFolder; }
		return null;
	}
	
	protected void openImage() {
		DecoratedPopupPanel popup = new DecoratedPopupPanel(true);
		popup.add(new Image(((MediaPicture) media).getUrl()));
		popup.setAnimationEnabled(true);
		popup.setGlassEnabled(true);
		popup.center();
	}
	
	protected void openVideo() {
		DecoratedPopupPanel popup = new DecoratedPopupPanel(true);
		Frame frame = new Frame(((MediaPicture) media).getUrl());
		frame.setSize("560px", "315px");
		popup.add(frame);
		popup.setAnimationEnabled(true);
		popup.setGlassEnabled(true);
		popup.center();
	}
}
