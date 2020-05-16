package Json;

public class JsonString implements JsonValue{
	private String s;
	public String getS() {
		return s;
	}public void setS(String s) {
		this.s = s;
	}
    @Override
    public JsonValue get(int i) throws JsonQueryException{
        throw new JsonQueryException("JsonString can't return value by index");
    }

    @Override
    public JsonValue get(String s) throws JsonQueryException{
    	throw new JsonQueryException("JsonString can't return value by index");
    }
    public String toString()
    {
    	return "<" + s + ">";
    }
}
