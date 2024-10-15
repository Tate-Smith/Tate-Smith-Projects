/*
 * this is the Crop class it is a subclass of
 * the Plant class and inherits its methods and
 * instance variables
 */

package com.gradescope.garden;

public class Crop extends Plant{
	
	public Crop(String type) {
		//class constructor
		if (validType(type)) this.type = type;
		this.letter = type.substring(0, 1);
		this.growthStage = 0;
	}
	
	public String getSpecies() {
		//returns its Class type
		return "crop";
	}
	
	public String[] print() {
		/*
		 * args: None
		 * returns: String[]
		 * 
		 * this function returns an array list version
		 * of the 2D array list that is the plants plot
		 * so the screen function can print it out
		 */
		
		plot[2][2] = letter;
		String[] newPlot = new String[5];
		for (int r = 0; r < plot.length; r++) {
			String row = "";
			for (int c = 0; c < plot[r].length; c++) {
				row += plot[r][c];
			}
			newPlot[r] = row;
		}
		return newPlot;
	}
	
	public void grow(int num) {
		/*
		 * args: int num
		 * returns None:
		 * 
		 * This function grows the plant in its
		 * plot everytime the grow function is called
		 * the Crop plant grows upwards from its wide base
		 */
		
		growthStage += num;
		if (growthStage > 4) growthStage = 4;
		for (int i = 1; i <= growthStage; i ++) {
			plot[i][0] = letter;
			plot[i][1] = letter;
			plot[i][2] = letter;
			plot[i][3] = letter;
			plot[i][4] = letter;
		}
	}
}
