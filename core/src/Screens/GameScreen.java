package Screens;

import Entities.Entity;
import Entities.EntityType;
import Entities.Player;
import World.GameMap;
import World.TileType;
import World.TiledGameMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import me.n00dl3nate.SideScrollingGame;
import sun.invoke.empty.Empty;

import java.util.ArrayList;

public class GameScreen implements Screen {

    OrthographicCamera cam;
    GameMap gameMap;
    final SideScrollingGame game;
    protected ArrayList<Entity> entities;
    public Boolean isPlayerDead;
    public SpriteBatch batch;
    public Entity player;
    private Texture fullHeart;
    private Texture halfHeart;
    private Texture emptyHeart;

    private static final int HEARTS_POSTION_X = -320;
    private static final int HEARTS_POSTION_Y = 155;
    private static final int HEARTS_WIDTH = 96;
    private static final int HEARTS_HEIGHT = 96;
    private int heartDistance = 48;




    public GameScreen(final SideScrollingGame game){
        this.game = game;
        isPlayerDead = false;
        batch = new SpriteBatch();
        fullHeart = new Texture("HealthHeartPixelArt/Full_Heart.png");
        halfHeart = new Texture("HealthHeartPixelArt/Half_Heart.png");
        emptyHeart = new Texture("HealthHeartPixelArt/Empty_Heart.png");
    }

    @Override
    public void show() {
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
//        cam.zoom -= .05;
        gameMap = new TiledGameMap();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameMap.update(Gdx.graphics.getDeltaTime(),this);
        gameMap.render(cam, game.batch,this.game,this);

        entities = gameMap.getEntities();

        for (int i = 0; i < entities.size() ; i++) {
            if(entities.get(i).getType() == EntityType.Player){
                player = entities.get(i);
            }
        }



        if(player.getY() < 160 || player.getHealth() <= 0 ){
            this.dispose();
            game.setScreen(new GameOverScreen(game));
            return;
        }

        for (int j = 0; j < entities.size(); j++) {
            if(player.getCollisionRect().collidesWith(entities.get(j).getCollisionRect()) && entities.get(j).getType() == EntityType.BlueBot){
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

        game.batch.begin();

        int Hitpoints = 0;
        int Distance = 0;
        for (int i = 0; i < 3 ; i++) {

            if(player.getHealth() > 1 + Hitpoints){
                game.batch.draw(fullHeart,player.getX() + HEARTS_POSTION_X + Distance,player.getY() + HEARTS_POSTION_Y,HEARTS_WIDTH,HEARTS_HEIGHT);
            } else if(player.getHealth() == 1 + Hitpoints){
                game.batch.draw(halfHeart,player.getX() + HEARTS_POSTION_X + Distance,player.getY() + HEARTS_POSTION_Y,HEARTS_WIDTH,HEARTS_HEIGHT);
            } else if(player.getHealth() <= 0 + Hitpoints){
                game.batch.draw(emptyHeart,player.getX() + HEARTS_POSTION_X + Distance,player.getY() + HEARTS_POSTION_Y,HEARTS_WIDTH,HEARTS_HEIGHT);
            }
            Distance += heartDistance;
            Hitpoints += 2;
        }


        cam.update();
        game.batch.end();
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
