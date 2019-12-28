package app.aspi;

import java.util.*;

import app.Ecrivain;

/**
* Les moteurs sont lancés avec la priorité minimum
*/
public class Aspirateur {





















;
/**
 * Constructor
 */
public Aspirateur() {
	super();
}


/**
 * Arr�te les moteurs de l'aspirateur
 */
public void arreter() {
	Iterator it = i_ensembleMoteur.iterator();
	while (it.hasNext()) {
		((MoteurSuceur) it.next()).arreter();
	}
}












/**
 * Les URLs sont pass�es en param�tre sous la forme <URL1>;<URL2>;...;<URLn>
 * @param chaineUrlDepart java.lang.String
 */
private Vector extraireLesUrl(String chaineUrlDepart) {
	Vector resultat = new Vector();
	String ch = chaineUrlDepart;
	int ind = -1;
	String url = "";
	while (ch.length() != 0) {
		ind = ch.indexOf(";");
		if (ind == -1) {
			resultat.addElement(ch);
			//TRACE
			Ecrivain.trace("URL pass�e : " + ch);
			//FIN TRACE
			ch = "";
		} else {
			url = ch.substring(0, ind);
			//TRACE
			Ecrivain.traceDetail("Aspirateur", "URL pass�e : " + url);
			//FIN TRACE
			resultat.addElement(url);
			ch = ch.substring(ind + 1);
		}
	} //while
	return resultat;
}






























































;
		
 
 
 
;


	Vector i_ensembleMoteur;
	TacheAspi i_tache;

/**
 * Continue l'ex�cution de la t�che courante.<br>
 * Les moteurs existent et ils connaissent la t�che en cours.
 *  Date de cr�ation : (21/03/2001 14:05:41)
 */
public void continuer() {
	//var locales
	Thread l_thread;
	MoteurSuceur l_moteur;
	//
	Ecrivain.trace("Reprise :" + new java.util.Date());
	//activer les moteurs
	Iterator l_iter = i_ensembleMoteur.iterator();
	while (l_iter.hasNext()) {
		l_moteur = (MoteurSuceur) l_iter.next();
		l_thread = new Thread(l_moteur);
		l_thread.setPriority(Thread.NORM_PRIORITY);
		try {
			//tempo entre les moteurs = 9s
			Thread.currentThread().sleep(9000);
		} catch (InterruptedException e) {
		}
		l_thread.start();
	}
}

/**
 * Ex�cuter une t�che.
 *  Date de cr�ation : (21/03/2001 14:05:41)
 */
public void executer(TacheAspi p_tache) {
	//var locales
	Thread l_thread;
	MoteurSuceur l_moteur;
	int l_nbMoteurs;
	//
	i_tache = p_tache;
	//la tache contient le nb de moteurs
	l_nbMoteurs = i_tache.getCriteres().getNbMoteurs();
	Ecrivain.trace("Debut ex�cution :" + new java.util.Date());
	//composer le pool de moteur
	i_ensembleMoteur = new Vector(l_nbMoteurs);
	for (int i = 0; i < l_nbMoteurs; i++) {
		l_moteur = new MoteurSuceur();
		l_moteur.setFournisseurURL(i_tache.getTas());
		l_moteur.setGarage(i_ensembleMoteur);
		l_moteur.criteres = i_tache.getCriteres();
		l_moteur.setIdentifiant("moteur " + i);
		//lancer les moteurs
		l_thread = new Thread(l_moteur);
		l_thread.setPriority(Thread.NORM_PRIORITY);
		try {
			//tempo entre les moteurs 9s
			Thread.currentThread().sleep(9000);
		} catch (InterruptedException e) {
		}
		l_thread.start();
		i_ensembleMoteur.addElement(l_moteur);
	}
}

/**
 * Ins�rez la description de la m�thode � cet endroit.
 *  Date de cr�ation : (22/03/2001 09:17:21)
 * @return olivier.aspi.TacheAspi
 */
public TacheAspi getTache() {
	return i_tache;
}

/**
 * Ins�rez la description de la m�thode � cet endroit.
 *  Date de cr�ation : (22/03/2001 09:17:21)
 * @param newI_tache olivier.aspi.TacheAspi
 */
private void setTache(TacheAspi newI_tache) {
	i_tache = newI_tache;
}
}