package app.aspi;

import java.io.*;
import java.net.*;

import app.url.URLCommentee;
import app.Ecrivain;

/**
* A partir d'une URL, ouvre le document distant et l'analyse.
*/
public class Analyseur {
/**
 * Commentaire relatif au constructeur CopieurDeFichierInternet.
 */
public Analyseur() {
	super();
}

/**
 * Examine le document désigné par l'URL spécifiée.
 * Si le document respecte les critères de l'aspirateur
 * (taille et type), renseigne la taille et le type du document
 * puis renvoit la connexion.
 * Sinon, ferme la connexion et signale une exception.
 * @return java.net.URLConnection
 * @exception app.aspi.SuceurException La description de l'exception.
 */
public URLConnection analyse(
	URLCommentee uneURLc, 
	CriteresAspirateur criteres)
	throws SuceurException {
	URLConnection reponse_ = null;
	//pour l'instant, renvoie tjs ok
	try {
		reponse_ = uneURLc.url.openConnection();
		uneURLc.setTypeMIME(reponse_.getContentType());
		//trace
		Ecrivain.traceDetail("Analyseur", "taille : " + uneURLc.getTaille());
		if (uneURLc.getTypeMIME() != null)
			Ecrivain.traceDetail(
				"Analyseur", 
				"type MIME : " + uneURLc.getTypeMIME()); 
		if (!criteres.accepteTypeMime(uneURLc.getTypeMIME()))
			throw new SuceurException(
				"! Type "
					+ uneURLc.getTypeMIME()
					+ " refusé pour "
					+ uneURLc.url.toString()); 
	} catch (IOException e) {
		throw new SuceurException("Pb d'ouverture de l'URL " + uneURLc);
	}
	return reponse_;
}
}