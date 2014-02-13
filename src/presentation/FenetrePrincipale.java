/**
 * @author jobustos
 */
package presentation;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import liaison.ILiaisonMetier;
import application.Constantes;
import application.FabriqueLiaisonMetier;
import application.Modele;

/**
 * @author jobustos
 *
 */
public class FenetrePrincipale extends JFrame implements Constantes, Observer {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1419100663826337589L;

	/**
	 * Constructeur de la fenetre principale de l'application.
	 * @param nbColonnes Le nombre de colonnes.
	 * @param nbLignes Le nombre de ligne. 
	 * @param liaison La liaison métier.
	 */
	public FenetrePrincipale(ILiaisonMetier liaison, int nbLignes, int nbColonnes) {
		super(NOM_FENETRE) ;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE) ;
		setSize(600,600);
		
		Modele modele = new Modele(liaison) ;
		Controleur controleur = new Controleur(modele) ;
		
		modele.addObserver(this);
		
		setContentPane(new Vue(modele, controleur, nbLignes,nbColonnes,liaison)) ;
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int nbLignes = 10 ;
		int nbColonnes = 8 ;
		ILiaisonMetier liaison = FabriqueLiaisonMetier.creerMetier(nbLignes, nbColonnes) ;
		new FenetrePrincipale(liaison, nbLignes, nbColonnes) ;

	}

	@Override
	public void update(Observable source, Object info) {
		
		if (source instanceof Modele) 
		{
			if (info.toString().equals(QUITTER))
			{
				dispose();
			}
		}
		
	}
}
