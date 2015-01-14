package fr.ring.abilities;

import java.util.Random;

import fr.ring.actors.BaseCharacter;

/**
 * Classe permettant de créer des Sorts défensifs.
 * 
 * @author Rémi Piérot
 * @author Myrvete Hatoum
 * @version 1.0
 */
public class DefensiveSpell extends Ability implements Parry, Spell {
	
	private static final long serialVersionUID = 4499831503650937203L;

	/**
	 * Constructeur reliant un personnage au Sort défensif créé.
	 * 
	 * @param character Personnage à relier au sort
	 * 
	 * @see Ability#Ability(BaseCharacter)
	 */
	public DefensiveSpell(BaseCharacter character){
		
		super(character);
	}
	
	/**
	 * Constructeur copiant les statistiques d'un Sort défensif pour relier la nouvelle instance au personnage reçu en paramètre.
	 * 
	 * @param character Personnage à relier au sort
	 * @param defensiveSpell Sort à copier (seulement les statistiques)
	 * 
	 * @see Ability#Ability(BaseCharacter, Ability)
	 */
	public DefensiveSpell(BaseCharacter character, DefensiveSpell defensiveSpell){
		
		super(character, defensiveSpell);
	}
	
	@Override
	public boolean isParrySuccessful() {
		
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
	public int getParryPower(Ability ability) {
		
		int power = 0;
		
		// Si la capacité réussit, on calcule sa puissance.
		if(isParrySuccessful())
			power = (this.getCharacter().getIntelligence()*this.getStats().get(SECOND_STAT)) / POWER_DIVIDER;
		
		// Si on tente de bloquer une capacité physique, on divise l'efficacité de notre blocage par 3.
		if(ability instanceof Melee)
			power /= 3;

		return power;
	}
	
	@Override
	public int getPower(int type, Ability ability){
		
		int power = 0;
		
		if(type == PARRY)
			power = getParryPower(ability);
		
		return power;
	}
	
	@Override
	public String toString(){
		
		String s = new String();
		
		s += "Sort défensif : ";
		s += "\n- Facilité : " + getStats().get(FIRST_STAT);
		s += "\n- Efficacité : " + getStats().get(SECOND_STAT);
		
		return s;
	}

	@Override
	public Ability copy(BaseCharacter character) {

		return new DefensiveSpell(character, this);
	}
}