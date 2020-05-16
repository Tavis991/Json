package Json;

public interface JsonValue {
    public JsonValue get(int i) throws JsonQueryException;
    public JsonValue get(String s)throws JsonQueryException;
}
