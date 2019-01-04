package de.rkasper.rkbongoapp.gui.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.rkasper.rkbongoapp.R;
import de.rkasper.rkbongoapp.model.BongoNote;

public class ListViewAdapter extends BaseAdapter {
	
	//region 0. Konstanten
	//endregion
	
	//region 1. Decl. and Init Attribute
	
	/**
	 * Aktuelle Activity damit der
	 * LayoutInflater geniert werden kann.
	 */
	private Context context;
	
	/**
	 * Erstellt das ListViewItem
	 */
	private LayoutInflater layoutInflater;
	
	/**
	 * Datenliste von {@link BongoNote}s
	 */
	private List<BongoNote> bongoNoteList;
	//endregion
	
	//region 2. Konstruktoren
	
	public ListViewAdapter(Context context, List<BongoNote> bongoNoteList) {
		this.context = context;
		this.layoutInflater = LayoutInflater.from(this.context);
		this.bongoNoteList = bongoNoteList;
	}
	
	//endregion
	
	//region 3.Listenfunktionen
	
	@Override
	public int getCount() {
		return this.bongoNoteList.size();
	}
	
	@Override
	public Object getItem(int index) {
		return this.bongoNoteList.get(index);
	}
	
	@Override
	public long getItemId(int index) {
		return this.bongoNoteList.get(index).getId();
	}
	
	//endregion
	
	//region 4. GetView
	
	@Override
	public View getView(int index, View listViewItem, ViewGroup listView) {
		ViewHolder viewHolder;
		
		if (listViewItem == null) {
			
			listViewItem = this.layoutInflater
					.inflate(R.layout.list_view_item_layout, listView, false);
			
			ImageView imgvBongoNoteThumbnail = listViewItem
					.findViewById(R.id.imgvBongoNoteThumbnail);
			
			TextView txtvBongoNoteInfo = listViewItem.findViewById(R.id.txtvBongoNoteInfo);
			
			viewHolder = new ViewHolder(imgvBongoNoteThumbnail, txtvBongoNoteInfo);
			
			listViewItem.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) listViewItem.getTag();
		}
		
		BongoNote bongoNote = (BongoNote) this.getItem(index);
		
		//TODO Spaeter Thumnail auslesen und anzeigen
		TextView txtvBongoNoteInfo = viewHolder.getTxtvBongoNoteInfo();
		txtvBongoNoteInfo.setText(bongoNote.getEditDate() + "\n" + bongoNote.getName());
		
		return listViewItem;
	}
	
	//endregion
}
