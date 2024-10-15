/*
 * this is the garden class where all the functions for the
 * garden are called like plant, grow, harvest, pick, cut,
 * farm and all their variations
 */

package com.gradescope.garden;

public class Garden {
	private Plant[][] plants;
	
	public Garden(int rows, int columns) {
		//class constructor
		this.plants = new Plant[rows][columns];
	}
	
	private Boolean inArray(String value, String[] arr) {
		/*
		 * args: String value, String[] arr
		 * returns: boolean
		 * 
		 * this is a simple function that checks if a value
		 * is in an array and returns true if it is and false
		 * if it isn't
		 */
		
        for (String element : arr) {
            if (element.equals(value)) return true;
        }
        return false;
    }
	
	public Plant[][] getGarden() {
		/*
		 * args: None
		 * returns: the plants 2d array
		 */
		
		return plants;
	}
	
	public void plant(int[] coordinates, String type) {
		/*
		 * args: int[] coordinates, String type
		 * returns: None
		 * 
		 * this function creates a new plant based on the type variable
		 * and plants it at the given coordinates in the plants 2d array
		 */
		
		String[] vegetableTypes = {"garlic", "zucchini", "tomato", "yam", "lettuce"};
		String[] flowerTypes = {"iris", "lily", "rose", "daisy", "tulip", "sunflower"};
		String[] treeTypes = {"oak", "willow", "banana", "coconut", "pine"};
		String[] cropTypes = {"corn", "wheat", "sugarcane", "potato", "radish", "beans"};
		if (coordinates[0] < plants.length && coordinates[1] < plants[0].length) {
			if (inArray(type, vegetableTypes)) plants[coordinates[0]][coordinates[1]] = new Vegetable(type);
			else if (inArray(type, flowerTypes)) plants[coordinates[0]][coordinates[1]] = new Flower(type);
			else if (inArray(type, treeTypes)) plants[coordinates[0]][coordinates[1]] = new Tree(type);
			else if (inArray(type, cropTypes)) plants[coordinates[0]][coordinates[1]] = new Crop(type);
		}
	}
	
	public void grow() {
		/*
		 * args: None
		 * returns: None
		 * 
		 * This function grows all the plants currently
		 * in the garden by 1
		 */
		
		System.out.println("> GROW\n");
		for (int r = 0; r < plants.length; r++) {
			for (int c = 0; c < plants[r].length; c++) {
				if (plants[r][c] != null) plants[r][c].grow(1);
			}
		}
	}
	
	public void grow(int num, int[] coordinates) {
		/*
		 * args: int num, int[] coordinates
		 * returns: None
		 * 
		 * This function grows the plants at the coordinates
		 * by num
		 */
		
		System.out.println("> GROW " + num + " (" + coordinates[0] + "," + coordinates[1] + ")\n");
		if (coordinates[0] >= plants.length || coordinates[1] >= plants[0].length 
				|| plants[coordinates[0]][coordinates[1]] == null ) {
			System.out.println("Can't grow there.\n");
		}
		else plants[coordinates[0]][coordinates[1]].grow(num);
	}
	
	public void grow(int num, String type) {
		/*
		 * args: int num, String type
		 * returns: None
		 * 
		 * This function grows all the plants currently
		 * in the garden that are equal to type by num
		 */
		
		System.out.println("> GROW " + num + " " + type + "\n");
		for (int r = 0; r < plants.length; r++) {
			for (int c = 0; c < plants[r].length; c++) {
				if (plants[r][c] != null && plants[r][c].getType().equals(type)) {
					plants[r][c].grow(num);
				}
			}
		}
	}
	
	public void grow(String plant, int num) {
		/*
		 * args: String plant, int num
		 * returns: None
		 * 
		 * This function grows all the plants currently
		 * in the garden that are of type plant by num
		 */
		
		System.out.println("> GROW " + num + " " + plant + "\n");
		for (int r = 0; r < plants.length; r++) {
			for (int c = 0; c < plants[r].length; c++) {
				if (plants[r][c] != null && plants[r][c].getSpecies().equals(plant)) {
					plants[r][c].grow(num);
				}
			}
		}
	}
	
	public void harvest() {
		/*
		 * args: None
		 * returns: None
		 * 
		 * the next few functions of this type are 
		 * repeats of eachother for each type of plant
		 * 
		 * The harvest version of this function removes all 
		 * Vegetable plants from the garden
		 * 
		 * The pick version of this function removes all 
		 * flowers plants from the garden
		 * 
		 * The cut version of this function removes all 
		 * trees plants from the garden
		 * 
		 * The farm version of this function removes all 
		 * crops plants from the garden
		 */
		
		System.out.println("> HARVEST\n");
		for (int r = 0; r < plants.length; r++) {
			for (int c = 0; c < plants[r].length; c++) {
				if (plants[r][c] instanceof Vegetable) plants[r][c] = null;
			}
		}
	}
	
	public void harvest(int[] coordinates) {
		/*
		 * args: int[] coordinates
		 * returns: None
		 * 
		 * the next few functions of this type are 
		 * repeats of eachother for each type of plant
		 * 
		 * The harvest version of this function removes the 
		 * Vegetable plant at these coordinates from the garden
		 * 
		 * The pick version of this function removes the 
		 * flower plant at these coordinates from the garden
		 * 
		 * The cut version of this function removes the 
		 * tree plant at these coordinates from the garden
		 * 
		 *The farm version of this function removes the 
		 *crop plant at these coordinates from the garden
		 */
		
		System.out.println("> HARVEST " + "(" + coordinates[0] + "," + coordinates[1] + ")\n");
		if ((coordinates[0] > plants.length && coordinates[1] > plants[0].length) 
				|| plants[coordinates[0]][coordinates[1]] instanceof Vegetable == false) {
			System.out.println("Can’t harvest there.\n");
		}
		else plants[coordinates[0]][coordinates[1]] = null;
	}
	
	public void harvest(String type) {
		/*
		 * args: String type
		 * returns: None
		 * 
		 * the next few functions of this type are 
		 * repeats of eachother for each type of plant
		 * 
		 * The harvest version of this function removes all 
		 * Vegetable plants of this type from the garden
		 * 
		 * The pick version of this function removes all 
		 * flowers plants of this type from the garden
		 * 
		 * The cut version of this function removes all 
		 * trees plants of this type from the garden
		 * 
		 * The farm version of this function removes all 
		 * crops plants of this type from the garden
		 */
		
		System.out.println("> HARVEST " + type +"\n");
		for (int r = 0; r < plants.length; r++) {
			for (int c = 0; c < plants[r].length; c++) {
				if (plants[r][c] != null && plants[r][c].getType().equals(type)) {
					plants[r][c] = null;
				}
			}
		}
	}
	
	public void pick() {		
		System.out.println("> PICK\n");
		for (int r = 0; r < plants.length; r++) {
			for (int c = 0; c < plants[r].length; c++) {
				if (plants[r][c] instanceof Flower) plants[r][c] = null;
			}
		}
	}
	
	public void pick(int[] coordinates) {
		System.out.println("> PICK " + "(" + coordinates[0] + "," + coordinates[1] + ")\n");
		if ((coordinates[0] > plants.length && coordinates[1] > plants[0].length) 
				|| plants[coordinates[0]][coordinates[1]] instanceof Flower == false) {
			System.out.println("Can’t pick there.\n");
		}
		else plants[coordinates[0]][coordinates[1]] = null;
	}
	
	public void pick(String type) {
		System.out.println("> PICK " + type +"\n");
		for (int r = 0; r < plants.length; r++) {
			for (int c = 0; c < plants[r].length; c++) {
				if (plants[r][c] != null && plants[r][c].getType().equals(type)) {
					plants[r][c] = null;
				}
			}
		}
	}
	
	public void cut() {
		System.out.println("> CUT\n");
		for (int r = 0; r < plants.length; r++) {
			for (int c = 0; c < plants[r].length; c++) {
				if (plants[r][c] instanceof Tree) plants[r][c] = null;
			}
		}
	}
	
	public void cut(int[] coordinates) {
		System.out.println("> CUT " + "(" + coordinates[0] + "," + coordinates[1] + ")\n");
		if ((coordinates[0] > plants.length && coordinates[1] > plants[0].length) 
				|| plants[coordinates[0]][coordinates[1]] instanceof Tree == false) {
			System.out.println("Can’t cut there.\n");
		}
		else plants[coordinates[0]][coordinates[1]] = null;
	}
	
	public void cut(String type) {
		System.out.println("> CUT " + type +"\n");
		for (int r = 0; r < plants.length; r++) {
			for (int c = 0; c < plants[r].length; c++) {
				if (plants[r][c] != null && plants[r][c].getType().equals(type)) {
					plants[r][c] = null;
				}
			}
		}
	}
	
	public void farm() {
		System.out.println("> FARM\n");
		for (int r = 0; r < plants.length; r++) {
			for (int c = 0; c < plants[r].length; c++) {
				if (plants[r][c] instanceof Crop) plants[r][c] = null;
			}
		}
	}
	
	public void farm(int[] coordinates) {
		System.out.println("> FARM " + "(" + coordinates[0] + "," + coordinates[1] + ")\n");
		if ((coordinates[0] > plants.length && coordinates[1] > plants[0].length) 
				|| plants[coordinates[0]][coordinates[1]] instanceof Crop == false) {
			System.out.println("Can’t farm there.\n");
		}
		else plants[coordinates[0]][coordinates[1]] = null;
	}
	
	public void farm(String type) {
		System.out.println("> FARM " + type +"\n");
		for (int r = 0; r < plants.length; r++) {
			for (int c = 0; c < plants[r].length; c++) {
				if (plants[r][c] != null && plants[r][c].getType().equals(type)) {
					plants[r][c] = null;
				}
			}
		}
	}
}