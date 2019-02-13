package Entities;

import World.GameMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;


import java.util.HashMap;

@SuppressWarnings("rawtypes")
public enum EntityType {

    Player("player",Player.class ,75,55,40),
    BlueBot("blueBot",PatrolBot.class ,32,32,40),
    GoldCoin("goldCoin",GoldCoin.class,16,16,40);

    private String id;
    private Class<? extends Entity> loaderClass;
    private int width, height;
    private float weight;

    private EntityType(String id, Class loaderClass, int width, int height, float weight) {
        this.id = id;
        this.loaderClass = loaderClass;
        this.width = width;
        this.height = height;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getWeight() {
        return weight;
    }

    public static Entity createEntityUsingSnapshot (EntitySnapShot entitySnapshot, GameMap map) {
        EntityType type = entityTypes.get(entitySnapshot.type);
        try {
            Entity entity = ClassReflection.newInstance(type.loaderClass);
            entity.create(entitySnapshot, type, map);
            return entity;
        } catch (ReflectionException e) {
            Gdx.app.error("Entities Loader", "Could not load entity of type " + type.id);
            return null;
        }
    }

    private static HashMap<String, EntityType> entityTypes;

    static{
        entityTypes = new HashMap<String, EntityType>();
        for(EntityType type : EntityType.values())
            entityTypes.put(type.id,type);
    }
}
