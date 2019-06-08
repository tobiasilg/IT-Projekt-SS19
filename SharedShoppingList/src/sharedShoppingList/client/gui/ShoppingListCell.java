package sharedShoppingList.client.gui;



import com.google.gwt.cell.client.AbstractCell;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import sharedShoppingList.shared.bo.ShoppingList;

/*
 * Klasse zur Darstellung von ShoppingList-Objekte für den Navigator
 */

public class ShoppingListCell extends AbstractCell<ShoppingList> {

	@Override
	public void render(Context context, ShoppingList value, SafeHtmlBuilder sb) {
		// TODO Auto-generated method stub
		if(value == null) {
			return;
		}
		sb.appendHtmlConstant("<div>");
		sb.appendEscaped(value.getName());
		sb.appendHtmlConstant("</div>");

	}


}
