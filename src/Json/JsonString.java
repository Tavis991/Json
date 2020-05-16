package Json;

public class JsonString implements JsonValue{
	private String s;
	public String getS() {
		return s;
	}public void setS(String s) {
		this.s = s;
	}
    @Override
    public JsonValue get(int i) {
        return null;
    }

    @Override
    public JsonValue get(String s) {
        return null;
    }
    public String toString()
    {
    	return s;
    }
}
