package application;

public class Hariyama extends Pokemon
{
	public final static int CloseCombat = 0, BrickBreak = 1, RockTomb = 2, LowSweep = 3; 
			
		public Hariyama()
		{
			//Set all data for hariyama 
		super.setPokImageFront("file:Pokemon's/Hariyama_Front.gif");
		
		super.setPokemonLv(75);
		super.setMaxHealth(492);
		super.setHealth(2);

		super.setAttackName("CloseCombat", "Brick Break", "Rock Tomb", "Low Sweep");
		super.setAttackDamage(120, 75, 60, 65);
		super.setAttackPP(5, 15, 15, 20);
		super.setMaxAttackPP(5, 15, 15, 20);

		super.setPokemonName("Hariyama");
		}
}
