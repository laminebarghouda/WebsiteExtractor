package swingtable;


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.awt.Dimension;


import javax.swing.JFrame;
import javax.swing.border.BevelBorder;
import javax.swing.JTable;
import javax.swing.JScrollPane;

/**
* Classe qui permettait de surveiller l'avancement des moteurs
* de l'aspirateur.<br>
* A refaire...
*/
public class FrameTable {
	private MonModeleDeTable modele;
/**
 * Commentaire relatif au constructeur FrameTable.
 */
public FrameTable() {
	super();
	initialize();
}
/**
 * R�cup�re le mod�le de la table
 *  Date de cr�ation : (18/10/2000 18:10:40)
 * @return swingtable.MonModeleDeTable
 */
public MonModeleDeTable getModele() {
	return modele;
}
/**
 * Appel� chaque fois que l'�l�ment �met une exception.
 * @param exception java.lang.Throwable
 */
private void handleException(java.lang.Throwable exception) {

	/* Annulation des commentaires des lignes suivantes pour diriger les exceptions non intercept�es vers stdout */
	// System.out.println("--------- EXCEPTION NON INTERCEPTEE---------");
	// exception.printStackTrace(System.out);
}
/**
 * M�thode d'ouverture de la fen�tre avec la JTable
 */
public void initialiser() {
	//initialiserModele();

	JFrame frame = new JFrame("Table");
	frame.addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	});
	frame.setSize(new Dimension(600, 100));
	modele = new MonModeleDeTable();
	JTable jt = new JTable(modele);
	JScrollPane scrollpane = new JScrollPane(jt);
	scrollpane.setBorder(new BevelBorder(BevelBorder.LOWERED));
	scrollpane.setPreferredSize(new Dimension(600, 100));
	frame.getContentPane().add(scrollpane);
	frame.pack();
	frame.setVisible(true);
}
/**
 * Cr�e le mod�le de la table
 *  Date de cr�ation : (18/10/2000 14:17:04)
 */
private void initialiserModele() {
	modele = new MonModeleDeTable();
}
/**
 * Initialisation de la classe.
 */
/* AVERTISSEMENT : CETTE METHODE SERA REGENEREE. */
private void initialize() {
	try {
		// user code begin {1}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	}
	// user code begin {2}
	// user code end
}
/**
 * Point d'entr�e principal. D�marre l'�l�ment lorsqu'il est ex�cut� comme une application.
 * @param args java.lang.String[]
 */
public static void main(java.lang.String[] args) {
	try {
		//ouverture fen�tre
		new FrameTable();
	} catch (Throwable exception) {
		System.err.println("Une exception s'est produite dans main() de javax.swing.JFrame");
		exception.printStackTrace(System.out);
	}
}
/**
 * Fixe le mod�le de la table
 *  Date de cr�ation : (18/10/2000 18:10:40)
 * @param newModele swingtable.MonModeleDeTable
 */
public void setModele(MonModeleDeTable newModele) {
	modele = newModele;
}
}