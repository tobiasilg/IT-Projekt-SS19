package sharedShoppingList.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;

import sharedShoppingList.client.ClientsideSettings;
import sharedShoppingList.client.SharedShoppingListEditorEntry.CurrentUser;
import sharedShoppingList.shared.EinkaufslistenverwaltungAsync;
import sharedShoppingList.shared.bo.User;

public class ViewGroupMembers extends FlowPanel {
	
	private EinkaufslistenverwaltungAsync elv = ClientsideSettings.getEinkaufslistenverwaltung();
	private User user = CurrentUser.getUser();
	private User newUser = null;
	
	private TextBox addUserTextBox = new TextBox();
	private Button saveMemberButton = new Button("Speichern");
	
	private FlexTable memberFlexTable = new FlexTable();

	public ViewGroupMembers() {
		
		memberFlexTable.setText(0, 0, "Nickname");
		memberFlexTable.setText(0, 1, "Löschen");
		
		saveMemberButton.addClickHandler(new AddMemberClickHandler());

	}
	
	public void onLoad() {
		this.add(memberFlexTable);
		this.add(addUserTextBox);
		this.add(saveMemberButton);
	}
	
	private class AddMemberClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			
	//		elv.getUserByUserName(addUserTextBox.getValue(), new GetUserCallback());
			
		}
		
	}
	
	private class GetUserCallback implements AsyncCallback<User> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(User result) {
			newUser = result;
			
		}
		
	}
	
	

}
