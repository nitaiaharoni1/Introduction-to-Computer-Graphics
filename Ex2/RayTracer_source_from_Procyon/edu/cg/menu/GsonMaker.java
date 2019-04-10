// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.menu;

import edu.cg.scene.objects.Shape;
import edu.cg.scene.lightSources.Light;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonParseException;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonDeserializationContext;
import java.lang.reflect.Type;
import com.google.gson.JsonElement;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

public class GsonMaker<T> implements JsonSerializer<T>, JsonDeserializer<T>
{
    private static final String CLASSNAME = "CLASSNAME";
    private static final String DATA = "DATA";
    
    @Override
    public T deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final JsonObject jsonObject = jsonElement.getAsJsonObject();
        final JsonPrimitive prim = (JsonPrimitive)jsonObject.get("CLASSNAME");
        final String className = prim.getAsString();
        final Class<T> klass = this.getObjectClass(className);
        return jsonDeserializationContext.deserialize(jsonObject.get("DATA"), klass);
    }
    
    @Override
    public JsonElement serialize(final T jsonElement, final Type type, final JsonSerializationContext jsonSerializationContext) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("CLASSNAME", jsonElement.getClass().getName());
        jsonObject.add("DATA", jsonSerializationContext.serialize(jsonElement));
        return jsonObject;
    }
    
    public Class<T> getObjectClass(final String className) {
        try {
            return (Class<T>)Class.forName(className);
        }
        catch (ClassNotFoundException e) {
            throw new JsonParseException(e.getMessage());
        }
    }
    
    public static Gson getInstance() {
        return GsonHolder.gson;
    }
    
    private static class GsonHolder
    {
        public static Gson gson;
        
        static {
            final GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setPrettyPrinting().registerTypeAdapter(Light.class, new GsonMaker()).registerTypeAdapter(Shape.class, new GsonMaker());
            GsonHolder.gson = gsonBuilder.create();
        }
    }
}
