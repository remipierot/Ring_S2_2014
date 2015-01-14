package fr.ring.abilities;

import java.util.Random;

import fr.ring.actors.BaseCharacter;

/**
 * Classe permettant de créer des Boucliers.
 * 
 * @author Rémi Piérot
 * @author Myrvete Hatoum
 * @version 1.0
 */
public class Shield extends Ability implements Parry, Melee {

	private static final long serialVersionUID = -7674159863096831796L;

	/**
	 * Constructeur reliant un personnage au Bouclier créé.
	 * 
	 * @param character Personnage à relier au bouclier
	 * 
	 * @see Ability#Ability(BaseCharacter)
	 */
	public Shield(BaseCharacter character){
		
		super(character);
	}
	
	/**
	 * Constructeur copiant les statistiques d'un Bouclier pour relier la nouvelle instance au personnage reçu en paramètre.
	 * 
	 * @param character Personnage à relier au bouclier
	 * @param shield Bouclier à copier (seulement les statistiques)
	 * 
	 * @see Ability#Ability(BaseCharacter, Ability)
	 */
	public Shield(BaseCharacter character, Shield shield){
		
		super(character, shield);
	}
	
	
	@Override
	public boolean isParrySuccessful() {
		
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
	public int getParryPower(Ability ability) {
		
		int power = 0;
		
		// Si la capacité réussit, on calcule sa puissance.
		if(isParrySuccessful())
			power = (this.getCharacter().getStrength()*this.getStats().get(SECOND_STAT)) / POWER_DIVIDER; 
		
		// Si on tente de bloquer une capacité spirituelle, on divise l'efficacité de notre blocage par 3.
		if(ability instanceof Spell)
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
		
		s += "Bouclier : ";
		s += "\n- Maniabilité : " + getStats().get(FIRST_STAT);
		s += "\n- Protection : " + getStats().get(SECOND_STAT);
		
		return s;
	}
	
	@Override
	public Ability copy(BaseCharacter character) {

		return new Shield(character, this);
	}
}
