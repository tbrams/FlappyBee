package dk.brams.flappybee;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Flower {
    private static final float MAX_SPEED_PER_SECOND = 100F;
    private static final float COLLISION_RECTANGLE_WIDTH = 13f;
    private static final float COLLISION_RECTANGLE_HEIGHT = 447f;
    private static final float COLLISION_CIRCLE_RADIUS = 33f;

    private static final float HEIGHT_OFFSET = -400f;
    private static final float DISTANCE_BETWEEN_FLOOR_AND_CEILING = 225F;

    public static final float WIDTH = COLLISION_CIRCLE_RADIUS * 2;

    private float x = 0;
    private float y = 0;
    private boolean pointClaimed = false;

    private Circle collisionCircle;
    private Rectangle collisionRectangle;

    public Flower(){
        this.collisionRectangle = new Rectangle(x, y, COLLISION_RECTANGLE_WIDTH, COLLISION_RECTANGLE_HEIGHT);
        this.collisionCircle = new Circle(x + collisionRectangle.width / 2, y + collisionRectangle.height, COLLISION_CIRCLE_RADIUS);
    }



    public void update(float delta) {
        setPosition(x - (MAX_SPEED_PER_SECOND * delta));
    }


    public void setPosition(float x) {
        this.x = x;
        updateCollisionCircle();
        updateCollisionRectangle();
    }

    public float getX() {
        return x;
    }

    public boolean isPointClaimed() {
        return pointClaimed;
    }

    public void markPointClaimed() {
        pointClaimed = true;
    }

    private void updateCollisionCircle() {
        collisionCircle.setX(x + collisionRectangle.width / 2);
    }

    private void updateCollisionRectangle() {
        collisionRectangle.setX(x);
    }

    public void draw(SpriteBatch batch) {
    }


    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.circle(collisionCircle.x, collisionCircle.y, collisionCircle.radius);
        shapeRenderer.rect(collisionRectangle.x, collisionRectangle.y, collisionRectangle.width, collisionRectangle.height);
    }
}
