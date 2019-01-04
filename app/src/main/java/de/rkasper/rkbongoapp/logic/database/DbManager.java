package de.rkasper.rkbongoapp.logic.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import de.rkasper.rkbongoapp.model.BongoNote;

/**
 *
 * Diese Klasse ist ein Singleton
 * und ihre Instanz exisitert nur einmal zur Laufzeit.
 * Sie stellt den zentralen Anlaufpunkt
 * der Datenhaltung da.
 * Created by Alfa-Dozent on 29.11.2018.
 */

public class DbManager extends SQLiteOpenHelper {
	
	//region 0.Konstanten
	private static final String TAG = DbManager.class.getSimpleName();
	
	/**
	 * Datenbankname
	 */
	private static final String DB_NAME = "bongoApp.db";
	
	/**
	 * Datenbankversion
	 */
	private static final int DB_VERSION = 1;
	//endregion
	
	//region 1. Decl. and Init Attribute
	
	/**
	 * Einzige instanz zur gesamten Laufzeit
	 */
	private static DbManager instance;
	
	/**
	 * Tabellenklasse
	 */
	private TblBongoNotes tblBongoNotes;
	//endregion
	
	//region 2. Konstruktor
	
	/**
	 * Privater Konstuktor verhindert
	 * das ein Objekt außerhalb der
	 * eigenen Klasse instanziiert werden kann.
	 * <p/>
	 * Es kann unter Android mit SQLite keine
	 * leere Datenbank(leere Tabellen) generiert
	 * werden.
	 * <p/>
	 * <p>
	 * Der Super-Konstruktor der Klasse
	 * {@link SQLiteOpenHelper#SQLiteOpenHelper(Context, String, SQLiteDatabase.CursorFactory, int)}
	 * checkt ob eine Datenabank bereits exisitiert.
	 * Sollte dies der Fall sein wird keine neue Datenbank generiert
	 * <p>
	 * Diese erst dann angelegt wenn
	 * die Funktion getWriteableDatabase() oder
	 * getReadableDatabase() aufgerufen wird.
	 * Sprich wenn ein Datensatz eingefuegt
	 * oder ausgelesen wird.
	 */
	private DbManager(Context context) {
		
		super(context, DB_NAME, null, DB_VERSION);
		
		this.tblBongoNotes = new TblBongoNotes();
		
		this.getWritableDatabase();
		
		Log.d(TAG + " - DbManager(): ", "Anlegen der Datenbank und generieren der Tabellenobjekte");
	}
	
	//endregion
	
	//region 3. Db Aufbau und updtate
	
	/**
	 * Wird aufgerufen wenn die Datenbank
	 * das erste mal generiert wird.
	 * <p>
	 * Hier werden die Datenbanktabellen
	 * generiert wird
	 *
	 * @param db : {@link SQLiteDatabase} : Datenbankobjekt.
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		try {
			Log.d(TAG + "- onCreate", "tblBongoNotes anlegen");
			String strStmt = tblBongoNotes.getCreateTableStatement();
			db.execSQL(strStmt);
			
		} catch (SQLException sqlEx) {
			Log.e(TAG, sqlEx.getMessage() + "\n" + sqlEx.getStackTrace().toString());
		}
	}
	
	/**
	 * Wird aufgerufen wenn die Datenbank ubgedatetd werde muss.<br>
	 * Fuer die implementierung des updates, sollten alle Tabellen<br>
	 * mit DROP TABLES entfernt werden und mit CREATE Tabeles<br>
	 * wieder hinzugefuegt werden<br>
	 * <br><br>
	 * Die Tabellen koennen auch mit dem SQL-Befehl ALTER-Tabele geaendert werden.<br>
	 * Soll nur eine Spalte hinzugefuegt werden so kann dies im Live-Betrieb gemacht werden.<br>
	 * Sobald daber eine Spaltennamen veraendert oder eine Spalte geloescht werden soll, muss<br>
	 * eine neue Tabelle angelegt werden und mit den Daten der alten Tabelle befuellt werden.<br>
	 * <br><br>
	 * Danach wird die alte Tabelle geloescht.<br>
	 * <br><br>
	 * Die Dokumentation zur Tabellenaenderungen findet sich<br>
	 * <a href="http://sqlite.org/lang_altertable.html">hier</a><br>
	 * <br>
	 * Diese Methode arbeitet mit Transactions, heist tritt eine Exception auf, wird
	 * alles automatisch wieder auf den Ursprungszustand zurueck gesetzt.
	 *
	 *
	 * @param db         : {@link SQLiteDatabase} : Datenbankobjekt
	 * @param oldVersion : int : alte Datenbankversion
	 * @param newVersion : int : neue Datenbankversion
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//Save all Tables
		//Drop all Tables
		//Generate new Tables
		
		Log.d(TAG + " - onUpgrade", "todo");
	}
	//endregion
	//region 4. Zurueck geben der einmalig generiert Instanz
	
	/**
	 * Gibt die einzige Instanz zur Laufzeit zurueck
	 *
	 * @return mInstance : {@link DbManager} : Instanz
	 */
	public static synchronized DbManager getInstance(Context context) {
		
		if (instance == null) {
			instance = new DbManager(context);
		}
		
		return instance;
	}
	
	/**
	 * Fuegt BongoNotes in die Db Tabell tblBongoNotes ein.
	 *
	 * @param bongoNotesToInsert : {@link List} {@link BongoNote} : Notiz zum einfuegen in die Db
	 *
	 * @return lngRowId : long : ZeilenId des zuletzt eingefuegten Datensatzen
	 */
	public long insertBongoNotesIntoDbTbl(List<BongoNote> bongoNotesToInsert) {
		
		return this.tblBongoNotes.insertTuples(this.getWritableDatabase(), bongoNotesToInsert);
	}
	
	/**
	 * Fuegt ein BongoNote in die Db Tabell tblBongoNotes ein.
	 *
	 * @param bongoNoteToInsert : {@link List} {@link BongoNote} : Notiz zum einfuegen in die Db
	 *
	 * @return lngRowId : long : ZeilenId des zuletzt eingefuegten Datensatzen
	 */
	public long insertBongoNoteIntoDbTbl(BongoNote bongoNoteToInsert) {
		
		return this.tblBongoNotes.insertTuple(this.getWritableDatabase(), bongoNoteToInsert);
	}
	
	/**
	 * Liest alle BongoNotes aus der DbTbl aus
	 *
	 * @return allBongoNotesFromTbl : {@link List} - {@link BongoNote}
	 */
	public List<BongoNote> getAllBongoNotesFromDbTbl() {
		
		return (List<BongoNote>) tblBongoNotes.getAllTuples(this.getWritableDatabase());
	}
	
	/**
	 * Gibt eine BongoNote aus der DB zurueck
	 *
	 * @param iId : int : id der Notiz
	 *
	 * @return singleBongoNoteFromTbl :{@link BongoNote} : Notiz
	 */
	@Nullable
	public BongoNote getSingleBongoNoteFromDbTbl(int iId) {
		
		return this.tblBongoNotes.getTupleById(this.getWritableDatabase(), iId);
	}
	
	/**
	 * Updaten einer Notiz
	 *
	 * @param singleBongoNoteUpdate : Notiz welche es zu updaten gilt
	 *
	 * @return countOfUpdatedRows : int : Anzahl der geaenderten Zeilen
	 */
	public int updateSingleBongoNoteInDbTbl(BongoNote singleBongoNoteUpdate) {
		
		return this.tblBongoNotes.updateTupleById(this.getWritableDatabase(), singleBongoNoteUpdate);
	}
	
	/**
	 * Loesche einer Notiz
	 *
	 * @param iId : int : id des zu loeschenden Objekts
	 *
	 * @return countOfUpdatedRows : int : Anzahl der geaenderten Zeilen
	 */
	public int deleteSingleBongoNote(int iId) {
		
		return this.tblBongoNotes.deleteTupleById(this.getWritableDatabase(), iId);
	}
}