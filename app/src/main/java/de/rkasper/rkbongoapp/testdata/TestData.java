package de.rkasper.rkbongoapp.testdata;

import java.util.ArrayList;
import java.util.List;

import de.rkasper.rkbongoapp.model.BongoNote;

public class TestData {
	
	//region 0. Konstanten
	private static final int MAX_COUNT_TEST_ELEMENTES = 18;
	//endregion
	
	//region 1. Decl. and Init Attribute
	//endregion
	
	//region 2. Konstruktoren
	
	/**
	 * Standardkonstruktor
	 * ist extra privat so das von
	 * ausserhalb dieser Klasse
	 * kein Objekt angelegt werden kann.
	 */
	private TestData() {
		//Nichts zu tun ausser privat zu sein
	}
	//endregion
	
	//region 3. TestBongoNotes
	
	
	/**
	 * Gibt eine Testliste von {@link BongoNote} zureuck
	 * @return testBongoNoteList : {@link List} : {@link BongoNote} : Testnotizen
	 */
	public static synchronized List<BongoNote> getTestBongoNoteList() {
		List<BongoNote> testBongoNoteList = new ArrayList<>();
		
		
		for (int index = 0; index < MAX_COUNT_TEST_ELEMENTES; index++) {
			testBongoNoteList.add(new BongoNote(
					index,
					"19.12.2018",
					"Notizname " + String.valueOf(index),
					"Notizinhalt"));
		}
		
		return testBongoNoteList;
	}
	//endregion
}
