import java.awt.*;

public class Ball {
	public static int standardBallRadius = 10;
	private Game instance;
	private Dimension vector = new Dimension(0,0); 
	private Point pos = new Point(0,0);
	private int radius;
	
	public Ball(Game inst, int x, int y, int radius) {
		instance = inst;
		pos = new Point(x,y);
		this.radius = radius;
	}
	
	public void setVector(int XMovement, int YMovement) {
		vector = new Dimension(XMovement, YMovement);
	}
	
	public Point getPosition() {
		return pos;
	}
	public void setPosition(int x, int y) {
		pos = new Point(x, y);

	}
	
	public void tick() {
		if (pos.x-radius <= 0 && vector.width<0) vector.width = -vector.width;
		if (pos.x+radius >= instance.getGameDimension().width && vector.width>0) vector.width = -vector.width;
		if (pos.y-radius <= 0 && vector.height<0) vector.height = -vector.height;
		if (pos.y+radius >= instance.getGameDimension().height && vector.height>0) instance.loseBall();
		
		if (instance.getPlayer() != null) {
			if (instance.getPlayer().collidesWith(new Rectangle(pos.x-radius+vector.width, pos.y-radius+vector.height, radius*2, radius*2))) {
				
				
				Player p = instance.getPlayer();
				if (pos.x >=  p.getX() && pos.x <= p.getX()+p.getWidth()  || (pos.x+radius*2) >=  p.getX() && (pos.x+radius*2) <= p.getX()+p.getWidth())  {
					vector.height = -vector.height;
				} else if (pos.y >= p.getY() && pos.y <= p.getY()+p.getHeight() || (pos.y+radius*2) >= p.getY() && (pos.y+radius*2) <= p.getY()+p.getHeight()) {
					vector.width = -vector.width;
				}
			}
		}
		
		pos.move(pos.x+vector.width, pos.y+vector.height);
		
		for (Platform[] pls : instance.getPlatforms()) {
			for (Platform p : pls) {
				if (p.collidesWith(new Rectangle(pos.x-radius, pos.y-radius, radius*2, radius*2))) {
					p.destroy();
					//vector.height = -vector.height;
					instance.addScore(p.getValue());
					
					if (pos.y >= p.getY() && pos.y <= p.getY()+p.getHeight() || (pos.y+radius*2) >= p.getY() && (pos.y+radius*2) <= p.getY()+p.getHeight()) {
						vector.width = -vector.width;
					} else if (pos.x >=  p.getX() && pos.x <= p.getX()+p.getWidth()  || (pos.x+radius*2) >=  p.getX() && (pos.x+radius*2) <= p.getX()+p.getWidth())  {
						vector.height = -vector.height;
					} 	
					
					
					boolean won = true;
					for (Platform[] pls2 : instance.getPlatforms() ) {
						for (Platform p2 : pls2) {
							if (!p2.isDestroyed()) won = false;
						}
					}
					if (won) instance.playerWon();
				}
			}
		}
		
	}
	
	public void render(Graphics g) {
		
		g.setColor(new Color(0,0,0));
		g.fillOval(pos.x-radius, pos.y-radius, radius*2, radius*2);
	
	}

}

//