package Json;

public interface JsonValue {
    public JsonValue get(int i) throws JsonSyntaxException;
    public JsonValue get(String s) throws JsonSyntaxException;
}
