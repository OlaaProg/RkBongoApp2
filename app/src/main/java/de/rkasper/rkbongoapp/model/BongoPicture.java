package de.rkasper.rkbongoapp.model;

/**
 * Repreasentiert ein Bild
 * einer {@link BongoNote}.
 * Die Verbindung geschieht
 * ueber zu weisen von der id dieser
 * Klasse in der Klasse {@link BongoNote}
 * {@link BongoNote#iBongoPictureId} == {@link BongoPicture#iId}.
 * Existier kein Bild zur Notiz muss kein Objekt
 * dieser Klasse generiert werden.
 */
public class BongoPicture extends BaseModel {
	//region 0. Konstanten
	//endregion
	
	//region 1. Decl. and Init Attribute
	
	/**
	 * Absoluter Pfad zum Bild auf
	 * dem Geraet
	 */
	private String strFullPath;
	
	/**
	 * Bildbeschreibung
	 */
	private String strPictureDescription;
	//endregion
	
	//region 2. Konstruktoren
	
	/**
	 * Standardkonstruktor
	 * zum initalisieren
	 * aller Attribute mit
	 * Standardwerten
	 * <ul>
	 * <li>{@link BaseModel#DEF_VALUE_STR}</li>
	 * <li>{@link BaseModel#DEF_VALUE_INT}</li>
	 * <li>{@link BaseModel#DEF_VALUE_DBL}</li>
	 * </ul>
	 * Die Attribute der Elternklassen werden durch
	 * den Aufruf des Elternkonstruktors gesetzt;
	 */
	public BongoPicture() {
		super();
		
		this.strFullPath = DEF_VALUE_STR;
		this.strPictureDescription = DEF_VALUE_STR;
	}
	
	/**
	 * 1. Ueberladener Konstruktor zum direkten setzen der Hauptattribute dieser Klasse
	 * @param strFullPath : {@link String} : Pfad zum Bild
	 * @param strPictureDescription : {@link String} : Beschreibung des Bildes
	 */
	public BongoPicture(String strFullPath, String strPictureDescription) {
		super();
		
		this.strFullPath = strFullPath;
		this.strPictureDescription = strPictureDescription;
	}
	
	//endregion
	
	//region 3. Getter und Setter
	
	public String getFullPath() {
		return strFullPath;
	}
	
	public void setFullPath(String strFullPath) {
		this.strFullPath = strFullPath;
	}
	
	public String getPictureDescription() {
		return strPictureDescription;
	}
	
	public void setPictureDescription(String strPictureDescription) {
		this.strPictureDescription = strPictureDescription;
	}
	
	//endregion
	
	//region 4. Stringfunktionen
	
	@Override
	public String toString() {
		return "BongoPicture{" +
				"strFullPath='" + strFullPath + '\'' +
				", strPictureDescription='" + strPictureDescription + '\'' +
				"} " + super.toString();
	}
	
	//endregion

}
