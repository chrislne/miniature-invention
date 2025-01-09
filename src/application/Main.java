package application;

import javafx.scene.control.Label;
import java.io.File;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.animation.KeyFrame;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.AudioClip;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.*;

//Sources/Links
//https://pkmn.net/?action=content&page=viewsection&id=87
//https://www.spriters-resource.com/game_boy_advance/pokemonemerald/
//https://www.online-image-editor.com/

/*
 * Name: Christoir Lane
 * Course: ICS 4UI
 * Date: January 19, 2023
 * Description: My game is a remake of pokemon emerald but mainly focuses on the battle aspect between trainers spotting the player
 * And surviving. The way I did my code is i knew that i would be reusing multipule lines of code so I put them all into sepereate
 * void methds that i can call to chain into each other based on the users actions. I chose to create an inherited class that utilizes polymorphism
 * called Pokemon that stores all the data that every pokemon must have to be one. Using that I created a few sub classes that inherite the
 * pokemon class to create the players pokemon, the enemy pokemon, and the final bosses pokemon. Within the seperate pokemon classes,
 * They all have their own uniqe name, attacks, attack pps, health, attack damage. For the player i added a feautre that animates
 * the players image if he player is holding shift and moving wich initiates, running, or moving without shift which initiates
 * walking, or letting go of all movements which initiates a standing animation that faces whatever the last direction the user was holding.
 * Within the player class i also have items stored in there that the user can use in battle and find on the map to store more items to help in battle
 * . I added a void that sets a rectangle that changes based on the enemy trainers direction and is supposed to repreresent the trainers field
 * of vision and if the player intersects the vision, then they initiate a battle. The point of the game is to beat tranverse from map to map
 * and defeat the final boss by any means such as avoiding battles with trainers or picking up items to help the player in battle. I also 
 * added a mechanic where the user can switch out their current pokemon to battle and set the health and attacks to the pokemon switched out.
 * The user also has the ability to select a new game or load a previous game attempt when loading up the game. The previous attempt
 * is data that is updated and overwritten after every battle and if the user exited the program then they can come load back into the same map
 * with the same items in their inventory.
 */

public class Main extends Application 
{
	//Global Variables
	private Random rnd;
	private Scene scene;
	private Image blur, switchbackimg, mainhimg, sidehimg, mainbimg, sidebimg, map1, map2, map3, imgitem;
	private ImageView blurview, switchbackiv, mainhiv, sidehiv1, sidehiv2, mainbiv, sidebiv1, sidebiv2, iveh, ivph, ivmap;
	private Image [] imgentry = new Image [3];
	private Image [] imgback = new Image [3];
	private ImageView [] ivitem = new ImageView [3];
	private ImageView [] iventry = new ImageView [3];
	private ImageView [] ivback = new ImageView [3];
	private AnimationTimer ani;
	private Timeline btimer, etimer, ttimer, dtimer, turntimer, ptimer;
	private TextArea tbox;
	private Label blktop, blkbot, pokswitch, blk;
	private Label hbar, gbar, gbar2, atkinfo;
	private Font textfont, optfont;
	private Pane root;
	private ArrayList <Character> chararr = new ArrayList <Character>();
	private int index = 0, select, initialhealth, pokeout, cycle, inter, item;
	private String word = "", phealthcol, ehealthcol, name;
	private int barwidth, barwidth2, enemyatkind, count, direction, enemysel;
	private boolean trainer, end;
	private Pokemon[] pok = new Pokemon[7];
	private Player player;
	private RadioButton bla, cha, ven;
	private boolean [] pokout = new boolean [3];
	private boolean [] itemtook = new boolean [3];
	private String opt = "";
	private boolean up, right, down, left, run, start, map1sel, map2sel, map3sel, tit, caught, bcaught, bhit, loot;
	private Image imgtit, imgminitit;
	private ImageView ivtit, ivminitit;
	private Enemy[] enemy;
	private Enemy boss;
	private Rectangle[] rec;
	private Media mtit, mbtl, mwin, mlow;
	private MediaPlayer titplay, btlplay, winplay, lowplay;
	private AudioClip [] aud;
	private File data;

	//START BATTLE METHOD VARIAVBLES
	private Image ph, eh;
	private Label backblk, backred, backgrey, pname, plv, bbar, pname2, elv, bbar2;
	private HBox hbox;
	private TilePane tpane;
	int dir = 0;

	//Void to start program (main)
	public void start(Stage primaryStage) 
	{
		try 
		{

			//Initialize all different classes including player, enemy, boss, and all pokemon
			player = new Player();

			boss = new Enemy();

			enemy = new Enemy [3];
			rec = new Rectangle[3];

			pok[0] = new Blastoise();
			pok[1] = new Charizard();
			pok[2] = new Venasuar();
			pok[3] = new Rayquaza();
			pok[4] = new Gardevoir();
			pok[5] = new Hariyama();
			pok[6] = new Crobat();

			//For loop to initialize enemy and enemy vision objects
			for (int i = 0; i < enemy.length; i++)
			{
				enemy[i] = new Enemy();
				rec[i] = new Rectangle();
			}

			//Initialize aand load data file
			data = new File("PokemonPlayerData.txt");

			//By default the blastoise is out first
			pokeout = 0;

			//Initialize variables
			rnd = new Random();

			root = new Pane();

			scene = new Scene(root, 1200, 680);

			pokswitch = new Label();

			//Initialize and set all Images and imageviews
			imgtit = new Image ("file:TitlePage (1).gif");
			ivtit = new ImageView (imgtit);

			imgminitit = new Image("file:Titlepage_Label.png");
			ivminitit = new ImageView (imgminitit);

			textfont = Font.loadFont("file:Fonts/pokemon-emerald.otf", 60);
			optfont = Font.loadFont("file:Fonts/pokemon-emerald.otf", 38);

			blur = new Image ("file:Battle Entrys/Blur.gif");
			blurview = new ImageView(blur);

			switchbackimg = new Image("file:Battle Entrys/Switch_Panel.png");
			switchbackiv = new ImageView(switchbackimg);

			mainhimg = new Image("file:Battle Entrys/Switch_Main.gif");
			mainhiv = new ImageView (mainhimg);

			sidehimg = new Image("file:Battle Entrys/Switch_Side.gif");
			sidehiv1 = new ImageView (sidehimg);
			sidehiv2 = new ImageView (sidehimg);

			//initialize map and item and entry images
			map1 = new Image("file:Maps/Map1.png");
			map2 = new Image("file:Maps/Map2.png");
			map3 = new Image("file:Maps/Map3.png");

			for (int i = 0; i < iventry.length; i++)
			{
				imgentry[i] = new Image ("file:Battle Entrys/Entry"+i+".gif");
				iventry[i] = new ImageView (imgentry[i]);
				iventry[i].setLayoutY(227);
			}

			imgitem = new Image ("file:Maps/Itemsinnit.png");

			for (int i = 0; i < ivitem.length; i++)
			{
				ivitem[i] = new ImageView (imgitem);
			}

			//Set all item locations for map1
			ivitem[0].setLayoutX(950);
			ivitem[0].setLayoutY(120);

			ivitem[1].setLayoutX(945);
			ivitem[1].setLayoutY(490);

			ivitem[2].setLayoutX(230);
			ivitem[2].setLayoutY(440);

			//Initialize all booleans controlling an event
			tit = true;
			caught = false;
			loot = false;

			trainer = true;
			bhit = false;
			start = false;
			end = false;

			pokout[0] = true;
			pokout[1] = false;
			pokout[2] = false;

			//Set map1 as default
			ivmap = new ImageView (map1);

			//Initalize all different songs and play titlescreen song by default
			File s1 = new File ("Audio/TitleTheme.mp3");
			mtit = new Media(s1.toURI().toString());
			titplay = new MediaPlayer (mtit);
			titplay.setOnEndOfMedia(new Runnable ()
			{
				//Handle method
				public void run()
				{
					//Set the song to loop back once it hits the end
					titplay.seek(Duration.ZERO);
				}
			});
			titplay.play();

			File s2 = new File("Audio/BattleTheme.mp3");
			mbtl = new Media(s2.toURI().toString());
			btlplay = new MediaPlayer(mbtl);
			btlplay.setOnEndOfMedia(new Runnable ()
			{
				//Handle method
				public void run()
				{
					//Set the song to loop back once it hits the end
					btlplay.seek(Duration.ZERO);
				}
			});

			File s3 = new File ("Audio/VictoryTheme.mp3");
			mwin = new Media(s3.toURI().toString());
			winplay = new MediaPlayer(mwin);


			File s4 = new File ("Audio/LowHealthTheme.mp3");
			mlow = new Media(s4.toURI().toString());
			lowplay = new MediaPlayer(mlow);
			lowplay.setOnEndOfMedia(new Runnable ()
			{
				//Handle method
				public void run()
				{
					//Set the song to loop back once it hits the end
					lowplay.seek(Duration.ZERO);
				}
			});

			//Initalize all sound effects
			aud = new AudioClip[] {new AudioClip ("file:Audio/ButtonSound.mp3"), new AudioClip ("file:Audio/HitEffect.mp3")
					, new AudioClip ("file:Audio/HealingEffect.mp3"), new AudioClip ("file:Audio/BarrierHit.mp3")
					, new AudioClip ("file:Audio/EnterRoom.mp3"), new AudioClip ("file:Audio/ItemPickUp.mp3")};

			name = "";

			//initialize health bar colors and width
			phealthcol = "lightgreen";
			ehealthcol = "lightgreen";

			barwidth = 241;
			barwidth2 = 255;

			//Initialize all indexs
			item = 0;
			enemyatkind = 0;
			direction = 1;
			inter = 0;

			//Set different battle backgrounds based on the map
			if (map1sel == true)
			{
				select = 0;
			}

			else if (map2sel == true)
			{
				select = 1;
			}

			else if (map3sel == true)
			{
				select = 2;
			}

			blk = new Label();
			blk.setPrefSize(1220, 680);
			blk.setStyle("-fx-background-color: black");

			gbar = new Label();
			gbar.setStyle("-fx-background-color: " + phealthcol);
			gbar.setLayoutX(639);
			gbar.setLayoutY(388);
			gbar.setPrefSize(barwidth, 25);

			//Add titlepage to gui
			root.getChildren().addAll(ivtit);

			scene.setOnKeyPressed(new EventHandler<KeyEvent>()
			{
				public void handle (KeyEvent e)
				{
					//If titlepage is open
					if (tit == true)
					{
						if (e.getCode() == KeyCode.ENTER)
						{
							aud[0].play();
							ButtonType ng = new ButtonType("New Game");
							ButtonType lg = new ButtonType("Load Game");

							//Ask user if they want to load a previous attempt or start a new game
							Alert load = new Alert (null);
							load.setTitle("Start Game");
							load.setHeaderText(null);
							load.setContentText("Select an option to start the game:");
							load.setGraphic(ivminitit);
							load.getButtonTypes().clear();
							load.getButtonTypes().addAll(ng, lg);

							//Set enemy and player locations by default
							enemy[0].setLocation(405, 524);
							enemy[1].setLocation(860, 315);
							enemy[2].setLocation(334, 140);
							player.setLocation(69, 225);

							//Store answer in arraylist
							Optional <ButtonType> res = load.showAndWait();

							//If the player chooses to play a new game file
							if (res.get() == ng)
							{
								aud[0].play();

								//Prompt user to enter in their trainer name
								TextInputDialog dg = new TextInputDialog();
								dg.setTitle("Trainer Name");
								dg.setHeaderText(null);
								dg.setGraphic(ivminitit);
								dg.setContentText("Enter in A Name:");

								Optional <String> res2 = dg.showAndWait();

								//If user clicks confirm set name as what user submitted
								if (res2.isPresent())
								{
									aud[0].play();
									player.setPlayerName(res2.get());
								}

								//If user clicks cancel
								else
								{
									aud[0].play();
								}
							}

							//if the player chooses to load a previously saved file
							else
							{
								loadGame();
								aud[0].play();
							}

							//Remove the titlepage and load the game in
							root.getChildren().remove(ivtit);
							root.getChildren().addAll(ivmap, player.getPlayerNode());

							for (int i = 0; i < enemy.length; i++)
							{
								enemy[i].setDirection(enemy[i].right);
								root.getChildren().addAll(enemy[i].getImage());
							}

							for (int i = 0; i < ivitem.length; i++)
							{
								root.getChildren().addAll(ivitem[i]);
							}

							tit = false;
							map1sel = true;
						}
					}

					//If shift is held, the player runs
					if (e.getCode() == KeyCode.SHIFT)
					{
						run = true;
					}

					//Movement keys to move player
					if (e.getCode() == KeyCode.RIGHT)
					{
						right = true;
						dir = player.FaceRight;
					}

					else if (e.getCode() == KeyCode.LEFT)
					{
						left = true;
						dir = player.FaceLeft;
					}

					else if (e.getCode() == KeyCode.UP)
					{
						up = true;
						dir = player.FaceUp;
					}

					else if (e.getCode() == KeyCode.DOWN)
					{
						down = true;
						dir = player.FaceDown;
					}
				}
			});

			//If the user releases the movement keys, set the action as false
			scene.setOnKeyReleased(new EventHandler<KeyEvent>()
			{
				public void handle (KeyEvent e)
				{
					if (e.getCode() == KeyCode.SHIFT)
					{
						run = false;
					}

					if (e.getCode() == KeyCode.RIGHT)
					{
						right = false;
					}

					else if (e.getCode() == KeyCode.LEFT)
					{
						left = false;
					}

					else if (e.getCode() == KeyCode.UP)
					{
						up = false;
					}

					else if (e.getCode() == KeyCode.DOWN)
					{
						down = false;
					}
				}
			});

			//Timer to turn the enemy trainers
			KeyFrame enemyturn = new KeyFrame(Duration.seconds(1.5), new EventHandler<ActionEvent>()
			{
				public void handle (ActionEvent e)
				{
					//Direction changes after every cycle
					direction++;

					//Reset direction back to 0
					if (direction >= 4)
					{
						direction = 0;
					}

					//Set the enemys direction as the new direction and update the rect to follow
					for (int i = 0; i < enemy.length; i++)
					{
						enemy[i].setDirection(direction);
						enemy[i].getImage();

						enemyRectBounds(enemy[i], rec[i]);
					}
				}
			});

			turntimer = new Timeline (enemyturn);
			turntimer.setCycleCount(Timeline.INDEFINITE);
			turntimer.play();


			//Timer to remove the blur animation after 2 seconds and start the enviornment animation
			KeyFrame blurintro = new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>()
			{
				public void handle (ActionEvent e)
				{
					btlplay.play();

					blkbot = new Label();
					blkbot.setStyle("-fx-background-color: black");
					blkbot.setPrefSize(scene.getWidth(), 227);
					blkbot.setLayoutY(454);

					blktop = new Label();
					blktop.setStyle("-fx-background-color: black");
					blktop.setPrefSize(scene.getWidth(), 227);
					blktop.setLayoutY(0);

					root.getChildren().remove(blurview);
					root.getChildren().addAll(iventry[select], blktop, blkbot);
					etimer.play();
				}
			});
			btimer = new Timeline(blurintro);

			//Timer to run through the battle entry intro and start the battle
			KeyFrame envintro = new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>()
			{
				public void handle (ActionEvent e)
				{
					for(int i = 3; i < pok.length; i++)
					{
						pok[i].setHealth(pok[i].getMaxHealth());
					}

					root.getChildren().removeAll(blurview, blktop, blkbot, iventry[select]);
					startBattle(pok[pokeout], pok[enemysel], select);
					ani.start();
				}

			});
			etimer = new Timeline(envintro);

			//Pause Timer
			KeyFrame pause = new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>()
			{
				public void handle (ActionEvent e)
				{
					ani.start();
				}
			});

			ptimer = new Timeline (pause);

			ani = new AnimationTimer()
			{
				public void handle(long arg0) 
				{
					//If battle has started
					if (start == true)
					{
						//Change current players pokemon health bar color and music based on health
						if (pokout[pokeout])
						{
							if (pok[pokeout].getHealth() > ((pok[pokeout].getMaxHealth() / 4) * 2) )
							{
								lowplay.stop();
								btlplay.play();
								phealthcol = "lightgreen";
								gbar.setStyle("-fx-background-color: " + phealthcol);
							}

							else if (pok[pokeout].getHealth() < ((pok[pokeout].getMaxHealth() / 4) * 2) && pok[pokeout].getHealth() > (pok[pokeout].getMaxHealth() / 4))
							{
								btlplay.pause();
								lowplay.play();
								phealthcol = "yellow";
								gbar.setStyle("-fx-background-color: " + phealthcol);
							}

							else if (pok[pokeout].getHealth() < (pok[pokeout].getMaxHealth() / 4))
							{
								phealthcol = "red";
								gbar.setStyle("-fx-background-color: " + phealthcol);
							}
						}

						//Change enemys health bar color based on health for boss/rayquaza
						if (pok[3].getHealth() > ((pok[3].getMaxHealth() / 4) * 2))
						{
							ehealthcol = "lightgreen";
							gbar2.setStyle("-fx-background-color: " + ehealthcol);
						}

						else if (pok[3].getHealth() < ((pok[3].getMaxHealth() / 4) * 2) && pok[3].getHealth() > (pok[3].getMaxHealth() / 4))
						{
							ehealthcol = "yellow";
							gbar2.setStyle("-fx-background-color: " + ehealthcol);
						}

						else if (pok[3].getHealth() < (pok[3].getMaxHealth() / 4))
						{
							ehealthcol = "red";
							gbar2.setStyle("-fx-background-color: " + ehealthcol);
						}

						//Change enemys health bar color based on health for gardevoir, hariyama, and crobat
						if (pok[enemysel].getHealth() > ((pok[enemysel].getMaxHealth() / 4) * 2))
						{
							ehealthcol = "lightgreen";
							gbar2.setStyle("-fx-background-color: " + ehealthcol);
						}

						else if (pok[enemysel].getHealth() < ((pok[enemysel].getMaxHealth() / 4) * 2) && pok[enemysel].getHealth() > (pok[enemysel].getMaxHealth() / 4))
						{
							ehealthcol = "yellow";
							gbar2.setStyle("-fx-background-color: " + ehealthcol);
						}

						else if (pok[enemysel].getHealth() < (pok[enemysel].getMaxHealth() / 4))
						{
							ehealthcol = "red";
							gbar2.setStyle("-fx-background-color: " + ehealthcol);
						}
					}

					//Set players image as stationary facing his last pressed direction
					if (up == false && right == false && down == false && left == false)
					{
						player.setDirection(dir);
						player.getPlayerNode();
					}

					//When player is moving up
					else if (up == true)
					{
						//Boundary check for player on map 1
						if (map1sel == true)
						{
							if (player.getX() >= 75 && player.getX() <= 1050 && player.getY() <= 90)
							{
								aud[3].play();
								player.setY(90);
							}

							else if (player.getX() <= 155 && player.getY() <= 190)
							{
								aud[3].play();
								player.setY(190);
							}

							else if (player.getX() >= 1050 && player.getY() <= 190)
							{
								aud[3].play();
								player.setY(190);
							}
						}

						//Boundary check for player on map 2
						if (map2sel == true)
						{
							//System.out.println("map 2 up");
							if (player.getY() <= 65)
							{
								aud[3].play();
								player.setY(65);
							}

							else if (player.getY() <= 468 && player.getX() >= 275 && player.getX() + player.getWidth() <= 503)
							{
								aud[3].play();
								player.setY(468);
							}

							else if (player.getY() <= 335 && player.getX() >= 835 && player.getX() + player.getWidth() <= 995)
							{
								aud[3].play();
								player.setY(335);
							}

							else if (player.getY() <= 191 && player.getX() >= 900)
							{
								aud[3].play();
								player.setY(191);
							}

							//ROCKS
							else if (player.getY() <= 332 && player.getY() + player.getHeight() >= 280 && player.getX() <= 145)
							{
								aud[3].play();
								player.setY(332);
							}

							else if (player.getY() <= 471 && player.getY() + player.getHeight() >= 337 && player.getX() >= 495 && player.getX() <= 670)
							{
								aud[3].play();
								player.setY(471);
							}

							else if (player.getY() <= 197 && player.getY() + player.getHeight() >= 130 && player.getX() >= 787 && player.getX() <= 856)
							{
								aud[3].play();
								player.setY(197);
							}

							else if (player.getY() <= 315 && player.getX() >= 990 && player.getX() <= 1050)
							{
								aud[3].play();
								player.setY(315);
							}

							//Computer
							else if (player.getY() <= 470 && player.getY() + player.getHeight() >= 410 && player.getX() + player.getWidth() >= 980 && player.getX() <= 1060)
							{
								aud[3].play();
								player.setY(470);
							}
						}

						//Boundary check for player on map 3
						if (map3sel == true)
						{
							if (player.getY() <= 210)
							{
								aud[3].play();
								player.setY(210);
							}

							else if (player.getY() <= 255 && player.getX() <= 400)
							{
								aud[3].play();
								player.setY(255);
							}

							else if (player.getY() <= 255 && player.getX() + player.getWidth() >= 800)
							{
								aud[3].play();
								player.setY(255);
							}
						}

						//If players running use run speed
						if (run == true)
						{
							player.runUp();
						}

						//If players walkinguse walking speed
						else if (up == true && run == false)
						{
							player.walkUp();
						}
					}

					//If player is moving rright
					else if (right == true)
					{
						//Boundary check for player on map 1
						if (map1sel == true)
						{
							if (player.getX() + player.getWidth() >= 1053 && player.getY() <= 170)
							{
								aud[3].play();
								player.setX(1053 - player.getWidth());
							}

							else if (player.getX() + player.getWidth() >= 1053 && player.getY() + player.getHeight() >= 530)
							{
								aud[3].play();
								player.setX(1053 - player.getWidth());
							}

							else if (player.getX() + player.getWidth() >= 1125)
							{
								aud[3].play();
								player.setX(1125 - player.getWidth());
							}
						}

						//Boundary check for player on map 1
						if (map2sel == true)
						{
							if (player.getX() + player.getWidth() >= 285 && player.getX() <= 450 && player.getY() <= 450)
							{
								aud[3].play();
								player.setX(285 - player.getWidth());
							}

							else if (player.getX() + player.getWidth() >= 570 && player.getX() <= 725 && player.getY() + player.getHeight() >= 170)
							{
								aud[3].play();
								player.setX(570 - player.getWidth());
							}

							else if (player.getX() + player.getWidth() >= 845 && player.getX() <= 940 && player.getY() <= 320)
							{
								aud[3].play();
								player.setX(845 - player.getWidth());
							}

							else if (player.getX() + player.getWidth() >= 1134 && player.getY() + player.getHeight() >= 140 && player.getY() <= 305)
							{
								aud[3].play();
								player.setX(1134 - player.getWidth());
							}

							else if (player.getX() + player.getWidth() >= 1134 && player.getY() + player.getHeight() >= 435 && player.getY() <= 600)
							{
								aud[3].play();
								player.setX(1134 - player.getWidth());
							}

							//If player hits the exit to next level
							else if (player.getY() + player.getHeight()  >= 325 && player.getY() <= 425 && player.getX() + player.getWidth() >=  1135)
							{
								//Transition from level 2 to 3
								aud[4].play();
								root.getChildren().addAll(blk);

								map2sel = false;
								map3sel = true;

								//Set all items and enemys off screen and already fought for boss battle
								for (int i = 0; i < enemy.length; i++)
								{
									enemy[i].fought = true;
									ivitem[i].setLayoutX(-1000);
									ivitem[i].setLayoutY(-1000);
									itemtook[i] = true;
								}

								enemy[0].setLocation(-1000, 536);
								enemy[1].setLocation(-1000, 60);
								enemy[2].setLocation(-1000, 280);

								//Set map and locations for player and boss battle
								player.setLocation(600, 400);

								ivmap.setImage(map3);
								boss.setLocation(600, 280);
								root.getChildren().add(boss.getImage());
								nextRoom();
							}
						}

						//Boundary check for player on map 3
						if (map3sel == true)
						{
							if (player.getX() + player.getWidth() >= 880)
							{
								aud[3].play();
								player.setX(880 - player.getWidth());
							}

							else if (player.getX() + player.getWidth() >= 805 && player.getY() <= 265)
							{
								aud[3].play();
								player.setX(805 - player.getWidth());
							}
						}

						//If player is running use run speed
						if (run == true)
						{
							player.runRight();
						}

						//If player is walking use walk speed
						else
						{
							player.walkRight();
						}
					}

					//If player is moving down
					else if (down == true)
					{
						//Boundary check for player on map 1
						if (map1sel == true)
						{
							if (player.getX() + player.getWidth() >= 1080 && player.getY() + player.getHeight() >= 517)
							{
								aud[3].play();
								player.setY(517 - player.getHeight());
							}

							else if (player.getX() + player.getWidth() <= 555 && player.getY() + player.getHeight() >= 605)
							{
								aud[3].play();
								player.setY(606 - player.getHeight());
							}

							else if (player.getX() >= 640 && player.getY() + player.getHeight() >= 605)
							{
								aud[3].play();
								player.setY(606 - player.getHeight());
							}

							else if (player.getX() + player.getWidth() <= 396 && player.getY() + player.getHeight() >= 513)
							{
								aud[3].play();
								player.setY(513 - player.getHeight());
							}

							//If player hits exit to next level
							else if (player.getX() + player.getWidth() <= 665 && player.getX() >= 545 && player.getY() + player.getHeight() >= 615)
							{
								aud[4].play();
								root.getChildren().addAll(blk);
								nextRoom();

								//Transition from level 1 to 2
								map1sel = false;
								map2sel = true;

								//Set all locations for enemys and items and resets their booleans as false to allow intersects
								enemy[0].setLocation(400, 540);
								enemy[1].setLocation(530, 60);
								enemy[2].setLocation(1070, 475);

								for (int i = 0; i < enemy.length; i++)
								{
									enemy[i].setDirection(enemy[0].right);
									enemy[i].fought = false;

									enemyRectBounds(enemy[i], rec[i]);
								}

								ivitem[0].setLayoutX(230);
								ivitem[0].setLayoutY(440);
								itemtook[0] = false;

								ivitem[1].setLayoutX(985);
								ivitem[1].setLayoutY(490);
								itemtook[1] = false;

								ivitem[2].setLayoutX(1060);
								ivitem[2].setLayoutY(208);
								itemtook[2] = false;

								//Set location and image for stage 2
								player.setLocation(67, 185);
								ivmap.setImage(map2);
								turntimer.play();
							}
						}

						//Boundary check for player on map 2
						if (map2sel == true)
						{
							if (player.getY() + player.getHeight() >= 615)
							{
								aud[3].play();
								player.setY(615 - player.getHeight());
							}

							else if (player.getY() + player.getHeight() >= 550 && player.getX() >= 965)
							{
								aud[3].play();
								player.setY(550 - player.getHeight());
							}

							else if (player.getY() + player.getHeight() >= 405 && player.getY() <= 470 && player.getX() + player.getWidth() >= 680 && player.getX() < 1055)
							{
								aud[3].play();
								player.setY(405 - player.getHeight());
							}

							else if (player.getY() + player.getHeight() >= 145 && player.getX() + player.getWidth() >= 575 && player.getX() <= 735)
							{
								aud[3].play();
								player.setY(145 - player.getHeight());
							}

							//ROCKS
							else if (player.getY() + player.getHeight() >= 270 && player.getY() <= 335 && player.getX() <= 142)
							{
								aud[3].play();
								player.setY(270 - player.getHeight());
							}

							else if (player.getY() + player.getHeight() >= 340 && player.getY() <= 468 && player.getX() + player.getWidth() >= 550 && player.getX() <= 590)
							{
								aud[3].play();
								player.setY(340 - player.getHeight());
							}

							else if (player.getY() + player.getHeight() >= 150 && player.getY() <= 200 && player.getX() + player.getWidth() >= 845 && player.getX() <= 860)
							{
								aud[3].play();
								player.setY(150 - player.getHeight());
							}

						}

						//Boundary check for player on map 3
						if (map3sel == true)
						{
							if (player.getY() + player.getHeight() >= 540)
							{
								aud[3].play();
								player.setY(540 - player.getHeight());
							}
						}

						//If player is running use run speed
						if (run == true)
						{
							player.runDown();
						}

						//If player is walking use walk speed
						else
						{
							player.walkDown();
						}
					}

					//If player is moving left
					else if (left == true)
					{
						//Boundary check for player on map 1
						if (map1sel == true)
						{
							if (player.getX() <= 150 && player.getY() <= 170)
							{
								aud[3].play();
								player.setX(150);
							}

							else if (player.getX() <= 380 && player.getY() + player.getHeight() >= 535)
							{
								aud[3].play();
								player.setX(380);
							}

							else if (player.getX() <= 75)
							{
								aud[3].play();
								player.setX(75);
							}
						}

						//Boundary check for player on map 2
						if (map2sel == true)
						{
							if (player.getX() <= 69)
							{
								aud[3].play();
								player.setX(69);
							}

							else if (player.getX() <= 155 && player.getY() + player.getHeight() >= 270 && player.getY() <= 340)
							{
								aud[3].play();
								player.setX(155);
							}

							else if (player.getX() <= 485 && player.getX() + player.getWidth() >= 290 && player.getY() <= 467)
							{
								aud[3].play();
								player.setX(485);
							}

							else if (player.getX() <= 745 && player.getX() + player.getWidth() >= 585 && player.getY() + player.getHeight() >= 180)
							{
								aud[3].play();
								player.setX(745);
							}

							else if (player.getX() <= 975 && player.getX() + player.getWidth() >= 830 && player.getY() <= 301 && player.getY() + player.getHeight() >= 180)
							{
								aud[3].play();
								player.setX(975);
							}

							else if (player.getX() <= 975 && player.getX() + player.getWidth() >= 795 && player.getY() >= 415 && player.getY() + player.getHeight() <= 600)
							{
								aud[3].play();
								player.setX(975);
							}

							else if (player.getX() <= 1055 && player.getX() + player.getWidth() >= 1020 && player.getY() + player.getHeight() <= 330)
							{
								aud[3].play();
								player.setX(1055);
							}
						}

						//Boundary check for player on map 3
						if (map3sel == true)
						{
							if (player.getX() <= 328)
							{
								aud[3].play();
								player.setX(328);
							}

							else if (player.getX() <= 400 && player.getY() <= 265)
							{
								aud[3].play();
								player.setX(400);
							}
						}

						//If player is running use run speed
						if (run == true)
						{
							player.runLeft();
						}

						//If player is walking use walk speed
						else
						{
							player.walkLeft();
						}
					}

					//When the titlepage is gone
					if (tit == false)
					{
						//If the boss isnt alrdy intersected with player
						if (bhit == false)
						{
							//When player intersects the boss initiate boss battle
							if (player.getPlayerNode().getBoundsInParent().intersects(boss.getImage().getBoundsInParent()))
							{
								bhit = true;
								boss.setX(-1000);
								turntimer.stop();
								bcaught = true;
								titplay.pause();
								ani.stop();		

							}
						}

						for (int i = 0; i < ivitem.length; i++)
						{
							//If current item isnt already looted
							if (itemtook[i] == false)
							{
								//When player intersects with an item give the user a random item
								if (player.getPlayerNode().getBoundsInParent().intersects(ivitem[i].getBoundsInParent()))
								{
									ivitem[i].setLayoutX(-10000);
									turntimer.stop();
									loot = true;
									inter = i;
									item = rnd.nextInt(3) + 0;
									titplay.pause();
									ani.stop();
								}
							}
						}

						for (int i = 0; i < enemy.length; i++)
						{
							//If the selected enemy hasnt already fought the user
							if (enemy[i].fought == false)
							{
								//If the player intersects the enemys vision initiate a fight
								if (player.getPlayerNode().getBoundsInParent().intersects(rec[i].getBoundsInParent()))
								{
									rec[i].setLayoutX(-2000);
									turntimer.stop();
									caught = true;
									inter = i;
									titplay.pause();
									ani.stop();
								}
							}
						}
					}

					//Alerts to run later
					Platform.runLater(new Runnable()
					{
						public void run()
						{
							//If the user lost load game over alert
							if (end == true)
							{
								Alert lose = new Alert(null);
								lose.setTitle("Game Over");
								lose.setHeaderText(null);
								lose.setContentText("All of your pokemon have fainted, please try again in 'New Game'");
								lose.setGraphic(ivminitit);
								lose.getButtonTypes().add(ButtonType.OK);
								lose.showAndWait();

								System.exit(0);
							}

							//If the user obtained item, prompt alert for item obtained
							else if (loot == true)
							{
								aud[5].play();
								Alert obtain = new Alert(null);
								obtain.setTitle("Found an Item!");
								obtain.setHeaderText(null);

								if (item == 0)
								{
									player.setItemSelection(player.MaxPot);
									player.maxpotionct++;
								}

								else if (item == 1)
								{
									player.setItemSelection(player.MaxPP);
									player.maxppct++;
								}

								else if (item == 2)
								{
									player.setItemSelection(player.MaxRev);
									player.maxrevivect++;
								}

								obtain.setGraphic(player.getItemNode());
								obtain.setContentText(player.getPlayerName() + " you just found a " + player.getItemName() + "!");
								obtain.getButtonTypes().clear();
								obtain.getButtonTypes().addAll(ButtonType.OK);
								obtain.showAndWait();
								aud[0].play();

								itemtook[inter] = true;
								loot = false;

								up = false;
								right = false;
								down = false;
								left = false;
								run = false;

								titplay.play();
								turntimer.play();

								ani.start();
							}

							//If player is caught by trainer, prompt alert to notify incoming battle
							else if (caught == true)
							{
								Alert seen = new Alert(null);
								seen.setTitle("Incoming Battle!");
								seen.setHeaderText(null);
								seen.setGraphic(enemy[inter].getFrontImage());
								seen.setContentText("Lets fight right now " + player.getPlayerName() + "!");
								seen.getButtonTypes().clear();
								seen.getButtonTypes().addAll(ButtonType.OK);
								seen.showAndWait();
								aud[0].play();

								enemysel = rnd.nextInt(3) + 4;
								enemy[inter].fought = true;
								start = true;
								caught = false;

								root.getChildren().add(blurview);			
								btimer.play();
							}

							//If the player is caught by the boss, prompt alert to notify incoming boss battle
							else if (bcaught == true)
							{
								Alert seen = new Alert(null);
								seen.setTitle("Final Battle!");
								seen.setHeaderText(null);
								seen.setGraphic(boss.getFrontImage());
								seen.setContentText("You made it to the end " + player.getPlayerName() + ", but you still have to fight me."
										+ "\nPrepare to lose!");
								seen.getButtonTypes().clear();
								seen.getButtonTypes().addAll(ButtonType.OK);
								seen.showAndWait();
								aud[0].play();

								enemysel = 3;
								start = true;
								bcaught = false;

								root.getChildren().add(blurview);			
								btimer.play();
							}

							//If player beat boss, prompt victory alert and end game
							else if (bcaught == true && pok[3].isdead == true)
							{
								Alert won = new Alert(null);
								won.setTitle("Congratulations!");
								won.setHeaderText(null);
								won.setContentText("Congratulations " + player.getPlayerName() + " You Win!"
										+ "\nThanks for playing");
								won.getButtonTypes().clear();
								won.getButtonTypes().addAll(ButtonType.OK);
								won.showAndWait();

								System.exit(0);
							}
						}
					});
				}
			};
			ani.start();

			primaryStage.setScene(scene);
			primaryStage.show();	
		}

		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}

	//Void that sets the screen as a black temporarily for when the user exits to the next stage
	private void nextRoom() 
	{	
		KeyFrame swap = new KeyFrame (Duration.seconds(1.5), new EventHandler <ActionEvent> ()
		{
			public void handle (ActionEvent e)
			{	
				root.getChildren().removeAll(blk);
			}
		});
		Timeline stimer = new Timeline(swap);
		stimer.play();

	}

	//Void that sets the enemys vision as a rectangle and changes positions based on direction
	//enemy = Specific enemy trainer class
	//rec = Specific enemy trainers vision/rectangle
	private void enemyRectBounds(Enemy enemy, Rectangle rec) 
	{
		//If enemy is facing up set the vision rec above the enemy
		if (enemy.getDirection() == enemy.up)
		{
			rec.setLayoutX(enemy.getX());
			rec.setLayoutY(enemy.getY() - 600);
			rec.setWidth(enemy.getWidth());
			rec.setHeight(600);
		}

		//If enemy is facing right set the vision rec to the right of the enemy
		else if (enemy.getDirection() == enemy.right)
		{
			rec.setLayoutX(enemy.getX() + enemy.getWidth());
			rec.setLayoutY(enemy.getY());
			rec.setWidth(800);
			rec.setHeight(enemy.getHeight());
		}

		//If enemy is facing down set the vision rec below the enemy
		else if (enemy.getDirection() == enemy.down)
		{
			rec.setLayoutX(enemy.getX());
			rec.setLayoutY(enemy.getY() + enemy.getHeight());
			rec.setWidth(enemy.getWidth());
			rec.setHeight(600);
		}

		//If enemy is facing left set the vision rec to the left of the enemy
		else if (enemy.getDirection() == enemy.left)
		{
			rec.setLayoutX(enemy.getX() - 800);
			rec.setLayoutY(enemy.getY());
			rec.setWidth(800);
			rec.setHeight(enemy.getHeight());
		}
	}

	//Method that initiates the gui look when a battle between the trainer and player has started
	//playerpok = players current pokemon
	//enemypok = enemy trainers only pokemon
	//select = index for the battle background and entry animation
	private void startBattle(Pokemon playerpok, Pokemon enemypok, int select) 
	{
		start = true;
		aud[3].stop();

		//Initialize battle background
		for (int i = 0; i < imgback.length; i++)
		{
			imgback[i] = new Image ("file:Battle Entrys/Background"+i+".png");
			ivback[i] = new ImageView (imgback[i]);
		}

		//Initialize all player and enemy health HUDs
		//player health icon
		ph = new Image ("file:Battle Entrys/Player_Health.png");
		ivph = new ImageView (ph);
		ivph.setLayoutX(412);
		ivph.setLayoutY(320);

		//enemy health icon
		eh = new Image ("file:Battle Entrys/Enemy_Health.png");
		iveh = new ImageView (eh);
		iveh.setLayoutX(302);
		iveh.setLayoutY(15);

		//Backgrounds for battle options
		backblk = new Label();
		backblk.setStyle("-fx-background-color: black");
		backblk.setPrefSize(1200, 200);
		backblk.setLayoutY(480);

		backred = new Label();
		backred.setStyle("-fx-background-color: red");
		backred.setPrefSize(576, 205);
		backred.setLayoutX(2);
		backred.setLayoutY(483);

		backgrey = new Label();
		backgrey.setStyle("-fx-background-color: grey");
		backgrey.setPrefSize(616, 197);
		backgrey.setLayoutX(580);
		backgrey.setLayoutY(483);

		//PLAYERS HEALTH
		pname = new Label (playerpok.getName());
		pname.setLayoutX(483);
		pname.setLayoutY(340);
		pname.setFont(optfont);
		pname.setAlignment(Pos.CENTER_LEFT);

		//players level
		plv = new Label (Integer.toString(playerpok.getLv()));
		plv.setLayoutX(850);
		plv.setLayoutY(340);
		plv.setFont(optfont);
		plv.setAlignment(Pos.CENTER_LEFT);

		//Players health number
		hbar = new Label();
		hbar.setLayoutX(744);
		hbar.setLayoutY(418);
		hbar.setFont(optfont);
		hbar.setText(Integer.toString(playerpok.getHealth()) + " / " + Integer.toString(playerpok.getMaxHealth()));
		hbar.setAlignment(Pos.CENTER_LEFT);

		//players background healthbar
		bbar = new Label();
		bbar.setStyle("-fx-background-color: grey");
		bbar.setLayoutX(635);
		bbar.setLayoutY(388);
		bbar.setPrefSize(241, 25);

		//ENEMYS HEALTH
		pname2 = new Label(enemypok.getName());
		pname2.setLayoutX(333);
		pname2.setLayoutY(36);
		pname2.setFont(optfont);
		pname2.setAlignment(Pos.CENTER_LEFT);

		//enemy level
		elv = new Label (Integer.toString(enemypok.getLv()));
		elv.setLayoutX(706);
		elv.setLayoutY(45);
		elv.setFont(optfont);
		elv.setAlignment(Pos.CENTER_LEFT);

		//enemy background healthbar
		bbar2 = new Label();
		bbar2.setStyle("-fx-background-color: grey");
		bbar2.setLayoutX(490);
		bbar2.setLayoutY(91);
		bbar2.setPrefSize(255, 35);

		//enemy health bar
		gbar2 = new Label();
		gbar2.setStyle("-fx-background-color: " + ehealthcol);
		gbar2.setLayoutX(490);
		gbar2.setLayoutY(92);
		gbar2.setPrefSize(255, 35);

		//OPTIONS
		//Hbox to store all battle options and teaxt area
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(0, 6, 0, 6));

		//textarea inputting all events in battle
		tbox = new TextArea();
		tbox.setPrefSize(1200 - 30 - 600, 100);
		tbox.setLayoutX(15);
		tbox.setLayoutY(680 - 188 - 6);
		tbox.setStyle("-fx-control-inner-background: seagreen");
		tbox.setEditable(false);
		tbox.setVisible(true);
		tbox.setFont(textfont);

		//Tilepane to store and organize all battle options
		TilePane tpane = new TilePane();
		tpane.setPrefColumns(2);
		tpane.setVgap(8);
		tpane.setHgap(10);

		Button [] [] opts = new Button [2] [2];

		for (int i = 0; i < opts.length; i++)
		{
			for (int j = 0; j < opts.length; j++)
			{
				opts[j][i] = new Button();
				opts[j][i].setPrefSize(300, 20);
				opts[j][i].setLayoutY(480);
				opts[j][i].setLayoutX(800);
				opts[j][i].setFont(textfont);
				opts[j][i].setStyle("-fx-background-color: white");
				tpane.getChildren().addAll(opts[j][i]);
			}
		}

		//Attack button
		opts[0][0].setText("FIGHT");
		opts[0][0].setAlignment(Pos.CENTER_LEFT);
		opts[0][0].setOnAction(e -> fightingMoves(playerpok, enemypok, opts, hbox));

		//Switch pokemon button
		opts[0][1].setText("POKéMON");
		opts[0][1].setAlignment(Pos.CENTER_LEFT);
		opts[0][1].setOnAction(e -> switchPokemon(hbox, playerpok, ivph, enemypok, iveh));

		//item bag button
		opts[1][0].setText("BAG");
		opts[1][0].setAlignment(Pos.CENTER_LEFT);
		opts[1][0].setOnAction(e -> bagOption(opts));

		//Run button
		opts[1][1].setText("RUN");
		opts[1][1].setAlignment(Pos.CENTER_LEFT);
		opts[1][1].setOnAction(e -> runAway(playerpok, ivph, enemypok, iveh, opts));

		hbox.setLayoutY(485);
		hbox.setMargin(tpane, new Insets(0, 0, 0, 8));
		hbox.getChildren().addAll(tbox, tpane);

		//Add all labels to battle gui
		//PLAYER
		root.getChildren().addAll(ivback[select], playerpok.getPokemonBack(), bbar, gbar, ivph, hbar ,pname, plv);
		//ENEMY
		root.getChildren().addAll(enemypok.getPokemonFront(), bbar2, gbar2, iveh, pname2, elv);
		//BATTLE
		root.getChildren().addAll(backblk, backred, backgrey, hbox);

		//type out default text
		typingText("What will\n" + playerpok.getName() + " do?", tbox);
	}

	//Void that allows the user to utilize all items in their inventory in battle
	//opts = battle button options
	private void bagOption(Button[][] opts) 
	{
		//Combobox to select and show all items in inventory
		aud[0].play();
		ComboBox<String> bag = new ComboBox();
		bag.getItems().addAll(player.potionct + "x\tPotion", player.ppct + "x\tPPUp", 
				player.maxpotionct + "x\tMax Potion", player.maxppct + "x\tMax PPUp", 
				player.revivect + "x\tRevive", player.maxrevivect + "x\tMax Revive");
		bag.setPromptText("POKEITEMS");
		bag.setStyle("-fx-font-size: 40");
		bag.setLayoutX(600);
		bag.setLayoutY(80);

		//Set up bag images to gui
		Image imgbck = new Image ("file:Bag Item Icons/BagBackground.png");
		ImageView ivbck = new ImageView (imgbck);
		ivbck.setLayoutX(100);
		ivbck.setLayoutY(10);

		Label item = new Label();
		item.setGraphic(player.getItemNode());
		item.setLayoutX(140);
		item.setLayoutY(315);

		TextArea info = new TextArea();
		info.setLayoutX(99);
		info.setLayoutY(425);
		info.setPrefSize(445, 230);
		info.setFont(optfont);
		info.setEditable(false);

		//when an item is selected
		bag.setOnAction(new EventHandler <ActionEvent> ()
		{
			public void handle(ActionEvent e) 
			{
				//Store selected item
				aud[0].play();
				opt = bag.getSelectionModel().getSelectedItem().substring(bag.getSelectionModel().getSelectedItem().indexOf("\t") + 1);

				//prompt info text for it, and the image for it
				//potion
				if (opt.equals("Potion"))
				{
					player.setItemSelection(player.Pot);
					item.setGraphic(player.getItemNode());
					info.setText("Potion\nHeals a specified pokemon "
							+ "\nwithin the party for " + player.potionhl + "HP");

				}

				//ppup
				else if (opt.equals("PPUp"))
				{
					player.setItemSelection(player.PP);
					item.setGraphic(player.getItemNode());
					info.setText("PPUp\nHeals a specified pokemon's "
							+ "\nmove for " + player.pphl + "PP");
				}

				//max potion
				else if (opt.equals("Max Potion"))
				{
					player.setItemSelection(player.MaxPot);
					item.setGraphic(player.getItemNode());		
					info.setText("Max Potion\nHeals a specified pokemmon "
							+ "\nwithin the party for " + player.maxpotionhl + "HP");
				}

				//max ppup
				else if (opt.equals("Max PPUp"))
				{
					player.setItemSelection(player.MaxPP);
					item.setGraphic(player.getItemNode());
					info.setText("Max PPUp\nHeals a specified pokemon's "
							+ "\nmove for " + player.maxpphl + "PP");
				}

				//revive
				else if (opt.equals("Revive"))
				{
					player.setItemSelection(player.Rev);
					item.setGraphic(player.getItemNode());
					info.setText("Revive\nReveives a players pokemon "
							+ "\nback to life but if applied on "
							+ "\nalive pokemon, heals for "
							+ "\n" + player.revivehl + "HP");
				}

				//max revive
				else if (opt.equals("Max Revive"))
				{
					player.setItemSelection(player.MaxRev);
					item.setGraphic(player.getItemNode());
					info.setText("Max Revive\nReveives a players pokemon "
							+ "\nback to life but if applied on "
							+ "\nalive pokemon, heals for "
							+ "\n" + player.maxrevivehl + "HP");
				}
			}			
		});

		//Cancel bag option
		Button cancel = new Button();
		cancel.setFont(textfont);
		cancel.setText("Cancel");
		cancel.setLayoutX(825);
		cancel.setLayoutY(510);
		cancel.setPrefSize(235, 25);

		//Apply selected item
		Button apply = new Button();
		apply.setFont(textfont);
		apply.setText("Apply");
		apply.setLayoutX(570);
		apply.setLayoutY(510);
		apply.setPrefSize(235, 25);
		apply.setOnAction(e -> applyItem(opts, opt,ivbck, item, bag, info, apply, cancel));

		//Return gui back to main battle creen
		cancel.setOnAction(new EventHandler <ActionEvent> ()
		{
			public void handle(ActionEvent arg0) 
			{
				aud[0].play();
				root.getChildren().removeAll(ivbck, item, bag, info, apply, cancel);	
			}
		});

		root.getChildren().addAll(ivbck, item, bag, info, apply, cancel);
	}

	//Void that prompts alerts to confirm with the user the item they selected and which pokemon to use on
	private void applyItem(Button[][] opts, String opt, ImageView ivbck, Label item, ComboBox<String> bag, TextArea info, Button apply, Button cancel)
	{
		//if the user chose an item and submitted
		if (opt.isBlank() == false)
		{
			aud[0].play();
			int poksel = 1;
			int ind = 0;
			ButtonType bla = new ButtonType ("Blastoise");
			ButtonType cha = new ButtonType ("Charizard");
			ButtonType ven = new ButtonType ("Venausar");

			//prompt alert to ask user what pokemon they want to apply item to
			Alert choice = new Alert(null);
			choice.setTitle("Select Pokemon");
			choice.setHeaderText(null);
			choice.setContentText("Select a pokemon you would like to apply a " + player.getItemName() + "for:");
			choice.setGraphic(player.getItemNode());
			choice.getButtonTypes().clear();
			choice.getButtonTypes().addAll(bla, cha, ven, ButtonType.CANCEL);

			Optional <ButtonType> res = choice.showAndWait();

			if (res.get() != ButtonType.CANCEL)
			{
				//Get logic error alerts ready
				aud[0].play();
				Alert error = new Alert(AlertType.ERROR);
				error.setHeaderText(null);
				error.setTitle("You Can't Do That!");
				error.setHeaderText(null);

				Alert dead = new Alert(AlertType.ERROR);
				dead.setTitle("You Can't Do That");
				dead.setHeaderText(null);
				dead.setContentText("You can't use a potion or max potion on a pokemon that is dead!");

				//if user selects blastoise store the index
				if (res.get() == bla)
				{
					poksel = 0;
				}

				//if user selects charizard store the index
				else if (res.get() == cha)
				{
					poksel = 1;
				}

				//if user selects venausar store the index
				else if (res.get() == ven)
				{
					poksel = 2;
				}

				//If the item selected was a pp up
				if (opt.contains("PPUp"))
				{
					ButtonType atk1 = null;
					ButtonType atk2 = null;
					ButtonType atk3 = null;
					ButtonType atk4 = null;

					//Prompt alert to ask user what attack they want to heal its pp for
					Alert selmove = new Alert(null);
					selmove.setTitle("Select Attack");
					selmove.setHeaderText(null);
					selmove.setContentText("Select a move to restore its pp for " + pok[poksel] + ":");
					selmove.setGraphic(player.getItemNode());

					//Show the moves for the users previously selected pokemon
					//Blastoise
					if (res.get() == bla)
					{
						poksel = 0;

						atk1 = new ButtonType(pok[0].atkname[0]);
						atk2 = new ButtonType(pok[0].atkname[1]);
						atk3 = new ButtonType(pok[0].atkname[2]);
						atk4 = new ButtonType(pok[0].atkname[3]);
					}

					//Charizard
					else if (res.get() == cha)
					{
						poksel = 1;

						atk1 = new ButtonType(pok[1].atkname[0]);
						atk2 = new ButtonType(pok[1].atkname[1]);
						atk3 = new ButtonType(pok[1].atkname[2]);
						atk4 = new ButtonType(pok[1].atkname[3]);
					}

					//Venasuar
					else if (res.get() == ven)
					{
						poksel = 2;

						atk1 = new ButtonType(pok[2].atkname[0]);
						atk2 = new ButtonType(pok[2].atkname[1]);
						atk3 = new ButtonType(pok[2].atkname[2]);
						atk4 = new ButtonType(pok[2].atkname[3]);
					}

					selmove.getButtonTypes().clear();
					selmove.getButtonTypes().addAll(atk1, atk2, atk3, atk4, ButtonType.CANCEL);

					Optional <ButtonType> res2 = selmove.showAndWait();
					aud[0].play();

					//IF THE USER CHOSE A MAX PP UP
					if (opt.contains("Max"))
					{
						//If user has it in inventory
						if (player.maxppct > 0)
						{
							if (res2.get() != ButtonType.CANCEL)
							{
								//Store the index of the pokemons attack tp heal its pp
								//atk 1
								if (res2.get() == atk1)
								{
									ind = 0;
								}

								//atk2
								else if (res2.get() == atk2)
								{
									ind = 1;
								}

								//atk3
								else if (res2.get() == atk3)
								{
									ind = 2;
								}

								//atk4
								else if (res2.get() == atk4)
								{
									ind = 3;
								}

								//Confirm code to apply item
								else
								{
									pok[poksel].PPPotion(player.maxpphl, ind);
									player.setMaxPPCount(player.maxppct - 1);
									typingText(player.getPlayerName() + " used a Max PPUp on\n" + pok[poksel].getName(), tbox);
								}
							}
						}

						//Error code for insufficent amount of item in inventory
						else
						{
							error.setContentText("You don't have enough Max PPUp's to use");
							error.showAndWait();
							aud[0].play();
						}
					}

					//IF THE USER CHOSE A REGULAR PP UP
					else
					{
						//If user has it in inventory
						if (player.ppct > 0)
						{
							if (res2.get() != ButtonType.CANCEL)
							{
								//Store the index of the pokemons attack tp heal its pp
								//atk1
								if (res2.get() == atk1)
								{
									ind = 0;
								}

								//Attack 2
								else if (res2.get() == atk2)
								{
									ind = 1;
								}

								//Attack3
								else if (res2.get() == atk3)
								{
									ind = 2;
								}

								//Attack 4
								else if (res2.get() == atk4)
								{
									ind = 3;
								}

								//Confirm code to apply item
								else
								{
									pok[poksel].PPPotion(player.pphl, ind);		
									player.setPPCount(player.ppct - 1);
									typingText(player.getPlayerName() + " used a PPUp on\n" + pok[poksel].getName(), tbox);
								}
							}
						}

						//Error code for insufficent amount of item in inventory
						else
						{
							error.setContentText("You don't have enough Max PPUp's to use");
							error.showAndWait();
							aud[0].play();
						}				
					}
				}	

				//IF THE USER APPLIES ANY TYPE OF POTION
				if (opt.contains("Potion"))
				{
					//IF A MAX POTION IS APPLIED
					if (opt.contains("Max"))
					{
						//If user has it in inventory
						if (player.maxpotionct > 0)
						{	
							if (pok[poksel].getHealth() <= 0)
							{
								dead.showAndWait();
								aud[0].play();
							}

							//Confirm code to apply item
							else
							{
								pok[poksel].HealthPotion(player.maxpotionhl);
								healthIncreasing(gbar, barwidth, pok[poksel], player.maxpotionhl);
								player.setMaxPotionCount(player.maxpotionct - 1);
								typingText(player.getPlayerName() + " used a Max Potion on\n" + pok[poksel].getName(), tbox);
							}
						}

						//Error code for insufficent amount of item in inventory
						else
						{
							error.setContentText("You don't have enough Max Potion's to use");
							error.showAndWait();
							aud[0].play();
						}	
					}

					//IF A NORMAL POTION IS APPLIED
					else
					{
						//If user has it in inventory
						if (player.potionct > 0)
						{
							//if pokemon slected is dead prompt error alert
							if (pok[poksel].getHealth() <= 0)
							{
								dead.showAndWait();
								aud[0].play();
							}

							//Confirm code to apply item
							else
							{
								pok[poksel].HealthPotion(player.potionhl);
								healthIncreasing(gbar, barwidth, pok[poksel], player.potionhl);
								player.setPotionCount(player.potionct - 1);
								typingText(player.getPlayerName() + " used a Potion on\n" + pok[poksel].getName(), tbox);
							}
						}

						//Error code for insufficent amount of item in inventory
						else
						{
							error.setContentText("You don't have enough Potion's to use");
							error.showAndWait();
							aud[0].play();
						}	
					}
				}

				//If item selected is a revive
				if (opt.contains("Revive"))
				{
					//IF THE USER USES A MAX REVIVE
					if (opt.contains("Max"))
					{
						//If user has it in inventory
						if (player.maxrevivect > 0)
						{
							pok[poksel].HealthPotion(player.maxrevivehl);
							healthIncreasing(gbar, barwidth, pok[poksel], player.maxrevivehl);
							player.setPotionCount(player.maxrevivect - 1);
							typingText(player.getPlayerName() + " used a Max Revive on\n" + pok[poksel].getName(), tbox);
						}

						//Error code for insufficent amount of item in inventory
						else
						{
							error.setContentText("You don't have enough Max Revive's to use");
							error.showAndWait();
							aud[0].play();
						}	
					}

					//IF THE USER USES A REVIVE
					else
					{
						//If user has it in inventory
						if (player.revivect > 0)
						{
							pok[poksel].HealthPotion(player.revivehl);
							healthIncreasing(gbar, barwidth, pok[poksel], player.revivehl);
							player.setPotionCount(player.revivect - 1);
							typingText(player.getPlayerName() + " used a Revive on\n" + pok[poksel].getName(), tbox);
						}

						//Error code for insufficent amount of item in inventory
						else
						{
							error.setContentText("You don't have enough Revive's to use");
							error.showAndWait();
							aud[0].play();
						}	
					}
				}
			}
		}
		//If user didnt select an item prompt error alert
		else
		{
			Alert error = new Alert (AlertType.ERROR);
			error.setTitle("Select An Item");
			error.setHeaderText(null);
			error.setContentText("You must select an item to click 'APPLY'!");
			error.showAndWait();
			aud[0].play();
		}

		//Temporarily disable players buttons to prevent errors
		root.getChildren().removeAll(ivbck, item, bag, info, apply, cancel);	

		opts[0][0].setDisable(true);
		opts[0][1].setDisable(true);
		opts[1][0].setDisable(true);
		opts[1][1].setDisable(true);

		KeyFrame pause = new KeyFrame(Duration.seconds(2.8), new EventHandler<ActionEvent>()
		{
			public void handle (ActionEvent e)
			{
				opts[0][0].setDisable(false);
				opts[0][1].setDisable(false);
				opts[1][0].setDisable(false);
				opts[1][1].setDisable(false);
			}
		});

		Timeline ptimer = new Timeline(pause);
		ptimer.play();
	}

	//If user selects the run away option like in pokemon
	private void runAway(Pokemon playerpok, ImageView ivph, Pokemon enemypok, ImageView iveh, Button[][] opts) 
	{
		//Ask user if their sure about leaving
		aud[0].play();
		Alert run = new Alert(null);
		run.setTitle("RUN");
		run.setHeaderText(null);
		run.setContentText("Are you sure you want to run?");
		run.getButtonTypes().clear();
		run.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);

		Optional <ButtonType> ans = run.showAndWait();

		if (ans.get() == ButtonType.YES)
		{
			aud[0].play();

			//If player is versing a trainer they cant run away
			if (trainer == true)
			{
				Alert error = new Alert(AlertType.ERROR);
				error.setTitle("Can't Run");
				error.setHeaderText(null);
				error.setContentText("You can't run away from trainers!");
				error.getButtonTypes().clear();
				error.getButtonTypes().add(ButtonType.OK);
				error.showAndWait();
				aud[0].play();
			}

			//If not trainer, run away
			else
			{			
				aud[0].play();
				tbox.setVisible(false);

				opts[0][0].setVisible(false);
				opts[0][1].setVisible(false);
				opts[1][0].setVisible(false);
				opts[1][1].setVisible(false);

				//PLAYER
				root.getChildren().removeAll(ivback[0], ivback[1], ivback[2], playerpok.getPokemonBack(), bbar, gbar, ivph, hbar ,pname, plv);
				//ENEMY
				root.getChildren().removeAll(enemypok.getPokemonFront(), bbar2, gbar2, iveh, pname2, elv);
				//BATTLE
				root.getChildren().removeAll(backblk, backred, backgrey, hbox);

			}
		}
	}

	//Switch current pokemon out for a pokemon in their party
	private void switchPokemon(HBox hbox2, Pokemon playerpok, ImageView ivph, Pokemon enemypok, ImageView iveh) 
	{
		aud[0].play();

		//Set up images for switch pokemon gui
		pokswitch.setLayoutX(900);
		pokswitch.setLayoutY(150);

		Label bckg = new Label();
		bckg.setGraphic(switchbackiv);

		VBox vicon = new VBox();
		vicon.setSpacing(20);
		vicon.setLayoutX(bckg.getLayoutX());
		vicon.setLayoutY(50);

		Label mainicon = new Label();
		mainicon.setGraphic(pok[0].getPokemonIcon());

		Label sideicon1 = new Label();
		sideicon1.setGraphic(pok[1].getPokemonIcon());

		Label sideicon2 = new Label();
		sideicon2.setGraphic(pok[2].getPokemonIcon());

		vicon.getChildren().addAll(mainicon, sideicon1, sideicon2);

		VBox vbar = new VBox();
		vbar.setSpacing(20);
		vbar.setLayoutX(bckg.getLayoutX() + 100);
		vbar.setLayoutY(50);

		Label mainhealth = new Label();
		mainhealth.setGraphic(mainhiv);

		Label sidehealth1 = new Label();
		sidehealth1.setGraphic(sidehiv1);

		Label sidehealth2 = new Label();
		sidehealth2.setGraphic(sidehiv2);

		vbar.getChildren().addAll(mainhealth, sidehealth1, sidehealth2);

		VBox vpok = new VBox();
		vpok.setSpacing(65);
		vpok.setLayoutX(bckg.getLayoutX() + 40);
		vpok.setLayoutY(60);

		//Radio Buttons for each players pokemon to select who to switch with
		bla = new RadioButton();
		bla.setFont(optfont);
		bla.setText("\t\tBLASTOISE\n\t\t  Lv.75\t" 
				+ Integer.toString(pok[0].getHealth()) + " / " + Integer.toString(pok[0].getMaxHealth()));

		bla.setOnAction(e -> switchPokemonSelected());

		cha = new RadioButton();
		cha.setFont(optfont);
		cha.setText("\t\tCHARIZARD\n\t\t  Lv.75\t" 
				+ Integer.toString(pok[1].getHealth()) + " / " + Integer.toString(pok[1].getMaxHealth()));
		cha.setOnAction(e -> switchPokemonSelected());

		ven = new RadioButton();
		ven.setFont(optfont);
		ven.setText("\t\tVENASUAR\n\t\t  Lv.75\t" 
				+ Integer.toString(pok[2].getHealth()) + " / " + Integer.toString(pok[2].getMaxHealth()));
		ven.setOnAction(e -> switchPokemonSelected());

		ToggleGroup party = new ToggleGroup();
		party.getToggles().addAll(bla, cha, ven);

		vpok.getChildren().addAll(bla, cha, ven);

		//Confirm switch button
		Button confirm = new Button();
		confirm.setPrefSize(265, 80);
		confirm.setFont(optfont);
		confirm.setText("SWITCH");
		confirm.setLayoutX(932);
		confirm.setLayoutY(575);

		//Cancel switch button
		Button cancel = new Button();
		cancel.setPrefSize(265, 80);
		cancel.setFont(optfont);
		cancel.setText("CANCEL");
		cancel.setLayoutX(657);
		cancel.setLayoutY(575);
		//Return back to battle gui
		cancel.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle (ActionEvent e)
			{
				aud[0].play();

				pokswitch.setGraphic(null);
				root.getChildren().removeAll(bckg, pokswitch, vpok, vicon, vbar, confirm, cancel);
			}
		});

		//Return back to battle gui with switched pokemon
		confirm.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle (ActionEvent e)
			{
				aud[0].play();

				tbox.setDisable(true);

				root.getChildren().removeAll(ivback[select], playerpok.getPokemonBack(), bbar, gbar, ivph, hbar ,pname, plv);
				//ENEMY
				root.getChildren().removeAll(enemypok.getPokemonFront(), bbar2, gbar2, iveh, pname2, elv);

				//BATTLE
				root.getChildren().removeAll(backblk, backred, backgrey, hbox2);

				//If blastoise was selected to switch out 
				if (party.getSelectedToggle() == bla)
				{
					//Switch current pokemon and health data to blastoise if hes alive
					if (pok[0].isdead == false)
					{
						pokeout = 0;
						double diff = (double)barwidth / pok[0].getMaxHealth();
						gbar.setPrefWidth(diff * pok[0].getHealth());
						gbar.setStyle("-fx-background-color: " + phealthcol);
						hbar.setText(Integer.toString(playerpok.getHealth()) + " / " + Integer.toString(playerpok.getMaxHealth()));

						startBattle(pok[pokeout], pok[enemysel], select);
						pokout[0] = true;
						pokout[1] = false ;
						pokout[2] = false;
					}

					//If hes dead prompt dead error alert
					else
					{
						Alert dead = new Alert(null);
						dead.setTitle("Pokemon Dead");
						dead.setHeaderText(null);
						dead.setContentText("Blastoise is dead you cannot select it");
						dead.getButtonTypes().add(ButtonType.OK);
						dead.showAndWait();
						aud[0].play();

						root.getChildren().removeAll(bckg, pokswitch, vpok, vicon, vbar, confirm, cancel);
						switchPokemon(hbox2, playerpok, ivph, enemypok, iveh);
					}
				}

				//Switch current pokemon and health data to charizard if hes alive
				else if (party.getSelectedToggle() == cha)
				{
					if (pok[1].isdead == false)
					{
						pokeout = 1;

						double diff = (double)barwidth / pok[1].getMaxHealth();
						gbar.setPrefWidth(diff * pok[1].getHealth());
						gbar.setStyle("-fx-background-color: " + phealthcol);
						hbar.setText(Integer.toString(playerpok.getHealth()) + " / " + Integer.toString(playerpok.getMaxHealth()));

						startBattle(pok[pokeout], pok[enemysel], select);
						pokout[0] = false;
						pokout[1] = true;
						pokout[2] = false;
						pokeout = 1;
					}

					//If dead prompt user dead error alert
					else
					{
						Alert dead = new Alert(null);
						dead.setTitle("Pokemon Dead");
						dead.setHeaderText(null);
						dead.setContentText("Charizard is dead you cannot select it");
						dead.getButtonTypes().add(ButtonType.OK);
						dead.showAndWait();
						aud[0].play();

						root.getChildren().removeAll(bckg, pokswitch, vpok, vicon, vbar, confirm, cancel);
						switchPokemon(hbox2, playerpok, ivph, enemypok, iveh);
					}
				}

				//Switch current pokemon and health data to venasuar if hes alive
				else if (party.getSelectedToggle() == ven)
				{
					if (pok[2].isdead == false)
					{
						pokeout = 2;
						double diff = (double)barwidth / pok[2].getMaxHealth();
						gbar.setPrefWidth(diff * pok[2].getHealth());
						gbar.setStyle("-fx-background-color: " + phealthcol);
						hbar.setText(Integer.toString(playerpok.getHealth()) + " / " + Integer.toString(playerpok.getMaxHealth()));

						startBattle(pok[pokeout], pok[enemysel], select);
						pokout[0] = false;
						pokout[1] = false;
						pokout[2] = true;
					}

					//If dead prompt user dead error alert					
					else
					{
						Alert dead = new Alert(null);
						dead.setTitle("Pokemon Dead");
						dead.setHeaderText(null);
						dead.setContentText("Venasuar is dead you cannot select it");
						dead.getButtonTypes().add(ButtonType.OK);
						dead.showAndWait();
						aud[0].play();

						root.getChildren().removeAll(bckg, pokswitch, vpok, vicon, vbar, confirm, cancel);
						switchPokemon(hbox2, playerpok, ivph, enemypok, iveh);
					}
				}

				pokswitch.setGraphic(null);
				root.getChildren().removeAll(bckg, pokswitch, vpok, vicon, vbar, confirm, cancel);
			}
		});

		root.getChildren().addAll(bckg, vbar, vpok, vicon, pokswitch, confirm, cancel);
	}

	//Change the graphic of the selected pokemon during the switch pokemon gui
	private void switchPokemonSelected() 
	{
		if (bla.isSelected() == true)
		{
			pokswitch.setGraphic(pok[0].getPokemonFront());
		}

		else if (cha.isSelected() == true)
		{
			pokswitch.setGraphic(pok[1].getPokemonFront());
		}

		else if (ven.isSelected() == true)
		{
			pokswitch.setGraphic(pok[2].getPokemonFront());
		}
	}

	//When the user selects fight, another set of buttons with info will pop up for the current pokemons atacks
	private void fightingMoves(Pokemon playerpok, Pokemon enemypok, Button[][] opts, HBox hbox2) 
	{
		aud[0].play();
		tbox.setVisible(false);

		//set battle option buttons as invisible and change battle options to pokemon attack options
		opts[0][0].setVisible(false);
		opts[0][1].setVisible(false);
		opts[1][0].setVisible(false);
		opts[1][1].setVisible(false);

		//Set images to creat pokemon attack option
		Label backgreyatk = new Label();
		backgreyatk.setStyle("-fx-background-color: grey");
		backgreyatk.setPrefSize(1200, 200);
		backgreyatk.setLayoutX(0);
		backgreyatk.setLayoutY(480);

		atkinfo = new Label();
		atkinfo.setPrefSize(350, 200);
		atkinfo.setLayoutX(850);
		atkinfo.setLayoutY(485);
		atkinfo.setStyle("-fx-background-color: white");
		atkinfo.setFont(optfont);
		atkinfo.setAlignment(Pos.CENTER);

		TilePane tpane = new TilePane();
		tpane.setPrefColumns(2);
		tpane.setVgap(8);
		tpane.setHgap(10);
		tpane.setLayoutY(485);

		Button [] atks = new Button [4];

		for (int i = 0; i < atks.length; i++)
		{
			atks[i] = new Button();
			atks[i].setPrefSize(400, 91);
			atks[i].setFont(optfont);
			atks[i].setStyle("-fx-background-color: white");
			atks[i].setText(playerpok.atkname[i]);						
			tpane.getChildren().addAll(atks[i]);
		}

		//If user hovers attack prompt attack pp and damage
		atks[0].setOnMouseEntered(e -> fightingMoveInfoEntered(playerpok, 0));
		atks[1].setOnMouseEntered(e -> fightingMoveInfoEntered(playerpok, 1));
		atks[2].setOnMouseEntered(e -> fightingMoveInfoEntered(playerpok, 2));
		atks[3].setOnMouseEntered(e -> fightingMoveInfoEntered(playerpok, 3));

		//If user unhovers over attack set prompt as empty
		atks[0].setOnMouseExited(e -> figthingMoveInfoExitted());
		atks[1].setOnMouseExited(e -> figthingMoveInfoExitted());
		atks[2].setOnMouseExited(e -> figthingMoveInfoExitted());
		atks[3].setOnMouseExited(e -> figthingMoveInfoExitted());

		//If user clicks attack button cycle through turns
		atks[0].setOnAction(e -> playerPokAtkTurn(playerpok, 0, enemypok, opts, tpane, hbox2, backgreyatk));
		atks[1].setOnAction(e -> playerPokAtkTurn(playerpok, 1, enemypok, opts, tpane, hbox2, backgreyatk));
		atks[2].setOnAction(e -> playerPokAtkTurn(playerpok, 2, enemypok, opts, tpane, hbox2, backgreyatk));
		atks[3].setOnAction(e -> playerPokAtkTurn(playerpok, 3, enemypok, opts, tpane, hbox2, backgreyatk));

		root.getChildren().addAll(backgreyatk, tpane, atkinfo);
	}

	//Info for specified pokemons attack damage and pp
	private void fightingMoveInfoEntered(Pokemon playerpok, int ind) 
	{
		atkinfo.setText("PP:\t\t" + playerpok.atkpp[ind] + "/" + playerpok.maxatkpp[ind] 
				+ "\nDamage:\t" + playerpok.atkdmg[ind]);
	}

	private void figthingMoveInfoExitted() 
	{
		atkinfo.setText("");
	}

	//Run through the turn system starting with the player starting irst and register each hit
	private void playerPokAtkTurn(Pokemon playerpok, int ind, Pokemon enemypok, Button[][] opts, TilePane tpane, HBox hbox2, Label backgreyatk) 
	{
		//Decrease players attack pp at the clicked index
		aud[0].play();
		playerpok.Attack(ind);

		//If the players attack pp is at 0 prompt error 
		if (playerpok.atkpp[ind] < 0)
		{
			//CHANGE ICON FOR ALERT
			Alert error = new Alert (AlertType.ERROR);
			error.setHeaderText(null);
			error.setTitle("Out of Moves");
			error.setContentText("You can't use that move you have no more pp for it!");
			error.showAndWait();
			aud[0].play();
		}

		else
		{
			root.getChildren().removeAll(atkinfo, backgreyatk, tpane);
			tbox.setVisible(true);

			opts[0][0].setVisible(true);
			opts[0][1].setVisible(true);
			opts[1][0].setVisible(true);
			opts[1][1].setVisible(true);

			opts[0][0].setDisable(true);
			opts[0][1].setDisable(true);
			opts[1][0].setDisable(true);
			opts[1][1].setDisable(true);

			//Register the attack to the enemys health and refresh the battle events in the textarea
			enemypok.Hit(playerpok.atkdmg[ind]);
			healthDecreasing(gbar2, barwidth2, enemypok, playerpok, ind, false);
			typingText(playerpok.getName() +  " used\n" + playerpok.atkname[ind] + "!", tbox);
			//Run through the enemys attack turn
			enemyPokAtkTurn(enemypok, playerpok, hbox2, opts);
		}
	}

	//Run through the turn system ending with the enemy starting after the player and register each hit
	private void enemyPokAtkTurn(Pokemon enemypok, Pokemon playerpok, HBox hbox2, Button [] []opts) 
	{
		//if the enemys pokemon is dead
		if (enemypok.isdead == true || enemypok.getHealth() <= 0)
		{
			count = 0;

			KeyFrame pause = new KeyFrame(Duration.seconds(4), new EventHandler<ActionEvent>()
			{
				public void handle (ActionEvent e)
				{
					count++;
					//on cycle 1
					if (count == 1)
					{
						//If the enemy has fainted update text and victory music
						winplay.play();
						enemypok.isdead = true;
						typingText("Foe " + enemypok.getName() + " has\nFainted!", tbox);
						btlplay.stop();
						lowplay.stop();

						Platform.runLater(new Runnable()
						{
							public void run() 
							{
								//If the user beat the bosses pokemon prompt victory alert and crash game
								if (enemypok.isdead == true)
								{
									if (map3sel)
									{
										Platform.runLater(() ->
										{
											Alert won = new Alert(null);
											won.setTitle("Congratulations!");
											won.setHeaderText(null);
											won.setContentText("Congratulations " + player.getPlayerName() + " You Win!"
													+ "\nThanks for playing");
											won.setGraphic(ivminitit);
											won.getButtonTypes().clear();
											won.getButtonTypes().addAll(ButtonType.OK);
											won.showAndWait();

											System.exit(0);
										});
									}

									//If enem pokemon is dead run alert confirming the user won and save the game data
									else
									{
										Platform.runLater(() ->
										{
											saveGame();

											Alert win = new Alert(null);
											win.setTitle("Good Job " + player.getPlayerName() + "!");
											win.setHeaderText(null);
											win.setGraphic(enemy[inter].getFrontImage());
											win.setContentText("Wow, I didn't think I would lose that bad. My fault " + player.getPlayerName() + "!");
											win.getButtonTypes().clear();
											win.getButtonTypes().addAll(ButtonType.OK);
											win.showAndWait();
											turntimer.play();
											aud[0].play();
											enemypok.isdead = false;
											btlplay.stop();
											winplay.stop();
											lowplay.stop();
											enemypok.isdead = false;
										});
									}

								}
							}
						});
					}

					//on cycle 2
					else if (count == 2)
					{

						//Remove images from battle gui to return back to map
						start = false;
						tbox.setVisible(false);

						//PLAYER
						root.getChildren().removeAll(ivback[select], playerpok.getPokemonBack(), bbar, gbar, ivph, hbar ,pname, plv);
						//ENEMY
						root.getChildren().removeAll(enemypok.getPokemonFront(), bbar2, gbar2, iveh, pname2, elv);

						//BATTLE
						root.getChildren().removeAll(backblk, backred, backgrey, hbox2);

						up = false;
						right = false;
						down = false;
						left = false;
					}
				}
			});

			Timeline ptimer = new Timeline(pause);
			ptimer.setCycleCount(2);
			ptimer.play();
		}

		//If the enemy pokemon is alive randomize its attack
		else
		{
			enemyatkind = rnd.nextInt(4) + 0;;

			while (enemypok.atkpp[enemyatkind] < 0)
			{
				enemyatkind = rnd.nextInt(4) + 0;
			}

			cycle = 0;
			KeyFrame pause = new KeyFrame(Duration.seconds(2.8), new EventHandler<ActionEvent>()
			{
				public void handle (ActionEvent e)
				{
					//Cycle one is the wait time for the player's attack and to finish the typing animation
					cycle++;
					opts[0][0].setDisable(true);
					opts[0][1].setDisable(true);
					opts[1][0].setDisable(true);
					opts[1][1].setDisable(true);

					//Cycle two is the wait time for the enemy's attack and to finish the typing animation
					if (cycle == 2)
					{
						//Register damage dealt to player
						playerpok.Hit(enemypok.atkdmg[enemyatkind]);
						healthDecreasing(gbar, barwidth, playerpok, enemypok, enemyatkind, true);
						typingText("Foe " + enemypok.getName() +  " used\n" + enemypok.atkname[enemyatkind] + "!", tbox);
					}

					//On cycle 3
					if (cycle == 3)
					{
						//If all players pokemon are dead prompt game over alert and crash game
						if (pok[0].getHealth() <= 0 && pok[1].getHealth() <= 0 && pok[2].getHealth() <= 0)
						{
							typingText("CHRIS's pokemon have\nall fainted!", tbox);
							end = true;

							if (end)
							{
								Platform.runLater(() ->
								{
									Alert lose = new Alert(null);
									lose.setTitle("Game Over");
									lose.setHeaderText(null);
									lose.setContentText("All of your pokemon have fainted, please try again in 'New Game'");
									lose.setGraphic(ivminitit);
									lose.getButtonTypes().add(ButtonType.OK);
									lose.showAndWait();

									System.exit(0);
								});
							}

							ani.stop();
							ptimer.stop();
						}

						//When players current pokemon dies force a switch out
						else if (playerpok.getHealth() <= 0)
						{
							switchPokemon(hbox, playerpok, ivph, enemypok, iveh);
							playerpok.isdead = true;
						}

						else
						{
							typingText("What will\n" + playerpok.getName() + " do?", tbox);
						}
					}

					//n cycle 4
					if (cycle == 4)
					{
						//If game isnt over return ability to select battle option
						if (end == false)
						{
							opts[0][0].setDisable(false);
							opts[0][1].setDisable(false);
							opts[1][0].setDisable(false);
							opts[1][1].setDisable(false);
						}
					}
				}
			});

			Timeline ptimer = new Timeline(pause);
			ptimer.setCycleCount(4);
			ptimer.play();
		}
	}

	//Void to create animation where textarea is typing the battles events
	private void typingText(String str, TextArea txt) 
	{
		//Set all values back to 0/empty
		index = 0;
		word = "";
		chararr.clear();

		//Store each character in the char array
		for (int i = 0; i < str.length(); i++)
		{
			chararr.add(str.charAt(i));
		}

		//Update the textarea every 80 millis with the new word
		KeyFrame type = new KeyFrame (Duration.millis(80), new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent arg0) 
			{
				//If index reaches the sentence size stop timer
				if (index >= chararr.size()) 
				{
					ttimer.stop();
				}

				else
				{
					//add a char to the string and update textarea
					word += chararr.get(index);
					txt.setText(word);
					index++;
				}
			}
		});

		ttimer = new Timeline(type);
		ttimer.setCycleCount(Timeline.INDEFINITE);
		ttimer.play();
	}

	//Void to create a health decreasing animation
	private void healthDecreasing(Label healthbar, int hbarwidth, Pokemon pokeatked, Pokemon pokeatking, int atkindex, boolean player) 
	{
		//Calculate difference between healthbars width and the pokemons max health
		//Hit sound effect
		aud[1].play();
		double diff = (double)hbarwidth / pokeatked.getMaxHealth();
		initialhealth = pokeatked.getHealth() + pokeatking.atkdmg[atkindex];

		KeyFrame decrease = new KeyFrame (Duration.millis(10), new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent arg0) 
			{	
				if (pokeatked.getHealth() <= 0 && initialhealth <= 0)
				{
					initialhealth = 0;
					dtimer.stop();
				}

				//If initial health reaches actual health stop timer
				if (initialhealth == pokeatked.getHealth())
				{
					dtimer.stop();
				}

				//If the players health bar is decreasing update its health number
				if (player == true)
				{
					hbar.setText(Integer.toString(initialhealth) + " / " + Integer.toString(pokeatked.getMaxHealth()));
				}
				//Every 10 millis update the healtbar width
				healthbar.setPrefWidth((double) initialhealth * diff);
				initialhealth--;
			}
		});

		dtimer = new Timeline(decrease);
		dtimer.setCycleCount(Timeline.INDEFINITE);
		dtimer.play();
	}

	//Void to create a health increasing animation
	private void healthIncreasing(Label healthbar, int hbarwidth, Pokemon pokehealing, int item) 
	{
		//Calculate difference between healthbars width and the pokemons max health
		//healing sound effect
		aud[2].play();
		double diff = (double)hbarwidth / pokehealing.getMaxHealth();
		initialhealth = pokehealing.getHealth() - item;

		KeyFrame increase = new KeyFrame (Duration.millis(10), new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent arg0) 
			{	
				if (pokehealing.getHealth() >= pokehealing.getMaxHealth())
				{
					pokehealing.setHealth(pokehealing.getMaxHealth());
					dtimer.stop();
				}

				//If initial health reaches actual health stop timer
				if (initialhealth >= pokehealing.getHealth())
				{
					initialhealth = pokehealing.getHealth();
					dtimer.stop();
				}

				//If the players health bar is increasing update its health number
				hbar.setText(Integer.toString(initialhealth) + " / " + Integer.toString(pokehealing.getMaxHealth()));
				healthbar.setPrefWidth((double) initialhealth * diff);
				initialhealth++;
			}
		});

		dtimer = new Timeline(increase);
		dtimer.setCycleCount(Timeline.INDEFINITE);
		dtimer.play();
	}

	//Method that stores data for players, enemys and items position, stats, and pokemon stats
	private void saveGame() 
	{		
		try
		{
			//Create file writer and buffered writer to write the data in data text file
			FileWriter out = new FileWriter (data, false);
			BufferedWriter write = new BufferedWriter (out);

			//Write out players position and trainer name
			write.write(player.getPlayerName());
			write.newLine();

			write.write(player.getX() + " " + player.getY());
			write.newLine();

			//Write all enemy trainer positions
			for(int i = 0; i < enemy.length; i++)
			{
				write.write(enemy[i].getX() + " " + enemy[i].getY());
				write.newLine();
			}

			//Store all pokemon health and their attack pp's at each index
			for(int i = 0; i < pok.length; i++)
			{
				write.write("" + pok[i].getHealth());

				for (int j = 0; j < 4; j++)
				{
					write.write(" " + pok[i].atkpp[j]);
				}
				write.newLine();
			}

			//Store all item locations, and if the item has been lotted or not
			for (int i = 0; i < ivitem.length; i++)
			{
				write.write(ivitem[i].getLayoutX() + " " + ivitem[i].getLayoutY());

				if (itemtook[i] == true)
				{
					write.write(" true");
				}

				else
				{
					write.write(" false");
				}
				write.newLine();
			}

			//Store what current map the user is on
			if (map1sel == true)
			{
				write.write("map1sel = true");
			}

			else if (map2sel == true)
			{
				write.write("map2sel = true");
			}

			else if (map3sel == true)
			{
				write.write("map3sel = true");
			}

			//Store all of the amount of items stored in their inventory
			write.newLine();
			write.write("" +player.potionct);

			write.newLine();
			write.write("" +player.ppct);

			write.newLine();
			write.write("" +player.revivect);

			write.newLine();
			write.write("" +player.maxpotionct);

			write.newLine();
			write.write("" +player.maxpotionct);

			write.newLine();
			write.write("" +player.maxpotionct);

			write.close();
		}		

		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//Void method used to load up a previous game file with their data and use it as the new data
	private void loadGame() 
	{
		try
		{
			//Create File reader and buffered reader to read through the data text file
			FileReader in = new FileReader (data);
			BufferedReader read = new BufferedReader(in);

			//String to store line data
			String line;
			//Line number
			int lnum = 0;

			//Read throught the text file until the bottom is reached
			while ((line = read.readLine()) != null)
			{
				//Increase line number after each line
				lnum++;

				//Line 1-2 is player triner name coordinates
				if (lnum == 1)
				{
					player.setPlayerName(line);
				}

				else if (lnum == 2)
				{
					String[] txtsplit = line.split(" ");

					player.setX(Double.parseDouble(txtsplit[0]));
					player.setY(Double.parseDouble(txtsplit[1]));
				}

				//line 3-5 is enemy coordinates
				else if (lnum == 3)
				{
					String[] txtsplit = line.split(" ");

					enemy[0].setX(Integer.parseInt(txtsplit[0]));
					enemy[0].setY(Integer.parseInt(txtsplit[1]));
				}

				else if (lnum == 4)
				{
					String[] txtsplit = line.split(" ");

					enemy[1].setX(Integer.parseInt(txtsplit[0]));
					enemy[1].setY(Integer.parseInt(txtsplit[1]));
				}

				else if (lnum == 5)
				{
					String[] txtsplit = line.split(" ");

					enemy[2].setX(Integer.parseInt(txtsplit[0]));
					enemy[2].setY(Integer.parseInt(txtsplit[1]));

				}

				//line 6-12 is all pokemons current health and attack pps
				else if (lnum == 6)
				{
					String[] txtsplit = line.split(" ");

					pok[0].setHealth(Integer.parseInt(txtsplit[0]));
					pok[0].setAttackPP(Integer.parseInt(txtsplit[1]), Integer.parseInt(txtsplit[2]), Integer.parseInt(txtsplit[3]), Integer.parseInt(txtsplit[4]));
				}

				else if (lnum == 7)
				{
					String[] txtsplit = line.split(" ");

					pok[1].setHealth(Integer.parseInt(txtsplit[0]));
					pok[1].setAttackPP(Integer.parseInt(txtsplit[1]), Integer.parseInt(txtsplit[2]), Integer.parseInt(txtsplit[3]), Integer.parseInt(txtsplit[4]));
				}

				else if (lnum == 8)
				{
					String[] txtsplit = line.split(" ");

					pok[2].setHealth(Integer.parseInt(txtsplit[0]));
					pok[2].setAttackPP(Integer.parseInt(txtsplit[1]), Integer.parseInt(txtsplit[2]), Integer.parseInt(txtsplit[3]), Integer.parseInt(txtsplit[4]));
				}

				else if (lnum == 9)
				{
					String[] txtsplit = line.split(" ");

					pok[3].setHealth(Integer.parseInt(txtsplit[0]));
					pok[3].setAttackPP(Integer.parseInt(txtsplit[1]), Integer.parseInt(txtsplit[2]), Integer.parseInt(txtsplit[3]), Integer.parseInt(txtsplit[4]));
				}

				else if (lnum == 10)
				{
					String[] txtsplit = line.split(" ");

					pok[4].setHealth(Integer.parseInt(txtsplit[0]));
					pok[4].setAttackPP(Integer.parseInt(txtsplit[1]), Integer.parseInt(txtsplit[2]), Integer.parseInt(txtsplit[3]), Integer.parseInt(txtsplit[4]));
				}

				else if (lnum == 11)
				{
					String[] txtsplit = line.split(" ");

					pok[5].setHealth(Integer.parseInt(txtsplit[0]));
					pok[5].setAttackPP(Integer.parseInt(txtsplit[1]), Integer.parseInt(txtsplit[2]), Integer.parseInt(txtsplit[3]), Integer.parseInt(txtsplit[4]));
				}

				else if (lnum == 12)
				{
					String[] txtsplit = line.split(" ");

					pok[6].setHealth(Integer.parseInt(txtsplit[0]));
					pok[6].setAttackPP(Integer.parseInt(txtsplit[1]), Integer.parseInt(txtsplit[2]), Integer.parseInt(txtsplit[3]), Integer.parseInt(txtsplit[4]));
				}

				//line 13-15 is all item coordinates and if they have been looted already
				else if (lnum == 13)
				{
					String[] txtsplit = line.split(" ");

					ivitem[0].setLayoutX(Double.parseDouble(txtsplit[0]));
					ivitem[0].setLayoutY(Double.parseDouble(txtsplit[1]));

					if (txtsplit[2].contains("true"))
					{
						itemtook[0] = true;
					}

					else if (txtsplit[2].contains("false"))
					{
						itemtook[0] = false;
					}
				}

				else if (lnum == 14)
				{
					String[] txtsplit = line.split(" ");

					ivitem[1].setLayoutX(Double.parseDouble(txtsplit[0]));
					ivitem[1].setLayoutY(Double.parseDouble(txtsplit[1]));

					if (txtsplit[2].contains("true"))
					{
						itemtook[1] = true;
					}

					else if (txtsplit[2].contains("false"))
					{
						itemtook[1] = false;
					}
				}

				else if (lnum == 15)
				{
					String[] txtsplit = line.split(" ");

					ivitem[2].setLayoutX(Double.parseDouble(txtsplit[0]));
					ivitem[2].setLayoutY(Double.parseDouble(txtsplit[1]));

					if (txtsplit[2].contains("true"))
					{
						itemtook[2] = true;
					}

					else if (txtsplit[2].contains("false"))
					{
						itemtook[2] = false;
					}
				}

				//line 16 is the current map the user was last one and based off of that sets both player, enemy, and item locations
				else if (lnum == 16)
				{
					if(line.contains("map1sel"))
					{
						map1sel = true;
						map2sel = false;
						map3sel = false;
						ivmap.setImage(map1);

						enemy[0].setLocation(405, 524);
						enemy[1].setLocation(860, 315);
						enemy[2].setLocation(334, 140);
						player.setLocation(69, 225);
					}

					else if(line.contains("map2sel"))
					{
						map1sel = false;
						map2sel = true;
						map3sel = false;
						ivmap.setImage(map2);

						enemy[0].setLocation(400, 540);
						enemy[1].setLocation(530, 60);
						enemy[2].setLocation(1070, 475);

						for (int i = 0; i < enemy.length; i++)
						{
							enemy[i].setDirection(enemy[0].right);
							enemy[i].fought = false;

							enemyRectBounds(enemy[i], rec[i]);
						}

						ivitem[0].setLayoutX(230);
						ivitem[0].setLayoutY(440);
						itemtook[0] = false;

						ivitem[1].setLayoutX(985);
						ivitem[1].setLayoutY(490);
						itemtook[1] = false;

						ivitem[2].setLayoutX(1060);
						ivitem[2].setLayoutY(208);
						itemtook[2] = false;

						player.setLocation(67, 185);
					}

					else if(line.contains("map3sel"))
					{
						map1sel = false;
						map2sel = false;
						map3sel = true;
						ivmap.setImage(map3);

						for (int i = 0; i < enemy.length; i++)
						{
							enemy[i].fought = true;
							ivitem[i].setLayoutX(-1000);
							ivitem[i].setLayoutY(-1000);
							itemtook[i] = true;
						}

						enemy[0].setLocation(-1000, 536);
						enemy[1].setLocation(-1000, 60);
						enemy[2].setLocation(-1000, 280);

						player.setLocation(600, 400);

						ivmap.setImage(map3);
						boss.setLocation(600, 280);

						root.getChildren().add(boss.getImage());
					}
				}

				//Line 17-22 is the amount of each item left in the players inventory
				else if (lnum == 17)
				{
					player.setPotionCount(Integer.parseInt(line));
				}

				else if (lnum == 18)
				{
					player.setPPCount(Integer.parseInt(line));
				}

				else if (lnum == 19)
				{
					player.setReviveCount(Integer.parseInt(line));
				}

				else if (lnum == 20)
				{
					player.setMaxPotionCount(Integer.parseInt(line));
				}

				else if (lnum == 21)
				{
					player.setMaxPPCount(Integer.parseInt(line));
				}

				else if (lnum == 22)
				{
					player.setMaxReviveCount(Integer.parseInt(line));
				}
			}

			//prompt user with alert to notify their saved file as been loaded in
			Alert hi = new Alert(null);
			hi.setTitle("Welcome Back!");
			hi.setHeaderText(null);
			hi.setContentText("Welcome back " + player.getPlayerName() + " lets start right back from where you left off.");
			hi.setGraphic(ivminitit);
			hi.getButtonTypes().clear();
			hi.getButtonTypes().addAll(ButtonType.OK);
			hi.showAndWait();
			aud[0].play();

		}

		catch (FileNotFoundException e)
		{

		} 

		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) 
	{
		launch(args);
	}
}


