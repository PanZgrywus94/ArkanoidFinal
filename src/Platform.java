import java.awt.*;


public class Platform {
	private boolean isDestroyed = false;
	private Rectangle hitBox;
	private int value = 0;
	
	public Platform(int value, int x, int y, int width, int height) {
		this.value = value;
		hitBox = new Rectangle(x,y,width,height);
		
	}
	
	public boolean collidesWith(Rectangle object) {
		return (isDestroyed)? false:hitBox.intersects(object);
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
	
	public int getValue() {
		return this.value;
	}
	
	public boolean isDestroyed() {
		return isDestroyed;
	}
	
	public void destroy() {
		isDestroyed = true;
	}
	
	public void render(Graphics g) {
		if (!isDestroyed) {	
			g.setColor(new Color(255,0,0));
			g.fillRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
			g.setColor(new Color(0,0,0));
			g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
		}
	}

}
