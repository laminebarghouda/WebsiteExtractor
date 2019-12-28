package app.html.analyseur;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import app.Ecrivain;
import app.url.URLCommentee;

/**
 * Parser HTML qui utilise les classes du JDK 1.3.<br>
 * Merci au site internet sur lequel j'ai récupéré le source !
 * Classe dérivée de HTMLDocument pour permettre de spécifier
 * un reader différent dans la méthode getReader.
 */
public class ExtracteurLienHypertexte
	extends HTMLDocument
	implements RecuperateurLienHypertexte {
	private class LecteurLienHypertexte
		extends HTMLEditorKit.ParserCallback {
		/**
		* Méthode appelée qd un tag de début est rencontré.
		*/
		public void handleStartTag(
			HTML.Tag tag, 
			MutableAttributeSet att, 
			int pos) {
			String attribut;
			URLCommentee urlTrouvee;
			int indexDiese;
			if (tag.equals(HTML.Tag.A)) //tags <a name=...> ou <a href=...>
				{
				Ecrivain.traceDetail("Extracteur", "ANCRE repérée");
				attribut = (String) att.getAttribute(HTML.Attribute.HREF);
				if (attribut != null) {
					try {
						Ecrivain.traceDetail("Extracteur", "HREF repérée");
						//enlever l'ancre pour les attributs du type #qch ou qch#qch
						indexDiese = attribut.lastIndexOf('#');
						if (indexDiese != -1) {
							if (indexDiese == 0) {
								attribut = null; //on n'en veut pas
							} else {
								attribut = attribut.substring(0, indexDiese);
							}
						}
						//fin enlevement
						if (attribut != null) {
							urlTrouvee = 
								new URLCommentee(
									new URL(urlOrigine.url, attribut), 
									urlOrigine.profondeur + 1); 
							tas.add(urlTrouvee);
							Ecrivain.traceDetail("Extracteur", urlTrouvee.toString());
						}
					} catch (MalformedURLException e) {
						Ecrivain.traceDetail("Extracteur", "ERREUR DE TRACE");
					}
				}
			}
		}
		/**
		* Méthode appelée qd un tag simple est lu.
		*/
		public void handleSimpleTag(
			HTML.Tag tag, 
			MutableAttributeSet att, 
			int pos) {
			String attribut;
			URLCommentee urlTrouvee = null;
			if (tag.equals(HTML.Tag.FRAME) //tag <frame src=...>
				|| tag.equals(HTML.Tag.IMG)) //tag < IMG SRC=...>
				{
				Ecrivain.traceDetail("Extracteur", "FRAME ou IMG repérée");
				attribut = (String) att.getAttribute(HTML.Attribute.SRC);
				if (attribut != null) {
					try {
						urlTrouvee = 
							new URLCommentee(
								new URL(urlOrigine.url, attribut), 
								urlOrigine.profondeur + 1); 
						tas.add(urlTrouvee);
						Ecrivain.traceDetail("Extracteur", urlTrouvee.toString());
					} catch (MalformedURLException e) {
						Ecrivain.traceAnomalie("Extracteur", "ERREUR : " + e);
					}
				}
			} else if (tag.equals(HTML.Tag.AREA)) //tag <AREA href=...>
				{
				attribut = (String) att.getAttribute(HTML.Attribute.HREF);
				if (attribut != null) {
					try {
						urlTrouvee = 
							new URLCommentee(
								new URL(urlOrigine.url, attribut), 
								urlOrigine.profondeur + 1); 
						tas.add(urlTrouvee);
						Ecrivain.traceDetail("Extracteur", urlTrouvee.toString());
					} catch (MalformedURLException e) {
						Ecrivain.traceAnomalie("Extracteur", "ERREUR : " + e);
					}
				}
			}
		}
	}


	//ens url mal écrites
	private Vector mauvaiseUrls = new Vector();
	//url de d�part
	String chaineUrlDepart = null;
/**
 * Commentaire relatif au constructeur HTMLDocumentLinks.
 */
public ExtracteurLienHypertexte() {
	super();
}
/**
 * Constructeur le plus utile.
 */
public ExtracteurLienHypertexte(URL uneURL) {
	super();
	//mémorisation de la base du fichier HTML
	setBase(uneURL);
}
/**
 * Commentaire relatif au constructeur HTMLDocumentLinks.
 * @param c javax.swing.text.AbstractDocument.Content
 * @param styles javax.swing.text.html.StyleSheet
 */
public ExtracteurLienHypertexte(AbstractDocument.Content c, StyleSheet styles) {
	super(c, styles);
}
/**
 * Commentaire relatif au constructeur HTMLDocumentLinks.
 * @param styles javax.swing.text.html.StyleSheet
 */
public ExtracteurLienHypertexte(StyleSheet styles) {
	super(styles);
}

/**
 * M�thode surchargée pour fournir un reader différent.
 */
public HTMLEditorKit.ParserCallback getReader(int pos) {
	return new LecteurLienHypertexte();
}
/**
 * Renvoit vrai si l'URL ne doit pas être exploitée.
 */
private boolean ignorerURL(String chaineURL) {
	//TO DO : filter diff�remment
	return chaineURL.indexOf(':')>=0;
}

	//ens dans lequel placer les URLs trouv�es
	Collection tas;
	//url du document analys�
	URLCommentee urlOrigine;

/**
 * M�thode que doit implémenter un extracteur d'URL.
 * @param nomFichier nom du fichier à analyser
 * @param ensemble dans lequel placer les URLs trouvées
 * @param urlOriginale url du document pour reconstituer les URLs relatives
 */
public void extraire(
	java.lang.String nomFichier,
	java.util.Collection ensemble,
	URLCommentee urlOriginale) {
	//affectation du tas
	tas = ensemble;
	//affectation de l'URl originale
	urlOrigine = urlOriginale;
	try {
		//ouverture fichier
		Ecrivain.traceDetail("Extracteur", "Ouverture...");
		Reader lecteurURL = new BufferedReader(new FileReader(nomFichier));
		//m�morisation base
		Ecrivain.traceDetail("Extracteur", "Base...");
		setBase(urlOriginale.url);
		/* m�morisation url du fichier analys� */
		this.chaineUrlDepart = urlOriginale.url.toString();
		Ecrivain.traceDetail("Extracteur", "Parsing...");
		this.putProperty("IgnoreCharsetDirective", new Boolean(true));
		//�vite les pbs de charset
		new HTMLEditorKit().read(lecteurURL, this, 0);
		lecteurURL.close();
	} catch (IOException e) {
		Ecrivain.traceAnomalie(
			"Extracteur",
			"Pb d'accés au fichier : " + nomFichier + " : " + e);
	} catch (BadLocationException e) {
		Ecrivain.traceAnomalie("Extracteur", "Bad location exception : " + e);
	}
}
}