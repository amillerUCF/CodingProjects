
public class Welcome 
{
	static int visited = 0;
	
	public Welcome()
	{
		if(visited == 1) // Player is not a newcomer
			System.out.println("\nHello again!");
		else
			System.out.println("\nHello newcomer!");
		visited = 1;
	}
}
