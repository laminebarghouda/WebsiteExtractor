package app.aspi;

import java.io.*;
import java.net.*;

import app.Ecrivain;

/**
 * A partir d'une URL et d'un nom de fichier, recopie un document distant
 * sur le disque local.<br>
 */
public class CopieurDeFichierInternet {

/**
 * Commentaire relatif au constructeur CopieurDeFichierInternet.
 */
public CopieurDeFichierInternet() {
	super();
}

/**
 * Méthode de copie ligne par ligne
 */
private void copierParLigne(URLConnection docDistant, File fichierLocal) throws IOException, SuceurException {
	String ligne;
	BufferedReader fluxIN = new BufferedReader(new InputStreamReader(docDistant.getInputStream()));
	BufferedWriter fluxOUT = new BufferedWriter(new FileWriter(fichierLocal));
	while ((ligne = fluxIN.readLine()) != null) {
		fluxOUT.write(ligne);
		fluxOUT.newLine();
	}
	fluxOUT.close();
	fluxIN.close();
}
/**
 * Méthode de copie octet par octet
 */
private void copierParOctet(URLConnection docDistant, File fichierLocal) throws IOException, SuceurException {
	int entier = 0;
	InputStream fluxIN = docDistant.getInputStream();
	BufferedOutputStream fluxOUT = new BufferedOutputStream(new FileOutputStream(fichierLocal));
	while ((entier = fluxIN.read()) != -1) {
		fluxOUT.write(entier);
	}
	fluxOUT.close();
	fluxIN.close();
}
/**
 * 
 */
public File creerFichier(String nomFichierComplet) throws SuceurException {

	//créer le ou les répertoires
	String rep = nomFichierComplet.substring(0,nomFichierComplet.lastIndexOf('\\'));
	creerRepertoires(rep);
	//créer le fichier
	return new File(nomFichierComplet);
}
/**
 * 
 */
public void creerRepertoires(String repertoires) throws SuceurException {
	if (!(new File(repertoires)).exists()) {
		if (!(new File(repertoires)).mkdirs()) {
			Ecrivain.traceAnomalie(
				"Copieur",
				"ERREUR lors de la création des répertoires " + repertoires);
			//"ERREUR lors de la cr�ation des r�pertoires " + repertoires);
			throw new SuceurException(
				"ERREUR lors de la création des répertoires " + repertoires);
		}
	}
}
/**
 * Méthode principale
 */
public void copier(
	URLConnection connexion, 
	String nomFichierComplet, 
	long taille, 
	String typeMIME)
	throws IOException, SuceurException {
	//en premier, créer le fichier et les répertoires nécessaires
	File f = creerFichier(nomFichierComplet);
	//si le fichier n'existe pas d�j�
	if (!f.exists()) {
		//copier...
		Ecrivain.traceDetail(
			"copieur", 
			connexion.getURL().toString() + " - " + connexion.getContentLength()); 
		if (typeMIME != null && typeMIME.startsWith("text")) {
			//soit ligne par ligne
			copierParLigne(connexion, f);
		} else {
			//soit par octet
			copierParOctet(connexion, f);
		}
	} else {
		//System.out.println(nomFichierComplet + " existe déjà");
	}
}
}