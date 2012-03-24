package net.thesocialos.client.presenter;

import net.thesocialos.client.view.PopAsker;
import net.thesocialos.client.view.profile.ProfileAttr;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class ProfilePanelPresenter implements Presenter {

	SimpleEventBus eventBus;
	Display display;
	public String value;
	
	public ProfilePanelPresenter(SimpleEventBus eventBus, Display display) {
		this.eventBus = eventBus;
		this.display = display;
	}
	
	public interface Display {
		ProfileAttr getName();
		
		ProfileAttr getUserTitle();
		
		ProfileAttr getEmail();
		
		ProfileAttr getMobile();
		
		ProfileAttr getAddress();
		
		Widget asWidget();
	}
	
	public void bind() {
		display.getName().getFocusPanel().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				PopAsker asker = getNewValueAsker("Name");
			}
		});
		
		display.getUserTitle().getFocusPanel().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				PopAsker asker = getNewValueAsker("Title"); 
			}
		});
		
		display.getMobile().getFocusPanel().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				PopAsker asker = getNewValueAsker("Mobile");
			}
		});
		
		display.getAddress().getFocusPanel().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				PopAsker asker = getNewValueAsker("Address");
			}
		});
	}
	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(this.display.asWidget());
		bind();
	}
	
	public PopAsker getNewValueAsker(String valueName) {
		value = null;
		final PopupPanel popup = new PopupPanel(true);
		popup.setWidth("40%");
		PopAsker asker = new PopAsker(valueName);
		popup.add(asker);
		asker.setWidth("100%");
		asker.getCancelButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				popup.hide();
			}
		});
		popup.center();
		return asker;
	}

}
