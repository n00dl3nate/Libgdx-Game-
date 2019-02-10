package Entities;

import World.GameMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;

public class Entityloader {

    private static Json json = new Json();

    public static ArrayList<Entity> loadEntities (String id, GameMap map, ArrayList<Entity> currentEntities){
        Gdx.files.local("maps/").file().mkdirs();
        FileHandle file = Gdx.files.local("maps/" + id + ".entities");
        if (file.exists()){
            EntitySnapShot[] snapShots = json.fromJson(EntitySnapShot[].class, file.readString());
            ArrayList<Entity> entities = new ArrayList<Entity>();
            for (EntitySnapShot snapshot : snapShots){
                entities.add(EntityType.createEntityUsingSnapshot(snapshot,map));
            }
            return entities;
        } else {
            Gdx.app.error("EntityLoader", "Could not Load entities.");
            saveEntities(id,currentEntities);
            return currentEntities;
        }
    }

    public static void saveEntities (String id, ArrayList<Entity> entities){
        ArrayList<EntitySnapShot> snapShots = new ArrayList<EntitySnapShot>();
        for(Entity entity : entities){
            snapShots.add(entity.getSaveSnapshot());
        }

        Gdx.files.local("maps/").file().mkdirs();
        FileHandle file = Gdx.files.local("maps/" + id + ".entities");
        file.writeString(json.prettyPrint(snapShots), false);
    }

}
