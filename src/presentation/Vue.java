/**
 * @author jobustos
 */
package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import liaison.ICase;
import liaison.ILiaisonMetier;
import liaison.IRobot;
import application.Constantes;
import application.Modele;

/**
 * @author jobustos
 *
 */
public class Vue extends JPanel implements Observer, Constantes {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3494588346268027181L;

	/** le controleur. */
	private Controleur controleur ;

	/** les labels (cases). */
	private Map<String, JLabel> lesCases = new HashMap<>() ; 
	
	/** les info robots .*/
	private Map<String, JLabel> infoRobots = new HashMap<>() ;
	
	@SuppressWarnings("unused")
	private int nbLignes ;
	@SuppressWarnings("unused")
	private int nbColonnes ;


	/**
	 * Constructeur de la vue.
	 * @param controleur 
	 * @param modele 
	 * @param liaison 
	 */
	public Vue (Modele modele, Controleur controleur, int nbLignes,int nbColonnes, ILiaisonMetier liaison) 
	{
		this.nbColonnes = nbColonnes ;
		this.nbLignes = nbLignes ;

		this.controleur = controleur ;
		modele.addObserver(this) ;

		setLayout(new BorderLayout()) ;

		add(nouvelleGrille(nbLignes, nbColonnes, liaison), BorderLayout.CENTER) ;
		add(nouveauPanneauCommande(),BorderLayout.EAST) ;
		add(nouveauPanneauInfo(liaison), BorderLayout.SOUTH) ;

	}


	/**
	 * Permet de creer le panneau des commandes.
	 * @return le panneau des commandes.
	 */
	private Component nouveauPanneauCommande()
	{
		JPanel pan = new JPanel(new BorderLayout()) ;
		
		pan.add(nouveauPanneauBouton(), BorderLayout.NORTH);

		return pan ;
	}

	/**
	 * Permet de creer le panneau d'information.
	 * @param liaison 
	 * @return le panneau d'information.
	 */
	private Component nouveauPanneauInfo(ILiaisonMetier liaison) {
		Box pan = Box.createVerticalBox() ;
		
		int nbRobots = liaison.getListeRobots().size() ;
		
		for (int r=1; r<=nbRobots; r++)
		{
			pan.add(nouveauLabelRobot(r)) ;
		}
		
		return pan ;
	}

	/**
	 * Permet de creer un label pour les infos du robot.
	 * @param le numero du robot.
	 * @return un label pour les infos du robot.
	 */
	private Component nouveauLabelRobot(int num) {
		String inf = ROBOT+num ;
		JLabel label = new JLabel(inf + ": ") ;
		
		infoRobots.put(inf,label) ;
		
		return infoRobots.get(inf) ;
	}


	/**
	 * 
	 * @return
	 */
	private Component nouveauPanneauBouton() {
		JPanel pan = new JPanel() ;
		
		pan.setLayout(new GridLayout(3,1,10,10));

		pan.add(nouveauBouton(LANCER));
		pan.add(nouveauBouton(ARRETER));
		pan.add(nouveauBouton(QUITTER));
		
		return pan;
	}


	/**
	 * Permet de creer un bouton.
	 * @param name le nom du bouton.
	 * @return le nouveau bouton.
	 */
	private Component nouveauBouton(String name)
	{
		JButton bouton = new JButton(name) ;

		bouton.setActionCommand(name) ;
		bouton.addActionListener(controleur);

		return bouton ;
	}


	/**
	 * Permet de creer le panneau de la grille du damier.
	 * @param liaison 
	 * @return le panneau de la grille du damier.
	 */
	private Component nouvelleGrille(int nbLignes,int nbColonnes, ILiaisonMetier liaison)
	{
		JPanel pan = new JPanel() ;

		pan.setLayout(new GridLayout(nbLignes,nbColonnes));
		pan.setBorder(BorderFactory.createLineBorder(Color.black, 1));

		for (int ligne = 0 ; ligne < nbLignes ; ligne++)
			for (int colonne = 0 ; colonne<nbColonnes ; colonne++)
			{
				pan.add(nouveauLabel(liaison, ligne, colonne)) ;

			}

		return pan ;
	}



	/**
	 * Permet de creer une case (jlabel).
	 * @param liaison la liaison metier.
	 * @param ligne l'index de la ligne de la case.
	 * @param colonne l'index de la colonne de la case.
	 * @return une case (jlabel).
	 */
	private Component nouveauLabel(ILiaisonMetier liaison, int ligne, int colonne)
	{
		JLabel label = new JLabel() ;

		ICase iCase = liaison.getCase(liaison.getCle(ligne, colonne)) ;

		label.setText(iCase.getValeur());
		label.setOpaque(true);
		label.setBackground(iCase.getCouleur());
		
		label.setVerticalAlignment((int) CENTER_ALIGNMENT);
		label.setHorizontalAlignment((int) CENTER_ALIGNMENT);

		label.setBorder(BorderFactory.createLineBorder(Color.gray, 1));

		lesCases.put(iCase.getName(),label);

		return lesCases.get(iCase.getName());
	}


	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable source, Object info)
	{
		if (source instanceof Modele)
		{
			if (info instanceof ICase )
			{
				ICase iCase = (ICase) info ;
				lesCases.get(iCase.getName()).setText(iCase.getValeur());
				lesCases.get(iCase.getName()).setBackground(iCase.getCouleur());
								
				repaint();
			}
			
			if (info instanceof IRobot)
			{
				IRobot iRobot = (IRobot) info ;
				infoRobots.get(iRobot.getName()).setText(iRobot.getName() + ": " + iRobot.getListeSauvees());
			}
		}
	}

}
