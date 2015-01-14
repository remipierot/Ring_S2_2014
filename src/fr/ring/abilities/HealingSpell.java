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
public class HealingSpell extends Ability implements Heal, Spell {
	
	private static final long serialVersionUID = -2336159735892242934L;

	/**
	 * Constructeur reliant un personnage au Sort de soin créé.
	 * 
	 * @param character Personnage à relier au sort
	 * 
	 * @see Ability#Ability(BaseCharacter)
	 */
	public HealingSpell(BaseCharacter character){
		
		super(character);
	}
	
	/**
	 * Constructeur copiant les statistiques d'un Sort de soin pour relier la nouvelle instance au personnage reçu en paramètre.
	 * 
	 * @param character Personnage à relier au sort
	 * @param healingSpell Sort à copier (seulement les statistiques)
	 * 
	 * @see Ability#Ability(BaseCharacter, Ability)
	 */
	public HealingSpell(BaseCharacter character, HealingSpell healingSpell){
		
		super(character, healingSpell);
	}
	
	@Override
	public boolean isHealSuccessful() {
		
		// On commence par calculer la probabilité de succès selon cette formule.
		int maxStat = SUM_STATS - ((this.getStats().size()-1)*MIN_STAT);
		double coefProba = (double)(this.getCharacter().getSpirit()*this.getStats().get(FIRST_STAT));
		double probability = coefProba / SUCCESS_DIVIDER;
		
		// Puis on génère une valeur aléatoire située dans la même tranche que le succès potentiel.
		Random r = new Random();
		int coefRand = MAX_SPIRIT*maxStat;
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
			power = (this.getCharacter().getIntelligence()*this.getStats().get(SECOND_STAT)) / POWER_DIVIDER; 

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
		
		s += "Sort de soin : ";
		s += "\n- Facilité : " + getStats().get(FIRST_STAT);
		s += "\n- Efficacité : " + getStats().get(SECOND_STAT);
		
		return s;
	}
	
	@Override
	public Ability copy(BaseCharacter character) {

		return new HealingSpell(character, this);
	}
}