package de.rkasper.rkbongoapp.model;

import android.location.Location;

/**
 * Stellt eine GPS-Location
 * zu einer {@link BongoNote} dar.
 * Sollte es keine Location geben
 * muss kein Objekt dieser Klasse
 * generiert. Auch hier funktioniert
 * die Zuordung ueber ids.
 * {@link BongoNote#iBongoLocationId} == {@link BongoLocation#iId}
 */
public class BongoLocation extends BaseModel {
	//region 0. Konstanten
	//endregion
	
	//region 1. Decl. and Init Attribute
	
	/**
	 * GPS Location einer {@link BongoNote}
	 */
	private Location location;
	//endregion
	
	//region 2. Konstruktoren
	
	/**
	 * Standarkonstruktor
	 */
	public BongoLocation() {
		super();
	}
	
	/**
	 * 1. Ueberladener Konstruktor zum direkten setzen der Location
	 * @param location : {@link Location} : GPS Locations
	 */
	public BongoLocation(Location location) {
		super();
		
		this.location = location;
	}
	
	/**
	 * 2. Ueberladener Konstruktor zum direkten setzen der Location
	 * @param iId : int : {@link BaseModel#iId}
	 * @param strLocationName : {@link String} : Name der Location
	 * @param location : {@link Location} : GPS Location
	 */
	public BongoLocation(int iId, String strLocationName, Location location) {
		this();
		
		this.iId = iId;
		this.strName = strLocationName;
		this.location = location;
	}
	
	//endregion
	
	//region 3. Getter und Setter
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	//endregion
	
	//region 4. Stringfunktionen
	
	@Override
	public String toString() {
		return "BongoLocation{" +
				"location=" + location +
				"} " + super.toString();
	}
	
	
	//endregion
}
