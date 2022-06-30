package fr.dawan.firstapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class LayoutController {
	
	@FXML
	private TextField textFieldNom;
	
	@FXML
	private ListView<String> listView1;
	
	@FXML
	private Button button1;
	
	public void addProduct() {
		button1.getStyleClass().add("myButton");
		String nom = textFieldNom.getText();
		listView1.getItems().add(nom);
	}
	
}
