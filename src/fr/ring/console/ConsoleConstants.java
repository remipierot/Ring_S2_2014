package fr.ring.console;

/**
 * Interface contenant toutes les constantes utilisées par l'affichage console.
 * 
 * @author Rémi Piérot
 * @author Myrvete Hatoum
 * @version 1.0
 */
public interface ConsoleConstants {
	
	/**
	 * Valeur = 1, Permet d'identifier le Guerrier lors du choix du type de combattant.
	 */
	public static final int WAR = 0;
	
	/**
	 * Valeur = 2, Permet d'identifier le Magicien lors du choix du type de combattant.
	 */
	public static final int WIZ = 1;
	
	/**
	 * Valeur = 3, Permet d'identifier l'Athlète lors du choix du type de combattant.
	 */
	public static final int ATH = 2;
	
	/**
	 * Valeur = 12, Taille maximale du nom du combattant.
	 */
	public static final int MAX_NAME_SIZE = 12;
	
	/**
	 * Valeur = 4, Nombre de statistiques à rentrer par l'utilisateur.
	 */
	public static final int NB_STATS = 4;
	
	/**
	 * Valeur = 0, Permet d'identifier l'Epée lors du choix des capacités du combattant.
	 */
	public static final int SWORD = 0;
	
	/**
	 * Valeur = 1, Permet d'identifier le Sort Offensif lors du choix des capacités du combattant.
	 */
	public static final int OFFENSIVE_SPELL = 1;
	
	/**
	 * Valeur = 2, Permet d'identifier le Bouclier lors du choix des capacités du combattant.
	 */
	public static final int SHIELD = 2;
	
	/**
	 * Valeur = 3, Permet d'identifier le Sort Défensif lors du choix des capacités du combattant.
	 */
	public static final int DEFENSIVE_SPELL = 3;
	
	/**
	 * Valeur = 4, Permet d'identifier le Remède lors du choix des capacités du combattant.
	 */
	public static final int POTION = 4;
	
	/**
	 * Valeur = 5, Permet d'identifier le Sort de soin lors du choix des capacités du combattant.
	 */
	public static final int HEALING_SPELL = 5;
	
	/**
	 * Valeur = -1, Utilisé pour indiquer un choix invalide.
	 */
	public static final int WRONG_CHOICE = -1;
	
	/**
	 * Valeur = 0, permet d'identifier une demande de création de personnage dans le Menu.
	 */
	public static final int CREATE = 0;
	
	/**
	 * Valeur = 1, permet d'identifier une demande de chargement dans le Menu.
	 */
	public static final int LOAD = 1;
	
	/**
	 * Valeur = 2, permet d'identifier une demande d'effacement dans le Menu.
	 */
	public static final int ERASE = 2;
	
	/**
	 * Valeur = 3, permet d'identifier une demande de combat dans le Menu.
	 */
	public static final int FIGHT = 3;
	
	/**
	 * Valeur = 4, permet d'identifier une demande de fin de programme dans le Menu.
	 */
	public static final int QUIT = 4;
	
	/**
	 * Utilisé pour clarifier l'affichage avec une chaine de -.
	 */
	public static final String CLEAR = "\n-------------------------------------------------------\n";
}
