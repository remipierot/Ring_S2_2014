package fr.ring.abilities;

/**
 * Interface implementée par les capacités de type parade.
 * 
 * @author Rémi Piérot
 * @author Myrvete Hatoum
 * @version 1.0
 */
public interface Parry{

	/**
	 * Vérifie le succès de la parade.
	 * 
	 * @return true si la parade a réussi, false sinon
	 * 
	 * @see AbilityConstants#FIRST_STAT
	 * @see AbilityConstants#SUCCESS_DIVIDER
	 */
	public abstract boolean isParrySuccessful();
	
	/**
	 * Permet de récupérer la puissance de la parade.
	 * 
	 * @param ability Capacité à bloquer, utilisé pour diviser l'efficacité par 3 si blocage pas du même type que l'attaque.
	 * @return Puissance de la parade
	 * 
	 * @see AbilityConstants#SECOND_STAT
	 * @see AbilityConstants#POWER_DIVIDER
	 */
	public abstract int getParryPower(Ability ability);
}
