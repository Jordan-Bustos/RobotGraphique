/**
 * @author jobustos
 */
package presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import application.Constantes;
import application.Modele;

/**
 * @author jobustos
 *
 */
public class Controleur implements ActionListener, Constantes {

	/** le modele. */
	private Modele modele ;
	
	
	/**
	 * Constructeur du controleur.
	 * @param modele le modele.
	 */
	public Controleur(Modele modele)
	{
		this.modele = modele ;
	}


	@Override
	public void actionPerformed(ActionEvent ae)
	{
		if (ae.getSource() instanceof JButton)
		{
			JButton bouton = (JButton) ae.getSource() ;

			if (bouton.getActionCommand().equals(LANCER))
			{
				modele.lancerJeux() ;
			}
			
			if (bouton.getActionCommand().equals(ARRETER))
			{
				modele.stoperJeux() ;
			}
			
			if (bouton.getActionCommand().equals(QUITTER))
			{
				modele.quitter() ;
			}
			
				
		}
		
		
		
		
	}
	
	

}
