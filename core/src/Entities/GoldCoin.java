package Entities;

import World.GameMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class GoldCoin extends Entity {

    TextureAtlas textureAtlas;
    Animation<TextureRegion> coin;
    private float elapsedTime = 0f;

    public void create(EntitySnapShot snapShot, EntityType type, GameMap map) {
        super.create(snapShot, type, map);
        textureAtlas = new TextureAtlas(Gdx.files.internal("Coin_Gems/GoldCoinSpritesheet.atlas"));
        coin = new Animation(1f / 10f, textureAtlas.findRegions("Moneda"));
    }


    @Override
    public void render(SpriteBatch batch) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = coin.getKeyFrame(elapsedTime, true);
        batch.draw(currentFrame,pos.x,pos.y,getWidth(),getHeight());
    }
}
