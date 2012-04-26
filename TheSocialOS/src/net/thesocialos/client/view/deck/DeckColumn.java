package net.thesocialos.client.view.deck;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.thesocialos.client.api.TwitterAPI.Tweet;
import net.thesocialos.shared.model.Columns;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DeckColumn extends Composite implements HasWidgets {
	
	interface DeckColumnUiBinder extends UiBinder<Widget, DeckColumn> {
	}
	
	private static DeckColumnUiBinder uiBinder = GWT.create(DeckColumnUiBinder.class);
	@UiField Label title;
	@UiField Label newCounts;
	
	@UiField VerticalPanel postsColumn;
	
	private Columns columns;
	private Set<Tweet> tweets = new HashSet<Tweet>();
	
	public DeckColumn() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public DeckColumn(String title) {
		initWidget(uiBinder.createAndBindUi(this));
		this.title.setText(title);
	}
	
	@Override
	public void add(Widget w) {
		postsColumn.add(w);
	}
	
	public void addTweet(Tweet tweet) {
		tweets.add(tweet);
	}
	
	public void addTweets(Set<Tweet> tweetsSet) {
		tweets.addAll(tweetsSet);
	}
	
	@Override
	public void clear() {
		postsColumn.clear();
	}
	
	public void clearTweets() {
		tweets.clear();
	}
	
	/**
	 * @return the columns
	 */
	public Columns getColumns() {
		return columns;
	}
	
	@Override
	public String getTitle() {
		return title.getText();
	}
	
	/**
	 * @return the tweets
	 */
	public Set<Tweet> getTweets() {
		return tweets;
	}
	
	@Override
	public Iterator<Widget> iterator() {
		return postsColumn.iterator();
	}
	
	public void loadTweets() {
		Set<TimelinePost> posts = new HashSet<TimelinePost>();
		for (Tweet t : tweets)
			posts.add(new TimelinePost(t));
		postsColumn.clear();
		for (TimelinePost tp : posts)
			postsColumn.add(tp);
	}
	
	@Override
	public boolean remove(Widget w) {
		return postsColumn.remove(w);
	}
	
	/**
	 * @param columns
	 *            the columns to set
	 */
	public void setColumns(Columns columns) {
		this.columns = columns;
	}
	
	@Override
	public void setTitle(String title) {
		this.title.setText(title);
	}
	
	/**
	 * @param tweets
	 *            the tweets to set
	 */
	public void setTweets(Set<Tweet> tweets) {
		this.tweets = tweets;
	}
}