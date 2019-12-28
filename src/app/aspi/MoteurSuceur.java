package app.aspi;

import java.net.*;
import java.io.*;
import java.util.*;


import app.url.URLCommentee;

import app.Ecrivain;

import app.html.analyseur.*;

/**
* Moteur du suceur de sites
* Appelé pour une URL, il va la traiter complétement
* (copie du document et extraction des URLs)
* puis il s'arrête
*/
public class MoteurSuceur implements Runnable {
	//
	private RecuperateurLienHypertexte extracteur;
	private CopieurDeFichierInternet lecteur;
	private java.lang.String identifiant = "moteur";
	private TasUrl fournisseurURL;

	//ensemble dans lequel le moteur doit se replacer lorsqu'il a terminé
	private Vector garage = new Vector();
/**
 * Rien de particulier ...
 */
public MoteurSuceur() {
	super();
	extracteur = new ExtracteurLienHypertexte();
	lecteur = new CopieurDeFichierInternet();
	localisateur = new Localisateur();
	analyseur = new Analyseur();
}



/**
 * Insérez la description de la méthode à cet endroit.
 * @return AspirateurDeSites.MoteurSuceur[]
 */
public Vector getGarage() {
	return garage;
}
/**
 * Insérez la description de la méthode à cet endroit.
 *  Date de création : (12/09/2000 14:41:09)
 * @return java.lang.String
 */
public java.lang.String getIdentifiant() {
	return identifiant;
}

/**
 * Le moteur a été initialisé avec l'URL qu'il doit traiter.
 * Cette m�thode lance le traitement.
 */
public void run() {
	urlC = null;
	while (!arretDemande) {
		//trace
		Ecrivain.traceDetail(getIdentifiant(), " demande URL");
		urlC = fournisseurURL.lireURLSuivante();
		if (urlC != null) { //securite
			//TRACE
			Ecrivain.traceDetail(getIdentifiant(), " a lu " + urlC);
			traiter();
		}
	}
	Ecrivain.trace(getIdentifiant() + " est arrété.");
	arretDemande = false;
}

/**
 * Insérez la description de la méthode à cet endroit.
 * @param newGarage AspirateurDeSites.MoteurSuceur[]
 */
public void setGarage(Vector newGarage) {
	garage = newGarage;
}
/**
 * Insérez la description de la méthode à cet endroit.
 * @param newIdentifiant java.lang.String
 */
public void setIdentifiant(java.lang.String newIdentifiant) {
	identifiant = newIdentifiant;
}



	/** analyseur */
	public Analyseur analyseur; /** critères de l'aspirateur */
	CriteresAspirateur criteres; /** localisateur */
	public Localisateur localisateur;				public URLCommentee urlC;
/**
* Traite l'URL récupérée dans le tas:
* <ol><li>appeler localisateur
*<li>appeler l'analyseur
*<li>appeler le copieur
*<li>appeler l'extracteur
* </ol>
* Tout ça s'il n'il y a pas de contre-indication...
*/
public void traiter() {
	String nomFichierComplet = null;
	URLConnection connexion = null;
	//trace
	Ecrivain.trace(
		getIdentifiant()
			+ ": "
			+ urlC.profondeur
			+ " "
			+ urlC.getTaille()
			+ " "
			+ urlC.url.toString());
	try {
		//localisateur
		nomFichierComplet = localisateur.nomLocalDe(urlC.url);
		//copie si le fichier n'existe pas déjà
		if (!(new File(nomFichierComplet)).exists()) {
			//analyseur
			connexion = analyseur.analyse(urlC, criteres);
			//copie
			lecteur.copier(
				connexion,
				nomFichierComplet,
				urlC.getTaille(),
				urlC.getTypeMIME());
		} else {
			//trace
			//Ecrivain.traceAnomalie(getIdentifiant(), nomFichierComplet + " existe déjà");
		}
		//extraire les liens
		if (urlC.getTypeMIME().startsWith("text")) {
			extracteur.extraire(nomFichierComplet, fournisseurURL, urlC);
		} else {
			Ecrivain.traceDetail(getIdentifiant(), "pas d'extraction pour " + urlC);
		}
	} catch (Exception e) {
		//trace
		Ecrivain.traceAnomalie(getIdentifiant(), e.toString());
	}
}

	/** demande d'arrêt du moteur */
	private boolean arretDemande = false;

/**
 * Commentaire relatif au constructeur MoteurSuceur.
 */
public MoteurSuceur(URLCommentee urlDepart, TasUrl stock) {
	super();
	urlC = urlDepart;
	fournisseurURL = stock;
	extracteur = new ExtracteurLienHypertexte();
	//extracteur = new ExtracteurURL();
	lecteur = new CopieurDeFichierInternet();
}

/**
 * Arréte le moteur qui termine le traitement de l'URL en cours.
 */
public void arreter() {
	arretDemande = true;
}

/**
 * Insérez la description de la méthode à cet endroit.
 * @return AspirateurDeSites.EnsembleURLTrie
 */
public TasUrl getFournisseurURL() {
	return fournisseurURL;
}

/**
 * Insérez la description de la méthode à cet endroit.
 * @param newFournisseurURL AspirateurDeSites.EnsembleURLTrie
 */
public void setFournisseurURL(TasUrl newFournisseurURL) {
	fournisseurURL = newFournisseurURL;
}
}