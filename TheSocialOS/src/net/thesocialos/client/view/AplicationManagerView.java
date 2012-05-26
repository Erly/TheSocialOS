package net.thesocialos.client.view;

import net.thesocialos.client.presenter.ApplicationManagerPresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class AplicationManagerView extends Composite implements Display {
	
	private static AplicationManagerViewUiBinder uiBinder = GWT.create(AplicationManagerViewUiBinder.class);
	@UiField SimplePanel htmlPanel;
	@UiField VerticalPanel addPanel;
	
	interface AplicationManagerViewUiBinder extends UiBinder<Widget, AplicationManagerView> {
	}
	
	public AplicationManagerView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public AplicationManagerView(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		
	}
	
	@Override
	public SimplePanel getHtmlPanel() {
		// TODO Auto-generated method stub
		return htmlPanel;
	}
	
	@Override
	public boolean addApplication(Aplication application) {
		// TODO Auto-generated method stub
		addPanel.add(application);
		return true;
	}
	
	@Override
	public boolean removeApplication(Aplication application) {
		// TODO Auto-generated method stub
		System.out.println(addPanel.remove(application));
		return true;
	}
	
}
