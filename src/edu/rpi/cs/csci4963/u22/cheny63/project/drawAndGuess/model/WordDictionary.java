package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
/**
 * Word dictionary model
 * @author Jeff Li
 * @version 1.0
 */
public class WordDictionary{
    private HashMap<String, String> wordDictionary;
    private LinkedList<String> remainWord;
    
    /**
     * Constructor of the dictionary
     * @param filename the input dictionary file
     * @throws IOException if failed to read the dictionary file
     */
    public WordDictionary(String filename) throws IOException{
        readWord(filename);
        resetWordList();
    }
    
    /**
     * Get a random word from the remaining word and delete it from the list
     * Reset the list if the remain word list is empty
     * @return a random word from the dictionary that have not shown yet
     */
    public String getRandomWord(){
        int index = Math.abs(new Random().nextInt()) % remainWord.size();
        String word = remainWord.remove(index);
        if(remainWord.size() == 0){
            resetWordList();
        }
        return word;
    }
    
    /**
     * Get the category of the word
     * @param word the word
     * @return category
     */
    public String getCategory(String word){
        return wordDictionary.get(word);
    }
    
    /**
     * Reset the remaining word list to the dictionary
     */
    public void resetWordList(){
        remainWord = new LinkedList<>(wordDictionary.keySet());
    }
    
    /**
     * Read the dictionary file and load the data to the dictionary
     * @param filename dictionary input file
     * @throws IOException if cannot read the file
     */
    private void readWord(String filename) throws IOException{
        wordDictionary = new HashMap<String,String>();
        FileReader fd = new FileReader(filename);
        BufferedReader reader = new BufferedReader(fd);
        String line;
        boolean first = true;
        String Sep = ",";
		while((line = reader.readLine()) != null) {
            // Get the seperator for the csv file
			if(first){
				if(line.contains(",")) {
					Sep = ",";
				}
				else if(line.contains(";")) {
					Sep = ";";
				}
				else if(line.contains("|")) {
					Sep = "|";
				}
				else if(line.contains(" ")) {
					Sep = " ";
				}
                first = false;
			}
            // Remove the encapsulator
			line = line.replace("\"", "");
			line = line.replace("\'", "");
            // Save the word into the dictionary
			String[] nodes = line.split(Sep);
			if (nodes.length > 2) {
                for (int i = 1; i < nodes.length; i++) {
                	wordDictionary.put(nodes[i], nodes[0]);
                }
            }else{
            	wordDictionary.put(nodes[1], nodes[0]);
            }
		}
        reader.close();
    }
}
