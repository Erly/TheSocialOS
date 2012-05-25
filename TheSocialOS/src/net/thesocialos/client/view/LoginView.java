package net.thesocialos.client.view;

import net.thesocialos.client.presenter.LoginPresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.TableCellElement;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class LoginView extends Composite implements Display {
	
	interface LoginViewUiBinder extends UiBinder<Widget, LoginView> {
	}
	
	private static LoginViewUiBinder uiBinder = GWT.create(LoginViewUiBinder.class);
	
	@UiField HTMLPanel html;
	@UiField ImageElement background;
	@UiField InputElement email;
	@UiField InputElement password;
	@UiField InputElement loginButton;
	@UiField DivElement helpers;
	@UiField DivElement footer;
	@UiField TableCellElement flags;
	Anchor spainFlag;
	Anchor usaFlag;
	
	public LoginView() {
		initWidget(uiBinder.createAndBindUi(this));
		/*
		 * lblPass.setText(TheSocialOS.getConstants().password());
		 * keepLogged.setText(TheSocialOS.getConstants().keepLogged());
		 * forgot.setText(TheSocialOS.getConstants().forgotPassword());
		 * register.setText(TheSocialOS.getConstants().registerNow());
		 * loginButton.setText(TheSocialOS.getConstants().login()); lblIncorrect.setVisible(false);
		 */
		
		Window.addResizeHandler(new ResizeHandler() {
			
			@Override
			public void onResize(ResizeEvent event) {
				repositionElements();
			}
		});
		
		repositionElements();
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				repositionElements();
			}
		};
		timer.schedule(10);
		timer.schedule(50);
		timer.schedule(100);
		timer.schedule(250);
		
		spainFlag = new Anchor(new SafeHtmlBuilder().appendHtmlConstant(
				"<image class='flags spain' src='http://www.crwflags.com/fotw/misc/wes.gif'/>").toSafeHtml());
		usaFlag = new Anchor(new SafeHtmlBuilder().appendHtmlConstant(
				"<image class='flags spain' src='http://www.crwflags.com/fotw/misc/wus.gif'/>").toSafeHtml());
		html.add(spainFlag, flags);
		html.add(usaFlag, flags);
	}
	
	protected void repositionElements() {
		email.getStyle().setTop(background.getHeight() - 100, Unit.PX);
		password.getStyle().setTop(background.getHeight() - 100, Unit.PX);
		loginButton.getStyle().setTop(background.getHeight() - 100, Unit.PX);
		helpers.getStyle().setTop(background.getHeight() - 50, Unit.PX);
		footer.getStyle().setTop(background.getHeight() + 2, Unit.PX);
	}
	
	@Override
	public String getEmail() {
		return email.getValue();
	}
	
	/*
	 * @Override public boolean getKeepLoged() { return keepLogged.getValue(); }
	 */
	
	@Override
	public InputElement getLoginButton() {
		return loginButton;
	}
	
	@Override
	public String getPassword() {
		return password.getValue();
	}
	
	@Override
	public boolean getKeepLoged() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public Anchor getSpainFlag() {
		return spainFlag;
	}
	
	@Override
	public Anchor getUsaFlag() {
		return usaFlag;
	}
}
