package fr.dawan.addressapp.view;

import fr.dawan.addressapp.model.Person;
import fr.dawan.addressapp.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class PersonEditDialogController {
	
	@FXML
	private TextField firstNameField;
	
	@FXML
	private TextField lastNameField;
	
	@FXML
	private TextField streetField;
	
	@FXML
	private TextField postalCodeField;
	
	@FXML
	private TextField cityField;
	
	@FXML
	private TextField birthdayField;
	
	private Stage dialogStage; 
	
	private Person person;
	
	/*
	 * Le booléen okClicked est utilisé de que l'appelant peut determiner si 
	 * l'utilisateur a cliqué sur le bouton OK ou Annuler
	 */
	private boolean okClicked;
	
	private void initialize() {
		
	}
	
	/**
	 * La méthode utilisé dans l'initialisation de l'IHM, dans notre classe principale
	 * L'attribut dialogStage sera mis à jours avec le stage definis dans la classe MainApp
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;		
	}
	
	
	/**
	 * Definit la personne à modifier dans la boite de dialogue (Stage où sera inseré la page PersonEditDialog)
	 * pour ajouter ou editer
	 * Permettra d'initialiser les champs du formulaire personEditDialog
	 * 
	 * @param person
	 */
	public void setPerson(Person person) {
		this.person = person;
		firstNameField.setText(person.getFirstName());
		lastNameField.setText(person.getLastName());
		cityField.setText(person.getCity());
		streetField.setText(person.getStreet());
		postalCodeField.setText(Integer.toString(person.getPostalCode()));
		birthdayField.setText(DateUtil.format(person.getBirthday()));
	}


	/**
	 * Méthode de contrôle de la validité des données de saisies
	 * @return  true si l'entrée est valide
	 */
	private boolean isInputValid() {
		String errorMessage = "";
		if(firstNameField.getText() == null || firstNameField.getText().length() == 0) {
			errorMessage += "No valid first name!\n";
		}
		if(lastNameField.getText() == null || lastNameField.getText().length() == 0) {
			errorMessage += "No valid last name!\n";
		}
		if(streetField.getText() ==  null || streetField.getText().length() == 0) {
			errorMessage += "No valid street!\n";
		}
		if(postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
			errorMessage += "No valid postal code!\n";
		}else {
			//Essayer de parser le code postal en un int 
			try {
				Integer.parseInt(postalCodeField.getText());
			} catch (NumberFormatException e) {
				errorMessage += "No valid postal code (must be an integer)!\n";
			}
		}
		if(cityField.getText() == null ||  cityField.getText().length() == 0) {
			errorMessage += "No valid city!\n";
		}
		if(birthdayField.getText() == null || birthdayField.getText().length() == 0) {
			errorMessage += "No valid birthday!\n";
		} else {
			if(!DateUtil.validDate(birthdayField.getText())) {
				errorMessage += "No valid birthday. Use format dd.mm.yyyy!\n";
			}
		}
		
		if(errorMessage.length() == 0) {
			return true;
		} else {
			//Affiche les messages d'erreur 
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);
			alert.showAndWait();
			
			return false;
		}
		
	}
	
	
	
	/**
	 * Methode appelée quand l'utilisateur clique sur le bouton OK
	 */
	@FXML
	private void handleOk() {
		//L'objet person récupère les données des champs de saisies 
		if(isInputValid()) {
			person.setFirstName(firstNameField.getText());
			person.setLastName(lastNameField.getText());
			person.setStreet(streetField.getText());
			person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
			person.setCity(cityField.getText());
			person.setBirthday(DateUtil.parse(birthdayField.getText()));
			
			this.okClicked = true;
			
			dialogStage.close();
		}
	}
	
	
	/**
	 * Methode quans l'utilisateur click sur le bouton Cancel
	 */
	@FXML
	private void handleCancel() {
		 this.dialogStage.close();
	}
	
	/**
	 * Returne true si l'utilsiateur click sur OK, sinon false
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	

}
