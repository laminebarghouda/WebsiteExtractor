package app.url;

import java.net.URL;


import java.io.*;

/**
 * Conteneur qui d�crit une URL.<br>
 * En plus d'une URL, cet objet contient la taille du document,
 * sa profondeur dans l'arbre de recherche
 * (voir les astuces), son type MIME.<br>
 * Ca permet de trier les URLs et aussi d'en �liminer certaines
 * (si, par exemple, on ne veut que les fichiers texte).
 *
 *  Date de cr�ation : (14/09/2000 11:00:17)
 * @author : Olivier Lecorn�
 */
public class URLCommentee implements Comparable, Serializable {
	public URL url = null;
	public int profondeur;
	//priv�s car calcul�s si besoin
	private int taille = -2;
	private String typeMime;
/**
 * Commentaire relatif au constructeur URLCommentee.
 */
public URLCommentee() {
	super();
}
/**
 * Commentaire relatif au constructeur URLCommentee.
 */
public URLCommentee(URL uneURL, int prof) {
	super();
	profondeur = prof;
	url = uneURL;
}


/**
 * Renvoi le nom du serveur de l'URL.
 */
public String getHost() {
	return url.getHost();
}
/**
	 * 
	 * @return  a string representation of the object.
	 */
public String toString() {
	return url.toString();
}
/**
* On compare la profondeur des URLs, puis leur taille.<br>
* On exploitera en priorit� les URL de moindre profondeur et 
* les documents les plus gros.
*
* @param   o the Object to be compared.
* @return  a negative integer, zero, or a positive integer as this object
* is less than, equal to, or greater than the specified object.
* 
* @throws ClassCastException if the specified object's type prevents it
*         from being compared to this Object.
*/
public int compareTo(java.lang.Object p_url) {
	int l_diff;
	if (!(p_url instanceof URLCommentee)) {
		//erreur
		throw new ClassCastException("On ne peut ajouter qu'une URL comment�e dans un TasUrl.");
	}
	URLCommentee l_url = (URLCommentee) p_url;
	//calcul
	l_diff = profondeur - l_url.profondeur;
	if (l_diff == 0) {
		l_diff = getTaille() - l_url.getTaille();
		if (l_diff == 0) {
			//cas de 2 docs de m�me taille
			//il ne faut pas dire qu'ils sont �gaux puisqu'ils ne le sont pas
			//au sens de equals => on va comparer les noms
			l_diff = url.toString().compareTo(l_url.toString());
		}
	}
	return l_diff;
}
public int getTaille() {
	if (taille == -2) {
		try {
			taille = url.openConnection().getContentLength();
		} catch (IOException e) {
			taille = -1;
		}
	}
	return taille;
}/**
 * Renvoi le type du document.
 */  
public String getTypeMIME() {
	if (typeMime == null) {
		try {
			typeMime = url.openConnection().getContentType();
		} catch (IOException e) {
			typeMime = "";
		}
	}
	return typeMime;
}/**
 * Renseigne le type du document.
 */  
public void setTypeMIME(String p_type) {
	typeMime = p_type;
}

	private final static long serialVersionUID = 8968850586501109768L;
}