package fr.dawan.addressapp.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Fonctions Helper pour la gstion des dates 
 * @author Admin stagiaire
 *
 */
public class DateUtil {

	//Le pattern de date utilité pour la conversion 
	private static final String DATE_PATTERN = "dd.MM.yyyy";
	
	//Le Formatter de date 
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
	
	
	/**
	 * Formatte la date en chaine de caractère (String)
	 * Retourne la chaine formée 
	 * @param date (Localdate)
	 * @return   chaine formattée (String)
	 */
	public static String format(LocalDate date) {
		if(date == null) {
			return null;
		}
		return DATE_FORMATTER.format(date);
	}
	
	/**
	 * Convertit un String au format definie {@link DateUtil#DATE_PATTERN}
	 * vers un objet {@link LocalDate}
	 * 
	 * Retourne null si la chaine n'a pas pu être convertie
	 * @param dateString  : La date sous forme de chaine 
	 * @return  : L'objet date ou null s'il n'a pas pu être convertie
	 */
	public static LocalDate parse(String dateString) {
		
		try {
			return DATE_FORMATTER.parse(dateString, LocalDate::from);
		} catch (DateTimeParseException e) {
			return null;
		}
	}
	
	/**
	 * Verification de la chaine de caractère (dateString), s'il s'agit d'une date valide 
	 * (la date spécifiée en entré respecte le bon format) 
	 * @param dateString
	 * @return  true (si la chaine est une date valide)
	 */
	public static boolean validDate(String dateString) {
		//Essaie de parser le String
		return DateUtil.parse(dateString) != null;
	}
}
