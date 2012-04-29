package com.google.gwt.user.client.ui;

/*
 * Copyright 2009 Google Inc. Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */

import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.desktop.window.WindowDisplay;
import net.thesocialos.client.desktop.window.WindowEndDragEvent;
import net.thesocialos.client.desktop.window.WindowEvent;
import net.thesocialos.client.desktop.window.WindowEventHandler;
import net.thesocialos.client.desktop.window.WindowOnTopEvent;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.HasAllMouseHandlers;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.client.HasSafeHtml;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.event.shared.SimpleEventBus;

/**
 * A form of popup that has a caption area at the top and can be dragged by the user. Unlike a PopupPanel, calls to
 * {@link #setWidth(String)} and {@link #setHeight(String)} will set the width and height of the dialog box itself, even
 * if a widget has not been added as yet.
 * <p>
 * <img class='gallery' src='doc-files/DialogBox.png'/>
 * </p>
 * <h3>CSS Style Rules</h3>
 * 
 * <ul>
 * <li>.gwt-DialogBox { the outside of the dialog }</li>
 * <li>.gwt-DialogBox .Caption { the caption }</li>
 * <li>.gwt-DialogBox .dialogContent { the wrapper around the content }</li>
 * <li>.gwt-DialogBox .dialogTopLeft { the top left cell }</li>
 * <li>.gwt-DialogBox .dialogTopLeftInner { the inner element of the cell }</li>
 * <li>.gwt-DialogBox .dialogTopCenter { the top center cell, where the caption is located }</li>
 * <li>.gwt-DialogBox .dialogTopCenterInner { the inner element of the cell }</li>
 * <li>.gwt-DialogBox .dialogTopRight { the top right cell }</li>
 * <li>.gwt-DialogBox .dialogTopRightInner { the inner element of the cell }</li>
 * <li>.gwt-DialogBox .dialogMiddleLeft { the middle left cell }</li>
 * <li>.gwt-DialogBox .dialogMiddleLeftInner { the inner element of the cell }</li>
 * <li>.gwt-DialogBox .dialogMiddleCenter { the middle center cell, where the content is located }</li>
 * <li>.gwt-DialogBox .dialogMiddleCenterInner { the inner element of the cell }</li>
 * <li>.gwt-DialogBox .dialogMiddleRight { the middle right cell }</li>
 * <li>.gwt-DialogBox .dialogMiddleRightInner { the inner element of the cell }</li>
 * <li>.gwt-DialogBox .dialogBottomLeft { the bottom left cell }</li>
 * <li>.gwt-DialogBox .dialogBottomLeftInner { the inner element of the cell }</li>
 * <li>.gwt-DialogBox .dialogBottomCenter { the bottom center cell }</li>
 * <li>.gwt-DialogBox .dialogBottomCenterInner { the inner element of the cell }</li>
 * <li>.gwt-DialogBox .dialogBottomRight { the bottom right cell }</li>
 * <li>.gwt-DialogBox .dialogBottomRightInner { the inner element of the cell }</li>
 * </ul>
 * <p>
 * <h3>Example</h3>
 * {@example com.google.gwt.examples.DialogBoxExample}
 * </p>
 * 
 * <h3>Use in UiBinder Templates</h3>
 * <p>
 * DialogBox elements in {@link com.google.gwt.uibinder.client.UiBinder UiBinder} templates can have one widget child
 * and one &lt;g:caption> child. (Note the lower case "c", meant to signal that the caption is not a runtime object, and
 * so cannot have a <code>ui:field</code> attribute.) The body of the caption can be html.
 * 
 * <p>
 * 
 * For example:
 * 
 * <pre>
 * &lt;g:DialogBox autoHide="true" modal="true">
 *   &lt;g:caption>&lt;b>Caption text&lt;/b>&lt;/g:caption>
 *   &lt;g:HTMLPanel>
 *     Body text
 *     &lt;g:Button ui:field='cancelButton'>Cancel&lt;/g:Button>
 *     &lt;g:Button ui:field='okButton'>Okay&lt;/g:Button>
 *   &lt;/g:HTMLPanel>
 * &lt;/g:DialogBox>
 * </pre>
 * 
 * You may also create your own header caption. The caption must implement {@link Caption}.
 * 
 * <p>
 * 
 * For example:
 * 
 * <p>
 * 
 * <pre>
 * &lt;g:DialogBox autoHide="true" modal="true">
 *   &lt;-- foo is your prefix and Bar is a class that implements {@link Caption}-->
 *   &lt;g:customCaption>&lt;foo:Bar/>&lt;/g:customCaption>
 *   &lt;g:HTMLPanel>
 *     Body text
 *     &lt;g:Button ui:field='cancelButton'>Cancel&lt;/g:Button>
 *     &lt;g:Button ui:field='okButton'>Okay&lt;/g:Button>
 *   &lt;/g:HTMLPanel>
 * &lt;/g:DialogBox>
 * </pre>
 * 
 */
public class WindowPanelLayout extends DecoratedPopupPanel implements HasHTML, HasSafeHtml, WindowDisplay {
	
	/**
	 * Set of characteristic interfaces supported by the {@link DialogBox} caption.
	 * 
	 */
	public interface Caption extends HasAllMouseHandlers, HasHTML, HasSafeHtml, IsWidget {
		public void addWindowEventBus(SimpleEventBus windowEventBus);
		
		int getHeight();
		
	}
	
	/**
	 * Default implementation of Caption. This will be created as the header if there isn't a header specified.
	 */
	public static class CaptionImpl extends HTML implements Caption {
		
		public CaptionImpl() {
			super();
			setStyleName("Caption");
		}
		
		@Override
		public void addWindowEventBus(SimpleEventBus windowEventBus) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public int getHeight() {
			// TODO Auto-generated method stub
			return 0;
		}
	}
	
	/**
	 * Set of characteristic interfaces supported by the {@link DialogBox} caption.
	 * 
	 */
	public interface Footer extends HasAllMouseHandlers, HasHTML, HasSafeHtml, IsWidget {
		int getHeight();
	}
	
	/**
	 * Default implementation of Caption. This will be created as the header if there isn't a header specified.
	 */
	public static class FooterImpl extends HTML implements Footer {
		
		public FooterImpl() {
			super();
			setStyleName("Footer");
		}
		
		@Override
		public int getHeight() {
			// TODO Auto-generated method stub
			return 0;
		}
	}
	
	/**
	 * The default style name.
	 */
	private static final String DEFAULT_STYLENAME = "sos-Window";
	
	private boolean isMaximized = false;
	private Caption caption;
	private Footer footer;
	private boolean dragging;
	private boolean resizing;
	private int dragStartX, dragStartY;
	private int windowWidth;
	private int clientLeft;
	private int clientTop;
	SimplePanel panel = new SimplePanel();
	
	private HandlerRegistration resizeHandlerRegistration;
	private HandlerRegistration NativeEventHandlerRegistration;
	
	private SimpleEventBus windowEventBus = new SimpleEventBus();
	
	/**
	 * Creates an empty dialog box. It should not be shown until its child widget has been added using
	 * {@link #add(Widget)}.
	 */
	public WindowPanelLayout() {
		this(false);
	}
	
	/**
	 * Creates an empty dialog box specifying its "auto-hide" property. It should not be shown until its child widget
	 * has been added using {@link #add(Widget)}.
	 * 
	 * @param autoHide
	 *            <code>true</code> if the dialog should be automatically hidden when the user clicks outside of it
	 */
	public WindowPanelLayout(boolean autoHide) {
		this(autoHide, true);
	}
	
	/**
	 * Creates an empty dialog box specifying its "auto-hide" and "modal" properties. It should not be shown until its
	 * child widget has been added using {@link #add(Widget)}.
	 * 
	 * @param autoHide
	 *            <code>true</code> if the dialog should be automatically hidden when the user clicks outside of it
	 * @param modal
	 *            <code>true</code> if keyboard and mouse events for widgets not contained by the dialog should be
	 *            ignored
	 */
	public WindowPanelLayout(boolean autoHide, boolean modal) {
		this(autoHide, modal, new CaptionImpl(), new FooterImpl());
	}
	
	/**
	 * 
	 * Creates an empty dialog box specifying its "auto-hide", "modal" properties and an implementation a custom
	 * {@link Caption}. It should not be shown until its child widget has been added using {@link #add(Widget)}.
	 * 
	 * @param autoHide
	 *            <code>true</code> if the dialog should be automatically hidden when the user clicks outside of it
	 * @param modal
	 *            <code>true</code> if keyboard and mouse events for widgets not contained by the dialog should be
	 *            ignored
	 * @param captionWidget
	 *            the widget that is the DialogBox's header.
	 */
	public WindowPanelLayout(boolean autoHide, boolean modal, Caption captionWidget, Footer footerWidget) {
		super(autoHide, modal, "dialog");
		
		assert captionWidget != null : "The caption must not be null";
		assert footerWidget != null : "The footer must not be null";
		captionWidget.asWidget().removeFromParent();
		footerWidget.asWidget().removeFromParent();
		caption = captionWidget;
		footer = footerWidget;
		panel.setStyleName("invisiblePanel");
		// Add the caption to the top row of the decorator panel. We need to
		// logically adopt the caption so we can catch mouse events.
		Element td = getCellElement(0, 1);
		DOM.appendChild(td, caption.asWidget().getElement());
		Element tf = getCellElement(2, 1);
		
		DOM.appendChild(tf, footer.asWidget().getElement());
		// adopt(footer.asWidget());
		// adopt(caption.asWidget());
		
		// Set the style name
		setStyleName(DEFAULT_STYLENAME);
		
		windowWidth = Window.getClientWidth();
		clientLeft = Document.get().getBodyOffsetLeft();
		clientTop = Document.get().getBodyOffsetTop();
		
		caption.addWindowEventBus(windowEventBus);
		caption.addMouseDownHandler(new MouseDownHandler() {
			
			@Override
			public void onMouseDown(MouseDownEvent event) {
				windowEventBus.fireEvent(new WindowOnTopEvent());
				beginDragging(event);
				
			}
		});
		footer.addMouseDownHandler(new MouseDownHandler() {
			
			@Override
			public void onMouseDown(MouseDownEvent event) {
				if (!isMaximized) {
					windowEventBus.fireEvent(new WindowOnTopEvent());
					beginResizing(event);
				}
				
			}
		});
		
		// addDomHandler(mouseHandler, MouseMoveEvent.getType());
		
	}
	
	/**
	 * Creates an empty dialog box specifying its {@link Caption}. It should not be shown until its child widget has
	 * been added using {@link #add(Widget)}.
	 * 
	 * @param captionWidget
	 *            the widget that is the DialogBox's header.
	 */
	public WindowPanelLayout(Caption captionWidget, Footer footerWidget) {
		this(false, true, captionWidget, footerWidget);
	}
	
	@Override
	public com.google.web.bindery.event.shared.HandlerRegistration addWindowEvents(WindowEventHandler handler) {
		// TODO Auto-generated method stub
		return windowEventBus.addHandler(WindowEvent.TYPE, handler);
	}
	
	protected void beginDragging(MouseDownEvent event) {
		TheSocialOS.get().getDesktop().add(panel);
		if (DOM.getCaptureElement() == null) {
			/*
			 * Need to check to make sure that we aren't already capturing an element otherwise events will not fire as
			 * expected. If this check isn't here, any class which extends custom button will not fire its click event
			 * for example.
			 */
			dragging = true;
			DOM.setCapture(getElement());
			dragStartX = event.getX();
			dragStartY = event.getY();
			moveNativeHandler();
		}
	}
	
	protected void beginResizing(MouseDownEvent event) {
		TheSocialOS.get().getDesktop().add(panel);
		if (DOM.getCaptureElement() == null) {
			/*
			 * Need to check to make sure that we aren't already capturing an element otherwise events will not fire as
			 * expected. If this check isn't here, any class which extends custom button will not fire its click event
			 * for example.
			 */
			resizing = true;
			DOM.setCapture(getElement());
			dragStartX = event.getX();
			dragStartY = event.getY();
			moveNativeHandler();
		}
	}
	
	/**
	 * Check the window position into the navigation window If the window excess the limits, this method will resize the
	 * windows into a limits
	 */
	private void checkSpace() {
		
		if (getAbsoluteLeft() < 0) setPopupPosition(0, getAbsoluteTop());
		else if (getAbsoluteLeft() + getOffsetWidth() > Window.getClientWidth())
			setPopupPosition(Window.getClientWidth() - getOffsetWidth(), getAbsoluteTop());
		if (getAbsoluteTop() + getOffsetHeight() > Window.getClientHeight()) setPopupPosition(getAbsoluteLeft(),
				Window.getClientHeight() - getOffsetHeight());
		else if (getAbsoluteTop() < 30) setPopupPosition(getAbsoluteLeft(), 30);
	}
	
	/**
	 * Called on mouse move in the caption area, continues dragging if it was started by {@link #beginDragging}.
	 * 
	 * @see #beginDragging
	 * @see #endDragging
	 * @param event
	 *            the mouse move event that continues dragging
	 */
	protected void continueDragging(int x, int y) {
		// onMouseMove(caption.asWidget(), event.getX(), event.getY());
		if (dragging) {
			
			int absX = x; // + getAbsoluteLeft();
			
			int absY = y;// + getAbsoluteTop();
			
			// if the mouse is off the screen to the left, right, or top, don't
			// move the dialog box. This would let users lose dialog boxes, which
			// would be bad for modal popups.
			if (absX < clientLeft || absX >= windowWidth || absY < clientTop) return;
			setPopupPosition(absX - dragStartX, absY - dragStartY);
			checkSpace();
		}
	}
	
	protected void continueResizing(int x, int y) {
		
		if (resizing) {
			// System.out.println(x + " " + y);
			int absX = x; // + getAbsoluteLeft();
			
			int absY = y;// + getAbsoluteTop();
			
			// if the mouse is off the screen to the left, right, or top, don't
			// move the dialog box. This would let users lose dialog boxes, which
			// would be bad for modal popups.
			if (x >= Window.getClientWidth()) {
				setWidth(Window.getClientWidth() - getAbsoluteLeft() + "px");
				getWidget().setWidth(Window.getClientWidth() - getAbsoluteLeft() - 7 + "px");
				return;
			}
			if (y >= Window.getClientHeight()) {
				setHeight(Window.getClientHeight() - getAbsoluteTop() + "px");
				getWidget().setHeight(
						Window.getClientHeight() - getAbsoluteTop() - caption.getHeight() - footer.getHeight() - 7
								+ "px");
				return;
			}
			// if (absX < clientLeft || absX >= windowWidth || absY < clientTop) {
			// return;
			// }
			
			if ((x - getAbsoluteLeft()) < 0) return;
			if ((y - getAbsoluteTop() - caption.getHeight() - footer.getHeight()) < 0) return;
			
			setWidth((x - getAbsoluteLeft()) + "px");
			getWidget().setWidth((x - getAbsoluteLeft()) + "px");
			setHeight((y - getAbsoluteTop()) + "px");
			getWidget().setHeight((y - getAbsoluteTop() - caption.getHeight() - footer.getHeight()) + "px");
			
			// checkResizeSpace();
		}
	}
	
	/**
	 * Enables or disables text selection for the element. A circular reference will be created when disabling text
	 * selection. Disabling should e cleared when the element is detached. See the <code>Component</code> source for an
	 * example.
	 * 
	 * @param elem
	 *            the element
	 * @param disable
	 *            <code>true</code> to disable
	 */
	
	@Override
	protected void doAttachChildren() {
		try {
			super.doAttachChildren();
		} finally {
			// See comment in doDetachChildren for an explanation of this call
			caption.asWidget().onAttach();
			footer.asWidget().onAttach();
		}
	}
	
	@Override
	protected void doDetachChildren() {
		try {
			super.doDetachChildren();
		} finally {
			/*
			 * We need to detach the caption specifically because it is not part of the iterator of Widgets that the
			 * {@link SimplePanel} super class returns. This is similar to a {@link ComplexPanel}, but we do not want to
			 * expose the caption widget, as its just an internal implementation.
			 */
			caption.asWidget().onDetach();
			footer.asWidget().onDetach();
		}
	}
	
	/**
	 * Called on mouse up in the caption area, ends dragging by ending event capture.
	 * 
	 * @param event
	 *            the mouse up event that ended dragging
	 * 
	 * @see DOM#releaseCapture
	 * @see #beginDragging
	 * @see #endDragging
	 */
	protected void endDragging() {
		TheSocialOS.get().getDesktop().remove(panel);
		windowEventBus.fireEvent(new WindowEndDragEvent());
		dragging = false;
		NativeEventHandlerRegistration.removeHandler();
		
		DOM.releaseCapture(getElement());
	}
	
	protected void endResizing() {
		TheSocialOS.get().getDesktop().remove(panel);
		// windowEventBus.fireEvent(new WindowEndDragEvent());
		resizing = false;
		NativeEventHandlerRegistration.removeHandler();
		
		DOM.releaseCapture(getElement());
	}
	
	/**
	 * Provides access to the dialog's caption.
	 * 
	 * @return the logical caption for this dialog box
	 */
	public Caption getCaption() {
		return caption;
	}
	
	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return getOffsetHeight();
	}
	
	@Override
	public String getHTML() {
		return caption.getHTML();
	}
	
	@Override
	public String getText() {
		return caption.getText();
	}
	
	@Override
	public int getwidth() {
		// TODO Auto-generated method stub
		return getOffsetWidth();
	}
	
	@Override
	public WindowPanelLayout getWindow() {
		// TODO Auto-generated method stub
		return this;
	}
	
	@Override
	public int getXposition() {
		// TODO Auto-generated method stub
		return getAbsoluteLeft();
	}
	
	@Override
	public int getYPosition() {
		// TODO Auto-generated method stub
		return getAbsoluteTop();
	}
	
	@Override
	public void hide() {
		if (resizeHandlerRegistration != null) {
			resizeHandlerRegistration.removeHandler();
			resizeHandlerRegistration = null;
		}
		super.hide();
	}
	
	private boolean isCaptionEvent(NativeEvent event) {
		EventTarget target = event.getEventTarget();
		if (com.google.gwt.dom.client.Element.is(target))
			return getCellElement(0, 1).getParentElement().isOrHasChild(com.google.gwt.dom.client.Element.as(target));
		return false;
	}
	
	/**
	 * Called on mouse down in the caption area, begins the dragging loop by turning on event capture.
	 * 
	 * @see DOM#setCapture
	 * @see #continueDragging
	 * @param event
	 *            the mouse down event that triggered dragging
	 */
	
	private void moveNativeHandler() {
		
		NativeEventHandlerRegistration = Event.addNativePreviewHandler(new NativePreviewHandler() {
			
			@Override
			public void onPreviewNativeEvent(NativePreviewEvent event) {
				// TODO Auto-generated method stub
				if (Event.ONMOUSEMOVE == event.getTypeInt()) {
					if (dragging) continueDragging(event.getNativeEvent().getClientX(), event.getNativeEvent()
							.getClientY());
					else
						continueResizing(event.getNativeEvent().getClientX(), event.getNativeEvent().getClientY());
					
				} else if (Event.ONMOUSEUP == event.getTypeInt()) if (dragging) endDragging();
				else
					endResizing();
				event.cancel();
			}
		});
	}
	
	@Override
	public void onBrowserEvent(Event event) {
		// If we're not yet dragging, only trigger mouse events if the event occurs
		// in the caption wrapper
		
		switch (event.getTypeInt()) {
		case Event.ONMOUSEDOWN:
		case Event.ONMOUSEUP:
		case Event.ONMOUSEMOVE:
		case Event.ONMOUSEOVER:
		case Event.ONMOUSEOUT:
			
			if (!dragging && !isCaptionEvent(event)) return;
		}
		
		super.onBrowserEvent(event);
	}
	
	/**
	 * <b>Affected Elements:</b>
	 * <ul>
	 * <li>-caption = text at the top of the {@link DialogBox}.</li>
	 * <li>-content = the container around the content.</li>
	 * </ul>
	 * 
	 * @see UIObject#onEnsureDebugId(String)
	 */
	@Override
	protected void onEnsureDebugId(String baseID) {
		super.onEnsureDebugId(baseID);
		caption.asWidget().ensureDebugId(baseID + "-caption");
		ensureDebugId(getCellElement(1, 1), baseID, "content");
	}
	
	@Override
	protected void onPreviewNativeEvent(NativePreviewEvent event) {
		// We need to preventDefault() on mouseDown events (outside of the
		// DialogBox content) to keep text from being selected when it
		// is dragged.
		
		NativeEvent nativeEvent = event.getNativeEvent();
		
		if (!event.isCanceled() && (event.getTypeInt() == Event.ONMOUSEDOWN) && isCaptionEvent(nativeEvent))
			nativeEvent.preventDefault();
		
		super.onPreviewNativeEvent(event);
	}
	
	/**
	 * Sets the html string inside the caption by calling its {@link #setHTML(SafeHtml)} method.
	 * 
	 * Use {@link #setWidget(Widget)} to set the contents inside the {@link DialogBox}.
	 * 
	 * @param html
	 *            the object's new HTML
	 */
	@Override
	public void setHTML(SafeHtml html) {
		caption.setHTML(html);
	}
	
	/**
	 * Sets the html string inside the caption by calling its {@link #setHTML(SafeHtml)} method. Only known safe HTML
	 * should be inserted in here.
	 * 
	 * Use {@link #setWidget(Widget)} to set the contents inside the {@link DialogBox}.
	 * 
	 * @param html
	 *            the object's new HTML
	 */
	@Override
	public void setHTML(String html) {
		caption.setHTML(SafeHtmlUtils.fromTrustedString(html));
	}
	
	@Override
	public void setMaximized(Boolean maximized) {
		isMaximized = maximized;
		
	}
	
	@Override
	public void setMinimized(Boolean minimized) {
		if (minimized) hide();
		else
			show();
		
	}
	
	@Override
	public void setPosition(int x, int y) {
		
		setPopupPosition(x, y);
		
	}
	
	@Override
	public void setSize(int width, int height) {
		setWidth(width + "px");
		getWidget().setWidth(width + "px");
		setHeight(height + "px");
		getWidget().setHeight(height - caption.getHeight() - footer.getHeight() + "px");
		checkSpace();
		
	}
	
	/**
	 * Sets the text inside the caption by calling its {@link #setText(String)} method.
	 * 
	 * Use {@link #setWidget(Widget)} to set the contents inside the {@link DialogBox}.
	 * 
	 * @param text
	 *            the object's new text
	 */
	@Override
	public void setText(String text) {
		caption.setText(text);
	}
	
	@Override
	public void setWindowTitle(String text) {
		caption.setText(text);
		
	}
	
	@Override
	public void show() {
		if (resizeHandlerRegistration == null) resizeHandlerRegistration = Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				windowWidth = event.getWidth();
			}
		});
		
		super.show();
	}
	
	@Override
	public void toback() {
		System.out.println("to back");
		getElement().getStyle().setZIndex(0);
	}
	
	@Override
	public void toFront() {
		System.out.println("to front");
		getElement().getStyle().setZIndex(100);
		
	}
	
}
