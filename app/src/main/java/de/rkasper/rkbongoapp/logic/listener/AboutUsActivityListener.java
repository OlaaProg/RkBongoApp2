package de.rkasper.rkbongoapp.logic.listener;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import de.rkasper.rkbongoapp.R;
import de.rkasper.rkbongoapp.gui.AboutUsActivity;
import de.rkasper.rkbongoapp.gui.BongoNoteCrudActivity;
import de.rkasper.rkbongoapp.gui.MainActivity;

public class AboutUsActivityListener extends ABaseListener implements View.OnClickListener {
	
	//region 0. Konstanten
	//endregion
	
	//region 1. Decl. and Init Attribute
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
	public AboutUsActivityListener(AppCompatActivity currentActivity) {
		super(currentActivity);
		
		generateWidgetReferences();
	}
	//endregion
	
	//region 3. Klickhandling
	/**
	 * Wird aufgerufen wenn ein Widget
	 * welches diesen Listener in der
	 * {@link AboutUsActivity} zugewiesen
	 * bekommen hat, geklickt wird
	 *
	 * @param v : {@link View} : Geklickte Widget
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.btnEmail:
				//1. Implizitens Intent
				Intent intentSendEmail = new Intent();
				
				//2. Aktion was moechte ich mit dem Intent machen, etwas senden
				intentSendEmail.setAction(Intent.ACTION_SEND);
				
				//3.Email Addressen im String Array eintragen und Betreff festlegen
				String[] strEmailAddresses = {this.currentActivity.getString(R.string.strDefaultEmailAddressToSendTo)};
				String strEmailSubject = this.currentActivity.getString(R.string.strDefaultEmailSubject);
				String strDataTypToSend = this.currentActivity.getString(R.string.strDataTypToSend);
				String strShareDialogTitle = this.currentActivity.getString(R.string.strEmailText);
				
				//5.Extras Email und Betreff mit passenden Konstanten an das Intent haengen
				intentSendEmail.putExtra(Intent.EXTRA_EMAIL, strEmailAddresses);
				intentSendEmail.putExtra(Intent.EXTRA_SUBJECT, strEmailSubject);
				
				//6. Was moechten wir senden, text
				intentSendEmail.setType(strDataTypToSend);
				
				//7. Intent starten Intent createCHooser sucht aus welcher Dialog zu zeigen ist.
				this.currentActivity.startActivity(Intent.createChooser(intentSendEmail, strShareDialogTitle));
			break;
			case R.id.btnCallHotline:
				//1. Nummer aus der res/values/strings.xml auslesen, muss vordefiniert werden.
				String strHotlineNumber = this.currentActivity.getString(R.string.strHotlineNumber);
				
				//2. Implizites Intent generieren mit Konstante ACTION_DAIL fuer Waheldialog
				Intent intentCallDialog = new Intent(Intent.ACTION_DIAL, Uri.parse(strHotlineNumber));
				
				//3. Starten des Dialogs
				this.currentActivity.startActivity(intentCallDialog);
				break;
			case R.id.btnVisitWebside:
				//1. Webside Url der res/values/strings.xml auslesen, muss vordefiniert werden.
				String strWebsideUrl = this.currentActivity.getString(R.string.strWebSideUrl);
				
				//2. Implizites Intent generieren mit Konstante ACTION_VIEW und gueltier Url
				Intent intentOpenWebside = new Intent(Intent.ACTION_VIEW, Uri.parse(strWebsideUrl));
				
				//3. Starten des Standardbrowsers
				this.currentActivity.startActivity(intentOpenWebside);
				
				break;
		}
	}
	
	//region Hilfsmethoden und funktionen
	@Override
	public void generateWidgetReferences() {
	//TODO spaeter
	}
	//endregion
}
