package snakeGame;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class GamePanel extends JPanel implements KeyListener, ActionListener{
		
         //two arrays to define the position of snake(x position , y position)
		private int[] snakexlength = new int[750];
		private int[] snakeylength = new int[750];
		private int defaultLengthofSnake = 3;
		
		//detecting on which side snake is moving
		private boolean left = false;
		private boolean right = false;
		private boolean up = false;
		private boolean down = false;
		
		//variables for snake face
		private ImageIcon rightmouth;
		private ImageIcon leftmouth;
		private ImageIcon upmouth;
		private ImageIcon downmouth;
		
		//variable for snake body image
		private ImageIcon snakeBody;
		
		//for movement of snake we use in-built timer class 
		//that will manage the speed of snake inside the panel
		private Timer timer;
		private int delay = 100;
		
		//variable for enemy
		private int[] enemyxpos = {25,50,75,100,125,150,175,200,225,250,275,
		                            300,325,350,375};
		private int[] enemyypos = {100,125,150,175,200,225,250,275,
                                    300,325,350,375};
		private ImageIcon enemyImage;
		
		//random position of enemy
		private Random random = new Random();
		private int xpos = random.nextInt(16);//no. of enemyxpos is 16
		private int ypos = random.nextInt(13);// no. of enemyypos is 13
		
		private int moves = 0;
		private ImageIcon titleImage; //variable to set title image
		private ImageIcon grassImgTop; 
		private ImageIcon grassImgBottom;
		
		private int score =0;
		
		public GamePanel() {
			  
			addKeyListener(this); //we need to pass the object of Class implementing KeyListner 
			                      //i.e. GamePanel in this case
			setFocusable(true);
			setFocusTraversalKeysEnabled(false);
			timer = new Timer(delay, this); //speed of snake
			timer.start(); 
		}
		//1.paint is in-built method used to display the panel
		public void paint(Graphics graphics) {
			
			if(moves==0) {
				//to check if the game has yet started 
				//and set the x , y coordinates for snake starting position
				snakexlength[2] = 50;
				snakexlength[1] = 75;
				snakexlength[0] = 100;
				
				snakeylength[2] = 100;
				snakeylength[1] = 100;
				snakeylength[0] = 100;
			}
			
			//draw title image border
			graphics.setColor(Color.BLACK);
			graphics.drawRect(25,9,449,52);
			
			//draw the title image
			titleImage = new ImageIcon("snake_title.PNG");
			titleImage.paintIcon(this, graphics, 26, 10);
			
			//draw border for the game area
			graphics.setColor(Color.BLACK);
			graphics.drawRect(25,80,450,360);
			
			//draw background for the game area
			graphics.setColor(Color.lightGray);
			graphics.fillRect(26, 81, 449, 359);
			
			//draw scores
			graphics.setColor(Color.BLACK);
			graphics.setFont(new Font("arial",Font.BOLD, 14));
			graphics.drawString("Scores: "+score, 390, 30);
			
			//draw length of snake
			graphics.setColor(Color.BLACK);
			graphics.setFont(new Font("arial",Font.BOLD, 14));
			graphics.drawString("Length: "+defaultLengthofSnake, 390, 50);
			
			
			grassImgTop = new ImageIcon("grass.PNG");
			grassImgTop.paintIcon(this, graphics, 26, 81);
			
			grassImgBottom = new ImageIcon("grass.PNG");
			grassImgBottom.paintIcon(this, graphics, 26, 431);
			
			//draw snake in the panel
			rightmouth = new ImageIcon("rightmouth.png");
			rightmouth.paintIcon(this, graphics, snakexlength[0], snakeylength[0]);
			//index zero of array will contain mouth image
			//and rest array will contain the body image
			for(int a=0;a<defaultLengthofSnake;a++) {
				
				if(a==0 && right) {
					rightmouth = new ImageIcon("rightmouth.png");
					rightmouth.paintIcon(this, graphics, snakexlength[a], snakeylength[a]);
				}
				if(a==0 && left) {
					leftmouth = new ImageIcon("leftmouth.png");
					leftmouth.paintIcon(this, graphics, snakexlength[a], snakeylength[a]);
				}
				if(a==0 && up) {
					upmouth = new ImageIcon("upmouth.png");
					upmouth.paintIcon(this, graphics, snakexlength[a], snakeylength[a]);
				}
				if(a==0 && down) {
					downmouth = new ImageIcon("downmouth.png");
					downmouth.paintIcon(this, graphics, snakexlength[a], snakeylength[a]);
				}
				
				if(a!=0) {//need to print the body of snake
					snakeBody = new ImageIcon("body.png");
					snakeBody.paintIcon(this, graphics, snakexlength[a], snakeylength[a]);
				}
			}
			//draw enemy
			enemyImage = new ImageIcon("enemy.png");
			
			
			if((enemyxpos[xpos] == snakexlength[0] && enemyypos[ypos] == snakeylength[0])) {
				score++;
				defaultLengthofSnake++;
				repaint();
				xpos = random.nextInt(16);
				ypos = random.nextInt(13);
				}
			
			enemyImage.paintIcon(this, graphics,enemyxpos[xpos], enemyypos[ypos]);
			
			for(int b =1; b < defaultLengthofSnake;b++) {
				
				if((snakexlength[b] == snakexlength[0] && snakeylength[b] == snakeylength[0])
					||snakeylength[0] <= 85 || snakeylength[0] >= 405 ) {
					
					right = false;
					left = false;
					up = false;
					down = false;
					
					graphics.setColor(Color.WHITE);
					graphics.setFont(new Font("arial",Font.BOLD, 50));
					graphics.drawString("Game Over!", 110, 250);
					
					graphics.setFont(new Font("arial",Font.BOLD, 16));
					graphics.drawString("Press SPACE to Restart", 165, 280);
					
				}
			}
			graphics.dispose();
		}
		
		//KeyListener is the interface used to implement the arrow keys
		//of keyboard with the help of which the snake will move
		@Override
		public void actionPerformed(ActionEvent e) {
			//automatically called when timer starts
			if(right) {
				for(int r=defaultLengthofSnake-1;r>=0;r--) {
					snakeylength[r+1] = snakeylength[r];
				}
				for(int r=defaultLengthofSnake-1;r>=0;r--) {
					
					if(r==0) {
					snakexlength[r] = snakexlength[r]+25;
					}else {
						snakexlength[r] = snakexlength[r-1];
					}
					if(snakexlength[r]>450) {//if snake goes into right it will come out from left
						snakexlength[r] = 25;
						
					}
				}
				repaint();//automatically called paint(Graphics g)
			}
			if(left) {
				for(int r=defaultLengthofSnake-1;r>=0;r--) {
					snakeylength[r+1] = snakeylength[r];
				}
				for(int r=defaultLengthofSnake-1;r>=0;r--) {
					
					if(r==0) {
					snakexlength[r] = snakexlength[r]-25;
					}else {
						snakexlength[r] = snakexlength[r-1];
					}
					if(snakexlength[r]<25) {//if snake goes into right it will come out from left
						snakexlength[r] = 450;
						
					}
				}
				repaint();//automatically called paint(Graphics g)
				
			}
			if(up) {
				for(int r=defaultLengthofSnake-1;r>=0;r--) {
					snakexlength[r+1] = snakexlength[r];
				}
				for(int r=defaultLengthofSnake-1;r>=0;r--) {
					
					if(r==0) {
					snakeylength[r] = snakeylength[r]-25;
					}else {
						snakeylength[r] = snakeylength[r-1];
					}
//					if(snakeylength[r]<75) {//if snake goes into right it will come out from left
//						snakeylength[r] = 420;
//						
//					}
				}
				repaint();//automatically called paint(Graphics g)
			}
			if(down) {
				for(int r=defaultLengthofSnake-1;r>=0;r--) {
					snakexlength[r+1] = snakexlength[r];
				}
				for(int r=defaultLengthofSnake-1;r>=0;r--) {
					
					if(r==0) {
					snakeylength[r] = snakeylength[r]+25;
					}else {
						snakeylength[r] = snakeylength[r-1];
					}
//					if(snakeylength[r]>423) {//if snake goes into right it will come out from left
//						snakeylength[r] = 80;
//						
//					}
				}
				repaint();//automatically called paint(Graphics g)
			}
		}
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void keyPressed(KeyEvent e) {
			//get which key is pressed from keyboard and if its the right key
			//make the right variable true and others false
			
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) { 
				moves++;
				
				right = true;
				if(!left) {//while moving in right, pressed left...it should not move to left
					       // but continue moving in right
					right =true;
				}else {
					right = false;
					left = true;
				}
				
				up = false;
				down = false;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_LEFT) { 
				moves++;
				
				left = true;
				if(!right) {
					left =true;
				}else {
					left = false;
					right = true;
				}
				
				up = false;
				down = false;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_UP) { 
				moves++;
				
				up = true;
				if(!down) {
					up = true;
				}else {
					up = false;
					down = true;
				}
				
				left = false;
				right = false;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_DOWN) { 
				moves++;
				
				down = true;
				if(!up) {
					down = true;
				}else {
					down = false;
					up = true;
				}
				
				left = false;
				right = false;
			}
			//Press space bar to restart the game
			if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				 moves = 0;
				 score = 0;
				 defaultLengthofSnake = 3;
				 repaint();
				 
			}
		}
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	

}
