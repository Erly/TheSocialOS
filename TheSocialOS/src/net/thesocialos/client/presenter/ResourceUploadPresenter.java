package net.thesocialos.client.presenter;

import gwtupload.client.SingleUploader;
import net.thesocialos.client.app.AppConstants;
import net.thesocialos.client.desktop.DesktopUnit;
import net.thesocialos.client.desktop.IsTypeInfo;
import net.thesocialos.client.view.ResourceUploadView;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class ResourceUploadPresenter extends DesktopUnit implements IsTypeInfo {
	Display display;
	boolean facebook = false;
	boolean picasa = false;
	
	public ResourceUploadPresenter() {
		super(AppConstants.RESOURCEUPLOADER, "Resource Uploader", null, TypeUnit.INFO, false);
		display = new ResourceUploadView();
		bind();
	}
	
	public interface Display {
		Widget asWidget();
		
		// Label getFacebook();
		
		// Label getPicasa();
		
		SimplePanel getHtmlPanel();
		
		SingleUploader getUploader();
	}
	
	private void bind() {
		/*
		 * display.getFacebook().addClickHandler(new ClickHandler() {
		 * @Override public void onClick(ClickEvent event) { facebook = !facebook;
		 * display.getFacebook().setStyleName("imageUpload_media_selected", facebook);
		 * display.getFacebook().setStyleName("imageUpload_media_unselected", !facebook); } });
		 * display.getPicasa().addClickHandler(new ClickHandler() {
		 * @Override public void onClick(ClickEvent event) { picasa = !picasa;
		 * display.getPicasa().setStyleName("imageUpload_media_selected", picasa);
		 * display.getPicasa().setStyleName("imageUpload_media_unselected", !picasa); } });
		 * display.getUploader().getForm().addSubmitHandler(new SubmitHandler() {
		 * @Override public void onSubmit(SubmitEvent event) { String value = ""; if (picasa) value = value + "picasa;";
		 * if (facebook) value = value + "facebook;"; display.getUploader().getForm().add(new Hidden("media", value)); }
		 * }); display.getUploader().getForm().addSubmitCompleteHandler(new SubmitCompleteHandler() {
		 * @Override public void onSubmitComplete(SubmitCompleteEvent event) { } });
		 */
	}
	
	@Override
	public void close(final AbsolutePanel absolutePanel) {
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				absolutePanel.remove(display.asWidget());
			}
			
		};
		display.getHtmlPanel().setStyleName("imageUpload_close", true);
		timer.schedule(500);
		
	}
	
	@Override
	public int getZposition() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void open(AbsolutePanel absolutePanel) {
		if (absolutePanel.getWidgetIndex(display.asWidget()) == -1) {
			absolutePanel.add(display.asWidget(), x, y);
			display.asWidget().setVisible(true);
		} else
			absolutePanel.setWidgetPosition(display.asWidget(), x, 0);
		display.getHtmlPanel().setStyleName("imageUpload_open", true);
		display.getHtmlPanel().setStyleName("imageUpload_close", false);
		
	}
	
	@Override
	public void toZPosition(int position) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getWidth() {
		
		return display.asWidget().getOffsetWidth();
		
	}
	
	@Override
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public int getHeight() {
		
		return display.asWidget().getOffsetHeight();
	}
	
	@Override
	public int getAbsoluteLeft() {
		
		return display.asWidget().getAbsoluteLeft();
	}
	
	@Override
	public int getAbsoluteTop() {
		
		return display.asWidget().getAbsoluteTop();
	}
	
	@Override
	public void toBack() {
		
	}
	
	@Override
	public void toFront() {
		
	}
	
}
