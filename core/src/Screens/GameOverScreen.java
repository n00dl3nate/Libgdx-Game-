package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import me.n00dl3nate.SideScrollingGame;

public class GameOverScreen implements Screen {

    Texture gameOverBanner;
    Texture exitActive;
    Texture exitDeactive;
    Texture playActive;
    Texture playDeactive;
    Vector2 pos;
    public SpriteBatch batch;

    private static final int BANNER_WIDTH = 350;
    private static final int BANNER_HEIGHT = 350;

    private static final int QUIT_BUTTON_WIDTH = 175;
    private static final int QUIT_BUTTON_HEIGHT = 63;
    private static final int START_BUTTON_WIDTH = 224;
    private static final int START_BUTTON_HEIGHT = 63;



    OrthographicCamera camera;

    protected final SideScrollingGame game;

    public GameOverScreen(SideScrollingGame game) {
        this.game = game;
        playActive = new Texture("buttons/Start.png");
        playDeactive = new Texture("buttons/StartActivate.png");
        exitDeactive = new Texture("buttons/QuitActivate.png");
        exitActive = new Texture("buttons/Quit.png");
        gameOverBanner = new Texture("GameOverLogo.png");
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 8);
    }

    @Override
    public void show() {


    }

    @Override
    public void render(float delta) {

        if (Gdx.input.isTouched()){
            camera.translate(-Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
        }
        camera.update();
//        System.out.println("Yes");
        Gdx.gl.glClearColor(.3f, .3f, .1f, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        int centreWidth = Gdx.graphics.getWidth()/2;
        int centreHeight = Gdx.graphics.getHeight()/2;

        int xQuit = centreWidth + 90;
        int yQuit = centreHeight - 170;
        int xStart = centreWidth - 260;
        int yStart = centreHeight - 170;

        if(Gdx.input.getX() < xStart + START_BUTTON_WIDTH && Gdx.input.getX() > xStart && Gdx.graphics.getHeight()- Gdx.input.getY() < START_BUTTON_HEIGHT + START_BUTTON_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > START_BUTTON_HEIGHT){
            batch.draw(playActive,xStart,yStart,START_BUTTON_WIDTH,START_BUTTON_HEIGHT);
            if(Gdx.input.isTouched()){
                this.dispose();
                game.setScreen(new GameScreen(game));
            }
        } else{
            batch.draw(playDeactive,xStart,yStart,START_BUTTON_WIDTH,START_BUTTON_HEIGHT);

        }
        if(Gdx.input.getX() < xQuit + QUIT_BUTTON_WIDTH && Gdx.input.getX() > xQuit && Gdx.graphics.getHeight()- Gdx.input.getY() < QUIT_BUTTON_HEIGHT + QUIT_BUTTON_HEIGHT && Gdx.graphics.getHeight() -  Gdx.input.getY() > QUIT_BUTTON_HEIGHT) {
            batch.draw(exitActive, xQuit, yQuit, QUIT_BUTTON_WIDTH, QUIT_BUTTON_HEIGHT);
            if(Gdx.input.isTouched()){
                game.setScreen(new MainMenuScreen(game));
            }
        } else {
            batch.draw(exitDeactive, xQuit, yQuit, QUIT_BUTTON_WIDTH, QUIT_BUTTON_HEIGHT);

        }

        batch.draw(gameOverBanner,Gdx.graphics.getWidth()/2 - BANNER_WIDTH / 2,Gdx.graphics.getHeight() -BANNER_HEIGHT- 15,BANNER_WIDTH,BANNER_HEIGHT);
        camera.update();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
//        batch.dispose();
    }
}
