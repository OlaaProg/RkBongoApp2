package de.rkasper.rkbongoapp.model;

/**
 * Diese Klasse stellt
 * alle Eigenschaften,
 * Konstanten Getter und
 * Setter zur Verfuegung
 * die alle Modelklassen diese
 * Projektes gemeinsam haben.
 * Jede Modelklasse leitet
 * sich von dieser Klasse ab
 */
public class BaseModel {
	
	//region 0. Konstanten
	
	/**
	 * Standardwerte fuer String Attribute
	 */
	public static final String DEF_VALUE_STR = "noValueEntered";
	
	/**
	 * Standardwerte fuer Integer Attribute
	 */
	public static final int    DEF_VALUE_INT = -1;
	
	/**
	 * Standardwerte fuer Double Attribute
	 */
	public static final double DEF_VALUE_DBL = -1D;
	//endregion
	
	//region 1. Decl. and Init Attribute
	
	/**
	 * Datenbank id eines Objekts
	 * wird von der SQLite3Datenbank selber
	 * gesetzt
	 */
	protected int iId;
	
	/**
	 * Name des Objektes
	 */
	protected String strName;
	
	/**
	 * Datum der letzen Bearbeitung
	 */
	protected String strEditDate;
	//endregion
	
	//region 2. Konstruktoren
	
	/**
	 * Standardkonstruktor
	 * zum initalisieren
	 * aller Attribute mit
	 * Standardwerten:
	 * <ul>
	 *     <li>{@link BaseModel#DEF_VALUE_STR}</li>
	 *     <li>{@link BaseModel#DEF_VALUE_INT}</li>
	 *     <li>{@link BaseModel#DEF_VALUE_DBL}</li>
	 * </ul>
	 */
	public BaseModel() {
		this.iId = DEF_VALUE_INT;
		this.strName = DEF_VALUE_STR;
		this.strEditDate = DEF_VALUE_STR;
	}
	
	/**
	 * 1. Ueberladener Konstruktor zum direkten setzen
	 * der Attribut werte
	 * @param iId : int : Datenbank Id eines Objektes
	 * @param strName : {@link String} : Objektname
	 * @param strEditDate : {@link String} Datum der letzen Aenderung
	 */
	public BaseModel(int iId, String strName, String strEditDate) {
		this.iId = iId;
		this.strName = strName;
		this.strEditDate = strEditDate;
	}
	
	//endregion
	
	//region 3. Getter und Setter
	
	public int getId() {
		return iId;
	}
	
	public void setId(int iId) {
		this.iId = iId;
	}
	
	public String getName() {
		return strName;
	}
	
	public void setName(String strName) {
		this.strName = strName;
	}
	
	public String getEditDate() {
		return strEditDate;
	}
	
	public void setEditDate(String strEditDate) {
		this.strEditDate = strEditDate;
	}
	
	//endregion
	
	//region 4. Stringfunktionen
	
	@Override
	public String toString() {
		return "BaseModel{" +
				"iId=" + iId +
				", strName='" + strName + '\'' +
				", strEditDate='" + strEditDate + '\'' +
				'}';
	}
	
	
	//endregion
}
