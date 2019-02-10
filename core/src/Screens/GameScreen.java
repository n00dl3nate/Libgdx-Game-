package Screens;

import Entities.Entity;
import Entities.Player;
import World.GameMap;
import World.TileType;
import World.TiledGameMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import me.n00dl3nate.SideScrollingGame;

import java.util.ArrayList;

public class GameScreen implements Screen {

    OrthographicCamera cam;
    GameMap gameMap;
    final SideScrollingGame game;
    Boolean isPlayerDead;
    protected ArrayList<Entity> entities;

    public GameScreen(final SideScrollingGame game){
        this.game = game;
        isPlayerDead = false;
    }

    @Override
    public void show() {
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        cam.zoom -= .1;
        gameMap = new TiledGameMap();
    }

    public void gameOver(){
        System.out.println("Here");
        this.dispose();
        game.setScreen(new GameOverScreen(game));
        return;
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameMap.update(Gdx.graphics.getDeltaTime(),this);
        gameMap.render(cam, game.batch,this.game,this);

//        if (Gdx.input.isTouched()){
//            cam.translate(-Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
//        }
//        if(Gdx.input.justTouched()){
//            Vector3 pos = cam.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
//            TileType type = gameMap.getTileTypeByLocation(4,pos.x,pos.y);
//
//            if(type != null){
//                System.out.println("Clicked tile" + type.getId() + "  " + type.getName() + "  ");
//            }
//        }



        entities = gameMap.getEntities();
        Entity player = entities.get(entities.size()-1);

        if(player.getY() < 160){
            this.dispose();
            game.setScreen(new GameOverScreen(game));
            return;
        }

        for (int j = 0; j < entities.size()-1 ; j++) {
            if(player.getCollisionRect().collidesWith(entities.get(j).getCollisionRect())){
                player.setHealth(player.getHealth()-1);
                System.out.println(player.getHealth());
                if(player.getX() <= entities.get(j).getX()){
                    player.setVelocityY(3f * player.getWeight());
                    player.CollideHit((-20f * delta));
                }
                else if(player.getX() > entities.get(j).getX()-20){
                    player.setVelocityY(3f * player.getWeight());
                    player.CollideHit((20f * delta));
                }
            }
        }
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
    }
}
