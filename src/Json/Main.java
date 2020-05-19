package Json;

import java.io.FileNotFoundException;

public class Main {
            //new code :
    
    public static void main(String[] args) {

		JsonBuilder k = null;
		
		try 
		{
			k = new JsonBuilder();
			System.out.println(k.getV());
		} 
		catch (FileNotFoundException | JsonSyntaxException e)  
		{
			e.printStackTrace();
		}
		
    	
    }
}
