/*
 * Author: Tate Smith
 * Class: CSC210
 * Project: RunGarden
 * 
 * This project uses 6 class files, with 4 of them inheriting
 * from the parent class plant. These classes implement specific types
 * of plants, that are used to create a garden, and all the functions
 * are implemented through the garden class, with the screen being displayed
 * using the screen class. The program uses a file as input through the 
 * terminal, and the file contains all functions of the program
 */

package com.gradescope.garden;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RunGarden {
	public static Boolean inArray(String value, String[] arr) {
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
	
	public static void grow(String[] input, Garden garden) {
		/*
		 * args: String[] input, Garden garden
		 * returns: None
		 * 
		 * This funtions job is to call the grow functions of
		 * the garden class. It takes a String[] as input, and 
		 * deciphers which grow function to call based on what
		 * the input is
		 */
		
		if (input.length == 2) garden.grow();
		else {
			String[] types = {"garlic", "zucchini", "tomato", "yam", "lettuce",
								"iris", "lily", "rose", "daisy", "tulip", "sunflower",
								"oak", "willow", "banana", "coconut", "pine",
								"corn", "wheat", "sugarcane", "potato", "radish", "beans"};
			if (input[2].startsWith("(")) {
				String[] coords = input[2].split(",");
				coords[0] = coords[0].replace("(", "");
				coords[1] = coords[1].replace(")", "");
				int[] coordinates = {Integer.valueOf(coords[0]), Integer.valueOf(coords[1])};
				garden.grow(Integer.valueOf(input[1]), coordinates);
			}
			else if (inArray(input[2], types)) garden.grow(Integer.valueOf(input[1]), input[2]);
			else garden.grow(input[2], Integer.valueOf(input[1]));
		}
	}
		
	public static void harvest(String[] input, Garden garden) {
		/*
		 * args: String[] input, Garden garden
		 * returns: None
		 * 
		 * This funtions job is to call the harvest functions of
		 * the garden class. It takes a String[] as input, and 
		 * deciphers which harvest function to call based on what
		 * the input is
		 */
		
		if (input.length == 1) garden.harvest();
		else {
			if (input[1].charAt(0) == '(') {
				String[] coords = input[1].split(",");
				coords[0] = coords[0].replace("(", "");
				coords[1] = coords[1].replace(")", "");
				int[] coordinates = {Integer.valueOf(coords[0]), Integer.valueOf(coords[1])};
				garden.harvest(coordinates);
			}
			else garden.harvest(input[1].toLowerCase());
		}
	}
	
	public static void pick(String[] input, Garden garden) {
		/*
		 * args: String[] input, Garden garden
		 * returns: None
		 * 
		 * This funtions job is to call the pick functions of
		 * the garden class. It takes a String[] as input, and 
		 * deciphers which pick function to call based on what
		 * the input is
		 */
		
		if (input.length == 1) garden.pick();
		else {
			if (input[1].charAt(0) == '(') {
				String[] coords = input[1].split(",");
				coords[0] = coords[0].replace("(", "");
				coords[1] = coords[1].replace(")", "");
				int[] coordinates = {Integer.valueOf(coords[0]), Integer.valueOf(coords[1])};
				garden.pick(coordinates);
			}
			else garden.pick(input[1].toLowerCase());
		}
	}
	
	public static void cut(String[] input, Garden garden) {
		/*
		 * args: String[] input, Garden garden
		 * returns: None
		 * 
		 * This funtions job is to call the cut functions of
		 * the garden class. It takes a String[] as input, and 
		 * deciphers which cut function to call based on what
		 * the input is
		 */
		
		if (input.length == 1) garden.cut();
		else {
			if (input[1].charAt(0) == '(') {
				String[] coords = input[1].split(",");
				coords[0] = coords[0].replace("(", "");
				coords[1] = coords[1].replace(")", "");
				int[] coordinates = {Integer.valueOf(coords[0]), Integer.valueOf(coords[1])};
				garden.cut(coordinates);
			}
			else garden.cut(input[1].toLowerCase());
		}
	}
	
	public static void farm(String[] input, Garden garden) {
		/*
		 * args: String[] input, Garden garden
		 * returns: None
		 * 
		 * This funtions job is to call the farm functions of
		 * the garden class. It takes a String[] as input, and 
		 * deciphers which farm function to call based on what
		 * the input is
		 */
		
		if (input.length == 1) garden.farm();
		else {
			if (input[1].charAt(0) == '(') {
				String[] coords = input[1].split(",");
				coords[0] = coords[0].replace("(", "");
				coords[1] = coords[1].replace(")", "");
				int[] coordinates = {Integer.valueOf(coords[0]), Integer.valueOf(coords[1])};
				garden.farm(coordinates);
			}
			else garden.farm(input[1].toLowerCase());
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		/*
		 * this is the main function where the file is opened and read
		 * and based on what each line is in the file it calls a certain
		 * function to perform the given task
		 */
		
		File f = new File("garden1");
		Scanner reader = new Scanner(f);
		String[] r = reader.nextLine().split(" ");
		int row = Integer.valueOf(r[1]);
		String[] c = reader.nextLine().split(" ");
		int column = Integer.valueOf(c[1]);
		Garden garden = new Garden(row, column);
		Screen screen = new Screen(garden);
		reader.nextLine();
		if (column < 17) {
			while (reader.hasNextLine()) {
				String[] input = reader.nextLine().split(" ");
				input[0] = input[0].toLowerCase();
				if (input[0].equals("plant")) {
					String[] coords = input[1].split(",");
					coords[0] = coords[0].replace("(", "");
					coords[1] = coords[1].replace(")", "");
					int[] coordinates = {Integer.valueOf(coords[0]), Integer.valueOf(coords[1])};
					garden.plant(coordinates, input[2]);
				}
				else if (input[0].equals("print")) {
					screen.print();
					if (reader.hasNextLine()) {
						System.out.println();
					}
				}
				else if (input[0].equals("grow")) grow(input, garden);
				else if (input[0].equals("harvest")) harvest(input, garden);
				else if (input[0].equals("pick")) pick(input, garden);
				else if (input[0].equals("cut")) cut(input, garden);
				else if (input[0].equals("farm")) farm(input, garden);
			}
		}
		else System.out.println("Too many plot columns.");
		reader.close();
	}

}
