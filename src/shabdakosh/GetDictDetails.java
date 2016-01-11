package shabdakosh;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import util.BuildURL;
import util.ParsePage;
import util.RetrieveWord;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class GetDictDetails {

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
		DBCollection coll = db.createCollection(collection, null);
		coll.ensureIndex(new BasicDBObject("Word", 1), new BasicDBObject("unique", true));

		coll.insert(createDocObject(word,meaning));
		return true;
	}

	public static ArrayList<String> retrieveWords(String wordURL){

		ArrayList<String> list = new ArrayList<String>();
		Document doc;
		try {
			doc = Jsoup.connect(wordURL).get();
			Element elm= doc.getElementById("ehresults");
			Elements sub_page = elm.select("div.row");
			Element firstRow = sub_page.first();
			Element firstMD7 = firstRow.select("div.col-md-7").first();
			if(firstMD7==null){
				firstMD7 = firstRow.select("div.col-md-8").first();

			}
			if(firstMD7==null){
				firstMD7 = firstRow.select("div.col-md-6").first();

			}
			
			
			Element firstLeft = firstMD7.getElementById("left");
			Element meaning = firstLeft.select("div.row").first();
			Elements meanings = meaning.getElementsByTag("a");

			for(Element e : meanings){
				list.add(e.text());
			}



		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(wordURL);
			e.printStackTrace();
		}


		return list;
	}

	public static HashMap<String,String> getWordsList(String pageUrl){
		String className="col-lg-8";
		Document doc;
		HashMap<String,String> map = new HashMap<String,String>();
		try {
			doc = Jsoup.connect(pageUrl).get();
			//	System.out.println(doc);

			String sub_page = doc.select("div."+className).html();
			//System.out.println(doc.select("div#cat_page").html());
			Document sub_doc = Jsoup.parse(sub_page);

			Elements es=sub_doc.getElementsByTag("a");

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


	public static String constructWordMeaning(ArrayList<String> list){
		String str = "";
		for(String s:list){
			str=str+s+",";
		}
		return str.substring(0,str.length()-1);

	}

	public static String buildWordURL(String str){
		return "http://www.shabdkosh.com"+str;
	}
	public static String buildPageURL(char c,int page){
		return "http://www.shabdkosh.com/bn/browse/"+Character.toUpperCase(c)+"/"+page;
	}

	public static String buildPageContent(String word,String meaning){
		String str = "{ \"Word\" : \"";
		str = str+word+"\" , \"meaning\" : \""+meaning+"\"}";
		/*
		{ "Word" : "abb" , "meaning" : "পড়েন ; কাপড়ের পড়েন"}*/

		return str;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String str = "http://www.shabdkosh.com/bn/translate/Chandra/Chandra-meaning-in-Bengali-English";
		String s1 = "http://www.shabdkosh.com/bn/translate/certainly/certainly-meaning-in-Bengali-English";
		String s2 = "http://www.shabdkosh.com/bn/translate/caused/caused-meaning-in-Bengali-English";
		String s3 = "http://www.shabdkosh.com/bn/translate/causation/causation-meaning-in-Bengali-English";
		//		System.out.println(retrieveWord(str, ""));
		//	System.out.println(constructWordMeaning(retrieveWords(s1)));
		//	System.out.println(retrieveWord(s2, ""));
		//System.out.println(retrieveWord(s3, ""));



		int pages[]={10,8,14,9,7,8,6,6,9,2,2,6,8,4,4,
				12,1,7,15,8,5,3,4,1,1,1};

		//	HashMap<String,String> map = getWordsList("http://www.shabdkosh.com/bn/browse/C/3");
		for(int ii=0;ii<1;ii++){
			File file = new File(System.getProperty("user.dir")+File.separator+(char)('a'+ii)+".txt");
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

			System.out.println(file.getAbsolutePath());
			System.out.println(file.getPath());

			if (!file.exists()) {
				try {
					file.createNewFile();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			try {
				/*Writer out = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(file.getAbsoluteFile()), "UTF-8"));
				//out.write("\n");
				 * 
*/				/*
				* Page 2 for letter a is incomplete	
				* 5 incomplete
				* 9-10
				* */
				for(int i = 7;i<10;i++){
					System.out.println("Page No: "+i+"\n\n");
					String pageUrl = buildPageURL((char)('a'+ii), i);
					HashMap<String, String> wordList= getWordsList(pageUrl);
					Set<String> words = wordList.keySet();
					for(String s:words){
						/*out = new BufferedWriter(new OutputStreamWriter(
								new FileOutputStream(file.getAbsoluteFile()), "UTF-8"));*/
					//	System.out.println(s);
						String meaning = constructWordMeaning(retrieveWords(buildWordURL(wordList.get(s))));
				//		System.out.println(s+" : "+meaning);
					//	String pageLine = buildPageContent(s, meaning);
						storeInMongo(s, meaning, Character.toString((char)('a'+ii)));
						/*out.write(pageLine+"\n");
						out.close();*/
						Thread.sleep(2000);
					}


				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}



			//	FileWriter fw = new FileWriter(file.getAbsoluteFile());

		}
	}


	/*
	 * 
				try {
					Writer out = new BufferedWriter(new OutputStreamWriter(
							new FileOutputStream(file.getAbsoluteFile()), "UTF-8"));
					out.write("\n");

					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	 */
}
