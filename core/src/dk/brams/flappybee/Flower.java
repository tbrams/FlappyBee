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

    public static final float WIDTH = COLLISION_CIRCLE_RADIUS * 2;


    private static final float HEIGHT_OFFSET = -400f;
    private static final float DISTANCE_BETWEEN_FLOOR_AND_CEILING = 225F;

    private float x = 0;
    private float y = 0;
    private boolean pointClaimed = false;

    private final Circle topCollisionCircle;
    private final Circle botCollisionCircle;
    private final Rectangle topCollisionRectangle;
    private final Rectangle botCollisionRectangle;

    private final Texture topTexture;
    private final Texture botTexture;

    public Flower(Texture bottom, Texture top){
        topTexture=top;
        botTexture=bottom;
        y = MathUtils.random(HEIGHT_OFFSET);
        botCollisionRectangle = new Rectangle(x, y, COLLISION_RECTANGLE_WIDTH, COLLISION_RECTANGLE_HEIGHT);
        botCollisionCircle = new Circle(x + botCollisionRectangle.width / 2, y + botCollisionRectangle.height, COLLISION_CIRCLE_RADIUS);
        topCollisionRectangle = new Rectangle(x, botCollisionCircle.y+DISTANCE_BETWEEN_FLOOR_AND_CEILING, COLLISION_RECTANGLE_WIDTH,COLLISION_RECTANGLE_HEIGHT);
        topCollisionCircle = new Circle(x+topCollisionRectangle.width/2,topCollisionRectangle.y,COLLISION_CIRCLE_RADIUS);
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
        botCollisionCircle.setX(x + botCollisionRectangle.width / 2);
        topCollisionCircle.setX(x + topCollisionRectangle.width / 2);
    }

    private void updateCollisionRectangle() {
        botCollisionRectangle.setX(x);
        topCollisionRectangle.setX(x);
    }

    public boolean isFlappyColliding(Flappy flappee) {
        Circle flappyCollisionCircle = Flappy.getCollisionCircle();
        return
                Intersector.overlaps(flappyCollisionCircle, topCollisionCircle) ||
                        Intersector.overlaps(flappyCollisionCircle, botCollisionCircle) ||
                        Intersector.overlaps(flappyCollisionCircle, topCollisionRectangle) ||
                        Intersector.overlaps(flappyCollisionCircle, botCollisionRectangle);
    }

    public void draw(SpriteBatch batch) {
        drawBottomFlower(batch);
        drawTopFlower(batch);
    }

    private void drawBottomFlower(SpriteBatch batch) {
        float textureX = botCollisionCircle.x - botTexture.getWidth() / 2;
        float textureY = botCollisionRectangle.getY() + COLLISION_CIRCLE_RADIUS;
        batch.draw(botTexture, textureX, textureY);
    }
    private void drawTopFlower(SpriteBatch batch) {
        float textureX = topCollisionCircle.x-topTexture.getWidth()/2;
        float textureY = topCollisionRectangle.getY()-COLLISION_CIRCLE_RADIUS;
        batch.draw(topTexture, textureX, textureY);
    }


    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.circle(botCollisionCircle.x, botCollisionCircle.y, botCollisionCircle.radius);
        shapeRenderer.rect(botCollisionRectangle.x, botCollisionRectangle.y, botCollisionRectangle.width, botCollisionRectangle.height);
        shapeRenderer.circle(topCollisionCircle.x, topCollisionCircle.y, topCollisionCircle.radius);
        shapeRenderer.rect(topCollisionRectangle.x, topCollisionRectangle.y, topCollisionRectangle.width, topCollisionRectangle.height);
    }
}
