package fr.dawan.addressapp.model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {
	
	/*
	 * Avec JavaFX il est courant d'utiliser les properties pour tous les champs de notre class. 
	 * Un property va nous permettre par exemple d'�tre automatiquement averti lorsque un des attributs a �t� modifi� 
	 * Ceci nous aide � maintenir la Vue synchonis� avec les donn�es 
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
		 * La classe SimpleStringProperty cr�e un string property qui est accessible en lecture et �criture
		 */
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		
		//Initialisation des autres attributs avec des donn�es factices
		this.street = new SimpleStringProperty("Rue de Marseille");
		this.postalCode = new SimpleIntegerProperty(13000);
		this.city = new SimpleStringProperty("Marseille");
		this.birthday = new SimpleObjectProperty<LocalDate>(LocalDate.of(1993, 8, 27));
	}
	
	/*
	 * Les conventions de nommage des m�thodes des propri�t�s sont les suivantes :
	 */
	
	 /* 1- La m�tode getFirstName() est un getter standard qui renvoie la valeur actuelle de la propri�t� firstName 
	 * 	  Par convention cette m�thode est d�clar�e comme final 
	 * 	  Le type de retour pour cette m�thode est String et non StringProperty
	 */
	public final String getFirstName() {
		//Obtient la valeur encapsul�e 
		//Celle-ci doit �tre identique � la valeur renvoy�e par getValue()
		return firstName.get();
	}
	
	/*
	 * 2- La m�thode setFirstName(String) (egalement final) est un param�tre standard qui permet � un appelant de definir 
	 * la valeur de la propri�t�
	 * La m�thode m�thode setter est facultative/ 
	 * Son param�tre est egalement de type String
	 */
	public final void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}
	
	
	/*
	 * 3- La methode firstNameProperty definit le getter de la propri�t�. 
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
