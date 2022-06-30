package fr.dawan.firstapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MyJavaUsingFXML extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		//Charge le fichier "Layout.fxml"
		//getClass permet d'acceder au package de l'application 
		//getRessource nous donne le fichier qui s'appelle  Layout.fxml
		BorderPane borderPaneRoot = FXMLLoader.load(getClass().getResource("Layout.fxml"));
		Scene scene = new Scene(borderPaneRoot, 600, 400);
		
		//On attache une de style à notre scene
		scene.getStylesheets().add(getClass().getResource("myStyle.css").toString());
		
		primaryStage.setScene(scene);
		
		primaryStage.show();
		
	}

}
