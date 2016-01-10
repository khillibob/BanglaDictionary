package util;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class StoreInMongo {

	public static DB getMongoDatabase(String host,int port,String database){
		// To connect to mongodb server
		MongoClient mongoClient =null;
		DB db = null;
		try {
			mongoClient= new MongoClient( host , port );

			db = mongoClient.getDB(database);


		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return db;

	}


	public static BasicDBObject createDocObject(ArrayList<String> list){

		int n = list.size();
		BasicDBObject doc = new BasicDBObject("Word", list.get(0));
		for(int  i = 1;i<n;i++){
			doc.append("meaning", list.get(i));
		}
		return doc;


	}

	public static void runStore(){

		int pages[] = {36,29,54,33,24,24,17,19,26,4,3,17,27,10,13,
				43,2,30,57,28,13,8,13,1,1,1};

		DB db = getMongoDatabase("localhost", 27017, "testDB");
		System.out.println("Connect to database successfully");
		// boolean auth = db.authenticate(myUserName, myPassword);
		// System.out.println("Authentication: "+auth);


		for(int i =0;i<26;i++){
			DBCollection coll = db.createCollection("words_started_with_"+(char)('a'+i), null);
			for(int j = 1;j<=pages[i];j++){
				String url = BuildURL.buildPageURL((char)('a'+i), j);
				HashMap<String ,String > map = ParsePage.parsePage(url);
				Set<String> set = map.keySet();
				for(String s : set){
					try{

						// Now connect to your databases

						//System.out.println("Collection created successfully");
						ArrayList<String> meaning = RetrieveWord.getMeaning(map.get(s));
						coll.insert(createDocObject(meaning));
						//System.out.println("Document inserted successfully");

					}catch(Exception e){


						System.out.println(e.getMessage());
					}
				}
				System.out.println("Page "+j+" Completed");
			}
			System.out.println("Completed "+(char)('a'+i));
		}

	}
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		runStore();
	}*/

}
