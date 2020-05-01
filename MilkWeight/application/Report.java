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
			e.printStackTrace(new PrintWriter(errors));
			String line1 = errors.toString().split(System.lineSeparator())[0];
			line1 = line1.split(":")[2];
			throw new NumberFormatException(line1);
		} catch (ArrayIndexOutOfBoundsException e) {
			// Give information about what index caused error
			throw new ArrayIndexOutOfBoundsException(Integer.toString(counter));
		} catch (Exception e) {
			// Something unusual happened, just throw regular exception
			// System.out.println("Unexpected Other Exception");
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
	 * Prepares arrayList of strings for all data required by Farm Report
	 * 
	 * @param farmID farmID
	 * @param year   -1 means all
	 * @param month  -1 means all
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
		// if user selected ALL then set month to -1
		if (monthString.equals("ALL")) {
			month = -1;
		}
		// if doing ALL years but not ALL months
		if (year == -1 && month != -1) {
			// initialize new data array
			ArrayList<String> data = new ArrayList<String>();
			// iterate through all farms
			for (int i = 0; i < Main.farms.size(); i++) {
				// iterate through all available years
				for (int j = 0; j < Main.farms.get(i).getYearIntList().size(); j++) {
					// increment total by given month
					total = total + (double) Main.farms.get(i).getTotalWeightMonth(
							Main.farms.get(i).getYearIntList().get(j), month);
				}
			}
			double farmShare = 0.0; // sum of how much milk farm produced in time range
			// iterate through all years for farm ID
			for (int j = 0; j < farmID.getYearIntList().size(); j++) {
				// Add up milk weight for month in year
				farmShare = farmShare + ((double) farmID
						.getTotalWeightMonth(farmID.getYearIntList().get(j), month));
			}
			// calculate percentage
			percent = farmShare / (total) * 100;
			// If no data available, total will be 0. In this case return 0 instead of NaN
			if (Double.isNaN(percent)) {
				percent = 0;
			}
			// Add calculated fields to data.
			data.add(monthString); // month
			data.add(Double.toString(farmShare)); // total weight for month
			data.add(String.format("%.2f", percent) + " %"); // percent for month
			data.add(Double.toString(percent));
			return data;
		}
		// If farm doesn't exist return null
		if (!Main.farms.contains(farmID))
			return null;

		// Check if going for all years and all months
		ArrayList<String> data = new ArrayList<String>(); // arrayList of data to be returned
		if (year == -1) {
			// iterate through all farms
			for (int i = 0; i < Main.farms.size(); i++) {
				// increment total by milk weight in farm
				total = total + (double) Main.farms.get(i).getTotalWeightAll();
			}
			// calculate statistics
			percent = ((double) farmID.getTotalWeightAll()) / (total) * 100;
			// add statistics to data
			data.add(farmID.getFarmID() + " Report from All Available Data");
			data.add("Total Weight Sold from All Available Data from All Farms: "
					+ Double.toString(total) + " lb");
			data.add("Percent for " + farmID.getFarmID() + " : " + String.format("%.2f", percent)
					+ " %");
			return data;
		} else {
			// not going for all years or all months, just take data from specific year and
			// month
			for (int i = 0; i < Main.farms.size(); i++) {
				total = total + (double) Main.farms.get(i).getTotalWeightMonth(year, month);
			}
			// calculate statistics
			percent = ((double) farmID.getTotalWeightMonth(year, month)) / (total) * 100;
			if (Double.isNaN(percent)) {
				percent = 0;
			}
			// Add statistics to data
			data.add(monthString); // month
			// total weight for month
			data.add(Integer.toString(farmID.getTotalWeightMonth(year, month)));
			data.add(String.format("%.2f", percent) + " %"); // percent for month
			data.add(Double.toString(percent));
			return data;

		}
	}

	/**
	 * ANNUAL REPORT Requirement: Prompt user for a year Then, display list of
	 * totals and percent of total by farm. List must be sorted by Farm ID
	 * 
	 * Prepares arrayList of strings for all data required by Annual Report
	 * 
	 * @param farmID farmID
	 * @param year   year for data we're collecting
	 * @return ArrayList with index 0 = FarmID, 1 = Total Weight, and 2 =
	 *         Percentage.
	 * @return null if farmID is not in the list of farms.
	 * @throws Exception if error encountered while collecting data
	 */
	protected static ArrayList<String> annualReport(Farm farmID, int year) throws Exception {
		// ArrayList of data to be returned
		ArrayList<String> data = new ArrayList<String>();
		double total = 0.0; // total of all weight for all farms in date range
		double percent = 0.0; // percent of weight for this farm

		// Go through all farms and sum up their weights for the year
		for (int i = 0; i < Main.farms.size(); i++) {
			total = total + (double) Main.farms.get(i).getTotalWeightYear(year);
		}
		// Calculate statistics
		percent = ((double) farmID.getTotalWeightYear(year)) / (total) * 100;
		if (Double.isNaN(percent)) {
			percent = 0;
		}
		// add statistics to data
		data.add(farmID.getFarmID()); // farm ID
		data.add(Integer.toString(farmID.getTotalWeightYear(year))); // total weight for year
		data.add(String.format("%.2f", percent) + " %"); // percent for year
		return data;
	}

	/**
	 * MONTHLY REPORT Requirement: Prompt user for a year and a month Then,display a
	 * list of totals and percent of total by farm
	 * 
	 * Prepares arrayList of strings for all data required by Monthly Report
	 * 
	 * @param farmID farmID
	 * @param year   year for data we're collecting
	 * @param month  the name of the month (as a string) for the data we're
	 *               collecting
	 * @return ArrayList with index 0 = FarmID, 1 = Total Weight, and 2 =
	 *         Percentage.
	 * @return null if farmID is not in the list of farms.
	 * @throws Exception if error encountered while gathering data
	 */
	protected static ArrayList<String> monthlyReport(Farm farmID, int year, String month)
			throws Exception {
		// arrayList of strings holding data to be returned
		ArrayList<String> data = new ArrayList<String>();
		double total = 0.0; // total of all weight for all farms in date range
		double percent = 0.0; // percent of weight for this farm
		
		// iterate through all farms
		for (int i = 0; i < Main.farms.size(); i++) {
			// add all month's weights to total
			total = total + (double) Main.farms.get(i).getTotalWeightMonth(year,
					Farm.monthStringToInt(month));
		}
		// calculate statistics
		percent = ((double) farmID.getTotalWeightMonth(year, Farm.monthStringToInt(month)))
				/ (total) * 100;
		if (Double.isNaN(percent)) {
			percent = 0;
		}
		// add statistics
		data.add(farmID.getFarmID()); // farm ID
		// total weight for month
		data.add(
				Integer.toString(farmID.getTotalWeightMonth(year, Farm.monthStringToInt(month))));
		data.add(String.format("%.2f", percent) + " %"); // percent for month
		return data;
	}

	/**
	 * DATE RANGE REPORT Requirements: Propt user for start date (year-month-day)
	 * and end month-day. Then display the total milk weight per farm and the
	 * percentage of the total for each farm over that date range.
	 * 
	 * Generate report for a given range of dates. Assumes user enters valid date
	 * range, otherwise returns report with no data
	 * 
	 * @param farmID     the ID of the farm
	 * @param startYear  earliest year for data
	 * @param startMonth earliest month for data
	 * @param startDay   earliest day for data
	 * @param endYear    latest year for data
	 * @param endMonth   latest month for data
	 * @param endDay     latest day for data
	 * @return arrayList with gathered data in
	 * @throws Exception if error encountered while collecting data
	 */
	protected static ArrayList<String> rangeReport(Farm farmID, int startYear, String startMonth,
			int startDay, int endYear, String endMonth, int endDay) throws Exception {
		// arrayList of strings holding data to be returned
		ArrayList<String> data = new ArrayList<String>();
		double total = 0.0; // total of all weight for all farms in date range
		double percent = 0.0; // percent of weight for this farm

		// iterate through all farms
		for (int i = 0; i < Main.farms.size(); i++) {
			// add milk data for given range
			total = total + (double) Main.farms.get(i).getTotalWeightRange(startYear,
					Farm.monthStringToInt(startMonth), startDay, endYear,
					Farm.monthStringToInt(endMonth), endDay);
		}
		// calculate statistics
		percent = ((double) farmID.getTotalWeightRange(startYear,
				Farm.monthStringToInt(startMonth), startDay, endYear,
				Farm.monthStringToInt(endMonth), endDay)) / (total) * 100;
		if (Double.isNaN(percent)) {
			percent = 0;
		}
		// add statistics to data
		data.add(farmID.getFarmID()); // farm ID
		// total weight for range
		data.add(Integer
				.toString(farmID.getTotalWeightRange(startYear, Farm.monthStringToInt(startMonth),
						startDay, endYear, Farm.monthStringToInt(endMonth), endDay)));
		data.add(String.format("%.2f", percent) + " %"); // percent for range
		return data;
	}
}
