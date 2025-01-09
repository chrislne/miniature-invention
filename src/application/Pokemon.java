package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//Inheritence Class that sets all default data and neccessities for a pokemon to be a pokemon
public class Pokemon 
{	
	//Variables
	private double xpos, ypos, width, height;
	private ImageView pokviewb, pokviewf, iconview;
	private int lv;
	private int maxhealth , health;
	private Image pokimgb, pokimgf, iconimg, deadimg;
	public int [] atkdmg, atkpp, maxatkpp;
	public String [] atkname;
	public final static int Move1 = 0, Move2 = 1, Move3 = 2, Move4 = 3;
	private String pokname;
	public boolean isdead;
	
	//Constructor to initalize all variables
	public Pokemon()
	{
		//Initialize and set locarion for pokemons back image
		//Only the players pokemon uses back images
		pokimgb = new Image ("file:Pokemon's/Blastoise_Back.png");
		pokviewb = new ImageView (pokimgb);
		pokviewb.setLayoutX(20);
		pokviewb.setLayoutY(680 - pokimgb.getHeight() - 200);

		//Initialize pokemons front image
		//Only the enemys pokemon uses front images
		pokimgf = new Image ("file:Pokemon's/Blastoise_Back.png");
		pokviewf = new ImageView (pokimgf);
		
		//Initialize and set pokemon icon image
		iconimg = new Image ("file:Pokemon's/Blastoise_Back.png");
		iconview = new ImageView (iconimg);
		
		//Initialize pokemon image position and coordinates
		xpos = 0;
		ypos = 0;
		lv = 0;
		width = pokimgb.getWidth();
		height = pokimgb.getHeight();
		
		//Initialize pokemon health and max health
		maxhealth = 10;
		health = maxhealth;
		
		//Initialize all arrays to store attack data, damage, current pp, max pp, and name
		atkdmg = new int [] {0, 0, 0, 0};
		atkpp = new int [] {0, 0, 0, 0};
		maxatkpp = new int [] {0, 0, 0, 0};
		atkname = new String [] {"", "", "", ""};
		
		//Set pokemon dead as false
		isdead = false;
	}
	
	//Get Pokemon name
	public String getName()
	{
		return pokname;
	}
	
	//Get Pokemon xpos
	public double getX()
	{
		return xpos;
	}

	//Get pokemon ypos
	public double getY()
	{
		return ypos;
	}
	
	//Get pokemon height
	public double getHeight()
	{
		return height;
	}

	//Get pokemon width
	public double getWidth()
	{
		return width;
	}
	
	//Get pokemon current health
	public int getHealth()
	{
		return health;
	}
	
	//Get pokemon max health
	public int getMaxHealth()
	{
		return maxhealth;
	}
	
	//get pokemon back image
	public ImageView getPokemonBack()
	{
		return pokviewb;
	}
	
	//get pokemon front image
	public ImageView getPokemonFront()
	{
		return pokviewf;
	}

	//get pokemon icon image
	public ImageView getPokemonIcon()
	{
		return iconview;
	}
	
	//Get pokemon lv
	public int getLv()
	{
		return lv;
	}
	
	//Set pokemon lv
	public void setPokemonLv(int lv)
	{
		this.lv = lv;
	}
	
	//Set pokemon name
	public void setPokemonName(String name)
	{
		pokname = name;
	}
	
	//Void to run when pokemon is being attacked
	public void Hit(int dmg)
	{
		health -= dmg;
		
		if (health < 0)
		{
			health = 0;
			isdead = true;
		}
	}
	
	//Void to run when pokemon is the attacker
	public void Attack(int atk)
	{
		atkpp[atk]--;
		
		if (atkpp[atk] < 0)
		{
			atkpp[atk] = 0;
		}
	}
	
	//Void to run when pokemon is using health potion
	public void HealthPotion(int heal)
	{
		health += heal;
	}
	
	//Void to run when pokemon is using pp
	public void PPPotion (int heal, int atk)
	{
		atkpp[atk] += heal;
		
		if (atkpp[atk] > maxatkpp[atk])
		{
			atkpp[atk] = maxatkpp[atk];
		}
	}
	
	//Set pokemons back image
	public void setPokImageBack(String imgaddress)
	{
		pokimgb = new Image(imgaddress);
		pokviewb = new ImageView (pokimgb);
		pokviewb.setLayoutX(20);
		pokviewb.setLayoutY(680 - pokimgb.getHeight() - 200);
	}
	
	//Set pokemons front image
	public void setPokImageFront(String imgaddress)
	{
		pokimgf = new Image(imgaddress);
		pokviewf = new ImageView (pokimgf);
		pokviewf.setLayoutX(893);
		pokviewf.setLayoutY(8);
	}
	
	//Set pokemons icon image
	public void setPokImageIcon(String imgaddress)
	{
		iconimg = new Image(imgaddress);
		iconview = new ImageView (iconimg);
	}
	
	//Set pokemons max health
	public void setMaxHealth(int health)
	{
		maxhealth = health;
	}
	
	//Set pokemons current health
	public void setHealth(int health)
	{
		this.health = health;
	}
	
	//Set pokemons attack damage
	public void setAttackDamage(int dmg1, int dmg2, int dmg3, int dmg4)
	{
		atkdmg[0] = dmg1;
		atkdmg[1] = dmg2;
		atkdmg[2] = dmg3;
		atkdmg[3] = dmg4;
	}
	
	//Set pokemons attack pp
	public void setAttackPP (int pp1, int pp2, int pp3, int pp4)
	{
		atkpp[0] = pp1;
		atkpp[1] = pp2;
		atkpp[2] = pp3;
		atkpp[3] = pp4;
	}
	
	//Set pokemons max attack pp
	public void setMaxAttackPP (int pp1, int pp2, int pp3, int pp4)
	{
		maxatkpp[0] = pp1;
		maxatkpp[1] = pp2;
		maxatkpp[2] = pp3;
		maxatkpp[3] = pp4;
	}
	
	//Set pokemons attack name
	public void setAttackName (String name1, String name2, String name3, String name4)
	{
		atkname[0] = name1;
		atkname[1] = name2;
		atkname[2] = name3;
		atkname[3] = name4;
	}
}
