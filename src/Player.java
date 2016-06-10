import java.awt.*;

public class Player {
	public static int standardPlayerWidth = 100;
	public static int standardPlayerHeight = 20;
	private Rectangle hitBox; //= new Rectangle(0,0,100,30);
	private Game instance;
	public Player(Game inst, int x, int y, int width, int height) {
		instance = inst;
		hitBox = new Rectangle(x, y, width, height);
	
	}
	
	public void moveOnXAxis(int speed) {
		hitBox.x = hitBox.x += speed;
		if (hitBox.x < 0) hitBox.x = 0;
		if (hitBox.x > instance.getGameDimension().width-instance.getPlayer().hitBox.width) hitBox.x = instance.getGameDimension().width-instance.getPlayer().hitBox.width;
		
	}
	
	public boolean collidesWith(Rectangle object) {
		return hitBox.intersects(object);
	}
	
	public void setX(int x) {
		hitBox.x = x;
	}
	
	public void setY(int y) {
		hitBox.y = y;
	}
	
	public int getX() {
		return hitBox.x;
	}
	
	public int getY() {
		return hitBox.y;
	}
	
	public int getWidth() {
		return this.hitBox.width;
	}
	
	public int getHeight() {
		return this.hitBox.height;
	}
	
	
	public void render(Graphics g) {
		g.setColor(new Color(0, 0, 0));
		g.fillRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
	}

}
//
