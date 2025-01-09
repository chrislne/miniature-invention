package application;

public class Venasuar extends Pokemon
{
	public final static int EnergyBall = 0, SludgeBomb = 1, LeafStorm = 2, SolarBeam = 3;

	public Venasuar()
	{
		//Set all data for venasuar 
		super.setPokImageBack("file:Pokemon's/Venasuar_Back.png");
		super.setPokImageFront("file:Pokemon's/Venasuar_Front.gif");
		super.setPokImageIcon("file:Pokemon's/Venasuar_Icon.gif");

		super.setPokemonLv(75);
		
		super.setMaxHealth(364);
		super.setHealth(364);

		super.setAttackName("Enerygy Ball", "Sludge Bomb", "Leaf Storm", "Solar Beam");
		super.setAttackDamage(90, 90, 130, 120);
		super.setAttackPP(10, 10, 5, 10);
		super.setMaxAttackPP(10, 10, 5, 10);

		super.setPokemonName("VENASUAR");		
	}
}