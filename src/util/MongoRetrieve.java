package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class MongoRetrieve {


	public static void main( String args[] ) {

		try{   

			// To connect to mongodb server
			MongoClient mongoClient = new MongoClient( "localhost" , 27017 );

			// Now connect to your databases
			DB db = mongoClient.getDB( "testDB" );
			System.out.println("Connect to database successfully");

			// boolean auth = db.authenticate(myUserName, myPassword);
			//   System.out.println("Authentication: "+auth);     
			for(int ii=0;ii<26;ii++){
				File file = new File((char)('a'+ii)+".txt");	
				System.out.println(file.getAbsolutePath());
				System.out.println(file.getPath());
				
				if (!file.exists()) {
					file.createNewFile();
				}
			//	FileWriter fw = new FileWriter(file.getAbsoluteFile());
				Writer out = new BufferedWriter(new OutputStreamWriter(
					    new FileOutputStream(file.getAbsoluteFile()), "UTF-8"));
				
			//	BufferedWriter bw = new BufferedWriter(fw);
				
				DBCollection coll = db.getCollection("words_started_with_"+(char)('a'+ii));
			//	System.out.println("Collection mycol selected successfully");

				DBCursor cursor = coll.find();
				int i = 1;

				while (cursor.hasNext()) { 
					//System.out.println("Inserted Document: "+i); 
					out.write(cursor.next().toString()+"\n");
					//System.out.println(cursor.next()); 
					i++;
				}
			}

		}catch(Exception e){
			e.getStackTrace();
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
	}

}
