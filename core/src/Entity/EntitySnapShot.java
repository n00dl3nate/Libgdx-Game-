package Entity;

import java.util.HashMap;

public class EntitySnapShot {

    private String id;
    private String type;
    private float x, y;
    private HashMap<String, String> data;

    public EntitySnapShot(){}

    public EntitySnapShot(String id, String type, float x, float y, HashMap<String, String> data) {
        this.id = id;
        this.type = type;
        this.x = x;
        this.y = y;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void putFloat (String key, float value){
        data.put(key, " "+ value);
    }
}
