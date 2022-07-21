package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel 
             implements KeyListener, ActionListener{
	static final long serialVersionUID = 1L;
	private boolean play = false;
	private int score = 0;
	private int totalBricks = 48;
	private Timer timer;
	private int delay = 8;
	private int playerX = 310;
	private int ballPosX = 310;
	private int ballPosY = 340;
	private int ballXdir = -1;
	private int ballYdir = -2;
	private MapGenerator Map;
	int[][] map;
	
	
	
	//Constructor
	public Gameplay() {
		System.out.println("Gameplay init");
		Map = new MapGenerator(4,12);
		map = Map.map;
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
		
	}
	
	@Override
	public void paint(Graphics g) {
		
		//background
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		//Draw map
		Map.draw((Graphics2D) g);
		
		//Borders
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, 3, 590); //left edge
		//g.fillRect(0, 0, 690, 3); //upper edge
		g.fillRect(682, 0, 3, 590); //right edge
		
		//the scores
		g.setColor(Color.RED);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString("Score :"+score+"   "+totalBricks,1, 30);
		
		// the paddle
		g.setColor(Color.BLUE);
		g.fillRect(playerX-50, 550, 100, 10);
		
		// the ball
		g.setColor(Color.RED);
		g.fillOval(ballPosX-10, ballPosY-10, 20, 20);
		
		g.dispose();
		
		// set play is true
		play = true;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		//System.out.println("Key Pressed");
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			moveRight();
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			moveLeft();
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {     
			newGame();
		}
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(play)
		{
			//System.out.println("Actionlistener called");
			end();
			// collision with paddle
			if(new Rectangle(ballPosX+10, ballPosY, 20, 20).intersects(new Rectangle(playerX-20, 550, 40, 10))) { // at middle
				ballYdir = -ballYdir;
				score++;
			}
			else if(new Rectangle(ballPosX+10, ballPosY, 20, 20).intersects(new Rectangle(playerX-50, 550, 30, 10))) { // at left side
				ballYdir = -ballYdir;
				ballXdir = -2;
			}
			else if(new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX+20, 550, 30, 10))) { // at right side
				ballYdir = -ballYdir;
				ballXdir = 2;
			}
			
			// check map collision with the ball		
			A: for(int i = 0; i<map.length; i++) {
				for(int j =0; j<map[0].length; j++) {				
					if(Map.map[i][j] > 0) {
						int brickX = j * Map.brickWidth + 80;
						int brickY = i * Map.brickHeight + 50;
						int brickWidth = Map.brickWidth;
						int brickHeight = Map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);					
						Rectangle ballRect = new Rectangle(ballPosX-10, ballPosY-10, 20, 20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect)) {					
							Map.setBrickValue(0, i, j);
							score+=5;	
							totalBricks--;
							
							// when ball hit right or left of brick
							if(ballPosX + 6 <= brickRect.x || ballPosX - 6 >= brickRect.x + brickRect.width) {
								ballXdir = -ballXdir;
							}
							// when ball hits top or bottom of brick
							else{
								ballYdir = -ballYdir;				
							}
							
							break A;
						}
					}
				}
			}
			
			ballPosX += ballXdir;
			ballPosY += ballYdir;
			
			// collision with edge
			if(ballPosX < 13)
			{
				ballXdir = -ballXdir;
			}
			if(ballPosY < 13)
			{
				ballYdir = -ballYdir;
			}
			if(ballPosX > 672)
			{
				ballXdir = -ballXdir;
			}
			repaint();
			
		}
	}
	// move paddle right 
	public void moveRight(){
		if (playerX >= 630) {
			playerX = 630;
		}else {
			playerX+=20;
		}
	}
	// move paddle left
	public void moveLeft() {
		if (playerX <=54) {
			playerX = 54;
		}else {
			playerX-=20;
		}
	}
	// check if game over / won
	public void end() {
		if (totalBricks == 0) {
			play = false;
			timer.stop();
			System.out.println("YOU WON \nPress ENTEN for NEW Game");
		}
		if (ballPosY > 600){
			play = false;
			timer.stop();
			System.out.println("Game Over \nPress ENTEN for NEW Game");
		}
	}
	
	// Create a new game
	private void newGame() {
		score = 0;
		totalBricks = 48;
		playerX = 310;
		ballPosX = 310;
		ballPosY = 340;
		ballXdir = -1;
		ballYdir = -2;
		Map = new MapGenerator(4,12);
		map = Map.map;
		timer.start();
		repaint();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
}
