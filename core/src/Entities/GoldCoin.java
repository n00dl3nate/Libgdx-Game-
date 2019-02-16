package Entities;

import World.GameMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import tools.CollisionRect;

public class GoldCoin extends Entity {

    TextureAtlas textureAtlas;
    Animation<TextureRegion> coin;
    private float elapsedTime = 0f;
    private CollisionRect rect;

    public void create(EntitySnapShot snapShot, EntityType type, GameMap map) {
        super.create(snapShot, type, map);
        textureAtlas = new TextureAtlas(Gdx.files.internal("Coin_Gems/GoldCoinSpritesheet.atlas"));
        coin = new Animation(1f / 10f, textureAtlas.findRegions("Moneda"));
        this.rect = new CollisionRect(pos.x, pos.y, type.getWidth()-15, type.getHeight()-20);

    }

    @Override
    public void update(float deltaTime, float gravity) {
        rect.move(this.pos.x, this.pos.y);
    }

    public CollisionRect getCollisionRect(){
        return rect;
    }

    @Override
    public void render(SpriteBatch batch) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = coin.getKeyFrame(elapsedTime, true);
        batch.draw(currentFrame,pos.x,pos.y,getWidth(),getHeight());
    }
}
