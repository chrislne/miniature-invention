package application;

public class Crobat extends Pokemon
{
	public final static int SludgeBomb = 0, ShadowBall = 1, ArielAce = 2, XScissors = 3;
	
	public Crobat()
	{
		//Set all data for crobat
		super.setPokImageFront("file:Pokemon's/Crobat_Front.gif");
		
		super.setPokemonLv(75);
		super.setMaxHealth(374);
		super.setHealth(4);

		super.setAttackName("Sludge Bomb", "Shadow Ball", "Ariel Ace", "X-Scissors");
		super.setAttackDamage(90, 80, 60, 80);
		super.setAttackPP(10, 15, 20, 15);
		super.setMaxAttackPP(10, 15, 20, 15);

		super.setPokemonName("CROBAT");
	}
}
