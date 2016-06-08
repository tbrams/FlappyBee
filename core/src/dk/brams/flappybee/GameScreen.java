package dk.brams.flappybee;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen extends ScreenAdapter {
    public static final float WORLD_WIDTH=480;
    public static final float WORLD_HEIGHT=640;

    private ShapeRenderer shapeRenderer;
    private FitViewport viewPort;
    private Camera camera;
    private SpriteBatch sb;
    private Flappy flappy;
    private Flower flower;


    @Override
    public void resize(int width, int height) {
        viewPort.update(width, height);
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        camera.update();
        viewPort = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        shapeRenderer = new ShapeRenderer();
        sb = new SpriteBatch();

        flappy = new Flappy();
        flappy.setPosition(WORLD_WIDTH / 4, WORLD_HEIGHT / 2);

        flower = new Flower();

    }

    @Override
    public void render(float delta) {
        clearScreen();
        sb.setProjectionMatrix(camera.projection);
        sb.setTransformMatrix(camera.view);
        sb.begin();
        sb.end();

        shapeRenderer.setProjectionMatrix(camera.projection);
        shapeRenderer.setTransformMatrix(camera.view);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        flappy.drawDebug(shapeRenderer);
        flower.drawDebug(shapeRenderer);
        shapeRenderer.end();

        update(delta);

    }

    private void update(float delta) {
        flappy.update(delta);
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) flappy.flyUp();
        blockFlappeeLeavingTheWorld();

    }

    private void blockFlappeeLeavingTheWorld() {
        flappy.setPosition(flappy.getX(), MathUtils.clamp(flappy.getY(), 0, WORLD_HEIGHT));
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r,Color.BLACK.g,Color.BLACK.b,Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
