package Json;

import static java.lang.Character.compare;
import static java.lang.Character.isDigit;

public class JsonBuilder implements JsonValue {
    private CharScanner cs;
    private JsonValue v;

    public JsonBuilder() {
        v=parseValue();



    }

    public JsonString parseString() throws JsonSyntaxException{
        StringBuilder bild = new StringBuilder();
        JsonString Jstr = new JsonString();
        while (cs.hasNext()){
            while(chCheck(cs.peek())!="Str"){
                try {
                    bild.append(cs.next());
                    if(((Character)cs.peek()).equals('\')||((Character)cs.peek()).equals(' ')) {
                        if (cs.hasNext()){
                            cs.next();
                        }
                        else{
                            throw new JsonSyntaxException("kelet lo takin");
                        }
                    }
                catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                }



            }
        }
        return arr;
    }
    public JsonArray parseArray() throws JsonSyntaxException{

        JsonArray arr = new JsonArray();
        while (cs.hasNext()){
            while(chCheck(cs.peek())!="ArrayEnd"){
                try {
                    arr.getA().add(parseValue());
                    while(((Character)cs.peek()).equals(',')||((Character)cs.peek()).equals(' ')) {
                        if (cs.hasNext()){
                            cs.next();
                        }
                        else{
                            throw new JsonSyntaxException("kelet lo takin");
                        }
                    }

                    }
                 catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }


            }
        }
        return arr;
    }
    public JsonValue parseValue() throws JsonSyntaxException{
        char ch;
        if (this.cs.hasNext()) {
            ch = this.cs.peek();
            switch (chCheck(ch)) {
                case "ArrayStart":
                    return parseArray();
                case "Str":

                case "AssoArrayStart":

                case "Num":



            }
            ;

        }

    }

    public String chCheck(char ch) {

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

        return "Neutral";
    }
    @Override

    public JsonValue get(int i) {
        return null;
    }

    @Override
    public JsonValue get(String s) {
        return null;
    }
}
