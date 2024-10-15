/*
 * this is the Flower class it is a subclass of
 * the Plant class and inherits its methods and
 * instance variables
 */

package com.gradescope.garden;

public class Flower extends Plant{
	
	public Flower(String type) {
		//class constructor
		if (validType(type)) this.type = type;
		this.letter = type.substring(0, 1);
		this.growthStage = 0;
	}
	
	public String getSpecies() {
		//returns its Class type
		return "flower";
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
	
	private void stage1() {
		//stage 1 of its growth process
		plot[1][2] = letter;
		plot[3][2] = letter;
		plot[2][1] = letter;
		plot[2][3] = letter;
	}
	
	private void stage2() {
		//stage 2 of its growth process
		plot[0][2] = letter;
		plot[4][2] = letter;
		plot[2][0] = letter;
		plot[2][4] = letter;
		plot[1][1] = letter;
		plot[3][3] = letter;
		plot[1][3] = letter;
		plot[3][1] = letter;
	}
	
	private void stage3() {
		//stage 3 of its growth process
		plot[1][0] = letter;
		plot[1][4] = letter;
		plot[0][1] = letter;
		plot[0][3] = letter;
		plot[3][0] = letter;
		plot[3][4] = letter;
		plot[4][1] = letter;
		plot[4][3] = letter;
	}
	
	private void stage4() {
		//stage 4 of its growth process
		plot[0][0] = letter;
		plot[0][4] = letter;
		plot[4][0] = letter;
		plot[4][4] = letter;
	}
	
	public void grow(int num) {
		/*
		 * args: int num
		 * returns None:
		 * 
		 * This function grows the plant in its
		 * plot everytime the grow funtion is called
		 * the Flower plant blooms untill if fills
		 * the whole plot, each function it calls
		 * is a stage in its growth process
		 */
		
		growthStage += num;
		if (growthStage > 4) growthStage = 4;
		if (growthStage == 1) {
			stage1();
		}
		else if (growthStage == 2) {
			stage1();
			stage2();
		}
		else if (growthStage == 3) {
			stage1();
			stage2();
			stage3();
		}
		else if (growthStage == 4) {
			stage1();
			stage2();
			stage3();
			stage4();
		}
	}
}
