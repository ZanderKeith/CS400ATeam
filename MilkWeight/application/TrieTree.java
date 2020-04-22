/**
 * TrieTree.java created by sparenti in MilkWeight
 * 
 * Author: 		Solly Parenti (sparenti@wisc.edu)
 * Date: 		Apr 19, 2020
 * 
 * Course: 		CS400
 * Semester: 	Spring 2020
 * Lecture: 	001
 * 
 * IDE: 		Eclipse IDE for Java Developers
 * Version: 	2019-12
 * 
 * OS:			Mac OS 10.14.6
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * TrieTree - This class holds the data for a TrieTree. This Trie is sorted
 * first by year, then by month, then by date. Each date holds an integer value
 * weight.
 * 
 * @author sparenti
 */
public class TrieTree {
	// Private class containing all of the data we will need for the whole year.
	private class TrieTreeYearNode {
		int year;
		TrieTreeMonthNode[] months;

		private TrieTreeYearNode(int year) {
			this.year = year;
			this.months = new TrieTreeMonthNode[12];
		}
	}

	/**
	 *
	 * TrieTreeMonthNode -This class holds an array corresponding to the days of the
	 * month
	 *
	 */
	private class TrieTreeMonthNode {
		TrieTreeDayNode[] days;

		private TrieTreeMonthNode(int daysInMonth) {
			this.days = new TrieTreeDayNode[daysInMonth];
		}

	}

	private class TrieTreeDayNode {
		int day;
		int weight;

		private TrieTreeDayNode(int day) {
			this.day = day;
			this.weight = 0;
		}
	}

	// HashMap of YearNodes to store our trietree data
	private HashMap<Integer, TrieTreeYearNode> treeYearMap;

	public TrieTree() {
		this.treeYearMap = new HashMap<Integer, TrieTreeYearNode>();
	}

	/**
	 * This method adds a leaf with value weight under the correct date. If the leaf
	 * already exists, this method updates the weight. This method uses normal human
	 * conventions for the day and month, no need to worry about array indexing. So
	 * month = 1 and day = 1 means the date January 1st
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param weight
	 */
	public void insert(int year, int month, int day, int weight) {
		// First, check if this year is already in the array
		if (!this.treeYearMap.containsKey(year)) {
			this.treeYearMap.put(year, new TrieTreeYearNode(year));
		}

		// Next, check if we have already added data for that month
		if (this.treeYearMap.get(year).months[month - 1] == null) {
			int lengthOfMonth;
			if (month == 2 && isLeapYear(year)) {
				lengthOfMonth = 29;
			} else if (month == 2 && !isLeapYear(year)) {
				lengthOfMonth = 28;
			} else if (month == 4 || month == 6 || month == 9 || month == 11) {
				lengthOfMonth = 30;
			} else {
				lengthOfMonth = 31;
			}
			this.treeYearMap.get(year).months[month - 1] = new TrieTreeMonthNode(lengthOfMonth);
		}

		// Next, we check if we have already added data for that day
		if (this.treeYearMap.get(year).months[month - 1].days[day - 1] == null) {
			this.treeYearMap.get(year).months[month - 1].days[day - 1] = new TrieTreeDayNode(day);
			this.treeYearMap.get(year).months[month - 1].days[day - 1].weight = weight;
		}
	}

	/**
	 * Private helper method to determine if the year is a leap year.
	 * 
	 * @param yearInt - year
	 * @return true if the year is a leap year, false otherwise
	 */
	private boolean isLeapYear(int yearInt) {
		if (yearInt % 4 != 0) {
			return false;
		} else if (yearInt % 100 != 0) {
			return true;
		} else if (yearInt % 400 != 0) {
			return false;
		} else {
			return true;
		}
	}

	// Gets weight for specified date. Returns 0 if no data for this date exists
	public int get(int year, int month, int day) {
		if (!contains(year, month, day)) {
			return 0;
		}
		return this.treeYearMap.get(year).months[month - 1].days[day - 1].weight;
	}

	// True if data for date exists, false otherwise
	public boolean contains(int year, int month, int day) {
		// First, check if we have data for that year
		if (!this.treeYearMap.containsKey(year)) {
			return false;
		}

		// Next, check if we have data for that year and month
		if (this.treeYearMap.get(year).months[month - 1] == null) {
			return false;
		}

		// Finally, we check for the day
		try {
			if (this.treeYearMap.get(year).months[month - 1].days[day - 1] == null) {
				return false;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return false; // Trying to get day that doesn't exist in month. IGNORE
		}

		// If we made it this far, the trie tree contains data for the given day
		return true;

	}

	// Removes node for date. Returns true if successfully removed,
	// false otherwise
	public boolean remove(int year, int month, int day) {
		if (!contains(year, month, day)) {
			return false;
		}

		// We need to set the data for the specific date to be null
		this.treeYearMap.get(year).months[month - 1].days[day - 1] = null;

		// Checking if the month has any data left
		TrieTreeMonthNode currentMonth = this.treeYearMap.get(year).months[month - 1];
		for (int i = 0; i < currentMonth.days.length; i++) {
			if (currentMonth.days[i] != null) {
				return true;
			}
		}

		// No more days of that month have any data, so we set the month as null
		this.treeYearMap.get(year).months[month - 1] = null;

		// Lastly, we check if the year has any data left
		TrieTreeYearNode currentYear = this.treeYearMap.get(year);
		for (int i = 0; i < currentYear.months.length; i++) {
			if (currentYear.months[i] != null) {
				return true;
			}
		}

		// No more months of the year have data, remove year from the hash map
		this.treeYearMap.remove(year);
		return true;
	}
	
	/**
	 * This method returns a sorted list of integers containing all of 
	 * the years in which this trietree has data for
	 * @return
	 */
	public List<Integer> getYearList() {
		List<Integer> yearList = new ArrayList<Integer>();
		for (Integer year : treeYearMap.keySet()) {
			yearList.add(year);
		}
		Collections.sort(yearList);
		return yearList;
	}

}
