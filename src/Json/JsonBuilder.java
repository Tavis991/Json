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
        	if(ch2=='"' && ch1 !='\'') break;
            build.append(ch2);

        }
        Jstr.setS(build.toString());
        System.out.println(Jstr.getS());
        return Jstr;
    }
        
    
    public JsonArray parseArray() throws JsonSyntaxException{

        JsonArray arr = new JsonArray();
       // arr.getA().add(parseValue());
        cs.next();
        while (cs.hasNext() && cs.peek()!=']')
        {
                try 
                {
                    arr.getA().add(parseValue());
                    while(((Character)cs.peek()).equals(',')||((Character)cs.peek()).equals(' ')) 
                    {
                        if (cs.hasNext()) cs.next();
                        else throw new JsonSyntaxException("Error in parseArray()");
                    }

                 }
                 catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
        }
        return arr;
    }
    public JsonValue parseValue() throws JsonSyntaxException{
        char ch;
        String choice;
        if (this.cs.hasNext()) 
        {
            ch = this.cs.peek();
            
            try
            {
            	choice = chCheck(ch);
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
    
    public JsonObject parseObject()
    {
    	JsonObject jo = new JsonObject();
    	// TODO
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
