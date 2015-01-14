package fr.ring.actors;

/**
 * Classe permettant de créer des Warriors.
 * 
 * @author Rémi Piérot
 * @author Myrvete Hatoum
 * @version 1.0
 */
public class Warrior extends BaseCharacter{
	
	private static final long serialVersionUID = -3617416786848484716L;
	
	/**
	 * Valeur = 10, Minimum de force pour un Warrior.
	 */
	public static final int ADD_STAT_WARRIOR = 10;
	
	@Override
	public int getMaxStat(int statID){
		int maxStat = 0;
		
		switch(statID){
			case STRENGTH : 
					maxStat = super.getMaxStat(statID);
					break;
				
			case DEXTERITY : 
					maxStat = getStrength() - ADD_STAT_WARRIOR;
					break;
				
			case INTELLIGENCE :
					maxStat = getDexterity();
					break;
				
			case SPIRIT :
					maxStat = getIntelligence() + ADD_STAT_WARRIOR;
					break;
					
			case EXPERIENCE :
					maxStat = super.getMaxStat(statID);
					break;
					
			case HEALTH :
					maxStat = super.getMaxStat(statID);
					break;
					
			case ABILITIES :
					maxStat = super.getMaxStat(statID);
					break;
					
			case STATS :
					maxStat = super.getMaxStat(statID);
					break;
		}
		
		if(maxStat < 0)
			maxStat = 0;
		else if(maxStat > super.getMaxStat(statID))
			maxStat = super.getMaxStat(statID);
		
		return maxStat;
	}
	
	@Override
	public int getMinStat(int statID){
		int minStat = 0;
		
		switch(statID){
			case STRENGTH : 
					minStat = getDexterity() + ADD_STAT_WARRIOR;
					break;
				
			case DEXTERITY : 
					minStat = getIntelligence();
					break;
				
			case INTELLIGENCE :
					minStat = getSpirit() - ADD_STAT_WARRIOR;
					break;
				
			case SPIRIT :
					minStat = 0;
					break;
					
			case EXPERIENCE :
					minStat = super.getMinStat(statID);
					break;
					
			case HEALTH :
					minStat = super.getMinStat(statID);
					break;
					
			case ABILITIES :
					minStat = super.getMinStat(statID);
					break;
		}
		
		if(minStat < 0)
			minStat = 0;
		
		return minStat;
	}
}
