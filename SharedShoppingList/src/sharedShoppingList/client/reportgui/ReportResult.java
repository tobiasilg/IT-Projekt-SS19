package sharedShoppingList.client.reportgui;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;


public class ReportResult extends VerticalPanel{

public void append(String text){
	
	/**
	 * Hängt den erstellten HTML-Report in das GUI-Panel ein
	 * */
	
	HTML content = new HTML(text);
	
	this.add(content);
	
	}
} 