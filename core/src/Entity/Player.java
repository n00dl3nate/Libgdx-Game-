package Entity;

import World.GameMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import tools.CollisionRect;

public class Player extends Entity {

    public enum State {FALLING, JUMPING, STANDING, RUNNING, GROWING, DEAD}

    ;
    public State currentState;
    public State previousState;

    private static final int SPEED = 120;
    private static final int JUMP_VELOCITY = 6;

    private TextureAtlas textureAtlas;
    private TextureAtlas textureAtlas2;

    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> runAnimation;
    private Animation<TextureRegion> jumpAnimation;
    private Animation<TextureRegion> bigJumpAnimation;
    private Animation<TextureRegion> fallAnimation;

    private CollisionRect rect;

    private Vector2 previousPosition;
    private float stateTimer;
    private boolean runningRight;
    private boolean isDead;


    private float elapsedTime = 0f;

    public void create(EntitySnapShot snapShot, EntityType type, GameMap map) {
        super.create(snapShot, type, map);
        runningRight = true;
        textureAtlas = new TextureAtlas(Gdx.files.internal("PlayerSpriteSheet/Spritesheet.atlas"));
        textureAtlas2 = new TextureAtlas(Gdx.files.internal("JumpSpriteSheet/SpriteSheet.atlas"));
        idleAnimation = new Animation(1f / 10f, textureAtlas.findRegions("adventurer-idle-00"));
        runAnimation = new Animation(1f / 10f, textureAtlas.findRegions("adventurer-run-00"));
        jumpAnimation = new Animation(1f / 10f, textureAtlas2.findRegions("adventurer-jump-00"));
        fallAnimation = new Animation(1f / 10f, textureAtlas2.findRegions("adventurer-fall-00"));
        previousPosition = new Vector2();
        rect = new CollisionRect(pos.x, pos.y, type.getWidth(), type.getHeight());
    }


    @Override
    public void update(float deltaTime, float gravity) {
        previousPosition.x = this.pos.x;
        previousPosition.y = this.pos.y;
        rect.move(this.pos.x, this.pos.y);
        previousState = currentState;
        currentState = State.STANDING;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            moveX(-SPEED * deltaTime);
            currentState = State.RUNNING;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moveX(SPEED * deltaTime);
            currentState = State.RUNNING;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && grounded) {
            this.velocityY += JUMP_VELOCITY * getWeight();
            currentState = State.JUMPING;
        } else if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !grounded && this.velocityY > 0) {
            this.velocityY += JUMP_VELOCITY * getWeight() * deltaTime;
            currentState = State.JUMPING;
        }
        super.update(deltaTime, gravity);

        if (!grounded && this.pos.y > previousPosition.y) {
            currentState = State.JUMPING;
        }

        if (!grounded && this.pos.y < previousPosition.y) {
            currentState = State.FALLING;
        }
    }

    public TextureRegion GetAnimationType() {
        TextureRegion result;
        result = idleAnimation.getKeyFrame(elapsedTime, true);

        switch (currentState) {
            case RUNNING:
                result = runAnimation.getKeyFrame(elapsedTime, true);
                break;
            case JUMPING:
                result = jumpAnimation.getKeyFrame(elapsedTime, false);
                break;
            case FALLING:
                result = fallAnimation.getKeyFrame(elapsedTime, false);
                break;
            case STANDING:
                result = idleAnimation.getKeyFrame(elapsedTime, true);
        }
        isRunningRight();

        if (runningRight == false && !result.isFlipX()) {
            result.flip(true, false);
        }
        if (runningRight == true && result.isFlipX()) {
            result.flip(true, false);
        }

        return result;
    }

    public CollisionRect getCollisionRect(){
        return rect;
    }


    public void isRunningRight() {
        if (this.pos.x > previousPosition.x) {
            runningRight = true;
        } else if (this.pos.x < previousPosition.x) {
            runningRight = false;
        }
    }


    @Override
    public void render(SpriteBatch batch) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        TextureRegion animation = GetAnimationType();

        batch.draw(animation, pos.x, pos.y, getWidth(), getHeight());

    }

    @Override
    public EntitySnapShot getSaveSnapshot() {
        EntitySnapShot snapShot = super.getSaveSnapshot();
        return snapShot;
    };
}