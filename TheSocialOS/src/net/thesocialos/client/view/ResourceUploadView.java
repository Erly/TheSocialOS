package net.thesocialos.client.view;

import gwtupload.client.SingleUploader;
import net.thesocialos.client.presenter.ResourceUploadPresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class ResourceUploadView extends Composite implements Display {
	
	private static ResourceUploadUiBinder uiBinder = GWT.create(ResourceUploadUiBinder.class);
	@UiField Label lblFacebook;
	@UiField Label lblPicassa;
	@UiField HTMLPanel htmlPanel;
	@UiField SimplePanel UploadPanel;
	SingleUploader uploader = new SingleUploader();
	
	interface ResourceUploadUiBinder extends UiBinder<Widget, ResourceUploadView> {
	}
	
	public ResourceUploadView() {
		initWidget(uiBinder.createAndBindUi(this));
		uploader.setAutoSubmit(false);
		uploader.setServletPath("/upload");
		UploadPanel.add(uploader);
	}
	
	@Override
	public Label getFacebook() {
		// TODO Auto-generated method stub
		return lblFacebook;
	}
	
	@Override
	public Label getPicassa() {
		// TODO Auto-generated method stub
		return lblPicassa;
	}
	
	@Override
	public HTMLPanel getHtmlPanel() {
		// TODO Auto-generated method stub
		return htmlPanel;
	}
	
	@Override
	public SingleUploader getUploader() {
		// TODO Auto-generated method stub
		return uploader;
	}
	
}
