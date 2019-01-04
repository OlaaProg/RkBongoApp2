package de.rkasper.rkbongoapp.testdata;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import de.rkasper.rkbongoapp.R;
import de.rkasper.rkbongoapp.logic.database.DbManager;
import de.rkasper.rkbongoapp.logic.handler.DateHandler;
import de.rkasper.rkbongoapp.model.BongoNote;

/**
 * Testet Dinge
 */
public class TestActivity extends AppCompatActivity implements View.OnClickListener {
	
	private static final String   TAG = TestActivity.class.getSimpleName();
	private              Button   btnTestStuff;
	private              EditText txtOutput;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.test_activity_layout);
		
		this.btnTestStuff = this.findViewById(R.id.btnTestStuff);
		this.txtOutput = this.findViewById(R.id.txtOutput);
		
		this.btnTestStuff.setOnClickListener(this);
	}
	
	/**
	 * Called when a view has been clicked.
	 *
	 * @param v The view that was clicked.
	 */
	@Override
	public void onClick(View v) {
		//Alle bestehenden Daten auslesen
		Log.d(TAG,"Ausgangsdaten:\n\n");
		for (BongoNote bn : DbManager.getInstance(this).getAllBongoNotesFromDbTbl()) {
			Log.d(TAG, bn.toString());
		}
//
////		//Einfuegen eines neuen Testdaten satzes
//		BongoNote bongoNoteToInsert = new BongoNote();
////
//		//Werte eitnragen ohne id weil die id von der Datenbank genereirt wird
//		bongoNoteToInsert.setName("Liste");
//		bongoNoteToInsert.setBongoPictureId(-1);
//		bongoNoteToInsert.setBongoLocationId(-1);
//		bongoNoteToInsert.setNoteContent("Herbert 01.01.1990\nKai 01.01.1989");
//		bongoNoteToInsert.setEditDate(DateHandler.getCurrentDateAsString(this));
//
//		//Einfuegen der neuen Notiz in die Datenbank
//		long rowId = DbManager.getInstance(this).insertBongoNoteIntoDbTbl(bongoNoteToInsert);
//
//		Log.d(TAG, "Einfuegen der bongoNoteToInsert hat geklappt - rowId: " + rowId);
//		Log.d(TAG,"Neuer DS:\n\n" + DbManager.getInstance(this).getSingleBongoNoteFromDbTbl((int)rowId).toString()+"\n\n");
		
//		//Auslesen einer bestimmten Notiz
//		BongoNote bongoNoteFromDb = DbManager.getInstance(this).getSingleBongoNoteFromDbTbl(20);
//
//		Log.d(TAG,"BongoNote mit id 20 vor UPDATE:\n" + bongoNoteFromDb.toString());
//
//		//Updaten einer bestimmten bongoNote
//		BongoNote bongoNoteToUpdate = bongoNoteFromDb;
//
//		bongoNoteToUpdate.setName("TO DO-Liste");
//		bongoNoteToUpdate.setNoteContent("Schuhe, Strümpfe und Schnürsenkel einkaufen\nDach fixen.");
//
//		//Update der bestimmten bongoNote in der Datenbank
//		DbManager.getInstance(this).updateSingleBongoNoteInDbTbl(bongoNoteToUpdate);
//
//		//Auslesen einer bestimmten Notiz
//		BongoNote bongoNoteFromDbAfterUpdate = DbManager.getInstance(this).getSingleBongoNoteFromDbTbl(20);
//
//		Log.d(TAG,"BongoNote mit id 20 nach UPDATE:\n" + bongoNoteFromDbAfterUpdate.toString());
		
		//Loeschen eines bestimmten Datensatzes
		DbManager.getInstance(this).deleteSingleBongoNote(23);
//
//		//Alle bestehenden Daten auslesen
		Log.d(TAG,"Nach loeschen:\n\n");
		for (BongoNote bn : DbManager.getInstance(this).getAllBongoNotesFromDbTbl()) {
			Log.d(TAG, bn.toString());
		}
//
		
	}
}
