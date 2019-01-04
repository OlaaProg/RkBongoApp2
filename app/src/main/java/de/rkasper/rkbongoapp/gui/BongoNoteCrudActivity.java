package de.rkasper.rkbongoapp.gui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.rkasper.rkbongoapp.R;
import de.rkasper.rkbongoapp.logic.database.DbManager;
import de.rkasper.rkbongoapp.logic.handler.DateHandler;
import de.rkasper.rkbongoapp.logic.handler.PhotoHandler;
import de.rkasper.rkbongoapp.logic.listener.BongoNoteCrudActivityListener;
import de.rkasper.rkbongoapp.logic.listener.MainActivityListener;
import de.rkasper.rkbongoapp.model.BongoNote;
import de.rkasper.rkbongoapp.testdata.TestData;

/**
 * Zeigt {@link de.rkasper.rkbongoapp.model.BongoNote}s
 * im Detail an. Und leiter die Crud Operation ein.
 */
public class BongoNoteCrudActivity extends AppCompatActivity {
	//region 0. Konstanten
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
	
	private BongoNoteCrudActivityListener bongoNoteCrudActivityListener;
	//endregion
	
	//region 2. Lebenszyklus
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//1. Layout setzen
		setContentView(R.layout.bongo_note_crud_activity_layout);
		
		//2. Widgets generieren
		this.txtvEditDate = this.findViewById(R.id.txtvEditDate);
		this.txtvLongitude = this.findViewById(R.id.txtvLongitude);
		this.txtvLatitude = this.findViewById(R.id.txtvLatitude);
		this.txtvAltitude = this.findViewById(R.id.txtvAltitude);
		this.txtvBongoPictureDescription = this.findViewById(R.id.txtvBongoPictureDescription);
		
		this.txtBongoNoteName = this.findViewById(R.id.txtBongoNoteName);
		this.txtBongoNoteContent = this.findViewById(R.id.txtBongoNoteContent);
		
		this.imgvBongoPicture = this.findViewById(R.id.imgvBongoPicture);
		
		//3. Listener generieren
		this.bongoNoteCrudActivityListener = new BongoNoteCrudActivityListener(this);
		
		//4. Listern zuweisen
		this.imgvBongoPicture.setOnClickListener(this.bongoNoteCrudActivityListener);
		
		//5. Evtl. Daten setzen
		this.checkForExtrasAndShowBongoNoteDataOnGui();
		
	
	}
	//endregion
	
	//region 3. Fallbackmethoden
	
	/**
	 *
	 * Springt jedes mal an wenn eine (System-)Activity mit startActivityForResult
	 * gestartet wurde. Dient zum auswerten des Ergebnisses. In unserem Fall wurde
	 * die Systemkameragestartet und hier ausgewertet ob ein Bild vorliegt. Wenn ja
	 * wird ein Bildauschnitt in der ImageView angezeigt.
	 * @param requestCode : int : {@link BongoNoteCrudActivityListener#REQUEST_CODE_IMAGE_CAPTURE}
	 * @param resultCode : int : Ergebnis der SystemkameraApp
	 *                   {@link BongoNoteCrudActivity#RESULT_OK} = Bild aufgenommen
	 *                   {@link BongoNoteCrudActivity#RESULT_CANCELED} = Bildaufnahme abgebrochen
	 * @param data : {@link Intent} : Daten der SystemkameraApp
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		//1. Checken welche Systemactivity gestartet wurde
		if (requestCode == BongoNoteCrudActivityListener.REQUEST_CODE_IMAGE_CAPTURE) {

			//2. Checken ob ein Bild aufgenommen wurde
			if (resultCode == BongoNoteCrudActivity.RESULT_OK) {

				//3. Aufgenommenes Bild anzeigen lassen.
				this.bongoNoteCrudActivityListener.showTakenPicInImageView();
			}
		}
	}
	
	/**
	 * Springt an nach dem der User einen zuvor angezeigten
	 * Berechtigunsdialog beatwortet hat
	 *
	 * @param requestCode   : int : Bei der Dialoganzeige mitgegebener Code
	 *                      zur identifizierung des Berechtigungsdialog
	 * @param permissions   : {@link String} - Array: Berechtigungen die
	 *                      erfragt worden sind, IMMER nur eine pro Dialog-
	 *                      aufruf<br>
	 *                      <br>
	 *                      Das Array permissions und das Array grantResults sind<br>
	 *                      immer gleich aufgebaut:
	 *                      <br>
	 *                      permissions == grantResults<br>
	 *                      <br>
	 *                      Beispiel bei einer Berechtigunserfragung:<br>
	 *                      permission[0] = Manifest.permission.ACCESS_FINE_LOCATION<br>
	 *                      <br>
	 *                      grantResults[0] = Userergebnis fuer Manifest.permission.ACCESS_FINE_LOCATION<br>
	 *                      <br>
	 *                      <br>
	 *                      Beispiel bei meheren Berechtigunserfragungen auf einmal:<br>
	 *                      permission[0] = Manifest.permission.ACCESS_FINE_LOCATION<br>
	 *                      permission[1] = Manifest.permission.WRITE_EXTERNAL_STORAGE<br>
	 *                      <br>
	 *                      grantResults[0] = Userergebnis fuer Manifest.permission.ACCESS_FINE_LOCATION<br>
	 *                      grantResults[1] = Userergebnis fuer Manifest.permission.WIRTE_EXTERNAL_STORAGE<br><br>
	 * @param grantResults: int - Array : Enthaelt die ausgewaehlte Antwort des
	 *                      Users
	 */
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		
		if (requestCode == BongoNoteCrudActivityListener.REQUEST_CODE_FINE_LOCATION) {
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				this.bongoNoteCrudActivityListener.checkPermissionAndTriggerGps(true);
			}
		}
	}
	
	//endregion
	
	//region 4. Menu
	
	/**
	 * Generiert das Menu
	 *
	 * @param menu : {@link Menu} : Menu was generiert werden soll
	 *
	 * @return true zum anzeigen des Menus false um es nicht anzuzeigen
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.getMenuInflater().inflate(R.menu.bongo_note_crud_menu, menu);
		return true;
	}
	
	/**
	 * Zum auswerten welches MenuItem geklickt wurde.
	 *
	 * @param mnuItem : {@link MenuItem} : Geklicktes menu item
	 *
	 * @return true : Richtiges Klickhandling
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem mnuItem) {
		this.bongoNoteCrudActivityListener.onMenuItemClick(mnuItem);
		return true;
	}
	
	//endregion
	
	//region Hilfsmethoden und Funktionen
	
	/**
	 *
	 * voliegen. Gibt es keine Extras wurde diese Activity
	 * im {@link MainActivityListener#onMenuItemClick(MenuItem)}
	 * gestartet. Liegene Extras vor so wurde diese Actvity im
	 * {@link MainActivityListener#onItemClick(AdapterView, View, int, long)}
	 * gestartet.
	 * <p>
	 * Liegen Extras vor so werden die Daten der passenden
	 * {@link BongoNote} auf der Gui angezeigt.
	 * <p>
	 * Liegen keine Extras vor geschieht nichts.
	 */
	private void checkForExtrasAndShowBongoNoteDataOnGui() {
		
		//1. Intent beschaffen welches die Activity gestartet hat.
		Intent intentStartBongoNoteCrudActivity = this.getIntent();
		
		//2. ExtraName aus res/values/strings.xml ausles
		String strExtraNameId = this.getString(R.string.strExtraNameId);
		
		//3. Checken ob ein Extra voliegt
		if (intentStartBongoNoteCrudActivity.hasExtra(strExtraNameId)) {
			
			//4. Extras beschaffen
			Bundle allExtras = intentStartBongoNoteCrudActivity.getExtras();
			
			//5. Id auslesen
			int iId = allExtras.getInt(strExtraNameId);
			
			
			
			//TODO Bongo mit uebergebener Id aus der Db auslesen
			BongoNote searchedBongoNote = DbManager.getInstance(this).getSingleBongoNoteFromDbTbl(iId);
			
			
			//Checken ob die Notiz tatsaelich gefunden wurde
			if (searchedBongoNote != null) {
				//Daten in die Gui eintragen
				this.txtvEditDate.setText(searchedBongoNote.getEditDate());
				
				//TO DO Location spaeter machen
//				this.txtvLongitude.setText(searchedBongoNote.get);
//				this.txtvLatitude.setText(searchedBongoNote.get);
//				this.txtvAltitude.setText(searchedBongoNote.get);
				
				//TO DO Bild spaeter machen
//				this.txtvBongoPictureDescription.setText(searchedBongoNote.get);
				
				this.txtBongoNoteName.setText(searchedBongoNote.getName());
				this.txtBongoNoteContent.setText(searchedBongoNote.getNoteContent());

//				this.imgvBongoPicture.setText(searchedBongoNote.get);
				
				//TODO Geklickte BongoNote an Listener weiterleiten
				this.bongoNoteCrudActivityListener.setBongoNoteToUpdateOrDelete(searchedBongoNote);
			}
		}else{
			//Akutelles Datum anzeigen sollten keine Extras vorliegen
			txtvEditDate.setText(DateHandler.getCurrentDateAsString(this));
			
			//Kein Extra keine Bonognote
			this.bongoNoteCrudActivityListener.setBongoNoteToUpdateOrDelete(null);
		}
	}
	//endregion
}
