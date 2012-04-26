package net.thesocialos.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class VideoPlayer extends Composite {
	
	interface VideoPlayerUiBinder extends UiBinder<Widget, VideoPlayer> {
	}
	
	private static VideoPlayerUiBinder uiBinder = GWT.create(VideoPlayerUiBinder.class);
	
	public VideoPlayer(String src) {
		initWidget(uiBinder.createAndBindUi(this));
		Document.get().getElementById("video").setPropertyString("src", src);
	}
	
}
