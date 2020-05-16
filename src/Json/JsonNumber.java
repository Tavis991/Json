package Json;

public class JsonNumber implements JsonValue {
    Number k;

    public JsonNumber(Number num) {
        this.k=num;
    }

    @Override

    public JsonValue get(int i) throws JsonQueryException {
        throw new JsonQueryException("nonono");
    }

    @Override
    public JsonValue get(String s) {
        return null;
    }
}
