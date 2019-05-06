package sharedShoppingList.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.ListBox;

/**
 * Formular für das Anlegen eines neuen Artikels im Datenstamm
 * @author moritzhampe
 *
 */

public class ArticleForm extends VerticalPanel {
	
	Label articleNameLabel = new Label("Name des Artikels");
	Label articleUnitLabel = new Label("Einheit in");
	Label articleAmountLabel = new Label("Menge");
	TextBox articelNameTextBox = new TextBox();
	TextBox articleAmountTextBox = new TextBox();
	ListBox articleUnitlistBox = new ListBox();
    Button addArticleButton = new Button("Artikel hinzufügen");
	Button deleteArticleButton = new Button("Artikel löschen");
	Button saveArticleButton = new Button("Artikel speichern");
	

}
