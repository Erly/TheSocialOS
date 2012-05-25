package net.thesocialos.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AvatarUpdateEvent extends GwtEvent<AvatarUpdateEventHandler> {
	
	public static Type<AvatarUpdateEventHandler> TYPE = new Type<AvatarUpdateEventHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AvatarUpdateEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	
	@Override
	protected void dispatch(AvatarUpdateEventHandler handler) {
		handler.onAvatarUpdate(this);
		
	}
	
}
