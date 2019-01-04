package de.rkasper.rkbongoapp.gui.listview;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * ViewHolder fuer die einzelenen
 * Widgets eines ListViewItems
 * generiert vom ListViewAdapter
 */
public class ViewHolder {
	//region 0. Konstanten
	//endregion
	
	//region 1. Decl. and Init Attribute
	/**
	 * Zeigt ein Vorschaubild der {@link de.rkasper.rkbongoapp.model.BongoNote} an.
	 * Sollte keines gesetze sein so wird ein Standardbild angezeigt
	 */
	private ImageView imgvBongoNoteThumbnail;
	
	/**
	 * Zeigt Notizinfos
	 */
	private TextView txtvBongoNoteInfo;
	//endregion
	
	//region 2. Konstruktoren
	
	/**
	 * Standardkonstruktor zum direkten setzen der Widgets eines einzelene ListViewItems
	 *
	 * @param imgvBongoNoteThumbnail : {@link ImageView} : Vorschaubild
	 * @param txtvBongoNoteInfo      : {@link TextView} : Infos
	 */
	public ViewHolder(ImageView imgvBongoNoteThumbnail, TextView txtvBongoNoteInfo) {
		this.imgvBongoNoteThumbnail = imgvBongoNoteThumbnail;
		this.txtvBongoNoteInfo = txtvBongoNoteInfo;
	}
	
	//endregion
	
	//region 3. Getter und Setter
	
	public ImageView getImgvBongoNoteThumbnail() {
		
		return imgvBongoNoteThumbnail;
	}
	
	public TextView getTxtvBongoNoteInfo() {
		return txtvBongoNoteInfo;
	}
	
	//endregion
	
	//region 4. Stringfunktionen
	//endregion
}
