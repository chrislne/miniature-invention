package application;

public class Gardevoir extends Pokemon
{
	public final static int Moonblast = 0, Psychic = 1, DazzlingGleam = 2, Ghostball = 3;
	
	public Gardevoir()
	{
		//Set all data for gardevoir 
		super.setPokImageFront("file:Pokemon's/Gardevoir_Front.gif");
		
		super.setPokemonLv(75);
		super.setMaxHealth(340);
		super.setHealth(9);
		
		super.setAttackName("Moonblast", "Psychic", "Dazzling Gleam", "Ghost Ball");
		super.setAttackDamage(95, 90, 80, 80);
		super.setAttackPP(15, 10, 10, 15);
		super.setMaxAttackPP(15, 10, 10, 15);
		
		super.setPokemonName("GARDEVOIR");

	}
}
