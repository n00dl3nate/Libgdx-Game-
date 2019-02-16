package World;

import Entities.Entity;
import Screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import Entities.Entityloader;
import com.badlogic.gdx.math.Vector3;
import me.n00dl3nate.SideScrollingGame;


import java.util.ArrayList;



public abstract class GameMap {

    protected ArrayList<Entity> entities;
    protected ArrayList<Entity> bluePatrolBots;
    protected ArrayList<Entity> players;
    protected ArrayList<Entity> coins;

    public GameMap() {
        entities = new ArrayList<Entity>();
        entities.addAll(Entityloader.loadEntities("basic",this, entities));

        bluePatrolBots = new ArrayList<Entity>();
        bluePatrolBots.addAll(Entityloader.loadEntities("blueBots",this, bluePatrolBots));
        players = new ArrayList<Entity>();
        players.addAll(Entityloader.loadEntities("players",this, players));
        coins = new ArrayList<Entity>();
        coins.addAll(Entityloader.loadEntities("coins",this, coins));


    }

    public ArrayList<Entity> getBluePatrolBots() {
        return bluePatrolBots;
    }

    public ArrayList<Entity> getPlayers() {
        return players;
    }

    public ArrayList<Entity> getCoins() {
        return coins;
    }

    public void render (OrthographicCamera camera, SpriteBatch batch, SideScrollingGame game){
        for (Entity entity : bluePatrolBots){
            entity.render(batch);
//
        }

        for (Entity entity : players){
            entity.render(batch);
            Vector3 position = camera.position;
            position.x = entity.getPos().x;
            position.y = entity.getPos().y;
            camera.position.set(position);
            camera.update();
        }

        for (Entity entity : coins){
            entity.render(batch);
        }
    }

    public void update (float delta,GameScreen screen) {
        for (Entity entity : entities) {
            entity.update(delta, -9.8f);
        }

        for (Entity entity : bluePatrolBots) {
            entity.update(delta, -9.8f);
        }

        for (Entity entity : players) {
            entity.update(delta, -9.8f);
        }

        for (Entity entity : coins) {
            entity.update(delta, -9.8f);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            Entityloader.saveEntities("basic", entities);
        }

    }
    public void dispose (){
        Entityloader.saveEntities("basic",entities);
    }

    /**
     * Gets a tile by pixel Location within the SideScrollingGame world
     * @param layer
     * @param x
     * @param y
     * @return
     */

    public TileType getTileTypeByLocation(int layer,float x,float y){
        return  this.getTileTypeByCoordinate(layer, (int) (x/TileType.TILE_SIZE), (int) (y/TileType.TILE_SIZE));
    }


    /**
     * Gets a tile at its coordinate within the map at a specified Layer.
     * @param layer
     * @param col
     * @param row
     * @return
     */
    public abstract  TileType getTileTypeByCoordinate(int layer,int col,int row);

    public boolean doesReactCollideWithMap(float x,float y, int width, int height){
        if(x < 0 || y <0 || x + width > getPixelWidth() || y + height > getPixelHeight()){
            return true;
        }
        for (int row = (int) (y / TileType.TILE_SIZE); row < Math.ceil(y+ height) / TileType.TILE_SIZE; row++) {
            for (int col = (int) (x / TileType.TILE_SIZE); col < Math.ceil(x + width) / TileType.TILE_SIZE; col++) {
                for (int layer = 0; layer < getLayers(); layer ++){
                    TileType type = getTileTypeByCoordinate(layer,col,row);
                    if (type != null && type.isCollidable()){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public abstract int getWidth();
    public abstract int getHeight();
    public abstract  int getLayers();

    public int getPixelWidth(){
        return this.getWidth() * TileType.TILE_SIZE;
    }

    public int getPixelHeight(){
        return this.getHeight() * TileType.TILE_SIZE;
    }



}
