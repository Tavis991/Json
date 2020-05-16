package Json;

import java.io.FileNotFoundException;

public class Main {
            //new code :
    
    public static void main(String[] args) {

		JsonBuilder k = null;
		
		try {
			k = new JsonBuilder();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	System.out.println(k.getV());
    }
}
