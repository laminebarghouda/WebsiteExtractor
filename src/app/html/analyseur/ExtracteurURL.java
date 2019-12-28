package app.html.analyseur;

import java.io.*;
import java.net.*;

import java.util.Collection;
import app.url.URLCommentee;

/**
 * Mon parser HTML.
 * Il extrait les liens hypertexte d'un document HTML.
 * Moins sophistiqué que celui fourni avec le JDK (voir la classe
 * ExtracteurLienHypertexte), je le souponne d'être plus rapide.
*/
public class ExtracteurURL implements RecuperateurLienHypertexte {
/**
 * Commentaire relatif au constructeur ExtracteurURL.
 */
public ExtracteurURL() {
	super();
}
/**
 * Coupe l'ancre si elle existe
 * Sinon, renvoie la chaîne
 */
private String couperAncre(String uneChaineURL) {
	//l'URL contient-elle un '#' ?
	int posDiese = uneChaineURL.indexOf("#");
	if (posDiese != -1) {
		return uneChaineURL.substring(0, posDiese);
	} else {
		return uneChaineURL;
	}
}


/**
 * Affiche une adresse électronique s'il y en a une.
 * @param ligne java.lang.String
 * @exception AspirateurDeSites.SuceurException The exception description.
 */
private void extraireAdresseElectronique(
	java.lang.String ligne, 
	String urlOriginale) {
	String ligneTravail = null;
	//'adresse' contient le r�sultat du traitement, 'null' si la ligne ne contient pas d'url
	String adresse = null;
	//on coupe la partie de la ligne avant 'mailto:'
	int indexMailto = ligne.toUpperCase().indexOf("MAILTO:");
	if (indexMailto != -1) {
		ligneTravail = ligne.substring(indexMailto + 7);
		//l'adresse doit se terminer par une double quote
		int fin = ligneTravail.indexOf("\"");
		if (fin != -1) {
			//on coupe avant la double quote
			adresse = ligneTravail.substring(0, fin);
			System.out.println(adresse);
		} else { //double quote de fin PAS trouv�e
			System.out.println(
				"Pb détection d'adresse électronique "
					+ urlOriginale
					+ " : "
					+ ligneTravail); 
		}
	}
}
/**
 * Extraire une URL qui commence par 'http://'
 */
private String extraireUrlAbsolue(String ligneEntiere, int indexProtocole, URL urlOriginale) {
	//l'URL d�tect�e est construite dans 'ligneTravail'
	//ATTENTION : mieux vaut ne pas construire directement dans 'urlDetectee'
	//en cas d'erreur, 'urlDetectee' reste à null
	String ligneTravail = null;
	//'urlDetectee' contient le résultat du traitement, 'null' si la ligne ne contient pas d'url
	String urlDetectee = null;

	//on coupe la partie de la ligne avant 'http://'
	ligneTravail = ligneEntiere.substring(indexProtocole);
	//l'URL doit se terminer par une double quote
	int fin = ligneTravail.indexOf("\"");
	if (fin != -1) {
		//on coupe avant la double quote
		urlDetectee = ligneTravail.substring(0, fin);
	} else { //double quote de fin PAS trouvée
		System.out.println("Pb d�tection URL ('http') dans " + urlOriginale + " : " + ligneTravail);
	}
	return couperAncre(urlDetectee);
}
/**
 * 
 */
static Reader getReader(String url) throws IOException {
	if (url.startsWith("http:")) {
		System.out.println("ANOMALIE : ce cas ne devrait pas se produire");
		URLConnection conn = new URL(url).openConnection();
		return new InputStreamReader(conn.getInputStream());
	} else {
		return new FileReader(url);
	}
}
/**
 * Cas des URL relatives : il faut rajouter la racine de l'URL d'origine
 */
private String reconstruireURL(String urlRelative, URL urlOriginale) {
	String urlComplete = "";
	if (urlRelative.startsWith("..")) {
		urlComplete = reconstruireURLRelativeAuParent(urlRelative, urlOriginale);
	} else {
		String origine = urlOriginale.toString();
		String ligneTravail = urlRelative.toUpperCase();
		//cas d'une URL relative commençant par un "/"
		//   => concaténer le nom du host
		if (urlRelative.charAt(0) == '/') {
			urlComplete = "http://" + urlOriginale.getHost() + urlRelative;
		} else {
			//cas d'une URL relative ne commen�ant pas par un "."
			//   => concat�ner l'url originale coup�e après le dernier "/"
			if (!urlRelative.startsWith(".")) {
				// le +1 parce qu'on garde le dernier "/"
				urlComplete = origine.substring(0, origine.lastIndexOf("/") + 1) + urlRelative;
			} else {
				//autres cas
				//
				//enlever le point
				if (ligneTravail.charAt(0) == '.')
					ligneTravail = urlRelative.substring(1);
				//ajouter le / � l'origine
				int fin = origine.lastIndexOf('/');
				if (fin != origine.length() - 1)
					origine = origine + "/";
				//concatener
				//TRACE
				//	System.out.println("Origine = " + origine);
				//FIN TRACE
				urlComplete = origine + ligneTravail;
			}
		}
	}
	return urlComplete;
}
/**
 * Cas des URL relatives de la forme "../toto.htm"
 * Exemple:
 *	relative = ../rel
 *	origine = http://serveur/rep/page
 *	absolue = http://serveur/rel
 */
private String reconstruireURLRelativeAuParent(String urlRelative, URL urlOriginale) {
	String urlComplete = "";
	String origine = urlOriginale.toString();
	String ligneTravail = urlRelative.toUpperCase();
	//couper dans l'URL d'origine :
	//	- la page
	//	- le r�pertoire
	int fin = origine.lastIndexOf('/');
	//couper la page
	origine = origine.substring(0, origine.lastIndexOf("/"));
	//couper le répertoire
	origine = origine.substring(0, origine.lastIndexOf("/"));
	//concaténer l'URL relative sans les deux points '..'
	urlComplete = origine + urlRelative.substring(2);
	return urlComplete;
}



/**
 * Extraire d'un fichier local les URL tout en connaissant l'URL originale du document.
 * Le traitement se fait ligne par ligne (on ne traite pas le cas ou l'URL se trouve sur 2 lignes).
 * Si le fichier n'est pas un document html, on ne le traite pas
 */
public void extraire(
	String nomFichierComplet, 
	Collection ensemble, 
	URLCommentee urlOriginale) {
	BufferedReader fluxIN = null;
	if (nomFichierComplet.toUpperCase().endsWith("HTM")
		|| nomFichierComplet.toUpperCase().endsWith("HTML")) {
		//ouvrir le fichier
		try {
			fluxIN = 
				new BufferedReader(new FileReader(new File(nomFichierComplet))); 
		} catch (FileNotFoundException e) {
			System.out.println(
				"Erreur lors de la lecture en local de "
					+ nomFichierComplet
					+ " --> "
					+ e); 
			return;
		}
		//lecture et traitement des lignes
		String ligne;
		try {
			while ((ligne = fluxIN.readLine()) != null) {
				//extraire de la ligne
				try {
					traiterLigne(ligne, ensemble, urlOriginale);
				} catch (Exception e) {
					System.out.println(
						"Erreur lors de l'analyse de la ligne "
							+ ligne
							+ " fu fichier "
							+ nomFichierComplet
							+ " --> "
							+ e); 
				}
				//extraire une adresse �lectronique
				//extraireAdresseElectronique(ligne);
			}
		} catch (Exception e) {
			System.out.println(
				"Erreur lors de l'analyse de " + nomFichierComplet + " --> " + e); 
		} finally {
			try {
				fluxIN.close();
			} catch (IOException e) {
			}
		}
	}
}/**
 * Extraire l'URL d'un hyper-lien.
 * 
 */  
private void traiterHref(
	String l, 
	Collection ensemble, 
	URLCommentee urlOriginale) {
	//l'URL détectée est construite dans 'ligneTravail'
	String ligneTravail = l;
	//'ligneTravail2' sert d'intermédiaire si besoin
	String ligneTravail2;
	//'urlDetectee' contient le résultat du traitement, 'null' si la ligne ne contient pas d'url
	String urlDetectee = null;
	// cas : href="/models/mishel"
	int debut = ligneTravail.toUpperCase().indexOf("HREF");
	if (debut != -1) {
		ligneTravail2 = ligneTravail.substring(debut);
		//la premi�re double quote
		debut = ligneTravail2.indexOf("\"");
		if (debut != -1) {
			ligneTravail = ligneTravail2.substring(debut + 1);
			//l'autre quote
			int fin = ligneTravail.indexOf("\"");
			if ((fin != -1) && (fin != 0) /*cas particulier 'href=""'*/
				) {
				ligneTravail2 = ligneTravail.substring(0, fin);
				//il y en a peut-être une autre sur la même ligne
				traiterLigne(ligneTravail.substring(fin), ensemble, urlOriginale);
				//
				urlDetectee = 
					couperAncre(reconstruireURL(ligneTravail2, urlOriginale.url)); 
			} else { //double quote de fin PAS trouvée
				System.out.println(
					"Pb d�tection URL ('href') dans: "
						+ urlOriginale.url
						+ " : "
						+ ligneTravail); 
				return;
			}
		}
	}
	if (urlDetectee != null) {
		//TRACE
		//System.out.println("URL http détectée : " + ligneTravail);
		//FIN TRACE
		try {
			ensemble.add(
				new URLCommentee(new URL(urlDetectee), urlOriginale.profondeur + 1)); 
			return;
		} catch (MalformedURLException e) {
			System.out.println(
				"L'URL détectée est mal formée: " + urlDetectee + " --> " + e);
		}
	}
}/**
 * Extraire les URL qui se trouvent dans une ligne, sachant qu'elle est l'URL du document original.
 * 
 */  
private void traiterLigne(
	String l, 
	Collection ensemble, 
	URLCommentee urlOriginale) {
	//l'URL d�tect�e est construite dans 'ligneTravail'
	String ligneTravail = l;
	//'ligneTravail2' sert d'intermédiaire si besoin
	String ligneTravail2;
	//'urlDetectee' contient le résultat du traitement, 'null' si la ligne ne contient pas d'url
	String urlDetectee = null;
	//
	// cas SRC
	//
	traiterSRC(l, ensemble, urlOriginale);
	//
	// cas HREF
	//
	traiterHref(l, ensemble, urlOriginale);
	//
	// autres cas
	//
	int debut = ligneTravail.toUpperCase().indexOf("HTTP://");
	if (debut != -1) {
		//URL absolue (commence par 'http://')
		urlDetectee = 
			extraireUrlAbsolue(ligneTravail, debut, urlOriginale.url); 
	}
	if (urlDetectee != null) {
		//TRACE
		//System.out.println("URL http détectée : " + ligneTravail);
		//FIN TRACE
		try {
			ensemble.add(
				new URLCommentee(new URL(urlDetectee), urlOriginale.profondeur + 1)); 
			return;
		} catch (MalformedURLException e) {
			System.out.println(
				"L'URL détectée est mal form�e: " + urlDetectee + " --> " + e);
		}
	}
}/**
 * Extraire l'URL d'une image ou d'une frame.
 * 
 */  
private void traiterSRC(
	String l, 
	Collection ensemble, 
	URLCommentee urlOriginale) {
	//l'URL détectée est construite dans 'ligneTravail'
	String ligneTravail = l;
	//'ligneTravail2' sert d'intermédiaire si besoin
	String ligneTravail2;
	//'urlDetectee' contient le résultat du traitement, 'null' si la ligne ne contient pas d'url
	String urlDetectee = null;
	// cas : <IMG SRC="/modindex.htm" >
	int debut = ligneTravail.toUpperCase().indexOf("SRC");
	if (debut != -1) {
		ligneTravail2 = ligneTravail.substring(debut);
		//la première double quote
		debut = ligneTravail2.indexOf("\"");
		if (debut != -1) {
			ligneTravail = ligneTravail2.substring(debut + 1);
			//l'autre quote
			int fin = ligneTravail.indexOf("\"");
			if (fin != -1) {
				ligneTravail2 = ligneTravail.substring(0, fin);
				//il y en a peut-être une autre sur la même ligne
				traiterLigne(ligneTravail.substring(fin), ensemble, urlOriginale);
				//
				urlDetectee = 
					couperAncre(reconstruireURL(ligneTravail2, urlOriginale.url)); 
			} else { //double quote de fin PAS trouvée
				System.out.println(
					"Pb détection URL ('src') dans: "
						+ urlOriginale.url
						+ " : "
						+ ligneTravail); 
				return;
			}
		}
	}
	if (urlDetectee != null) {
		//TRACE
		//System.out.println("URL http détectée : " + ligneTravail);
		//FIN TRACE
		try {
			ensemble.add(
				new URLCommentee(new URL(urlDetectee), urlOriginale.profondeur + 1)); 
			return;
		} catch (MalformedURLException e) {
			System.out.println(
				"L'URL détectée est mal formée: " + urlDetectee + " --> " + e);
		}
	}
}
}