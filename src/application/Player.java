package application;

import javafx.scene.image.*;

//Player class for managing player character animations and coordinates and items
public class Player 
{
	private String name, item;
	private Image imgfaceup, imgfaceright, imgfacedown, imgfaceleft;
	private Image imgwalkup, imgwalkright, imgwalkdown, imgwalkleft;
	private Image imgrunup, imgrunright, imgrundown, imgrunleft;
	private ImageView ivplayer;
	public final static int FaceUp = 1, FaceRight = 2, FaceDown = 3, FaceLeft = 4;
	public final static int WalkUp = 5, WalkRight = 6, WalkDown = 7, WalkLeft = 8;
	public final static int RunUp = 9, RunRight = 10, RunDown = 11, RunLeft = 12;
	private int dir, sel;
	public int potionct, ppct, maxpotionct, maxppct, revivect, maxrevivect;
	public int potionhl, pphl, maxpotionhl, maxpphl, revivehl, maxrevivehl;
	private Image imgpot, imgpp, imgmaxpot, imgmaxpp, imgrev, imgmaxrev;
	private ImageView ivitem;
	public final static int Pot = 0, PP = 1, Rev = 2, MaxPot = 3, MaxPP = 4, MaxRev = 5;
	private double xpos, ypos, width, height;

	//Constructor initializng all values and images
	public Player()
	{
		//Default names
		name = "Chris";
		item = "idk styl";
		
		//Initialize player face images
		imgfaceup = new Image("file:PlayerAnimations/FaceUp.png");
		imgfaceright = new Image("file:PlayerAnimations/FaceRight.png");
		imgfacedown = new Image("file:PlayerAnimations/FaceDown.png");
		imgfaceleft = new Image("file:PlayerAnimations/FaceLeft.png");

		//Initialize player walking images
		imgwalkup = new Image("file:PlayerAnimations/WalkUp.gif");
		imgwalkright = new Image("file:PlayerAnimations/Walkright.gif");
		imgwalkdown = new Image("file:PlayerAnimations/WalkDown.gif");
		imgwalkleft = new Image("file:PlayerAnimations/WalkLeft.gif");

		//Initialize player running images
		imgrunup = new Image("file:PlayerAnimations/RunUp.gif");
		imgrunright = new Image("file:PlayerAnimations/Runright.gif");
		imgrundown = new Image("file:PlayerAnimations/RunDown.gif");
		imgrunleft = new Image("file:PlayerAnimations/RunLeft.gif");

		//Set imageviw for player as face down
		ivplayer = new ImageView(imgfacedown);
		
		//Initialize all bag items
		imgpot = new Image ("file:Bag Item Icons/PotionIcon.png");
		imgpp = new Image ("file:Bag Item Icons/PPUpIcon.png");
		imgmaxpot = new Image ("file:Bag Item Icons/MaxPotionIcon.png");
		imgmaxpp = new Image ("file:Bag Item Icons/MaxPPUpIcon.png");
		imgrev = new Image ("file:Bag Item Icons/ReviveIcon.png");
		imgmaxrev = new Image ("file:Bag Item Icons/MaxReviveIcon.png");
		
		//Set imageview for items as potiion and location
		ivitem = new ImageView (imgpot);
		ivitem.setLayoutX(400);
		ivitem.setLayoutY(280);
		
		//Initialize dir, positions, and image width and height
		dir = FaceUp;
		xpos = 0;
		ypos = 0;

		width = imgfacedown.getWidth();
		height = imgfacedown.getHeight();

		//Initialize player bag item count
		potionct = 5;
		maxpotionct = 0;
		ppct = 3;
		maxppct = 0;
		revivect = 2;
		maxrevivect = 0;

		//Set player bag item healing amount
		potionhl = 60;
		maxpotionhl = 120;
		pphl = 5;
		maxpphl = 15;
		revivehl = 40;
		maxrevivehl = 250;
	}

	//Get x position
	public double getX()
	{
		return xpos;
	}

	//Get y position
	public double getY()
	{
		return ypos;
	}

	//Get player height
	public double getHeight()
	{
		return height;
	}

	//Get player width
	public double getWidth()
	{
		return width;
	}

	//Get player direction
	public int getDirection()
	{
		return dir;
	}

	//Get item imageview
	public ImageView getItemNode()
	{
		if (sel == Pot)
		{
			ivitem.setImage(imgpot);
			item = "Potion";
		}
		
		else if (sel == PP)
		{
			ivitem.setImage(imgpp);
			item = "PPUp";
		}
		
		else if (sel == Rev)
		{
			ivitem.setImage(imgrev);
			item = "Revive";
		}
		
		else if (sel == MaxPot)
		{
			ivitem.setImage(imgmaxpot);
			item = "Max Potion";
		}
		
		else if (sel == MaxPP)
		{
			ivitem.setImage(imgmaxpp);
			item = "Max PPUp";
		}
		
		else if (sel == MaxRev)
		{
			ivitem.setImage(imgmaxrev);
			item = "Max Revive";
		}
		
		return ivitem;
	}
	
	//Get player imageview
	public ImageView getPlayerNode()
	{
		//Facing Animations
		if (dir == FaceUp)
		{
			ivplayer.setImage(imgfaceup);
		}

		else if (dir == FaceRight)
		{
			ivplayer.setImage(imgfaceright);
		}

		else if (dir == FaceDown)
		{
			ivplayer.setImage(imgfacedown);
		}	

		else if (dir == FaceLeft)
		{
			ivplayer.setImage(imgfaceleft);
		}

		//Walking Animations
		else if (dir == WalkUp)
		{
			ivplayer.setImage(imgwalkup);
		}

		else if (dir == WalkRight)
		{
			ivplayer.setImage(imgwalkright);
		}

		else if (dir == WalkDown)
		{
			ivplayer.setImage(imgwalkdown);
		}	

		else if (dir == WalkLeft)
		{
			ivplayer.setImage(imgwalkleft);
		}

		//Running Animations
		else if (dir == RunUp)
		{
			ivplayer.setImage(imgrunup);
		}

		else if (dir == RunRight)
		{
			ivplayer.setImage(imgrunright);
		}

		else if (dir == RunDown)
		{
			ivplayer.setImage(imgrundown);
		}	

		else if (dir == RunLeft)
		{
			ivplayer.setImage(imgrunleft);
		}

		return ivplayer;
	}

	//Get player name
	public String getPlayerName()
	{
		return name;
	}
	
	//Get item name
	public String getItemName()
	{
		return item;
	}
	
	//Player walk right data
	public void walkRight()
	{
		xpos += 7;
		dir = WalkRight;
		ivplayer.setImage(imgwalkright);
		ivplayer.setX(xpos);
	}

	//Player walk left data
	public void walkLeft()
	{
		xpos -= 7;
		dir = WalkLeft;
		ivplayer.setImage(imgwalkleft);
		ivplayer.setX(xpos);
	}

	//Player walk up data
	public void walkUp()
	{
		ypos -= 7;
		dir = WalkUp;
		ivplayer.setImage(imgwalkup);
		ivplayer.setY(ypos);
	}

	//Player walk down data
	public void walkDown()
	{
		ypos += 7;
		dir = WalkDown;
		ivplayer.setImage(imgwalkdown);
		ivplayer.setY(ypos);
	}

	//Player run right data
	public void runRight()
	{
		xpos += 12;
		dir = RunRight;
		ivplayer.setImage(imgrunright);
		ivplayer.setX(xpos);
	}

	//Player run left data
	public void runLeft()
	{
		xpos -= 12;
		dir = RunLeft;
		ivplayer.setImage(imgrunleft);
		ivplayer.setX(xpos);
	}

	//Player run up data
	public void runUp()
	{
		ypos -= 12;
		dir = RunUp;
		ivplayer.setImage(imgrunup);
		ivplayer.setY(ypos);
	}

	//Player run down data
	public void runDown()
	{
		ypos += 12;
		dir = RunDown;
		ivplayer.setImage(imgrundown);
		ivplayer.setY(ypos);
	}
	
	//Set player location
	public void setLocation (double x, double y)
	{
		xpos = x;
		ypos = y;

		ivplayer.setX(xpos);
		ivplayer.setY(ypos);
	}

	//Set player xpos
	public void setX(double x)
	{
		xpos = x;
	}

	//Set player ypos
	public void setY(double y)
	{
		ypos = y;
	}

	//Set player direction
	public void setDirection(int dir)
	{
		this.dir = dir;
	}
	
	//Set item selected
	public void setItemSelection (int sel)
	{
		this.sel = sel;
	}

	//Set bag item count in inventory
	//potion
	public void setPotionCount(int count)
	{
		potionct = count;
	}

	//max potion
	public void setMaxPotionCount(int count)
	{
		maxpotionct = count;
	}

	//ppup
	public void setPPCount(int count)
	{
		ppct = count;
	}

	//max ppup
	public void setMaxPPCount(int count)
	{
		maxppct = count;
	}
	
	//revive
	public void setReviveCount(int count)
	{
		revivect = count;
	}

	//max revive
	public void setMaxReviveCount(int count)
	{
		maxrevivect = count;
	}
	
	//Set player name
	public void setPlayerName(String name)
	{
		this.name = name; 
	}
}
