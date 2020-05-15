package Json;

import java.util.ArrayList;
import java.util.List;

public class JsonArray implements JsonValue{
    private List<JsonValue> a = new ArrayList<JsonValue>();
    @Override
    public JsonValue get(int i) throws JsonSyntaxException {
        return null;
    }

    @Override
    public JsonValue get(String s) throws JsonSyntaxException {
        return null;
    }

    public List<JsonValue> getA() {
        return a;
    }

    public void setA(List<JsonValue> a) {
        this.a = a;
    }
}
