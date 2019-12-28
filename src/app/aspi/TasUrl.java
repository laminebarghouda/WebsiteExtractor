package app.aspi;

import app.url.URLCommentee;
import java.util.Collection;
import java.io.Serializable;

/**
 * Ensemble d'URL triées.
 * On utilise au mieux l'interface Collection et
 * on ajoute quelques méthodes particulières.
 * Il existe cette interface (qui étend l'interface
 * java.util.Collection) et une implémentation (TasUrlImpl).
 * Le tas est très important. Il permet
 * a) de trier les URLs par serveur
 * b) de trier les serveurs (d'abord celui de départ
 * et ensuite ceux que le serveur de départ référence le plus souvent)
 * c) d'éviter d'aspirer 2 fois le même document
 * d) de refuser les URLs qui ne respectent pas les critères de recherche.
 */
public interface TasUrl extends Collection, Serializable {

/**
 * Renvoie la prochaine URL à traiter.
 */
public URLCommentee lireURLSuivante() ;

/**
 * Spécifie au tas quels sont les critères de recherche.
 * Cela permet au tas de filtrer les URLs et de ne pas les accepter toutes.
 */
public void setCriteres(CriteresAspirateur p_crit);
}