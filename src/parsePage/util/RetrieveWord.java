package parsePage.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RetrieveWord {
	
	
public static ArrayList<String> parsePage(String url){
		
		ArrayList<String> list = new ArrayList<String>();
		
		
		try {
			Document doc = Jsoup.connect(url).get();
			
		//	Elements els = doc.select("div#cat_page");
			String sub_page = doc.select("div#cat_page").html();
			//System.out.println(doc.select("div#cat_page").html());
			Document sub_doc = Jsoup.parse(sub_page);
			Elements list_items = sub_doc.getElementsByTag("li");
			for(Element e:list_items){
				
				list.add(e.text());
				
			//	System.out.println(e.text());
			}
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
		
	}
	
	
	
	public static ArrayList<String> getMeaning(String url){
		ArrayList<String > list = new ArrayList<String>();
		Document doc;
		
		try {
			doc = Jsoup.connect(url).get();
		//	System.out.println(doc);
			
			Elements elms1 = doc.getElementsByClass("stl3");
			list.add(elms1.first().text());
			
			Elements elms = doc.getElementsByClass("format1");
			System.out.println(elms.first().text());
			list.add(elms.first().text());
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return list;
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String url = "http://www.english-bangla.com/dictionary/";
		getMeaning(url);
		  
	}

}
