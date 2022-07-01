package fr.dawan.addressapp.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Classe Helper pour envelopper (wrapper) une liste de personne 
 * Cette classe est utilisé pour enregistrer la liste de spersonnes au format XML
 * @author Admin stagiaire

 */

@XmlRootElement(name="persons")   //definit le nom de l'élément racine
public class PersonListWrapper {

	private List<Person> persons;

	@XmlElement(name= "person")
	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
	
}
