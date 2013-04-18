package com.wos.game;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Parser {

	private static File 				fFile;
	private static ArrayList<String> 	line = new ArrayList<String>();
	private static int 					i = 0;
  
	public static void Init(){
		fFile = new File("Data/map/parser/1-1.txt"); 
		try {
			processLineByLine();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void processLineByLine() throws FileNotFoundException  {
		Scanner scanner = new Scanner(new FileReader(fFile));
		try {
			while ( scanner.hasNextLine() ){
				line.add(scanner.nextLine());
				i++;
			}
		}
		finally {
			scanner.close();
		}
	}
	
	public static ArrayList<String> getArray()
	{
		return line;
	}
} 
