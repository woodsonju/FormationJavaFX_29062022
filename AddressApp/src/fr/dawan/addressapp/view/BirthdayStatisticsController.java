package fr.dawan.addressapp.view;

import java.text.DateFormatSymbols;
import java.util.List;
import java.util.Locale;

import fr.dawan.addressapp.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

/**
 * Le controleur pour la vue birthday statistic
 * @author Admin stagiaire
 *
 */
public class BirthdayStatisticsController {
	
	@FXML
	private BarChart<String, Integer> barChart;
	
	@FXML
	private CategoryAxis xAxis;
	
	/**
	 * Les données sous forme de liste de Strin observabke. 
	 * Permettra de tracer les modifications apportées à ses elements
	 */
	private ObservableList<String> monthNames = FXCollections.observableArrayList();
	
	
	/**
	 * Méthode automiquement appelée après le chargement du fichier fxml
	 */
	@FXML
	private void initialize() {
		//On obtient un tableau avec les noms des Mois en anglais 
		String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
		
		//On le convertit en liste et on l'ajoute à notre liste de mois ObservableList 
		monthNames.addAll(months);
		
		//On assigne les noms de mois en tant que catégorie pour l'axe horizontal
		xAxis.setCategories(monthNames);
	}
	
	
	/**
	 * Définit les personnes pour lesquelles afficher les statisques 
	 * Cette méthode sera appelée dans le mainApp
	 * @param persons
	 */
	public void setPersonData(List<Person> persons) {
		//Compte le nombre de personnes qui fête leur anniversaire au cours d'un mois donné
		int[] monCounter = new int[12];
		for (Person person : persons) {
			//Retourne le mois sous la forme d'un entier de 1 à 12
			int month = person.getBirthday().getMonthValue() - 1;
			monCounter[month]++;
		}
		
		XYChart.Series<String, Integer> series = new XYChart.Series<>();
		
		for (int i = 0; i < monCounter.length; i++) {
			//Chaque objet XYChart.Data représente une barre du graphique
			series.getData().add(new XYChart.Data<String, Integer>(monthNames.get(i), monCounter[i])) ;
		}
		
		barChart.getData().add(series);
		
	}

}
