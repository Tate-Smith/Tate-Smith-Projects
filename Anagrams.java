/*
 * This project uses recursive backtracking to find
 * every possible combinations of anagrams in a given word.
 * The program prompts the user in the terminal in the format
 * file name, word to scramble, max number of anagrams. The
 * file is for all possible words that the letters of the
 * word to scramble could possibly create, the word to scramble
 * is the word you want to make anagrams out of. Finally max number
 * of anagrams is the max amount of anagrams the program is allowed
 * to make from one division of the word. if the max is 0
 * then it can make an infinite amount just depending on the amount
 * of characters per word
 */

package com.gradescope.anagrams;
import java.io.*;
import java.util.*;

public class Anagrams {
	public static HashSet<String> getWordList(String wordList) throws FileNotFoundException {
		/*
		 * args: String wordList
		 * returns: HashSet<string> of all words in the file
		 * 
		 * This functions job is to take a file name as input,
		 * then open this file and add each line into a HashSet
		 * then it returns that HashSet
		 */
		
		File f = new File(wordList);
		Scanner reader = new Scanner(f);
		HashSet<String> validWords = new HashSet<String>();
		
		//loops through the file and adds each line to the ArrayList
		while(reader.hasNextLine()) validWords.add(reader.nextLine().strip());
		reader.close();
		return validWords;
	}
	
	public static ArrayList<Character> getChars(String word) {
		/*
		 * args: String word
		 * returns: ArrayList<Character>
		 * 
		 * This function takes a word as input and splits that
		 * word up into all its characters, then adds those characters
		 * to an ArrayList, sorts the ArrayList and returns it
		 */
		
		ArrayList<Character> allChars = new ArrayList<Character>();
		word = word.toLowerCase();
		word = word.replace(" ", "");
		
		//loops through the word splitting up all its characters and adding them to an ArrayList
		for(int i = 0; i < word.length(); i ++) {
			allChars.add(word.charAt(i));
		}
		Collections.sort(allChars);
		return allChars;
	}
	
	public static void getCombinations(ArrayList<Character> allChars, String str, HashSet<String> validWords, HashSet<String> solutions) {
		/*
		 * args: ArrayList<Character>, String str, 
		 * HashSet<String>, HashSet<String>
		 * returns: None
		 * 
		 * This function inputs the ArrayList of all the
		 * characters in the word, an empty string, the HashSet of all
		 * the words from the file, and an empty HashSet. The Function uses
		 * recursive backtracking to find every possible combination of characters
		 * from the ArrayList, that is also a valid word in the HashSet validWords.
		 * it then adds this word to the empty HashSet solutions, and it does this
		 * until its gone through every possible combination
		 */
		
		if(validWords.contains(str)) solutions.add(str);
        boolean prefix = false; //checks to make sure that the str is a valid prefix in any word in validWords
        for(String s: validWords) {
        	if(s.startsWith(str)) prefix = true;
        }
        if(!prefix) return; //if the prefix doesn't exist in the words return
		for(int i = 0; i < allChars.size(); i++) { //it then recursively backtracks through every possible combination of characters
			char c = allChars.get(i);
			ArrayList<Character> newChars = new ArrayList<Character>(allChars);
			newChars.remove(i); //it then removes the character just used so it can't be an infinite loop
			getCombinations(newChars, str + c, validWords, solutions);
			}
	}
	
	public static void getAnagrams(int len, ArrayList<String> orderedSolution, String word, ArrayList<String> result, int max, int count, ArrayList<ArrayList<String>> allResults) {
		/*
		 * args: int len (length of the word), ArrayList<String> (all possible anagrams),
		 * String word, ArrayList<String> result an empty ArrayList, 
		 * int max (the max amount of anagrams), int count, 
		 * ArrayList<ArrayList<String>> (a 2D ArrayList of all possible anagram combos)
		 * returns: None
		 * 
		 * This functions main purpose is to use recursive backtracking to
		 * find all possible combinations of anagrams that work for the word.
		 * It makes sure there are no left over letters, and that there
		 * are not more anagrams than the max, if max is -1 then it can be an infinite
		 * amount of anagrams. It will recursively search until all 
		 * possible combinations are found
		 */
		
		if(len == count) { //if the num of letter match then add to result
			allResults.add(new ArrayList<String>(result)); 
			return;
		}
		else if(len < count) return; //if the num of letters is too much return
		else if(max != -1 && result.size() >= max) return; //if the size of result is larger than max return
		ArrayList<Character> wordChars = getChars(word);
		ArrayList<Character> anagrams = new ArrayList<Character>(); //populates an array with all characters in result
		for(String i : result) { 
			ArrayList<Character> resultChar = getChars(i);
			anagrams.addAll(resultChar);
		}
		for(char c : anagrams) { //removes all characters in the original array that are also in result
			int index = wordChars.indexOf(c);
			wordChars.remove(index);
		}
		HashSet<String> solutions = new HashSet<String>();
		HashSet<String> validWords = new HashSet<String>(orderedSolution);
		//calls combinations to see what words from valid words can be made with the leftover letters
		getCombinations(wordChars, "", validWords, solutions);
		ArrayList<String> newSolutions = new ArrayList<String>(solutions);
		Collections.sort(newSolutions);
		if(solutions.size() == 0) return; //makes sure there are still solutions for the letters left
		else {
			for(String s : newSolutions) { //uses newSolutions to show only valid words left
				result.add(s);
				getAnagrams(len, newSolutions, word, result, max, count + s.length(), allResults);
				result.remove(result.size() - 1);
			}
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
        
        String wordList = args[0];
        String word = args[1];
        int maxAnas = Integer.valueOf(args[2]);
        if (maxAnas == 0) maxAnas = -1;  // set to -1 for no limit

        System.out.println("Phrase to scramble: " + word);
        
        HashSet<String> validWords = getWordList(wordList);
        HashSet<String> solutions = new HashSet<String>();
        ArrayList<Character> allChars = getChars(word);
        
        getCombinations(allChars, "", validWords, solutions);
        ArrayList<String> orderedSolution = new ArrayList<String>(solutions);
        Collections.sort(orderedSolution);
        
        System.out.println("\nAll words found in " + word + ":");
        System.out.println(orderedSolution);
        
        ArrayList<String> result = new ArrayList<String>();
        System.out.println("\nAnagrams for " + word + ":");
        ArrayList<ArrayList<String>> allResults = new ArrayList<ArrayList<String>>();
        getAnagrams(word.length(), orderedSolution, word, result, maxAnas, 0, allResults);
        for (int i = 0; i < allResults.size(); i++) System.out.println(allResults.get(i));


    }

}
