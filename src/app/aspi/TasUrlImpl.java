package app.aspi;





import app.url.URLCommentee;


import app.Ecrivain;
import java.util.*;

public class TasUrlImpl implements TasUrl {
	/** ensemble ordonnné des URLs des serveurs en cours d'aspiration.<br>
	Chaque élément est un ensemble d'URL du même serveur. */
	LinkedList i_serveursOrdonnes = null;
	/** ensemble ordonné des noms des serveurs de l'ensemble 'serveursOrdonnes' */
	LinkedList i_nomsServeursOrdonnes = null;
	/** URL de départ */
	URLCommentee i_urlDeDepart = null;
	/** ensemble des serveurs candidats à l'aspiration.
	Une HashMap n'est pas pratique pour sélectionner quel sera le prochain serveur
	aspiré mais elle est bien adaptée pour le stockage. Et comme c'est ça le plus
	courant ... CQFD */
	HashMap i_autresServeurs = null;
/**
 * Commentaire relatif au constructeur TasUrl.
 */
public TasUrlImpl() {
	super();
}
/**
 * Ajoute une URL au tas.
 */
public synchronized boolean add(Object p_url) {
	SortedSet l_ensUrl = null;
	boolean acceptee = true;
	if (!(p_url instanceof URLCommentee)) {
		//erreur
		throw new IllegalArgumentException("On ne peut ajouter qu'une URL commentée dans un TasUrl.");
	}
	URLCommentee l_url = (URLCommentee) p_url;
	//url initiale ?
	if (i_urlDeDepart == null) {
		//oui
		initialiser(l_url);
	} else {
		//non
		//déjà vue ?
		if (dejaVus.contains(l_url.url.toString().toUpperCase())) {
			//oui
			acceptee = false;
			Ecrivain.traceDetail("Tas", "URL deja vue : " + l_url.url);
		} else {
			//non
			//URL acceptable ?
			if (criteres.accepteUrlCommentee(l_url)) {
				//oui
				dejaVus.add(l_url.url.toString().toUpperCase());
				l_ensUrl = ensembleDuServeur(l_url.getHost());
				Ecrivain.traceDetail("Tas", "Ajout OK :" + l_ensUrl.add(l_url));
				Ecrivain.traceDetail(
					"Tas", 
					"Nb d'URL du serveur "
						+ l_url.getHost()
						+ " apres ajout de "
						+ l_url
						+ " : \r\n"
						+ l_ensUrl.size()
						+ "\r\n"
						+ l_ensUrl.toString()); 
				//signaler aux moteurs en attente
				notify();
			} else {
				//non
				Ecrivain.traceDetail("Tas", "URL refusée : " + l_url.url);
				acceptee = false;
			}
		}
	}
	return acceptee;
}
/**
 * Ajoute un ensemble d'URL commentées.
 */
public boolean addAll(java.util.Collection c) {
	Iterator l_iter = c.iterator();
	while (l_iter.hasNext()) {
		add((URLCommentee) l_iter.next());
	}
	return true;
}

/**
 * Renvoie l'ensemble d'URL le plus gros parmi les serveurs en vrac.
 * Retire l'ensemble choisi de la liste des serveurs en vrac.
 * Peut renvoyer null.
 */
private SortedSet choisirNouveauServeurOrdonne() {
	SortedSet l_ens = null, l_choix = null;
	int l_taille = 0, l_max = 0;
	String l_nom = null, l_nomChoix = null;
	Iterator l_iter = i_autresServeurs.keySet().iterator();
	//parcourir tous les ensembles
	while (l_iter.hasNext()) {
		l_nom = (String) l_iter.next();
		l_ens = (SortedSet) i_autresServeurs.get(l_nom);
		l_taille = l_ens.size();
		if (l_taille > l_max) {
			//ensemble trouvé plus grand que le précédent
			l_max = l_taille;
			l_choix = l_ens;
			l_nomChoix = l_nom;
		}
	}
	//trouvé ?
	if (l_choix != null) {
		//oui
		//le retirer de l'ensemble en vrac
		i_autresServeurs.remove(l_nomChoix);
		//l'ajouter dans la liste ordonn�e
		i_serveursOrdonnes.addLast(l_choix);
		//ajouter son nom dans la liste ordonn�e
		i_nomsServeursOrdonnes.addLast(l_nomChoix);
		Ecrivain.trace("NOUVEAU serveur en cours : " + l_nomChoix);
	}
	return l_choix;
}
/**
 * Nettoyage par le vide.
 *
 * @throws UnsupportedOperationException if the <tt>clear</tt> method is
 *         not supported by this collection.
 */
public void clear() {
	i_autresServeurs.clear();
	i_nomsServeursOrdonnes.clear();
	i_serveursOrdonnes.clear();
	i_urlDeDepart = null;
}
	/**
	 * Pas prévu pour l'instant.
	 *
	 * Returns <tt>true</tt> if this collection contains the specified
	 * element.  More formally, returns <tt>true</tt> if and only if this
	 * collection contains at least one element <tt>e</tt> such that
	 * <tt>(o==null ? e==null : o.equals(e))</tt>.
	 *
	 * @param o element whose presence in this collection is to be tested.
	 * @return <tt>true</tt> if this collection contains the specified
	 *         element
	 */
public boolean contains(java.lang.Object o) {
	return false;
}
/**
 * 
 * @see #contains(Object)
 */
public boolean containsAll(Collection c) {
	Iterator e = c.iterator();
	while (e.hasNext())
		if (!contains(e.next()))
			return false;
	return true;
}
/**
 * Crée et renvoie un ensemble pour les URLs d'un nouveau serveur.
 */
private SortedSet creerNouvelEnsemble(String p_nomServeur) {
	Ecrivain.trace("nouveau serveur détecté : " + p_nomServeur);
	SortedSet l_ens = new TreeSet();
	i_autresServeurs.put(p_nomServeur, l_ens);
	return l_ens;
}
/**
 * Recherche de l'ens des urls d'un serveur.
 *  Date de création : (14/03/2001 12:23:58)
 */
private SortedSet ensembleDuServeur(String p_nomServeur) {
	SortedSet l_ens = null;
	//parmi les serveurs en cours d'aspiration ?
	l_ens = ensParmiServeursEnCours(p_nomServeur);
	if (l_ens == null) {
		//non
		Ecrivain.traceDetail(
			"Tas", 
			"Le serveur "
				+ p_nomServeur
				+ " ne fait pas partie des serveurs en cours de traitement"); 
		//parmi les autres serveurs ?
		l_ens = ensParmiServeursEnVrac(p_nomServeur);
		if (l_ens == null) {
			//non
			Ecrivain.traceDetail(
				"Tas", 
				"Le serveur " + p_nomServeur + " était jusqu'alors inconnu.");
			l_ens = creerNouvelEnsemble(p_nomServeur);
		}
	}
	return l_ens;
}
/**
 * Recherche d'un ens d'urls.
 */
private SortedSet ensParmiServeursEnCours(String p_nomServeur) {
	SortedSet l_ens = null;
	Ecrivain.traceDetail(
		"Tas", 
		"recherche de "
			+ p_nomServeur
			+ " parmi les serveurs en cours ("
			+ i_nomsServeursOrdonnes
			+ ")"); 
	int l_index = i_nomsServeursOrdonnes.indexOf(p_nomServeur);
	//trouvé ?
	if (l_index != -1) {
		//oui
		l_ens = (SortedSet) i_serveursOrdonnes.get(l_index);
	}
	return l_ens;
}
/**
 * Renvoie l'ensemble des URLs stockées pour le serveur spécifié.
 * Renvoie null si ce serveur ne fait pas partie des serveurs en vrac.
 */
private SortedSet ensParmiServeursEnVrac(String p_nomServeur) {
	return (SortedSet) i_autresServeurs.get(p_nomServeur);
}
/**
 * Renvoie vrai si l'on ne doit traiter que les URLs du serveur de l'URL initiale.
 * @return boolean
 */
private boolean estMonoServeur() {
	return criteres.doitResterSurServeurInitial();
}



/**
 * Ajoute une URL au tas.
 */
private void initialiser(URLCommentee p_url) {
	Ecrivain.traceDetail("Tas", "INITIALISATION : " + p_url);
	//mémoriser url de départ
	i_urlDeDepart = p_url;
	//liste des déjà vues
	dejaVus = new LinkedList();
	dejaVus.add(p_url.url.toString().toUpperCase());
	//initialiser la liste ordonnée des serveurs
	i_serveursOrdonnes = new LinkedList();
	SortedSet l_ens = new TreeSet();
	l_ens.add(p_url);
	i_serveursOrdonnes.addFirst(l_ens);
	//initialiser la liste ordonnée des noms des serveurs
	i_nomsServeursOrdonnes = new LinkedList();
	i_nomsServeursOrdonnes.addFirst(p_url.getHost());
	//initialiser la liste des autres serveurs (en vrac)
	i_autresServeurs = new HashMap();
	//
	Ecrivain.traceDetail("Tas", this.toString());
}
/**
 * Renvoit vrai s'il n'y a plus d'URL dans le tas.<br>
 * Prend en compte le fait qu'on doit rester sur le même serveur.
 */
public synchronized boolean isEmpty() {
	if (estMonoServeur()) {
		return ((SortedSet) i_serveursOrdonnes.getFirst()).isEmpty();
	} else {
		return (serveursOrdonnesSontVides() && serveursEnVracSontVides());
	}
}
/**
 * Returns an iterator over the elements in this collection.  There are no
 * guarantees concerning the order in which the elements are returned
 * (unless this collection is an instance of some class that provides a
 * guarantee).
 * 
 * @returns an <tt>Iterator</tt> over the elements in this collection
 */
public java.util.Iterator iterator() {
	throw new RuntimeException("Pas d'iterator possible sur un tas d'URL");
}
/**
 * Renvoie la prochaine URL � traiter, tous serveurs confondus.<br>
 *  Date de cr�ation : (14/03/2001 12:23:58)
 */
private URLCommentee lireURL() {
	URLCommentee l_url = null;
	//URL parmi les serveurs ordonnés ?
	l_url = lireURLServeurOrdonne();
	if (l_url == null) {
		//non
		SortedSet l_nouveauServeur = choisirNouveauServeurOrdonne();
		//nouveau serveur possible ?
		if (l_nouveauServeur != null) {
			//oui
			l_url = (URLCommentee) l_nouveauServeur.first();
		}
	}
	return l_url;
}
/**
 * Renvoie la prochaine URL à traiter pour le serveur initial.
 */
private URLCommentee lireURLServeurInitial() {
	SortedSet l_ensServeurInitial = 
		(SortedSet) i_serveursOrdonnes.getFirst(); 
	Ecrivain.traceDetail(
		"Tas", 
		"Nb d'URL du serveur initial = " + l_ensServeurInitial.size()); 
	URLCommentee l_urlc = (URLCommentee) l_ensServeurInitial.first();
	if (l_urlc != null) {
		l_ensServeurInitial.remove(l_urlc);
	}
	return l_urlc;
}
/**
 * Renvoie la prochaine URL à traiter, parmi les serveurs en cours.
 */
private URLCommentee lireURLServeurOrdonne() {
	URLCommentee l_url = null;
	SortedSet l_ensServeurCourant = null;
	Iterator l_iter = i_serveursOrdonnes.iterator();
	//on cherche la premi�re URL en prenant les serveurs dans l'ordre
	while (l_iter.hasNext() && l_url == null) {
		l_ensServeurCourant = (SortedSet) l_iter.next();
		try {
			l_url = (URLCommentee) l_ensServeurCourant.first();
		} catch (NoSuchElementException e) {
			l_url = null;
		}
		if (l_url != null) {
			//et on la retire !
			l_ensServeurCourant.remove(l_url);
		}
	}
	return l_url;
}
/**
 * Renvoie la prochaine URL à traiter.
 * Prend en compte le fait qu'on doit rester sur le même serveur.
 *  Date de création : (14/03/2001 12:23:58)
 */
public synchronized URLCommentee lireURLSuivante() {
	//s�curit� sur le total aspiré
	if (i_totalEnCours > I_TotalMax) {
		//limite atteinte => on renvoie null
		return null;
	}
	URLCommentee l_urlc = null;
	if (isEmpty()) {
		try {
			Ecrivain.trace("WAIT du thread " + Thread.currentThread().toString());
			//mise en attente du thread demandeur
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	Ecrivain.traceDetail("Tas", " n'est pas vide");
	//on ne traite que les URLs du serveur initial ?
	if (estMonoServeur()) {
		//oui
		Ecrivain.traceDetail("Tas", " mono serveur");
		l_urlc = lireURLServeurInitial();
	} else { //non
		Ecrivain.traceDetail("Tas", " multi serveurs");
		l_urlc = lireURL();
	}
	//s�curit� sur le total aspiré
	i_totalEnCours += l_urlc.getTaille();
	return l_urlc;
}
	/**
	 * Removes a single instance of the specified element from this
	 * collection, if it is present (optional operation).  More formally,
	 * removes an element <tt>e</tt> such that <tt>(o==null ?  e==null :
	 * o.equals(e))</tt>, if this collection contains one or more such
	 * elements.  Returns true if this collection contained the specified
	 * element (or equivalently, if this collection changed as a result of the
	 * call).
	 *
	 * @param o element to be removed from this collection, if present.
	 * @return <tt>true</tt> if this collection changed as a result of the
	 *         call
	 * 
	 * @throws UnsupportedOperationException remove is not supported by this
	 *         collection.
	 */
public boolean remove(java.lang.Object o) {
	return false;
}
	/**
	 * 
	 * Removes all this collection's elements that are also contained in the
	 * specified collection (optional operation).  After this call returns,
	 * this collection will contain no elements in common with the specified
	 * collection.
	 *
	 * @param c elements to be removed from this collection.
	 * @return <tt>true</tt> if this collection changed as a result of the
	 *         call
	 * 
	 * @throws UnsupportedOperationException if the <tt>removeAll</tt> method
	 * 	       is not supported by this collection.
	 * 
	 * @see #remove(Object)
	 * @see #contains(Object)
	 */
public boolean removeAll(java.util.Collection c) {
	return false;
}
	/**
	 * Retains only the elements in this collection that are contained in the
	 * specified collection (optional operation).  In other words, removes from
	 * this collection all of its elements that are not contained in the
	 * specified collection.
	 *
	 * @param c elements to be retained in this collection.
	 * @return <tt>true</tt> if this collection changed as a result of the
	 *         call
	 * 
	 * @throws UnsupportedOperationException if the <tt>retainAll</tt> method
	 * 	       is not supported by this Collection.
	 * 
	 * @see #remove(Object)
	 * @see #contains(Object)
	 */
public boolean retainAll(java.util.Collection c) {
	return false;
}
/**
 * Renvoie vrai s'il n'y a plus d'URL parmi les serveurs en vrac.<br>
 * Il suffit de voir s'il reste au moins un serveur parmi les serveurs en vrac
 * car on ne fait qu'ajouter des URLs dans ces ensembles, on n'en lit jamais
 * (sinon on bascule carrément le serveur parmi les serveurs ordonnés).
 */
private boolean serveursEnVracSontVides() {
	return i_autresServeurs.isEmpty();
}
/**
 * Renvoit vrai s'il n'y a plus d'URL parmi les serveurs en cours.
 * Il faut donc qu'ils soient TOUS vides.
 */
private boolean serveursOrdonnesSontVides() {
	Iterator l_iter = i_serveursOrdonnes.iterator();
	boolean l_vide = true;
	while (l_iter.hasNext()) {
		l_vide = l_vide && ((SortedSet) l_iter.next()).isEmpty();
	}
	return l_vide;
}
	/**
	 * Returns the number of elements in this collection.  If this collection
	 * contains more than <tt>Integer.MAX_VALUE</tt> elements, returns
	 * <tt>Integer.MAX_VALUE</tt>.
	 * 
	 * @return the number of elements in this collection
	 */
public int size() {
	return 0;
}
	/**
	 * Returns an array containing all of the elements in this collection.  If
	 * the collection makes any guarantees as to what order its elements are
	 * returned by its iterator, this method must return the elements in the
	 * same order.<p>
	 *
	 * The returned array will be "safe" in that no references to it are
	 * maintained by this collection.  (In other words, this method must
	 * allocate a new array even if this collection is backed by an array).
	 * The caller is thus free to modify the returned array.<p>
	 *
	 * This method acts as bridge between array-based and collection-based
	 * APIs.
	 *
	 * @return an array containing all of the elements in this collection
	 */
public java.lang.Object[] toArray() {
	return null;
}
	/**
	 * Returns an array containing all of the elements in this collection
	 * whose runtime type is that of the specified array.  If the collection
	 * fits in the specified array, it is returned therein.  Otherwise, a new
	 * array is allocated with the runtime type of the specified array and the
	 * size of this collection.<p>
	 *
	 * If this collection fits in the specified array with room to spare
	 * (i.e., the array has more elements than this collection), the element
	 * in the array immediately following the end of the collection is set to
	 * <tt>null</tt>.  This is useful in determining the length of this
	 * collection <i>only</i> if the caller knows that this collection does
	 * not contain any <tt>null</tt> elements.)<p>
	 *
	 * If this collection makes any guarantees as to what order its elements
	 * are returned by its iterator, this method must return the elements in
	 * the same order.<p>
	 *
	 * Like the <tt>toArray</tt> method, this method acts as bridge between
	 * array-based and collection-based APIs.  Further, this method allows
	 * precise control over the runtime type of the output array, and may,
	 * under certain circumstances, be used to save allocation costs<p>
	 *
	 * Suppose <tt>l</tt> is a <tt>List</tt> known to contain only strings.
	 * The following code can be used to dump the list into a newly allocated
	 * array of <tt>String</tt>:
	 *
	 * <pre>
	 *     String[] x = (String[]) v.toArray(new String[0]);
	 * </pre><p>
	 *
	 * Note that <tt>toArray(new Object[0])</tt> is identical in function to
	 * <tt>toArray()</tt>.
	 *
	 * @param the array into which the elements of this collection are to be
	 *        stored, if it is big enough; otherwise, a new array of the same
	 *        runtime type is allocated for this purpose.
	 * @return an array containing the elements of this collection
	 * 
	 * @throws ArrayStoreException the runtime type of the specified array is
	 *         not a supertype of the runtime type of every element in this
	 *         collection.
	 */
public java.lang.Object[] toArray(java.lang.Object[] a) {
	return null;
}

	/** crit�res de recherche de l'aspirateur */
	CriteresAspirateur criteres = null;	//m�moire de toutes les URLs qui ont été conservées à un moment ou à un autre
	//(permet de refuser l'ajout d'une URL déjà présente ou qui a été lues)
	private List dejaVus;
public void setCriteres(CriteresAspirateur p_crit) {
	criteres = p_crit;
}/**
	 * @return  a string representation of the object.
	 */
public String toString() {
	StringBuffer l_rep = new StringBuffer("Tas ");
	l_rep.append("URL initiale: " + i_urlDeDepart + "\r\n");
	l_rep.append("Serveurs en cours: " + i_nomsServeursOrdonnes + "\r\n"); 
	l_rep.append(
		"Serveurs en vrac: " + i_autresServeurs.keySet() + "\r\n"); 
	return l_rep.toString();
}

	private int i_totalEnCours = 0;
	private final static int I_TotalMax=500100100; //500 Mo
}