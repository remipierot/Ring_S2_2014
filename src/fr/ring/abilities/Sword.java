package fr.ring.abilities;

import java.util.Random;

import fr.ring.actors.BaseCharacter;

/**
 * Classe permettant de créer des Epées.
 * 
 * @author Rémi Piérot
 * @author Myrvete Hatoum
 * @version 1.0
 */
public class Sword extends Ability implements Attack, Parry, Melee {

	private static final long serialVersionUID = 2877782899975548602L;
	
	/**
	 * Valeur = 2, Code de la troisième statistique de la liste, utilisée pour calculer la puissance d'une parade.
	 */
	public static final int THIRD_STAT = 2;
	
	/**
	 * Constructeur reliant un personnage à l'Epée créée.
	 * On initialise également la liste de statistiques avec 3 statistiques tirées au hasard.
	 * 
	 * @param character Personnage à relier à l'épée
	 * 
	 * @see Ability#Ability(BaseCharacter)
	 */
	public Sword(BaseCharacter character){
		
		super(character);
		
		Random r = new Random();
		int firstStat = r.nextInt(SUM_STATS - 3*MIN_STAT + 1) + MIN_STAT;
		this.getStats().set(FIRST_STAT, firstStat);
		int secondStat = r.nextInt(SUM_STATS - firstStat - 2*MIN_STAT + 1) + MIN_STAT;
		this.getStats().set(SECOND_STAT, secondStat);
		
		this.getStats().add(SUM_STATS - (firstStat+secondStat));
	}
	
	/**
	 * Constructeur copiant les statistiques d'une Epée pour relier la nouvelle instance au personnage reçu en paramètre.
	 * 
	 * @param character Personnage à relier à l'épée
	 * @param sword Epée à copier (seulement les statistiques)
	 * 
	 * @see Ability#Ability(BaseCharacter, Ability)
	 */
	public Sword(BaseCharacter character, Sword sword){
		
		super(character, sword);
	}

	@Override
	public boolean isAttackSuccessful() {
		
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
	public int getAttackPower() {
		
		int power = 0;
		
		// Si la capacité réussit, on calcule sa puissance.
		if(isAttackSuccessful())
			power = (this.getCharacter().getStrength()*this.getStats().get(SECOND_STAT)) / POWER_DIVIDER; 

		return power;
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
			power = (this.getCharacter().getStrength()*this.getStats().get(THIRD_STAT)) / POWER_DIVIDER; 
		
		// Si on tente de bloquer une capacité spirituelle, on divise l'efficacité de notre blocage par 3.
		if(ability instanceof Spell)
			power /= 3;

		return power;
	}
	
	@Override
	public int getPower(int type, Ability ability){
		
		int power = 0;
		
		switch(type){
			case ATTACK :
					power = getAttackPower();
					break;
					
			case PARRY :
					power = getParryPower(ability);
					break;
		}
		
		return power;
	}
	
	@Override
	public String toString(){
		
		String s = new String();
		
		s += "Epée : ";
		s += "\n- Maniabilité : " + getStats().get(FIRST_STAT);
		s += "\n- Impact : " + getStats().get(SECOND_STAT);
		s += "\n- Parade : " + getStats().get(THIRD_STAT);
		
		return s;
	}
	
	@Override
	public Ability copy(BaseCharacter character) {

		return new Sword(character, this);
	}
}
