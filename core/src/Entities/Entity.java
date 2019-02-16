package Entities;

import World.GameMap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import tools.CollisionRect;

import java.util.Timer;

public abstract class Entity {

    protected Vector2 pos;
    protected EntityType type;
    public float velocityY = 0;
    protected GameMap map;
    protected boolean grounded = false;
    private CollisionRect rect;
    private Timer timer;
    protected int health;
    protected int coins;


    public void create(EntitySnapShot snapShot,EntityType type, GameMap map) {
        this.pos = new Vector2(snapShot.getX(), snapShot.getY());
        this.type = type;
        this.map = map;
        this.rect = new CollisionRect(pos.x, pos.y, type.getWidth()-25, type.getHeight());
    }

    public void update (float deltaTime, float gravity) {
        rect.move(this.pos.x, this.pos.y);
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

    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public abstract void render (SpriteBatch batch);

    public void moveX (float amount) {
        float newX = this.pos.x + amount;
        if(!map.doesReactCollideWithMap(newX,pos.y,getWidth(),getHeight())){
            this.pos.x = newX;
        }
    }

    public void CollideHit(float amountX ){
        System.out.println(amountX);
        for (int i = 0; i <50 ; i++) {
            float newX = this.pos.x + amountX;
            if(!map.doesReactCollideWithMap(newX,pos.y,getWidth(),getHeight())){
                this.pos.x = newX;
            }
        }
    }

    public CollisionRect getCollisionRect(){
        return rect;
    }


    public EntitySnapShot getSaveSnapshot () {
        return new EntitySnapShot(type.getId(), pos.x, pos.y);
    }


    public float getX(){
        return pos.x;
    }

    public float getY(){
        return pos.y;
    }

    public Vector2 getPos() {
        return pos;
    }

    public EntityType getType() {
        return type;
    }


    public boolean isGrounded() {
        return grounded;
    }

    public int getWidth(){
        return type.getWidth();
    }

    public int getHeight(){
        return type.getHeight();
    }

    public float getWeight(){
        return type.getWeight();
    }
}
