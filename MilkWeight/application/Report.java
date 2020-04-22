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
	//TODO: use Enume for month name print. 

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
	 * @throws Exception 
	 */
	public static ArrayList<Farm> parseFile(String sourceFile, ArrayList<Farm> farmList) throws Exception {
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
			throw new Exception();
		}
		return farmList;
	}

	/**
	 * Get all farm ID's from a specified source file. 
	 * TODO actual exception
	 * handling
	 * 
	 * @param sourceFile
	 * @return
	 */
	private ArrayList<String> getFarmsInSource(String sourceFile) {
		ArrayList<String> farmIDs = new ArrayList<String>();

		return null;
	}
	
	/**
	 * FARM REPORT Requirement:
	 * Prompt user for a farm id and year (or use all available data)
	 * Then, display the total milk weight and percent of the total of 
	 * all farm for each month. 
	 * Sort, the list by month number 1-12, show total weight, 
	 * then that farm's percent of the total milk received for each month.
	 * @param farmID farmID
	 * @param year -1 means all
	 * @param month -1 means all
	 * @return ArrayList with index 0 = Farm ID, 1 = Total Weight, and 2 = Percentage.  
	 * @return null if farmID is not in the list of farms. 
	 * @return null if year == -1 and month !=-1 because that doesn't make sense... 
	 */
	protected static ArrayList<String> farmReport(Farm farmID, int year, int month){
		if(year == -1 && month !=-1) return null;
		if(!Main.farms.contains(farmID)) return null;
		double total = 0.0;
		double percent = 0.0;
		ArrayList<String> data = new ArrayList<String>();
		if (year == -1 && month == -1) {
			for (int i = 0 ; i < Main.farms.size() ; i ++ ) {
				total = total + (double) Main.farms.get(i).getTotalWeightAll();
			}
			percent = ((double) farmID.getTotalWeightAll())/(total)*100;
			data.add(farmID.getFarmID() + " Report from All Availble Data");
			data.add("Total Weight Sold: " + Double.toString(total)+" lb");
			data.add("Percent : " + String.format("%.2f", percent) + " %");
			return data;			
		}
		else if (month == -1) {
			for (int i = 0 ; i < Main.farms.size() ; i ++ ) {
				total = total + (double) Main.farms.get(i).getTotalWeightYear(year);
			}
			
			percent = ((double) farmID.getTotalWeightYear(year))/(total)*100;
			data.add(farmID.getFarmID() + " Report for " + Integer.toString(year));
			data.add("Total Weight Sold: " + Double.toString(total)+" lb");
			data.add("Percent : " + String.format("%.2f", percent) + " %");
			return data;			
		}
		else {
			for (int i = 0 ; i < Main.farms.size() ; i ++ ) {
				total = total + (double) Main.farms.get(i).getTotalWeightMonth(year, month);
			}
			
			percent = ((double) farmID.getTotalWeightMonth(year,month))/(total)*100;
			data.add(farmID.getFarmID() + " Report for " + Integer.toBinaryString(month)+", "+Integer.toString(year));
			data.add("Total Weight Sold: " + Double.toString(total)+" lb");
			data.add("Percent : " + String.format("%.2f", percent) + " %");
			return data;
			
		}
	}
	/*
	 * main method just to see if it works
	 * comment this out for the Main.java
	 */
	public static void main(String[] args) throws Exception {
		ArrayList<Farm> farms = new ArrayList<Farm>();
		Report.parseFile("./csv/small/2019-1.csv", farms);
		Report.parseFile("./csv/small/2019-2.csv", farms);
		Main.farms=farms;
		System.out.println(Report.farmReport(farms.get(2), -1, -1));
		System.out.println(Report.farmReport(farms.get(2), 2019, 1));
		System.out.println(Report.farmReport(farms.get(2), 2019, -1));
		
	}
	
	

}
