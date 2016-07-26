
public class TrainerIDCheck {
	
	private int trainerID = 01301;
	
	public int getTrainerID()
	{
		return trainerID;
	}
	
	public boolean trainerActive(int trainerIDToCheck)
	{
		if(trainerIDToCheck == trainerID)
			return true;
		return false;
	}
}