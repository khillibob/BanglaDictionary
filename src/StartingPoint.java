import java.util.ArrayList;

import UI.DictScreen;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;


public class StartingPoint {
/*
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			
	         // To connect to mongodb server
	         MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
				
	         // Now connect to your databases
	         DB db = mongoClient.getDB("myDB");
	         System.out.println("Connect to database successfully");
	        // boolean auth = db.authenticate(myUserName, myPassword);
	        // System.out.println("Authentication: "+auth);
	         DBCollection coll = db.createCollection("mycol", null);
	         System.out.println("Collection created successfully");
	         BasicDBObject doc = new BasicDBObject("title", "MongoDB").
	                 append("description", "database").
	                 append("likes", 100).
	                 append("url", "random").
	                 append("by", "demo");
	     				
	              coll.insert(doc);
	              System.out.println("Document inserted successfully");
				
	      }catch(Exception e){
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      }
	}
*/
	public static void main(String[] args) {
		//System.out.println(System.getProperty("StartingPoint.java"));
		System.out.println(DictScreen.class.getClassLoader().getResource("src"));
		System.out.println(DictScreen.class.getClassLoader().getResource("Bangla.ttf").getPath());
		System.out.println(DictScreen.class.getClassLoader().getResource("src/"));
		
		DictScreen.dictUI();
		
	}
	
	public static BasicDBObject createDocObject(ArrayList<String> list){
		
		int n = list.size();
		BasicDBObject doc = new BasicDBObject("Word", list.get(0));
		for(int  i = 1;i<n;i++){
			doc.append("meaning"+i, list.get(i));
		}
		return doc;
		
		
	}
	
}
