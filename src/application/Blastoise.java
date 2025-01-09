package application;

public class Blastoise extends Pokemon
{

	public Blastoise()
	{		
		//Set all data for blastoise 
		super.setPokImageBack("file:Pokemon's/Blastoise_Back.png");
		super.setPokImageFront("file:Pokemon's/Blastoise_Front.gif");
		super.setPokImageIcon("file:Pokemon's/Blastoise_Icon.gif");
		super.setPokemonLv(75);
		
		super.setMaxHealth(362);
		super.setHealth(362);

		super.setAttackName("Surf", "IceBeam", "Earthquake", "HydroCannon");
		super.setAttackDamage(90, 90, 100, 120);
		super.setAttackPP(15, 10, 10, 5);
		super.setMaxAttackPP(15, 10, 10, 5);
		
		super.setPokemonName("BLASTOISE");
	}
}
