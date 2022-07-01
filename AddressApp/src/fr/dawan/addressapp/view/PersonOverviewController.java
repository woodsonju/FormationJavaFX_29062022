package fr.dawan.addressapp.view;



import java.time.LocalDate;
import java.util.Optional;

import fr.dawan.addressapp.MainApp;
import fr.dawan.addressapp.model.Person;
import fr.dawan.addressapp.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PersonOverviewController {
	
	/*
	 * Tous les champs et toutes les mthodes auxquelles le fichier fxml aura besoin d'acceder 
	 * devront être annotées avec @FXML
	 * Ceci est nécessaire au fichier fxml pour acceder aux champs et aux méthodes privées.
	 */
	@FXML
	private TableView<Person> personTable;	
	@FXML
	private TableColumn<Person, String> firstNameColumn;
	@FXML
	private TableColumn<Person, String> lastNameColumn;
	
	@FXML
	private Label firstNameLabel;
	@FXML
	private Label lastNameLabel;
	@FXML
	private Label streetLabel;
	@FXML
	private Label postalCodeLabel;
	@FXML
	private Label cityLabel;
	@FXML
	private Label birthdayLabel;
	
	/*
	 * Objet servant de référence à notre classe principale afin de pouvoir récupére la liste de nos objets
	 */
	private MainApp mainApp;

	/**
	 * Le constructeur est appelé avant la méthode initialize()
	 */
	public PersonOverviewController() {
		super();
	}
	
	/*
	 * Méthode qui initialise  notre interface graphique avec nos données métier. 
	 * Cette méthode est automatiquement appelée après le chargement du fichier fxml
	 */
	@FXML
	private void initialize() {
		//Initialise la table personne avec les deux colonnes 
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
		
		//On réinitialise les detals de la personne avec des caractères vide
		showPersonDetails(null);
		
		//On va attacher un ecouteur (Listener) à notre tableau afin qu'il puisse ecouter les chengements de 
		//selection et afficher les details de la personne (dans la partie à droite du tableau)
		//Chaque fois que l'utilisateur selectionne une personne dans la table, notre expression lambda est executée
		//Nous prenons la personne nouvellement sélectionnée pour la transmettre à la méthode showPersonDetails
		personTable.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> this.showPersonDetails(newValue));
	}
	
	/**
	 * Methode utilisée dans l'initialisation de l'IHM dans notre classe principale (MainApp)
	 * setMainApp : va permettre de récuperer la reference de MainApp afin d'appeler ses méthodes 
	 * @param main
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		/*
		 * On lie notre liste observable au composant TableView 
		 *Ajoute des données de la liste observable à la table
		 */
		this.personTable.setItems(this.mainApp.getPersonData());
	}
	
	
	/**
	 * Remplit tous les champs de texte pour afficher les details de la personne
	 * Si la personne spécifiée est null, tous les champs de texte seront remplacés avec 
	 * une chaine de caractère vide 
	 * @param person  : la personne avec des données ou null
	 */
	private void showPersonDetails(Person person) {
		if(person != null) {
			/*
			 * Remplir les labels avec les informations de l'objet person 
			 * avec la méthode setText
			 */
			firstNameLabel.setText(person.getFirstName());
			lastNameLabel.setText(person.getLastName());
			streetLabel.setText(person.getStreet());
			postalCodeLabel.setText(String.valueOf(person.getPostalCode()));
			cityLabel.setText(person.getCity());
			//TO-DO : On a besoin d'un moyen de convertir birthday en chaine ! 
			//birthdayLabel.setText(person.getBirthday().toString());
			birthdayLabel.setText(DateUtil.format(person.getBirthday()));
		} else {
			//Person est null, on reinitialise avec une chainde de caractère vide 
			firstNameLabel.setText("");
			lastNameLabel.setText("");
			streetLabel.setText("");
			postalCodeLabel.setText("");
			cityLabel.setText("");
			birthdayLabel.setText("");
		}
	}
	
	@FXML
	private void handlerDeletePerson() {
		
		int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
		//Création d'un alerte de type WARNING
		Alert alert = new Alert(AlertType.WARNING);
		
		//On spécifie la fenetre propriétaire (Notre Stage) cette boite de dialogue 
		alert.initOwner(this.mainApp.getPrimaryStage());
		
		if(selectedIndex > -1) {
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Delete a person");
			alert.setHeaderText("Confirmation, delete a person");
			alert.setContentText("Are you sure you want to delete this person ?");
			
			Optional<ButtonType>  result =  alert.showAndWait();
			if(result.get() == ButtonType.OK) {
				//Utilisateur clique sur le bouton OK
				personTable.getItems().remove(selectedIndex);
			}
		} else {
			//Aucun element selectionné 
			alert.setTitle("No Selection");
			alert.setHeaderText("No Person selected");
			alert.setContentText("Please select a person in the table.");
			alert.showAndWait();
		}
	}
	
	
	/**
	 * Methode appelée quand l'utilisateur click sur le bouton New 
	 */
	@FXML
	private void handleNewPerson() {
		Person temPerson = new Person();
		boolean OkCliked = mainApp.showPersonEditDialog(temPerson);
		
		if(OkCliked) {
			mainApp.getPersonData().add(temPerson); //On met à jour la liste des personnes
		}
	}
	
	
	/**
	 * La méthode appelée lorsque l'utilisateur clique sur le bouton Edit 
	 * après avoir selection une "Person" dans la liste des "Person"
	 */
	@FXML
	private void handleEditPerson() {
		Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
		if(selectedPerson != null) {
			boolean okClicked =  mainApp.showPersonEditDialog(selectedPerson);
			if(okClicked) {
				showPersonDetails(selectedPerson);  //On affiche la personne mis à jours
			}
		} else {
			//Aucun element selectionné 
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No selection");
			alert.setHeaderText("No Person selected");
			alert.setContentText("Please select a person in the table");
			alert.showAndWait();
		}
	}

}
