package fr.dawan.addressapp.view;

import java.io.File;
import java.util.Optional;

import fr.dawan.addressapp.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;

/**
 * Le controleur pour le RootLayout.fxml 
 * C'est le RootLayout de base de  l'application contenant une barre de menus...
 * @author Admin stagiaire
 *
 */
public class RootLayoutController {

	//Reference vers l'application principal
	private MainApp mainApp;

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	} 
	
	/**
	 * Crée un carnet d'adresse vide
	 */
	@FXML
	private void handleNew() {
		mainApp.getPersonData().clear();
		mainApp.setPersonFilePath(null);
	}
	
	
	/**
	 * Ouvre un FileChooser (une boite de dialogue) pour permettre à l'utilisateur de selectionner un carnet d'adresse à 
	 * charger
	 */
	@FXML
	private void handleOpen() {
		
		//FileChooser permet aux utilisateurs de naviguer dans le systeme des fichiers pour choisir un ou plusieurs fichiers
		FileChooser fileChooser = new FileChooser();
		
		//Configure le filtre d'extension
		//Un filtre d'extension est ajouté de telle manière que seuls sont affichés les fichiers dont 
		//l'extension est .xml
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files(*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		
		//Affiche la boite de dialogue d'ouverture de fichier (OpenFileDialog)
		File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
		
		if(file != null) {
			mainApp.loadPersonDataFromFile(file);
		}
	}
	
	/**
	 * Enregistre les données dans le fichier person actuellement ouvert 
	 * S'il n'y a pas de fichier ouvert , la boite de dialogue "save as" s'affiche
	 */
	@FXML
	private void handleSave()
	{
		File personFile = mainApp.getPersonFilePath();
		
		if(personFile != null) {
			mainApp.savePersonDataToFile(personFile);
		} else {
			handleSaveAs();
		}
	}

	/**
	 * Ouvre un FileChooser pour permettre à l'utilisateur de sélectionner un fichier dans lequel enregistrer 
	 * les données
	 */
	@FXML
	private void handleSaveAs() {
		
		FileChooser fileChooser = new FileChooser();
		
		//Configure le filtre d'extension 
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files(*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		
		//Affiche la boite de dialogue d'enregistrement du fichier (Save File Dialog)
		File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
		
		if(file != null) {
			//On assure qu'il a la bonne extension 
			if(!file.getPath().endsWith(".xml")) {
				file = new File(file.getPath() + ".xml");
			}
			
			mainApp.savePersonDataToFile(file);
		}
	}
	
	
	/**
	 * Ferme l'application
	 */
	@FXML
	private void handleExit() {
		
		//TO-DO : Sauvegarder le travail avant de quitter 
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Exit Application");
		alert.setHeaderText("Save before exit");
		alert.setContentText("Do you to save your work before leaving ?");
		ButtonType leave = new ButtonType("Leave");
		ButtonType save = new ButtonType("Save");
		ButtonType cancel = new ButtonType("Cancel");
		alert.getButtonTypes().clear();   //TO-DO : Supprimer que le bouton  
		alert.getButtonTypes().addAll(leave, save, cancel);
		Optional<ButtonType> result = alert.showAndWait();
		
		if(result.get() == leave) {
			System.exit(0);
		} else if(result.get() == save) {
			handleSave();
		} else {
			alert.close();
		}

	}
	
	@FXML
	private void handleShowBirthdayStatistics() {
		mainApp.showBirthdayStatistics();
	}
}
