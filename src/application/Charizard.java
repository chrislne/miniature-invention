package application;

public class Charizard extends Pokemon
{
	public final static int BlastBurn = 0, ArielAttack = 1, DragonPulse = 2, Flamethrower = 3;

	public Charizard()
	{
		//Set all data for charizard 
		super.setPokImageBack("file:Pokemon's/Charizard_Back.png");
		super.setPokImageFront("file:Pokemon's/Charizard_Front.gif");
		super.setPokImageIcon("file:Pokemon's/Charizard_Icon.gif");

		super.setPokemonLv(75);
		
		super.setMaxHealth(360);
		super.setHealth(360);

		super.setAttackName("BlastBurn", "ArielAttack", "DragonPulse", "Flamethrower");
		super.setAttackDamage(120, 90, 85, 95);
		super.setAttackPP(5, 20, 10, 15);
		super.setMaxAttackPP(5, 20, 10, 15);

		
		super.setPokemonName("CHARIZARD");
	}
}
