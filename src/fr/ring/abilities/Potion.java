package fr.ring.abilities;

import java.util.Random;

import fr.ring.actors.BaseCharacter;

/**
 * Classe permettant de créer des Sorts de soin.
 * 
 * @author Rémi Piérot
 * @author Myrvete Hatoum
 * @version 1.0
 */
public class Potion extends Ability implements Heal, Melee {
	
	private static final long serialVersionUID = -9157231606295074224L;

	/**
	 * Constructeur reliant un personnage au Remède créé.
	 * 
	 * @param character Personnage à relier au remède
	 * 
	 * @see Ability#Ability(BaseCharacter)
	 */
	public Potion(BaseCharacter character){
		
		super(character);
	}
	
	/**
	 * Constructeur copiant les statistiques d'un Remède pour relier la nouvelle instance au personnage reçu en paramètre.
	 * 
	 * @param character Personnage à relier au remède
	 * @param potion Remède à copier (seulement les statistiques)
	 * 
	 * @see Ability#Ability(BaseCharacter, Ability)
	 */
	public Potion(BaseCharacter character, Potion potion){
		
		super(character, potion);
	}
	
	@Override
	public boolean isHealSuccessful() {
		
		// On commence par calculer la probabilité de succès selon cette formule.
		int maxStat = SUM_STATS - ((this.getStats().size()-1)*MIN_STAT);
		double coefProba = (double)(this.getCharacter().getDexterity()*this.getStats().get(FIRST_STAT));
		double probability = coefProba / SUCCESS_DIVIDER;
		
		// Puis on génère une valeur aléatoire située dans la même tranche que le succès potentiel.
		Random r = new Random();
		int coefRand = MAX_DEXTERITY*maxStat;
		double rand = (double)r.nextInt(coefRand);
		double success = rand / SUCCESS_DIVIDER;
		
		// Si le nombre aléatoire est inférieur à la probabilité calculée, c'est un succès, sinon c'est un échec.
		return (success <= probability);
	}

	@Override
	public int getHealPower() {
		
		int power = 0;
		
		// Si la capacité réussit, on calcule sa puissance.
		if(isHealSuccessful())
			power = (this.getCharacter().getDexterity()*this.getStats().get(SECOND_STAT)) / POWER_DIVIDER; 

		return power;
	}
	
	@Override
	public int getPower(int type, Ability ability){
		
		int power = 0;
		
		if(type == HEAL)
			power = getHealPower();
		
		return power;
	}
	
	@Override
	public String toString(){
		
		String s = new String();
		
		s += "Remède : ";
		s += "\n- Facilité : " + getStats().get(FIRST_STAT);
		s += "\n- Efficacité : " + getStats().get(SECOND_STAT);
		
		return s;
	}
	
	@Override
	public Ability copy(BaseCharacter character) {

		return new Potion(character, this);
	}
}