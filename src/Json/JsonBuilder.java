package Json;

import static java.lang.Character.isDigit;
import java.lang.Double;
import java.lang.Integer;
import java.io.File;
import java.io.FileNotFoundException;

public class JsonBuilder implements JsonValue {
    private CharScanner cs;
    private JsonValue v;

    public JsonBuilder(String filename) throws FileNotFoundException {
    	cs = new CharScanner(new File(filename));

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
    //Json parsenumber helper functions
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
    
    public boolean numCheck(char ch){

        if (isDigit(ch) || isE(ch)|| isOp(ch)) return true;
        if (ch=='.') return true;
        return false;
    }
    public  boolean isOp(char cnNs){
        return (cnNs=='-' || cnNs=='+');
    }
    public boolean isE(char ch) { return (ch=='E' || ch=='e'); }

    //Json parsenumber function, flags known and legit values
public JsonNumber parseNumber() throws JsonSyntaxException{
    int flagMinus=0, flagE=0, flagDot=0, flagPlus=1;;
    StringBuilder bild = new StringBuilder();
    char cnPs, cnNs =' ';
    cnPs=cs.next();

    //check special initial conditions
    switch (cnPs) {
        case ('-'): {
            flagMinus++;
            bild.append(cnPs);
            if (cs.hasNext() && numCheck(cs.peek())) { cnPs = cs.next(); }
            break;
        }
        case ('0'): {
            if (cs.hasNext() && !(cs.peek() == '.')) {
                throw new JsonSyntaxException("Json whole numbers cannot begin with zero");
          }
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
            if (isDigit(cnNs) || cnNs=='.'){
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
