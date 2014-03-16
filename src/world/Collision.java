package world;

public class Collision {

	Float leftCollision, rightCollision, topCollision, bottomCollision,
		  horizontalCollision, verticalCollision;
	boolean isLeftCollision, isRightCollision, isBottomCollision, isTopCollision,
		  isHorizontalCollision, isVerticalCollision, isNoCollision;
	
	
	public Collision(Float leftCollision, Float rightCollision, Float topCollision, Float bottomCollision) {
		this.leftCollision = leftCollision;
		this.rightCollision = rightCollision;
		this.topCollision = topCollision;
		this.bottomCollision = bottomCollision;
		horizontalCollision = leftCollision == null ? ( rightCollision == null ? null : rightCollision) : leftCollision;
		verticalCollision = bottomCollision == null ? ( topCollision == null ? null : topCollision) : bottomCollision;
		isLeftCollision = leftCollision != null;
		isRightCollision = rightCollision != null;
		isBottomCollision = bottomCollision != null;
		isTopCollision = topCollision != null;
		isHorizontalCollision = horizontalCollision != null;
		isVerticalCollision = verticalCollision != null;
		isNoCollision = !isHorizontalCollision && !isVerticalCollision; 
	}
	
}
