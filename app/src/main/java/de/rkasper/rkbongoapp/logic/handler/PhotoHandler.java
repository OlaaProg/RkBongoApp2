package de.rkasper.rkbongoapp.logic.handler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import de.rkasper.rkbongoapp.R;

/**
 * Leitet das Aufnehmen von Fotos ein.
 * Created by Alfa-Dozent on 27.11.2018.
 */

public class PhotoHandler {
	
	//region 0. Konstanten
	private static final String TAG = PhotoHandler.class.getSimpleName();
	//endregion
	
	
	//region 1. Konstruktor
	
	/**
	 * Privater Konstruktor,
	 * bitte die statischen Klassen
	 * methoden und Funkitonen nutzen
	 */
	private PhotoHandler() {
		//Nichts zu tun außer Privat sein
	}
	//endregion
	
	/**
	 * Generiert eine neue leere Datei in welcher
	 * das aufgenommene Foto gespeichert werden soll
	 *
	 * @return emptyImageFile : {@link File} : Neue Bilddatei
	 *
	 * @throws IOException
	 */
	public static synchronized File createEmptyImageFile(Context context) throws IOException {
		
		
		//1. Dateiname generieren lassen
		String strImageFileNameAsPrefix = PhotoHandler.createImageFileNameWithoutFileExtension(context);
		
		//2. FileNameSuffix generieren
		String strFileExtensionJpgAsSuffix = context.getString(R.string.strFileExtensionJpg);
		
		//3. Pfad zur Datei beschaffen
		File storageDirectoryPath = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		
		//4. Image File zum speichern eines UserFotos generieren
		File emptyImageFile = File.createTempFile(
				strImageFileNameAsPrefix,
				strFileExtensionJpgAsSuffix,
				storageDirectoryPath
		);
		
		
		Log.d(TAG, "createEmptyImageFile - strCurrentPhotoUrl:" + emptyImageFile.getAbsolutePath());
		
		return emptyImageFile;
	}
	
	/**
	 * Baut einen validen Dateinamen(OHNE Dateiendung).
	 * Dazu wird ein Zeitstempelgeneriert.
	 *
	 * @param context : {@link Context} : Aktuelle Activity
	 *
	 * @return strImageFileNameWithoutFileExtension : {@link String} Imagename ohne Dateiendung
	 */
	private static String createImageFileNameWithoutFileExtension(Context context) {
		
		//1. Zeitstempel auslesen
		String strPhotoTimeStamp = DateHandler.getPhotoTimeStamp(context);
		
		//2. Dateiprefix generieren
		String strImagePrefixJpg = context.getString(R.string.strImagePrefixJpg);
		
		//3. FileNameWordSeperator generieren
		String strFileNameSeperator = context.getString(R.string.strFileNameSeperator);
		
		
		//4. File name zusammenbauen
		String strImageFileNameWithoutFileExtension = strImagePrefixJpg;
		strImageFileNameWithoutFileExtension += strPhotoTimeStamp;
		strImageFileNameWithoutFileExtension += strFileNameSeperator;
		
		return strImageFileNameWithoutFileExtension;
	}
	
	
	/**
	 * Diese Funktion gibt ein Bitmap auf Basis
	 * des Ihr uebergebenen Pfads zurueck.
	 *
	 * @param strPhotoUrl : String : Photopfad
	 *
	 * @return bmImageFile : {@link Bitmap} : Wenn das  Foto exisitert wird ein
	 * Bitmap zurueck geliefert. wenn nicht dann ist das Objekt null
	 */
	@Nullable
	public static synchronized Bitmap getBitmapFromPhotoUrl(String strPhotoUrl) {
		
		//Decl. and Init
		Bitmap bmImageFile = null;
		File imageFile = new File(strPhotoUrl);
		
		if ((imageFile.exists()) && (imageFile != null)) {
			
			bmImageFile = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
			
			Log.d(TAG, imageFile.getAbsolutePath());
		}
		
		return bmImageFile;
	}
}