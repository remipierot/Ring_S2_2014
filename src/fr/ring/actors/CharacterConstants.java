package fr.ring.actors;


/**
 * Interface contenant les constantes reliées aux personnages.
 * 
 * @author Myrvete Hatoum
 * @author Rémi Piérot
 * @version 1.0
 */
public interface CharacterConstants{

	/**
	 * Valeur = 1, Minimum d'expérience pour un personnage.
	 */
	public static final int MIN_EXPERIENCE = 1;
	
	/**
	 * Valeur = 20, Maximum d'expérience pour un personnage.
	 */
	public static final int MAX_EXPERIENCE = 20;
	
	/**
	 * Valeur = 100, A ajouter à l'expérience pour vérifier la validité des caractéristiques.
	 */
	public static final int ADD_STAT = 100;
	
	/**
	 * Valeur = 200, Vie de base (stats toutes à 0 et pas d'expérience) servant au calcul de la vitalité.
	 */
	public static final int ADD_HEALTH = 200;
	
	/**
	 * Valeur = 0, Vitalité minimale d'un personnage, arrivé à ce stade il meurt.
	 */
	public static final int DEAD = 0;
	
	/**
	 * Valeur = 0, Minimum possible d'une caractéristique.
	 */
	public static final int MIN_STAT = 0;
	
	/**
	 * Valeur = 2, Minimum de capacités possédées par un personnage.
	 */
	public static final int MIN_ABILITIES = 2;
	
	/**
	 * Valeur = 10, Maximum de capacités possédées par un personnage (s'il est niveau 20).
	 */
	public static final int MAX_ABILITIES = (MAX_EXPERIENCE/2);
	
	/**
	 * Valeur = 0, Permet d'identifier la Force.
	 */
	public static final int STRENGTH = 0;
	
	/**
	 * Valeur = 1, Permet d'identifier la Dextérité..
	 */
	public static final int DEXTERITY = 1;
	
	/**
	 * Valeur = 2, Permet d'identifier l'Intelligence.
	 */
	public static final int INTELLIGENCE = 2;
	
	/**
	 * Valeur = 3, Permet d'identifier la Concentration.
	 */
	public static final int SPIRIT = 3;
	
	/**
	 * Valeur = 4, Permet d'identifier l'Expérience.
	 */
	public static final int EXPERIENCE = 4;
	
	/**
	 * Valeur = 5, Permet d'identifier la Vitalité.
	 */
	public static final int HEALTH = 5;
	
	/**
	 * Valeur = 6, Permet d'identifier les Capacités.
	 */
	public static final int ABILITIES = 6;
	
	/**
	 * Valeur = 7, Permet d'identifier les points que l'on peut encore dépenser dans les statistiques.
	 */
	public static final int STATS = 7;
}
