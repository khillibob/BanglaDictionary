package bdword;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class GetBDWordDictDetails {


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


	public static BasicDBObject createDocObject(String word,String meaning){
		BasicDBObject doc = new BasicDBObject("Word", word);
		doc.append("meaning",meaning);

		return doc;


	}



	public static boolean storeInMongo(String word,String meaning,String collection){
		DB db = getMongoDatabase("localhost", 27017, "testDB");
		//System.out.println("Connect to database successfully");
		// boolean auth = db.authenticate(myUserName, myPassword);
		// System.out.println("Authentication: "+auth);
		
		try{
			DBCollection coll = db.createCollection(collection, null);
			coll.ensureIndex(new BasicDBObject("Word", 1), new BasicDBObject("unique", true));
			coll.insert(createDocObject(word,meaning));
			
		}catch(Exception e){
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			
			return true;
		}
		return true;
	}


	public static String retrieveWords(String wordURL){
		Document doc;
		String meaning = "";
		try {
			doc = Jsoup.connect(wordURL).get();
			Elements elms = doc.select("div.bs-callout");
			if(elms==null)return "";
			Element e = elms.get(1);
			if(e==null)return "";
			Element ee=e.getElementsByTag("b").first();
			if(ee==null)return "";
			Node eee= ee.nextSibling();
			if(eee==null)return "";
			meaning = eee.toString();
			//System.out.println(meaning.replace("=", "").replace(" ", ""));
			meaning = meaning.replace("=", "");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			return "";
			//e1.printStackTrace();
		}


		return meaning;

	}



	public static HashMap<String,String> getWordsList(String pageUrl){
		String className="bs-callout";
		Document doc;
		HashMap<String,String> map = new HashMap<String,String>();
		try {
			doc = Jsoup.connect(pageUrl).get();
			//	System.out.println(doc);

			String sub_page = doc.select("div."+className).html();
			if(sub_page==null)return map;
			//System.out.println(doc.select("div#cat_page").html());
			Document sub_doc = Jsoup.parse(sub_page);
			if(sub_doc==null)return map;

			Elements es1=sub_doc.getElementsByTag("table");
			if(es1==null)return map;
			Elements es = es1.first().getElementsByTag("a");
			if(es==null)return map;
			for(Element e : es){
				String word = e.text();
				String wordURL =  e.attr("href");
				map.put(word, wordURL);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	public static String buildWordsListPage(char c,int page){
		return "http://www.bdword.com/browse.php?lang=bengali&word="+Character.toString(c)+"&page="+page;
	}

	public static void main(String[] args) {

		int pages[]={15,
				10,
				22,
				12,
				9,
				9,
				6,
				7,
				9,
				1,
				1,
				7,
				11,
				4,
				5,
				20,
				1,
				11,
				20,
				8,
				4,
				3,
				4,
				0,
				0,
				0

		};
		/*
		 * e 9 to rest remaining
		 * h 5 to rest
		 */
		for(int ii=5
				;ii<26;ii++){
			System.out.println("Completed: "+(char)('a'+ii));
			for(int i = 0;i<=pages[ii];i++){
				System.out.println("Page No: "+i);
				String pageUrl = buildWordsListPage((char)('a'+ii), i);
				HashMap<String, String> wordList= getWordsList(pageUrl);
				Set<String> words = wordList.keySet();
				for(String s:words){
					//System.out.println(s);
					String meaning =retrieveWords(wordList.get(s));
					storeInMongo(s, meaning, Character.toString((char)('a'+ii)));

				}


			}

		}


	}



}
