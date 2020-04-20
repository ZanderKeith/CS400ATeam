/**
 * Main.java created by zkeith on personal laptop in MilkWeight
 *
 * Author: 		Zander Keith (zkeith@wisc.edu)
 * Date: 		Apr 20, 2020
 * 
 * Course: 		CS400
 * Semester: 	Spring 2020
 * Lecture: 	001
 * 
 * IDE: 		Eclipse IDE for Java Developers
 * Version: 	2020-03
 * 
 * OS:			Windows 10
 * 
 * List Collaborators: Name, email@wisc.edu, lecture number
 * 
 * Other Credits: describe other source (websites or people)
 * 
 * Known Bugs: describe known unresolved bugs here
 */
package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Report - Does all more involved analysis required for project to run such as
 * parsing CSV files and calculating milk percentages
 * 
 * @author TODO
 *
 */
public class Report {

	/**
	 * Takes in source file's name and adds data to all farms in farmList
	 * 
	 * @param sourceFile
	 * @param farmID
	 * @param milkData
	 * @return altered trieTree with new data added
	 */
	public static ArrayList<Farm> parseFile(String sourceFile, ArrayList<Farm> farmList) {
		return farmList;
	}
	
	/**
	 * Get all farm ID's from a specified source file.
	 * TODO actual exception handling
	 * @param sourceFile
	 * @return
	 */
	private ArrayList<String> getFarmsInSource(String sourceFile) {
		ArrayList<String> farmIDs = new ArrayList<String>();
		try {
			FileReader fr = new FileReader(sourceFile);
			BufferedReader buff = new BufferedReader(fr);
			String fileLine = buff.readLine(); // Skip first entry of CSV
			while((fileLine = buff.readLine()) != null) {
				farmIDs.add(fileLine.split(",")[1]); // Second item in each line is farm ID
			}
		} catch (Exception e) {
			System.out.println("UNEXPECTED EXCEPTION PARSING FILE");
		}
		
		return null;
	}
	
	

}
