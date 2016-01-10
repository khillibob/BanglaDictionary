package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import UI.DictScreen;

public class ReadWord {

	/**
	 * @param args
	 */
	
	public static String getWord(String str){
		String path = System.getProperty("user.dir");
		
		String filePath = DictScreen.class.getClassLoader().getResource("Files/").getPath();
		String res = "কিছু পাচ্ছিনা ";
		str  = str.toLowerCase();
		if(str.length()<1)return "ঠিক করে টাইপ করুন ";
		String fileName = filePath +File.separator+ str.substring(0,1)+".txt";
	//	System.out.println(fileName);
		File file = new File(fileName);
		JSONParser parser = new JSONParser();
		//Object obj = parser.parse(new FileReader("c:\\test.json"));
		try(BufferedReader br = new BufferedReader(new FileReader(file))) {
		    for(String line; (line = br.readLine()) != null; ) {
		        // process the line.
		    	if(line.length()==0||line.charAt(line.length()-1)!='}')break;
		    	Object obj = parser.parse(line);
		    	JSONObject jsonObject = (JSONObject) obj;
		    	
		    	String name = (String) jsonObject.get("Word");
				//System.out.println(name);
				if(name.equals(str)){
				//	System.out.println(jsonObject.get("meaning"));
					res = jsonObject.get("meaning").toString();
					break;
				}
		    	
		    }
		    // line is not visible here.
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
		
	}
	
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(getWord("awe"));
	}*/

}
