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

	public static void mainFunc(){

		try{   

			// To connect to mongodb server
			MongoClient mongoClient = new MongoClient( "localhost" , 27017 );

			// Now connect to your databases
			DB db = mongoClient.getDB( "testDB" );
			System.out.println("Connect to database successfully");

			

		}catch(Exception e){
			e.getStackTrace();
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
	}

/*
	public static void main( String args[] ) {
	}*/
}
