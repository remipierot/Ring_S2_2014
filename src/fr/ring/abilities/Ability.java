package fr.ring.abilities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import fr.ring.actors.BaseCharacter;

/**
 * Classe abstraite servant de base à toute capacité.
 * Implémente Serializable pour faciliter la sauvegarde et le chargement.
 * 
 * @see fr.ring.engine.FileHandler
 * 
 * @author Rémi Piérot
 * @author Myrvete Hatoum
 * @version 1.0
 */
public abstract class Ability implements AbilityConstants, Serializable{
	
	private static final long serialVersionUID = 4112676503520992955L;
	private BaseCharacter character;
	private ArrayList<Integer> stats;
	
	/**
	 * Constructeur reliant un personnage à la capacité créée.
	 * On intialise également la liste de statistiques avec 2 statistiques tirées au hasard.
	 * 
	 * @param character Personnage à relier à la capacité
	 */
	public Ability(BaseCharacter character){
		
		this.character = character;
		stats = new ArrayList<Integer>();
		
		// Tirage au sort des statistiques de la capacité.
		Random r = new Random();
		int firstStat = r.nextInt(SUM_STATS - 2*MIN_STAT + 1) + MIN_STAT;
		this.getStats().add(firstStat);
		this.getStats().add(SUM_STATS-firstStat);
	}
	
	/**
	 * Constructeur copiant les statistiques d'une capacité pour relier la nouvelle instance au personnage reçu en paramètre.
	 * 
	 * @param character Personnage à relier à la capacité
	 * @param ability Capacité à copier (seulement les statistiques)
	 */
	public Ability(BaseCharacter character, Ability ability){
		
		this(character);
		stats = new ArrayList<Integer>(ability.getStats());
	}
	
	/**
	 * Permet de récupérer le personnage rattaché à la capacité.
	 * 
	 * @return Personnage lié à la capacité
	 */
	public BaseCharacter getCharacter(){
		
		return character;
	}

	/**
	 * Permet de récupérer la liste de statistiques de la capacité.
	 * 
	 * @return Statistiques de la capacité
	 */
	public ArrayList<Integer> getStats() {
		
		return stats;
	}
	
	/**
	 * Permet de récupérer la puissance de la capacité selon les paramètres reçus.
	 * 
	 * @param type Code du type de capacité, attaque, soin ou parade
	 * @param ability Capacité reçue, utilisée si jamais on pare un coup
	 * @return Puissance de la capacité
	 * 
	 * @see AbilityConstants#ATTACK
	 * @see AbilityConstants#PARRY
	 * @see AbilityConstants#HEAL
	 * @see Attack#getAttackPower()
	 * @see Parry#getParryPower(Ability)
	 * @see Heal#getHealPower()
	 */
	public abstract int getPower(int type, Ability ability);
	
	/**
	 * Permet de récupérer une copie de la capacité courante en la liant au personnage paramètre.
	 * Evite d'avoir à faire appel au constructeur adéquat pour copier des enfants de Capacité.
	 * 
	 * @param character Personnage à relier à la capacité.
	 * @return Copie de la capacité reliée au personnage paramètre.
	 */
	public abstract Ability copy(BaseCharacter character);
}
