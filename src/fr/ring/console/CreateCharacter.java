package fr.ring.console;

import java.util.ArrayList;

import fr.ring.abilities.Ability;
import fr.ring.abilities.DefensiveSpell;
import fr.ring.abilities.HealingSpell;
import fr.ring.abilities.OffensiveSpell;
import fr.ring.abilities.Potion;
import fr.ring.abilities.Shield;
import fr.ring.abilities.Sword;
import fr.ring.actors.Athlete;
import fr.ring.actors.BaseCharacter;
import fr.ring.actors.CharacterConstants;
import fr.ring.actors.StatException;
import fr.ring.actors.Warrior;
import fr.ring.actors.Wizard;
import fr.ring.engine.Engine;
import fr.ring.engine.FileHandler;

/**
 * Classe permettant de créer des personnages par le biais de la console.
 * 
 * @author Myrvete Hatoum
 * @author Rémi Piérot
 * @version 1.0
 */
public class CreateCharacter implements ConsoleConstants{

	private static BaseCharacter character;
	
	/**
	 * Réalise toutes les étapes de création d'un personnage.
	 */
	public static void create(){
		character = new BaseCharacter();
		
		// On choisit le type du personnage
		System.out.println(CLEAR);
		chooseCharacterType();
		System.out.println(CLEAR);
		
		// Si le personnage n'est pas vide (donc que l'utilisateur n'a pas demandé un retour au menu), on continue.
		if(character != null){
			// On choisit ensuite le nom, les statistiques, les capacités.
			chooseCharacterName();
			System.out.println(CLEAR);
			
			try{
				character.setExperience(CharacterConstants.MIN_EXPERIENCE);
			}
			catch(StatException e){ }
			
			chooseCharacterStats();
			System.out.println(CLEAR);
			
			chooseCharacterAbilities();
			
			character.setMaxHealth();
			
			// On ajoute le personnage au moteur de jeu puis on sauvegarde avant d'afficher un récapitulatif du personnage nouvellement créé.
			Engine.engine.addBaseCharacter(character);
			FileHandler.save();
			System.out.println(character.getName() + " créé !\n");
			System.out.print(character);
			
			for(Ability ability : character.getAbilities())
				System.out.print("\n\n" + ability);
		}
		else
			System.out.print("Abandon de la création de personnage.");
		
	}
	
	/**
	 * Fonction permettant de choisir un type de combattant.
	 */
	private static void chooseCharacterType(){
		
		int choice = WRONG_CHOICE;
		ArrayList<Integer> index = new ArrayList<Integer>();
		
		index.add(WAR);
		index.add(WIZ);
		index.add(ATH);
		index.add(QUIT-1);
		
		System.out.println("Choisir une classe de personnage :\n\n"
							+ (WAR+1) + ") Guerrier\n"
							+ (WIZ+1) + ") Magicien\n"
							+ (ATH+1) + ") Athlète\n"
							+ QUIT + ") Retour au menu");
		
		System.out.print("\nVotre choix : ");
		
		choice = Menu.choice(index);
		
		// On instancie un personnage de la classe choisie par l'utilisateur.
		switch(choice) {
		
			case WAR : character = new Warrior();
					   break;
					
			case WIZ : character = new Wizard();
					   break;
					   
			case ATH : character = new Athlete();
					   break;
					   
			case (QUIT-1) : character = null;
						break;
		}	
	}
	
	/**
	 * Fonction permettant de donner un nom au combattant.
	 */
	private static void chooseCharacterName(){
		
		String name = new String();
		
		System.out.print("Choisir le nom du personnage (" + MAX_NAME_SIZE + " caractères maximum) : ");
		
		// Saisie sécurisée du nom, pas de caractères non alphabétiques ni d'accents.
		do{
			name = Menu.sc.next();
			
			if(name.length() > MAX_NAME_SIZE)
				System.out.print("Votre nom est trop long, raccourcissez-le : ");
			else if(!isNameValid(name))
				System.out.print("Votre nom contient des caractères invalides, recommencez : ");
			
		}while(name.length() > MAX_NAME_SIZE || !isNameValid(name));
		
		character.setName(name);
	}
	
	/**
	 * Fonction permettant de choisir les statistiques du combattant.
	 */
	private static void chooseCharacterStats(){
		// Si le personnage est un sorcier, on commence par choisir Concentration et Intelligence pour ne pas être bloqué à 0 pour la force et la dextérité.
		if(character instanceof Wizard)
			for(int i=NB_STATS-1; i>=0; i--)
				chooseStat(i);
		else
			for(int i=0; i<NB_STATS; i++)
				chooseStat(i);
	}
	
	/**
	 * Fonction permettant d'initialiser la statistique dont l'ID est reçu en paramètre.
	 * 
	 * @param statID Identifiant de la statistique à initialiser.
	 */
	private static void chooseStat(int statID){
		
		int choice = WRONG_CHOICE;
		String values = "(" + character.getMinStat(statID) + " - " + character.getMaxStat(statID) + ")";
		
		System.out.print("Veuillez saisir la valeur de ");
		
		// Selon le statID, l'utilisateur rentre la valeur de la statistique correspondant au paramètre.
		switch(statID){
		
			case CharacterConstants.STRENGTH : 
							System.out.print("la force " + values + " : ");
							
							do{
								try{
									choice = Integer.parseInt(Menu.sc.next());
								}
								catch(NumberFormatException e){ }
								
								try{
									character.setStrength(choice);
								}
								catch(StatException e){
									System.out.print("La valeur rentrée est invalide, recommencez : ");
									choice = WRONG_CHOICE;
								}
							}while(choice == WRONG_CHOICE);
							
							break;
				
			case CharacterConstants.DEXTERITY : 
							System.out.print("la dextérité " + values + " : ");
							
						 	do{
						 		try{
									choice = Integer.parseInt(Menu.sc.next());
								}
								catch(NumberFormatException e){ }
						 		
						 		try{
						 			character.setDexterity(choice);
							 	}
							 	catch(StatException e){
								 	System.out.print("La valeur rentrée est invalide, recommencez : ");
								 	choice = WRONG_CHOICE;
							 	}
						 	}while(choice == WRONG_CHOICE);
						 	
						 	break;
				
			case CharacterConstants.INTELLIGENCE : 
							System.out.print("l'intelligence " + values + " : ");
							
							do{
								try{
									choice = Integer.parseInt(Menu.sc.next());
								}
								catch(NumberFormatException e){ }
								
								try{
									character.setIntelligence(choice);
								}
								catch(StatException e){
									System.out.print("La valeur rentrée est invalide, recommencez : ");
									choice = WRONG_CHOICE;
								}
							}while(choice == WRONG_CHOICE);
							
							break;
				
			case CharacterConstants.SPIRIT : 
							System.out.print("la concentration " + values + " : ");
							
							do{
								try{
									choice = Integer.parseInt(Menu.sc.next());
								}
								catch(NumberFormatException e){ }
								
								try{
									character.setSpirit(choice);
								}
								catch(StatException e){
									  System.out.print("La valeur rentrée est invalide, recommencez : ");
									  choice = WRONG_CHOICE;
								}
							}while(choice == WRONG_CHOICE);
							
							break;
		}
	}
	
	/**
	 * Fonction permettant de choisir les deux capacités initiales du combattant.
	 */
	private static void chooseCharacterAbilities(){
		
		for(int i =0; i<CharacterConstants.MIN_ABILITIES; i++){
			chooseAbility();
			System.out.println(CLEAR);
		}
	}
	
	/**
	 * Fonction permettant de choisir une capacité à ajouter au combattant.
	 */
	private static void chooseAbility(){
		
		int choice = WRONG_CHOICE;
		Ability ability = null;
		ArrayList<Integer> index = new ArrayList<Integer>();
		
		index.add(SWORD);
		index.add(OFFENSIVE_SPELL);
		index.add(SHIELD);
		index.add(DEFENSIVE_SPELL);
		index.add(POTION);
		index.add(HEALING_SPELL);
		
		System.out.println("Choisissez une capacité :\n\n"
							+ "1) Epée\n"
							+ "2) Sortilège offensif\n"
							+ "3) Bouclier\n"
							+ "4) Sortilège défensif\n"
							+ "5) Remède\n"
							+ "6) Sortilège guérisseur");
		
		System.out.print("\nVotre choix : ");
		
		choice = Menu.choice(index);
		
		// On instancie une capacité du type choisi par l'utilisateur puis on l'ajoute au personnage.
		switch(choice){
			case SWORD : ability = new Sword(character);
						 break;
				
			case OFFENSIVE_SPELL : ability = new OffensiveSpell(character);
								   break;
				
			case SHIELD : ability = new Shield(character);
						  break;
				
			case DEFENSIVE_SPELL : ability = new DefensiveSpell(character);
								   break;
				
			case POTION : ability = new Potion(character);
						  break;
				
			case HEALING_SPELL : ability = new HealingSpell(character);
								 break;
		}
		
		character.addAbility(ability);
		
		System.out.println("\n" + ability);
	}
	
	/**
	 * Permet de savoir si un nom est valide ou non.
	 * 
	 * @param name Nom à vérifier.
	 * @return true si le nom est bon, false sinon
	 */
	private static boolean isNameValid(String name){
		
		int i = 0;
		boolean alphabetic = true;
		
		// Pour chaque caractère on vérifie qu'il est alphabétique.
		while(i < name.length() && alphabetic){
			if((name.charAt(i) < 'A' || name.charAt(i) > 'Z') && (name.charAt(i) < 'a' || name.charAt(i) > 'z'))
				alphabetic = false;
			else
				i++;
		}
		
		return i == name.length();
	}
}
