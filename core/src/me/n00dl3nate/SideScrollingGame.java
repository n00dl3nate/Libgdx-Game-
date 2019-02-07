package me.n00dl3nate;

import Screens.GameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class SideScrollingGame extends Game {

	public SpriteBatch batch;
	
	@Override
	public void create () {
        batch = new SpriteBatch();
        this.setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {

	}
}
