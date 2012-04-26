package net.thesocialos.client.view.deck;

import net.thesocialos.client.api.TwitterAPI.Tweet;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class TimelinePost extends Composite {
	
	interface TimelinePostUiBinder extends UiBinder<Widget, TimelinePost> {
	}
	
	private static TimelinePostUiBinder uiBinder = GWT.create(TimelinePostUiBinder.class);
	@UiField Image photo;
	@UiField HTML post;
	@UiField Label user;
	
	@UiField Label time;
	
	public TimelinePost() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public TimelinePost(Tweet tweet) {
		initWidget(uiBinder.createAndBindUi(this));
		photo.setUrl(tweet.getProfile_image_url());
		post.setHTML(tweet.getText());
		user.setText(tweet.getUser_name() + " @" + tweet.getScreen_name());
		String time = "";
		long dif = System.currentTimeMillis() - tweet.getCreated_at().getTime();
		if (dif < 1000) time = "now";
		else if (dif < 60000) time = dif / 1000 + "s";
		else if (dif < 60 * 60 * 1000) time = dif / 60 / 1000 + "m";
		else if (dif < 24 * 60 * 60 * 1000) time = dif / 60 / 60 / 1000 + "h";
		else
			time = dif / 24 / 60 / 60 / 1000 + "d";
		this.time.setText(time);
	}
	
	/**
	 * @return the post
	 */
	public Label getPost() {
		return post;
	}
	
	/**
	 * @return the time
	 */
	public Label getTime() {
		return time;
	}
	
	/**
	 * @return the user
	 */
	public Label getUser() {
		return user;
	}
	
	/**
	 * @param post
	 *            the post to set
	 */
	public void setPost(String post) {
		this.post.setHTML(post);
	}
	
	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(Label time) {
		this.time = time;
	}
	
	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(Label user) {
		this.user = user;
	}
	
}
