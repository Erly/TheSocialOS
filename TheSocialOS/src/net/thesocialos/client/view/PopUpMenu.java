package net.thesocialos.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.MenuItem;

public class PopUpMenu extends PopupPanel{
	@UiField MenuItem MenuISendPriv;
	@UiField MenuItem MenuIAccept;
	@UiField MenuItem MenuIDeny;
	@UiField MenuItem MenuIViewPerfil;
	interface Binder extends UiBinder<Widget, PopUpMenu> { } 
	private static final Binder binder = GWT.create(Binder.class);
	
	public MenuItem getMenuIViewPerfil() {
		return MenuIViewPerfil;
	}




	public MenuItem getMenuISendPriv() {
		return MenuISendPriv;
	}




	public MenuItem getMenuIDeny() {
		return MenuIDeny;
	}




	public MenuItem getMenuIAccept() {
		return MenuIAccept;
	}


	

	interface PopUpmenuUiBinder extends
	UiBinder<Widget, PopUpMenu> {
}

	public PopUpMenu() {
		super(true);
		add(binder.createAndBindUi(this));
	}



	
	public void show(int x, int y){
		this.setPopupPosition(x, y);
		this.show();
	}
	
	





}
