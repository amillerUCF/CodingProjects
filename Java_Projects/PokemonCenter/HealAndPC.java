import java.util.*;


public class HealAndPC 
{
	/**
	 * This ArrayList will represent the Pokemon the trainer is currently carrying.
	 * For the ease of coding, we will assume that the trainer has, indeed, caught them all.
	 * We will also assume that the trainer has an infinite number of Pokemon in the PC.
	 * This makes coding easier by assuming Pokemon are a commodity.
	 * This also foregoes the process of keeping track of Pokemon stored on the PC.
	 */

	private ArrayList<Pokemon> pokemonTeam;
	private int TeamSize = 6;
	
	// Sets up the trainer's team.
	public HealAndPC()
	{
		pokemonTeam = new ArrayList<Pokemon>();
		pokemonTeam.add(new Pokemon(25));
		pokemonTeam.add(new Pokemon(3));
		pokemonTeam.add(new Pokemon(6));
		pokemonTeam.add(new Pokemon(9));
		pokemonTeam.add(new Pokemon(131));
		pokemonTeam.add(new Pokemon(143));
	}
	
	@SuppressWarnings("rawtypes")
	public ArrayList getPokemonTeam()
	{
		return pokemonTeam;
	}
	
	public void healPokemon()
	{
		for(int i = 0; i < TeamSize; i++)
		{	
			pokemonTeam.get(i).heal();
		}
		System.out.println("Thank you for waiting.");
		System.out.println("We've restored your pokemon to full health.");
		System.out.println("We hope to see you again!");
	}

	public void depositPokemon(int dexNum)
	{
		for(int i = 0; i < TeamSize; i++)
			if(pokemonTeam.get(i).getPokedexNumber() == dexNum)
			{
				TeamSize--;
				pokemonTeam.remove(i);
			}
	}
	
	public void withdrawPokemon(int dexNum)
	{	
		if(containsPokemon(dexNum))
			return;
		pokemonTeam.add(new Pokemon(dexNum));
		TeamSize++;
	}
	
	public boolean containsPokemon(int dexNum)
	{
		System.out.println(dexNum);
		for(int i = 0; i < TeamSize; i++)
			if(pokemonTeam.get(i).getPokedexNumber() == dexNum)
				return true;
		return false;
	}
	
	public boolean isTeamEmpty()
	{
		if(TeamSize == 0)
			return true;
		return false;
	}
	
	public boolean isTeamFull()
	{
		if(TeamSize == 6)
			return true;
		return false;
	}
	
	public void printTeamAndHealth()
	{
		System.out.println("The current team is as follows: ");
		
		for(int i = 0; i < pokemonTeam.size(); i++)
		{
			Pokemon cur = pokemonTeam.get(i);
			System.out.println("Pokedex number: " + cur.getPokedexNumber() + "\n\tName: " + cur.getPokemonName() + "\n\tHealth: " + cur.getPercentHealth());
		}
	}

}
