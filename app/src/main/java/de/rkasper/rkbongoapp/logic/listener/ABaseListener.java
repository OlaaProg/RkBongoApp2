package de.rkasper.rkbongoapp.logic.listener;

import android.support.v7.app.AppCompatActivity;

/**
 * Diese Klasse stellt alle
 * Methoden/Funktionen und Eigenschaften
 * zur Verfuegung, welche alle Listenerklassen
 * diese Projektes benoetigen.
 * Diese Klasse ist abstrakt, das heisst
 * von ihr kann kein direktes Objekt instanziiert
 * werden. Lediglich von Kindklassen dieser
 * Klasse koennen Objekte generiert werden.
 * Dennoch kann es Konstruktoren geben, die
 * Attribute vordefinieren.
 */
public abstract class ABaseListener {
	
	//region 0. Konstanten
	
	//endregion
	
	//region 1. Decl. and Init Attribute
	
	/**
	 * Arbeitsreferenz auf die aktuelle Activity
	 * Diese koennen sein:
	 * <ul>
	 * <li>{@link de.rkasper.rkbongoapp.gui.MainActivity}</li>
	 * <li>{@link de.rkasper.rkbongoapp.gui.BongoNoteCrudActivity}</li>
	 * <li>{@link de.rkasper.rkbongoapp.gui.AboutUsActivity}</li>
	 * </ul>
	 */
	protected AppCompatActivity currentActivity;
	//endregion
	
	//region 2. Konstruktoren
	
	/**
	 * Standardkonstruktor zum direkten setzen
	 * der Arbeitsreferenz auf die Aktuelle Activity diese koennen sein:
	 * {@link de.rkasper.rkbongoapp.gui.MainActivity}
	 * <ul>
	 * <li>{@link de.rkasper.rkbongoapp.gui.MainActivity}</li>
	 * <li>{@link de.rkasper.rkbongoapp.gui.BongoNoteCrudActivity}</li>
	 * <li>{@link de.rkasper.rkbongoapp.gui.AboutUsActivity}</li>
	 * </ul>
	 *
	 * @param currentActivity :{@link AppCompatActivity}
	 */
	public ABaseListener(AppCompatActivity currentActivity) {
		this.currentActivity = currentActivity;
	}
	
	//endregion
	
	//region abstrakte Methoden / Funktionen
	public abstract void generateWidgetReferences();
	//endregion
}
