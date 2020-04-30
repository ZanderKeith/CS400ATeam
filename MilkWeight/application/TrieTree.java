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

	/**
	 * 
	 * TrieTreeYearNode - inner class that forms top level structure of Trie.
	 * Contains all data needed for a particular year. Expects year identifier to be
	 * handled where nodes are stored
	 *
	 */
	private class TrieTreeYearNode {
		TrieTreeMonthNode[] months; // All months in given year

		/**
		 * Create a new TrieTreeYearNode
		 */
		private TrieTreeYearNode() {
			// Initialize months array
			this.months = new TrieTreeMonthNode[12];
		}
	}

	/**
	 *
	 * TrieTreeMonthNode - Inner class forming midlevel structure of TrieTree. This
	 * class holds an array corresponding to the days of the month
	 *
	 */
	private class TrieTreeMonthNode {
		TrieTreeDayNode[] days; // All days in given month

		/**
		 * Create new TrieTreeMonthNode
		 * 
		 * @param daysInMonth
		 */
		private TrieTreeMonthNode(int daysInMonth) {
			// Initialize months array of correct length
			this.days = new TrieTreeDayNode[daysInMonth];
		}

	}

	/**
	 * 
	 * TrieTreeDayNode - Inner class forming leafs of TrieTree. Holds a weight for a
	 * given day
	 */
	private class TrieTreeDayNode {
		int weight; // weight for given day

		private TrieTreeDayNode() {
			// begin with weight being 0
			this.weight = 0;
		}
	}

	// HashMap of YearNodes to store our trietree data
	private HashMap<Integer, TrieTreeYearNode> treeYearMap;

	/**
	 * Create a new TrieTree
	 */
	public TrieTree() {
		// Initialize hash map for storing all year nodes
		this.treeYearMap = new HashMap<Integer, TrieTreeYearNode>();
	}

	/**
	 * This method adds a leaf with value weight under the correct date. If the leaf
	 * already exists, this method updates the weight. This method uses normal human
	 * conventions for the day and month, no need to worry about array indexing. So
	 * month = 1 and day = 1 means the date January 1st. Expects date to be valid
	 * ex: no February 31st.
	 * 
	 * @param year   the date's year
	 * @param month  the date's month
	 * @param day    the date's day
	 * @param weight the weight for day
	 */
	public void insert(int year, int month, int day, int weight) {
		// First, check if this year is already in the array
		if (!this.treeYearMap.containsKey(year)) {
			this.treeYearMap.put(year, new TrieTreeYearNode());
		}

		// Next, check if we have already added data for that month
		if (this.treeYearMap.get(year).months[month - 1] == null) {
			int lengthOfMonth; // number of days in month
			if (month == 2 && isLeapYear(year)) { // if leap year, february has 29
				lengthOfMonth = 29;
			} else if (month == 2 && !isLeapYear(year)) { // if no leap year, february has 28
				lengthOfMonth = 28;
				// 30 days hath September, April, June, and November
			} else if (month == 4 || month == 6 || month == 9 || month == 11) {
				lengthOfMonth = 30;
				// All the rest have 31
			} else {
				lengthOfMonth = 31;
			}
			// no data added for month, create new monthNode
			this.treeYearMap.get(year).months[month - 1] = new TrieTreeMonthNode(lengthOfMonth);
		}

		// Next, we check if we have already added data for that day
		if (this.treeYearMap.get(year).months[month - 1].days[day - 1] == null) {
			// No data for day yet, create it
			this.treeYearMap.get(year).months[month - 1].days[day - 1] = new TrieTreeDayNode();
		}
		// Put weight in given day. If it already existed this will replace it
		this.treeYearMap.get(year).months[month - 1].days[day - 1].weight = weight;
	}

	/**
	 * Private helper method to determine if the year is a leap year.
	 * 
	 * @param yearInt - year
	 * @return true if the year is a leap year, false otherwise
	 */
	private boolean isLeapYear(int yearInt) {
		// Gregorian calendar has leap years on multiples of 4, not multiples of 100,
		// but yes on multiples of 400.

		if (yearInt % 4 != 0) {
			// not a multiple of 4, return false
			return false;
		} else if (yearInt % 100 != 0) {
			// multiple of 4 but not a multiple of 100, return true
			return true;
		} else if (yearInt % 400 != 0) {
			// multiple of 100 but not multiple of 400, return false
			return false;
		} else {
			// multiple of 400, return true
			return true;
		}
	}

	/**
	 * Gets weight for specified date. Returns 0 if no data for this date exists.
	 * 
	 * @param year date's year
	 * @param month date's month
	 * @param day date's day
	 * @return milk weight for given date
	 */
	public int get(int year, int month, int day) {
		// If node doesn't exist return 0
		if (!contains(year, month, day)) {
			return 0;
		}
		return this.treeYearMap.get(year).months[month - 1].days[day - 1].weight;
	}

	/**
	 * Determines if data for date exists
	 * 
	 * @param year date's year
	 * @param month date's month
	 * @param day date's day
	 * @return true if data for date exists, false otherwise
	 */
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

	/**
	 * Removes node for date
	 * @param year date's year
	 * @param month date's month
	 * @param day date's day
	 * @return true if successfully removed, false otherwise
	 */
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
	 * Makes list of years in TrieTree
	 * 
	 * @return sorted list of integers containing all of the years in
	 * which this TrieTree has data for
	 */
	public List<Integer> getYearList() {
		// list for years
		List<Integer> yearList = new ArrayList<Integer>();
		// If top hashMap has a key for year, that year exists
		for (Integer year : treeYearMap.keySet()) {
			yearList.add(year);
		}
		Collections.sort(yearList);
		return yearList;
	}

}
