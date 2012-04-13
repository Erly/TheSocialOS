package net.thesocialos.client.view;

import net.thesocialos.client.presenter.NotificationsPresenter.Display;
import net.thesocialos.shared.model.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class NotificationsView extends Composite implements Display{

	private static PetitionsUiBinder uiBinder = GWT
			.create(PetitionsUiBinder.class);
	@UiField(provided=true) CellList<User> cellListFriends = new CellList<User>(new AbstractCell<User>(){
		@Override
		public void render(Context context, User value, SafeHtmlBuilder sb) {
			// TODO
		}
	});
	@UiField LabelText labelTextFriends;
	@UiField LabelText labelTextGroups;

	interface PetitionsUiBinder extends UiBinder<Widget, NotificationsView> {
	}

	public NotificationsView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public LabelText getContactsLabelText() {
		// TODO Auto-generated method stub
		return labelTextFriends;
	}

	@Override
	public LabelText getGroupsLabelText() {
		// TODO Auto-generated method stub
		return labelTextGroups;
	}

	@Override
	public CellList<User> getContactsCellList() {
		// TODO Auto-generated method stub
		return cellListFriends;
	}

}
