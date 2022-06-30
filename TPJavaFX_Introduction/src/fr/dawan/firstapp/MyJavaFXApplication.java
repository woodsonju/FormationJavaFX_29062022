package fr.dawan.firstapp;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MyJavaFXApplication extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		//Titre de la fenetre 
		primaryStage.setTitle("Gestion de produits");
		
		BorderPane borderPaneRoot = new BorderPane();
		Scene scene = new Scene(borderPaneRoot, 800, 600);
		
		//Insertion de la scene au stage 
		primaryStage.setScene(scene);
		
		//Création d'un HBox dans lequel on va aligner le label, la zone de texte (TextField) et le bouton 
		HBox hBox1 = new HBox();
		//Définir la marge entre le contenu et les bors du conteneur hBox1 
		hBox1.setPadding(new Insets(10));
		//Définir l'espace entre les contenus 
		hBox1.setSpacing(10);
		//Créer le label Produit 
		Label labelNom = new Label("Produit: ");
		//Definir la marge entre le texte du Label et les bords du label 
		labelNom.setPadding(new Insets(5));
		//Créer le composant de saisie TextField 
		TextField textFieldNom = new TextField();
		//Créer le bouton 
		Button buttonAdd = new Button("Ajouter");
		//Ajouter les composat Label, TextField et Button dans HBox
		hBox1.getChildren().addAll(labelNom, textFieldNom, buttonAdd);
		//Mettre hBox1 dans le BorderPane, en Haut (Top)
		borderPaneRoot.setTop(hBox1);
		
		//Créer un VBox dans lequel on va mettre un ListView 
		VBox  vbox1 = new VBox();
		ObservableList<String> observableList = FXCollections.observableArrayList();
		ListView<String> listView1 = new ListView<String>(observableList);
		observableList.addAll("Iphone13", "Asus ROG", "Imprimante");
		vbox1.getChildren().add(listView1);
		//Metre vBox dans le BorderPane, au centre (Center)
		borderPaneRoot.setCenter(vbox1);
		
		//La gestion des évenements : 
		//Quand on clique sur le bouton Ajouter 
		//On effectue un traitement (dans la méthode handle)
//		buttonAdd.setOnAction(new EventHandler<ActionEvent>() {
//			
//			@Override
//			public void handle(ActionEvent event) {
//				
//			}
//		});
		
		buttonAdd.setOnAction(e -> {
			//Lire le contenu saisi dans la zone de texte 
			String nom = textFieldNom.getText();
			observableList.add(nom);
		});
		
		
		primaryStage.show();
		
	}

}
