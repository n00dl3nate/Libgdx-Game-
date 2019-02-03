package Entity;

import World.GameMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import static com.badlogic.gdx.graphics.g2d.Animation.PlayMode.REVERSED;


public class Player extends Entity {

    private static final int SPEED = 80;
    private static final int JUMP_VELOCITY = 5;

    private TextureAtlas textureAtlas;
    private TextureAtlas textureAtlas2;
    private Animation<TextureRegion> animation;
    private Array<TextureAtlas.AtlasRegion> idleRegions;
    private Array<TextureAtlas.AtlasRegion> runRegions;
    private Array<TextureAtlas.AtlasRegion> runReverseRegions;

    private float elapsedTime = 0f;
    Texture image;



    public void create (EntitySnapShot snapShot, EntityType type,GameMap map){
        super.create(snapShot,type,map);
        textureAtlas = new TextureAtlas(Gdx.files.internal("PlayerSpriteSheet/Spritesheet.atlas"));
        textureAtlas2 = new TextureAtlas(Gdx.files.internal("JumpSpriteSheet/SpriteSheet.atlas"));
        runRegions = textureAtlas.findRegions("adventurer-run-00");
        runReverseRegions = textureAtlas.findRegions("adventurer-run-reverse-00");
        idleRegions = textureAtlas.findRegions("adventurer-idle-00");
        animation = new Animation(1f/5f, idleRegions);
    }

    @Override
    public void update(float deltaTime, float gravity) {
        animation = new Animation(1f/5f, idleRegions);
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && grounded){
            this.velocityY += JUMP_VELOCITY * getWeight();
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && !grounded && this.velocityY > 0){
            this.velocityY += JUMP_VELOCITY * getWeight() * deltaTime;
        }
        super.update(deltaTime,gravity);

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            moveX(-SPEED * deltaTime);
            animation = new Animation(1f/5f, runReverseRegions);

        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            moveX(SPEED * deltaTime);
            animation = new Animation(1f/5f, runRegions);
        }

    }

    @Override
    public void render(SpriteBatch batch) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = animation.getKeyFrame(elapsedTime, true);
        batch.draw(currentFrame,pos.x,pos.y,getWidth(),getHeight());

    }

    @Override
    public EntitySnapShot getSaveSnapshot(){
        EntitySnapShot snapShot = super.getSaveSnapshot();
//        snapShot.putFloat("spawnRadius,value");
        return snapShot;
    }

//    @Override
    public void dispose(){
        textureAtlas.dispose();
    }
}