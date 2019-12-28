package app.aspi;

import java.net.URL;

/**
 * Traduit une URL compléte en un nom de fichier local.<br>
 * A partir d'une URL et d'un répertoire sur le disque local
 * il construit le nom d'un fichier local.
 * C'est ce nom que l'on passe au copieur
 * pour lui indiquer ou l'on veut copier le fichier distant.
 * Le localisateur respecte l'arborescence du site aspiré.
 *
 */
public class Localisateur {
	/** répertoire de base */
	public String repertoire = "d:/abdelghani/temp";
/**
 * Commentaire relatif au constructeur Localisateur.
 */
public Localisateur() {
	super();
}
/**
 * Ca marche mais je sais plus comment :-(
 */
public String nomLocalDe(URL url) {
	int index;
	String urlChaine = url.toString();
	// cas www.soleri.com/
	if (urlChaine.endsWith("/")) {
		//premier "/" pour enlever le protocole
		index = urlChaine.indexOf('/');
		urlChaine = urlChaine.substring(index + 1);
		//nom serveur + index.htm
		urlChaine = urlChaine + "index.htm";
		if (urlChaine.startsWith("/"))
			urlChaine = repertoire + urlChaine;
		else
			urlChaine = repertoire + "/" + urlChaine;
	} else {
		urlChaine = repertoire + "/" + url.getHost() + url.getFile();
	}
	urlChaine = urlChaine.replace('/', '\\');
	return urlChaine;
}
}