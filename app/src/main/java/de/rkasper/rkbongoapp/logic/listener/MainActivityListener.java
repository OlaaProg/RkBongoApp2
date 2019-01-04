package de.rkasper.rkbongoapp.logic.listener;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import de.rkasper.rkbongoapp.R;
import de.rkasper.rkbongoapp.gui.AboutUsActivity;
import de.rkasper.rkbongoapp.gui.BongoNoteCrudActivity;
import de.rkasper.rkbongoapp.gui.MainActivity;
import de.rkasper.rkbongoapp.gui.listview.ListViewAdapter;
import de.rkasper.rkbongoapp.logic.database.DbManager;
import de.rkasper.rkbongoapp.testdata.TestData;

/**
 * Nimmt alle Klickevents
 * der {@link MainActivity}
 * entgegen und leitet
 * das Starten der {@link BongoNoteCrudActivity} mit
 * oder ohne Extras ein. Auch die {@link AboutUsActivity}
 * wird gestartet.
 *
 */
public class MainActivityListener extends ABaseListener implements AdapterView.OnItemClickListener,
                                                                   MenuItem.OnMenuItemClickListener {
	
	//region 0. Konstanten
	//endregion
	
	//region 1. Decl. and Init Attribute
	
	/**
	 * Zeigt alle {@link de.rkasper.rkbongoapp.model.BongoNote}s an
	 */
	private ListView lvBongoNotes;
	
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
	public MainActivityListener(AppCompatActivity currentActivity) {
		super(currentActivity);
		
		//1. Widgets generieren
		generateWidgetReferences();
		
		//2. ListView updaten
		updateListView();
	}
	//endregion
	
	//region 3. ListView Click Handling
	
	
	/**
	 * Springt jedes mal an wenn ein ListViewItem innerhalb der {@link android.widget.ListView} geklickt wird.
	 *
	 * @param parentListView      : {@link AdapterView} : ListView aus der {@link MainActivity}
	 * @param clickedListViewItem : {@link View} : Geklickte im
	 *                            {@link de.rkasper.rkbongoapp.gui.listview.ListViewAdapter} generierte ListViewItem
	 * @param index               : int : index des Datenelementes aus der uebergebenen Liste an den
	 *                            {@link de.rkasper.rkbongoapp.gui.listview.ListViewAdapter#bongoNoteList}
	 * @param lngId               : id : eines Datenelementes aus der uebergebenene Liste an den
	 *                            {@link de.rkasper.rkbongoapp.gui.listview.ListViewAdapter#bongoNoteList}
	 */
	@Override
	public void onItemClick(AdapterView<?> parentListView, View clickedListViewItem, int index, long lngId) {
		
		//Explizites Intent generieren
		Intent intentStartBongoNoteCrudActivity = new Intent(this.currentActivity, BongoNoteCrudActivity.class);
		
		//Name des Extras aus der res/values/strings.xml auslesen, muss vorher definiert werden
		String strExtraNameId = this.currentActivity.getString(R.string.strExtraNameId);
		
		//Id des Objektes von long zu int EXPLICIT CASTEN!!!
		int iId = (int) lngId;
		
		//Id mit namen an Intent geben.
		intentStartBongoNoteCrudActivity.putExtra(strExtraNameId, iId);
		
		
		//Activity starten
		this.currentActivity.startActivity(intentStartBongoNoteCrudActivity);
	}
	
	//endregion
	
	/**
	 * Zum auswerten welches MenuItem geklickt wurde.
	 *
	 * @param mnuItem : {@link MenuItem} : Geklicktes menu item
	 *
	 * @return true : Richtiges Klickhandling
	 */
	@Override
	public boolean onMenuItemClick(MenuItem mnuItem) {
		Intent intentActivityToStart = null;
		
		switch (mnuItem.getItemId()) {
			case R.id.mnuItemAdd:
				/*
				 *  Explizites Intent
				 *  Das Intent ist ein Nachrichtenobjekt von Android
				 *  welches die Information kapselt/entahelt welche Activity welche startet.
				 *  1. Parameter Ausgangsactivity
				 *  2. Parameter: Zielactivity sprich die Activity die gestartet werden soll
				 */
				intentActivityToStart = new Intent(this.currentActivity, BongoNoteCrudActivity.class);
				
				
				break;
			case R.id.mnuItemInfo:
				//AboutUs Activity
				intentActivityToStart = new Intent(this.currentActivity, AboutUsActivity.class);
				break;
		}
		
		if (intentActivityToStart != null) {
			//Activity Starten
			this.currentActivity.startActivity(intentActivityToStart);
		}
		return true;
	}
	
	
	//region Hilfsmethoden und Funktionen
	
	
	@Override
	public void generateWidgetReferences() {
		this.lvBongoNotes = this.currentActivity.findViewById(R.id.lvBongoNotes);
	}
	
	/**
	 * Updated die {@link ListView} {@link MainActivity#lvBongoNotes}
	 * mit den neusten Daten
	 */
	public void updateListView() {
		//TODO DB-Aufruf alle Daten auslesen
		ListViewAdapter listViewAdapter = new ListViewAdapter(this.currentActivity,
				DbManager.getInstance(this.currentActivity).getAllBongoNotesFromDbTbl());
		
		//Daten akutalisieren
		this.lvBongoNotes.setAdapter(listViewAdapter);
	}
	//endregion
}
