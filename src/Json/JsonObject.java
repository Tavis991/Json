package Json;

import java.util.Map;

public class JsonObject implements JsonValue {
    private Map<String, JsonValue> o;

    @Override
    public JsonValue get(int i)  {
        return null;
    }

    @Override
    public JsonValue get(String s) {
        return null;
    }

    @Override
    public String toString() {
        return "JsonObject{" +
                "o=" + o.toString() +
                '}';
    }
}
