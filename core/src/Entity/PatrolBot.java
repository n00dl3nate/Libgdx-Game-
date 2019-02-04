package Entity;

import World.GameMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.Timer;

public class PatrolBot extends Entity {

    private static final int SPEED = 80;
    private static final int JUMP_VELOCITY = 5;

    private TextureAtlas textureAtlas;
    private Animation<TextureRegion> animation;
    private Array<TextureAtlas.AtlasRegion> idleRegions;
    private Timer timer = new Timer("Timer");

    private float elapsedTime = 0f;

    public void create (EntitySnapShot snapShot, EntityType type, GameMap map) {
        super.create(snapShot, type, map);

        textureAtlas = new TextureAtlas(Gdx.files.internal("BlueBotSpriteSheet/Spritesheet.atlas"));
        idleRegions = textureAtlas.findRegions("azul");
        animation = new Animation(1f/5f, idleRegions);
        System.out.println("Animation mada");
    }

    @Override
    public void update(float deltaTime, float gravity) {
        float newY = pos.y;

        this.velocityY += gravity * deltaTime * getWeight();
        newY += this.velocityY * deltaTime;

        if (map.doesReactCollideWithMap(pos.x, newY, getWidth(), getHeight())) {
            if (velocityY < 0) {
                this.pos.y = (float) Math.floor(pos.y);
                grounded = true;
            }
            this.velocityY = 0;
        } else {
            this.pos.y = newY;
            grounded = false;
        }

//        while(1>0) {
//            moveX(-SPEED * 0.03f);
//            moveX(SPEED * 0.03f);
//        }
    }

    @Override
    public void render(SpriteBatch batch) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = animation.getKeyFrame(elapsedTime, true);
        batch.draw(currentFrame,pos.x,pos.y,getWidth(),getHeight());
//        long delay = 20000000L;
//        moveX( delay * SPEED);
    }

    @Override
    public EntitySnapShot getSaveSnapshot(){
        EntitySnapShot snapShot = super.getSaveSnapshot();
//        snapShot.putFloat("spawnRadius,value");
        return snapShot;
    }


}
