import java.util.Scanner;

// This is the Facade class
public class FacadePokemonCenter 
{
	private int id;
	private String name;
	
	TrainerIDCheck TIDC;
	TrainerNameCheck TNC;
	HealAndPC HAPC;
	Welcome W;
	TestPokemonCenter TPC;
	
	public FacadePokemonCenter(int newID, String newName) throws Exception
	{
		id = newID;
		name = newName;
		
		W = new Welcome();
		TIDC = new TrainerIDCheck();
		TNC = new TrainerNameCheck();
		HAPC = new HealAndPC();
		main();
	}
	
	@SuppressWarnings("resource")
	public void main()throws Exception
	{
		Scanner input = new Scanner(System.in);
		System.out.println("What operation are you looking for today?\n1) Healing Center\n2) Pokemon Box\n3) Exit Center\n(type in the number of the operation)");
		int i = input.nextInt();
		if(i == 1) // for the healing center
		{
			healPokemon();
			main();
		}
		else if(i == 2)
		{
			while(i != 1 || i != 2 || i != 3 || i != 4)
			{
				System.out.println("-----Pokemon Box-----\n1) Deposit\n2) Withdraw\n3) Check current team\n4) Go Back");
				i = input.nextInt();
				if(i == 1) {
					System.out.println("What is the number of the pokemon you wish to deposit?");
					depositPokemon(input.nextInt());
				}
				else if(i == 2) {
					System.out.println("What is the number of the pokemon you wish to withdraw?");
					withdrawPokemon(input.nextInt());
				}
				else if(i == 3) {
					printTeamAndHealth();
				}
				else if(i == 4)
					main();
				else
					System.out.println("Invalid Choice!");
			}
		}
		else if(i == 3)
		{	
			System.out.println("Have a good day!");
			System.out.close();
		}
		else
		{
			System.out.println("Invalid Choice!");
			main();
		}
	}
	
	public int getTrainerID()
	{
		return id;
	}
	
	public String getTrainerName()
	{
		return name;
	}
	
	public void healPokemon() throws Exception
	{
		if(!TIDC.trainerActive(id))
			System.out.println("Trainer ID is invalid");
		else if(!TNC.trainerActive(name))
			System.out.println("Trainer name is invalid");
		else if(HAPC.isTeamEmpty())
			System.out.println("Your current team has no pokemon");
		else
		{
			HAPC.healPokemon();
			main();
		}
		System.out.println("The healing process was unsuccessful");
	}
	
	public void depositPokemon(int dexNum) throws Exception
	{
		if(!TIDC.trainerActive(id))
			System.out.println("Trainer ID is invalid");
		else if(!TNC.trainerActive(name))
			System.out.println("Trainer name is invalid");
		else if(HAPC.isTeamEmpty())
			System.out.println("Your current team has no pokemon");
		else if(dexNum >= 722)
			System.out.println("That pokenumber is invalid");
		else if(!HAPC.containsPokemon(dexNum))
			System.out.println("That pokemon is not in your current team");
		else
		{
			HAPC.depositPokemon(dexNum);
			System.out.println("Deposit was successful!");
			main();
		}
		System.out.println("Deposit was unsuccessful!");
		main();
	}
	
	public void withdrawPokemon(int dexNum) throws Exception
	{
		if(!TIDC.trainerActive(id))
			System.out.println("Trainer ID is invalid");
		else if(!TNC.trainerActive(name))
			System.out.println("Trainer name is invalid");
		else if(HAPC.isTeamFull())
			System.out.println("Your team is already full");
		else if(dexNum >= 722)
			System.out.println("The pokenumber is invalid");
		else
		{
			HAPC.withdrawPokemon(dexNum);
			System.out.println("Withdrawl was successful!");
			main();
		}
		System.out.println("Withdrawl was unsuccessful!");
	}
	
	public void printTeamAndHealth()
	{
		HAPC.printTeamAndHealth();
	}
	

}
