package de.rkasper.rkbongoapp.logic.listener;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import de.rkasper.rkbongoapp.R;
import de.rkasper.rkbongoapp.gui.AboutUsActivity;
import de.rkasper.rkbongoapp.gui.BongoNoteCrudActivity;
import de.rkasper.rkbongoapp.gui.MainActivity;
import de.rkasper.rkbongoapp.logic.database.DbManager;
import de.rkasper.rkbongoapp.logic.handler.DateHandler;
import de.rkasper.rkbongoapp.logic.handler.PhotoHandler;
import de.rkasper.rkbongoapp.model.BongoNote;
import de.rkasper.rkbongoapp.model.BongoPicture;

public class BongoNoteCrudActivityListener extends ABaseListener implements
                                                                 View.OnClickListener,
                                                                 MenuItem.OnMenuItemClickListener,
                                                                 LocationListener {
	
	//region 0. Konstanten
	/**
	 * Gibt an nach welchem Zeitintervall in Millisekunden
	 * das GPS Modul checkt ob ein neuer Standort
	 * vorliegt. Liegt ein neuer Stanort vor, so
	 * springt die Methode {@link BongoNoteCrudActivityListener#onLocationChanged(Location)}
	 * an.
	 */
	private static final int REQUEST_LOCATION_UPDATE_TIME_INTERVAL_IN_MILLI_SECS = 1000;
	
	/**
	 * Gibt an nach welchem Distanzintervall in Meter
	 * das GPS Modul checkt ob ein neuer Standort
	 * vorliegt. Liegt ein neuer Stanort vor, so
	 * springt die Methode {@link BongoNoteCrudActivityListener#onLocationChanged(Location)}
	 * an.
	 */
	private static final float REQUEST_LOCATION_UPDATE_DISTANCE_INTERVAL_IN_METERS = 1F;
	
	/**
	 * Request Code zum auswerten anzuzeigenden Berechtigunsuberpruefung
	 */
	public static final int REQUEST_CODE_FINE_LOCATION = 1;
	
	private static final String TAG = BongoNoteCrudActivityListener.class
			.getSimpleName();
	
	/**
	 * Request zum auswerten der SystemkamerApp und deren
	 * Ergebnis
	 */
	public static final int REQUEST_CODE_IMAGE_CAPTURE = 2;
	
	//endregion
	
	//region 1. Decl. and Init Attribute
	private TextView txtvEditDate;
	private TextView txtvLongitude;
	private TextView txtvLatitude;
	private TextView txtvAltitude;
	private TextView txtvBongoPictureDescription;
	
	private EditText txtBongoNoteName;
	private EditText txtBongoNoteContent;
	
	private ImageView imgvBongoPicture;
	
	private BongoPicture bongoPictureLastTakenPic;
	
	/**
	 * An/Abschlaten der Standortbestimmung
	 */
	private LocationManager locationManager;
	
	private BongoNote bongoNoteToUpdateOrDelete;
	//endregion
	
	//region 2. Konstruktoren
	
	/**
	 * Standardkonstruktor zum direkten setzen
	 * der Arbeitsreferenz auf die Aktuelle Activity diese koennen sein:
	 * {@link MainActivity}
	 * <ul>
	 * <li>{@link MainActivity}</li>
	 * <li>{@link BongoNoteCrudActivity}</li>
	 * <li>{@link AboutUsActivity}</li>
	 * </ul>
	 *
	 * @param currentActivity :{@link AppCompatActivity}
	 */
	public BongoNoteCrudActivityListener(AppCompatActivity currentActivity) {
		super(currentActivity);
		
		generateWidgetReferences();
	}
	//endregion
	
	/**
	 * Ist das Attribut null so wird eine neue Notiz hinzugefuegt.
	 * Ist nicht null so muss diese in der  Db geupdaten/geloscht werden
	 *
	 * @param bongoNoteToUpdateOrDelete : {@link BongoNote} : Notiz zum bearbeiten oder loeschen
	 */
	public void setBongoNoteToUpdateOrDelete(@Nullable BongoNote bongoNoteToUpdateOrDelete) {
		this.bongoNoteToUpdateOrDelete = bongoNoteToUpdateOrDelete;
	}
	
	
	//region Klickhandling Views
	
	/**
	 * Springt jedes mal wenn eine View
	 * die diesen Listener zugeordnet bekommen hat.
	 * geklickt wird
	 *
	 * @param v : {@link View} : Geklicktes Widget
	 */
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
			case R.id.imgvBongoPicture:
				this.startSystemCameraApp();
				break;
		}
		
	}
	
	
	//endregion
	
	//region Klickhandling Menu
	
	/**
	 * Called when a menu item has been invoked.  This is the first code
	 * that is executed; if it returns true, no other callbacks will be
	 * executed.
	 *
	 * @param item The menu item that was invoked.
	 *
	 * @return Return true to consume this click and prevent others from
	 * executing.
	 */
	@Override
	public boolean onMenuItemClick(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.mnuItemSave:
				//TODO Speichern/Updaten in DB oder Aendern un DB
//				Log.d(TAG,DateHandler.getPhotoTimeStamp(this.currentActivity));
				
				//Eingabe werte auslesen
				BongoNote userInputAsBongoNote = this.getUserInputAsBongoNote();
//				if(userInputAsBongoNote != null){

//				}else{
					//TOASt
//				}
				
				if (bongoNoteToUpdateOrDelete != null) {
					//Id der alten Bongo not uebernehmen
					userInputAsBongoNote.setId(bongoNoteToUpdateOrDelete.getId());
					
					//Updaten in der Datenbank
					DbManager.getInstance(this.currentActivity).updateSingleBongoNoteInDbTbl(userInputAsBongoNote);
					
					//UserMsg
					Toast.makeText(this.currentActivity, R.string.strUserMsgUpdatedSuccessfully, Toast.LENGTH_SHORT).show();
				} else {
					
					//Neu einfuegen in die Datenbank
					DbManager.getInstance(this.currentActivity).insertBongoNoteIntoDbTbl(userInputAsBongoNote);
					
					//UserMsg
					Toast.makeText(this.currentActivity, R.string.strUserMsgSavedSuccessfully, Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id.mnuItemDelete:
				if (bongoNoteToUpdateOrDelete != null) {
					//TODO Loeschen in der Datenbank
					DbManager.getInstance(this.currentActivity).deleteSingleBongoNote(bongoNoteToUpdateOrDelete.getId());
					//Activity beenden
					this.currentActivity.finish();
				}
				break;
			case R.id.mnuItemLocation:
				this.checkPermissionAndTriggerGps(true);
				break;
			case R.id.mnuItemShare:
				//TO DO spaeter ShareDialog aufrufen.
				break;
		}
		return true;
	}
	//endregion
	
	//region Location handling
	
	/**
	 * Schaltet die GPS Standortbestimmung an und aus.
	 * Generiert einen Berechtigungsdialog zur Laufzeit,
	 * der User entscheidet ob unsere App den Standort auslesen
	 * darf oder nicht.
	 * <p>
	 * Die Antwortauswertung des Dialogs wird wieder in einer
	 * FALL BACK Methode in der aktuellen Activity stattfinden.
	 * Hier ist es die Methode:
	 * {@link BongoNoteCrudActivity#onRequestPermissionsResult(int, String[], int[])}
	 */
	public void checkPermissionAndTriggerGps(boolean turnOnGps) {
		
		//Checken ob der User bereits die Berechtigung bestaetigt hat
		if (ActivityCompat.checkSelfPermission(
				this.currentActivity, Manifest.permission.ACCESS_FINE_LOCATION)
				!= PackageManager.PERMISSION_GRANTED) {
			
			/*
			 * Berechtigung in eine String Array packen.
			 * Es koennten im Prinzip hier meherere
			 * Berechtigungen auf einmal erfragt werden.
			 * Dies empfiehlt sich nur in Aussnahmefaellen.
			 * Grundsaetlich sollte es immer eine gesonder
			 * Berechtigungsueberpruefung pro Berechtigung
			 * geben.
			 */
			
			String[] strPermissionsToRequest = {Manifest.permission.ACCESS_FINE_LOCATION};
			
			//Berechtigungsdialog anzeigen
			this.currentActivity.requestPermissions(strPermissionsToRequest,
					BongoNoteCrudActivityListener.REQUEST_CODE_FINE_LOCATION);
			
		} else {
			if (turnOnGps) {
				//Location Manager generieren
				this.locationManager = (LocationManager) this.currentActivity
						.getSystemService(Context.LOCATION_SERVICE);
				
				this.locationManager.requestLocationUpdates(
						LocationManager.GPS_PROVIDER,
						REQUEST_LOCATION_UPDATE_TIME_INTERVAL_IN_MILLI_SECS,
						REQUEST_LOCATION_UPDATE_DISTANCE_INTERVAL_IN_METERS,
						this
				);
			} else {
				
				if (this.locationManager != null) {
					//Standortueberwachung abschalten
					this.locationManager.removeUpdates(this);
				}
			}
		}
		
		
	}
	
	/**
	 * Springt jedes mal wenn sich der Standort
	 * des Geraets veraendert.
	 *
	 * @param gpsLocation : {@link Location} : Akuteller Standort
	 */
	@Override
	
	public void onLocationChanged(Location gpsLocation) {
		
		
		String strLonText = this.currentActivity.getString(R.string.strLongitudeText);
		String strLatText = this.currentActivity.getString(R.string.strLatitudeText);
		String strAltText = this.currentActivity.getString(R.string.strAltitudeText);
		
		txtvLongitude.setText(strLonText + " " + String.valueOf(gpsLocation.getLongitude()));
		txtvLatitude.setText(strLatText + " " + String.valueOf(gpsLocation.getLatitude()));
		txtvAltitude.setText(strAltText + " " + String.valueOf(gpsLocation.getAltitude()));
		
		Log.d(TAG, String.valueOf(gpsLocation.getAltitude()));
	}
	
	/**
	 * Springt jedes mal wenn sich etwas mit GPS-Signal
	 * veraendert
	 *
	 * @param providerTypeHereGps : Art den Standort zu bestimmen
	 *                            Wird bei der Generierung des {@link LocationManager}s
	 *                            als aller erstes angegeben.
	 * @param status              : int : Aenderugn des Status:
	 *                            um den Status festsettellen
	 *                            werden die Konstanten der Klasse {@link LocationProvider}
	 *                            Genutzt:
	 *                            <ul>
	 *                            <li>{@link LocationProvider#AVAILABLE} : Singal vorhanden</li>
	 *                            <li>
	 *                            {@link LocationProvider#TEMPORARILY_UNAVAILABLE}<br>
	 *                            Momentan kein Signal : Weniger als 3 Sateliten<br>
	 *                            zu erreichen
	 *                            </li>
	 *                            <li>{@link LocationProvider#OUT_OF_SERVICE}</li>
	 *                            </ul>
	 * @param extras              : {@link Bundle} extras
	 */
	@Override
	public void onStatusChanged(String providerTypeHereGps, int status, Bundle extras) {
		
		String strOnStatusChangedMsg = "Provider:\t" + providerTypeHereGps + "\nStatus:\t";
		
		
		switch (status) {
			case LocationProvider.AVAILABLE:
				strOnStatusChangedMsg += "Verfuegbar / Signal vorhanden\n";
				break;
			
			case LocationProvider.TEMPORARILY_UNAVAILABLE:
				strOnStatusChangedMsg += "Temporaer kein Signal vorhanden\n";
				break;
			
			case LocationProvider.OUT_OF_SERVICE:
				strOnStatusChangedMsg += "Kein Signal vorhanden\n";
				break;
		}
		
		Log.d(TAG, strOnStatusChangedMsg);
	}
	
	/**
	 * Springt an wenn das GPS aktiviert wird.
	 * Anschalten per Systemknopf in der Infoleiste
	 *
	 * @param providerTypeHereGps : Art den Standort zu bestimmen
	 *                            Wird bei der Generierung des {@link LocationManager}s
	 *                            als aller erstes angegeben.
	 */
	@Override
	public void onProviderEnabled(String providerTypeHereGps) {
		
		Log.d(TAG, "Standortbestimmung hier GPS angeschaltet");
	}
	
	/**
	 * Springt an wenn das GPS dekativiert wird.
	 * Anschalten per Systemknopf in der Infoleiste
	 *
	 * @param providerTypeHereGps : Art den Standort zu bestimmen
	 *                            Wird bei der Generierung des {@link LocationManager}s
	 *                            als aller erstes angegeben.
	 */
	@Override
	public void onProviderDisabled(String providerTypeHereGps) {
		
		Log.d(TAG, "Standortbestimmung hier GPS ausgeschaltet");
	}
	//endregion
	
	//region PhotoHandling
	
	/**
	 * Erweitern
	 * Startet die Systemkamera App
	 */
	private void startSystemCameraApp() {
		//1. Implizites Intent geneierieren
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		
		//Checken ob eine SystemkameraApp voliegt
		if (takePictureIntent.resolveActivity(this.currentActivity.getPackageManager()) != null) {
			
			//3. Leere Datei deklarieren
			File emptyImageFile = null;
			
			try {
				//4. Leere Datei generrieren lassen
				emptyImageFile = PhotoHandler.createEmptyImageFile(this.currentActivity);
			} catch (IOException e) {
				Log.d(TAG, e.getMessage() + "\n" + e.getStackTrace().toString());
			}
			
			//5. Checken ob das FileObjekt generiert wurde.
			if (emptyImageFile != null) {
				
				//6. Merken des letzten Pfades
				this.bongoPictureLastTakenPic = new BongoPicture();
				this.bongoPictureLastTakenPic.setFullPath(emptyImageFile.getAbsolutePath());
				
				//7. Packagestruktur der App auslesen. Dient zur identifizierung der Appp
				String strFileProviderAuthority = this.currentActivity
						.getString(R.string.strFileProviderAuthority);
				
				//8. Uri per FileProvider generieren lassen. Aktuelle Ativity, packagstruktur, leere Datei werden mitgegeben
				Uri photoUri = FileProvider.getUriForFile(this.currentActivity,
						strFileProviderAuthority,
						emptyImageFile);
				
				//9. Uri objekt als extra an das Intent haengen
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
				
				//10. Systemkamera App starten
				this.currentActivity
						.startActivityForResult(takePictureIntent, REQUEST_CODE_IMAGE_CAPTURE);
			}
			
			
		} else {
			Toast.makeText(this.currentActivity, R.string.strUserMsgNoSystemCameraApp, Toast.LENGTH_SHORT)
			     .show();
		}
		
	}
	
	/**
	 * Bild anzeigen
	 * Zeigt das aufgenommene Bild in der ImageView an.
	 * wird aus der {@link BongoNoteCrudActivity#onActivityResult(int, int, Intent)}
	 * aufgegrufen
	 */
	public void showTakenPicInImageView() {
		if (this.bongoPictureLastTakenPic != null) {
			
			String strLastTakenPictureFullPath = this.bongoPictureLastTakenPic.getFullPath();
			
			Bitmap bmLastTakenPicture = PhotoHandler
					.getBitmapFromPhotoUrl(strLastTakenPictureFullPath);
			
			this.imgvBongoPicture.setImageBitmap(bmLastTakenPicture);
		}
	}
	
	//endregion
	
	private BongoNote getUserInputAsBongoNote() {
		BongoNote userInputAsBongoNote = null;
		
		String strBongoNoteName = this.txtBongoNoteName.getText().toString();
		String strBongoNoteContent = this.txtBongoNoteContent.getText().toString();
//		boolean userInputValid = true;

//		if(strBongoNoteContent.isEmpty()){
//			userInputValid = false;
//		}
//		if(userInputValid){
		userInputAsBongoNote = new BongoNote();
		userInputAsBongoNote.setName(strBongoNoteName);
		userInputAsBongoNote.setNoteContent(strBongoNoteContent);
		//Neues EditDatum festlegen
		userInputAsBongoNote.setEditDate(DateHandler.getCurrentDateAsString(this.currentActivity));
//		}
		
		return userInputAsBongoNote;
	}
	
	@Override
	public void generateWidgetReferences() {
		this.txtvEditDate = this.currentActivity.findViewById(R.id.txtvEditDate);
		this.txtvLongitude = this.currentActivity.findViewById(R.id.txtvLongitude);
		this.txtvLatitude = this.currentActivity.findViewById(R.id.txtvLatitude);
		this.txtvAltitude = this.currentActivity.findViewById(R.id.txtvAltitude);
		this.txtvBongoPictureDescription = this.currentActivity
				.findViewById(R.id.txtvBongoPictureDescription);
		
		this.txtBongoNoteName = this.currentActivity.findViewById(R.id.txtBongoNoteName);
		this.txtBongoNoteContent = this.currentActivity.findViewById(R.id.txtBongoNoteContent);
		
		this.imgvBongoPicture = this.currentActivity.findViewById(R.id.imgvBongoPicture);
	}
	
}
