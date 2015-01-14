package fr.ring.abilities;

import fr.ring.actors.BaseCharacter;
import fr.ring.actors.Warrior;
import fr.ring.actors.Wizard;

/**
 * Interface contenant les constantes reliées aux capacités.
 * 
 * @author Myrvete Hatoum
 * @author Rémi Piérot
 * @version 1.0
 */
public interface AbilityConstants{

	/**
	 * Valeur = 100, Valeur obligatoire de la somme des statistiques d'une capacité.
	 */
	public static final int SUM_STATS = 100;
	
	/**
	 * Valeur = 20, Minimum possible d'une statistique de capacité.
	 */
	public static final int MIN_STAT = 20;
	
	/**
	 * Valeur = 0, Code de la première statistique de la liste, utilisée pour calculer le succès de la capacité.
	 */
	public static final int FIRST_STAT = 0;
	
	/**
	 * Valeur = 1, Code de la deuxième statistique de la liste, utilisée pour calculer la puissance de la capacité.
	 */
	public static final int SECOND_STAT = 1;
	
	/**
	 * Valeur = 10000.0, Dénominateur utilisé pour calculer le succès d'une capacité.
	 */
	public static final double SUCCESS_DIVIDER = 10000.0;
	
	/**
	 * Valeur = 100, Dénominateur utilisé pour calculer la puissance d'une capacité.
	 */
	public static final int POWER_DIVIDER = 100;
	
	/**
	 * Valeur = 55, Dextérité maximale d'un Warrior niveau 20 ayant privilégié cette statistique autant que possible.
	 * Utilisée comme référence dans le calcul de succès des capacités physiques, un Warrior étant le plus doué en ce qui concerne le physique.
	 */
	public static final int MAX_DEXTERITY = (BaseCharacter.ADD_STAT + BaseCharacter.MAX_EXPERIENCE - Warrior.ADD_STAT_WARRIOR)/2 ;
	
	/**
	 * Valeur = 105, Concentration maximale d'un Wizard niveau 20 ayant privilégié cette statistique autant que possible.
	 * Utilisée comme référence dans le calcul de succès des capacités spirituelles, un Wizard étant le plus doué en ce qui concerne la magie.
	 */
	public static final int MAX_SPIRIT = (BaseCharacter.ADD_STAT + BaseCharacter.MAX_EXPERIENCE - Wizard.ADD_STAT_WIZARD);
	
	/**
	 * Valeur = 0, Permet d'identifier une attaque dans le getPower, la capacité sera utilisée pour attaquer.
	 * 
	 * @see Ability#getPower(int, Ability)
	 */
	public static final int ATTACK = 0;
	
	/**
	 * Valeur = 1, Permet d'identifier une parade dans le getPower, la capacité sera utilisée pour défendre.
	 * 
	 * @see Ability#getPower(int, Ability)
	 */
	public static final int PARRY = 1;
	
	/**
	 * Valeur = 2, Permet d'identifier un soin dans le getPower, la capacité sera utilisée pour soigner.
	 * 
	 * @see Ability#getPower(int, Ability)
	 */
	public static final int HEAL = 2;
}
