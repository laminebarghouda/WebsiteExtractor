package app;

import java.io.*;
import java.util.*;

/**
 * Classe utilitaire pour les traces.
 */
public class Ecrivain {
/**
 * Commentaire relatif au constructeur Ecrivain.
 */
public Ecrivain() {
	super();
}
/**
 * Ecrit une ligne dans un fichier
 * @param nomFichier java.lang.String
 * @param ligne java.lang.String
 * @exception java.io.IOException La description de l'exception.
 */
public void ecrireDansFichier(String nomFichier, String ligne) throws java.io.IOException {
	RandomAccessFile buffer = new RandomAccessFile((new File(nomFichier)), "rw");
	buffer.seek(buffer.length() - 1);
	buffer.writeBytes(ligne+"\r\n");
	buffer.close();
}
/**
 * Ecrit une chaine (avec un timestamp) dans un fichier dont on passe le nom
 * @param nomFichier java.lang.String
 * @param ligne java.lang.String
 * @exception java.io.IOException La description de l'exception.
 */
public void ecrireDansFichierAvecTimeStamp(String nomFichier, String ligne) throws IOException {
	this.ecrireDansFichier(nomFichier, this.getTimeStamp() + ligne);
}
/**
 * Renvoie une chaine avec les secondes
 * @return java.lang.String
 */
private String getTimeStamp() {
	int second = (new GregorianCalendar()).get(Calendar.SECOND);
	int milli = (new GregorianCalendar()).get(Calendar.MILLISECOND);
	return ((new Integer(second)).toString() + " : " + (new Integer(milli)).toString());
}
/**
 * Ecrit un simple message.
 * @param ecrivain java.lang.String
 * @param message java.lang.String
 */
public static void trace(String message) {
	System.out.println(message);
}
/**
 * Ecrit une anomalie sur la console.
 * @param ecrivain java.lang.String
 * @param message java.lang.String
 */
public static void traceAnomalie(String ecrivain, String message) {
	System.out.println("! " + ecrivain + " --> " + message);
}
/**
 * Ecrit une trace détaillée sur la console.
 * @param ecrivain java.lang.String
 * @param message java.lang.String
 */
public static void traceDetail(String ecrivain, String message) {
	//System.out.println("      " + ecrivain + " --> " + message);
}
}