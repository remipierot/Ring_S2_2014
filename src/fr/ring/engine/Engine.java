package fr.ring.engine;

import java.util.ArrayList;

import fr.ring.actors.BaseCharacter;


/**
 * Classe du moteur de jeu.
 *  
 * @author Rémi Piérot
 * @author Myrvete Hatoum
 * @version 1.0 
 */
public class Engine {
	
	/**
	 * Moteur du jeu.
	 * Attribut de classe pour éviter d'avoir à instancier un Engine au lancement du jeu. On a ainsi un Engine unique à chaque session, à moins d'effectuer un chargement.
	 */
	public static Engine engine = new Engine();
	
	private String saveSlot;
	private ArrayList<BaseCharacter> baseCharacters;
	
	/**
	 * Valeur = 12, Quantité maximale de personnages stockés en mémoire.
	 */
	public static final int MAX_CHARACTERS = 12;
	
	/**
	 * Constructeur par défaut.
	 * Initialise une nouvelle liste de BaseCharacter vierge et le nom du dossier de sauvegarde en fonction de l'OS.
	 */
	public Engine(){
		
		baseCharacters = new ArrayList<BaseCharacter>();
		saveSlot = new String("saves");
		
		// On récupère le nom du système d'exploitation.
		String OS_NAME = System.getProperty("os.name");
	
		// Si le nom ne commence pas par Windows, on utilise les slash pour séparer le nom de dossier du nom de fichier
		if(!OS_NAME.startsWith("Windows"))
			saveSlot += "/";
		// Sinon, on utilise les antislash
		else
			saveSlot += "\\";
		
		// Enfin on ajoute ring et le temps courant du système au nom de fichier pour avoir un saveSlot unique à chaque exécution du programme.
		saveSlot += "ring"+System.currentTimeMillis();
	}
	
	/**
	 * Permet de récupérer le nom de la sauvegarde correspondant à la partie en cours.
	 * 
	 * @return Nom de la sauvegarde
	 */
	public String getSave() {
		
		return saveSlot;
	}
	
	/**
	 * Permet de récupérer la liste des personnages de la partie en cours.
	 * 
	 * @return Liste des personnages
	 */
	public ArrayList<BaseCharacter> getBaseCharacters() {
		
		return baseCharacters;
	}
	
	/**
	 * Permet de récupérer le personnage de la liste correspondant au paramètre reçu.
	 * 
	 * @param n Position du personnage
	 * @return Personnage à la position n
	 */
	public BaseCharacter getBaseCharacter(int n){
		
		return baseCharacters.get(n);
	}

	/**
	 * Remplace le nom de la sauvegarde de la partie en cours par le paramètre reçu.
	 * 
	 * @param saveSlot Nouveau nom de sauvegarde
	 */
	public void setSave(String saveSlot) {
		
		this.saveSlot = saveSlot;
	}
	
	/**
	 * Remplace la liste de personnages actuelle par celle reçue en paramètre si sa taille n'est pas supérieure au maximum de personnages autorisé.
	 * 
	 * @param baseCharacters Nouvelle liste de personnages
	 */
	public void setBaseCharacters(ArrayList<BaseCharacter> baseCharacters){
		
		if(baseCharacters.size() <= MAX_CHARACTERS)
			this.baseCharacters = baseCharacters;
	}
	
	/**
	 * Ajoute le personnage reçu en paramètre à la liste actuelle si sa taille le permet.
	 * 
	 * @param baseCharacter Personnage à ajouter
	 */
	public void addBaseCharacter(BaseCharacter baseCharacter){
		
		if(baseCharacters.size() != MAX_CHARACTERS)
			baseCharacters.add(baseCharacter);
	}
	
	/**
	 * Supprime le personnage de la liste correspondant au paramètre reçu.
	 * 
	 * @param character Personnage à supprimer
	 */
	public void removeCharacter(BaseCharacter character){
		
		if(baseCharacters.contains(character))
			baseCharacters.remove(character);
	}
	
	/**
	 * Permet de savoir si la liste de personnages est pleine.
	 * 
	 * @return true si la liste est pleine, false sinon.
	 */
	public boolean isFull(){
		
		return baseCharacters.size() == MAX_CHARACTERS;
	}
	
	/**
	 * Permet de savoir si la liste de personnages est vide.
	 * 
	 * @return true si la liste est vide, false sinon.
	 */
	public boolean isEmpty(){
		
		return baseCharacters.isEmpty();
	}

}
