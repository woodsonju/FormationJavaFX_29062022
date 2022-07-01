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
	 * devront �tre annot�es avec @FXML
	 * Ceci est n�cessaire au fichier fxml pour acceder aux champs et aux m�thodes priv�es.
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
	 * Objet servant de r�f�rence � notre classe principale afin de pouvoir r�cup�re la liste de nos objets
	 */
	private MainApp mainApp;

	/**
	 * Le constructeur est appel� avant la m�thode initialize()
	 */
	public PersonOverviewController() {
		super();
	}
	
	/*
	 * M�thode qui initialise  notre interface graphique avec nos donn�es m�tier. 
	 * Cette m�thode est automatiquement appel�e apr�s le chargement du fichier fxml
	 */
	@FXML
	private void initialize() {
		//Initialise la table personne avec les deux colonnes 
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
		
		//On r�initialise les detals de la personne avec des caract�res vide
		showPersonDetails(null);
		
		//On va attacher un ecouteur (Listener) � notre tableau afin qu'il puisse ecouter les chengements de 
		//selection et afficher les details de la personne (dans la partie � droite du tableau)
		//Chaque fois que l'utilisateur selectionne une personne dans la table, notre expression lambda est execut�e
		//Nous prenons la personne nouvellement s�lectionn�e pour la transmettre � la m�thode showPersonDetails
		personTable.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> this.showPersonDetails(newValue));
	}
	
	/**
	 * Methode utilis�e dans l'initialisation de l'IHM dans notre classe principale (MainApp)
	 * setMainApp : va permettre de r�cuperer la reference de MainApp afin d'appeler ses m�thodes 
	 * @param main
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		/*
		 * On lie notre liste observable au composant TableView 
		 *Ajoute des donn�es de la liste observable � la table
		 */
		this.personTable.setItems(this.mainApp.getPersonData());
	}
	
	
	/**
	 * Remplit tous les champs de texte pour afficher les details de la personne
	 * Si la personne sp�cifi�e est null, tous les champs de texte seront remplac�s avec 
	 * une chaine de caract�re vide 
	 * @param person  : la personne avec des donn�es ou null
	 */
	private void showPersonDetails(Person person) {
		if(person != null) {
			/*
			 * Remplir les labels avec les informations de l'objet person 
			 * avec la m�thode setText
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
			//Person est null, on reinitialise avec une chainde de caract�re vide 
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
		//Cr�ation d'un alerte de type WARNING
		Alert alert = new Alert(AlertType.WARNING);
		
		//On sp�cifie la fenetre propri�taire (Notre Stage) cette boite de dialogue 
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
			//Aucun element selectionn� 
			alert.setTitle("No Selection");
			alert.setHeaderText("No Person selected");
			alert.setContentText("Please select a person in the table.");
			alert.showAndWait();
		}
	}
	
	
	/**
	 * Methode appel�e quand l'utilisateur click sur le bouton New 
	 */
	@FXML
	private void handleNewPerson() {
		Person temPerson = new Person();
		boolean OkCliked = mainApp.showPersonEditDialog(temPerson);
		
		if(OkCliked) {
			mainApp.getPersonData().add(temPerson); //On met � jour la liste des personnes
		}
	}
	
	
	/**
	 * La m�thode appel�e lorsque l'utilisateur clique sur le bouton Edit 
	 * apr�s avoir selection une "Person" dans la liste des "Person"
	 */
	@FXML
	private void handleEditPerson() {
		Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
		if(selectedPerson != null) {
			boolean okClicked =  mainApp.showPersonEditDialog(selectedPerson);
			if(okClicked) {
				showPersonDetails(selectedPerson);  //On affiche la personne mis � jours
			}
		} else {
			//Aucun element selectionn� 
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No selection");
			alert.setHeaderText("No Person selected");
			alert.setContentText("Please select a person in the table");
			alert.showAndWait();
		}
	}

}
