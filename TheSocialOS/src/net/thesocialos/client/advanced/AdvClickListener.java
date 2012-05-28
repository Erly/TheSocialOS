package net.thesocialos.client.advanced;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;

public interface AdvClickListener extends ClickListener {
	void onClick(Widget sender, Event event);
	
	void onRightClick(Widget sender, Event event);
}