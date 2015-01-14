package fr.ring.console;

import java.util.ArrayList;

import fr.ring.abilities.Ability;
import fr.ring.abilities.AbilityConstants;
import fr.ring.abilities.Attack;
import fr.ring.abilities.Heal;
import fr.ring.abilities.Parry;
import fr.ring.actors.BaseCharacter;
import fr.ring.actors.CharacterConstants;
import fr.ring.engine.FileHandler;
import fr.ring.fight.Fight;
import fr.ring.fight.FightConstants;

/**
 * Classe destinée à gérer les combats dans la console.
 * 
 * @author Rémi Piérot
 * @author Myrvete Hatoum
 * @version 1.0
 */
public class Fighting implements ConsoleConstants, AbilityConstants, FightConstants{
	
	private static Fight fight;
	private static BaseCharacter winner;
	private static BaseCharacter loser;
	
	/**
	 * Intialise un combat avec la liste de personnages reçue en paramètre.
	 * 
	 * @param characters Personnages prenant part au combat.
	 */
	public static void setUpFight(ArrayList<BaseCharacter> characters) {
		
		fight = new Fight(characters);
	}
	
	/**
	 * Lance et joue le combat jusqu'à sa fin.
	 */
	public static void startFight(){
		
		int type = WRONG_CHOICE;
		int oldHP = 0;
		boolean looseLoop = false;
		Ability currentAbility = null;
		BaseCharacter character = null;
		
		// Boucle qui tourne tant qu'il n'y a ni abandon ni mort.
		do{
			character = fight.getCurrentCharacter();
			
			// Affiche les infos concernant le tour et le personnage qui doit jouer.
			System.out.println("------------ Tour "+ (fight.getTurnID()+1)
								+ ", " + character.getName() 
								+ " (" + character.getHealth() + " / " + character.getMaxStat(CharacterConstants.HEALTH) + ")"
								+ ", Round "+ (fight.getRound()+1) + " ------------");
			
			if(fight.getPendingAttack() != 0)
				System.out.println("\nVous allez perdre " + fight.getPendingAttack() + " points de vie après votre tour si vous ne faites rien!");
			
			System.out.println();
			
			// Récupère le choix du joueur concernant le type de capacité à utiliser (Attaque, parade, Soin, Abandon).
			do{
				type = chooseAbilityType();
				
				if(type == FORFAIT)
					looseLoop = Menu.confirm();
				
			}while(!looseLoop && (type == WRONG_CHOICE || type == FORFAIT));
			
			if(looseLoop)
				System.out.println("Vous abandonnez la partie.");
			
			System.out.println(CLEAR);
			
			// Si pas d'abandon, on choisit parmi les capacités correspondant au type souhaité.
			if(type!=FORFAIT){
				currentAbility = chooseAbility(type);
				System.out.println(CLEAR);
			}
			
			oldHP = character.getHealth();
			
			// On joue le tour avec la capacité voulue.
			fight.playTurn(type,currentAbility);
			
			if(!fight.getForfait()){
		
				// Affiche l'efficacité d'une capacité/attaque ou l'échec
				if(fight.getLastAbility() instanceof Heal && fight.getCurrentPower() != 0)
					System.out.println("Votre soin vous rend "+ fight.getCurrentPower() +" points de vie !");
				else if(fight.getLastAbility() instanceof Parry && fight.getCurrentPower() != 0)
					System.out.println("Vous parez "+ fight.getCurrentPower() +" points de dégâts !");
				else if(fight.getRound() == Fight.SECOND_ROUND && fight.getLastAbility() instanceof Attack && fight.getCurrentAttack() != 0)
					System.out.println("Vous vous préparez à infliger " + fight.getCurrentAttack() + " points de dégâts à l'adversaire.");
				else if(fight.getRound() == Fight.FIRST_ROUND && fight.getLastAbility() instanceof Attack && fight.getPendingAttack() != 0)
					System.out.println("Vous vous préparez à infliger " + fight.getPendingAttack() + " points de dégâts à l'adversaire.");
				else
					System.out.println("Votre capacité a échoué.");
				
				System.out.println(CLEAR);
				
				// Affiche la quantité de points de vie perdue si le joueur en perd.
				if(oldHP > character.getHealth()){
					
					System.out.println("Vous perdez " + (oldHP - character.getHealth()) + " points de vie.");
					
					if(character.getHealth() == CharacterConstants.DEAD)
						System.out.println("Ceci cause votre mort.");
					
					System.out.println(CLEAR);
				}
			}
			
		} while(!fight.getForfait() && !fight.isDead());
		
		// On gère la fin du combat puis on sauvegarde les changements.
		victory();
		FileHandler.save();
		
		System.out.print("Félicitations à " + winner.getName() + " pour sa victoire!");
		
	}
		
	
	/**
	 * Fonction permettant de choisir un type de capacité (attaque, parade, soin) ou d'abandonner.
	 */
	private static int chooseAbilityType(){
		
		boolean attack = false;
		boolean parry = false;
		boolean heal = false;
		ArrayList<Integer> index = new ArrayList<Integer>();
		
		// On liste les types possibles en fonction des capacités présentes dans la liste du personnage actuellement en cours de jeu.
		for(Ability ability : fight.getCurrentCharacter().getAbilities()){
			if(ability instanceof Attack)
				attack = true;
			if(ability instanceof Parry)
				parry = true;
			if(ability instanceof Heal)
				heal = true;
		}
	
		System.out.println("Choisissez un type de capacité ou abandonnez :\n");
		
		if(attack){
			System.out.println((ATTACK+1) + ") Attaque");
			index.add(ATTACK);
		}
		
		if(parry){
			System.out.println((PARRY+1) + ") Parade");
			index.add(PARRY);
		}
		
		if(heal){
			System.out.println((HEAL+1) + ") Soin");
			index.add(HEAL);
		}
		
		System.out.println((FORFAIT+1) + ") Abandon");
		index.add(FORFAIT);
		
		System.out.print("\nVotre choix : ");
		
		// Renvoie le choix de l'utilisateur
		return Menu.choice(index);			
	}
	
	/**
	 * Fonction permettant de choisir une capacité en fonction du type reçu en paramètre.
	 * 
	 * @param type Type de la capacité (attaque, parade ou soin)
	 * @return Capacité choisie
	 */
	private static Ability chooseAbility(int type){
		
		ArrayList<Ability> abilities = fight.getCurrentCharacter().getAbilities();
		ArrayList<Integer> index = new ArrayList<Integer>();
		String jump = "";
		boolean printed = false;
		
		// Affiche les capacités de la liste correspondant au choix de l'utilisateur reçu en paramètre.
		for(Ability ability : abilities){
			if(printed)
				jump = "\n";
			
			if(type == ATTACK && ability instanceof Attack){
				System.out.println(jump + (abilities.indexOf(ability)+1) + ") " + ability);
				index.add(abilities.indexOf(ability));
				printed = true;
			}
			else if(type == PARRY && ability instanceof Parry){
				System.out.println(jump + (abilities.indexOf(ability)+1) + ") " + ability);
				index.add(abilities.indexOf(ability));
				printed = true;
			}
			else if(type == HEAL && ability instanceof Heal){
				System.out.println(jump + (abilities.indexOf(ability)+1) + ") " + ability);
				index.add(abilities.indexOf(ability));
				printed = true;
			}
		}
		
		System.out.print("\nVotre choix : ");
		
		// Renvoie la capacité correspondant au choix de l'utilisateur
		return abilities.get(Menu.choice(index));
	}
	
	/**
	 * Fonction gérant la fin de combat.
	 */
	private static void victory(){
		
		int choice = WRONG_CHOICE;
		boolean statLoop = false;
		ArrayList <Integer> index = new ArrayList<Integer>();
		
		// Le perdant est forcément le personnage en cours de jeu et le gagnant celui en attente.
		loser = fight.getCurrentCharacter();
		winner = fight.getPendingCharacter();
		
		// Affiche les informations concernant la fin du combat.
		if(fight.getForfait()){
			System.out.println(winner.getName() + " remporte le combat par forfait de " + loser.getName() + ".");
			System.out.println(loser.getName() + " perd donc un point d'expérience.");
		}
			
		else if(fight.isDead()){
			System.out.println(winner.getName() + " remporte le combat par mort de " + loser.getName() + ".");
			System.out.println(loser.getName() + " disparait de la liste des personnages jouables.");
		}
		
		System.out.println(winner.getName() + " a gagné un point d'expérience.\n");
		
		// Propose au gagnant de distribuer ses points supplémentaires où il le souhaite.
		do{
			statLoop = distributePoints();
		}while(!statLoop && winner.getMaxStat(CharacterConstants.STATS) != 0);
		
		// Propose d'apprendre de nouvelles capacités au gagnant.
		System.out.println("Souhaitez-vous accéder à l'apprentissage de capacité ?\n\n1) Oui\n2) Non (Retour au menu)");
		
		System.out.print("\nVotre choix : ");
		
		index.add(0);
		index.add(1);
		
		choice = Menu.choice(index);
		
		System.out.println(CLEAR);
		
		if(choice == 0)
			learn();
		
		// Gère l'oubli d'une capacité du perdant s'il en connait trop par rapport à son expérience.
		if((loser.getAbilities().size() > loser.getMaxStat(CharacterConstants.ABILITIES)) && fight.getForfait()){
			
			choice = WRONG_CHOICE;
			index = new ArrayList<Integer>();
			
			do{
				statLoop = false;
				System.out.println(loser.getName() + ", vous avez une capacité en trop, choisissez laquelle oublier :");
				
				for(Ability ability : loser.getAbilities()){
					System.out.println("\n" + (loser.getAbilities().indexOf(ability)+1) + ") " + ability);
					index.add(loser.getAbilities().indexOf(ability));
				}
				
				System.out.print("\nVotre choix : ");
				
				choice = Menu.choice(index);
				
				statLoop = Menu.confirm();
				
				if(!statLoop)
					choice = WRONG_CHOICE;
				
			}while(choice == WRONG_CHOICE);
			
			System.out.println(loser.getName() + " oublie :\n\n" + loser.getAbilities().get(choice));
			
			System.out.println(CLEAR);
			
			loser.removeAbility(loser.getAbilities().get(choice));
		}
	}
	
	/**
	 * Fonction gérant la distribution des points du personnage vainqueur.
	 * 
	 * @return false si l'utilisateur n'a pas demandé à quitter, true sinon.
	 */
	private static boolean distributePoints(){
		
		int choice = WRONG_CHOICE;
		int stock = 0;
		int stat = 0;
		ArrayList<Integer> index = new ArrayList<Integer>();
		
		// Affiche les statistiques dans lesquels le gagnant peut encore dépenser des points.
		System.out.println(winner.getName() + " peut encore dépenser " + winner.getMaxStat(CharacterConstants.STATS) + " points dans les statistiques de son choix : \n");
		
		if(winner.getStrength() != winner.getMaxStat(CharacterConstants.STRENGTH)){
			index.add(CharacterConstants.STRENGTH);
			System.out.println((CharacterConstants.STRENGTH+1) + ") Force");
		}
			
		if(winner.getDexterity() != winner.getMaxStat(CharacterConstants.DEXTERITY)){
			index.add(CharacterConstants.DEXTERITY);
			System.out.println((CharacterConstants.DEXTERITY+1) + ") Dextérité");
		}
		
		if(winner.getIntelligence() != winner.getMaxStat(CharacterConstants.INTELLIGENCE)){
			index.add(CharacterConstants.INTELLIGENCE);
			System.out.println((CharacterConstants.INTELLIGENCE+1) + ") Intelligence");
		}
		
		if(winner.getSpirit() != winner.getMaxStat(CharacterConstants.SPIRIT)){
			index.add(CharacterConstants.SPIRIT);
			System.out.println((CharacterConstants.SPIRIT+1) + ") Concentration");
		}
		
		index.add(QUIT);
		System.out.println((QUIT+1) + ") Etape suivante");
		
		System.out.print("\nVotre choix : ");
		
		choice = Menu.choice(index);
		
		// On récupère la valeur actuelle de la statistique pour afficher la tranche de valeurs possible de la statistique par la suite.
		switch(choice){
			case CharacterConstants.STRENGTH :
							stat = winner.getStrength();
							break;
							
			case CharacterConstants.DEXTERITY :
							stat = winner.getDexterity();
							break;
				
			case CharacterConstants.INTELLIGENCE :
							stat = winner.getIntelligence();
							break;
				
			case CharacterConstants.SPIRIT :
							stat = winner.getSpirit();
							break;
		}
		
		System.out.println(CLEAR);
		
		// Si l'utilisateur ne souhaite pas quitter, on lui propose de distribuer les points dans la statistique de son choix.
		if(choice != QUIT){
			System.out.println("Entrez la nouvelle valeur de cette statistique (" + stat + " - " + winner.getMaxStat(choice) + ")");
			
			System.out.print("\nVotre choix : ");
		
			stock = choice;
			
			do{
				
				try{
					choice = Integer.parseInt(Menu.sc.next());
				}
				catch(NumberFormatException e){
					choice = WRONG_CHOICE;
				}
				
				if(choice < stat || choice > winner.getMaxStat(stock)){
					System.out.print("Veuillez entrer une valeur valide : ");
					choice = WRONG_CHOICE;
				}
				
			}while(choice == WRONG_CHOICE);
			
			System.out.println(CLEAR);
			
			switch(stock){
				case CharacterConstants.STRENGTH :
								try{
									winner.setStrength(choice);
								}
								catch(Exception e){ }
								break;
								
				case CharacterConstants.DEXTERITY :
								try{
									winner.setDexterity(choice);
								}
								catch(Exception e){ }
								break;
					
				case CharacterConstants.INTELLIGENCE :
								try{
									winner.setIntelligence(choice);
								}
								catch(Exception e){ }
								break;
					
				case CharacterConstants.SPIRIT :
								try{
									winner.setSpirit(choice);
								}
								catch(Exception e){ }
								break;
			}
		}
		else
			return true;
		
		return false;
	}
	
	/**
	 * Fonction gérant l'apprentissage de capacités par le vainqueur du combat et l'oubli éventuel par le perdant s'il connait trop de capacités pour son niveau.
	 */
	private static void learn(){
		
		int choice = WRONG_CHOICE;
		int stock;
		ArrayList<Integer> index = null;
		
		// On vérifie si le gagnant peut apprendre une nouvelle capacité ou simplement en remplacer une.
		if(winner.getAbilities().size() != winner.getMaxStat(CharacterConstants.ABILITIES))
			System.out.println(winner.getName() + " peut apprendre une nouvelle capacité.\n");
		else{
			index = new ArrayList<Integer>();
			
			System.out.println(winner.getName() + " peut remplacer une de ses capacités par une de celles de l'adversaire.\n\nCapacités de " + winner.getName() + " (vainqueur) : ");
			
			// Liste les capacités du gagnant pour qu'il sélectionne celle à remplacer.
			for(Ability ability : winner.getAbilities()){
				System.out.println("\n----- " + (winner.getAbilities().indexOf(ability)+1) + " -----\n" + ability);
				index.add(winner.getAbilities().indexOf(ability));
			}
			
			System.out.println("\n----- " + (winner.getAbilities().size()+1) + " -----\nRetour au menu");
			index.add(winner.getAbilities().size());
			
			System.out.print("\nVotre choix (remplacer) : ");
			
			choice = Menu.choice(index);
			
			System.out.println(CLEAR);
		}
		
		// Si l'utilisateur n'a pas demandé à retourner au menu, on continue l'apprentissage.
		if(choice != winner.getAbilities().size()){
			index = new ArrayList<Integer>();
			
			stock = choice;
			
			// Liste les capacités du perdant.
			System.out.println("Capacités de " + loser.getName() + " (perdant) : ");
			for(Ability ability : loser.getAbilities()){
				System.out.println("\n----- " + (loser.getAbilities().indexOf(ability)+1) + " -----\n" + ability);
				index.add(loser.getAbilities().indexOf(ability));
			}
			
			System.out.println("\n----- " + (loser.getAbilities().size()+1) + "-----\nRetour au menu");
			index.add(loser.getAbilities().size());
			
			System.out.print("\nVotre choix (apprendre) : ");
			
			choice = Menu.choice(index);
			
			System.out.println(CLEAR);
			
			// Si l'utilisateur ne souhaite pas retourner au menu, on continue.
			if(choice != loser.getAbilities().size()){
				// Récupération d'une copie du sort du perdant choisi rattachée au gagnant.
				Ability ability = loser.getAbilities().get(choice).copy(winner);
				
				// Vérifie si le gagnant ne peut que remplacer une de ses capacités ou en apprendre une nouvelle.
				if(stock != WRONG_CHOICE){
					System.out.println(winner.getName() + " oublie :\n\n" + winner.getAbilities().get(stock) + "\n\nEt il apprend :\n\n" + ability);
					winner.getAbilities().set(stock, ability);
				}
				else{
					System.out.println(winner.getName() + " apprend :\n\n" + ability);
					winner.addAbility(ability);
				}
			}
			else
				System.out.println("Les capacités de " + winner.getName() + " n'ont pas changé.");
		}
		else
			System.out.println("Les capacités de " + winner.getName() + " n'ont pas changé.");
		
		System.out.println(CLEAR);
	}
}