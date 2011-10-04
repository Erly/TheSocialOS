package net.thesocialos.client;

import net.thesocialos.client.presenter.Presenter;
import net.thesocialos.client.presenter.RegisterPresenter;
import net.thesocialos.client.service.UserService;
import net.thesocialos.client.service.UserServiceAsync;
import net.thesocialos.client.view.RegisterView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class AppController implements ValueChangeHandler<String> {

	//private final UserServiceAsync userService = GWT.create(UserService.class);

	private final SimpleEventBus eventBus;
	
	public AppController(SimpleEventBus eventBus) {
		this.eventBus = eventBus;
		bind();
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();
		if (token != null) {
			Presenter presenter = null;
			if (token.equals("register")) { 
				presenter = new RegisterPresenter(eventBus, new RegisterView());
				presenter.go(TheSocialOS.get().root);
				return;
			} else if (token.equals("login")) {
				TheSocialOS.get().showLoginView();
				return;
			}
		}
	}
	
	private void bind() {
		History.addValueChangeHandler(this);
	}
	
	public void go() {
		if (History.getToken().equals("")) {
			History.newItem("index");
		} else {
			History.fireCurrentHistoryState();
		}
	}
}
