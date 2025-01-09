package application;

public class Rayquaza extends Pokemon
{
	public final static int HyperBeam = 0, DragonClaw = 1, FireBlast = 2, Thunder = 3;

	public Rayquaza()
	{
		//Set all data for rayquaza 
		super.setPokImageBack("file:Pokemon's/Rayquaza_Back.png");
		super.setPokImageFront("file:Pokemon's/Rayquaza_Front.gif");
		super.setPokImageIcon("file:Pokemon's/Rayquza_Icon.gif");
		
		super.setPokemonLv(100);
		super.setMaxHealth(600);
		super.setHealth(600);

		super.setAttackName("Hyper Beam", "Dragon Claw", "Fire Blast", "Thunder");
		super.setAttackDamage(130, 80, 120, 110);
		super.setAttackPP(5, 15, 5, 10);
		super.setMaxAttackPP(5, 15, 5, 10);

		super.setPokemonName("RAYQUAZA");
	}
}