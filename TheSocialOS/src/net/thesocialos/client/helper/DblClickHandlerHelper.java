package net.thesocialos.client.helper;

import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.api.DriveAPI;
import net.thesocialos.client.api.FacebookAPI;
import net.thesocialos.client.api.FlickrAPI;
import net.thesocialos.client.api.Media;
import net.thesocialos.client.api.MediaPicture;
import net.thesocialos.client.api.PicasaAPI;
import net.thesocialos.client.api.YoutubeAPI;
import net.thesocialos.client.app.AppConstants;
import net.thesocialos.client.desktop.DesktopEventOnOpen;
import net.thesocialos.client.desktop.window.FolderWindow;
import net.thesocialos.client.desktop.window.FrameWindow;

import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.Image;

public class DblClickHandlerHelper {
	final FolderWindow folder;
	final Media media;
	
	private DoubleClickHandler picasaAlbum = new DoubleClickHandler() {
		
		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			prepareFolder();
			openPicasaAlbum();
		}
	};
	
	private void openPicasaAlbum() {
		new PicasaAPI().loadPicturesInFolder((PicasaAPI.Album) media, folder);
	}
	
	private DoubleClickHandler picasaPicture = new DoubleClickHandler() {
		
		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			openImage();
		}
	};
	
	private DoubleClickHandler facebookAlbum = new DoubleClickHandler() {
		
		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			prepareFolder();
			openFacebookAlbum();
		}
	};
	
	private void openFacebookAlbum() {
		new FacebookAPI().loadPicturesInFolder((FacebookAPI.Album) media, folder);
	}
	
	private DoubleClickHandler facebookPicture = new DoubleClickHandler() {
		
		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			openImage();
		}
	};
	
	private DoubleClickHandler flickrAlbum = new DoubleClickHandler() {
		
		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			prepareFolder();
			openFlickrAlbum();
		}
	};
	
	private void openFlickrAlbum() {
		prepareFolder();
		new FlickrAPI().loadPicturesInFolder((FlickrAPI.Album) media, folder);
	}
	
	private DoubleClickHandler flickrPicture = new DoubleClickHandler() {
		
		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			openImage();
		}
	};
	
	private DoubleClickHandler youtubeAlbum = new DoubleClickHandler() {
		
		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			prepareFolder();
			openYoutubeAlbum();
		}
	};
	
	private void openYoutubeAlbum() {
		new YoutubeAPI().loadPlaylistVideosInFolder((YoutubeAPI.Album) media, folder);
	}
	
	private DoubleClickHandler youtubeVideo = new DoubleClickHandler() {
		
		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			openVideo();
		}
	};
	
	private DoubleClickHandler youtubeFolder = new DoubleClickHandler() {
		
		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			prepareFolder();
			openYoutubeFolder();
		}
	};
	
	private DoubleClickHandler driveFile = new DoubleClickHandler() {
		
		@Override
		public void onDoubleClick(DoubleClickEvent event) {
			Window.alert(media.getName());
		}
	};
	
	private void openYoutubeFolder() {
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
		TheSocialOS.getEventBus().fireEvent(new DesktopEventOnOpen(folder));
	}
	
	public DblClickHandlerHelper(FolderWindow folder, Media media) {
		this.folder = folder;
		this.media = media;
	}
	
	public DoubleClickHandler getDoubleClickHandler() {
		if (media instanceof PicasaAPI.Album) return picasaAlbum;
		else if (media instanceof PicasaAPI.Picture) return picasaPicture;
		else if (media instanceof FacebookAPI.Album) return facebookAlbum;
		else if (media instanceof FacebookAPI.Picture) return facebookPicture;
		else if (media instanceof FlickrAPI.Album) return flickrAlbum;
		else if (media instanceof FlickrAPI.Picture) return flickrPicture;
		else if (media instanceof YoutubeAPI.Album) return youtubeAlbum;
		else if (media instanceof YoutubeAPI.Video) return youtubeVideo;
		else if (media instanceof YoutubeAPI.Folder) return youtubeFolder;
		else if (media instanceof DriveAPI.File) return driveFile;
		return null;
	}
	
	protected void openImage() {
		DecoratedPopupPanel popup = new DecoratedPopupPanel(true);
		popup.add(new Image(((MediaPicture) media).getUrl()));
		popup.setAnimationEnabled(true);
		popup.setGlassEnabled(true);
		popup.getElement().getStyle().setZIndex(850);
		popup.center();
	}
	
	protected void openVideo() {
		FrameWindow window = new FrameWindow(((MediaPicture) media).getUrl(), "VIDEO", AppConstants.VIDEOPLAYER);
		TheSocialOS.getEventBus().fireEvent(new DesktopEventOnOpen(window));
		/*
		 * DecoratedPopupPanel popup = new DecoratedPopupPanel(true); Frame frame = new Frame(((MediaPicture)
		 * media).getUrl()); frame.setSize("560px", "315px"); popup.add(frame); popup.setAnimationEnabled(true);
		 * popup.setGlassEnabled(true); popup.getElement().getStyle().setZIndex(850); popup.center();
		 */
	}
	
	public void prepareFolder() {
		folder.setTitle(media.getName());
		folder.clearMedia();
		folder.next();
		folder.setParent(media);
	}
	
	public void simulateDblClick() {
		if (media instanceof PicasaAPI.Album) openPicasaAlbum();
		else if (media instanceof FacebookAPI.Album) openFacebookAlbum();
		else if (media instanceof FlickrAPI.Album) openFlickrAlbum();
		else if (media instanceof YoutubeAPI.Album) openYoutubeAlbum();
		else if (media instanceof YoutubeAPI.Folder) openYoutubeFolder();
	}
}
