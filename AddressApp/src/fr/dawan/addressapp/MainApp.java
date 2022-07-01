package fr.dawan.addressapp;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import fr.dawan.addressapp.model.Person;
import fr.dawan.addressapp.model.PersonListWrapper;
import fr.dawan.addressapp.view.BirthdayStatisticsController;
import fr.dawan.addressapp.view.PersonEditDialogController;
import fr.dawan.addressapp.view.PersonOverviewController;
import fr.dawan.addressapp.view.RootLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
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
		
		this.primaryStage.getIcons().add(new Image("file:resources/images/address_book_icon.png"));
		
		this.primaryStage.setMinHeight(400);
		this.primaryStage.setMinWidth(740);
		
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
			
			
			/*
			 * Donne au controleur l'accès à l'application mainApp
			 * On va passer la reference de mainApp afin que le controleur 
			 * puisse acceder aux méthode de mainApp
			 */
			RootLayoutController controller =  loader.getController();
			controller.setMainApp(this);
			
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
	 * Retourne la preference du fichier person, c'est  à dire le derner fichier ouvert
	 * La preference est lue à partir du registre spécifique au systeme d'exploitation
	 * Si aucune préference est trouvé, nul est retournée
	 * @return
	 */
	public File getPersonFilePath() {
		
		/*
		 * ex ==> prefs  = /fr/dawan/addresapp
		 */
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		
		/*
		 * Si mon fichier xml est enregistré dans le dossier testJavaFX\person.xml
		 * ex : filePath = c:\\users\\admin.......\desktop\testJavaFX\person.xml
		 * 
		 */
		String filePath = prefs.get("filePath", null);
		
		if(filePath != null) {
			return new File(filePath);
		} else {
			return null;
		}
		
	}
	
	/**
	 * Definit le chemin du fichier actuellement chargé. 
	 * Le chemin est persisté dans le registre spécifique au systeme d'exploitation
	 * @param file: Le fichier ou null pour supprimer le chemin
	 */
	public void setPersonFilePath(File file) {
		
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		
		if(file != null) {
			//Ajout du chemin du fichier dans prefs
			prefs.put("filePath", file.getPath());
			//Mise à jour du titre du stage (fenetre principale) 
			//"AdressApp - " + file.getName()  = AddressApp - person.xml
			primaryStage.setTitle("AddressApp - " + file.getName());
		} else {
			prefs.remove("filePath");
			primaryStage.setTitle("AddressApp");
		}
		
	}
	
	
	/**
	 * Deserialisation  
	 * Charge les données person à partir du fichier spécifié 
	 * Les données person actuelles seront remplacées
	 * @param file
	 */
	public void loadPersonDataFromFile(File file) {
		try {
			
			//Obtient une nouvelle instance d'une classe JAXBContext
			JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			
			//Lecture du XML à partir du fichier et unmarshalling(deserialisation)
			PersonListWrapper wrapper =  (PersonListWrapper) unmarshaller.unmarshal(file);
			
			personData.clear();
			
			personData.addAll(wrapper.getPersons());
			
			//Sauvegarde le chemin du fichier dans le registre 
			setPersonFilePath(file);
			
		} catch (Exception e) {    //Attrape toutes les exceptions
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not load data");
			alert.setContentText("Could not load data form file:\n" + file.getPath());
			
			alert.showAndWait();
		}
	}
	
	/**
	 * Serialisation 
	 * Enregistre les données person actuelles dans le fichier spécifié 
	 * @param file
	 */
	public void savePersonDataToFile(File file) {
		try {
			JAXBContext  context = JAXBContext.newInstance(PersonListWrapper.class);
			Marshaller marshaller = context.createMarshaller();
			
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			//Wrapping de nos données person 
			PersonListWrapper wrapper = new PersonListWrapper();
			
			wrapper.setPersons(personData);
			
			//Marshalling et sauvegarde de l'objet vers le fichier
			marshaller.marshal(wrapper, file);
			
			//Sauvegarde le chemin fichier dans le registre
			setPersonFilePath(file);
			
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not load data");
			alert.setContentText("Could not load data form file:\n" + file.getPath());
			
			alert.showAndWait();
		}
	}
	
	public void showBirthdayStatistics() {
		try {
			//Chargez le fichier fxml et créer un nouvel stage pour la boite de dialogue
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/BirthdayStatistics.fxml"));
			AnchorPane page = loader.load();
			
			//Creation stage pour la boite de dialogue qui depedant du stage principal 
			Stage birthdayStatisticsStage = new Stage();
			birthdayStatisticsStage.setTitle("Birthday Statistics");
			
			//Notre fenêtre sera modal par rapport à notre fenêtre stage principal 
			birthdayStatisticsStage.initModality(Modality.WINDOW_MODAL);
			birthdayStatisticsStage.initOwner(primaryStage);
			
			//Scene contient le layout AnchorPane 
			Scene scene = new Scene(page);
			
			//Ajoute la scene au stage 
			birthdayStatisticsStage.setScene(scene);
			
			//On passe à la méthode setPersonData la liste des personnes avec leurs données
			BirthdayStatisticsController controller = loader.getController();
			controller.setPersonData(personData);
			
			//Afficher la boite de dialogue 
			birthdayStatisticsStage.show();
		}catch (Exception e) {
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
