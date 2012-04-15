package net.thesocialos.client.view.profile;

import net.thesocialos.client.api.TwitterAPI.Tweet;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;

public class TimelinePost extends Composite {

	private static TimelinePostUiBinder uiBinder = GWT
			.create(TimelinePostUiBinder.class);
	@UiField Image photo;
	@UiField HTML post;
	@UiField Label user;
	@UiField Label time;

	interface TimelinePostUiBinder extends UiBinder<Widget, TimelinePost> {
	}

	public TimelinePost() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public TimelinePost(Tweet tweet) {
		initWidget(uiBinder.createAndBindUi(this));
		this.photo.setUrl(tweet.getProfile_image_url());
		this.post.setHTML(tweet.getText());
		this.user.setText(tweet.getUser_name() + " @" + tweet.getScreen_name());
		this.time.setText("");
	}

	/**
	 * @return the post
	 */
	public Label getPost() {
		return post;
	}

	/**
	 * @param post the post to set
	 */
	public void setPost(String post) {
		this.post.setHTML(post);
	}

	/**
	 * @return the user
	 */
	public Label getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(Label user) {
		this.user = user;
	}

	/**
	 * @return the time
	 */
	public Label getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(Label time) {
		this.time = time;
	}

}
