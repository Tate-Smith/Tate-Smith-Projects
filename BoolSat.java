/*
 * Author: Tate Smith
 * Class: CSC210
 * Project: BoolSat
 * 
 * This project uses many different classes
 * to ultimately do the function of completing a logical expression
 * it takes input through the terminal in the form
 * file name debug, if debug is on it returns the logical expression
 * and all possible combinations of true and fLalse for the variables
 * regardless of where the expression is true. But if off it only
 * prints the true expressions
 */

package com.gradescope.bool_exp;

import java.io.*;
import java.util.*;

public class BoolSat {
	public static boolean expression(boolean a, boolean b) {
		/*
		 * args: 2 booleans a and b
		 * returns: boolean
		 * 
		 * This function represents the NAND expression if both a and b
		 * are true then its false otherwise its true
		 */
		
		if (a && b) return false;
		return true;
	}
	public static void findVars(ASTNode root, HashMap<String, Boolean> vars) {
		/*
		 * args: root node of the AST tree, HashMap of String keys and boolean values
		 * returns: None
		 * 
		 * This function traverses the tree inorder to find all the possible variable
		 * and populate a hashmap with them as keys
		 */
		
		if (root == null) return;
		findVars(root.child1, vars);
		if (root.isId()) vars.put(root.getId(), null);
		findVars(root.child2, vars);
	}
	
	public static void traverse(ASTNode root, HashMap<String, Boolean> vars, ArrayList<Boolean> truths) {
		/*
		 * args: AST root node, HashMap of String keys and boolean values, an ArrayList of 
		 * booleans
		 * returns: None
		 * 
		 * This function given a tree root, and a map of variables and their truth values,
		 * traverses the tree with the truth values and solves the logical equation, using
		 * the expression function
		 */
		
		if (root == null) return;
		traverse(root.child1, vars, truths);
		traverse(root.child2, vars, truths);
		//checks to see if both children exist and are both id nodes
		if (root.child1 != null && root.child2 != null && !root.child1.isNand() && !root.child2.isNand()) {
			boolean child1 = vars.get(root.child1.getId());
			boolean child2 = vars.get(root.child2.getId());
			truths.add(expression(child1, child2));
		}
		//checks if both children exists and child1 is an id node and child2 is a Nand node
		else if (root.child1 != null && root.child2 != null && root.child1.isId() && root.child2.isNand()) {
			boolean child1 = vars.get(root.child1.getId());
			truths.add(expression(child1, truths.get(truths.size() - 1)));
			if (truths.size() > 2) truths.remove(1);
			else truths.remove(0);
		}
		//checks if both children exists and child2 is an id node and child1 is a Nand node
		else if (root.child2 != null && root.child1 != null && root.child2.isId() && root.child1.isNand()) {
			boolean child2 = vars.get(root.child2.getId());
			truths.add(expression(child2, truths.get(truths.size() - 1)));
			if (truths.size() > 2) truths.remove(1);
			else truths.remove(0);
		}
		//if the truths list is at least 2 then its calls this function
		else if (truths.size() > 1) {
			truths.add(expression(truths.get(0), truths.get(1)));
			truths.remove(0);
			truths.remove(0);
		}
	}
	
	public static void exhaustiveSearch(ASTNode root, HashMap<String, Boolean> vars, 
				ArrayList<Boolean> combos, boolean debug, ArrayList<String> strings, ArrayList<Boolean> satisfiable) {
		/*
		 * args: AST root node, HashMap of String keys and boolean values, an ArrayList of 
		 * booleans, a boolean debug, and ArrayList of Strings, and an ArrayList of Booleans
		 * returns: None
		 * 
		 * This function uses exhaustive backtracking to find every possible combinations
		 * of truth values for the given variables, then adds those combos to the HashMap
		 * vars with the given variables, it then calls traverse to solve the equation.
		 * if then creates a String based on the results from traverse and depending on debug's
		 * truth value it either only adds the variable combos that are satisfiable
		 * to strings, or all combos with their final truth value
		 */
		
		//only works if all the variables have a boolean value
		if (combos.size() == vars.size()) {
			String str = "";
			int i = 0;
			for (String s : vars.keySet()) {
				vars.put(s, combos.get(i));
				i++;
			}
			ArrayList<String> sorted = new ArrayList<String>(vars.keySet());
			ArrayList<Boolean> truths = new ArrayList<Boolean>();
			traverse(root, vars, truths);
			Collections.sort(sorted);
			//if the expression is true then it adds true to the list
			if (truths.get(0)) {
				satisfiable.add(true);
			}
			//if debug is on
			if (debug) {
				for (String v : sorted) str += v + ": " + vars.get(v) + ", ";
				str += truths.get(0) + "\n";
			}
			//if debug is off
			else {
				if (truths.get(0)) {
					for (String v : sorted) str += v + ": " + vars.get(v) + ", ";
					str = str.substring(0, str.length() - 2) + "\n";
				}
			}
			strings.add(str);
			return;
		}
		combos.add(false);
		exhaustiveSearch(root, vars, combos, debug, strings, satisfiable);
		combos.remove(combos.size() -1);
		combos.add(true);
		exhaustiveSearch(root, vars, combos, debug, strings, satisfiable);
		combos.remove(combos.size() -1);
	}
	
	public static String getOuput(ASTNode root, boolean debug) {
		/*
		 * args: AST root node, boolean debug
		 * returns: a String the output
		 * 
		 * This is the function called by the main method, it creates all the 
		 * variables and calls findVars and exhaustiveSearch, i then gets the 
		 * ArrayList output from exhaustiveSearch and converts it to a String
		 * then returns it for the main method to print out
		 */
		
		HashMap<String, Boolean> vars = new HashMap<String, Boolean>();
		//finds all variables
		findVars(root, vars);
		ArrayList<Boolean> combos = new ArrayList<Boolean>();
		ArrayList<String> strings = new ArrayList<String>();
		ArrayList<Boolean> s = new ArrayList<Boolean>();
		//finds all expressions
		exhaustiveSearch(root, vars, combos, debug, strings, s);
		Collections.sort(strings);
		String str = "";
		//makes the ArrayList into a string to return
		for (String i : strings) str += i;
		String satisfiable = "";
		//if the expression is satisfiable or not
		if (s.size() > 0 && s.get(0)) satisfiable = "SAT\n";
		else satisfiable = "UNSAT\n";
		return satisfiable + str;
	}
	
	public static void main(String[] args) throws FileNotFoundException{
        if(args.length < 1 || args.length > 2){
            System.err.println(
                    "USAGE: java BoolSat <inputFile.txt> <DEBUG*>");
            System.exit(1);
        }

        // Get the expression from the file
        String expression = null;
        Scanner s = new Scanner(new File(args[0]));
        expression = s.nextLine();

        System.out.println("input: " + expression);
        
        s.close();

        // call the parser to generate the AST for the expression
        ASTNode root = BoolSatParser.parse(expression);
        
        // get string output to print out
        String output;
        if (args.length == 2 && args[1].equals("DEBUG")) {
            output = getOuput(root, true);
        } else {
            output = getOuput(root, false);
        }
        
        // print output string
        System.out.println(output);
        
    }
}
