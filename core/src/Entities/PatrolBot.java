package Entities;

import World.GameMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import tools.CollisionRect;

import java.util.Timer;

public class PatrolBot extends Entity {

    private static final int SPEED = 80;
    private static final int TILE_SIZE = 32;

    private TextureAtlas textureAtlas;
    private Animation<TextureRegion> animation;
    private Array<TextureAtlas.AtlasRegion> idleRegions;
    public int movement;
    private CollisionRect rect;
    private boolean movingRight;

    private float elapsedTime = 0f;

    public void create (EntitySnapShot snapShot, EntityType type, GameMap map) {
        super.create(snapShot, type, map);
        textureAtlas = new TextureAtlas(Gdx.files.internal("BlueBotSpriteSheet/Spritesheet.atlas"));
        idleRegions = textureAtlas.findRegions("azul");
        animation = new Animation(1f/5f, idleRegions);
        this.rect = new CollisionRect(pos.x, pos.y, type.getWidth()-15, type.getHeight()-20);
        this.movingRight = true;
    }

    @Override
    public void update(float deltaTime, float gravity) {
        float newY = pos.y;
        rect.move(this.pos.x, this.pos.y);
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
        move(deltaTime);
    }


    @Override
    public void render(SpriteBatch batch) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = animation.getKeyFrame(elapsedTime, true);
        batch.draw(currentFrame,pos.x,pos.y,getWidth(),getHeight());

    }

    public CollisionRect getCollisionRect(){
        return rect;
    }

    @Override
    public EntitySnapShot getSaveSnapshot(){
        EntitySnapShot snapShot = super.getSaveSnapshot();
        return snapShot;
    }

    public void move(float deltaTime){

        if(movingRight){
            moveX(SPEED * deltaTime);
        } else {
            moveX(-SPEED * deltaTime);
        }

        if(!checkNextTileRight()){
            movingRight=false;
        }
        if(!checkNextTileLeft()){
            movingRight=true;
        }
    }

    public boolean checkNextTileLeft(){
        if(map.doesReactCollideWithMap(pos.x - (getWidth()/2), pos.y - (getWidth()/2), getWidth(), getHeight())){
            return true;
        }
        return false;
    }

    public boolean checkNextTileRight(){
        if(map.doesReactCollideWithMap(pos.x + (getWidth()/2), pos.y - (getWidth()/2), getWidth(), getHeight())){
            return true;
        }
        return false;
    }

    public void dispose(){

    }


}
