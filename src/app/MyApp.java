package app;

import app.aspi.*;
/**
 * IHM de l'aspirateur.
 */
public class MyApp extends java.awt.Frame {
	private java.awt.Button ivjButton1 = null;
	private java.awt.Button ivjButton2 = null;
	private java.awt.Button ivjButton3 = null;
	private java.awt.Panel ivjContentsPane = null;
	IvjEventHandler ivjEventHandler = new IvjEventHandler();
	private java.awt.Label ivjLabel1 = null;
	private java.awt.Label ivjLabel2 = null;
	private java.awt.Label ivjLabel3 = null;
	private java.awt.TextField ivjTextField1 = null;
	private java.awt.TextField ivjTextField2 = null;
	private java.awt.TextField ivjTextField3 = null;
	private app.aspi.Aspirateur ivjv_aspi = null;

	class IvjEventHandler implements java.awt.event.ActionListener, java.awt.event.WindowListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			if (e.getSource() == MyApp.this.getButton1())
				connEtoC2(e);
			if (e.getSource() == MyApp.this.getButton3())
				connEtoC3(e);
			if (e.getSource() == MyApp.this.getButton2())
				connEtoC4(e);
		}

		;

		public void windowActivated(java.awt.event.WindowEvent e) {
		}

		;

		public void windowClosed(java.awt.event.WindowEvent e) {
		}

		;

		public void windowClosing(java.awt.event.WindowEvent e) {
			if (e.getSource() == MyApp.this)
				connEtoC1(e);
		}

		;

		public void windowDeactivated(java.awt.event.WindowEvent e) {
		}

		;

		public void windowDeiconified(java.awt.event.WindowEvent e) {
		}

		;

		public void windowIconified(java.awt.event.WindowEvent e) {
		}

		;

		public void windowOpened(java.awt.event.WindowEvent e) {
		}

		;
	}

	;

	/**
	 * Commentaire relatif au constructeur MyApp.
	 */
	public MyApp() {
		super();
		initialize();
	}

	/**
	 * Commentaire relatif au constructeur MyApp.
	 *
	 * @param title java.lang.String
	 */
	public MyApp(String title) {
		super(title);
	}

	/**
	 * Arrête la tache courante.
	 */
	void arreter() {
		getv_aspi().arreter();
	}


	/**
	 * connEtoC1:  (MyApp.window.windowClosing(java.awt.event.WindowEvent) --> MyApp.dispose()V)
	 *
	 * @param arg1 java.awt.event.WindowEvent
	 */
	/* AVERTISSEMENT : CETTE METHODE SERA REGENEREE. */
	private void connEtoC1(java.awt.event.WindowEvent arg1) {
		try {
			// user code begin {1}
			// user code end
			this.dispose();
			// user code begin {2}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {3}
			// user code end
			handleException(ivjExc);
		}
	}

	/**
	 * connEtoC2:  (Button1.action.actionPerformed(java.awt.event.ActionEvent) --> MyApp.executer()V)
	 *
	 * @param arg1 java.awt.event.ActionEvent
	 */
	/* AVERTISSEMENT : CETTE METHODE SERA REGENEREE. */
	private void connEtoC2(java.awt.event.ActionEvent arg1) {
		try {
			// user code begin {1}
			// user code end
			this.executer();
			// user code begin {2}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {3}
			// user code end
			handleException(ivjExc);
		}
	}

	/**
	 * connEtoC3:  (Button3.action.actionPerformed(java.awt.event.ActionEvent) --> MyApp.continuer()V)
	 *
	 * @param arg1 java.awt.event.ActionEvent
	 */
	/* AVERTISSEMENT : CETTE METHODE SERA REGENEREE. */
	private void connEtoC3(java.awt.event.ActionEvent arg1) {
		try {
			// user code begin {1}
			// user code end
			this.continuer();
			// user code begin {2}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {3}
			// user code end
			handleException(ivjExc);
		}
	}

	/**
	 * connEtoC4:  (Button2.action.actionPerformed(java.awt.event.ActionEvent) --> MyApp.arreter()V)
	 *
	 * @param arg1 java.awt.event.ActionEvent
	 */
	/* AVERTISSEMENT : CETTE METHODE SERA REGENEREE. */
	private void connEtoC4(java.awt.event.ActionEvent arg1) {
		try {
			// user code begin {1}
			// user code end
			this.arreter();
			// user code begin {2}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {3}
			// user code end
			handleException(ivjExc);
		}
	}

	/**
	 * Continue la tache courante.
	 */
	void continuer() {
		getv_aspi().continuer();
	}

	/**
	 * Exécute une tache.
	 */
	void executer() {
		try {
			//cr�er la tache
			app.aspi.TacheAspi l_tache =
					new app.aspi.TacheAspi(
							getTextField1().getText(),
							Integer.parseInt(getTextField2().getText()));
			getv_aspi().executer(l_tache);
		} catch (java.net.MalformedURLException e) {
			Ecrivain.traceAnomalie("ihm", "URL mal écrite");
		}
	}

	/**
	 * Renvoi de la valeur de propriété Button1.
	 *
	 * @return java.awt.Button
	 */
	/* AVERTISSEMENT : CETTE METHODE SERA REGENEREE. */
	private java.awt.Button getButton1() {
		if (ivjButton1 == null) {
			try {
				ivjButton1 = new java.awt.Button();
				ivjButton1.setName("Button1");
				ivjButton1.setBounds(309, 66, 56, 23);
				ivjButton1.setLabel("Aspirer");
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjButton1;
	}

	/**
	 * Renvoi de la valeur de propriété Button2.
	 *
	 * @return java.awt.Button
	 */
	/* AVERTISSEMENT : CETTE METHODE SERA REGENEREE. */
	private java.awt.Button getButton2() {
		if (ivjButton2 == null) {
			try {
				ivjButton2 = new java.awt.Button();
				ivjButton2.setName("Button2");
				ivjButton2.setBounds(18, 105, 56, 23);
				ivjButton2.setLabel("stop");
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjButton2;
	}

	/**
	 * Renvoi de la valeur de propriété Button3.
	 *
	 * @return java.awt.Button
	 */
	/* AVERTISSEMENT : CETTE METHODE SERA REGENEREE. */
	private java.awt.Button getButton3() {
		if (ivjButton3 == null) {
			try {
				ivjButton3 = new java.awt.Button();
				ivjButton3.setName("Button3");
				ivjButton3.setBounds(96, 106, 64, 23);
				ivjButton3.setLabel("continuer");
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjButton3;
	}


	/**
	 * Renvoi de la valeur de propriété ContentsPane.
	 *
	 * @return java.awt.Panel
	 */
	/* AVERTISSEMENT : CETTE METHODE SERA REGENEREE. */
	private java.awt.Panel getContentsPane() {
		if (ivjContentsPane == null) {
			try {
				ivjContentsPane = new java.awt.Panel();
				ivjContentsPane.setName("ContentsPane");
				ivjContentsPane.setLayout(null);
				ivjContentsPane.setBackground(java.awt.SystemColor.info);
				getContentsPane().add(getTextField1(), getTextField1().getName());
				getContentsPane().add(getLabel1(), getLabel1().getName());
				getContentsPane().add(getLabel2(), getLabel2().getName());
				getContentsPane().add(getLabel3(), getLabel3().getName());
				getContentsPane().add(getTextField2(), getTextField2().getName());
				getContentsPane().add(getButton1(), getButton1().getName());
				getContentsPane().add(getButton2(), getButton2().getName());
				getContentsPane().add(getButton3(), getButton3().getName());
				getContentsPane().add(getTextField3(), getTextField3().getName());
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjContentsPane;
	}

	/**
	 * Renvoi de la valeur de propriété Label1.
	 *
	 * @return java.awt.Label
	 */
	/* AVERTISSEMENT : CETTE METHODE SERA REGENEREE. */
	private java.awt.Label getLabel1() {
		if (ivjLabel1 == null) {
			try {
				ivjLabel1 = new java.awt.Label();
				ivjLabel1.setName("Label1");
				ivjLabel1.setText("URL initiale");
				ivjLabel1.setBounds(14, 5, 75, 23);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjLabel1;
	}

	/**
	 * Renvoi de la valeur de propriété Label2.
	 *
	 * @return java.awt.Label
	 */
	/* AVERTISSEMENT : CETTE METHODE SERA REGENEREE. */
	private java.awt.Label getLabel2() {
		if (ivjLabel2 == null) {
			try {
				ivjLabel2 = new java.awt.Label();
				ivjLabel2.setName("Label2");
				ivjLabel2.setText("Nb de moteurs");
				ivjLabel2.setBounds(14, 58, 99, 23);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjLabel2;
	}

	/**
	 * Renvoi de la valeur de propriété Label3.
	 *
	 * @return java.awt.Label
	 */
	/* AVERTISSEMENT : CETTE METHODE SERA REGENEREE. */
	private java.awt.Label getLabel3() {
		if (ivjLabel3 == null) {
			try {
				ivjLabel3 = new java.awt.Label();
				ivjLabel3.setName("Label3");
				ivjLabel3.setText("Path Sortie");
				ivjLabel3.setBounds(320, 180, 100, 23);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjLabel3;
	}

	/**
	 * Renvoi de la valeur de propriété TextField1.
	 *
	 * @return java.awt.TextField
	 */
	/* AVERTISSEMENT : CETTE METHODE SERA REGENEREE. */
	private java.awt.TextField getTextField1() {
		if (ivjTextField1 == null) {
			try {
				ivjTextField1 = new java.awt.TextField();
				ivjTextField1.setName("TextField1");
				ivjTextField1.setText("http://ma647021/");
				ivjTextField1.setBounds(12, 31, 403, 23);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjTextField1;
	}

	/**
	 * Renvoi de la valeur de propriété TextField2.
	 *
	 * @return java.awt.TextField
	 */
	/* AVERTISSEMENT : CETTE METHODE SERA REGENEREE. */
	private java.awt.TextField getTextField2() {
		if (ivjTextField2 == null) {
			try {
				ivjTextField2 = new java.awt.TextField();
				ivjTextField2.setName("TextField2");
				ivjTextField2.setText("1");
				ivjTextField2.setBounds(123, 59, 42, 23);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjTextField2;
	}

	/**
	 * Renvoi de la valeur de propriété TextField3.
	 *
	 * @return java.awt.TextField
	 */
	/* AVERTISSEMENT : CETTE METHODE SERA REGENEREE. */
	private java.awt.TextField getTextField3() {
		if (ivjTextField3 == null) {
			try {
				ivjTextField3 = new java.awt.TextField();
				ivjTextField3.setName("TextField3");
				ivjTextField3.setText("d:/Abdelghani/temp/tache01");
				ivjTextField3.setBounds(16, 180, 290, 23);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjTextField3;
	}

	/**
	 * Renvoi de la valeur de propriété v_aspi.
	 *
	 * @return app.aspi.Aspirateur
	 */
	/* AVERTISSEMENT : CETTE METHODE SERA REGENEREE. */
	private app.aspi.Aspirateur getv_aspi() {
		if (ivjv_aspi == null) {
			try {
				ivjv_aspi = new app.aspi.Aspirateur();
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjv_aspi;
	}

	/**
	 * Appelé chaque fois que l'élément lance une exception.
	 *
	 * @param exception java.lang.Throwable
	 */
	private void handleException(java.lang.Throwable exception) {

		/* Annulation des commentaires des lignes suivantes pour diriger les exceptions non intercept�es vers stdout */
		// System.out.println("--------- EXCEPTION NON INTERCEPTEE---------");
		// exception.printStackTrace(System.out);
	}

	/**
	 * Initialisation des connexions
	 *
	 * @throws java.lang.Exception La description de l'exception.
	 */
	/* AVERTISSEMENT : CETTE METHODE SERA REGENEREE. */
	private void initConnections() throws java.lang.Exception {
		// user code begin {1}
		// user code end
		this.addWindowListener(ivjEventHandler);
		getButton1().addActionListener(ivjEventHandler);
		getButton3().addActionListener(ivjEventHandler);
		getButton2().addActionListener(ivjEventHandler);
	}

	/**
	 * Initialisation de la classe.
	 */
	/* AVERTISSEMENT : CETTE METHODE SERA REGENEREE. */
	private void initialize() {
		try {
			// user code begin {1}
			// user code end
			setName("MyApp");
			setLayout(new java.awt.BorderLayout());
			setSize(426, 240);
			setTitle("Aspirateur WEB");
			add(getContentsPane(), "Center");
			initConnections();
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
		// user code begin {2}
		// user code end
	}

	/**
	 * Point d'entrée principal. Démarre l'élément lorsqu'il est exécuté comme une application.
	 *
	 * @param args java.lang.String[]
	 */
	public static void main(java.lang.String[] args) {
		try {
			MyApp aMyApp;
			aMyApp = new MyApp();
			aMyApp.addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowClosing(java.awt.event.WindowEvent e) {
					System.exit(0);
				}

				;
			});
			aMyApp.show();
			java.awt.Insets insets = aMyApp.getInsets();
			aMyApp.setSize(aMyApp.getWidth() + insets.left + insets.right, aMyApp.getHeight() + insets.top + insets.bottom);
			aMyApp.setVisible(true);
		} catch (Throwable exception) {
			System.err.println("Une exception s'est produite dans main() de java.awt.Frame");
			exception.printStackTrace(System.out);
		}
	}
}