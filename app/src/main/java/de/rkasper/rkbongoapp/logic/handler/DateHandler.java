package de.rkasper.rkbongoapp.logic.handler;

import android.content.Context;
import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.rkasper.rkbongoapp.R;

/**
 * Stellt Zeitstempel als
 * Strings zur verfuegung.
 */
public class DateHandler {
	
	//region 0. Konstanten
	//endregion
	
	//region 1. Decl. and Init Attribute
	//endregion
	
	//region 2. Konstruktoren
	
	private DateHandler() {
		//Nichts zu tun ausser privat zu sein
	}
	
	//endregion
	
	//region 3. Zeitstempelfunktionen
	/**
	 * Gibt uns den aktuellen Zeitstempel zurueck
	 * @param context : {@link Context} : Akutelle Activity die das Datum brauch
	 * @return strTimeStamp : {@link String} : Zeitstempel
	 */
	public static synchronized String getCurrentDateAsString(@NonNull Context context){
		
		//1. StingResource aus res/values/strings.xml auslesen "dd.MM.yyyy"
		String strDatePattern = context.getString(R.string.strDatePattern);
		
		//2. Datumsformatierer mit dem eigenen TimeStampPAttern erstellen
		DateFormat dateFormat = new SimpleDateFormat(strDatePattern);
		
		//3. Akutelles Datum und aktuelle Zeitfestellen
		Date currentDateAndTime = new Date();
		
		//4. Aktueller Zeitstempel als String zurueckgeben
		return dateFormat.format(currentDateAndTime);
	}
	
	
	/**
	 * Generiert einen Zeitstempel extra fuer Fotos
	 * welche es zu speichern gilt
	 * @param context : {@link Context} : Akutelle Activity
	 * @return strPhotoTimeStamp : {@link String} : Fotozeitstempel
	 */
	public static synchronized String getPhotoTimeStamp(Context context){
		
		//1. Zeitstempel generieren
		String strImageTimeStampPattern = context.getString(R.string.strImageTimeStampPattern);
		
		//2. Aktuelle Zeit feststellen durch ein neue Date Objekts
		Date currentTime = new Date();
		
		//3. Datumsformatierung festlegen
		DateFormat simpleDateFormat = new SimpleDateFormat(strImageTimeStampPattern);
		
		
		return  simpleDateFormat.format(currentTime);
	}
	//endregion
	

}
