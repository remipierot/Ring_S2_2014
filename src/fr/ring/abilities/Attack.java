package fr.ring.abilities;

/**
 * Interface implementée par les capacités de type attaque.
 * 
 * @author Myrvete Hatoum
 * @author Rémi Piérot
 * @version 1.0
 */
public interface Attack{
	
	/**
	 * Vérifie le succès de l'attaque.
	 * 
	 * @return true si l'attaque a réussi, false sinon
	 * 
	 * @see AbilityConstants#FIRST_STAT
	 * @see AbilityConstants#SUCCESS_DIVIDER
	 */
	public abstract boolean isAttackSuccessful();
	
	/**
	 * Permet de récupérer la puissance de l'attaque.
	 * 
	 * @return Puissance de l'attaque
	 * 
	 * @see AbilityConstants#SECOND_STAT
	 * @see AbilityConstants#POWER_DIVIDER
	 */
	public abstract int getAttackPower();
}

