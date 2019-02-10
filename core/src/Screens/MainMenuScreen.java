package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.n00dl3nate.SideScrollingGame;

public class MainMenuScreen implements Screen {

    private static final int QUIT_BUTTON_WIDTH = 175;
    private static final int QUIT_BUTTON_HEIGHT = 63;
    private static final int START_BUTTON_WIDTH = 224;
    private static final int START_BUTTON_HEIGHT = 63;


    SideScrollingGame game;
    Texture exitActive;
    Texture exitDeactive;
    Texture playActive;
    Texture playDeactive;
    public SpriteBatch batch;

    public MainMenuScreen(SideScrollingGame game) {
        this.game = game;
        playActive = new Texture("buttons/Start.png");
        playDeactive = new Texture("buttons/StartActivate.png");
        exitDeactive = new Texture("buttons/QuitActivate.png");
        exitActive = new Texture("buttons/Quit.png");
        batch = new SpriteBatch();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
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
                Gdx.app.exit();
            }
        } else {
            batch.draw(exitDeactive, xQuit, yQuit, QUIT_BUTTON_WIDTH, QUIT_BUTTON_HEIGHT);

        }

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
