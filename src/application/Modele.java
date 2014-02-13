/**
 * @author jobustos
 */
package application;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import liaison.ILiaisonMetier;

/**
 * @author jobustos
 *
 */
public class Modele extends Observable implements Constantes, Observer {

	/** la lisaison metier. */
	ILiaisonMetier liaison ;
	
	public Modele(ILiaisonMetier liaison) {
		this.liaison = liaison ;
		liaison.addObserver(this);
	}

	/**
	 * Permet de lancer le jeux.
	 */
	public void lancerJeux() {
		
		liaison.lancer() ;
	}

	/**
	 * Permet d'arreter le jeux.
	 */
	public void stoperJeux() {
		
		liaison.stopper();
	}
	
	@Override
	public void update(Observable source, Object info) {
		setChanged();
		notifyObservers(info);		
	}

	/**
	 * 
	 */
	public void quitter() {

		int rep = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment quitter ?" , "Attention", JOptionPane.WARNING_MESSAGE);
		if (rep == JOptionPane.OK_OPTION)
		{
			setChanged();
			notifyObservers(QUITTER);
		}
	}

	
	
	

}
