package app.aspi;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import app.url.URLCommentee;
import app.Ecrivain;
/**
 * Une tâche, pour un aspirateur, est constituée de critères
 * et d'un tas initialisé.


 */
public class TacheAspi implements Serializable {
	private TasUrl i_tas;
	private CriteresAspirateur i_criteres;
	private final static long serialVersionUID = -2073585167519351902L;
/**
 * Commentaire relatif au constructeur TacheAspi.
 */
public TacheAspi() {
	super();
}
/**
 * Constructeur rapide : on indique seulement le nombre
 * de moteurs et l'URL de départ.
 */
public TacheAspi(String p_urlDepart, int p_nbMoteurs)
	throws MalformedURLException {
	super();
	//critères par défaut
	i_criteres = new CriteresAspirateur();
	i_criteres.resteSurServeurInitial = true;
	i_criteres.setNbMoteurs(p_nbMoteurs);
	//tas initialisé
	i_tas = new TasUrlImpl();
	i_tas.setCriteres(i_criteres);
	i_tas.add(new URLCommentee(new URL(p_urlDepart), 0));
}
/**
 * Sérialise l'objet dans un fichier.
 */
public void exporter(String nomFichier) {
	try {
		ObjectOutputStream oos =
			new ObjectOutputStream(new FileOutputStream(new File(nomFichier)));
		oos.writeObject(this);
		oos.close();
	} catch (IOException e) {
		Ecrivain.traceAnomalie("TacheAspi", "pb à l'exportation : " + e);
	}
}
/**
 * Insérez la description de la méthode à cet endroit.
 * @return aspi.CriteresAspirateur
 */
public CriteresAspirateur getCriteres() {
	return i_criteres;
}
/**
 * Insérez la description de la méthode à cet endroit.
 * @return aspi.TasUrl
 */
public TasUrl getTas() {
	return i_tas;
}
/**
 * Importe un objet sérialisé.
 */
public static TacheAspi importer(String nomFichier) {
	TacheAspi l_tache = null;
	try {
		java.io.ObjectInputStream ois =
			new java.io.ObjectInputStream(
				new java.io.FileInputStream(new java.io.File(nomFichier)));
		l_tache = (TacheAspi) ois.readObject();
		ois.close();
	} catch (Exception e) {
		Ecrivain.traceAnomalie("TacheAspi", "Pb à l'importation : " + e);
	}
	return l_tache;
}
/**
 * Insérez la description de la méthode à cet endroit.
 * @param newI_criteres aspi.CriteresAspirateur
 */
void setCriteres(CriteresAspirateur newI_criteres) {
	i_criteres = newI_criteres;
}
}