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
 * TrieTree - This class holds the data for a TrieTree, sorted first by year, 
 * then by month, then by date. Each date holds an integer value weight. 
 * The methods use standard human convention for months and days, rather
 * than array indexing.  That is, January is the 1st month of the year, not the
 * 0th month of the year, and similarly for days.
 * 
 */
public class TrieTree {
	
	/**
	 * TrieTreeYearNode: A private inner class which stores an array of month 
	 * nodes.
	 */
	private class TrieTreeYearNode {
		TrieTreeMonthNode[] months;

		/**
		 * This constructor creates an array of month nodes
		 * @param year
		 */
		private TrieTreeYearNode(int year) {
			this.months = new TrieTreeMonthNode[12];
		}
	}

	/**
	 * TrieTreeMonthNode -This class holds an array corresponding to the days of the
	 * month
	 */
	private class TrieTreeMonthNode {
		TrieTreeDayNode[] days;

		/**
		 * This constructor creates an array of day nodes
		 * @param daysInMonth - how many days are in the month
		 */
		private TrieTreeMonthNode(int daysInMonth) {
			this.days = new TrieTreeDayNode[daysInMonth];
		}

	}

	/**
	 * TrieTreeDayNode: For each day, we store an integer value meant to 
	 * represent the weight of milk from that day
	 */
	private class TrieTreeDayNode {
		int weight;

		/**
		 * This constructor sets the weight to 0
		 */
		private TrieTreeDayNode() {
			this.weight = 0;
		}
	}

	// HashMap of YearNodes to store our trietree data
	private HashMap<Integer, TrieTreeYearNode> treeYearMap;

	/**
	 * The constructor of the TrieTree
	 */
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
	 * @throws Exception if the data is overwritten
	 */
	public void insert(int year, int month, int day, int weight) throws Exception {
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
			this.treeYearMap.get(year).months[month - 1].days[day - 1] = new TrieTreeDayNode();
		} else {
			this.treeYearMap.get(year).months[month - 1].days[day - 1].weight = weight;
			throw new Exception("Data Overwritten.");
		}

		this.treeYearMap.get(year).months[month - 1].days[day - 1].weight = weight;
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

	/**
	 * This method returns the weight stored by this trietree on a given date.
	 * If there is no data for the given date, this method returns 0. Recall
	 * that the month and day use normal calendar conventions.
	 * @param year
	 * @param month
	 * @param day
	 * @return the weight at the given date, or 0 if there is no data for the 
	 * given date
	 */
	public int get(int year, int month, int day) {
		if (!contains(year, month, day)) {
			return 0;
		}
		return this.treeYearMap.get(year).months[month - 1].days[day - 1].weight;
	}

	/**
	 * This method returns true or false based on whether or not this trie tree 
	 * contains data for the given date. Recall that the month and the day use
	 * normal calendar conventions.
	 * @param year
	 * @param month
	 * @param day
	 * @return true if data exists for the given date in this trie tree, false
	 * otherwise.
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
	 * This method removes any data on the given date from the trie tree if it 
	 * exists.  This method returns true if the remove was successful, false if
	 * the trie tree doesn't have data for that date. Recall that the month and
	 * day use normal calendar conventions.
	 * @param year
	 * @param month
	 * @param day
	 * @return true if the data was removed successfully, false if the trie
	 * tree does not contain data for the given date.
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
	 * This method returns a sorted list of integers containing all of the years in
	 * which this trietree has data for
	 * 
	 * @return a sorted list of integers containing all of the years that this 
	 * trie tree has data for.
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
