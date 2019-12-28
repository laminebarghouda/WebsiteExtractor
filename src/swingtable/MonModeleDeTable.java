package swingtable;

import app.aspi.CopieurDeFichierInternet;

/**
* Classe utile pour surveiller l'avancement des moteurs
* de l'aspirateur.<br>
* A refaire...
*/
public class MonModeleDeTable
	extends javax.swing.table.AbstractTableModel {
	private java.util.Vector data = new java.util.Vector();
	private java.util.Hashtable dicoCopieur = new java.util.Hashtable();
	boolean nouvelleLigneInseree = false;
/**
 * Commentaire relatif au constructeur MonModeleDeTable.
 */
public MonModeleDeTable() {
	super();
}

/**
 *  Returns Object.class by default
 */
public Class getColumnClass(int columnIndex) {
	if (columnIndex == 0)
		return String.class;
	else
		return Integer.class;
}
/**
 * Commentaire relatif � la m�thode getColumnCount.
 */
public int getColumnCount() {
	return 2;
}
/**
 *  Renvoie le nom des colonnes.
 */
public String getColumnName(int column) {
	if (column == 0)
		 return "document";
	else if (column == 1)
		 return "taille (Ko)";
	else
		return "vitesse (Ko/s)";
}
/**
 * Ins�rez la description de la m�thode � cet endroit.
 *  Date de cr�ation : (18/10/2000 14:20:35)
 * @return java.lang.Object[][]
 */
private java.util.Vector getData() {
	return data;
}
/**
 * Commentaire relatif � la m�thode getRowCount.
 */
public int getRowCount() {
	return data.size();
}
/**
 * Renvoie la valeur
 */
public Object getValueAt(int arg1, int arg2) {
	return ((Object[])data.elementAt(arg1))[arg2];
}

/**
 * Ins�rez la description de la m�thode � cet endroit.
 *  Date de cr�ation : (18/10/2000 14:20:35)
 * @param newData java.util.Vector
 */
private void setData(java.util.Vector newData) {
	data = newData;
}
/**
 *  Ajoute une donn�e
 */
public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	if (data.size() < rowIndex + 1) {
		//la ligne n'existe pas encore
		data.addElement(new Object[2]);
	}
	((Object[]) data.elementAt(rowIndex))[columnIndex] = aValue;
}
/**
 * Ecrit pour un copieur de fichier
 * le nom du fichier copi� et sa taille
 *  Date de cr�ation : (20/10/2000 10:07:19)
 * @param unCopieur aspi.CopieurDeFichierInternet
 * @param uneURLChaine java.lang.String
 * @param uneTaille int
 */
public void ecrirePourCopieur(CopieurDeFichierInternet unCopieur, String uneURLChaine, int uneTaille) {
	int rowIndex = retrouverIndexCopieur(unCopieur);
	//colonne 0 = l'URL
	setValueAt(uneURLChaine, rowIndex, 0);

	//colonne 1 = la taille
	setValueAt(new Integer(uneTaille), rowIndex, 1);
	if (nouvelleLigneInseree) {
		nouvelleLigneInseree = false;
		fireTableRowsInserted(rowIndex, rowIndex);
	}
	fireTableCellUpdated(rowIndex, 0);
	fireTableCellUpdated(rowIndex, 1);
//artillerie lourde !
	fireTableDataChanged();
}/**
 * Renvoie le num�ro de la ligne affect�e � un copieur
 *  Date de cr�ation : (20/10/2000 10:13:21)
 * @return int
 * @param unCopieur olivier.aspi.CopieurDeFichierInternet
 */    
private int retrouverIndexCopieur(CopieurDeFichierInternet unCopieur) {
	if (!dicoCopieur.containsKey(unCopieur)) {
		int rowIndex = dicoCopieur.size();
		//nouveau copieur
		dicoCopieur.put(unCopieur, new Integer(rowIndex));
		//SIGNALER
		nouvelleLigneInseree = true;
	}
	return ((Integer) dicoCopieur.get(unCopieur)).intValue();
}
}