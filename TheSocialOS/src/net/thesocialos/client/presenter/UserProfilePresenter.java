package net.thesocialos.client.presenter;

import net.thesocialos.client.helper.RPCCall;
import net.thesocialos.shared.UserDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class UserProfilePresenter implements Presenter {
	
	private UserDTO user;
	
	public interface Display {
		/*HasClickHandlers getProfileButton();
		HasClickHandlers getTimelineButton();
		HasClickHandlers getPhotosButton();
		HasClickHandlers getMusicButton();
		HasClickHandlers getVideosButton();
		HasClickHandlers getLinksButton();
		*/
		void setData(UserDTO user);
		
		Widget asWidget();
	}

	private SimpleEventBus eventBus;
	private final Display display;
	
	public UserProfilePresenter(SimpleEventBus eventBus, Display display) {
		this.eventBus = eventBus;
		this.display = display;
	}
	
	public void bind() {
		
	}
	
	@Override
	public void go(final HasWidgets container) {
		GWT.log("UserProfilePresenter.go");
		container.clear();
		container.add(display.asWidget());
	}
	
	public void fetchUserDTO() {
		new RPCCall<UserDTO>() {

			@Override
			protected void callService(AsyncCallback<UserDTO> cb) {
			}
			
		};
	}

}
