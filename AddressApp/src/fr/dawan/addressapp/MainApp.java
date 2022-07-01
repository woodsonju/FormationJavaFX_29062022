package fr.dawan.addressapp;

import java.io.IOException;

import fr.dawan.addressapp.model.Person;
import fr.dawan.addressapp.view.PersonEditDialogController;
import fr.dawan.addressapp.view.PersonOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {
	
	/*
	 * Création des variables de classes afin de pouvoir y acceder partout 
	 * Ceci afin de pouvoir y positionner les éléments que nous avont fait 
	 * Le conteneur principal de notre IHM est le BorderPane
	 */
	private Stage primaryStage; 
	private BorderPane rootLayout;
	
	/*
	 * Les données sont sous formes de liste de person observable. 
	 * Cela nous permettra de tracer les modification apportés à ses elements (personData)
	 */
	private ObservableList<Person> personData = FXCollections.observableArrayList();
	
	//Constructeur par defaut : initialise une liste de personne
	public MainApp() {
		personData.add(new Person("Edson", "Arantes do Nascimento"));
		personData.add(new Person("Diego", "Maradona"));
		personData.add(new Person("George", "Weah"));
		personData.add(new Person("Franz", "Beckenbauer"));
		personData.add(new Person("Ronaldo", "De Assis Moreira"));
		personData.add(new Person("Manoel", "Francisco dos Santos"));
		personData.add(new Person("George", "Cruyff"));
		personData.add(new Person("Jean Pierre", "Papin"));
		personData.add(new Person("Zinédine", "Zidane"));
		personData.add(new Person("Kylian", "MBappe"));
	}
	
	
	/**
	 * Retourne les données comme une liste de personne observable
	 * @return
	 */
	public ObservableList<Person> getPersonData() {
		return personData;
	}



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
			
			/*
			 * Donne au contrôleur l'accès  à l'application MainApp 
			 * On passe la reference mainApp à l'application 
			 * afin que le contrôleur puisse avoir accès aux méthode de mainApp
			 */
			PersonOverviewController controller =  loader.getController();
			
			/*
			 * Nous passons notre instance de classe (MainApp)
			 * Pour qu'il puisse récuperer notre liste observable (notre liste de personne)
			 */
			controller.setMainApp(this);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
	public boolean showPersonEditDialog(Person person) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
			AnchorPane page =  loader.load();
			
			//Création du Stage pour la  boite dialogue qui sera dependant du stage principal
			Stage dialoStage = new Stage();
			dialoStage.setTitle("Edit person");
			
			//Notre fenêtre sera Modal par rapport à notre stage principal
			dialoStage.initModality(Modality.WINDOW_MODAL);
			
			Scene scene = new Scene(page);
			dialoStage.setScene(scene);
			
			/*
			 * Donne au contrôleur l'accès à l'application MainApp 
			 */
			PersonEditDialogController controller = loader.getController();
			controller.setDialogStage(dialoStage);
			
			/*
			 * On passe à la méthode setPerson la personne à editer ou à jouter 
			 */
			controller.setPerson(person);
			
			//Afficher la boite de dialogue et attendre que l'utilisateur la ferme
			dialoStage.showAndWait();
			
			return controller.isOkClicked();
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
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
