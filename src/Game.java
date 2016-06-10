import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

public class Game extends JPanel {
	
	private Dimension gameField = new Dimension(400,300);
	private Dimension sideBar = new Dimension(100, 300);
	private boolean isRunning = false;
	private boolean isPaused = false;
	private boolean won = false;
	private boolean lost = false;
	
	
	private int ballCount;
	
	private int score;
	private int scoreCount;
	
	private Platform[][] platforms;
	private Player player;
	private Ball ball;
	
	public Game(Frame container, int platformsOnX, int platformsOnY) {
		container.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e){
				if (won || lost) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) System.exit(0);
				} else if (!isRunning || isPaused) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) start();
				} else {
					if (e.getKeyCode() == KeyEvent.VK_RIGHT) player.moveOnXAxis(50);
					if (e.getKeyCode() == KeyEvent.VK_LEFT) player.moveOnXAxis(-50);
				}
				
				
			}
			
		});
		platforms = new Platform[platformsOnX][platformsOnY];
		for (int x = 0; x != platforms.length; x++) {
			for (int y = 0; y != platforms[0].length; y++) {
				int pWidth = gameField.width/platformsOnX;
				int pHeight = (gameField.height/4)/platformsOnY;
				platforms[x][y] = new Platform(100, x*pWidth, y*pHeight, pWidth, pHeight);
			}
		}
		player = new Player(this,(int)((gameField.getWidth()-Player.standardPlayerWidth)/2), gameField.height-Player.standardPlayerHeight, Player.standardPlayerWidth, Player.standardPlayerHeight); 
		ball = new Ball(this, gameField.width/2, gameField.height/2, Ball.standardBallRadius);
		ballCount = 3;
		score = 0;
		scoreCount = 0;
	}
	
	public void addScore(int score) {
		this.scoreCount += score;
	}
	
	public void loseBall() {
		pause();
		ballCount-=1;
		if (ballCount <= 0) lost=true; 
		ball.setVector(10, 10);
		ball.setPosition(gameField.width/2, gameField.height/2);
		player.setX((int)((gameField.getWidth()-Player.standardPlayerWidth)/2));
		player.setY(gameField.height-Player.standardPlayerHeight);
		repaint();
		
		
	}
	
	public void playerWon() {
		won = true;
	}
	
	
	public void start() {
		isPaused = false;
		if (!isRunning) gameThread.start();
	}
	
	public void pause() {
		isPaused = true;
		

	}
	public void stop() {
		isRunning = false;
	}
	
	public Dimension getGameDimension(){
		return gameField;
	}
	public void setPlayer(Player player){
		this.player = player;
	}
	
	public Player getPlayer(){
		return this.player;
	}
	
	public Platform[][] getPlatforms() {
		return this.platforms;
	}
	
	public void setSize(Dimension size) {
		super.setSize(size);
		if (!isRunning) {
			gameField = new Dimension((size.width*3)/4, size.height);
			sideBar = new Dimension(size.width/4, size.height);
			for (int x = 0; x != platforms.length; x++) {
				for (int y = 0; y != platforms[0].length; y++) {
					int pWidth = gameField.width/platforms.length;
					int pHeight = (gameField.height/4)/platforms[0].length;
					platforms[x][y] = new Platform(100, x*pWidth, y*pHeight, pWidth, pHeight);
				}
			}
			ball.setPosition(gameField.width/2, gameField.height/2);
			player.setX((int)((gameField.getWidth()-Player.standardPlayerWidth)/2));
			player.setY(gameField.height-Player.standardPlayerHeight);
		}
	}
	
	private Thread gameThread = new Thread(new Runnable() {
		public void run() {
			isRunning = true;
			ball.setVector(10, 10);
			while (isRunning) {
				if (!isPaused) {
					ball.tick();
					
					score = ((score+3)>=scoreCount)? scoreCount:score+3;
					
				
					
					repaint();
					try {
						Thread.sleep(30);
					} catch (Exception e) {}
				}
			}
		}
	});
	

	
	public void paint(Graphics g) {
		super.paint(g);
		
		if (!isRunning) {
			setSize(getSize());
		}
		
		g.translate((getWidth()-(gameField.width+sideBar.width))/2, (getHeight()-(gameField.height))/2 );
		
		
		g.setColor(new Color(255,255,255,100));
		g.fillRect(0, 0, gameField.width, gameField.height);
		
		ball.render(g);
		player.render(g);
		
		for (Platform[] pls : platforms ) {
			for (Platform p : pls) {
				p.render(g);
			}
		}
		
		g.setColor(new Color(0,0,255));
		g.fillRect(gameField.width, 0, sideBar.width, sideBar.height);
		g.setColor(new Color(255,255,255));
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.drawString("Wynik:	"+score, gameField.width+20, 40);
		g.drawString("Pi³ka:	"+ballCount, gameField.width+20, 75);
		
		
		g.setColor(new Color(0,0,0));
		g.drawRect(0, 0, gameField.width, gameField.height);
		
		
		
		if (won) {
			g.drawString("Wygra³eœ!", 100, 100);
			stop();
		}
		if (lost) {
			g.drawString("Przegra³eœ!", 100, 100);
			stop();
		}
		
	}

}
