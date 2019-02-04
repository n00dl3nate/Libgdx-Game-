package World;

import Entity.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import Entity.Entityloader;
import Entity.Player;
import com.badlogic.gdx.math.Vector3;


import java.util.ArrayList;



public abstract class GameMap {

    protected ArrayList<Entity> entities;

    public GameMap() {
        entities = new ArrayList<Entity>();
        entities.addAll(Entityloader.loadEntities("basic",this, entities));

    }

    public void render (OrthographicCamera camera, SpriteBatch batch){
        for (Entity entity : entities){
            entity.render(batch);
            Vector3 position = camera.position;
            position.x = entity.getPos().x;
            position.y = entity.getPos().y;
            camera.position.set(position);
            camera.update();
        }

    }

    public void update (float delta) {
        for (Entity entity : entities) {
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
     * Gets a tile by pixel Location within the game world
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
