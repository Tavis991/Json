package Json;

import static java.lang.Character.isDigit;

import java.io.File;
import java.io.FileNotFoundException;

public class JsonBuilder implements JsonValue {
    private CharScanner cs;
    private JsonValue v;

    public JsonBuilder() throws FileNotFoundException {
    	cs = new CharScanner(new File("C:\\Users\\אדיר\\adir\\Json Final\\Json\\src\\Json\\example.txt"));

        try
        {
        	v = parseValue();
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
    }

    public JsonString parseString() throws JsonSyntaxException
    {
        StringBuilder build = new StringBuilder();
        JsonString Jstr = new JsonString();
        char ch1,ch2=' ';
        cs.next();
        while(cs.hasNext())
        {
        	ch1=ch2;
        	
        	if(cs.hasNext()) ch2=cs.next();
        	else break;
        	if(ch2=='"' && ch1 !='\\') break;
        	if(ch2=='\\' && cs.peek() == '"') continue;
            build.append(ch2);

        }
        Jstr.setS(build.toString());
        return Jstr;
    }
        
    
    public JsonArray parseArray() throws JsonSyntaxException{

        JsonArray arr = new JsonArray();
        cs.next();
        while (cs.hasNext() && cs.peek()!=']')
        {
                try 
                {
                    arr.getA().add(parseValue());
                    if(cs.peek()==',') cs.next();
                }
                catch (JsonSyntaxException e) 
                {
                    e.printStackTrace();
                }
        }
        cs.next();
        return arr;
    }
    public JsonValue parseValue() throws JsonSyntaxException{
        String choice;
        if (cs.hasNext()) 
        {
            try
            {
            	choice = chCheck(cs.peek());
            }
            catch (JsonSyntaxException e)
            {
            	throw e;
            }
            	
            switch (choice) 
            {
                case "ArrayStart":
                    return parseArray();
                case "Str":
                	return parseString();
                case "AssoArrayStart":
                	return parseObject();
                case "Num":
                	// TODO after parseNum
                default: throw new JsonSyntaxException("Error in parseValue()");
            }
        }
        else throw new JsonSyntaxException("Error in parseValue()");
    }
    
    public JsonObject parseObject() throws JsonSyntaxException
    {
    	JsonObject jo = new JsonObject();
    	String key;
    	JsonValue value;
    	cs.next(); 
    	while(cs.hasNext() && cs.peek()!='}')
    	{
    		try
    		{
    			key = parseString().getS();
    			cs.next();
    			value = parseValue();
    			cs.next();
    			jo.getO().put(key, value);
    		}
    		catch(JsonSyntaxException e)
    		{
    			throw e;
    		}
    	}
    	return jo;
    }

    public String chCheck(char ch) throws JsonSyntaxException{

        if (isDigit(ch) || ch == '-') {
            return "Num";
        }
        if (ch == '[') {
            return "ArrayStart";
        }
        if (ch == ']') {
            return "ArrayEnd";
        }
        if (ch == '{') {
            return "AssoArrayStart";
        }
        if (ch == '}') {
            return "AssoArrayEnd";
        }
        if (ch == '"') {
            return "Str";
        }
        return "nothing";
       // throw new JsonSyntaxException("unknown character. Error in chCheck()");
    }
    
    public String strtCheck(char ch) throws JsonSyntaxException {
        if (ch == '[') {
            return "ArrayStart";
        }
        if (isDigit(ch) || ch == '-'){
            return "Num";
        }
        if (ch == '"') {
            return "Str";
        }
        if (ch == '{') {
            return "AssoArrayStart";
        }
        throw new  JsonSyntaxException("no possible");
    }
    
    @Override
    public JsonValue get(int i) {
        return null;
    }

    @Override
    public JsonValue get(String s) {
        return null;
    }

	public JsonValue getV() {
		return v;
	}

	public void setV(JsonValue v) {
		this.v = v;
	}
	public String toString()
	{
		return v.toString();
	}
}
