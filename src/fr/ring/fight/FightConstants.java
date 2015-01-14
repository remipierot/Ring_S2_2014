package fr.ring.fight;

/**
 * Interface contenant toutes les constantes destinées au Combat.
 * 
 * @author Rémi Piérot
 * @author Myrvete Hatoum
 * @version 1.0
 */
public interface FightConstants {

	/**
	 * Valeur = 2, Nombre de personnages en combat.
	 */
	public static final int CHARACTERS_IN_FIGHT = 2;
	
	/**
	 * Valeur = 0, Permet d'accéder au premier personnage de la liste reçue par le constructeur.
	 */
	public static final int FIRST_CHARACTER = 0;
	
	/**
	 * Valeur = 1, Permet d'accéder au second personnage de la liste reçue par le constructeur.
	 */
	public static final int SECOND_CHARACTER = 1;
	
	/**
	 * Valeur = 0, Identifiant du premier round d'un tour de jeu.
	 */
	public static final int FIRST_ROUND = 0;
	
	/**
	 * Valeur = 1, Identifiant du second round d'un tour de jeu.
	 */
	public static final int SECOND_ROUND = 1;
	
	/**
	 * Valeur = 0, Valeur de turnID au début du combat.
	 */
	public static final int START = 0;
	
	/**
	 * Valeur = 3, Paramètre envoyé à playTurn() lorsque le joueur souhaite abandonner.
	 */
	public static final int FORFAIT = 3;
	
}
