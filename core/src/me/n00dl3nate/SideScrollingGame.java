package me.n00dl3nate;

import Screens.GameOverScreen;
import Screens.GameScreen;
import Screens.MainMenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class SideScrollingGame extends Game {

	public SpriteBatch batch;
	SideScrollingGame game;

	public SideScrollingGame() {
		game = this;
	}

	@Override
	public void create () {
        batch = new SpriteBatch();
        this.setScreen(new MainMenuScreen(game));
	}

	@Override
	public void render () {
		super.render();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		super.dispose();
	}
}
