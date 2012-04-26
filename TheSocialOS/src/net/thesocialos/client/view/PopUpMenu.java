package net.thesocialos.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.MenuItem;

public class PopUpMenu extends PopupPanel {
	interface Binder extends UiBinder<Widget, PopUpMenu> {
	}
	interface PopUpmenuUiBinder extends UiBinder<Widget, PopUpMenu> {
	}
	@UiField MenuItem MenuISendPriv;
	@UiField MenuItem MenuIAccept;
	
	@UiField MenuItem MenuIDeny;
	
	@UiField MenuItem MenuIViewPerfil;
	
	private static final Binder binder = GWT.create(Binder.class);
	
	public PopUpMenu() {
		super(true);
		add(binder.createAndBindUi(this));
	}
	
	public MenuItem getMenuIAccept() {
		return MenuIAccept;
	}
	
	public MenuItem getMenuIDeny() {
		return MenuIDeny;
	}
	
	public MenuItem getMenuISendPriv() {
		return MenuISendPriv;
	}
	
	public MenuItem getMenuIViewPerfil() {
		return MenuIViewPerfil;
	}
	
	public void show(int x, int y) {
		this.setPopupPosition(x, y);
		this.show();
	}
	
}
