package application;

import java.util.Random;
import javafx.scene.image.*;

// The Enemy class represents an enemy character in the game.
public class Enemy 
{
	private Random rnd;
	private int dir;
	private int xpos, ypos;
	private Image imgeup, imgeright, imgedown, imgeleft, imgefront;
	private ImageView ive, ivefront;
	private double width, height;
	public final static int up = 0, right = 1, down = 2, left = 3;
	private int sel;
	public boolean fought;

	//Constructs an Enemy clss with random initial settings.
	public Enemy() 
	{
		rnd = new Random();
		sel = rnd.nextInt(3) + 0;
		dir = up;

		// Setting images based on direction
		imgeup = new Image("file:EnemyTrainers/Trainer" + sel + "_0.png");
		imgeright = new Image("file:EnemyTrainers/Trainer" + sel + "_1.png");
		imgedown = new Image("file:EnemyTrainers/Trainer" + sel + "_2.png");
		imgeleft = new Image("file:EnemyTrainers/Trainer" + sel + "_3.png");
		imgefront = new Image("file:EnemyTrainers/Trainer" + sel + "_Front.png");

		// Initializing ImageViews
		ive = new ImageView(imgeup);
		ivefront = new ImageView(imgefront);

		width = imgeup.getWidth();
		height = imgeup.getHeight();

		fought = false;
	}

	//Sets the X-coordinate of the enemy.
	public void setX(int x) 
	{
		xpos = x;
	}

	//Sets the Y-coordinate of the enemy.
	public void setY(int y) 
	{
		ypos = y;
	}

	// Gets the X-coordinate of the enemy.
	public int getX() 
	{
		return xpos;
	}

	// Gets the Y-coordinate of the enemy.
	public int getY() 
	{
		return ypos;
	}

	//Gets the width of the enemy image.
	public double getWidth() 
	{
		return width;
	}

	//Gets the height of the enemy image.
	public double getHeight() 
	{
		return height;
	}

	//Gets the current direction of the enemy.
	public int getDirection()
	{
		if (dir == up) 
		{
			return up;
		} 

		else if (dir == left) 
		{
			return left;
		} 

		else if (dir == right) 
		{
			return right;
		} 

		else if (dir == down) 
		{
			return down;
		}

		return dir;
	}

	//Sets the direction of the enemy.
	public void setDirection(int dir2) 
	{
		dir = dir2;
	}

	//Gets the enemy image based on its current direction.
	public ImageView getImage() 
	{
		if (fought == false) 
		{
			if (dir == up) 
			{
				ive.setImage(imgeup);
			} 

			else if (dir == right) 
			{
				ive.setImage(imgeright);
			} 

			else if (dir == down) 
			{
				ive.setImage(imgedown);
			} 

			else if (dir == left) 
			{
				ive.setImage(imgeleft);
			}
		}
		return ive;
	}

	// Gets the front image of the enemy.
	public ImageView getFrontImage() 
	{
		return ivefront;
	}

	//Sets the location of the enemy.
	public void setLocation(int x, int y) 
	{
		xpos = x;
		ypos = y;

		ive.setX(xpos);
		ive.setY(ypos);
	}
}