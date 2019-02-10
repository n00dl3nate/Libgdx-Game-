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
    Texture tryAgainActive;
    Texture tryAgainDeactive;
    Vector2 pos;
    public SpriteBatch batch;

    private static final int BANNER_WIDTH = 380;
    private static final int BANNER_HEIGHT = 280;

    private static final int QUIT_BUTTON_WIDTH = 175;
    private static final int QUIT_BUTTON_HEIGHT = 63;

    private static final int TRYAGAIN_BUTTON_WIDTH = 357;
    private static final int TRYAGAIN_BUTTON_HEIGHT = 63;



    OrthographicCamera camera;

    protected final SideScrollingGame game;

    public GameOverScreen(SideScrollingGame game) {
        this.game = game;
        tryAgainActive = new Texture("buttons/TryAgain.png");
        tryAgainDeactive = new Texture("buttons/TryAgainActivate.png");
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
        Gdx.gl.glClearColor(.211f, .211f, .211f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        int centreWidth = Gdx.graphics.getWidth()/2;
        int centreHeight = Gdx.graphics.getHeight()/2;

        int xQuit = centreWidth - QUIT_BUTTON_WIDTH/2;
        int yQuit = centreHeight - QUIT_BUTTON_HEIGHT*3;
        
        int xTryAgain = centreWidth - TRYAGAIN_BUTTON_WIDTH / 2;
        int yTryAgain = centreHeight - TRYAGAIN_BUTTON_HEIGHT * 2;

        if(Gdx.input.getX() < xTryAgain + TRYAGAIN_BUTTON_WIDTH && Gdx.input.getX() > xTryAgain && Gdx.graphics.getHeight() - Gdx.input.getY()- TRYAGAIN_BUTTON_HEIGHT < TRYAGAIN_BUTTON_HEIGHT*2 && Gdx.graphics.getHeight() - Gdx.input.getY() > TRYAGAIN_BUTTON_HEIGHT*2){
            batch.draw(tryAgainActive,xTryAgain,yTryAgain,TRYAGAIN_BUTTON_WIDTH,TRYAGAIN_BUTTON_HEIGHT);
            if(Gdx.input.isTouched()){
                this.dispose();
                game.setScreen(new GameScreen(game));
            }
        } else{
            batch.draw(tryAgainDeactive,xTryAgain,yTryAgain,TRYAGAIN_BUTTON_WIDTH,TRYAGAIN_BUTTON_HEIGHT);

        }
        if(Gdx.input.getX() < xQuit + QUIT_BUTTON_WIDTH && Gdx.input.getX() > xQuit && Gdx.graphics.getHeight()- Gdx.input.getY() < QUIT_BUTTON_HEIGHT + QUIT_BUTTON_HEIGHT && Gdx.graphics.getHeight() -  Gdx.input.getY() > QUIT_BUTTON_HEIGHT){
            batch.draw(exitActive, xQuit, yQuit, QUIT_BUTTON_WIDTH, QUIT_BUTTON_HEIGHT);
            if(Gdx.input.isTouched()){
                game.setScreen(new MainMenuScreen(game));
            }
        } else {
            batch.draw(exitDeactive, xQuit, yQuit, QUIT_BUTTON_WIDTH, QUIT_BUTTON_HEIGHT);

        }

        batch.draw(gameOverBanner,centreWidth - BANNER_WIDTH/2,centreHeight -50 ,BANNER_WIDTH,BANNER_HEIGHT);
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
