package Json;

import java.util.HashMap;
import java.util.Map;

public class JsonObject implements JsonValue {
    private Map<String, JsonValue> o;
    
    public JsonObject()
    {
    	 o = new HashMap<String,JsonValue>();
    }

    public Map<String, JsonValue> getO() {
		return o;
	}

	public void setO(Map<String, JsonValue> o) {
		this.o = o;
	}

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
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        int i=0;
        for(String key : o.keySet())
        {
        	sb.append("<" + key + ":" + o.get(key)+">");
        	i++;
        }
        sb.append('}');
        return sb.toString();
    }
}
