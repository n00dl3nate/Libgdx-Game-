package Screens;

import World.GameMap;
import World.TileType;
import World.TiledGameMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import me.n00dl3nate.SideScrollingGame;

public class GameScreen implements Screen {

    OrthographicCamera cam;
    GameMap gameMap;
    SideScrollingGame game;

    public GameScreen( SideScrollingGame game){
        this.game = game;

    }

    @Override
    public void show() {
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        cam.zoom -= .1;
        gameMap = new TiledGameMap();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if (Gdx.input.isTouched()){
            cam.translate(-Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
        }
        if(Gdx.input.justTouched()){
            Vector3 pos = cam.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
            TileType type = gameMap.getTileTypeByLocation(4,pos.x,pos.y);

            if(type != null){
                System.out.println("Clicked tile" + type.getId() + "  " + type.getName() + "  ");
            }
        }
        gameMap.update(Gdx.graphics.getDeltaTime());
        gameMap.render(cam, game.batch);

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
        game.batch.dispose();
        gameMap.dispose();
    }
}
