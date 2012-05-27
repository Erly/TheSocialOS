package net.thesocialos.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ShareHistoryChangEvent extends GwtEvent<ShareHistoryChngeEventHandler> {
	
	public static Type<ShareHistoryChngeEventHandler> TYPE = new Type<ShareHistoryChngeEventHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ShareHistoryChngeEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	
	@Override
	protected void dispatch(ShareHistoryChngeEventHandler handler) {
		handler.onHistoryChange(this);
	}
	
}
