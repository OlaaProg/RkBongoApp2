package de.rkasper.rkbongoapp.logic.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.util.List;

import de.rkasper.rkbongoapp.model.BaseModel;

public abstract class ABaseTableDefinition extends ASQLiteKeyWords{
	
	//region 0.Konstanten
	
	/**
	 * Spalte 1 - Index 0 Spaltenamen fuer Primaerschluessel id
	 */
	protected static final String COL_NAME_PRIMARY_KEY = "_id";
	
	/**
	 * Spalte 2 - Index 1 Spaltenname fuer den Namen eins Objektes
	 */
	protected static final String COL_NAME_NAME = "name";
	
	/**
	 * Spalte 3 - Index 2 Spaltenname fuer Fotopfade/Urls
	 */
	protected static final String COL_NAME_EDIT_DATE = "editDate";
	
	//endregion
	
	//region 1. Decl. and Init Attribute
	
	/**
	 * Tabellenname
	 */
	protected String strTableName;
	
	//endregion
	
	//region 2. Konstruktor
	
	/**
	 * Um den Tabellenname zu setzen sollten
	 * in den Unterklassen Konstanten verwendet werden.
	 * @param strTableName : {@link String} : Tabellenname
	 */
	protected ABaseTableDefinition(String strTableName){
		this.strTableName = strTableName;
	}
	//endreigon
	
	//region 3. Table CRUD Operationen
	
	/**
	 * Gibt das Statement zum anlegen einer Tabelle zurueck:<br>
	 * Beispiel: CREATE TABLE tblExample(...);
	 *
	 * @return
	 */
	public abstract String getCreateTableStatement();
	
	/**
	 * Fuegt mehere Datensaetze in die Datenbanktablle ein
	 *
	 * @param db  : {@link SQLiteDatabase} : Datenbankobjekt vom {@link DbManager}
	 *            zur Verfuegung gestellt
	 * @param tuplesToInsert - {@link List}  : Liste von Models
	 *
	 * @return rowId : long : ZeilenID
	 */
	public abstract long insertTuples(SQLiteDatabase db, List<? extends BaseModel> tuplesToInsert);
	
	/**
	 * Fuegt einen Datensatz in die Datenbanktablle ein
	 *
	 * @param db  : {@link SQLiteDatabase} : Datenbankobjekt vom {@link DbManager}
	 *            zur Verfuegung gestellt
	 * @param tupleToInsert - {@link List}  : Liste von Models
	 *
	 * @return rowId : long : ZeilenID
	 */
	public abstract long insertTuple(SQLiteDatabase db,BaseModel tupleToInsert);
	
	/**
	 * Liest alle Datensaetze aus einer Tabelle
	 * aus und gibt diese als Liste von Models zurueck
	 * @param db  : {@link SQLiteDatabase} : Datenbankobjekt vom {@link DbManager}
	 *            zur Verfuegung gestellt
	 * @return
	 */
	public abstract List<? extends BaseModel> getAllTuples(SQLiteDatabase db);
	
	/**
	 * Sucht einen bestimmten Datensatz aus der Tabelle raus.
	 * Dafuer wird die id genutzt.
	 *
	 * @param db  : {@link SQLiteDatabase} : Datenbankobjekt vom {@link DbManager}
	 *            zur Verfuegung gestellt
	 * @param iId : int : Id des gesuchten Datensatzes
	 *
	 * @return ein Modelobjekt welches von der Klasse BaseModl abgeleitet ist
	 */
	@Nullable
	public abstract BaseModel getTupleById(SQLiteDatabase db, int iId);
	
	
	/**
	 * Updaten ein bestimmtes Tupel in der Datenbank tabelle
	 * @param db  : {@link SQLiteDatabase} : Datenbankobjekt vom {@link DbManager}
	 *            zur Verfuegung gestellt
	 *
	 * @param baseModel : {@link BaseModel} : Model zum updaten
	 * @return
	 */
	public abstract int updateTupleById(SQLiteDatabase db, BaseModel baseModel );
	
	/**
	 * Loescht einen bestimmten Datensatz aus der Tabelle raus.
	 * Dafuer wird die id genutzt.
	 *
	 * @param db  : {@link SQLiteDatabase} : Datenbankobjekt vom {@link DbManager}
	 *            zur Verfuegung gestellt
	 * @param iId : int : Id des gesuchten Datensatzes
	 *
	 * @return ein Modelobjekt welches von der Klasse BaseModl abgeleitet ist
	 */
	public abstract int deleteTupleById(SQLiteDatabase db, int iId);
	//endregion
	
	//region 4. Konvertierung
	public abstract ContentValues getContentValuesFromModel(BaseModel baseModel);
	
	public abstract BaseModel getModelFromCursor(Cursor cursor);
	//endregion
}
