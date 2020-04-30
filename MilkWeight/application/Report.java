/**
 * Report.java created by zkeith on personal laptop in MilkWeight
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
 * List Collaborators:
 * Zander Keith,   zkeith@wisc.edu,   LEC001
 * Daniel Levy,    dslevy2@wisc.edu,  LEC001
 * Matthew Holmes, mrholmes@wisc.edu, LEC001
 * Solly Parenti,  sparenti@wisc.edu, LEC001
 * Hyejin Yeon,    hyeon2@wisc.edu,   LEC001
 * 
 * Other Credits: describe other source (websites or people)
 * 
 * Known Bugs: describe known unresolved bugs here
 */
package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

/**
 * Report - Does all more involved analysis required for project to run such as
 * parsing CSV files and calculating milk percentages
 *
 */
public class Report {

	/**
	 * Takes in source file's name and adds data to all farms in farmList
	 * 
	 * @param sourceFile the file we are importing data from
	 * @param farmList   list of farms to add data to
	 * @return altered farms with new data added
	 * @throws Exception if encounters problem while parsing file
	 */
	public static ArrayList<Farm> parseFile(String sourceFile, ArrayList<Farm> farmList)
			throws Exception {
		int counter = 1; // Counter used for finding where index is out of bounds
		try {
			// Create new file reader for current file
			FileReader fr = new FileReader(sourceFile);
			BufferedReader buff = new BufferedReader(fr);
			String fileLine = buff.readLine(); // Skip first entry of CSV
			// String array for splitting up entire line of file
			String[] CSVLine;
			// String array for splitting up date of file
			String[] dateLine;

			boolean match; // one way flag for finding farm while parsing
			// Keep going until reach end of file
			while ((fileLine = buff.readLine()) != null) {
				counter++; // increment counter
				match = false; // set found farm match to false
				CSVLine = fileLine.split(","); // split CSV by date, ID, weight
				dateLine = CSVLine[0].split("-"); // split date by '-'
				// Find farm with matching ID
				for (Farm farm : farmList) {
					// see if second line entry (ID) matches farm we're looking for
					if (farm.getFarmID().equals(CSVLine[1])) {
						match = true;
						// found match, input data into it
						farm.addInput(Integer.parseInt(dateLine[0]),
								Integer.parseInt(dateLine[1]), Integer.parseInt(dateLine[2]),
								Integer.parseInt(CSVLine[2]));
						break;
					}
				}
				// no farm with matching ID created yet
				if (!match) {
					// Create new farm with new ID
					Farm newFarm = new Farm(CSVLine[1]);
					// put data into it
					newFarm.addInput(Integer.parseInt(dateLine[0]), Integer.parseInt(dateLine[1]),
							Integer.parseInt(dateLine[2]), Integer.parseInt(CSVLine[2]));
					// place farm in farmList
					farmList.add(newFarm);
				}

			}
			fr.close(); // close file
			buff.close(); // Close buffered reader
		} catch (FileNotFoundException e) {
			// Throw FileNotFoundException to wherever this was called to be handled
			System.out.println("File not found");
			throw new FileNotFoundException();
		} catch (NumberFormatException e) {
			// Get information about what caused format exception and throw that to
			// wherever this was called
			StringWriter errors = new StringWriter();
			String line1 = errors.toString().split(System.lineSeparator())[0];
			line1 = line1.split(":")[2];
			throw new NumberFormatException(line1);
		} catch (ArrayIndexOutOfBoundsException e) {
			// Give information about what index caused error
			throw new ArrayIndexOutOfBoundsException(Integer.toString(counter));
		} catch (Exception e) {
			// Something unusual happened, just throw regular exception
			System.out.println("Unexpected Other Exception");
			throw new Exception();

		}
		// return updated farmList
		return farmList;
	}

	// Month enum with all months included
	enum Month {
		ALL, January, February, March, April, May, June, July, August, September, October,
		November, December;

	}

	/**
	 * FARM REPORT Requirement: Prompt user for a farm id and year (or use all
	 * available data) Then, display the total milk weight and percent of the total
	 * of all farm for each month. Sort, the list by month number 1-12, show total
	 * weight, then that farm's percent of the total milk received for each month.
	 * 
	 * @param farmID farmID
	 * @param year   12 means all
	 * @param month  12 means all
	 * @return ArrayList with index 0 = Month, 1 = Total Weight, and 2 = Percentage.
	 * @return null if farmID is not in the list of farms.
	 * @return null if year == -1 and month !=-1 because that doesn't make sense...
	 * @throws Exception
	 */
	protected static ArrayList<String> farmReport(Farm farmID, int year, String monthString)
			throws Exception {
		double total = 0.0; // running total of weight
		double percent = 0.0; // percent weight
		// check that farmID is valid
		if (farmID == null) {
			System.out.print("doesn't look like there is any farm under this name...");
			throw new Exception("farmID is null");
		}
		int month = -1; // start month enum at a non-month value
		// iterate through all months until find one that matches
		for (Month s : Month.values()) {
			if (s.toString().equals(monthString)) {
				month = s.ordinal();
			}

		}
		// if user selected ALL then 
		if (monthString.equals("ALL")) {
			month = -1;
		}
		if (year == -1 && month != -1) {
			ArrayList<String> data = new ArrayList<String>();
			for (int i = 0; i < Main.farms.size(); i++) {
				for (int j = 0; j < Main.farms.get(i).getYearIntList().size(); j++) {
					total = total + (double) Main.farms.get(i).getTotalWeightMonth(
							Main.farms.get(i).getYearIntList().get(j), month);
				}
			}
			double farmShare = 0.0;
			for (int j = 0; j < farmID.getYearIntList().size(); j++) {
				farmShare = farmShare + ((double) farmID
						.getTotalWeightMonth(farmID.getYearIntList().get(j), month));
			}

			percent = farmShare / (total) * 100;
			if (Double.isNaN(percent)) {
				percent = 0;
			}
			data.add(monthString); // month
			data.add(Double.toString(farmShare)); // total weight for month
			data.add(String.format("%.2f", percent) + " %"); // percent for month
			data.add(Double.toString(percent));
			return data;

		}

		if (!Main.farms.contains(farmID))
			return null;

		ArrayList<String> data = new ArrayList<String>();
		if (year == -1) {
			for (int i = 0; i < Main.farms.size(); i++) {
				total = total + (double) Main.farms.get(i).getTotalWeightAll();
			}
			percent = ((double) farmID.getTotalWeightAll()) / (total) * 100;
			data.add(farmID.getFarmID() + " Report from All Available Data");
			data.add("Total Weight Sold from All Available Data from All Farms: "
					+ Double.toString(total) + " lb");
			data.add("Percent for " + farmID.getFarmID() + " : " + String.format("%.2f", percent)
					+ " %");
			return data;
		} else {
			for (int i = 0; i < Main.farms.size(); i++) {
				total = total + (double) Main.farms.get(i).getTotalWeightMonth(year, month);
			}
			percent = ((double) farmID.getTotalWeightMonth(year, month)) / (total) * 100;
			if (Double.isNaN(percent)) {
				percent = 0;
			}
			data.add(monthString); // month
			data.add(Integer.toString(farmID.getTotalWeightMonth(year, month))); // total weight
																					// for month
			data.add(String.format("%.2f", percent) + " %"); // percent for month
			data.add(Double.toString(percent));
			return data;

		}
	}

	/**
	 * ANNUAL REPORT Requirement: Prompt user for a year Then,
	 * 
	 * @param farmID farmID
	 * @param year   year for data we're collecting
	 * @return ArrayList with index 0 = FarmID, 1 = Total Weight, and 2 =
	 *         Percentage.
	 * @return null if farmID is not in the list of farms.
	 * @throws Exception
	 */
	protected static ArrayList<String> annualReport(Farm farmID, int year) throws Exception {
		ArrayList<String> data = new ArrayList<String>();
		double total = 0.0;
		double percent = 0.0;

		for (int i = 0; i < Main.farms.size(); i++) {
			total = total + (double) Main.farms.get(i).getTotalWeightYear(year);
		}
		percent = ((double) farmID.getTotalWeightYear(year)) / (total) * 100;
		if (Double.isNaN(percent)) {
			percent = 0;
		}
		data.add(farmID.getFarmID()); // farm ID
		data.add(Integer.toString(farmID.getTotalWeightYear(year))); // total weight for year
		data.add(String.format("%.2f", percent) + " %"); // percent for year
		return data;
	}

	/**
	 * ANNUAL REPORT Requirement: Prompt user for a year and a month Then,
	 * 
	 * @param farmID farmID
	 * @param year   year for data we're collecting
	 * @param month  the name of the month (as a string) for the data we're
	 *               collecting
	 * @return ArrayList with index 0 = FarmID, 1 = Total Weight, and 2 =
	 *         Percentage.
	 * @return null if farmID is not in the list of farms.
	 * @throws Exception
	 */
	protected static ArrayList<String> monthlyReport(Farm farmID, int year, String month)
			throws Exception {
		ArrayList<String> data = new ArrayList<String>();
		double total = 0.0;
		double percent = 0.0;

		for (int i = 0; i < Main.farms.size(); i++) {
			total = total + (double) Main.farms.get(i).getTotalWeightMonth(year,
					Farm.monthStringToInt(month));
		}
		percent = ((double) farmID.getTotalWeightMonth(year, Farm.monthStringToInt(month)))
				/ (total) * 100;
		if (Double.isNaN(percent)) {
			percent = 0;
		}
		data.add(farmID.getFarmID()); // farm ID
		data.add(
				Integer.toString(farmID.getTotalWeightMonth(year, Farm.monthStringToInt(month)))); // total
																									// weight
																									// for
																									// month
		data.add(String.format("%.2f", percent) + " %"); // percent for month
		return data;
	}

	// Generates a report for a given range of dates. I'm going to assume that the
	// user
	// enters a valid date range
	protected static ArrayList<String> rangeReport(Farm farmID, int startYear, String startMonth,
			int startDay, int endYear, String endMonth, int endDay) throws Exception {
		ArrayList<String> data = new ArrayList<String>();
		double total = 0.0;
		double percent = 0.0;

		for (int i = 0; i < Main.farms.size(); i++) {
			total = total + (double) Main.farms.get(i).getTotalWeightRange(startYear,
					Farm.monthStringToInt(startMonth), startDay, endYear,
					Farm.monthStringToInt(endMonth), endDay);
		}
		percent = ((double) farmID.getTotalWeightRange(startYear,
				Farm.monthStringToInt(startMonth), startDay, endYear,
				Farm.monthStringToInt(endMonth), endDay)) / (total) * 100;
		if (Double.isNaN(percent)) {
			percent = 0;
		}
		data.add(farmID.getFarmID()); // farm ID
		data.add(Integer
				.toString(farmID.getTotalWeightRange(startYear, Farm.monthStringToInt(startMonth),
						startDay, endYear, Farm.monthStringToInt(endMonth), endDay))); // total
																						// weight
																						// for
																						// range
		data.add(String.format("%.2f", percent) + " %"); // percent for range
		return data;
	}

	/**
	 * EDITED I think this function is still useful but shouldn't just be relegated
	 * to farm report These stats are probably good to print for all reports and the
	 * labels are there anyway Just shouldn't be made for only farm report
	 * 
	 * 
	 * @param farmID farmID
	 * @param year   12 means all
	 * @param month  12 means all
	 * @return ArrayList with index 0 = Farm ID, 1 = Total Weight, and 2 =
	 *         Percentage.
	 * @return null if farmID is not in the list of farms.
	 * @return null if year == -1 and month !=-1 because that doesn't make sense...
	 * @throws Exception
	 */
	protected static ArrayList<String> generalReport(Farm farmID, int year, String monthString)
			throws Exception {
		if (farmID == null) {
			System.out.print("doesn't look like there is any farm under this name...");
			throw new Exception("farmID is null");
		}
		int month = -1;
		for (Month s : Month.values()) {
			if (s.toString().equals(monthString)) {
				month = s.ordinal();
			}

		}
		if (monthString.equals("ALL")) {
			month = -1;
		}
		if (year == -1 && month != -1)
			return null;
		if (!Main.farms.contains(farmID))
			return null;
		double total = 0.0;
		double percent = 0.0;
		ArrayList<String> data = new ArrayList<String>();
		if (year == -1 && month == -1) {
			for (int i = 0; i < Main.farms.size(); i++) {
				total = total + (double) Main.farms.get(i).getTotalWeightAll();
			}
			percent = ((double) farmID.getTotalWeightAll()) / (total) * 100;
			if (Double.isNaN(percent)) {
				percent = 0;
			}
			data.add(farmID.getFarmID() + " Report from All Available Data");
			data.add("Total Weight Sold from All Available Data from All Farms: "
					+ Double.toString(total) + " lb");
			data.add("Percent for " + farmID.getFarmID() + " : " + String.format("%.2f", percent)
					+ " %");
			return data;
		} else if (month == -1) {
			for (int i = 0; i < Main.farms.size(); i++) {
				total = total + (double) Main.farms.get(i).getTotalWeightYear(year);
			}

			percent = ((double) farmID.getTotalWeightYear(year)) / (total) * 100;
			if (Double.isNaN(percent)) {
				percent = 0;
			}
			data.add(farmID.getFarmID() + " Report for " + Integer.toString(year));
			data.add("Total Weight Sold for Year " + Integer.toString(year) + " from All Farms: "
					+ Double.toString(total) + " lb");
			data.add("Percent for " + farmID.getFarmID() + " : " + String.format("%.2f", percent)
					+ " %");
			return data;
		} else {
			for (int i = 0; i < Main.farms.size(); i++) {
				total = total + (double) Main.farms.get(i).getTotalWeightMonth(year, month);
			}

			percent = ((double) farmID.getTotalWeightMonth(year, month)) / (total) * 100;
			if (Double.isNaN(percent)) {
				percent = 0;
			}
			data.add(farmID.getFarmID() + " Report for " + Month.values()[month] + ", "
					+ Integer.toString(year));
			data.add("Total Weight Sold for " + monthString + ", " + Integer.toString(year)
					+ " from ALL Farms : " + Double.toString(total) + " lb");
			data.add("Percent for " + farmID.getFarmID() + " : " + String.format("%.2f", percent)
					+ " %");
			return data;

		}
	}

	/*
	 * main method just to see if it works comment this out for the Main.java
	 */
	public static void main(String[] args) throws Exception {
		/*
		 * ArrayList<Farm> farms = new ArrayList<Farm>();
		 * Report.parseFile("./csv/small/2019-1.csv", farms);
		 * Report.parseFile("./csv/small/2019-2.csv", farms); Main.farms = farms;
		 * System.out.println(Report.farmReport(farms.get(1), -1, "ALL"));
		 * System.out.println(Report.farmReport(farms.get(2), 2019, "January"));
		 * System.out.println(Report.farmReport(farms.get(2), 2019, "ALL"));
		 */

	}

}
