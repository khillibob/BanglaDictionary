package util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParsePage {

	public static boolean isAlpha(char c){
		if((c>='a'&&c<='z')||(c>='A'&&c<='Z')||(c>='0'&&c<='9'))return true;
		return false;
	}
	public static HashMap<String,String>  parsePage(String url){

		ArrayList<String> list = new ArrayList<String>();

		HashMap<String,String> map = new HashMap<String,String>();

		try {

			Document doc = Jsoup.connect(url).get();

			//	Elements els = doc.select("div#cat_page");
			String sub_page = doc.select("div#cat_page").html();
			//System.out.println(doc.select("div#cat_page").html());
			Document sub_doc = Jsoup.parse(sub_page);
			Elements list_items = sub_doc.getElementsByTag("li");
			//System.out.println("list items:"+list_items.size());
			for(Element e:list_items){
				
				String word = e.text();
				String wordURL =  e.child(0).attr("href");
				char[] wordletter = word.toCharArray();
				if(!isAlpha(wordletter[wordletter.length-1])){
					word = word.substring(0,wordletter.length-1);
				}
			
				map.put(word,wordURL);
	//			System.out.println();
//				System.out.println(e.text());

				//	System.out.println((String)e.attr("href"));
				//e.att
			}



		} catch (IOException e) {
			// TODO Auto-generated catch block

			System.out.println(e.getMessage());
		}
		return map;


	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String url = "http://www.english-bangla.com/browse/index/a";
		HashMap<String,String>  map = parsePage(url);
		Set<String> set = map.keySet();
		for(String s : set){
		//	System.out.println(s+" "/*+map.get(s)*/);
		}
		System.out.println(set.size());
	}

}
