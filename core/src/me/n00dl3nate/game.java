package me.n00dl3nate;

import World.GameMap;
import World.TileType;
import World.TiledGameMap;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class game extends ApplicationAdapter {

    OrthographicCamera cam;
	GameMap gameMap;
	SpriteBatch batch;
	
	@Override
	public void create () {
        batch = new SpriteBatch();
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        cam.zoom -= .1;
		gameMap = new TiledGameMap();

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (Gdx.input.isTouched()){
		    cam.translate(-Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
//		    cam.update();
        }
        if(Gdx.input.justTouched()){
            Vector3 pos = cam.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
            TileType type = gameMap.getTileTypeByLocation(4,pos.x,pos.y);

            if(type != null){
                System.out.println("Clicked tile" + type.getId() + "  " + type.getName() + "  ");
            }
        }
//        cam.update();
        gameMap.update(Gdx.graphics.getDeltaTime());
		gameMap.render(cam, batch);
	}
	
	@Override
	public void dispose () {
        batch.dispose();
		gameMap.dispose();
	}
}
