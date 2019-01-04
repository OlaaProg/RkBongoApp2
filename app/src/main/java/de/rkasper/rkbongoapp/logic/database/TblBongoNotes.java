package de.rkasper.rkbongoapp.logic.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import de.rkasper.rkbongoapp.model.BaseModel;
import de.rkasper.rkbongoapp.model.BongoNote;

public class TblBongoNotes extends ABaseTableDefinition {
	
	//region 0 Konstanten
	
	/**
	 * Tabellenname
	 */
	private static final String TBL_NOTES = "tblNotes";
	
	/**
	 * 4. Spalte mit dem Index 3 Femdschluessel auf Bild
	 */
	private static final String COL_NAME_FKEY_PICTURE_ID = "fkeyPictureId";
	
	/**
	 * 5. Spalte mit dem Index 4 Femdschluessel auf Location
	 */
	private static final String COL_NAME_FKEY_LOCATION_ID = "fkeyLocationId";
	
	/**
	 * 6. Spalte mit dem Index 5 Notizinhalt
	 */
	private static final String COL_NAME_NOTE_CONTENT = "noteContent";
	//endregion
	
	//region 1. Decl. and Init Attribute
	//endregion
	
	//region 2. Konstruktor
	
	/**
	 * Setzt die Eigenschaft
	 * des Tabellennamens
	 */
	public TblBongoNotes() {
		super(TBL_NOTES);
	}
	
	//endregion
	
	//region 3. Table CRUD Operationen
	
	/**
	 * Gibt das Statement zum anlegen einer Tabelle zurueck:<br>
	 * CREATE TABLE tblBongoNotes<br>
	 * //Spaltennamen / Datentyp und Anweisungen//<br>
	 * (<br>
	 * _id INTEGER PRIMARY KEY,<br>
	 * name TEXT,<br>
	 * editDate TEXT,<br>
	 * fkeyPictureId INTEGER,<br>
	 * fkeyLocationId INTEGER,<br>
	 * noteContent TEXT<br>
	 * );<br>
	 *
	 * @return strCreateStatement: {@link String} : CREATE Statement
	 */
	@Override
	public String getCreateTableStatement() {
		
		return CREATE_TBL + this.strTableName
				+ CHAR_OPEN_BRACKET
				+ COL_NAME_PRIMARY_KEY + PRIMARY_KEY_AUTO_INCREMENT_INC_COMMA
				+ COL_NAME_NAME + DATA_TYPE_TEXT_INC_COMMA
				+ COL_NAME_EDIT_DATE + DATA_TYPE_TEXT_INC_COMMA
				+ COL_NAME_FKEY_PICTURE_ID + DATA_TYPE_INTEGER_INC_COMMA
				+ COL_NAME_FKEY_LOCATION_ID + DATA_TYPE_INTEGER_INC_COMMA
				+ COL_NAME_NOTE_CONTENT + DATA_TYPE_TEXT
				+ CHAR_CLOSE_BRACKET_SEMICOLON;
	}
	
	/**
	 * Fuegt mehere Datensaetze in die Datenbanktablle ein
	 *
	 * @param db             : {@link SQLiteDatabase} : Datenbankobjekt vom {@link DbManager}
	 *                       zur Verfuegung gestellt
	 * @param tuplesToInsert - {@link List}  : Liste von Models
	 *
	 * @return rowId : long : ZeilenID
	 */
	@Override
	public long insertTuples(SQLiteDatabase db, List<? extends BaseModel> tuplesToInsert) {
		long lngRowId = -1;
		
		try {
			
			
			for (BaseModel bm : tuplesToInsert) {
				//1. Content Values generieren
				ContentValues cvDish = this.getContentValuesFromModel(bm);
				
				//2. Insert ausfuehren
				lngRowId = db.insert(this.strTableName, null, cvDish);
			}
		} catch (SQLiteException sqlEx) {
			Log.e(this.strTableName, sqlEx.getMessage() + "\n" + sqlEx.getStackTrace().toString());
		} finally {
			db.close();
		}
		
		return lngRowId;
	}
	
	/**
	 * Fuegt einen Datensatz in die Datenbanktablle ein
	 *
	 * @param db            : {@link SQLiteDatabase} : Datenbankobjekt vom {@link DbManager}
	 *                      zur Verfuegung gestellt
	 * @param tupleToInsert - {@link List}  : Liste von Models
	 *
	 * @return rowId : long : ZeilenID
	 */
	@Override
	public long insertTuple(SQLiteDatabase db, BaseModel tupleToInsert) {
		long lngRowId = -1;
		
		try {
			
			//1. Content Values generieren
			ContentValues cvDish = this.getContentValuesFromModel(tupleToInsert);
			
			//2. Insert ausfuehren
			lngRowId = db.insert(this.strTableName, null, cvDish);
			
		} catch (SQLiteException sqlEx) {
			Log.e(this.strTableName, sqlEx.getMessage() + "\n" + sqlEx.getStackTrace().toString());
		} finally {
			db.close();
		}
		
		return lngRowId;
	}
	
	/**
	 * Liest alle Datensaetze aus einer Tabelle
	 * aus und gibt diese als Liste von Models zurueck
	 *
	 * @param db : {@link SQLiteDatabase} : Datenbankobjekt vom {@link DbManager}
	 *           zur Verfuegung gestellt
	 *
	 * @return
	 */
	@Override
	public List<? extends BaseModel> getAllTuples(SQLiteDatabase db) {
		List<BongoNote> allBongoNotesFromTbl = new ArrayList<>();
		
		try {
			
			/*
			 * 2. Abfrage an Db stellen und Ergebnismenge speichern
			 *
			 * SQL-Statement was ausgefuert wird.
			 *
			 * SELECT * FROM tblDishes
			 */
			Cursor cTblBongoNoteResult = db.query(this.strTableName,
					null,
					null,
					null,
					null,
					null,
					null
			);
			
			//2. Ergebnismenge ausweten
			if (cTblBongoNoteResult != null) {
				
				//3. Jedes Tupel auslesen
				while (cTblBongoNoteResult.moveToNext()) {
					
					//4. Aus Cursor ein Dish fertigen
					BongoNote singleBongoNoteFromTbl = this.getModelFromCursor(cTblBongoNoteResult);
					
					//5. Model zur Liste hinzufuegen
					allBongoNotesFromTbl.add(singleBongoNoteFromTbl);
				}
			}
			
		} catch (SQLiteException sqlEx) {
			Log.e(this.strTableName, sqlEx.getMessage() + "\n" + sqlEx.getStackTrace().toString());
		} finally {
			db.close();
		}
		
		return allBongoNotesFromTbl;
	}
	
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
	@Override
	public BongoNote getTupleById(SQLiteDatabase db, int iId) {
		BongoNote singleBongoNoteFromTbl = null;
		
		try {
			
			/*
			 * 2. Abfrage an Db stellen und Ergebnismenge speichern
			 *
			 * SQL-Statement was ausgefuert wird.
			 *
			 * SELECT * FROM tblNotes WHERE _id = iId
			 * EXKLUSIVE WHERE Schluesselwort
			 */
			
			//_id = ?
			String strWhereClause = COL_NAME_PRIMARY_KEY + EQUALS_OPERATOR_INC_PLACE_HOLDER;
			
			//10
			String[] strWhereArgs = {String.valueOf(iId)};
			
			Cursor cTblBongoNoteResult = db.query(this.strTableName,
					null,
					strWhereClause,
					strWhereArgs,
					null,
					null,
					null
			);
			
			//2. Ergebnismenge ausweten
			if (cTblBongoNoteResult != null) {
				
				cTblBongoNoteResult.moveToFirst();
				
				//4. Aus Cursor ein Dish fertigen
				singleBongoNoteFromTbl = this.getModelFromCursor(cTblBongoNoteResult);
				
			}
			
		} catch (SQLiteException sqlEx) {
			Log.e(this.strTableName, sqlEx.getMessage() + "\n" + sqlEx.getStackTrace().toString());
		} finally {
			db.close();
		}
		
		return singleBongoNoteFromTbl;
	}
	
	/**
	 * Updaten ein bestimmtes Tupel in der Datenbank tabelle
	 *
	 * @param db        : {@link SQLiteDatabase} : Datenbankobjekt vom {@link DbManager}
	 *                  zur Verfuegung gestellt
	 * @param baseModel : {@link BaseModel} : Model zum updaten
	 *
	 * @return
	 */
	@Override
	public int updateTupleById(SQLiteDatabase db, BaseModel baseModel) {
		int countOfUpdatedRows = -1;
		
		try {
			
			//1. Content Values generieren
			ContentValues cvBongoNote = this.getContentValuesFromModel(baseModel);
			
			//2. WHERE-Clause festlegen _id =? EXKLUSIVE Schluesselwort WHERE
			String strWhereClause = COL_NAME_PRIMARY_KEY + EQUALS_OPERATOR_INC_PLACE_HOLDER;
			
			//3. WHERE-Argument festlegen _id = 5
			String[] strWhereArgs = {String.valueOf(baseModel.getId())};
			
			//2. Update ausfuehren
			countOfUpdatedRows = db.update(this.strTableName, cvBongoNote, strWhereClause, strWhereArgs);
			
		} catch (SQLiteException sqlEx) {
			Log.e(this.strTableName, sqlEx.getMessage() + "\n" + sqlEx.getStackTrace().toString());
		} finally {
			db.close();
		}
		
		return countOfUpdatedRows;
	}
	
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
	@Override
	public int deleteTupleById(SQLiteDatabase db, int iId) {
		//Decl. and Init
		int iCountOfRows = -1;
		
		try {
			
			//2. SQLiteStatement Objekt generieren DELETE * FROM tblNotes WHERE _id = iId
			SQLiteStatement stmtDelete = db.compileStatement(
					DELETE_FROM_TBL +
							this.strTableName +
							WHERE_CONDITION +
							COL_NAME_PRIMARY_KEY +
							EQUALS_OPERATOR_INC_PLACE_HOLDER
			);
			
			//3. WHERE-Argument
			String[] strWhereArgs = {String.valueOf(iId)};
			
			//4. Argumentwerte mit Statement verbinden
			stmtDelete.bindAllArgsAsStrings(strWhereArgs);
			
			//5.Anzahl der geleoschten Zeilen merken
			iCountOfRows = stmtDelete.executeUpdateDelete();
			
			//6.Closings
			stmtDelete.close();
			
			
		} catch (SQLiteException sqlEx) {
			Log.e(this.strTableName, sqlEx.getMessage() + "\n\n" + sqlEx.getStackTrace());
		}finally {
			db.close();
		}
		
		return iCountOfRows;
	}
	
	//endregion
	
	//region 4. Konvertierung
	@Override
	public ContentValues getContentValuesFromModel(BaseModel baseModel) {
		
		//1. Bongo note aus BaseModel casten
		BongoNote bongoNote = (BongoNote) baseModel;
		
		//2. ContentValues geneireren
		ContentValues cvBongoNote = new ContentValues();
		
		//3. Spaltenname und  Werte zusammenpacken
		cvBongoNote.put(COL_NAME_NAME, bongoNote.getName());
		cvBongoNote.put(COL_NAME_EDIT_DATE, bongoNote.getEditDate());
		cvBongoNote.put(COL_NAME_FKEY_PICTURE_ID, bongoNote.getBongoPictureId());
		cvBongoNote.put(COL_NAME_FKEY_LOCATION_ID, bongoNote.getBongoLocationId());
		cvBongoNote.put(COL_NAME_NOTE_CONTENT, bongoNote.getNoteContent());
		
		return cvBongoNote;
	}
	
	@Override
	public BongoNote getModelFromCursor(Cursor cursor) {
		
		BongoNote singleBongoNoteFromTbl = new BongoNote();
		
		/**
		 * 1. Daten aus der Ergebnismenge
		 * der Datenbank (Cursor) auslesen.
		 *
		 * Die Daten werden ueber den Datentyp
		 * der Spalte, dann durch Angabe des
		 * Spaltenindex angerufen. Diesen
		 * erhaelt man durch die Angabe des Spaltennamens
		 *
		 * Folgende Spaltenindizes sollen ausgelesen werden
		 *
		 * _id INTEGER PRIMARY KEY,<br>
		 * name TEXT,<br>
		 * editDate TEXT,<br>
		 * fkeyPictureId INTEGER,<br>
		 * fkeyLocationId INTEGER,<br>
		 * noteContent TEXT<br>
		 * );<br>
		 */
		int indexId              = cursor.getColumnIndex(COL_NAME_PRIMARY_KEY);
		int indexName            = cursor.getColumnIndex(COL_NAME_NAME);
		int indexEditDate        = cursor.getColumnIndex(COL_NAME_EDIT_DATE);
		int indexFkeyPictureId        = cursor.getColumnIndex(COL_NAME_FKEY_PICTURE_ID);
		int indexFkeyLocationId       = cursor.getColumnIndex(COL_NAME_FKEY_LOCATION_ID);
		int indexNoteContent        = cursor.getColumnIndex(COL_NAME_NOTE_CONTENT);
		
		
		/**
		 * 2.
		 * Daten ueber die Erbnismenge
		 * in die Bean eintragen.
		 * Die Werte werden immer ueber
		 * die getterFunktionen der Datentypen
		 * zurueckgeliefert. Diese wiederum
		 * erwarten den Spalten Index den wir oben
		 * ausgelesen war.
		 */
		singleBongoNoteFromTbl.setId(cursor.getInt(indexId));
		singleBongoNoteFromTbl.setName(cursor.getString(indexName));
		singleBongoNoteFromTbl.setEditDate(cursor.getString(indexEditDate));
		singleBongoNoteFromTbl.setBongoPictureId(cursor.getInt(indexFkeyPictureId));
		singleBongoNoteFromTbl.setBongoLocationId(cursor.getInt(indexFkeyLocationId));
		singleBongoNoteFromTbl.setNoteContent(cursor.getString(indexNoteContent));
		
		return singleBongoNoteFromTbl;
		
	}
	//endregion
}
