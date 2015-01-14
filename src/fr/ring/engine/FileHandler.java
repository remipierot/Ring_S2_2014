package fr.ring.engine;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import fr.ring.actors.BaseCharacter;

/**
 * Classe de gestion des fichiers.
 * Cette classe nécessite que les données à sauvegarder implémentent Serializable pour pouvoir utiliser les File/Object Output/Input Stream.
 * Ces fonctions facilitent grandement l'enregistrement et le chargement de données.
 *  
 * @author Rémi Piérot
 * @author Myrvete Hatoum
 * @version 1.0 
 */
public class FileHandler {
	
	/**
	 * Valeur = ".ygreg", Format des sauvegardes du jeu
	 */
	public static final String SAVE_FORMAT = ".ygreg";

	/**
	 * Permet de sauvegarder la partie dans un fichier à l'emplacement indiqué dans le saveSlot d'Engine.
	 * 
	 * @see Engine#getSave()
	 */
	public static void save(){
		
		// On récupère le nom de la sauvegarde courante.
		String fileName = Engine.engine.getSave();
		FileOutputStream file = null;
		
		try{
			// On ouvre un nouveau fichier en écriture avec pour nom le nom de sauvegarde et le format.
			file = new FileOutputStream(fileName+SAVE_FORMAT);
			
			try {
				// On ouvre un nouveau flux d'objet en écriture sur le fichier ouvert précédemment.
				ObjectOutputStream object = new ObjectOutputStream(file);
				
				// On utilise le flux d'objet pour écrire la liste de personnages contenue dans Engine dans le fichier cible du flux d'objet.
				object.writeObject(Engine.engine.getBaseCharacters());
				
				// On ferme le flux d'objet et le fichier ouverts.
				object.close();
				file.close();
			}
			catch(IOException e){ }
			
		}
		catch(FileNotFoundException e){
			// Si le fichier n'a pas pu être ouvert, on créé le répertoire saves qui contiendra les sauvegardes et on recommence l'opération.
			new File("saves").mkdir();
			save();
		}
		
	}
	
	/**
	 * Permet de choisir un fichier à charger dans le moteur.
	 * 
	 * @throws FileNotFoundException Exception renvoyée lorsque le fichier demandé n'est pas trouvé.
	 * @throws IOException Exception levée lorsque la lecture/écriture du fichier rencontre un problème.
	 * @throws ClassNotFoundException Exception levée lorsque la classe voulue n'est pas trouvée dans le fichier.
	 */
	// Pour supprimer les warnings concernant la vérification du type d'objets lus par le le flux d'objet.
	@SuppressWarnings("unchecked")
	public static void load() throws FileNotFoundException, IOException, ClassNotFoundException{
		
		// On ouvre une boîte de dialogue pour sélectionner le fichier à charger.
		FileDialog nav = new FileDialog(new Frame(), "Charger un fichier", FileDialog.LOAD);
		nav.setVisible(true);
		
		// On récupère le nom du fichier sélectionné en chemin absolu.
		String fileName = nav.getDirectory()+nav.getFile();
		
		// Si le fichier existe et que le format est bien .ygreg, on rentre dans cette condition.
		if(nav.getFile()!=null  && fileName.substring(fileName.length()-SAVE_FORMAT.length()).equals(SAVE_FORMAT))
		{
			// On ouvre le fichier sélectionné en lecture.
			FileInputStream file = new FileInputStream(fileName);
			
			// On ouvre un nouveau flux d'objet en lecture sur le fichier sélectionné.
			ObjectInputStream object = new ObjectInputStream(file);

			// On remplace la liste de personnages de l'Engine par celle contenue dans le fichier et on change le nom de la sauvegarde courante pour qu'il corresponde à celui du fichier chargé.
			Engine.engine.setBaseCharacters((ArrayList<BaseCharacter>)object.readObject());
			Engine.engine.setSave(fileName.substring(0, fileName.length()-6));
			
			// On ferme le flux d'objet et le fichier ouverts.
			object.close();
			file.close();
			
			save();
		}
		else
			throw new FileNotFoundException();
	}
}

