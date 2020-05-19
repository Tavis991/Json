package Json;

public class JsonNumber implements JsonValue {
    Number k;

    public JsonNumber(Number num) {
        this.k=num;
    }

    @Override

    public JsonValue get(int i) throws JsonQueryException {
        throw new JsonQueryException("JsonNumber can not return value by index");
    }

    @Override
    public JsonValue get(String s) throws JsonQueryException{
    	throw new JsonQueryException("JsonNumber can not return value by String");
    }

    @Override
    public String toString() {
        return k.toString();
    }
}
