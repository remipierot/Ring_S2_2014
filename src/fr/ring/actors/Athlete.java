package fr.ring.actors;

/**
 * Classe permettant de créer des Athletes.
 * 
 * @author Myrvete Hatoum
 * @author Rémi Piérot
 * @version 1.0
 */
public class Athlete extends BaseCharacter{
	
	private static final long serialVersionUID = -6862488207549698262L;
	
	/**
	 * Valeur = 20, Minimum des caractéristiques d'un Athlete.
	 */
	public static final int ADD_STAT_ATHLETE = 20;
	
	@Override
	public int getMaxStat(int statID){
		int maxStat = super.getMaxStat(statID);
		
		if(getStrength() == 0 && statID != STRENGTH)
			maxStat -= ADD_STAT_ATHLETE;
		
		if(getDexterity() == 0 && statID != DEXTERITY)
			maxStat -= ADD_STAT_ATHLETE;
		
		if(getIntelligence() == 0 && statID != INTELLIGENCE)
			maxStat -= ADD_STAT_ATHLETE;
		
		if(getSpirit() == 0 && statID != SPIRIT)
			maxStat -= ADD_STAT_ATHLETE;
		
		switch(statID){
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
					minStat = ADD_STAT_ATHLETE;
					break;
				
			case DEXTERITY : 
					minStat = ADD_STAT_ATHLETE;
					break;
				
			case INTELLIGENCE :
					minStat = ADD_STAT_ATHLETE;
					break;
				
			case SPIRIT :
					minStat = ADD_STAT_ATHLETE;
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
		
		return minStat;
	}
}
