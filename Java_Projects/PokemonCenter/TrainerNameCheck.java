
public class TrainerNameCheck {
	
	private String trainerName = "Ash Ketchum";
	
	public String getTrainerName()
	{
		return trainerName;
	}
	
	public boolean trainerActive(String trainerNameToCheck)
	{
		if(trainerNameToCheck.equals(trainerName))
			return true;
		return false;
	}
	
		

}
