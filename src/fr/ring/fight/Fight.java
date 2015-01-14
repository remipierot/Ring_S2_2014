package fr.ring.fight;

import java.util.ArrayList;

import fr.ring.abilities.Ability;
import fr.ring.abilities.AbilityConstants;
import fr.ring.abilities.Attack;
import fr.ring.actors.BaseCharacter;
import fr.ring.actors.CharacterConstants;
import fr.ring.actors.StatException;
import fr.ring.engine.Engine;
import fr.ring.engine.FileHandler;

/**
 * Classe destinée à gérer les combats.
 * 
 * @author Rémi Piérot
 * @author Myrvete Hatoum
 * @version 1.0
 */
public class Fight implements FightConstants{
	private BaseCharacter pendingCharacter;
	private BaseCharacter currentCharacter;
	private ArrayList<Ability> pendingAbilities;
	private ArrayList<Ability> currentAbilities;
	private int pendingAttack;
	private int currentPower;
	private int currentAttack;
	private boolean turn;
	private int round;
	private int turnID;
	private boolean dead;
	private boolean forfait;
	
	/**
	 * Constructeur utilisé pour initialiser un combat avec les 2 personnages reçus en paramètre.
	 * Vérification de la validité du paramètre puis construction du combat.
	 * 
	 * @param baseCharacters Personnages prenant part au combat
	 */
	public Fight(ArrayList<BaseCharacter> baseCharacters){
		
		if(baseCharacters.size() == CHARACTERS_IN_FIGHT){
			
			int xp1 = baseCharacters.get(FIRST_CHARACTER).getExperience();
			int xp2 = baseCharacters.get(SECOND_CHARACTER).getExperience();
			int random = (int)(100*Math.random())/50;
			
			// Si le premier perso a plus d'xp, c'est lui qui commence le combat.
			if(xp1 > xp2){
				currentCharacter = baseCharacters.get(FIRST_CHARACTER);
				pendingCharacter = baseCharacters.get(SECOND_CHARACTER);
			}
			// Dans le cas contraire, c'est le second perso qui commence.
			else if(xp1 < xp2){
				currentCharacter = baseCharacters.get(SECOND_CHARACTER);
				pendingCharacter = baseCharacters.get(FIRST_CHARACTER);
			}
			// Si les deux persos ont autant d'xp, alors le hasard décide de qui commence.
			else{
				currentCharacter = baseCharacters.get(random);
				pendingCharacter = baseCharacters.get(SECOND_CHARACTER-random);
			}
			
			//On initialise la vie des combattants à son maximum.
			currentCharacter.setMaxHealth();
			pendingCharacter.setMaxHealth();
			
			// Ces listes de capacités serviront à stocker les coups choisis.
			pendingAbilities = new ArrayList<Ability>();
			pendingAbilities.add(null);
			pendingAbilities.add(null);
			
			currentAbilities = new ArrayList<Ability>();
			currentAbilities.add(null);
			currentAbilities.add(null);
			
			dead = false;
			forfait = false;
			turn = false;
		}
	}

	/**
	 * Permet de récupérer le personnage en attente.
	 * 
	 * @return Personnage en attente
	 */
	public BaseCharacter getPendingCharacter() {
		
		return pendingCharacter;
	}
	
	/**
	 * Permet de récupérer le personnage en action.
	 * 
	 * @return Personnage en action
	 */
	public BaseCharacter getCurrentCharacter() {
		
		return currentCharacter;
	}
	
	/**
	 * Permet de récupérer la puissance de l'attaque en attente (Différent de 0 seulement si il y a une attaque réussie).
	 * 
	 * @return Puissance en attente
	 */
	public int getPendingAttack() {
		
		return pendingAttack;
	}
	
	/**
	 * Permet de récupérer la puissance de la capacité en cours (Différent de 0 seulement si il y a une parade ou un soin réussi).
	 * 
	 * @return Puissance en cours
	 */
	public int getCurrentPower() {
		
		return currentPower;
	}

	/**
	 * Permet de récupérer la puissance de l'attaque en cours (Différent de 0 seulement si il y a une attaque réussie).
	 * 
	 * @return Puissance de l'attaque en cours
	 */
	public int getCurrentAttack() {
		
		return currentAttack;
	}
	
	/**
	 * Permet de récupérer le numéro du round en cours.
	 * 
	 * @return Numéro du round
	 * 
	 * @see #FIRST_ROUND
	 * @see #SECOND_ROUND
	 */
	public int getRound() {
		
		return round;
	}
	
	/**
	 * Permet de récupérer le numéro du tour en cours.
	 * 
	 * @return Numéro du tour
	 * 
	 * @see #START
	 */
	public int getTurnID(){
		
		return turnID;
	}
	
	/**
	 * Permet de récupérer le statut de vie/mort des personnages combat.
	 * 
	 * @return true si un des deux personnages est mort, false sinon
	 */
	public boolean isDead() {
		
		return dead;
	}
	
	/**
	 * Permet de récupérer l'abandon d'un personnage.
	 * 
	 * @return true si un des deux personnages a abandonné, false sinon
	 */
	public boolean getForfait() {
		
		return forfait;
	}

	/**
	 * Remplace l'actuelle puissance de la capacité en attente par celle reçue en paramètre.
	 * 
	 * @param pendingAttack Nouvelle puissance en attente
	 */
	private void setPendingAttack(int pendingAttack) {
		
		this.pendingAttack = pendingAttack;
		
		if(this.pendingAttack < 0)
			this.pendingAttack = 0;
	}
	
	/**
	 * Met à jour le tour de jeu automatiquement en effectuant les permutations nécessaires.
	 */
	private void setTurn() {
		
		turn = !turn;
		pendingAttack = currentAttack;
		currentAttack = 0;
		pendingAbilities = new ArrayList<Ability>(currentAbilities);
		round = FIRST_ROUND;
		
		BaseCharacter temp = currentCharacter;
		currentCharacter = pendingCharacter;
		pendingCharacter = temp;
		
		turnID++;
	}
	
	/**
	 * Joue un round automatiquement.
	 * Les permutations de capacités, calculs de puissance et changement de tour se font automatiquement.
	 * 
	 * @param type Type de capacité utilisé, attaque, soin, parade ou abandon.
	 * @param currentAbility Capacité à utiliser pour ce tour.
	 */
	public void playTurn(int type, Ability currentAbility){
		
		// Si l'utilisateur demande à abandonner, on met forfait à true.
		if(type == FORFAIT)
			forfait = true;
		
		// S'il n'y a ni abandon ni mort, on effectue le tour.
		if(!forfait && !dead){
			
			// Si l'on est au premier tour, on passe directement au second round pour ne faire qu'un seul round.
			if(turnID == START)
				round++;
			
			// On dépose la capacité reçue en paramètre dans l'emplacement correspondant au round dans la liste des capacités courantes.
			currentAbilities.set(round, currentAbility);
			int power = 0;
			
			switch(type){
				// On récupère la puissance de l'attaque et on l'ajoute à currentAttack.
				case AbilityConstants.ATTACK :
							power = currentAbility.getPower(type, null);
							currentAttack += power;
							break;
							
				// On récupère la puissance de la parade la plus faible et on réduit la pendingAttack d'autant de points.
				case AbilityConstants.PARRY :
							for(Ability ability : pendingAbilities){
								if((ability instanceof Attack && (power > currentAbility.getPower(type, ability))) || power == 0)
									power = currentAbility.getPower(type, ability);
							}
							
							currentPower = power;
							
							if(pendingAttack < power)
								currentPower = pendingAttack;
							
							setPendingAttack(pendingAttack-currentPower);
							break;
						
				// On récupère la puissance du soin et on l'effectue.
				case AbilityConstants.HEAL :
							power = currentAbility.getPower(type, null);
							currentPower = power;
							
							if(currentCharacter.getMaxStat(CharacterConstants.HEALTH) - currentCharacter.getHealth() < power)
								currentPower = currentCharacter.getMaxStat(CharacterConstants.HEALTH) - currentCharacter.getHealth();
							
							currentCharacter.takeHeal(currentPower);
							break;
			}
		
			// Si l'on est au premier round, alors on passe au second.
			if(round == FIRST_ROUND)
				round++;
			// Sinon on effectue la fin de tour.
			else{
				// On applique les dégâts contenus dans pendingAttack au personnage courant.
				currentCharacter.takeDamage(pendingAttack);
	
				// Si le personnage courant est mort, on met dead à true et on achève le combat.
				if(!(currentCharacter.isAlive())){
					dead = true;
					endFight();
				}
				// Sinon on passe au tour suivant.
				else
					setTurn();
			}
		}
		// Sinon, s'il y a abandon ou mort, on achève le combat.
		else
			endFight();
	}
	
	/**
	 * Permet d'achever un combat avec les modifications de statistiques nécessaires pour le perdant.
	 */
	private void endFight(){
		// S'il y a abandon, on maintient l'intégrité des statistiques du perdant.
		if(forfait){
			try{
				// On réduit l'expérience du perdant de 1.
				currentCharacter.setExperience(currentCharacter.getExperience()-1);
			}
			catch(StatException e){ }
			
			// Si les statistiques du perdant ne sont plus valides, on les réduit jusqu'à ce que ce soit le cas.
			if(!(currentCharacter.statsBalanced())){
				int random = 0;
				
				do{
					random = (int)(4*Math.random());
					
					switch(random){
						case CharacterConstants.STRENGTH :
												try{
													currentCharacter.setStrength(currentCharacter.getStrength()-1);
												}
												catch(StatException e){ }
												break;
													
						case CharacterConstants.DEXTERITY :
												try{
													currentCharacter.setDexterity(currentCharacter.getDexterity()-1);
												}
												catch(StatException e){ }
												break;
													
						case CharacterConstants.INTELLIGENCE :
												try{
													currentCharacter.setIntelligence(currentCharacter.getIntelligence()-1);
												}
												catch(StatException e){ }
												break;
													
						case CharacterConstants.SPIRIT:
												try{
													currentCharacter.setSpirit(currentCharacter.getSpirit()-1);
												}
												catch(StatException e){ }
												break;
					}
				}while(!(currentCharacter.statsBalanced()));			
			}
			
			// On remet la vitalité du perdant à son maximum.
			currentCharacter.setMaxHealth();
		}
		// Sinon, si le perdant est mort, on le supprime de la liste des personnages jouables.
		else if(dead)
			Engine.engine.removeCharacter(currentCharacter);

		try{
			// On incrémente l'expérience du gagnant (personnage en attente).
			pendingCharacter.setExperience(pendingCharacter.getExperience()+1);
		}
		catch(StatException e){ }
		
		// On remet la vitalité du gagnant à son maximum.
		pendingCharacter.setMaxHealth();
		
		// Puis on sauvegarde.
		FileHandler.save();
	}
	
	/**
	 * Renvoie la référence de la dernière capacité utilisée durant le combat.
	 * 
	 * @return Dernière capacité utilisée.
	 */
	public Ability getLastAbility(){
		
		if(round == FIRST_ROUND)
			return pendingAbilities.get(SECOND_ROUND);
		else
			return currentAbilities.get(FIRST_ROUND);
	}
}
