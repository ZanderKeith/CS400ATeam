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

/**
 * Farm - Stores milk data related to specific farm and can retrieve it from
 * various time windows
 * 
 * @author zkeith
 *
 */
public class Farm {
	private String farmID;
	private TrieTree milkData;

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
	 * @param farmID
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
	 */
	public void addInput(int year, int month, int day, int weight) {
		milkData.insert(year, month, day, weight);
	}

	/**
	 * TODO
	 * 
	 * @return total milk weight from all available data
	 */
	public int getTotalWeightAll() {
		return -1;
	}

	/**
	 * TODO
	 * 
	 * @param year
	 * @return total milk weight of the year
	 */
	public int getTotalWeightYear(int year) {
		return -1;
	}

	/**
	 * Iterates through all days in a given month and calculates total milk weight
	 * 
	 * @param year
	 * @param month
	 * @return total milk weight for the month
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
	 * TODO
	 * 
	 * @param year1
	 * @param month1
	 * @param day1
	 * @param year2
	 * @param month2
	 * @param day2
	 * @return total milk weight between specified dates
	 */
	public int getTotalWeightRange(int year1, int month1, int day1, int year2, int month2,
			int day2) {
		return -1;
	}

	// Getter but no setter because we don't want to change farm's ID
	public String getFarmID() {
		return this.farmID;
	}

}
