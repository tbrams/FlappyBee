package dk.brams.flappybee;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen extends ScreenAdapter {
    public static final float WORLD_WIDTH=480;
    public static final float WORLD_HEIGHT=640;
    private static final float GAP_BETWEEN_FLOWERS = 200F;


    private ShapeRenderer shapeRenderer;
    private FitViewport viewPort;
    private Camera camera;
    private SpriteBatch sb;
    private Flappy flappy;

    private int score=0;
    private Array<Flower> flowers = new Array<Flower>();

    private BitmapFont bitmapFont;
    private GlyphLayout glyphLayout;

    private Texture bg;
    private Texture flowerTop, flowerBottom;
    private Texture flappyTexture;

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewPort.update(width, height);
    }

    @Override
    public void show() {
        super.show();
        bitmapFont = new BitmapFont();
        glyphLayout = new GlyphLayout();

        camera = new OrthographicCamera();
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        camera.update();
        viewPort = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        shapeRenderer = new ShapeRenderer();
        sb = new SpriteBatch();


        bg = new Texture(Gdx.files.internal("bg.png"));
        flowerBottom = new Texture(Gdx.files.internal("flowerBottom.png"));
        flowerTop = new Texture(Gdx.files.internal("flowerTop.png"));
        flappyTexture = new Texture(Gdx.files.internal("bee.png"));
        flappy = new Flappy(flappyTexture);
        flappy.setPosition(WORLD_WIDTH / 4, WORLD_HEIGHT / 2);


    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        clearScreen();
        draw();

        // drawDebug();
    }

    private void drawDebug() {
        shapeRenderer.setProjectionMatrix(camera.projection);
        shapeRenderer.setTransformMatrix(camera.view);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        flappy.drawDebug(shapeRenderer);
        for (Flower flower :flowers) {
            flower.drawDebug(shapeRenderer);
        }
        shapeRenderer.end();
    }

    private void update(float delta) {
        updateFlappy(delta);
        updateFlowers(delta);
        updateScore();
        if (checkForCollision())
            restart();
    }

    private void draw() {
        sb.setProjectionMatrix(camera.projection);
        sb.setTransformMatrix(camera.view);
        sb.begin();
        sb.draw(bg, 0,0);
        drawFlowers();
        flappy.draw(sb);
        drawScore();
        sb.end();
    }


    private void updateFlappy(float delta) {
        flappy.update(delta);
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) flappy.flyUp();
        blockFlappeeLeavingTheWorld();
    }



    private void updateFlowers(float delta) {
        for (Flower flower :flowers) {
            flower.update(delta);
        }
        checkIfNewFlowerIsNeeded();
        removeFlowersIfPassed();
        
        if (checkForCollision()){
            restart();
        }
    }

    private void updateScore() {
        if (flowers.size>0) {
            Flower flower = flowers.first();
            if (flower.getX() < flappy.getX() && !flower.isPointClaimed()) {
                flower.markPointClaimed();
                score++;
            }
        }
    }


    private void drawScore() {
        String scoreAsString = Integer.toString(score);
        glyphLayout.setText(bitmapFont, scoreAsString);
        bitmapFont.draw(sb, scoreAsString, (viewPort.getWorldWidth() - glyphLayout.width) / 2, (4 * viewPort.getWorldHeight() / 5) - glyphLayout.height / 2);
    }


    private void drawFlowers() {
        for (Flower flower : flowers) {
            flower.draw(sb);
        }
    }


    private void restart() {
        flappy.setPosition(WORLD_WIDTH / 4, WORLD_HEIGHT / 2);
        flowers.clear();
        score = 0;
    }


    private boolean checkForCollision() {
        for (Flower flower : flowers) {
            if (flower.isFlappyColliding(flappy)) {
                return true;
            }
        }
        return false;
    }


    private void blockFlappeeLeavingTheWorld() {
        flappy.setPosition(flappy.getX(), MathUtils.clamp(flappy.getY(), 0, WORLD_HEIGHT));
    }


    private void checkIfNewFlowerIsNeeded() {
        if (flowers.size == 0) {
            createNewFlower();
        } else {
            Flower flower = flowers.peek();
            if (flower.getX() < WORLD_WIDTH - GAP_BETWEEN_FLOWERS) {
                createNewFlower();
            }
        }
    }


    private void createNewFlower() {
        Flower newFlower = new Flower(flowerBottom, flowerTop);
        newFlower.setPosition(WORLD_WIDTH + Flower.WIDTH);
        flowers.add(newFlower);
    }


    private void removeFlowersIfPassed() {
        if (flowers.size > 0) {
            Flower firstFlower = flowers.first();
            if (firstFlower.getX() < -Flower.WIDTH) {
                flowers.removeValue(firstFlower, true);
            }
        }
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r,Color.BLACK.g,Color.BLACK.b,Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
