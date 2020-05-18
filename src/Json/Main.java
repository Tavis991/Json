package Json;

import java.io.FileNotFoundException;

public class Main {
            //new code :
    
    public static void main(String[] args) {

		JsonBuilder k = null;
		if (args.length!=1){
			System.out.println("Program requires one input parameter : <file name/full path>");
			System.exit(1);
		}
		try {
			k = new JsonBuilder(args[0]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

    	System.out.println(k.getV());
    }
}
