package fr.dawan.addressapp.model;

import java.time.LocalDate;
import java.util.Random;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import fr.dawan.addressapp.util.LocalDateAdapter;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {
	
	/*
	 * Avec JavaFX il est courant d'utiliser les properties pour tous les champs de notre class. 
	 * Un property va nous permettre par exemple d'être automatiquement averti lorsque un des attributs a été modifié 
	 * Ceci nous aide à maintenir la Vue synchonisé avec les données 
	 */
	private StringProperty firstName;
	private StringProperty lastName;
	private StringProperty street;
	private IntegerProperty postalCode;
	private StringProperty city;
	private ObjectProperty<LocalDate> birthday;
	
	public Person() {
		this(null, null);  
	}

	public Person(String firstName, String lastName) {
		super();
		/*
		 * La classe SimpleStringProperty crée un string property qui est accessible en lecture et écriture
		 */
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		
		//Initialisation des autres attributs avec des données factices
		this.street = new SimpleStringProperty("Rue de Marseille");
		this.postalCode = new SimpleIntegerProperty(13000);
		this.city = new SimpleStringProperty("Marseille");

        this.birthday = new SimpleObjectProperty<LocalDate>(LocalDate.of(getRandomNumber(2020, 1950), getRandomNumber(12, 1), 05));
	}

	/**
	 * Genère une date aléatoire entre 1940 et 2020
	 * @return
	 */
	private int getRandomNumber(int max, int min) {
		return new Random().nextInt((max- min)+1) + min;
	}
	
	
	/*
	 * Les conventions de nommage des méthodes des propriétés sont les suivantes :
	 */
	
	 /* 1- La métode getFirstName() est un getter standard qui renvoie la valeur actuelle de la propriété firstName 
	 * 	  Par convention cette méthode est déclarée comme final 
	 * 	  Le type de retour pour cette méthode est String et non StringProperty
	 */
	public final String getFirstName() {
		//Obtient la valeur encapsulée 
		//Celle-ci doit être identique à la valeur renvoyée par getValue()
		return firstName.get();
	}
	
	/*
	 * 2- La méthode setFirstName(String) (egalement final) est un paramètre standard qui permet à un appelant de definir 
	 * la valeur de la propriété
	 * La méthode méthode setter est facultative/ 
	 * Son paramètre est egalement de type String
	 */
	public final void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}
	
	
	/*
	 * 3- La methode firstNameProperty definit le getter de la propriété. 
	 * 
	 */
	public StringProperty firstNameProperty() {
		return this.firstName;
	}
	
	
	
	public final String getLastName() {
		return lastName.get();
	}
	public final void setLastName(String lastName) {
		this.lastName.set(lastName);
	}
	public StringProperty lastNameProperty() {
		return this.lastName;
	}
	
	
	
	public final String getStreet() {

		return street.get();
	}
	public final void setStreet(String street) {
		this.street.set(street);
	}
	public StringProperty streetProperty() {
		return this.street;
	}
	
	
	public final int getPostalCode() {

		return postalCode.get();
	}
	public final void setPostalCode(int postalCode) {
		this.postalCode.set(postalCode);
	}
	public IntegerProperty postalCodeProperty() {
		return this.postalCode;
	}
	
	
	public final String getCity() {

		return city.get();
	}
	public final void setCity(String city) {
		this.city.set(city);
	}
	public StringProperty cityProperty() {
		return this.city;
	}

	
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public final LocalDate getBirthday() {
		return birthday.get();
	}
	public final void setBirthday(LocalDate birthday) {
		this.birthday.set(birthday);
	}
	public ObjectProperty<LocalDate> birthdayProperty() {
		return this.birthday;
	}
	
}
