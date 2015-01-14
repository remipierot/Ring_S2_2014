package fr.ring.abilities;

import java.util.Random;

import fr.ring.actors.BaseCharacter;

/**
 * Classe permettant de créer des Sorts offensifs.
 * 
 * @author Rémi Piérot
 * @author Myrvete Hatoum
 * @version 1.0
 */
public class OffensiveSpell extends Ability implements Attack, Spell {
	
	private static final long serialVersionUID = 6977603452040709128L;

	/**
	 * Constructeur reliant un personnage au Sort offensif créé.
	 * 
	 * @param character Personnage à relier au sort
	 * 
	 * @see Ability#Ability(BaseCharacter)
	 */
	public OffensiveSpell(BaseCharacter character){
		
		super(character);
	}
	
	/**
	 * Constructeur copiant les statistiques d'un Sort offensif pour relier la nouvelle instance au personnage reçu en paramètre.
	 * 
	 * @param character Personnage à relier au sort
	 * @param offensiveSpell Sort à copier (seulement les statistiques)
	 * 
	 * @see Ability#Ability(BaseCharacter, Ability)
	 */
	public OffensiveSpell(BaseCharacter character, OffensiveSpell offensiveSpell){
		
		super(character, offensiveSpell);
	}
	
	@Override
	public boolean isAttackSuccessful() {
		
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
	public int getAttackPower() {
		
		int power = 0;
		
		// Si la capacité réussit, on calcule sa puissance.
		if(isAttackSuccessful())
			power = (this.getCharacter().getIntelligence()*this.getStats().get(SECOND_STAT)) / POWER_DIVIDER; 

		return power;
	}
	
	@Override
	public int getPower(int type, Ability ability){
		
		int power = 0;
		
		if(type == ATTACK)
			power = getAttackPower();
		
		return power;
	}
	
	@Override
	public String toString(){
		
		String s = new String();
		
		s += "Sort offensif : ";
		s += "\n- Facilité : " + getStats().get(FIRST_STAT);
		s += "\n- Efficacité : " + getStats().get(SECOND_STAT);
		
		return s;
	}
	
	@Override
	public Ability copy(BaseCharacter character) {

		return new OffensiveSpell(character, this);
	}
}