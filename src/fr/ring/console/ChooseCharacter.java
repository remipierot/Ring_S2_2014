package fr.ring.console;
import java.util.ArrayList;

import fr.ring.actors.BaseCharacter;
import fr.ring.engine.Engine;

/**
 * Classe permettant à l'utilisateur de choisir deux personnages parmi la liste de l'Engine pour combattre.
 * 
 * @author Rémi Piérot
 * @author Myrvete Hatoum
 * @version 1.0
 */
public class ChooseCharacter implements ConsoleConstants{

	private static BaseCharacter character1;
	private static BaseCharacter character2;
	
	/**
	 * Propose les choix de combattant à l'utilisateur avant de lancer le combat.
	 */
	public static void selectCharacters(){
		
		System.out.println(CLEAR);
		
		try{
			character1 = chooseCharacter();
			System.out.println(CLEAR);
			character2 = chooseCharacter();
			System.out.println(CLEAR);
			ArrayList<BaseCharacter> characters = new ArrayList<BaseCharacter>();
			characters.add(character1);
			characters.add(character2);
			
			Fighting.setUpFight(characters);
			Fighting.startFight();
		}
		catch(IndexOutOfBoundsException e){
			System.out.println(CLEAR);
			System.out.print("Retour au menu.");
		}
		finally{
			clear();
		}
		
	}
	
	/**
	 * Propose à l'utilisateur de choisir un personnage de la liste d'Engine.
	 * 
	 * @return Personnage sélectionné.
	 */
	private static BaseCharacter chooseCharacter() throws IndexOutOfBoundsException{
		
		int choice = WRONG_CHOICE;
		ArrayList<BaseCharacter> characters = Engine.engine.getBaseCharacters();
		ArrayList<Integer> index = new ArrayList<Integer>();
		BaseCharacter backCharacter = null;
		
		System.out.println("Choisissez le personnage à faire combattre !\n");
		
		// Liste tous les personnages présents dans le moteur
		for(BaseCharacter character : characters){
			if(character != character1){
				System.out.println((characters.indexOf(character)+1) + ") " + character.getName());
				index.add(characters.indexOf(character));
			}
		}
		
		System.out.println((characters.size()+1) + ") Retour au menu");
		index.add(characters.size());
		
		System.out.print("\nChoisir un personnage : ");
		
		choice = Menu.choice(index);
		
		backCharacter = characters.get(choice);
		
		// Renvoie le personnage sélectionné par l'utilisateur.
		return backCharacter;
	}
	
	/**
	 * Vide les deux personnages en les mettant à null.
	 */
	private static void clear(){
		character1 = null;
		character2 = null;
	}
}

