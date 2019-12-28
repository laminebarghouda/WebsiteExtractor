package app.html.analyseur;

import app.url.URLCommentee;
import java.util.Collection;

/**
 * Interface d'un extracteur d'URL.
 * Permet plusieurs implémentations.
 * @author : Administrator
 */
public interface RecuperateurLienHypertexte {
/**
 * M�thode que doit implémenter un extracteur d'URL.
 * @param nomFichier nom du fichier à analyser
 * @param ensemble dans lequel placer les URLs trouvées
 * @param urlOriginale url du document pour reconstituer les URLs relatives
 */
public void extraire(String nomFichier, Collection ensemble, URLCommentee urlOriginale);
}