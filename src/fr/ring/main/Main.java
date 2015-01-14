package fr.ring.main;

import fr.ring.console.Menu;

/**
 * Classe contenant le Main.
 * 
 * @author Myrvete Hatoum
 * @author Rémi Piérot
 * @version 1.0
 */
public class Main {
	
	public static void main(String[] args){
		
		// Tant que l'utilisateur n'a pas demandé à quitter, la boucle tourne.
		do{
			Menu.playing();
		}while(!Menu.isEnded());
		
		System.exit(0);
	}

}
