package fr.ring.console;

import java.util.ArrayList;

import fr.ring.actors.BaseCharacter;
import fr.ring.engine.Engine;
import fr.ring.engine.FileHandler;

/**
 * Classe permettant d'effacer des personnages de la liste d'Engine depuis la console.
 * 
 * @author Rémi Piérot
 * @author Myrvete Hatoum
 * @version 1.0
 */
public class EraseCharacter implements ConsoleConstants{
	
	/**
	 * Gère toutes les étapes de la suppression de personnage.
	 */
	public static void erase(){
		
		int choice = WRONG_CHOICE;
		ArrayList<Integer> index = new ArrayList<Integer>();
		
		System.out.println(CLEAR);
		
		do{
			System.out.println("Sélectionnez un personnage à supprimer :");
			
			// Liste tous les personnages contenus dans le moteur.
			for(BaseCharacter character : Engine.engine.getBaseCharacters()){
				System.out.println("\n----- " + (Engine.engine.getBaseCharacters().indexOf(character)+1) + " -----\n" + character);
				index.add(Engine.engine.getBaseCharacters().indexOf(character));
			}
		
			System.out.println("\n----- " + (Engine.engine.getBaseCharacters().size()+1) + " -----\nRetour au menu");
			index.add(Engine.engine.getBaseCharacters().size());
			
			System.out.print("\nVotre choix : ");
			
			choice = Menu.choice(index);
			
			if(choice != Engine.engine.getBaseCharacters().size() && !Menu.confirm()){
				choice = WRONG_CHOICE;
				System.out.println("Aucune suppression n'a été effectuée !");
				System.out.println(CLEAR);
			}
			else if(choice == Engine.engine.getBaseCharacters().size())
				System.out.println(CLEAR);
			
		}while(choice == WRONG_CHOICE);
		
		// On supprime le personnage sélectionné puis on sauvegarde.
		if(choice != Engine.engine.getBaseCharacters().size()){
			try{
				System.out.print("Suppression de " + Engine.engine.getBaseCharacter(choice).getName() + ".");
				Engine.engine.removeCharacter(Engine.engine.getBaseCharacter(choice));
				FileHandler.save();
			}
			catch(Exception e){ }
		}
		else
			System.out.print("Aucune suppression n'a été effectuée !");
	
	}
}
