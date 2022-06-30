package fr.dawan.addressapp;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {
	
	/*
	 * Création des variables de classes afin de pouvoir y acceder partout 
	 * Ceci afin de pouvoir y positionner les éléments que nous avont fait 
	 * Le conteneur principal de notre IHM est le BorderPane
	 */
	private Stage primaryStage; 
	private BorderPane rootLayout;

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("AdressApp");
		
		//Nous allons utiliser nos fichiers FXML dans ces deux méthodes 
		initRoot();
		showPersonOverview();
	}

	/**
	 * Initialiser le RootLayout, le conteneur principal 
	 * On va charger le fichier (RootLayout.fxml)
	 */
	private void initRoot() {
		try {
			//On crée un char de FXML
			FXMLLoader loader = new FXMLLoader();
			
			//On lui spécifier le chemin vers notre fichier 
			//getClass permet d'acceder au package de l'application 
			//getRessource nous donne le fichier qui s'appelle  RootLayoutLayout.fxml
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = loader.load();
			
			//On definit une scene principal avec notre conteneur (le layout : BorderPane)
			Scene scene = new Scene(rootLayout);
			
			primaryStage.setScene(scene);
			
			primaryStage.show();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void showPersonOverview() {
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
			AnchorPane personOverview = loader.load();
			
			//Nous ajoutons personOverview à notre conteneur principal au centre 
			rootLayout.setCenter(personOverview);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}

	/**
	 * Retourne le stage principale
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}


}
