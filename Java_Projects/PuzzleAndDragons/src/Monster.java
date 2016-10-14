import java.util.ArrayList;
import java.util.List;

class Monster {
	
	protected int health; // holds the health
	protected int attack; // holds the attack of monster
	protected int recovery; // holds RCV of monster
	protected List<Integer> attributes = new ArrayList<Integer>(); // holds two different attributes
	/* Attributes:
	 * 0 = fire
	 * 1 = water
	 * 2 = grass
	 * 3 = light
	 * 4 = dark
	 */

	public Monster(int health, int attack, int recovery, int attribute1, int attribute2)
	{
		this.health = health;
		this.attack = attack;
		this.recovery = recovery;
		this.attributes.add(attribute1);
		this.attributes.add(attribute2);
	}
	
	public int GetAttribute1(Monster m)
	{
		return m.attributes.get(0);
	}
	
	public int GetAttribute2(Monster m)
	{
		return m.attributes.get(1);
	}
}
