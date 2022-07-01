package fr.dawan.addressapp.util;

import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Adapter (pour JAXB) pour la conversion entre le LocalDate et le String '2022-07-01'
 * @author Admin stagiaire
 *
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate>{

	/**
	 * Convertir le XML en String puis le String en LocalDate
	 */
	@Override
	public LocalDate unmarshal(String v) throws Exception {
		return LocalDate.parse(v);
	}

	/**
	 * COnvertit la date  en string puis le string en xml
	 */
	@Override
	public String marshal(LocalDate v) throws Exception {
		return v.toString();
	}

}
