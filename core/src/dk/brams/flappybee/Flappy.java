package dk.brams.flappybee;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;


public class Flappy {
    public static final float COLLISION_RADIUS = 24f;
    private static final float DIVE_ACCEL = 0.30F;
    private static final float FLY_ACCEL = 5F;
    public static final int TILE_WIDTH=118;
    public static final int TILE_HEIGHT=118;
    public static final float FRAME_DURATION = .25F;


    private Animation animation;
    private float animationTimer = 0;
    private static Circle collisionCircle;

    private float x = 0;
    private float y = 0;

    private float ySpeed = 0;


    public Flappy(Texture texture){
        TextureRegion[][] textures = new TextureRegion(texture).split(TILE_WIDTH, TILE_HEIGHT);
        animation = new Animation(FRAME_DURATION, textures[0][0],textures[0][1]);
        animation.setPlayMode(Animation.PlayMode.LOOP);

        collisionCircle = new Circle(x, y, COLLISION_RADIUS);
    }

    public static Circle getCollisionCircle() {return collisionCircle;}
    public float getY() { return y;}
    public float getX() {return x;}

    public void update(float delta) {
        animationTimer += delta;
        ySpeed -= DIVE_ACCEL;
        setPosition(x, y + ySpeed);
    }

    public void flyUp() {
        ySpeed = FLY_ACCEL;
        setPosition(x, y + ySpeed);
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
        updateCollisionCircle();
    }

    private void updateCollisionCircle() {
        collisionCircle.setX(x);
        collisionCircle.setY(y);
    }

    public void draw(SpriteBatch sb) {
        TextureRegion flappySlice = animation.getKeyFrame(animationTimer);
        float textureX = collisionCircle.x - flappySlice.getRegionWidth() / 2;
        float textureY = collisionCircle.y - flappySlice.getRegionHeight() / 2;
        sb.draw(flappySlice, textureX, textureY);
    }

    public void drawDebug(ShapeRenderer sr){
        sr.circle(x, y, COLLISION_RADIUS);
    }

}
