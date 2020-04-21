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
	 * TEST DIRECTORY
	 * C:\Users\16125\eclipse-workspace\CS400ATeam\MilkWeight\csv\small\2019-1.csv
	 * TODO Gonna have to clean this up eventually, just getting it working for now
	 * 
	 * @param sourceFile
	 * @param farmList
	 * @return altered farms with new data added
	 */
	public static ArrayList<Farm> parseFile(String sourceFile, ArrayList<Farm> farmList) {
		try {
			FileReader fr = new FileReader(sourceFile);
			BufferedReader buff = new BufferedReader(fr);
			String fileLine = buff.readLine(); // Skip first entry of CSV
			String[] CSVLine;
			String[] dateLine;
			boolean match; // one way flag for while parsing
			// Keep going until reach end of file
			while ((fileLine = buff.readLine()) != null) {
				match = false;
				CSVLine = fileLine.split(",");
				dateLine = CSVLine[0].split("-");
				// Find farm with matching ID
				for (Farm farm : farmList) {
					if (farm.getFarmID().equals(CSVLine[1])) {
						match = true;
						farm.addInput(Integer.parseInt(dateLine[0]),
								Integer.parseInt(dateLine[1]), Integer.parseInt(dateLine[2]),
								Integer.parseInt(CSVLine[2]));
						break;
					}
				}
				// no farm with matching ID created yet
				if (!match) {
					Farm newFarm = new Farm(CSVLine[1]);
					newFarm.addInput(Integer.parseInt(dateLine[0]),
							Integer.parseInt(dateLine[1]), Integer.parseInt(dateLine[2]),
							Integer.parseInt(CSVLine[2]));
					farmList.add(newFarm);
				}

			}
		} catch (Exception e) {
			System.out.println("UNEXPECTED EXCEPTION PARSING FILE");
		}
		return farmList;
	}

	/**
	 * Get all farm ID's from a specified source file. TODO actual exception
	 * handling
	 * 
	 * @param sourceFile
	 * @return
	 */
	private ArrayList<String> getFarmsInSource(String sourceFile) {
		ArrayList<String> farmIDs = new ArrayList<String>();

		return null;
	}

}
