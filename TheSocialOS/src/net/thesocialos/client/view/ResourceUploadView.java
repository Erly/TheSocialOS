package net.thesocialos.client.view;

import gwtupload.client.SingleUploader;

import java.util.Iterator;
import java.util.Map;

import net.thesocialos.client.CacheLayer;
import net.thesocialos.client.presenter.ResourceUploadPresenter.Display;
import net.thesocialos.shared.model.Account;
import net.thesocialos.shared.model.Facebook;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.objectify.Key;

public class ResourceUploadView extends Composite implements Display {
	
	private static ResourceUploadUiBinder uiBinder = GWT.create(ResourceUploadUiBinder.class);
	// @UiField Label lblFacebook;
	// @UiField Label lblPicassa;
	@UiField SimplePanel simplePanel;
	// @UiField SimplePanel UploadPanel;
	SingleUploader uploader = new SingleUploader();
	@UiField FormPanel imgForm;
	@UiField Hidden token;
	@UiField SubmitButton uploadButton;
	@UiField FileUpload fileUpload;
	@UiField TextBox description;
	
	interface ResourceUploadUiBinder extends UiBinder<Widget, ResourceUploadView> {
	}
	
	public ResourceUploadView() {
		initWidget(uiBinder.createAndBindUi(this));
		// uploader.setAutoSubmit(false);
		// uploader.setServletPath("/upload");
		// UploadPanel.add(uploader);
		Facebook facebookAccount = getFacebookAccount();
		if (null != facebookAccount) token.setValue(facebookAccount.getAuthToken());
		imgForm.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				
			}
		});
		
	}
	
	/*
	 * @Override public Label getFacebook() { // TODO Auto-generated method stub return lblFacebook; }
	 * @Override public Label getPicasa() { // TODO Auto-generated method stub return lblPicassa; }
	 */
	@Override
	public SimplePanel getHtmlPanel() {
		// TODO Auto-generated method stub
		return simplePanel;
	}
	
	@Override
	public SingleUploader getUploader() {
		// TODO Auto-generated method stub
		return uploader;
	}
	
	private Facebook getFacebookAccount() {
		// Map<Key<Account>, Account> accounts =
		Map<Key<Account>, Account> accounts = CacheLayer.UserCalls.getAccounts();
		Iterator<Account> it = accounts.values().iterator();
		while (it.hasNext()) {
			Account account = it.next();
			if (account instanceof Facebook) return (Facebook) account;
		}
		return null;
		
	}
	
}
