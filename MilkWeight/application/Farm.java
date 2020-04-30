/**
 * Farm.java created by zkeith on personal laptop in MilkWeight
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

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Farm - Stores milk data related to specific farm and can retrieve it from
 * various time windows
 *
 * @author yun91
 *
 */
public class Farm {
	private String farmID; // identification for a specific farm
	private TrieTree milkData; // reference to a tree of milk data for this farm

	/**
	 * No-Arg constructor should never be called, only happens when no farmID is
	 * supplied
	 */
	public Farm() {
		this.farmID = "ERROR: NO FARM ID SUPPLIED";
		this.milkData = new TrieTree();
	}

	/**
	 * Create a Farm with given ID and new tree for data
	 * 
	 * @param farmID for this farm
	 */
	public Farm(String farmID) {
		this.farmID = farmID;
		this.milkData = new TrieTree();
	}

	/**
	 * Adds milk data to farm for specified date
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param weight
	 * @throws Exception
	 */
	public void addInput(int year, int month, int day, int weight) throws Exception {
		try {
			milkData.insert(year, month, day, weight);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Returns the total weight for a specific farm from all time
	 * 
	 * @return totalWeight of milk for this farm
	 */
	public int getTotalWeightAll() {
		int totalWeight = 0;
		for (Integer year : milkData.getYearList()) {
			totalWeight += getTotalWeightYear(year);
		}
		return totalWeight;
	}

	/**
	 * Iterates through all days in a year and calculates the total milk weight
	 * 
	 * @param year
	 * @return totalWeight for a specific year
	 */
	public int getTotalWeightYear(int year) {
		int totalWeight = 0; // running total

		// Sum over the months in a year
		for (int i = 1; i <= 12; i++) {
			totalWeight += getTotalWeightMonth(year, i);
		}
		return totalWeight;
	}

	/**
	 * Iterates through all days in a given month and calculates total milk weight
	 * 
	 * @param year
	 * @param month
	 * @return totalWeight for the month for this farm
	 */
	public int getTotalWeightMonth(int year, int month) {
		int totalWeight = 0; // running total

		// Iterate over all possible days
		// (TrieTree returns 0 when date doesn't exist in month)
		for (int i = 1; i <= 31; i++) {
			totalWeight += milkData.get(year, month, i);
		}
		return totalWeight;
	}

	/**
	 * Returns the total milk from the time starting at month1/day1/year1 until
	 * month2/day2/year2, including both entered days.
	 * 
	 * @param year1
	 * @param month1
	 * @param day1
	 * @param year2
	 * @param month2
	 * @param day2
	 * @throws IllegalArgumentException if an invalid range is entered
	 * @return total milk weight between specified dates
	 */
	public int getTotalWeightRange(int year1, int month1, int day1, int year2, int month2,
			int day2) throws IllegalArgumentException {
		// Invalid range returns 0. Or should we throw error?
		if (year1 > year2) {
			throw new IllegalArgumentException("That is not a valid range.");
		}
		if (year1 == year2 && month1 > month2) {
			throw new IllegalArgumentException("That is not a valid range.");
		}
		if (year1 == year2 && month1 == month2 && day1 > day2) {
			throw new IllegalArgumentException("That is not a valid range.");
		}

		int totalWeight = 0;

		// We will consider two separate cases, whether or not year1 = year2
		if (year1 == year2) {
			// Again, separate cases whether or not month1 = month2
			if (month1 == month2) {
				for (int i = day1; i <= day2; i++) {
					totalWeight += milkData.get(year1, month1, i);
				}
			}

			// Case where months aren't equal
			else {
				// First, add in the rest of the days of month1
				for (int day = day1; day <= 31; day++) {
					totalWeight += milkData.get(year1, month1, day);
				}

				// Next, add in months in between month1 and month2
				for (int month = month1 + 1; month < month2; month++) {
					totalWeight += getTotalWeightMonth(year1, month);
				}

				// Finally, add in the days before month2/day2
				for (int day = 1; day <= day2; day++) {
					totalWeight += milkData.get(year1, month2, day);
				}
			}
		}

		// Case where year1 < year2
		else {
			// First, we add up all of the milk from year1 using our previous work
			totalWeight += getTotalWeightRange(year1, month1, day1, year1, 12, 31);

			// Next, we add in all of the years in between
			for (int year = year1 + 1; year < year2; year++) {
				totalWeight += getTotalWeightYear(year);
			}

			// Finally, add up all of the milk from year2
			totalWeight += getTotalWeightRange(year2, 1, 1, year2, month2, day2);
		}

		return totalWeight;
	}

	/**
	 * Getter for this farm's ID. ID should not change so no setter exists
	 * @return farmID for this farm
	 */
	public String getFarmID() {
		return this.farmID;
	}

	/**
	 * Gets a set of the years of data for this farm
	 * @return castSet a set of years for this farm
	 */
	public Set<String> getYearSet() {
		TreeSet<String> castSet = new TreeSet<String>();
		for (Integer i : milkData.getYearList()) {
			castSet.add(Integer.toString(i));
		}
		return castSet;
	}

	/**
	 * Gets an ordered list of the years of data for this farm
	 * @return this farm's tree's yearList
	 */
	public List<Integer> getYearIntList() {
		return milkData.getYearList();
	}

	/**
	 * Gets the data for a specified day
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return the milkweight for a specified day within this farm's data
	 */
	public int getTotalWeightDay(int year, int month, int day) {
		return milkData.get(year, month, day);
	}

	/**
	 * Overloaded method for adding using a string month
	 * 
	 * @param year
	 * @param userMonthChoice - a string E.g. January, February, etc
	 * @param day
	 * @param weight
	 * @throws Exception
	 */
	public void addInput(int year, String month, int day, int weight) throws Exception {
		try {
			addInput(year, monthStringToInt(month), day, weight);
		} catch (Exception e) { // There won't be any exceptions when this is called in main
			throw e;
		}
	}

	/**
	 * Given a String representing a month, this returns the integer of the month.
	 * Capitalization doesn't matter. So "january" should return 1, and "MAY" should
	 * return 5.
	 * 
	 * @param month - a string meant to be the name of the month
	 * @throws IllegalArgumentException if month input does not match a month
	 * @return the corresponding integer for the specified month
	 */
	public static int monthStringToInt(String month) throws IllegalArgumentException {
		if (month.equalsIgnoreCase("January")) {
			return 1;
		}
		if (month.equalsIgnoreCase("February")) {
			return 2;
		}
		if (month.equalsIgnoreCase("March")) {
			return 3;
		}
		if (month.equalsIgnoreCase("April")) {
			return 4;
		}
		if (month.equalsIgnoreCase("May")) {
			return 5;
		}
		if (month.equalsIgnoreCase("June")) {
			return 6;
		}
		if (month.equalsIgnoreCase("July")) {
			return 7;
		}
		if (month.equalsIgnoreCase("August")) {
			return 8;
		}
		if (month.equalsIgnoreCase("September")) {
			return 9;
		}
		if (month.equalsIgnoreCase("October")) {
			return 10;
		}
		if (month.equalsIgnoreCase("November")) {
			return 11;
		}
		if (month.equalsIgnoreCase("December")) {
			return 12;
		}

		throw new IllegalArgumentException(); // month parameter did not match any month
	}
}
