/*
 * this is the screen class, it is used to print out
 * what the garden looks like everytime print() is called
 */

package com.gradescope.garden;

public class Screen {
	private Plant[][] screen;
	
	public Screen(Garden garden) {
		//class constructor
		this.screen = garden.getGarden();;
	}
	
	public void print() {
		/*
		 * args: None
		 * returns: None
		 * 
		 * This funtions job is to print what the garden currently looks
		 * like at any given time, it checks to see if each plot is occupied
		 * by a plant or not, and if it is it prints that plants plot
		 */
		
		System.out.println("> PRINT");
		for (int r = 0; r < screen.length * 5; r++) {
			for (int c = 0; c < screen[r/5].length; c++) {
				if (screen[r/5][c] != null) {
					String[] printed = screen[r/5][c].print();
					System.out.print(printed[r % 5]);
				}
				else System.out.print(".....");
			}
			System.out.println();
		}
	}
}
