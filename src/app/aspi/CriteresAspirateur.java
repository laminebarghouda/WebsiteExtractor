package app.aspi;

import app.url.URLCommentee;
import java.io.Serializable;

/**
* Centralise les critères de recherche de l'aspirateur.
* Permet à tous les composants de contrôler la légalité
* de leurs opérations.
* @author : Administrator
*/
public class CriteresAspirateur implements Serializable {
	//serveur
	boolean resteSurServeurInitial = true;
	//JPEG
	int tailleMinJpeg = 0;
	int tailleMaxJpeg = 300;
	boolean accepteJpeg = false;
	//GIF
	boolean accepteGIF = false;
	int tailleMinGif = 0;
	int tailleMaxGif = 300;
/**
 * Commentaire relatif au constructeur CriteresAspirateur.
 */
public CriteresAspirateur() {
	super();
}
/**
 * Indique à l'ensemble que les GIF sont acceptés
 * @param min int
 * @param max int
 */
public void accepterGIF(int min, int max) {
	accepteGIF = true;
	tailleMinGif = min;
	tailleMaxGif = max;
}
/**
 * Indique à l'ensemble que les JPEG sont acceptés
 * @param min int
 * @param max int
 */
public void accepterJPEG(int min, int max) {
	accepteJpeg = true;
	tailleMinJpeg = min;
	tailleMaxJpeg = max;
}
/**
 * Renvoit vrai si un document du type MIME spécifié peut être aspiré.
 * @return boolean
 * @param unTypeMIME java.lang.String
 */
public boolean accepteTypeMime(String unTypeMIME) {
	return true;
}
/**
 * This method was created in VisualAge.
 * @return boolean
 */
public boolean doitResterSurServeurInitial() {
	return resteSurServeurInitial;
}
/**
 * Indique à l'ensemble qu'il ne faut pas accepter les GIF
 */
public void refuserGIF() {
	accepteGIF=false;
}
/**
 * Indique à l'ensemble qu'il ne faut pas accepter les JPEG
 */
public void refuserJPEG() {
	accepteJpeg=false;
}
/**
 * Indique à l'ensemble de refuser les URL d'un autre serveur
 * @param memeServeur boolean
 */
public void resterSurMemeServeur(boolean memeServeur) {
	setResteSurServeurInitial(memeServeur);
}
/**
 * This method was created in VisualAge.
 * @param newValue boolean
 */
private void setAccepteGIF(boolean newValue) {
	this.accepteGIF = newValue;
}
/**
 * This method was created in VisualAge.
 * @param newValue boolean
 */
private void setAccepteJpeg(boolean newValue) {
	this.accepteJpeg = newValue;
}
/**
 * This method was created in VisualAge.
 * @param newValue boolean
 */
private void setResteSurServeurInitial(boolean newValue) {
	this.resteSurServeurInitial = newValue;
}
/**
 * renvoie vrai si l'URL répond aux critères de recherche.
 */
public boolean accepteUrlCommentee(URLCommentee p_urlc) {
	return true;
}

	//nb de moteurs (1 par défaut)
	private int i_nbMoteurs = 1;
	private final static long serialVersionUID = -5767204267857239241L;

/**
 * Ins�rez la description de la méthode à cet endroit.
 * @return int
 */
public int getNbMoteurs() {
	return i_nbMoteurs;
}

/**
 * Insérez la description de la méthode à cet endroit.
 * @param newI_nbMoteurs int
 */
void setNbMoteurs(int newI_nbMoteurs) {
	i_nbMoteurs = newI_nbMoteurs;
}
}