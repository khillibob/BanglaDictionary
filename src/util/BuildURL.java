package util;

public class BuildURL {

	
	public static String urlWord(String s){
		return s.replaceAll(" ", "%20");
	}
	
	public static String buildPageURL(char c,int page){
		String s = "http://www.english-bangla.com/browse/index/";
		if(page==1){
			s= s + Character.toString(c);
		}
		else{
			s= s + Character.toString(c) +"/"+page;
			
		}
		return s;
	}
	
	public static String buildWordURL(String word){
		return "http://www.english-bangla.com/dictionary/"+urlWord(word);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s= "a bit much";
		System.out.println(buildWordURL(s));
	}

}
