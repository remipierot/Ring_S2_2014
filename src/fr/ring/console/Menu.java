package fr.ring.console;

import java.util.ArrayList;
import java.util.Scanner;

import fr.ring.engine.Engine;
import fr.ring.engine.FileHandler;
import fr.ring.fight.Fight;

/**
 * Fonction proposant le menu principal en mode console.
 * 
 * @author Myrvete Hatoum
 * @author Rémi Piérot
 * @version 1.0
 */
public class Menu implements ConsoleConstants {
	
	private static boolean ended = false;
	public static Scanner sc = new Scanner(System.in);

	/**
	 * Permet de savoir si le joueur veut quitter ou non.
	 * 
	 * @return true si le joueur veut quitter, false sinon.
	 */
	public static boolean isEnded(){
		return ended;
	}
	
	/**
	 * Gère le menu dans la console.
	 */
	public static void playing(){
		
		int choice = WRONG_CHOICE;
		ArrayList<Integer> index = new ArrayList<Integer>();
		
		System.out.println(CLEAR);
		
		if(Engine.engine.isEmpty())
			System.out.println("Bienvenue dans Ring!\n");
		
		System.out.println("Que souhaitez-vous faire ?\n");
		
		// On ne peut créer un personnage que si le moteur n'est pas plein
		if(!(Engine.engine.isFull())){
			System.out.println("1) Créer un personnage");
			index.add(CREATE);
		}
		
		System.out.println("2) Charger un fichier");
		index.add(LOAD);
		
		// On ne peut supprimer un personnage que si le moteur n'est pas vide
		if(!Engine.engine.isEmpty()){
			System.out.println("3) Supprimer des personnages");
			index.add(ERASE);
		}
		
		// on ne peut combattre que si le moteur contient au moins 2 personnages différents
		if(Engine.engine.getBaseCharacters().size() >= Fight.CHARACTERS_IN_FIGHT){
			System.out.println("4) Combattre");
			index.add(FIGHT);
		}
		
		System.out.println("5) Quitter");
		index.add(QUIT);
		System.out.print("\nVotre choix : ");
		
		choice = choice(index);
		
		switch(choice){
			case CREATE :
				CreateCharacter.create();
				break;
				
			case LOAD :
				System.out.println(CLEAR);
				try{
					FileHandler.load();
					System.out.print("Chargement réussi!");
				}
				catch (Exception e){
					System.out.print("Aucun chargement n'a été effectué.");
				}
				break;
				
			case ERASE :
				EraseCharacter.erase();
				break;
				
			case FIGHT : 
				ChooseCharacter.selectCharacters();
				break;
				
			case QUIT :
				ended = confirm();
				if(ended)
					sc.close();
				else
					System.out.print("Retour au menu.");
				break;
		}
		
		System.out.println();
	}
	
	/**
	 * S'assure de la certitude de l'utilisateur concernant son action.
	 * Utilisé lorsque l'on souhaite quitter le programme par exemple.
	 * 
	 * @return true si l'utilisateur confirme son choix, false s'il se rétracte.
	 */
	public static boolean confirm(){
		
		int choice = WRONG_CHOICE;
		ArrayList<Integer> index = new ArrayList<Integer>();
		
		index.add(0);
		index.add(1);
		
		System.out.println(CLEAR);
		System.out.println("Etes-vous sûr ?\n\n1) Oui\n2) Non");
		System.out.print("\nVotre choix : ");
		
		choice = choice(index);
		
		System.out.println(CLEAR);
		
		return (choice == 0);
	}
	
	/**
	 * Propose une saisie sécurisée d'entier.
	 * On poursuit la saisie tant que l'entier rentré n'est pas présent dans l'ArrayList passée en paramètre.
	 * 
	 * @param index Liste d'entiers acceptables comme choix
	 * @return Choix de l'utilisateur
	 */
	public static int choice(ArrayList<Integer> index){
		
		int choice = WRONG_CHOICE;
		
		do{
			
			try{
				choice = Integer.parseInt(Menu.sc.next());
				
				// choice-- car l'utilisateur est plus habitué à voir des 1,2,3,... que des 0,1,2,... pour ses choix.
				choice--;
			}
			catch(NumberFormatException e){ 
				choice = WRONG_CHOICE;
			}
			
			if(!index.contains(choice)){
				System.out.print("Veuillez entrer un choix valide : ");
				choice = WRONG_CHOICE;
			}
			
		}while(choice == WRONG_CHOICE);
		
		return choice;
	}

}
