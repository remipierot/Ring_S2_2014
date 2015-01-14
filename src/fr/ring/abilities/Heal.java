package fr.ring.abilities;

/**
 * Interface implementée par les capacités de type soin.
 * 
 * @author Myrvete Hatoum
 * @author Rémi Piérot
 * @version 1.0
 */
public interface Heal{

	/**
	 * Vérifie le succès du soin.
	 * 
	 * @return true si le soin a réussi, false sinon
	 * 
	 * @see AbilityConstants#FIRST_STAT
	 * @see AbilityConstants#SUCCESS_DIVIDER
	 */
	public abstract boolean isHealSuccessful();
	
	/**
	 * Permet de récupérer la puissance du soin.
	 * 
	 * @return Puissance du soin
	 * 
	 * @see AbilityConstants#SECOND_STAT
	 * @see AbilityConstants#POWER_DIVIDER
	 */
	public abstract int getHealPower();
}
