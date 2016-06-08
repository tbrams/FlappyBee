package dk.brams.flappybee;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class StartScreen extends ScreenAdapter{
    private static final float WORLD_WIDTH = 480;
    private static final float WORLD_HEIGHT = 640;
    private Stage stage;

    private Texture bgTexture;
    private Texture bUpTexture;
    private Texture bDnTexture;
    private Texture titleTexture;

    private final Game game;

    public StartScreen(Game game) {
        this.game = game;
    }


    public void show(){
        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        bgTexture = new Texture(Gdx.files.internal("bg.png"));
        Image bgImage = new Image(bgTexture);
        stage.addActor(bgImage);

        bUpTexture = new Texture(Gdx.files.internal("play.png"));
        bDnTexture = new Texture(Gdx.files.internal("playPress.png"));
        ImageButton play = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(bUpTexture)),
                new TextureRegionDrawable(new TextureRegion(bDnTexture))
        );

        // Add event listener to this button
        play.addListener(new ActorGestureListener(){
            public void tap(InputEvent event, float x, float y, int count, int button){
                super.tap(event, x, y, count, button);
                game.setScreen(new GameScreen());
                dispose();
            }
        });

        play.setPosition(WORLD_WIDTH/2,WORLD_HEIGHT/4, Align.center);
        stage.addActor(play);

        titleTexture = new Texture(Gdx.files.internal("title.png"));
        Image titleImage = new Image(titleTexture);
        titleImage.setPosition(WORLD_WIDTH/2, 3*WORLD_HEIGHT/4, Align.center);
        stage.addActor(titleImage);


    }

    public void resize(int w, int h){
        stage.getViewport().update(w,h,true);
    }

    public void render(float delta){
        stage.act(delta);
        stage.draw();
    }
}
