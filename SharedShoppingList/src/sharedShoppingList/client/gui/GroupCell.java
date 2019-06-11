package sharedShoppingList.client.gui;


import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import sharedShoppingList.shared.bo.Group;

/*
 * Klasse zur Darstellung von Gruppen-Objekte für den Navigator
 */

public class GroupCell extends AbstractCell<Group> {

	@Override
	public void render(Context context, Group value, SafeHtmlBuilder sb) {
		// TODO Auto-generated method stub
		if(value == null) {
			return;
		}
		sb.appendHtmlConstant("<div>");
		sb.appendEscaped(value.getName());
		sb.appendHtmlConstant("</div>");
	}

	

}
