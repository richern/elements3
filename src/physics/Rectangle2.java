package physics;

import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class Rectangle2 extends Rectangle {
	
	private Vector2f origin, topLeft, topRight, bottomLeft, bottomRight;
	private Line topEdge, rightEdge, bottomEdge, leftEdge;
	
	public Rectangle2(Point position, float width, float height) {
		super(position.getX() - width / 2, position.getY() - height / 2, width, height);
		x = position.getX();
		y = position.getY();
		origin = new Vector2f(x,y);
		
		topLeft = new Vector2f(x - width / 2, y - height / 2);
		topRight = new Vector2f(x + width / 2, y - height / 2);
		bottomLeft = new Vector2f(x - width / 2, y + height / 2);
		bottomRight = new Vector2f(x + width / 2, y + height / 2);
		
		/*System.out.println((int)(topLeft.getX()/32) + ":" + (int)((topLeft.getY() + 1)/32) + " " + 
				(int)(bottomLeft.getX()/32) + ":" + (int)((bottomLeft.getY() - 1)/32));*/
		
		// add pixel gap to avoid edge cases
		topEdge = new Line(new Vector2f(topLeft.getX() + 1, topLeft.getY()), 
				new Vector2f(topRight.getX() - 1, topRight.getY()));
		rightEdge = new Line(new Vector2f(topRight.getX(), topRight.getY() + 1), 
				new Vector2f(bottomRight.getX(), bottomRight.getY() - 1));
		bottomEdge = new Line(new Vector2f(bottomLeft.getX() + 1, bottomLeft.getY()), 
				new Vector2f(bottomRight.getX() - 1, bottomRight.getY()));
		leftEdge = new Line(new Vector2f(topLeft.getX(), topLeft.getY() + 1),
				new Vector2f(bottomLeft.getX(), bottomLeft.getY() - 1));
	}
	
	public Rectangle toRectangle() {
		return new Rectangle(topLeft.getX(), topLeft.getY(), getWidth(), getHeight());
	}
	
	public Line getTopEdge() {
		return topEdge;
	}
	
	public Line getRightEdge() {
		return rightEdge;
	}
	
	public Line getBottomEdge() {
		return bottomEdge;
	}
	
	public Line getLeftEdge() {
		return leftEdge;
	}
	
}
