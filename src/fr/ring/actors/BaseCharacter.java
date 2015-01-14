package fr.ring.actors;

import java.io.Serializable;
import java.util.ArrayList;

import fr.ring.abilities.Ability;

/**
 * Classe servant de base à tous les personnages, quel que soit le type.
 * Implémente Serializable pour faciliter la sauvegarde et le chargement.
 * 
 * @see fr.ring.engine.FileHandler
 *
 * @author Myrvete Hatoum
 * @author Rémi Piérot
 * @version 1.0
 */
public class BaseCharacter implements CharacterConstants, Serializable{
	
	private static final long serialVersionUID = -6871311008931564093L;
	private String name;
	private int strength;
	private int dexterity;
	private int intelligence;
	private int spirit;
	private int experience;
	private int health;
	private ArrayList<Ability> abilities;
	
	/**
	 * Constructeur par défaut.
	 * Génère un personnage sans nom, avec une expérience de 1 et aucune statistique ni capacité.
	 */
	public BaseCharacter(){
		
		name = new String();
		experience = getMinStat(EXPERIENCE);
		abilities = new ArrayList<Ability>();
		strength = getMinStat(STRENGTH);
		dexterity = getMinStat(DEXTERITY);
		intelligence = getMinStat(INTELLIGENCE);
		spirit = getMinStat(SPIRIT);
		setMaxHealth();
	}
	
	/**
	 * Permet de récupérer le nom.
	 * 
	 * @return Nom du personnage
	 */
	public String getName() {
		
		return name;
	}
	
	/**
	 * Remplace le nom actuel par celui reçu en paramètre.
	 * 
	 * @param name Nouveau nom
	 */
	public void setName(String name) {
		
		this.name = name;
	}
	
	/**
	 * Permet de récupérer la force.
	 * 
	 * @return Force du personnage
	 */
	public int getStrength() {
		
		return strength;
	}
	
	/**
	 * Remplace la force actuelle par celle reçue en paramètre si elle est conforme, lève une exception sinon.
	 * 
	 * @param strength Nouvelle force
	 * @throws StatException Exception levée si paramètre invalide
	 */
	public void setStrength(int strength) throws StatException{
		
		if((strength < getMinStat(STRENGTH)) || (strength > getMaxStat(STRENGTH))) 
			throw new StatException(StatException.BASIC_MESSAGE, STRENGTH, strength);
			
		this.strength = strength;
	}
	
	/**
	 * Permet de récupérer la dextérité.
	 * 
	 * @return Dextérité du personnage.
	 */
	public int getDexterity() {
		
		return dexterity;
	}
	
	/**
	 * Remplace la dextérité actuelle par celle reçue en paramètre si elle est conforme, lève une exception sinon.
	 * 
	 * @param dexterity Nouvelle dextérité
	 * @throws StatException Exception levée si paramètre invalide
	 */
	public void setDexterity(int dexterity) throws StatException{
		
		if((dexterity < getMinStat(DEXTERITY)) || (dexterity > getMaxStat(DEXTERITY))) 
			throw new StatException(StatException.BASIC_MESSAGE, DEXTERITY, dexterity);
		
		this.dexterity = dexterity;
	}
	
	/**
	 * Permet de récupérer l'intelligence.
	 * 
	 * @return Intelligence du personnage
	 */
	public int getIntelligence() {
		
		return intelligence;
	}
	
	/**
	 * Remplace l'intelligence actuelle par celle reçue en paramètre si elle est conforme, lève une exception sinon.
	 * 
	 * @param intelligence Nouvelle intelligence
	 * @throws StatException Exception levée si paramètre invalide
	 */
	public void setIntelligence(int intelligence) throws StatException {
		
		if((intelligence < getMinStat(INTELLIGENCE)) || (intelligence > getMaxStat(INTELLIGENCE))) 
			throw new StatException(StatException.BASIC_MESSAGE, INTELLIGENCE, intelligence);
		
		this.intelligence = intelligence;
	}
	
	/**
	 * Permet de récupérer la concentration.
	 * 
	 * @return Concentration du personnage
	 */
	public int getSpirit() {
		
		return spirit;
	}
	
	/**
	 * Remplace la concentration actuelle par celle reçue en paramètre si elle est conforme, lève une exception sinon.
	 * 
	 * @param spirit Nouvelle concentration
	 * @throws StatException Exception levée si paramètre invalide
	 */
	public void setSpirit(int spirit) throws StatException {
		
		if((spirit < getMinStat(SPIRIT)) || (spirit > getMaxStat(SPIRIT))) 
			throw new StatException(StatException.BASIC_MESSAGE, SPIRIT, spirit);
		
		this.spirit = spirit;
	}
	
	/**
	 * Permet de récupérer l'expérience.
	 * 
	 * @return Expérience du personnage
	 */
	public int getExperience() {
		
		return experience;
	}
	
	/**
	 * Remplace l'expérience actuelle par celle reçue en paramètre si elle est conforme, lève une exception sinon.
	 * 
	 * @param experience Nouvelle expérience
	 * @throws StatException Exception levée si paramètre invalide
	 */
	public void setExperience(int experience) throws StatException {
		
		if((experience < getMinStat(EXPERIENCE)) || (experience > getMaxStat(EXPERIENCE)))
			throw new StatException(StatException.BASIC_MESSAGE, EXPERIENCE, experience);
		
		this.experience = experience;
	}
	
	/**
	 * Permet de récupérer la vitalité.
	 * 
	 * @return Vitalité du personnage
	 */
	public int getHealth() {
		
		return health;
	}
	
	/**
	 * Remplace la vitalité actuelle par celle reçue en paramètre si elle est conforme, lève une exception sinon.
	 * 
	 * @param health Nouvelle vitalité
	 * @throws StatException Exception levée si paramètre invalide
	 */
	public void setHealth(int health) throws StatException {
		
		if(health < getMinStat(HEALTH) || health > getMaxStat(HEALTH))
			throw new StatException(StatException.BASIC_MESSAGE, HEALTH, health);
	
		this.health = health;
	}
	
	/**
	 * Permet de récupérer la liste de capacités.
	 * 
	 * @return Liste de capacités du personnage
	 */
	public ArrayList<Ability> getAbilities() {
		
		return abilities;
	}
	
	/**
	 * Ajoute la capacité recue en paramètre à la liste de capacité si sa taille reste conforme.
	 * 
	 * @param ability Capacité à ajouter
	 */
	public void addAbility(Ability ability) {
		
		if(abilities.size() < experience/2 || abilities.size() < MIN_ABILITIES)
			abilities.add(ability);
	}
	
	/**
	 * Supprime la capacité reçue en paramètre si elle se trouve dans la liste.
	 * 
	 * @param ability Capacité à supprimer
	 */
	public void removeAbility(Ability ability){
		
		if(abilities.contains(ability))
			abilities.remove(ability);
	}
	
	/**
	 * Permet de récupérer la capacité en position n.
	 * 
	 * @param n Position de la capacité souhaitée.
	 * @return Capacité à la position n.
	 */
	public Ability getAbility(int n){
		
		return abilities.get(n);
	}

	/**
	 * Place la vitalité à son maximum selon la formule suivante :
	 * health = ADD_HEALTH - strength - dexterity - intelligence - spirit + 3*experience;
	 * 
	 * @see #ADD_HEALTH
	 */
	public void setMaxHealth(){
		
		health = ADD_HEALTH - strength - dexterity - intelligence - spirit + 3*experience;
	}
	
	/**
	 * Le personnage subit les dégâts reçus en paramètre.
	 * Si les dégâts dépassent sa vitalité minimale, les points subis se limitent à la-dite vitalité minimale.
	 * 
	 * @param power Dégâts à subir
	 */
	public void takeDamage(int power){
		
		try{
			setHealth(health - power);
		}
		catch(StatException e){
			health = DEAD;
		}
	}
	
	/**
	 * Le personnage se soigne de la quantité reçue en paramètre.
	 * Si le soin dépasse sa vitalité maximale, la vie rendue se limite à la-dite vitalité maximale.
	 * 
	 * @param power Soin à appliquer
	 */
	public void takeHeal(int power ){
		
		try{
			setHealth(health + power);
		}
		catch(StatException e){
			setMaxHealth();
		}
	}
	
	/**
	 * Permet de savoir si les statistiques sont valides.
	 * 
	 * @return Validité des statistiques (force dextérité intelligence concentration)
	 */
	public boolean statsBalanced(){
		
		return strength + dexterity + intelligence + spirit <= ADD_STAT + experience;
	}
	
	/**
	 * Permet de récupérer l'état des données du personnage.
	 * 
	 * @return Etat des données
	 */
	public String toString(){
		
		String s = "Nom: " + this.name + "\n"
					+ "Force: " + this.strength + "\n"
					+ "Dextérité: " + this.dexterity + "\n" 
					+ "Intelligence: " + this.intelligence + "\n"
					+ "Concentration: " + this.spirit + "\n"
					+ "Expérience: " + this.experience + "\n"
					+ "Vitalité: " + this.health;
		return s;
	}
	
	/**
	 * Utilisé à l'initialisation des personnages et à la modification des statistiques.
	 * Permet de connaitre le nombre de points maximal que l'on peut dépenser dans la statistique reçue en paramètre.
	 * 
	 * @param statID Identifiant de la statistique visée
	 * @return Nombre de points restants
	 * 
	 * @see CharacterConstants#STRENGTH
	 * @see CharacterConstants#DEXTERITY
	 * @see CharacterConstants#INTELLIGENCE
	 * @see CharacterConstants#SPIRIT
	 * @see CharacterConstants#EXPERIENCE
	 * @see CharacterConstants#HEALTH
	 * @see CharacterConstants#ABILITIES
	 * @see CharacterConstants#STATS
	 */
	public int getMaxStat(int statID){
		int maxStat = 0;
		
		switch(statID){
			case STRENGTH : 
					maxStat = ADD_STAT + experience - dexterity - intelligence - spirit;
					break;
				
			case DEXTERITY : 
					maxStat = ADD_STAT + experience - strength - intelligence - spirit;
					break;
				
			case INTELLIGENCE :
					maxStat = ADD_STAT + experience - strength - dexterity - spirit;
					break;
				
			case SPIRIT :
					maxStat = ADD_STAT + experience - strength - dexterity - intelligence;
					break;
					
			case EXPERIENCE :
					maxStat = MAX_EXPERIENCE;
					break;
					
			case HEALTH :
					maxStat = ADD_HEALTH - strength - dexterity - intelligence - spirit + 3*experience;
					break;
					
			case ABILITIES :
					maxStat = MIN_ABILITIES;
					
					if(experience/2 >= MIN_ABILITIES)
						maxStat = experience/2;
					break;
					
			case STATS :
					maxStat = ADD_STAT + experience - strength - dexterity - intelligence - spirit;
					break;
		}
		
		return maxStat;
	}
	
	/**
	 * Utilisé à l'initialisation des personnages et à la modification des statistiques.
	 * Permet de connaitre le nombre de points minimal que l'on peut dépenser dans la statistique reçue en paramètre.
	 * 
	 * @param statID Identifiant de la statistique visée
	 * @return Nombre de points minimum
	 * 
	 * @see CharacterConstants#STRENGTH
	 * @see CharacterConstants#DEXTERITY
	 * @see CharacterConstants#INTELLIGENCE
	 * @see CharacterConstants#SPIRIT
	 * @see CharacterConstants#EXPERIENCE
	 * @see CharacterConstants#HEALTH
	 * @see CharacterConstants#ABILITIES
	 * @see CharacterConstants#STATS
	 */
	public int getMinStat(int statID){
		int minStat = 0;
		
		switch(statID){
			case STRENGTH : 
					minStat = MIN_STAT;
					break;
				
			case DEXTERITY : 
					minStat = MIN_STAT;
					break;
				
			case INTELLIGENCE :
					minStat = MIN_STAT;
					break;
				
			case SPIRIT :
					minStat = MIN_STAT;
					break;
					
			case EXPERIENCE :
					minStat = MIN_EXPERIENCE;
					break;
					
			case HEALTH :
					minStat = DEAD;
					break;
					
			case ABILITIES :
					minStat = MIN_ABILITIES;
					break;
		}
		
		return minStat;
	}
	
	/**
	 * Permet de savoir si le personnage est mort.
	 * 
	 * @return true si personnage en vie, false sinon
	 */
	public boolean isAlive(){
		
		return health != DEAD;
	}
}
