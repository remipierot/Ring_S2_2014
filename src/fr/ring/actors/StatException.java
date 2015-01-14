package fr.ring.actors;

/**
 * Classe permettant de gérer les exceptions des statistiques de personnages.
 * 
 * @author Rémi Piérot
 * @author Myrvete Hatoum
 * @version 1.0
 */
@SuppressWarnings("serial")
public class StatException extends Exception implements CharacterConstants{
	
	private int stat;
	private int val;
	
	/**
	 * Valeur = "Problème au niveau des statistiques", Message de base utilisé pour ce type d'exception.
	 */
	public static final String BASIC_MESSAGE = "Problème au niveau des statistiques";
	
	/**
	 * Constructeur d'initialisation champ à champ.
	 * 
	 * @param message Message d'erreur
	 * @param stat Code de la statistique concernée par l'exception
	 * @param val Valeur posant problème
	 */
	public StatException(String message, int stat, int val){
		
		super(message);
		this.stat = stat;
		this.val = val;
	}
	
	/**
	 * Masquage du toString() de la mère par redéfinition pour fournir des informations supplémentaires.
	 * Ajoute des informations à propos de la statistique concernée par l'exception ainsi que la valeur gênante au toString() de la mère.
	 * 
	 * @return Informations concernant l'exception
	 */
	@Override
	public String toString(){
	
		String s = super.toString();
		
		switch(stat){
		
			case STRENGTH : s += " - La force ne respecte pas les conditions: ";
				break;
				
			case DEXTERITY : s += " - La dextérité ne respecte pas les conditions: ";
				break;
				
			case INTELLIGENCE : s += " - L'intelligence ne respecte pas les conditions: ";
				break;
				
			case SPIRIT : s += " - La concentration ne respecte pas les conditions: ";
				break;
				
			case EXPERIENCE : s += " - L'expérience ne respecte pas les conditions: ";
				break;
				
			case HEALTH : s += " - La santé ne respecte pas les conditions: ";
				break;
		}
		
		s += val;
		return s;
	}
}
