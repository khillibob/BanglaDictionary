package util;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Test {

    public static void main(String[] args) throws Exception {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        
        BufferedReader br  = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        String s1 = br.readLine();
        System.out.println(new BigInteger(s).add(new BigInteger(s1)));
        System.out.println(new BigInteger(s).multiply(new BigInteger(s1)));
        
        
    }
}