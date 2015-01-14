package fr.ring.actors;

/**
 * Classe permettant de créer des Wizards.
 * 
 * @author Myrvete Hatoum
 * @author Rémi Piérot
 * @version 1.0
 */
public class Wizard extends BaseCharacter {

	private static final long serialVersionUID = -3617014108117707824L;
	
	/**
	 * Valeur = 15, Minimum de l'intelligence et de la concentration d'un Wizard.
	 */
	public static final int ADD_STAT_WIZARD = 15;
	
	@Override
	public int getMaxStat(int statID){
		int maxStat = 0;
		
		switch(statID){
			case STRENGTH : 
					maxStat = Math.min(getIntelligence(), getSpirit()) - ADD_STAT_WIZARD;
					break;
				
			case DEXTERITY : 
					maxStat = Math.min(getIntelligence(), getSpirit()) - ADD_STAT_WIZARD;
					break;
				
			case INTELLIGENCE :
					if(getSpirit() != 0)
						maxStat = super.getMaxStat(statID);
					else
						maxStat = super.getMaxStat(statID) - ADD_STAT_WIZARD;
					break;
				
			case SPIRIT :
					if(getIntelligence() != 0)
						maxStat = super.getMaxStat(statID);
					else
						maxStat = super.getMaxStat(statID) - ADD_STAT_WIZARD;
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
					minStat = 0;
					break;
				
			case DEXTERITY : 
					minStat = 0;
					break;
				
			case INTELLIGENCE :
					minStat = Math.max(this.getStrength(), this.getDexterity()) + ADD_STAT_WIZARD;
					break;
				
			case SPIRIT :
					minStat = Math.max(this.getStrength(), this.getDexterity()) + ADD_STAT_WIZARD;
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