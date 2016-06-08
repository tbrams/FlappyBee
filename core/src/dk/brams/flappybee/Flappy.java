package dk.brams.flappybee;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;


public class Flappy {
    public static final float COLLISION_RADIUS = 24f;
    private static final float DIVE_ACCEL = 0.30F;
    private static final float FLY_ACCEL = 5F;
    private float ySpeed = 0;

    private static Circle collisionCircle;

    private float x=0;

    public static Circle getCollisionCircle() {
        return collisionCircle;
    }

    public float getY() {

        return y;
    }

    public float getX() {
        return x;
    }

    private float y = 0;
    public Flappy(){
        collisionCircle = new Circle(x,y,COLLISION_RADIUS);
    }

    public void update(float delta) {
   //     animationTimer += delta;
        ySpeed -= DIVE_ACCEL;
        setPosition(x, y + ySpeed);
    }

    public void flyUp() {
        ySpeed = FLY_ACCEL;
        setPosition(x, y + ySpeed);
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y=y;
        updateCollisionCircle();
    }

    private void updateCollisionCircle() {
        collisionCircle.setX(x);
        collisionCircle.setY(y);
    }

    public void drawDebug(ShapeRenderer sr){
        sr.circle(x, y, COLLISION_RADIUS);
    }

}
