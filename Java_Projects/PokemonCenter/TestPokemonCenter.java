/**
 * This program emulates the operations of the pokemon centers in the famous franchise "Pokemon".
 * Design of program is based off of the Facade Pattern where a facade/object("FacadePokemonCenter.java") 
 * is used to provide a simplified interface amongst the rest of the classes.
 */

// Class uses the facade to make multiple trainers
public class TestPokemonCenter 
{
	public static void main(String[] args) throws Exception
	{	
		FacadePokemonCenter FPC = new FacadePokemonCenter(01301, "Ash Ketchum");
	}
}
