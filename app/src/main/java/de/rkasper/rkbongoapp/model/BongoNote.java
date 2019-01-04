package de.rkasper.rkbongoapp.model;

/**
 * Stellt eine Notiz da.
 * Sie verweist auf ein
 * evtl. Bild ode einen Standort
 */
public class BongoNote extends BaseModel {
	//region 0. Konstanten
	//endregion
	
	//region 1. Decl. and Init Attribute
	/**
	 * Fremdschluessel Id zu einem Bild
	 * wenn dieses Attribut den Standardwert
	 * {@link BaseModel#DEF_VALUE_INT} hat
	 * dann existiert kein Bild zur Notiz
	 */
	private int iBongoPictureId;
	
	/**
	 * Fremdschluessel Id zu einer Location
	 * wenn dieses Attribut den Standardwert
	 * {@link BaseModel#DEF_VALUE_INT} hat
	 * dann existiert kein Bild zur Notiz
	 */
	private int iBongoLocationId;
	
	/**
	 * Inhalt der Notiz
	 */
	private String strNoteContent;
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
	public BongoNote() {
		super();

		//Eigene Attribute setzen
		this.iBongoPictureId = DEF_VALUE_INT;
		this.iBongoLocationId = DEF_VALUE_INT;
		this.strNoteContent = DEF_VALUE_STR;
	}
	
	/**
	 * 1. Ueberladener Konstruktor
	 *
	 * @param iId            : int : {@link BaseModel#iId}
	 * @param strNoteName    : {@link String} : {@link BaseModel#strName}
	 * @param strNoteContent : {@link String} : Notizinhalt
	 */
	public BongoNote(int iId, String strNoteName, String strNoteContent) {
		this();

		this.iId = iId;
		this.strName = strNoteName;
		this.strNoteContent = strNoteContent;
	}

	/**
	 * 2. Ueberladener Konstruktor zum direkten setzen der Hauptattribute
	 *
	 * @param iBongoPictureId  : int : Fremdschluessel Id zu einem Bild.
	 *                         Existiert kein Bild dann bitte den Wert
	 *                         mit folgender Konstante setze: {@link BaseModel#DEF_VALUE_INT}
	 * @param iBongoLocationId : int : Fremdschluessel Id zu einer Location.
	 *                         Existiert kein Bild dann bitte den Wert
	 *                         mit folgender Konstante setze: {@link BaseModel#DEF_VALUE_INT}
	 * @param strNoteContent   : {@link String} : Notizinhalt
	 */
	public BongoNote(int iBongoPictureId, int iBongoLocationId, String strNoteContent) {
		this();

		this.iBongoPictureId = iBongoPictureId;
		this.iBongoLocationId = iBongoLocationId;
		this.strNoteContent = strNoteContent;
	}

	/**
	 * 3. Ueberladener Konstruktor
	 *
	 * @param iId            : int : {@link BaseModel#iId}
	 * @param strEditDate    : {@link String} : {@link BaseModel#strEditDate}
	 * @param strNoteName    : {@link String} : {@link BaseModel#strName}
	 * @param strNoteContent : {@link String} : Notizinhalt
	 */
	public BongoNote(int iId, String strEditDate, String strNoteName, String strNoteContent) {
		this();

		this.iId = iId;
		this.strEditDate = strEditDate;
		this.strName = strNoteName;
		this.strNoteContent = strNoteContent;
	}
	
	//endregion
	
	//region 3. Getter und Setter
	
	public int getBongoPictureId() {
		return iBongoPictureId;
	}
	
	public void setBongoPictureId(int iBongoPictureId) {
		this.iBongoPictureId = iBongoPictureId;
	}
	
	public int getBongoLocationId() {
		return iBongoLocationId;
	}
	
	public void setBongoLocationId(int iBongoLocationId) {
		this.iBongoLocationId = iBongoLocationId;
	}
	
	public String getNoteContent() {
		return strNoteContent;
	}
	
	public void setNoteContent(String strNoteContent) {
		this.strNoteContent = strNoteContent;
	}
	
	//endregion
	
	//region 4. Stringfunktionen
	
	@Override
	public String toString() {
		return "BongoNote{" +
				"iBongoPictureId=" + iBongoPictureId +
				", iBongoLocationId=" + iBongoLocationId +
				", strNoteContent='" + strNoteContent + '\'' +
				"} " + super.toString();
	}
	
	
	//endregion
}
