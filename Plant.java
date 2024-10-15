/*
 * this is the parent class Plant that Vegetable,
 * Flower, Tree, and Crop all inherit instance variables
 * and methods from
 */

package com.gradescope.garden;

public abstract class Plant {
	protected String type;
	protected String letter;
	protected int growthStage;
	//all the specific types a Plant can be
	public final static String[] TYPES = {"garlic", "zucchini", "tomato", "yam", "lettuce",
											"iris", "lily", "rose", "daisy", "tulip", "sunflower",
											"oak", "willow", "banana", "coconut", "pine",
											"corn", "wheat", "sugarcane", "potato", "radish", "beans"};
	protected String[][] plot = {{".", ".", ".", ".", "."}, 
								 {".", ".", ".", ".", "."},
								 {".", ".", ".", ".", "."},
								 {".", ".", ".", ".", "."},
								 {".", ".", ".", ".", "."},};
	
	public Plant() {}
	//constructor
	
	protected abstract String[] print();
	//print method inherited by the subclasses
	
	protected abstract String getSpecies();
	//getSpecies method inherited by the subclasses
	
	protected abstract void grow(int num);
	//grow method inherited by the subclasses
	
	protected String[][] getPlot() {
		//returns the plot
		return plot;
	}

	protected String getType() {
		//returns the type
		return type;
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
    
    protected Boolean validType(String type) {
    	/*
    	 * args: String type
    	 * return: Boolean
    	 * 
    	 * this function uses the inArray method
    	 * to determine if the type is in TYPES
    	 */
        return inArray(type, TYPES);
    }
}
