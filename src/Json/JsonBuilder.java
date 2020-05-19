package Json;

import static java.lang.Character.isDigit;
import java.lang.Double;
import java.lang.Integer;
import java.io.File;
import java.io.FileNotFoundException;

public class JsonBuilder implements JsonValue{
    private CharScanner cs;
    private JsonValue v;

    public JsonBuilder() throws FileNotFoundException, JsonSyntaxException
    {
    	cs = new CharScanner(new File("example.txt"));
        v = parseValue();
    }

    public JsonString parseString() throws JsonSyntaxException
    {
        StringBuilder build = new StringBuilder(); // will build the string for Jstr
        JsonString Jstr = new JsonString();
        char current;
        boolean inside_string = false;
        cs.next(); // reading the first "
        while(cs.hasNext())
        {
        	current = cs.nextWithSpaces();
        	
        	// if (current = '\') there is 2 legal options after it: " or \
        	if(current=='\\')
        	{
        		if(cs.peek()=='"')
        		{
        			// every \" have 2 options meaning: starting or ending an inside string
            		inside_string=!inside_string; // true->false , false -> true
            		continue;         			
        		}
        		else if(cs.peek()=='\\')
        		{	
        			build.append(current);
        			cs.next();
        			continue;
        		}
        		
        		// illegal option
        		else throw new JsonSyntaxException("Error in parseString(): \\ with no context");
        	}
        	
        	// if not \ and there is " after we finish the string
        	else if (cs.peek()=='"') 
        	{
        		build.append(current);
        		break;
        	}
        	build.append(current);     	
        }
        cs.next(); // reading the last "
        if(inside_string==true) throw new JsonSyntaxException("Error in parseString(): inside string never closed");
        Jstr.setS(build.toString());
        return Jstr;
    }
        
    
    public JsonArray parseArray() throws JsonSyntaxException
    {
        JsonArray arr = new JsonArray();
        cs.next(); // reading the '['
        char ch;
        while (cs.hasNext())
        {
            arr.getA().add(parseValue()); // adding the next JsonValue to arr
            ch = cs.next();
            if(ch==']') break;
            if(!cs.hasNext()) throw new JsonSyntaxException("Error in parseArray(): ']' missing ");
            if(ch!=',') throw new JsonSyntaxException("Error in parseArray(): ',' or ']' is missing ");
            else if(chCheck(cs.peek())=="nothing") 
            	throw new JsonSyntaxException("Error in parseArray(): illegal char after ','");
        }
        return arr;
    }
    
    public JsonValue parseValue() throws JsonSyntaxException
    {
        String choice;
        if (cs.hasNext())
        {
            choice = chCheck(cs.peek());
            switch (choice) 
            {
                case "ArrayStart":
                    return parseArray();
                case "StrStart":
                	return parseString();
                case "AssoArrayStart":
                	return parseObject();
                case "Num":
                	return parseNumber();
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
    	char ch;
    	cs.next(); // reading the '{'
    	
    	while(cs.hasNext())
    	{
    		/* Building the key */
    		if(cs.peek()!='"') throw new JsonSyntaxException("Error in parseObject(): key is not a String");
    		key = parseString().getS();
    		
    		/* Making sure value is ok, building it if yes */
    		ch = cs.next();
    		if(ch!=':') throw new JsonSyntaxException("Error in parseObject(): ':' is missing");
    		else if(chCheck(cs.peek())=="nothing")
    			throw new JsonSyntaxException("Error in parseObject(): illegal char after ':' ");
    		value = parseValue();
    		
    		jo.getO().put(key, value); // put (key:value) in JsonObject's map
    		
    		/* reading next char, should be ',' or '}' , with no additional chars, otherwise throws exception */
    		ch = cs.next(); 
    		if(ch=='}') break;
    		if(!cs.hasNext()) throw new JsonSyntaxException("Error in parseObject(): '}' is missing");
    		if(ch!=',') throw new JsonSyntaxException("Error in parseObject(): ',' or '}' is missing");
    		else if(chCheck(cs.peek())=="nothing")
    			throw new JsonSyntaxException("Error in parseObject(): illegal char after ',' ");
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
        if (ch == '{') {
            return "AssoArrayStart";
        }
        if (ch == '"') {
            return "StrStart";
        }
        return "nothing";
    }
    
    
    //parseNumber() helper functions
    public boolean numCheck(char ch){

        if (isDigit(ch) || isE(ch)|| isOp(ch)) return true;
        if (ch=='.') return true;
        return false;
    }
    public  boolean isOp(char cnNs){
        return (cnNs=='-' || cnNs=='+');
    }
    public boolean isE(char ch) { return (ch=='E' || ch=='e'); }

    //parseNumber() function, flags known and legit values
public JsonNumber parseNumber() throws JsonSyntaxException{
    int flagMinus=0, flagE=0, flagDot=0, flagPlus=1;;
    StringBuilder bild = new StringBuilder();
    char cnPs, cnNs =' ';
    cnPs=cs.next();

    if (cnPs=='-'){
        flagMinus++;
        bild.append(cnPs);
        if (cs.hasNext() && numCheck(cs.peek())){
            cnPs=cs.next();
        }
    }
    //main loop, reads chars and confirms Json number syntax, throws exception else
    while (cs.hasNext()){
        if(numCheck(cs.peek())){
            try {
                    cnNs=cs.next();
                    if (isOp(cnPs)) {
                        if ((isDigit(cnNs)&& isE(bild.charAt(bild.length()-1))))
                        {
                            if (cnPs=='-'){ if(++flagMinus>2) throw new JsonSyntaxException("notminus"); }
                            else if(++flagPlus>2) throw new JsonSyntaxException("notplus");
                            bild.append(cnPs);
                            cnPs=cnNs;
                            continue;
                        }
                        else throw new JsonSyntaxException("not+-");
                    }
                    if (isE(cnPs)) {
                        if (isDigit(cnNs) || isOp(cnNs)) {
                            if(++flagE>1) throw new JsonSyntaxException("notE");
                            bild.append(cnPs);
                            cnPs=cnNs;
                            continue;
                        }
                        else throw new JsonSyntaxException("noE");
                    }
                    if (cnPs=='.'){
                        if (isDigit(cnNs)) {
                            if(++flagDot>1) throw new JsonSyntaxException("notdot");
                            bild.append(cnPs);
                            cnPs=cnNs;
                            continue;
                        }
                        else throw new JsonSyntaxException("notdot");
                    }
                    if (isDigit(cnPs)){
                        bild.append(cnPs);
                        cnPs=cnNs;
                        continue;
                    }
                 }
            catch (JsonSyntaxException e) { e.printStackTrace(); throw new JsonSyntaxException("number not legal"); }
        }

        else{
            if (isDigit(cnNs)){
                bild.append(cnNs);
                break;
            }
        else throw new JsonSyntaxException("end bad");}

    }
    Number temp;
    if ((flagE+flagDot) != 0) { temp = Double.parseDouble(bild.toString()); }
    else { temp = Integer.parseInt(bild.toString()); }

    return new JsonNumber(temp);
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
