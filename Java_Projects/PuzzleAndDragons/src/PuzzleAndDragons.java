/* Bugs needed to be fixed:
 * 1. Math.round() doesn't round up on some machines
 * 
 */


/* Orb Types:
 *  1. Fire (Red)
 *  2. Water (Blue)
 *  3. Grass (Green)
 *  4. Light (Yellow)
 *  5. Dark (Purple)
 *  6. Heal (Pink)
 */

import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;

public class PuzzleAndDragons extends Applet implements MouseListener, MouseMotionListener, ActionListener {
	
	private static final int NUMROWS = 5;
	private static final int NUMCOLS = 6;
	private static final int SQUAREWIDTH = 70;
	private static final int SQUAREHEIGHT = 71;
	private static final int BOARDLEFT = 72;
	private static final int BOARDTOP = 72;
	
	/*
	 * 1 = Fire orb
	 * 2 = Water orb
	 * 3 = Grass orb
	 * 4 = Light orb
	 * 5 = Dark orb
	 * 6 = Heal orb
	 */
	int board[][] = new int[NUMROWS][NUMCOLS];
	
	
	/* Orb Files */
	File fire = new File("red_orb.png");
	File water = new File("blue_orb.png");
	File grass = new File("green_orb.png");
	File light = new File("yellow_orb.png");
	File dark = new File("purple_orb.png");
	File heal = new File("pink_orb.png");
	File background = new File("background.jpeg");
	File backWall = new File("backwall.jpeg");
	
	MediaTracker tracker = new MediaTracker(this);
	BufferedImage image;
	
	/* Buttons */
	private static Button FireOrb = new Button("Fire Orb"); 
	private static Button WaterOrb = new Button("Water Orb"); 
	private static Button GrassOrb = new Button("Grass Orb");
	private static Button LightOrb = new Button("Light Orb");
	private static Button DarkOrb = new Button("Dark Orb");
	private static Button HeartOrb = new Button("Heal Orb");
	private static Button Random = new Button("Randomize Board");
	private static Button MaxScore = new Button("Calculate Team Score ");
	
	
	@Override
	public void init()
	{
		addMouseMotionListener(this); //Allows motion and clicks simultaneous
		addMouseListener(this); 
		setSize(1020,700);
		setBackground(Color.GRAY);
		
		/* Fire Orb Selection */
		FireOrb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) 
			{
				FireOrb.setEnabled(false);
				WaterOrb.setEnabled(true);
				GrassOrb.setEnabled(true);
				LightOrb.setEnabled(true);
				DarkOrb.setEnabled(true);
				HeartOrb.setEnabled(true);
			}
		});

		/* Water Orb Selection */
		WaterOrb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) 
			{
				FireOrb.setEnabled(true);
				WaterOrb.setEnabled(false);
				GrassOrb.setEnabled(true);
				LightOrb.setEnabled(true);
				DarkOrb.setEnabled(true);
				HeartOrb.setEnabled(true);
			}
		});
		
		/* Grass Orb Selection */
		GrassOrb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) 
			{
				FireOrb.setEnabled(true);
				WaterOrb.setEnabled(true);
				GrassOrb.setEnabled(false);
				LightOrb.setEnabled(true);
				DarkOrb.setEnabled(true);
				HeartOrb.setEnabled(true);
			}
		});
				
		/* Light Orb Selection */
		LightOrb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) 
			{
				FireOrb.setEnabled(true);
				WaterOrb.setEnabled(true);
				GrassOrb.setEnabled(true);
				LightOrb.setEnabled(false);
				DarkOrb.setEnabled(true);
				HeartOrb.setEnabled(true);
			}
		});
		
		/* Dark Orb Selection */
		DarkOrb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) 
			{
				FireOrb.setEnabled(true);
				WaterOrb.setEnabled(true);
				GrassOrb.setEnabled(true);
				LightOrb.setEnabled(true);
				DarkOrb.setEnabled(false);
				HeartOrb.setEnabled(true);
			}
		});
				
		/* Heart Orb Selection */
		HeartOrb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) 
			{
				FireOrb.setEnabled(true);
				WaterOrb.setEnabled(true);
				GrassOrb.setEnabled(true);
				LightOrb.setEnabled(true);
				DarkOrb.setEnabled(true);
				HeartOrb.setEnabled(false);
			}
		});
		
		/* Randomizes Board Button */
		Random.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) 
			{
				RandomizeBoard();
			}
		});
		
		/* Calculates Max Score of Team */
		MaxScore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) 
			{
				//Monster m = new Monster(500, 500, 200, 0, -1); // create hard-coded monster; represents a "Dub-rubylit"
															   // Health: 500
															   // Attack: 500
															   // Recovery: 200
															   // MainAttribute: fire
															   // SubAttribute: None
				
				//Monster m = new Monster(1451, 636, 133, 1, 1); // create hard-coded monster; represents my "Water Twin Star Leviathan"
				   												// Health: 1451
				   												// Attack: 636
				   												// Recovery: 133
																// MainAttribute: water
				   												// SubAttribute: water
				
				Monster m = new Monster(500, 400, 300, 0, 2); // create hard-coded monster; represents a "Red Dragon Fruit"
																// Health: 500
																// Attack: 400
																// Recovery: 300
																// MainAttribute: fire
																// SubAttribute: grass
				
				int maxScore = MaxScoreOfMonster_vs_SingleEnemy_No_Skyfall(m);
				System.out.println("Max output: "+maxScore);
			}
		});
		
		add(FireOrb);
		add(WaterOrb);
		add(GrassOrb);
		add(LightOrb);
		add(DarkOrb);
		add(HeartOrb);
		add(Random);
		add(MaxScore);
	}
	
	/*public void reset ()
	{
        for (int i = 0; i < board.length; i++) 
		{
        	for (int j = 0; j < board.length; j++)
			{
        		board[i][j] = 0;
			}
		}
		repaint();
		
	}*/
	

	public void paint(Graphics canvas)
	{
		/* Set locations of components */
		Random.setLocation(72, 440);
		
		try {
			canvas.drawImage(image = ImageIO.read(backWall), 0, 0, null);
		} catch (IOException e) { e.printStackTrace(); }
		
		/* Draw the canvas */
		try { DrawSquares(canvas); } catch (IOException e) { e.printStackTrace(); } 
	}

	
	/*public void DownToTopSearch()
	{
		int trace[][] = board;
		int nF = 0, nW = 0, nG = 0, nL = 0, nD = 0, nH = 0;
		for(int i = NUMROWS-1; i >= 0; i--)
		{
			
			for(int j = 0; j < NUMCOLS; j++)
			{
				
				int color = trace[i][j];
				if(color == 1)
					nF++;
				else if(color == 2)
					nW++;
				else if(color == 3)
					nG++;
				else if(color == 4)
					nL++;
				else if(color == 5)
					nD++;
				else if(color == 6)
					nH++;
				System.out.println("Right of "+trace[i][j]+" is "+goRight(i,j));
			}
			
		}
	}*/
	
	
	public void DrawSquares(Graphics canvas) throws IOException
	{
		canvas.drawImage(image = ImageIO.read(background), 72, 72, null);
		canvas.setColor(Color.black);
		
		for (int nRow = 0; nRow < NUMROWS; nRow++ )
		{
			for (int nCol = 0; nCol < NUMCOLS; nCol++)
			{
				canvas.drawRect(BOARDLEFT + nCol * SQUAREWIDTH, BOARDTOP + nRow * SQUAREHEIGHT, SQUAREWIDTH, SQUAREHEIGHT);
				
				if(board[nRow][nCol] != 0)
				{
					//If orb is "Fire"
					if (board[nRow][nCol] == 1)
						image = ImageIO.read(fire);
					
					//If orb is "Water"
					else if (board[nRow][nCol] == 2)
						image = ImageIO.read(water);
					
					//If orb is "Grass"
					else if (board[nRow][nCol] == 3)
						image = ImageIO.read(grass);
					
					//If orb is "Light"
					else if (board[nRow][nCol] == 4)
						image = ImageIO.read(light);
					
					//If orb is "Light"
					else if (board[nRow][nCol] == 5)
						image = ImageIO.read(dark);
					
					//If orb is "Light"
					else if (board[nRow][nCol] == 6)
						image = ImageIO.read(heal);

					canvas.drawImage(image, BOARDLEFT - 7 + nCol * SQUAREWIDTH + 8, BOARDTOP + nRow * SQUAREHEIGHT + 6 - 5, null);
				}
			}
		}
		//DownToTopSearch();
	}
	
	
	
	/* Mouse Listener Methods */
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		int x = e.getX();
		int y = e.getY();
		
		if((x < 72 || x > 491) || (y < 72 || y > 428)) //If user clicks outside of board, then do nothing
			return;
		
		for (int nRow = 0; nRow < NUMROWS; nRow++ )
		{
			for (int nCol = 0; nCol < NUMCOLS; nCol++)
			{
				int nLeftXBoxEdge = BOARDLEFT + nCol * SQUAREWIDTH;
				int nRightXBoxEdge = nLeftXBoxEdge + SQUAREWIDTH;
				int nLeftYBoxEdge = BOARDLEFT + nRow * SQUAREHEIGHT;
				int nRightYBoxEdge = nLeftYBoxEdge + SQUAREHEIGHT;
				
				if (x > nLeftXBoxEdge && x < nRightXBoxEdge && y > nLeftYBoxEdge && y < nRightYBoxEdge)
				{
					
					/* Indicates what orbs are to be placed; doesn't allow multiple placements of same orb */
					if(!FireOrb.isEnabled()) //'1' indicates a red orb
					{
						if(board[nRow][nCol] == 1)
							return;
						board[nRow][nCol] = 1;
					}
					else if(!WaterOrb.isEnabled()) //'2' indicates a blue orb
					{
						if(board[nRow][nCol] == 2)
							return;
						board[nRow][nCol] = 2;
					}
					else if(!GrassOrb.isEnabled()) //'3' indicates a green orb
					{
						if(board[nRow][nCol] == 3)
							return;
						board[nRow][nCol] = 3;
					}
					else if(!LightOrb.isEnabled()) //'4' indicates a yellow orb
					{
						if(board[nRow][nCol] == 4)
							return;
						board[nRow][nCol] = 4;
					}
					else if(!DarkOrb.isEnabled()) //'5' indicates a purple orb
					{
						if(board[nRow][nCol] == 5)
							return;
						board[nRow][nCol] = 5;
					}
					else if(!HeartOrb.isEnabled()) //'6' indicates a pink orb
					{
						if(board[nRow][nCol] == 6)
							return;
						board[nRow][nCol] = 6;
					}
					else
						return;
					
					repaint(nLeftXBoxEdge, nLeftYBoxEdge, nRightXBoxEdge - nLeftXBoxEdge, nRightYBoxEdge - nLeftYBoxEdge); //repaints only the selected square
				}
				System.out.println("Combos: \n"+NumOfCombos());
			}
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		/* For design purposes */
		//System.out.println("X: "+e.getX());
		//System.out.println("Y: "+e.getY());
		
	}

	@Override
	public void mousePressed(MouseEvent e) { mouseClicked(e); }

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void actionPerformed(ActionEvent e) {}
	
	
	/* Technical Methods */
	
	/* return:
	 *  - 0 is no number above, else the number above
	 */
	public int goUp(int x, int y)
	{
		if(x > 0)
				return board[x-1][y];
		return 0;
	}
	
	/* return:
	 *  - 0 is no number below, else the number below
	 */
	public int goDown(int x, int y)
	{
		if(x < 4)
			return board[x+1][y];
		return 0;
	}
	
	public int goLeft(int x, int y)
	{
		if(y > 0)
			return board[x][y-1];
		return 0;
	}
	
	public int goRight(int x, int y)
	{
		if(y < 5)
			return board[x][y+1];
		return 0;
	}
	
	
	/* Calculates the number of each color */
	public int[] NumOfColors()
	{
		int[] colors = {0, 0, 0, 0, 0, 0};
		int color = 0;
		
		for(int i = 0; i < NUMROWS; i++)
			for(int j = 0; j < NUMCOLS; j++)
			{
				color = board[i][j];
				if(color == 1)
					colors[0]++;
				else if(color == 2)
					colors[1]++;
				else if(color == 3)
					colors[2]++;
				else if(color == 4)
					colors[3]++;
				else if(color == 5)
					colors[4]++;
				else if(color == 6)
					colors[5]++;
			}
		return colors;
	}
	
	
	/* Calculates the total number of possible combos on a board 
	 * 
	 * **Need Improvement**
	 * 
	 * *Note* this will return wrong number when 30 of a single orb
	 * 
	 */
	public int NumOfCombos()
	{
		int[] colors = NumOfColors();
		return colors[0]/3 + colors[1]/3 + colors[2]/3 + colors[3]/3 + colors[4]/3 + colors[5]/3;
	}
	
	
	public void RandomizeBoard()
	{
		int min = 1, max = 6; // ranges for all kinds of orbs
		
		/* Set each index in board to a random number (indicating a color) */
		for(int i = 0; i < NUMROWS; i++)
			for(int j = 0; j < NUMCOLS; j++)
				board[i][j] = ThreadLocalRandom.current().nextInt(min, max + 1);

		/* Grab the canvas and redraw all squares on board */
		Graphics canvas = this.getGraphics();
		try { DrawSquares(canvas);
		} catch (IOException e) { e.printStackTrace(); }
		
		System.out.println(NumOfCombos());
	}
	
	
	/*public void CreateMonster()
	{
		
	}
	*/
	
	
	/* MaxScoreOfMonster - calculates the max score possible of monster versus a boss
	 * 
	 * parameter: Monster m - given monster to calculate new attack on
	 * 
	 * return: max damage the monster can output with given board
	 * 
	 * *Note* If sub different from main, sub-attribute attack starts at ceiling_function[attack/3]
	 * *Note* If sub == main, main-attribute attack starts at ceiling_function[attack/10] + attack
	 */
	public int MaxScoreOfMonster_vs_SingleEnemy_No_Skyfall(Monster m)
	{
		int[] colors = NumOfColors();
		/* colors:
		 * index 0 - red
		 * index 1 - blue
		 * index 2 - green
		 * index 3 - yellow
		 * index 4 - purple
		 * index 5 - pink
		 */
		
		int main = m.GetAttribute1(m);
		int sub = m.GetAttribute2(m);
		boolean sameAttFlag = false; // set 'true' when both attributes of monster are same, otherwise 'false'
		
		/* Formula variables */
		int mainAttack = 0;
		int subAttack = 0;
		
		/* If main and sub attributes are the same, calculate the new overall attack, and set flag */
		if(main == sub)
		{
			m.attack = m.attack + Math.round(m.attack / 10);	
			sameAttFlag = true;
		}
		
		/* Calculate main attribute attack */
		if(main != -1)
		{
			/* Main attribute is fire and there are enough colors to create at least 1 combo */
			if(main == 0 && colors[0] > 2)
				mainAttack = MaxOutput(colors[0], m.attack);
			
			/* Main attribute is water and there are enough colors to create at least 1 combo */
			else if(main == 1 && colors[1] > 2)
				mainAttack = MaxOutput(colors[1], m.attack);
			
			/* Main attribute is grass and there are enough colors to create at least 1 combo */
			else if(main == 2 && colors[2] > 2)
				mainAttack = MaxOutput(colors[2], m.attack);
			
			/* Main attribute is light and there are enough colors to create at least 1 combo */
			else if(main == 3 && colors[3] > 2)
				mainAttack = MaxOutput(colors[3], m.attack);
			
			/* Main attribute is dark and there are enough colors to create at least 1 combo */
			else if(main == 4 && colors[4] > 2)
				mainAttack = MaxOutput(colors[4], m.attack);
		}
		else
			System.out.println("Error: Attack cannot be calculated because the given monster has no main attribute.");
		
		
		/* Calculate sub attribute attack */
		if(sub != -1 && sameAttFlag == false)
		{	
			/* Sub attribute is fire and there are enough colors to create at least 1 combo */
			if(sub == 0 && colors[0] > 2)
				subAttack = MaxOutput(colors[0], Math.round(m.attack / 3));
			
			/* Sub attribute is water and there are enough colors to create at least 1 combo */
			else if(sub == 1 && colors[1] > 2)
				subAttack = MaxOutput(colors[1], Math.round(m.attack / 3));
			
			/* Sub attribute is grass and there are enough colors to create at least 1 combo */
			else if(sub == 2 && colors[2] > 2)
				subAttack = MaxOutput(colors[2], Math.round(m.attack / 3));
			
			/* Sub attribute is light and there are enough colors to create at least 1 combo */
			else if(sub == 3 && colors[3] > 2)
				subAttack = MaxOutput(colors[3], Math.round(m.attack / 3));
			
			/* Sub attribute is dark and there are enough colors to create at least 1 combo */
			else if(sub == 4 && colors[4] > 2)
				subAttack = MaxOutput(colors[4], Math.round(m.attack / 3));
		}
		
		int maxOutput = mainAttack + subAttack; // create the final damage output
		
		System.out.println("Main: "+mainAttack);
		System.out.println("Sub: "+subAttack);
		
		return maxOutput;
	}
	
	public int MaxOutput(int numOfOrbs, int attack)
	{
		int combos = NumOfCombos();
		int newOutput = NewOutput(numOfOrbs, attack);
		
		return (int) Math.round(newOutput * C(combos));
	}
	
	
	/* NewOutput - calculates the change in attack before counting combos
	 * 
	 * parameters: 
	 * 		- int numOfOrb, number of combos with same attribute as monster
	 * 		- int attack, attack of monster
	 * 
	 * return: value of new attack for monster
	 */
	public int NewOutput(int numOfOrb, int attack)
	{			
		return (int) (Math.round(attack * O((numOfOrb % 3) + 3)) + Math.round(((numOfOrb / 3) - 1) * (attack * O(3))));
	}
	
	/* Orb Multiplier */
	public double O(int n)
	{
		double d = 1.00 + ((double) n - 3.00) * (0.25);
		return d;
	}
	
	/* Combo Multiplier */
	public double C(int n)
	{
		double d = 1.00 + ((double) n - 1.00) * (0.25);
		return d;
	}
}